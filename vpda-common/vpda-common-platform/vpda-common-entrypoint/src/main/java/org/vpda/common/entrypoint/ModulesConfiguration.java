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

import java.util.List;

/**
 * Global configuration for modules
 * 
 * @author kitko
 *
 */
public interface ModulesConfiguration {
    /**
     * 
     * @return all module entry points
     */
    public List<Class<? extends ModuleEntryPoint>> getModuleEntryPoints();

    /**
     * Returns configuration for module
     * 
     * @param clazz
     * @param module
     * @return Configuration for one module
     */
    public ModuleConfiguration createConfigurationForModule(Class<? extends ModuleEntryPoint> clazz, Module module);

    /**
     * Register configuration for module
     * 
     * @param clazz
     * @param moduleConf
     */
    public void registerModuleConfigurationFactory(Class<? extends ModuleEntryPoint> clazz, ModuleConfigurationFactory moduleConf);

    /**
     * Creates module entrypoint
     * 
     * @param <T>
     * @param clazz
     * @return ModuleEntryPoint
     */
    public <T extends ModuleEntryPoint> T createModuleEntryPoint(Class<T> clazz);

    /**
     * Return list of implementation interfaces for interface module entry point.
     * E.g one interface module may be later implemented by 2 implementation modules
     * and those modules entrypoints interfaces are returned
     * 
     * @param <T>
     * @param <Z>
     * @param clazz
     * @return list of entry point interfaces
     */
    public List<Class<? extends ModuleEntryPoint>> getMappingsForInterfaceModule(Class<? extends ModuleEntryPoint> clazz);

    public default List<Class<? extends ModuleEntryPoint>> filterMappingsForInterfaceModuleWhenRunAsRootModule(Class<? extends ModuleEntryPoint> clazz, List<Class<? extends ModuleEntryPoint>> list) {
        return list;
    }

    public default List<Class<? extends ModuleEntryPoint>> getFilteredMappingsForInterfaceModuleWhenRunningAsRootModule(Class<? extends ModuleEntryPoint> clazz) {
        return filterMappingsForInterfaceModuleWhenRunAsRootModule(clazz, getMappingsForInterfaceModule(clazz));
    }
}
