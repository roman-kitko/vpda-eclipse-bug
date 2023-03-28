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
package org.vpda.clientserver.viewprovider.moduleentry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.vpda.clientserver.communication.moduleentry.ClientServerCoreModuleEntryPoint;
import org.vpda.clientserver.communication.moduleentry.ClientServerCoreModuleEntryPointImpl;
import org.vpda.clientserver.viewprovider.ViewProviderCompsLocValueBuilderCollector;
import org.vpda.clientserver.viewprovider.module.ClientServerViewProviderModule;
import org.vpda.common.comps.moduleentry.CommonComponentsModuleEntryPoint;
import org.vpda.common.comps.moduleentry.CommonComponentsModuleEntryPointImpl;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;

/**
 * Entry point implementation to {@link ClientServerViewProviderModule}
 * 
 * @author kitko
 *
 */
public final class ClientServerViewProviderModuleEntryPointImpl extends AbstractModuleEntryPoint implements ClientServerViewProviderModuleEntryPoint {
    private static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(ClientServerViewProviderModuleEntryPointImpl.class);

    @Override
    protected Module createModule() {
        return ClientServerViewProviderModule.getInstance();
    }

    @Override
    public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        ViewProviderCompsLocValueBuilderCollector builder = new ViewProviderCompsLocValueBuilderCollector(module.getContainer().getComponent(LocValueBuilderFactory.class));
        builder.collect();
        logger.methodExit(method);

    }

    @Override
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
        List<Class<? extends ModuleEntryPoint>> reqModules = new ArrayList<Class<? extends ModuleEntryPoint>>(2);
        reqModules.add(ClientServerCoreModuleEntryPoint.class);
        reqModules.add(CommonComponentsModuleEntryPoint.class);
        return reqModules;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
        if (ClientServerCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) ClientServerCoreModuleEntryPointImpl.class;
        }
        if (CommonComponentsModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) CommonComponentsModuleEntryPointImpl.class;
        }
        return null;
    }

}
