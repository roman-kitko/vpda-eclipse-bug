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
package org.vpda.clientserver.communication.moduleentry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.vpda.clientserver.communication.module.ClientServerCoreModule;
import org.vpda.common.comps.loc.BasicCompsLocValueBuilderCollector;
import org.vpda.common.core.module.CommonCoreModule;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPoint;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPointImpl;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.entrypoint.ConfigurationConstants;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.ioc.picocontainer.PicoHelper;
import org.vpda.common.service.ServiceRegistry;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.resources.AllModulesRelativeInputStreamResolver;
import org.vpda.common.service.resources.ModuleHomeRelativeURLResolver;
import org.vpda.common.service.resources.RelativePathInputStreamResolver;
import org.vpda.common.service.resources.ResourceService;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;

/**
 * @author kitko
 *
 */
public final class ClientServerCoreModuleEntryPointImpl extends AbstractModuleEntryPoint implements ClientServerCoreModuleEntryPoint {
    private static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(ClientServerCoreModuleEntryPointImpl.class);

    @Override
    protected Module createModule() {
        return ClientServerCoreModule.getInstance();
    }

    @Override
    public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        LocValueBuilderFactory locValueBuilderFactory = PicoHelper.getMandatoryComponent(module.getContainer(), LocValueBuilderFactory.class);
        Application application = appEntryPoint.getApplication();
        ServiceRegistry serviceRegistry = CommonCoreModule.getInstance().getContainer().getComponent(ServiceRegistry.class);
        ResourceService resourceService = serviceRegistry.getService(ResourceService.class);
        RelativePathInputStreamResolver iconsStreamResolver = new AllModulesRelativeInputStreamResolver(application, "", ModuleHomeRelativeURLResolver.create(ConfigurationConstants.DATA_ICONS_PATH));
        BasicCompsLocValueBuilderCollector builder = new BasicCompsLocValueBuilderCollector(locValueBuilderFactory, resourceService, iconsStreamResolver);
        builder.collect();
        logger.methodExit(method);
    }

    @Override
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
        List<Class<? extends ModuleEntryPoint>> reqModules = new ArrayList<Class<? extends ModuleEntryPoint>>(2);
        reqModules.add(CommonCoreModuleEntryPoint.class);
        return reqModules;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
        if (CommonCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) CommonCoreModuleEntryPointImpl.class;
        }
        return null;
    }

}
