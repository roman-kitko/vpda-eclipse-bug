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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.vpda.common.entrypoint.AppConfiguration;
import org.vpda.common.entrypoint.AppConfigurationImpl;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.AppEntryPointImpl;
import org.vpda.common.entrypoint.ConcreteApplication;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.entrypoint.ModulesConfiguration;
import org.vpda.common.entrypoint.ModulesConfigurationtImpl;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * Test of {@link ClientServerCoreModuleEntryPointImpl}
 * 
 * @author kitko
 *
 */
public class ClientServerCoreModuleEntryPointImplTest {

    /**
     * 
     */
    @Test
    public void testEntryPoint() {
        AppEntryPoint entryPoint = new AppEntryPointImpl();
        AppConfiguration appConf = new AppConfigurationImpl();
        List<Class<? extends ModuleEntryPoint>> modules = new ArrayList<Class<? extends ModuleEntryPoint>>(1);
        modules.add(ClientServerCoreModuleEntryPoint.class);
        appConf.getRootContainer().addComponent(ClientServerCoreModuleEntryPoint.class, ClientServerCoreModuleEntryPointImpl.class);
        ModulesConfiguration modulesConf = new ModulesConfigurationtImpl(appConf, modules);
        appConf.getRootContainer().addComponent(ModulesConfiguration.class, modulesConf);
        ConcreteApplication application = new ConcreteApplication(CommonUtilConstants.VPDA_PROJECT_NAME, appConf);
        entryPoint.entryPoint(application);
    }

    /**
     * 
     */
    @Test
    public void testGetModule() {
        ClientServerCoreModuleEntryPoint moduleEntryPoint = new ClientServerCoreModuleEntryPointImpl();
        assertNotNull(moduleEntryPoint.getModule());
    }

}
