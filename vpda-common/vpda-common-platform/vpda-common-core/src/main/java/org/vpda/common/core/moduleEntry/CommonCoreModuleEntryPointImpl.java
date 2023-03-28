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
package org.vpda.common.core.moduleEntry;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import org.vpda.common.command.CommandConst;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorRegistryImpl;
import org.vpda.common.core.module.CommonCoreModule;
import org.vpda.common.core.shutdown.ShutdownRegistry;
import org.vpda.common.core.shutdown.ShutdownRegistryImpl;
import org.vpda.common.dto.factory.CommonDTOsFactory;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger.LoggerBasedMetaClassesGeneratorLogger;
import org.vpda.common.dto.runtime.spi.DTORuntimeBootstrap;
import org.vpda.common.dto.runtime.spi.DTORuntimeRegistry;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.ioc.picocontainer.PicoHelper;
import org.vpda.common.service.MuttableServiceRegistry;
import org.vpda.common.service.ServiceRegistry;
import org.vpda.common.service.ServiceRegistryImpl;
import org.vpda.common.service.localization.EHCacheLikeLocalizationServiceCacheImpl;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocValueBuilderRegistry;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.LocalizationServiceCache;
import org.vpda.common.service.localization.LocalizationServiceImpl;
import org.vpda.common.service.localization.LocalizationValueResolver;
import org.vpda.common.service.localization.ModulesLocalizationValueResolver;
import org.vpda.common.service.resources.ResourceService;
import org.vpda.common.service.resources.ResourceServiceImpl;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;

/**
 * @author kitko
 *
 */
public final class CommonCoreModuleEntryPointImpl extends AbstractModuleEntryPoint implements CommonCoreModuleEntryPoint {
    private LoggerMethodTracer logger = LoggerMethodTracer.getLogger(CommonCoreModuleEntryPointImpl.class);

    
    @Override
    public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        registerShutdownRegistry(appEntryPoint, module);
        registerDTORuntimeRegistry(appEntryPoint, module);
        registerDTOModel(appEntryPoint, module);
        registerServices(appEntryPoint, module);
        createCommandExecutors(appEntryPoint, module);
        logger.methodExit(method);
    }

    private void registerDTOModel(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        DTORuntimeRegistry registry = PicoHelper.getMandatoryComponent(module.getContainer(), DTORuntimeRegistry.class);
        DTORuntimeBootstrap.createAllModelsAndInitMetaClasses(new CommonDTOsFactory().createInput(),
                new LoggerBasedMetaClassesGeneratorLogger(logger, Level.INFO), registry);
        logger.methodExit(method);
        
    }

    private void registerDTORuntimeRegistry(AppEntryPoint appEntryPoint, Module module) {
        DTORuntimeRegistry registry = module.getContainer().getComponent(DTORuntimeRegistry.class);
        if(registry == null) {
            registry = DTORuntimeBootstrap.createDTORuntimeRegistry();
            module.getContainer().addComponent(DTORuntimeRegistry.class, registry);
        }
    }

    private void registerShutdownRegistry(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        ShutdownRegistry shutdownRegistry = module.getContainer().getComponent(ShutdownRegistry.class);
        if (shutdownRegistry == null) {
            shutdownRegistry = new ShutdownRegistryImpl(new CommandExecutorBase("ShutdownExecutor"), EmptyCommandExecutionEnv.getInstance());
            module.getContainer().addComponent(ShutdownRegistry.class, shutdownRegistry);
        }
        logger.methodExit(method);
    }

    private void registerServices(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        PicoHelper.registerComponentIfNotPresent(module.getContainer(), LocValueBuilderFactory.class, new LocValueBuilderRegistry());
        LocValueBuilderFactory locValueBuilderFactory = module.getContainer().getComponent(LocValueBuilderFactory.class);

        LocalizationService locService = module.getContainer().getComponent(LocalizationService.class);
        if (locService == null) {
            LocalizationValueResolver lvr = new ModulesLocalizationValueResolver(appEntryPoint.getApplication(), appEntryPoint.getListenerSupport());
            LocalizationServiceCache cache = Objects.requireNonNullElseGet(module.getContainer().getComponent(LocalizationServiceCache.class), () -> new EHCacheLikeLocalizationServiceCacheImpl());
            locService = new LocalizationServiceImpl(lvr, locValueBuilderFactory, cache);
            module.getContainer().addComponent(LocalizationService.class, locService);
        }
        MuttableServiceRegistry sr = (MuttableServiceRegistry) module.getContainer().getComponent(ServiceRegistry.class);
        if (sr == null) {
            sr = new ServiceRegistryImpl();
            module.getContainer().addComponent(ServiceRegistry.class, sr);
        }
        ResourceService rs = module.getContainer().getComponent(ResourceService.class);
        if (rs == null) {
            rs = new ResourceServiceImpl();
            module.getContainer().addComponent(ResourceService.class, rs);
        }
        sr.registerService(LocalizationService.class, locService);
        sr.registerService(ResourceService.class, rs);
        logger.methodExit(method);
    }

    @Override
    protected Module createModule() {
        return CommonCoreModule.getInstance();
    }

    @Override
    public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
        return null;
    }

    /**
     * Creates main command executors
     * 
     * @param appEntryPoint
     * @param module
     */
    protected void createCommandExecutors(AppEntryPoint appEntryPoint, Module module) {
        CommandExecutorRegistry commandExecutorRegistry = module.getContainer().getComponent(CommandExecutorRegistry.class);
        if (commandExecutorRegistry == null) {
            commandExecutorRegistry = new CommandExecutorRegistryImpl();
            module.getContainer().addComponent(CommandExecutorRegistry.class, commandExecutorRegistry);
        }
        commandExecutorRegistry.registerCommandExecutor(new CommandExecutorBase(CommandConst.DEFAULT_COMMON_EXECUTOR));
    }

    @Override
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
        return Collections.emptyList();
    }

}
