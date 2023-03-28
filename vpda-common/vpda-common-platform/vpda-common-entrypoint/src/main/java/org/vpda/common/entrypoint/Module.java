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

import org.picocontainer.MutablePicoContainer;

/**
 * This is one module - part of the project
 * 
 * @author kitko
 *
 */
public interface Module {
    /**
     * @return Returns the name.
     */
    public abstract String getName();

    /**
     * @return the moduleHome
     */
    public abstract URL getModuleHomeURL();

    /**
     * 
     * @return list of modules this module depends on
     */
    public List<Module> getDependsOnModules();

    /**
     * 
     * @return container for this module
     */
    public MutablePicoContainer getContainer();

    /**
     * 
     * @return true if module with its dependencies is completed
     */
    public boolean isModuleCompleted();

    /**
     * @return application
     */
    public Application getApplication();

    /**
     * @return module classloader
     */
    public ClassLoader getModuleClassloader();

    /**
     * Gets parent of the module
     * 
     * @return parent of module if module is child module , otherwise returns null
     */
    public Module getParent();

    /**
     * Gets child modules
     * 
     * @return children
     */
    public List<Module> getSubModules();

    /**
     * @return all extensions
     */
    public List<Module> getExtensions();

    /**
     * 
     * @return true if this module is submodule
     */
    public boolean isSubModule();

    /**
     * @return true if this module is just the extension of another module
     */
    public boolean isExtension();

    public ModuleKind getKind();

    public ModuleNamespace getModuleNamespace();

    public boolean isInterfaceModule();

    public java.lang.Module getJavaModule();
}
