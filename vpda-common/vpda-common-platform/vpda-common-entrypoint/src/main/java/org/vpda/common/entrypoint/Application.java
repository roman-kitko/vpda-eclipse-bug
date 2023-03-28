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

import java.net.URL;
import java.util.List;

import org.picocontainer.PicoContainer;

/**
 * This is the running application - container for all modules. Only one
 * instance can exist per JVM
 * 
 * @author kitko
 *
 */
public interface Application {

    /**
     * @return Returns the modules.
     */
    public abstract List<Module> getModules();

    /**
     * @return current registered modules hashcode
     */
    public int getModulesHashCode();

    /**
     * @return Returns the name.
     */
    public abstract String getName();

    /**
     * @param name
     * @return Module of this name
     */
    public abstract Module getModule(String name);

    /**
     * 
     * @return project url home
     */
    public URL getApplicationHomeURL();

    /**
     * 
     * @return container for all modules
     */
    public PicoContainer getAllModulesContainer();

    /**
     * @return root container
     */
    public PicoContainer getRootContainer();
}
