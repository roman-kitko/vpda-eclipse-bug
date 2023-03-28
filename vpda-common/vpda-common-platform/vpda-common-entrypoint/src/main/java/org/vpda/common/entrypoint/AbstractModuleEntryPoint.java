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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;

/**
 * Abstraction with defalt impl of {@link ModuleEntryPoint}
 * 
 * @author kitko
 *
 */
public abstract class AbstractModuleEntryPoint implements ModuleEntryPoint {
    /** Module of this entry point */
    protected Module module;

    /**
     * Creates module
     * 
     * @return module, never null
     */
    protected abstract Module createModule();

    @Override
    public Module getModule() {
        if (module == null) {
            module = createModule();
        }
        return module;
    }

    /**
     * Gets submodule entry point classes from container
     * 
     * @return submodule entry point classes from container
     */
    protected List<Class<? extends ModuleEntryPoint>> getSubmodulesEntryPointClasses() {
        String key = module.getName() + "-" + "submodules";
        @SuppressWarnings("unchecked")
        List<Class<? extends ModuleEntryPoint>> classes = (List<Class<? extends ModuleEntryPoint>>) module.getContainer().getComponent(key);
        return classes != null ? Collections.unmodifiableList(classes) : Collections.<Class<? extends ModuleEntryPoint>>emptyList();
    }

    /**
     * Gets submodule entry point classes from container
     * 
     * @return submodule entry point classes from container
     */
    protected List<Class<? extends ModuleEntryPoint>> getExtensionsEntryPointClasses() {
        String key = module.getName() + "-" + "extensions";
        @SuppressWarnings("unchecked")
        List<Class<? extends ModuleEntryPoint>> classes = (List<Class<? extends ModuleEntryPoint>>) module.getContainer().getComponent(key);
        return classes != null ? Collections.unmodifiableList(classes) : Collections.<Class<? extends ModuleEntryPoint>>emptyList();
    }

    /**
     * @param clazz
     * @return submodule entry
     */
    protected <T extends ModuleEntryPoint> T createChildModuleEntryPoint(AppEntryPoint appEntryPoint, Class<T> clazz) {
        return appEntryPoint.createChildModuleEntryPoint(clazz, module);
    }

    /**
     * Runs single module
     * 
     * @param appEntryPoint
     * @param clazz
     * @param moduleEntryPoint
     */
    protected <T extends ModuleEntryPoint> void runModule(AppEntryPoint appEntryPoint, Class<? extends ModuleEntryPoint> clazz, ModuleEntryPoint moduleEntryPoint) {
        appEntryPoint.runModule(clazz, moduleEntryPoint);
    }

    /**
     * Runs all submodules. Module itself is responsible to call that.
     * 
     * @param appEntryPoint
     * @return run modules entry points
     */
    protected List<ModuleEntryPoint> runAllSubmodules(AppEntryPoint appEntryPoint) {
        LoggerMethodTracer logger = LoggerMethodTracer.getLogger(getClass());
        MethodTimer methodEntry = logger.methodEntry(Level.INFO);
        List<ModuleEntryPoint> result = new ArrayList<>();
        List<Class<? extends ModuleEntryPoint>> clazzes = getSubmodulesEntryPointClasses();
        for (Class<? extends ModuleEntryPoint> clazz : clazzes) {
            ModuleEntryPoint submoduleEntryPoint = createChildModuleEntryPoint(appEntryPoint, clazz);
            appEntryPoint.runModule(clazz, submoduleEntryPoint);
            result.add(submoduleEntryPoint);
        }
        logger.methodExit(methodEntry);
        return result;
    }

    /**
     * Runs all extensions. Module itself is responsible to call that
     * 
     * @param appEntryPoint
     * @return run modules entry points
     */
    protected List<ModuleEntryPoint> runAllExtensions(AppEntryPoint appEntryPoint) {
        LoggerMethodTracer logger = LoggerMethodTracer.getLogger(getClass());
        MethodTimer methodEntry = logger.methodEntry(Level.INFO);
        List<ModuleEntryPoint> result = new ArrayList<>();
        List<Class<? extends ModuleEntryPoint>> clazzes = getExtensionsEntryPointClasses();
        for (Class<? extends ModuleEntryPoint> clazz : clazzes) {
            ModuleEntryPoint submoduleEntryPoint = createChildModuleEntryPoint(appEntryPoint, clazz);
            runModule(appEntryPoint, clazz, submoduleEntryPoint);
            result.add(submoduleEntryPoint);
        }
        logger.methodExit(methodEntry);
        return result;
    }

    public List<Class<? extends ModuleEntryPoint>> filterMappingsForInterfaceModuleWhenRunAsRequiredModule(Class<? extends ModuleEntryPoint> clazz, List<Class<? extends ModuleEntryPoint>> list) {
        return list;
    }

}
