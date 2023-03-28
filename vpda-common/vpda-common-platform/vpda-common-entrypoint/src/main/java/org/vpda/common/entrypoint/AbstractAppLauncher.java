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
/**
 * 
 */
package org.vpda.common.entrypoint;

import java.util.Collections;
import java.util.List;

import org.picocontainer.PicoContainer;
import org.vpda.common.ioc.picocontainer.PicoHelper;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * Abstract application launcher
 * 
 * @author kitko
 *
 */
public abstract class AbstractAppLauncher implements AppLauncher {

    /** Constant for SPI or system property for application xml picocontainer */
    public static final String VPDA_CONF_XML = "vpda.conf.xml";
    /** Constant for SPI or system property of builder for application xml */
    public static final String VPDA_CONF_BUILDER = "vpda.conf.builder";

    @Override
    public AppEntryPoint createAndlaunchEntryPoint(AppConfiguration appConf, List<EntryPointListener> listeners) throws Exception {
        Application application = createApplication(appConf);
        AppEntryPoint appEntryPoint = new AppEntryPointImpl();
        for (EntryPointListener listener : listeners) {
            appEntryPoint.getListenerSupport().registerEntryPointListener(listener);
        }
        appEntryPoint.entryPoint(application);
        return appEntryPoint;
    }

    @Override
    public AppEntryPoint createAndlaunchEntryPoint(AppConfiguration appConf) throws Exception {
        return createAndlaunchEntryPoint(appConf, Collections.<EntryPointListener>emptyList());
    }

    @Override
    public Application createApplication(AppConfiguration appConf) throws Exception {
        ModulesConfiguration modulesConfiguration = appConf.getRootContainer().getComponent(ModulesConfiguration.class);
        if (modulesConfiguration == null) {
            ModulesConfigurationFactory modulesConfigurationFactory = PicoHelper.getMandatoryComponent(appConf.getRootContainer(), ModulesConfigurationFactory.class);
            modulesConfiguration = modulesConfigurationFactory.createModulesConfiguration(appConf);
            appConf.getRootContainer().addComponent(ModulesConfiguration.class, modulesConfiguration);
        }
        Application application = appConf.getRootContainer().getComponent(Application.class);
        if (application == null) {
            // application is added to container in constructor
            application = new ConcreteApplication(CommonUtilConstants.VPDA_PROJECT_NAME, appConf);
        }
        return application;
    }

    @Override
    public void launch() throws Exception {
        PicoContainer container = AppLauncherHelper.loadApplicationContainer(VPDA_CONF_BUILDER, VPDA_CONF_XML, VPDA_CONF_BUILDER, VPDA_CONF_XML, Thread.currentThread().getContextClassLoader(), null);
        AppConfiguration appConf = new AppConfigurationImpl(container);
        appConf.getRootContainer().addComponent(getClass(), this);
        this.createAndlaunchEntryPoint(appConf);
    }

    static AppEntryPoint defaultLaunch(AppConfiguration appConf, List<EntryPointListener> listeners) throws Exception {
        AbstractAppLauncher launcher = new AbstractAppLauncher() {
        };
        return launcher.createAndlaunchEntryPoint(appConf, listeners);
    }

}
