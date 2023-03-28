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
 * Module entry point is entry point to one module. Its task is to create
 * objects this module will need during runtime
 * 
 * @author kitko
 *
 */
public interface ModuleEntryPoint {
    /**
     * Gets module for entryPoint
     * 
     * @return module for entrypoint
     */
    public Module getModule();

    /**
     * Entry point method - start method of module
     * 
     * @param appEntryPoint
     * @param module
     */
    public void entryPoint(AppEntryPoint appEntryPoint, Module module);

    /**
     * 
     * @param appEntryPoint * @return list of all ModuleEntryPoint classes this
     *                      module requires
     */
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint);

    /**
     * Gets default impl for moduleEntrypoint
     * 
     * @param <T>
     * @param moduleIface
     * @return impl class for another module entry point
     */
    public abstract <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface);

    public List<Class<? extends ModuleEntryPoint>> filterMappingsForInterfaceModuleWhenRunAsRequiredModule(Class<? extends ModuleEntryPoint> clazz, List<Class<? extends ModuleEntryPoint>> list);
}
