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
package org.vpda.abstractclient.viewprovider.ui.moduleentry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.vpda.abstractclient.core.moduleentry.AbstractClientCoreModuleEntryPoint;
import org.vpda.abstractclient.core.moduleentry.GenericClientCoreModuleEntryPointImpl;
import org.vpda.abstractclient.data.moduleentry.AbstractClientDataModuleEntryPoint;
import org.vpda.abstractclient.data.moduleentry.AbstractClientDataModuleEntryPointImpl;
import org.vpda.abstractclient.viewprovider.ui.module.AbstractClientViewProviderModule;
import org.vpda.clientserver.communication.moduleentry.ClientServerCoreModuleEntryPoint;
import org.vpda.clientserver.communication.moduleentry.ClientServerCoreModuleEntryPointImpl;
import org.vpda.clientserver.viewprovider.moduleentry.ClientServerViewProviderModuleEntryPoint;
import org.vpda.clientserver.viewprovider.moduleentry.ClientServerViewProviderModuleEntryPointImpl;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPoint;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPointImpl;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;

/**
 * Entry point implementation to abstract client view provider module.
 * 
 * @author kitko
 *
 */
public final class AbstractClientViewProviderModuleEntryPointImpl extends AbstractModuleEntryPoint implements AbstractClientViewProviderModuleEntryPoint {
    private static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AbstractClientViewProviderModuleEntryPointImpl.class);

    /**
     * 
     */
    public AbstractClientViewProviderModuleEntryPointImpl() {
    }

    @Override
    protected Module createModule() {
        return AbstractClientViewProviderModule.getInstance();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
        if (CommonCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) CommonCoreModuleEntryPointImpl.class;
        }
        else if (ClientServerCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) ClientServerCoreModuleEntryPointImpl.class;
        }
        else if (AbstractClientCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) GenericClientCoreModuleEntryPointImpl.class;
        }
        else if (ClientServerViewProviderModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) ClientServerViewProviderModuleEntryPointImpl.class;
        }
        else if (AbstractClientDataModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) AbstractClientDataModuleEntryPointImpl.class;
        }
        return null;
    }

    @Override
    public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        logger.methodExit(method);
    }

    @Override
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
        List<Class<? extends ModuleEntryPoint>> reqModules = new ArrayList<Class<? extends ModuleEntryPoint>>(2);
        reqModules.add(CommonCoreModuleEntryPoint.class);
        reqModules.add(ClientServerCoreModuleEntryPoint.class);
        reqModules.add(AbstractClientCoreModuleEntryPoint.class);
        reqModules.add(AbstractClientDataModuleEntryPoint.class);
        reqModules.add(ClientServerViewProviderModuleEntryPoint.class);
        return reqModules;
    }

}
