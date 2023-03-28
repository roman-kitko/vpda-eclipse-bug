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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.picocontainer.Parameter;
import org.vpda.common.entrypoint.AppConfiguration;
import org.vpda.common.entrypoint.AppConfigurationImpl;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.AppEntryPointImpl;
import org.vpda.common.entrypoint.ConcreteApplication;
import org.vpda.common.entrypoint.ModuleConfiguration;
import org.vpda.common.entrypoint.ModuleConfigurationImpl;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.entrypoint.ModulesConfiguration;
import org.vpda.common.entrypoint.ModulesConfigurationtImpl;
import org.vpda.common.entrypoint.SimpleModuleConfigurationFactory;
import org.vpda.common.service.localization.LocalizationServiceCache;
import org.vpda.common.service.localization.MemLocalizationServiceCacheImpl;
import org.vpda.common.service.localization.MemURLsLocalizationValueResolverCacheImpl;
import org.vpda.common.service.localization.URLsLocalizationValueResolverCache;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * Test for {@link CommonCoreModuleEntryPointImpl}
 * 
 * @author kitko
 *
 */
public class CommonModuleEntryPointImplTest {

    /**
     * test entry point
     */
    @Test
    public void testEntryPoint() {
        AppEntryPoint entryPoint = new AppEntryPointImpl();
        AppConfiguration appConf = new AppConfigurationImpl();
        ModuleConfiguration commonConf = new ModuleConfigurationImpl(appConf);
        commonConf.getContainer().addComponent(LocalizationServiceCache.class, new MemLocalizationServiceCacheImpl());
        commonConf.getContainer().addComponent(URLsLocalizationValueResolverCache.class, new MemURLsLocalizationValueResolverCacheImpl());
        List<Class<? extends ModuleEntryPoint>> modules = new ArrayList<Class<? extends ModuleEntryPoint>>(1);
        modules.add(CommonCoreModuleEntryPoint.class);
        appConf.getRootContainer().addComponent(CommonCoreModuleEntryPoint.class, CommonCoreModuleEntryPointImpl.class);
        ModulesConfiguration modulesConf = new ModulesConfigurationtImpl(appConf, modules);
        modulesConf.registerModuleConfigurationFactory(CommonCoreModuleEntryPoint.class, new SimpleModuleConfigurationFactory(commonConf));
        appConf.getRootContainer().addComponent(ModulesConfiguration.class, modulesConf, (Parameter[]) null);
        ConcreteApplication application = new ConcreteApplication(CommonUtilConstants.VPDA_PROJECT_NAME, appConf);
        entryPoint.entryPoint(application);
    }

    /**
     * Test get module
     */
    @Test
    public void testGetModule() {
        CommonCoreModuleEntryPoint moduleEntryPoint = new CommonCoreModuleEntryPointImpl();
        assertNotNull(moduleEntryPoint.getModule());
    }

}
