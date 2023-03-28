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
 * Listener for events when entering or exiting application or module entry
 * point methods
 */
public interface EntryPointListener {
    /**
     * Called at starting of {@link AppEntryPoint#entryPoint(Application)}
     * 
     * @param entryPoint
     * @param app
     */
    public void applicationEntryStart(AppEntryPoint entryPoint, Application app);

    /**
     * Called at end of {@link AppEntryPoint#entryPoint(Application)}
     * 
     * @param entryPoint
     * @param app
     */
    public void applicationEntryExit(AppEntryPoint entryPoint, Application app);

    /**
     * Called at starting of
     * {@link ModuleEntryPoint#entryPoint(AppEntryPoint, Module)}
     * 
     * @param entryPoint
     * @param moduleEntryClass
     * @param moduleEntryPoint
     * @param module
     */
    public void moduleEntryStart(AppEntryPoint entryPoint, Class<? extends ModuleEntryPoint> moduleEntryClass, ModuleEntryPoint moduleEntryPoint, Module module);

    /**
     * Called at end of {@link ModuleEntryPoint#entryPoint(AppEntryPoint, Module)}
     * 
     * @param entryPoint
     * @param moduleEntryClass
     * @param moduleEntryPoint
     * @param module
     */
    public void moduleEntryExit(AppEntryPoint entryPoint, Class<? extends ModuleEntryPoint> moduleEntryClass, ModuleEntryPoint moduleEntryPoint, Module module);

}
