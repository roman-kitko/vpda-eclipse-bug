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

/**
 * Main entry point to application framework
 * 
 * @author kitko
 *
 */
public interface AppEntryPoint {

    /**
     * Main entry point method
     * 
     * @param application
     */
    public void entryPoint(Application application);

    /**
     * 
     * @return application
     */
    public Application getApplication();

    /**
     * @return AppEntryPointListenerSupport for the entry point events
     */
    public AppEntryPointListenerSupport getListenerSupport();

    /**
     * Will run one module
     * 
     * @param clazz
     * @param moduleEntry
     */
    public void runModule(Class<? extends ModuleEntryPoint> clazz, ModuleEntryPoint moduleEntry);

    /** Just creates new module entry point by interface name */
    public <T extends ModuleEntryPoint> T createChildModuleEntryPoint(Class<T> clazz, Module parent);
}
