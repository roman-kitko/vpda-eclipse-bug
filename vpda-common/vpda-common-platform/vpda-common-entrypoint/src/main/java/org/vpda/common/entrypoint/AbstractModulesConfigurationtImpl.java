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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.picocontainer.ComponentAdapter;

/**
 * Container of modules configurations
 * 
 * @author kitko
 *
 */
public abstract class AbstractModulesConfigurationtImpl implements ModulesConfiguration, Serializable {
    private static final long serialVersionUID = -4555480190234232640L;
    private final List<Class<? extends ModuleEntryPoint>> modules;
    private final AppConfiguration appConf;
    private final Map<Class<? extends ModuleEntryPoint>, ModuleConfigurationFactory> modulesConf;

    @Override
    public List<Class<? extends ModuleEntryPoint>> getModuleEntryPoints() {
        return Collections.unmodifiableList(modules);
    }

    /**
     * Creates ModulesConfigurationtImpl
     * 
     * @param appConf
     * @param modules
     */
    public AbstractModulesConfigurationtImpl(AppConfiguration appConf, List<Class<? extends ModuleEntryPoint>> modules) {
        this.modules = new ArrayList<Class<? extends ModuleEntryPoint>>(modules);
        this.appConf = appConf;
        modulesConf = new HashMap<Class<? extends ModuleEntryPoint>, ModuleConfigurationFactory>(2);
    }

    @Override
    public ModuleConfiguration createConfigurationForModule(Class<? extends ModuleEntryPoint> clazz, Module module) {
        ModuleConfigurationFactory mcf = modulesConf.get(clazz);
        if (mcf == null) {
            mcf = new DefaultModuleConfigurationFactory();
        }
        ModuleConfiguration conf = mcf.createModuleConfiguration(appConf, clazz, module);
        return conf;
    }

    @Override
    public void registerModuleConfigurationFactory(Class<? extends ModuleEntryPoint> clazz, ModuleConfigurationFactory mcf) {
        modulesConf.put(clazz, mcf);
    }

    @Override
    public <T extends ModuleEntryPoint> T createModuleEntryPoint(Class<T> clazz) {
        ComponentAdapter<?> componentAdapter = appConf.getRootContainer().getComponentAdapter(clazz);
        if (componentAdapter == null) {
            return null;
        }
        return clazz.cast(componentAdapter.getComponentInstance(appConf.getRootContainer(), ComponentAdapter.NOTHING.class));
    }

}
