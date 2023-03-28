/**
 * View provider driven applications - java application framework for developing RIA
 * Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vpda.common.entrypoint;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.vpda.common.ioc.picocontainer.PicoHelper;
import org.vpda.common.util.exceptions.VPDAConfigurationRuntimeException;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;
import org.vpda.internal.common.util.ClassUtil;
import org.vpda.internal.common.util.CollectionUtil;
import org.vpda.internal.common.util.ProxyUtil;
import org.vpda.internal.common.util.StaticCache;

/**
 * Main impl of {@link AppEntryPoint}
 * 
 * @author kitko
 *
 */
public final class AppEntryPointImpl implements AppEntryPoint {
    private static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AppEntryPointImpl.class);
    private MuttableApplication application;
    private AppEntryPointListenerSupport listenerSupport;
    private Application publicApplication;
    private final List<ModuleMapping> alreadyRunModules;
    private final Deque<ModuleMapping> currentModulesRunStack;

    private static final class ModuleMapping {
        Class<? extends ModuleEntryPoint> clazz;
        ModuleEntryPoint impl;

        ModuleMapping(Class<? extends ModuleEntryPoint> clazz, ModuleEntryPoint impl) {
            this.clazz = clazz;
            this.impl = impl;
        }

        @Override
        public String toString() {
            return clazz + " -> " + impl.getClass();
        }
    }

    /**
     * Creates AppEntryPointImpl
     */
    public AppEntryPointImpl() {
        listenerSupport = new AppEntryPointListenerSupport();
        alreadyRunModules = new ArrayList<>();
        currentModulesRunStack = new LinkedList<>();
    }

    @Override
    public void entryPoint(Application application) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        if (application == null) {
            throw new IllegalArgumentException("application is null");
        }
        StaticCache.clearCurrentCache();
        StaticCache.put(Application.class, application);
        for (EntryPointListener listener : listenerSupport.getListeners()) {
            listener.applicationEntryStart(this, application);
        }
        this.application = (MuttableApplication) application;
        publicApplication = ProxyUtil.createDelegatingProxy(Thread.currentThread().getContextClassLoader(), Application.class, this.application);
        ModulesConfiguration modulesConf = PicoHelper.getMandatoryComponent(application.getRootContainer(), ModulesConfiguration.class);
        entryModules(modulesConf);
        for (EntryPointListener listener : listenerSupport.getListeners()) {
            listener.applicationEntryExit(this, application);
        }
        listenerSupport.clear();
        // Nobody should add listeners after entry point is done
        listenerSupport = null;
        logger.methodExit(method);
    }

    private void entryModules(ModulesConfiguration modulesConf) {
        List<ModuleMapping> entryPoints = new ArrayList<ModuleMapping>();
        for (Class<? extends ModuleEntryPoint> clazz : modulesConf.getModuleEntryPoints()) {
            try {
                ModuleEntryPoint moduleEntryPoint = modulesConf.createModuleEntryPoint(clazz);
                entryPoints.add(new ModuleMapping(clazz, moduleEntryPoint));
            }
            catch (Exception e) {
                logger.logAndThrowRuntimeException(Level.SEVERE, "Error creating module entry point", e);
            }
        }
        for (ModuleMapping moduleEntryPoint : entryPoints) {
            entryRootModule(modulesConf, moduleEntryPoint);

        }
    }

    private void entryRootModule(ModulesConfiguration modulesConf, ModuleMapping moduleEntryPoint) {
        if (entryModule(moduleEntryPoint, modulesConf)) {
            Module module = moduleEntryPoint.impl.getModule();
            if (module.isInterfaceModule()) {
                List<Class<? extends ModuleEntryPoint>> mappingsForInterfaceModule = modulesConf.getFilteredMappingsForInterfaceModuleWhenRunningAsRootModule(moduleEntryPoint.clazz);
                CollectionUtil.emptyIfNull(mappingsForInterfaceModule).forEach(requiredImplClass -> {
                    if (findSameModuleAlreadyRunOrRunningNow(requiredImplClass) == null) { // just to not create if already run
                        ModuleEntryPoint requiredModuleEntryPoint = createModuleEntryPoint(modulesConf, moduleEntryPoint.impl, requiredImplClass);
                        entryModule(new ModuleMapping(requiredImplClass, requiredModuleEntryPoint), modulesConf);
                    }
                });
            }
        }
    }

    private ModuleMapping findSameModuleAlreadyRunOrRunningNow(Class<? extends ModuleEntryPoint> moduleClass) {
        for (ModuleMapping alreadyRunModule : alreadyRunModules) {
            if (moduleMatch(moduleClass, alreadyRunModule)) {
                return alreadyRunModule;
            }
        }
        if (!currentModulesRunStack.isEmpty()) {
            for (Iterator<ModuleMapping> descendingIterator = currentModulesRunStack.descendingIterator(); descendingIterator.hasNext();) {
                ModuleMapping stackEntry = descendingIterator.next();
                if (moduleMatch(moduleClass, stackEntry)) {
                    return stackEntry;
                }
            }
        }
        return null;
    }

    private boolean moduleMatch(Class<? extends ModuleEntryPoint> requiredModuleClass, ModuleMapping alreadyRunModule) {
        if (requiredModuleClass.isAssignableFrom(alreadyRunModule.clazz)) {
            return true;
        }
        if (requiredModuleClass.isAssignableFrom(alreadyRunModule.impl.getClass().getSuperclass())) {
            return true;
        }
        return false;
    }

    /**
     * Run module entry points and register module in this appEntryPoint This method
     * also handle modules dependencies
     * 
     * @param moduleEntryPointMapping
     */
    private boolean entryModule(ModuleMapping moduleEntryPointMapping, ModulesConfiguration modulesConf) {
        if (moduleEntryPointMapping == null || moduleEntryPointMapping.impl == null) {
            throw new IllegalArgumentException("ModuleEntryPoint argument cannot be null");
        }
        if (findSameModuleAlreadyRunOrRunningNow(moduleEntryPointMapping.clazz) != null) {
            return false;
        }
        handleRequiredModulesForEntryPoint(moduleEntryPointMapping, modulesConf);
        // Need to check again, because the same module could be required also from
        // other place
        if (findSameModuleAlreadyRunOrRunningNow(moduleEntryPointMapping.clazz) != null) {
            return false;
        }
        MuttableModule module = (MuttableModule) moduleEntryPointMapping.impl.getModule();
        ModuleConfiguration moduleConf = modulesConf.createConfigurationForModule(moduleEntryPointMapping.clazz, module);
        module.setModuleContainer(application.getRootContainer(), moduleConf.getContainer());
        module.setApplication(publicApplication);
        application.registerModule(module);
        for (EntryPointListener listener : listenerSupport.getListeners()) {
            listener.moduleEntryStart(this, moduleEntryPointMapping.clazz, moduleEntryPointMapping.impl, module);
        }
        logger.log(Level.INFO, "Starting module entrypoint [{0}]", moduleEntryPointMapping.impl.getClass().getName());
        currentModulesRunStack.push(moduleEntryPointMapping);
        try {
            moduleEntryPointMapping.impl.entryPoint(this, module);
        }
        finally {
            currentModulesRunStack.pop();
        }
        alreadyRunModules.add(moduleEntryPointMapping);
        logger.log(Level.INFO, "Module entrypoint [{0}] finished", moduleEntryPointMapping.impl.getClass().getName());
        module.setCompleted();
        for (EntryPointListener listener : listenerSupport.getListeners()) {
            listener.moduleEntryExit(this, moduleEntryPointMapping.clazz, moduleEntryPointMapping.impl, module);
        }
        return true;
    }

    private void handleRequiredModulesForEntryPoint(ModuleMapping moduleEntryPointMapping, ModulesConfiguration modulesConf) {
        if (moduleEntryPointMapping.impl == null) {
            throw new VPDARuntimeException("We are missing impl binding for module entry point : " + moduleEntryPointMapping.clazz);
        }
        MuttableModule module = (MuttableModule) moduleEntryPointMapping.impl.getModule();
        List<Class<? extends ModuleEntryPoint>> requiredModulesClasses = moduleEntryPointMapping.impl.getRequiredModuleEntryPointsClasses(this);
        if (CollectionUtil.isEmpty(requiredModulesClasses)) {
            return;
        }
        for (Class<? extends ModuleEntryPoint> requiredModuleClass : requiredModulesClasses) {
            handleRunRequiredSingleModule(modulesConf, moduleEntryPointMapping, module, requiredModuleClass);
        }
    }

    private void handleRunRequiredSingleModule(ModulesConfiguration modulesConf, ModuleMapping moduleEntryPointMapping, MuttableModule module, Class<? extends ModuleEntryPoint> requiredModuleClass) {
        // We must look if module is already in appEntryPoint
        ModuleMapping findSameModuleAlreadyRun = findSameModuleAlreadyRunOrRunningNow(requiredModuleClass);
        if (findSameModuleAlreadyRun != null) {
            module.addDependsOnModule(findSameModuleAlreadyRun.impl.getModule());
            handleAddDependsOnInterfaceModule(modulesConf, moduleEntryPointMapping, module, findSameModuleAlreadyRun.clazz, findSameModuleAlreadyRun.impl.getModule());
            return;

        }
        // We will try to create module and run it
        ModuleEntryPoint requiredModuleEntryPoint = createModuleEntryPoint(modulesConf, moduleEntryPointMapping.impl, requiredModuleClass);
        entryModule(new ModuleMapping(requiredModuleClass, requiredModuleEntryPoint), modulesConf);
        module.addDependsOnModule(requiredModuleEntryPoint.getModule());
        handleRunInterfaceMappingModules(modulesConf, moduleEntryPointMapping, module, requiredModuleClass, requiredModuleEntryPoint.getModule());
    }

    private void handleRunInterfaceMappingModules(ModulesConfiguration modulesConf, ModuleMapping moduleEntryPointMapping, MuttableModule module, Class<? extends ModuleEntryPoint> requiredModuleClass,
            Module requiredModule) {
        if (requiredModule.isInterfaceModule()) {
            // If the module is interface and we have mapping, then run also mapped modules
            List<Class<? extends ModuleEntryPoint>> mappingsForInterfaceModule = moduleEntryPointMapping.impl.filterMappingsForInterfaceModuleWhenRunAsRequiredModule(requiredModuleClass,
                    CollectionUtil.emptyIfNull(modulesConf.getMappingsForInterfaceModule(requiredModuleClass)));
            CollectionUtil.emptyIfNull(mappingsForInterfaceModule).forEach(requiredImplClass -> {
                handleRunRequiredSingleModule(modulesConf, moduleEntryPointMapping, module, requiredImplClass);
            });
        }
    }

    private void handleAddDependsOnInterfaceModule(ModulesConfiguration modulesConf, ModuleMapping moduleEntryPointMapping, MuttableModule module,
            Class<? extends ModuleEntryPoint> requiredModuleClass, Module requiredModule) {
        if (requiredModule.isInterfaceModule()) {
            // If the module is interface and we have mapping, then add dependencies to all
            // mapped modules to the handled module
            List<Class<? extends ModuleEntryPoint>> mappingsForInterfaceModule = modulesConf.getMappingsForInterfaceModule(requiredModuleClass);
            if (CollectionUtil.isNotEmpty(mappingsForInterfaceModule)) {
                mappingsForInterfaceModule.forEach(requiredImplClass -> {
                    ModuleMapping findSameModuleAlreadyRun = findSameModuleAlreadyRunOrRunningNow(requiredModuleClass);
                    if (findSameModuleAlreadyRun != null) {
                        module.addDependsOnModule(findSameModuleAlreadyRun.impl.getModule());
                    }
                });
            }
        }
    }

    private ModuleEntryPoint createModuleEntryPoint(ModulesConfiguration modulesConf, ModuleEntryPoint currentMappingModule, Class<? extends ModuleEntryPoint> clazz) {
        ModuleEntryPoint requiredModuleEntryPoint = modulesConf.createModuleEntryPoint(clazz);
        if (requiredModuleEntryPoint == null) {
            Class<? extends ModuleEntryPoint> implClass = null;
            if (currentMappingModule != null) {
                implClass = currentMappingModule.getDefImplClassForModule(clazz);
            }
            if (implClass == null) {
                if (!this.currentModulesRunStack.isEmpty()) {
                    for (Iterator<ModuleMapping> descendingIterator = currentModulesRunStack.descendingIterator(); descendingIterator.hasNext();) {
                        ModuleEntryPoint stackEntry = descendingIterator.next().impl;
                        implClass = stackEntry.getDefImplClassForModule(clazz);
                        if (implClass != null) {
                            break;
                        }
                    }
                }
            }
            if (implClass == null) {
                throw new VPDAConfigurationRuntimeException("Cannot create moduleentrypoint " + clazz);
            }
            requiredModuleEntryPoint = ClassUtil.createInstance(implClass);
        }
        return requiredModuleEntryPoint;
    }

    @Override
    public Application getApplication() {
        return publicApplication;
    }

    @Override
    public AppEntryPointListenerSupport getListenerSupport() {
        return listenerSupport;
    }

    @Override
    public void runModule(Class<? extends ModuleEntryPoint> clazz, ModuleEntryPoint moduleEntry) {
        if (!clazz.isInstance(moduleEntry)) {
            throw new IllegalArgumentException("Passed moduleEntryPoint of class " + moduleEntry.getClass() + " is not instance of " + clazz);
        }
        ModuleMapping moduleEntryPointMapping = new ModuleMapping(clazz, moduleEntry);
        ModulesConfiguration modulesConf = PicoHelper.getMandatoryComponent(application.getRootContainer(), ModulesConfiguration.class);
        entryModule(moduleEntryPointMapping, modulesConf);
    }

    public <T extends ModuleEntryPoint> T createChildModuleEntryPoint(Class<T> clazz, Module parent) {
        ModulesConfiguration modulesConf = PicoHelper.getMandatoryComponent(application.getRootContainer(), ModulesConfiguration.class);
        ModuleEntryPoint submoduleEntryPoint = createModuleEntryPoint(modulesConf, null, clazz);
        Module newModule = submoduleEntryPoint.getModule();
        if (newModule.isExtension()) {
            if (newModule instanceof MuttableModule) {
                ((MuttableModule) newModule).setParent(parent);
            }
            if (parent instanceof MuttableModule) {
                ((MuttableModule) parent).addExtension(newModule);
            }
        }
        if (newModule.isSubModule()) {
            if (newModule instanceof MuttableModule) {
                ((MuttableModule) newModule).setParent(parent);
            }
            if (parent instanceof MuttableModule) {
                ((MuttableModule) parent).addSubmodule(newModule);
            }
        }
        return clazz.cast(submoduleEntryPoint);
    }

}
