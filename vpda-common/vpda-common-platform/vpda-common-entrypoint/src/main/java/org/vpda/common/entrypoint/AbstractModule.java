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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.vpda.common.ioc.picocontainer.ChainPicoContainer;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.internal.common.util.ProxyUtil;
import org.vpda.internal.common.util.StaticCache;

/**
 * Representation of Module
 * 
 * @author kitko
 *
 */
public abstract class AbstractModule implements MuttableModule, Serializable {
    private static final long serialVersionUID = 8952658855706680439L;

    private static final MutablePicoContainer emptyContainer = new DefaultPicoContainer();
    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AbstractModule.class);

    /** Name of module */
    protected String name;
    /** Url of module home */
    protected URL moduleHome;
    /** Module container */
    protected ChainPicoContainer container;
    /** List of modules this container depends on */
    protected List<Module> dependsOnModules;
    /** Root application */
    protected Application application;
    /** Completed flag */
    protected boolean isCompleted;
    /** Parent module */
    protected Module parent;
    /** Children */
    protected List<Module> submodules;
    /** Extensions */
    protected List<Module> extensions;

    /**
     * Constructor for module
     */
    protected AbstractModule() {
        dependsOnModules = new ArrayList<Module>(3);
        initName();
        initModuleHome();
    }

    private void initModuleHome() {
        Class<?> clazz = getClass();
        URL url = clazz.getResource('/' + clazz.getName().replace('.', '/') + ".class");
        if (url != null) {
            String removeString = clazz.getName().replace('.', '/') + ".class";
            String path = url.toExternalForm();
            path = path.substring(0, path.length() - removeString.length());
            try {
                logger.log(Level.INFO, "Module defined by class [{0}] will use following module home url path : {1}", new Object[] { getClass().getName(), path });
                moduleHome = new URL(path);
            }
            catch (MalformedURLException e) {
                throw new VPDARuntimeException("Cannot find module home", e);
            }
        }
    }

    @Override
    public ClassLoader getModuleClassloader() {
        return getClass().getClassLoader();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        return object instanceof AbstractModule && name.equals(((AbstractModule) object).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Initialize name
     */
    protected abstract void initName();

    @Override
    public URL getModuleHomeURL() {
        return moduleHome;
    }

    @Override
    public void setModuleContainer(PicoContainer appContainer, MutablePicoContainer moduleConfContainer) {
        if (moduleConfContainer instanceof ChainPicoContainer) {
            this.container = (ChainPicoContainer) moduleConfContainer;
        }
        else {
            this.container = new ChainPicoContainer();
            for (ComponentAdapter adapter : moduleConfContainer.getComponentAdapters()) {
                this.container.addAdapter(adapter);
            }
            if (moduleConfContainer.getParent() != null) {
                this.container.addDependendsOnContainer(moduleConfContainer.getParent());
            }
        }
        for (Module dependsOn : dependsOnModules) {
            this.container.addDependendsOnContainer(dependsOn.getContainer());
        }
        if (this.parent != null) {
            this.container.addDependendsOnContainer(parent.getContainer());
        }
        this.container.addDependendsOnContainer(appContainer);
    }

    @Override
    public void addDependsOnModule(Module module) {
        if (module.equals(this)) {
            return;
        }
        if (!dependsOnModules.contains(module)) {
            dependsOnModules.add(module);
            if (this.container != null) {
                this.container.addDependendsOnContainer(module.getContainer());
            }
        }
        if (!module.getSubModules().isEmpty()) {
            for (Module subModule : module.getSubModules()) {
                addDependsOnModule(subModule);
            }
        }
    }

    @Override
    public List<Module> getDependsOnModules() {
        return Collections.unmodifiableList(dependsOnModules);
    }

    @Override
    public MutablePicoContainer getContainer() {
        if (container == null) {
            return emptyContainer;
        }
        return container;
    }

    @Override
    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public boolean isModuleCompleted() {
        return isCompleted;
    }

    @Override
    public void setCompleted() {
        this.isCompleted = true;
    }

    @Override
    public Module getParent() {
        return parent;
    }

    @Override
    public List<Module> getSubModules() {
        return submodules != null ? Collections.unmodifiableList(submodules) : Collections.<Module>emptyList();
    }

    @Override
    public List<Module> getExtensions() {
        return extensions != null ? Collections.unmodifiableList(extensions) : Collections.<Module>emptyList();
    }

    @Override
    public void setParent(Module parent) {
        this.parent = parent;
        if (this.parent != null && this.container != null) {
            this.container.addDependendsOnContainer(parent.getContainer());
        }
    }

    @Override
    public void addSubmodule(Module module) {
        if (submodules == null) {
            submodules = new ArrayList<>(2);
        }
        submodules.add(module);
    }

    @Override
    public void addExtension(Module module) {
        if (extensions == null) {
            extensions = new ArrayList<>(2);
        }
        extensions.add(module);
    }

    /**
     * @param clazz
     * @return Module by class
     */
    public static Module getModule(Class<? extends Module> clazz) {
        Module module = StaticCache.getOrPutNewByReflection(clazz);
        if (!module.isModuleCompleted()) {
            return module;
        }
        return ProxyUtil.createDelegatingProxy(Thread.currentThread().getContextClassLoader(), Module.class, module);
    }

    @Override
    public boolean isSubModule() {
        return false;
    }

    @Override
    public boolean isExtension() {
        return false;
    }

    @Override
    public boolean isInterfaceModule() {
        return BasicModuleKind.IFC.equals(getKind());
    }

    @Override
    public java.lang.Module getJavaModule() {
        return getClass().getModule();
    }

}
