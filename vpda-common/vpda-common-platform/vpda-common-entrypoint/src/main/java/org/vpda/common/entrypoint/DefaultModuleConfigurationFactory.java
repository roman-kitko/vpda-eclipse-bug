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

import java.net.URL;

import org.picocontainer.PicoContainer;
import org.vpda.internal.common.ChainedClassLoader;

/**
 * ModuleConfigurationFactory that returns configured ModuleConfiguration.
 * 
 * @author kitko
 *
 */
public final class DefaultModuleConfigurationFactory implements ModuleConfigurationFactory {
    @Override
    public ModuleConfiguration createModuleConfiguration(AppConfiguration appConf, Class<? extends ModuleEntryPoint> clazz, Module module) {
        PicoContainer container = null;
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader moduleLoader = module.getModuleClassloader();
        ClassLoader effectiveLoader = moduleLoader;
        if (contextLoader != null && contextLoader != moduleLoader) {
            effectiveLoader = new ChainedClassLoader(contextLoader, moduleLoader);
        }
        String path = (String) appConf.getRootContainer().getComponent(module.getName() + "-conf.xml");
        if (path != null) {
            container = AppLauncherHelper.loadXMLPicoContainer(path, effectiveLoader, null);
        }
        else {
            String defPath = "META-INF/" + module.getName() + "-conf.xml";
            URL defURL = effectiveLoader.getResource(defPath);
            if (defURL == null && module.getClass().getPackage() != null) {
                defPath = module.getClass().getPackage().getName().replace('.', '/') + "/" + module.getName() + "-conf.xml";
            }
            defURL = effectiveLoader.getResource(defPath);
            if (defURL != null) {
                container = AppLauncherHelper.loadXMLPicoContainer("resource:" + defPath, effectiveLoader, null);
            }
        }
        ModuleConfiguration conf = new ModuleConfigurationImpl(appConf, container);
        return conf;
    }
}
