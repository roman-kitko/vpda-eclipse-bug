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

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.picocontainer.containers.ImmutablePicoContainer;
import org.vpda.common.ioc.picocontainer.ChainPicoContainer;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ProxyUtil;
import org.vpda.internal.common.util.StaticCache;

/**
 * Concrete application with modules
 * 
 * @author kitko
 *
 */
public final class ConcreteApplication implements MuttableApplication, Serializable {
    private static final long serialVersionUID = 8830099761322942393L;
    private final String name;
    private final Map<String, Module> modules;
    private final URL projectHome;
    private final ChainPicoContainer allModulesContainer;
    private final MutablePicoContainer rootContainer;
    private final PicoContainer publicAllModulesContainer;
    private final PicoContainer publicRootContainer;
    private int modulesHashCode;

    /**
     * Creates project
     * 
     * @param name
     * @param appConf
     *
     */
    public ConcreteApplication(String name, AppConfiguration appConf) {
        this.name = Assert.isNotNull(name, "Name argument is null");
        this.allModulesContainer = (ChainPicoContainer) appConf.getAllModulesContainer();
        this.rootContainer = appConf.getRootContainer();
        this.rootContainer.addComponent(Application.class, ProxyUtil.createDelegatingProxy(Thread.currentThread().getContextClassLoader(), Application.class, this));
        this.publicAllModulesContainer = new ImmutablePicoContainer(this.allModulesContainer);
        this.publicRootContainer = new ImmutablePicoContainer(rootContainer);
        modules = new HashMap<String, Module>();
        StaticCache.put(Application.class, this);
        projectHome = initProjectHome();
    }

    @Override
    public List<Module> getModules() {
        return Collections.unmodifiableList(new ArrayList<>(modules.values()));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Module getModule(String name) {
        return modules.get(name);
    }

    @Override
    public void registerModule(Module module) {
        modules.put(module.getName(), module);
        allModulesContainer.addDependendsOnContainer(module.getContainer());
        this.modulesHashCode = Arrays.hashCode(modules.values().toArray());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Application) {
            return name.equals(((Application) object).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public URL getApplicationHomeURL() {
        return projectHome;
    }

    private URL initProjectHome() {
        Class clazz = getClass();
        URL url = clazz.getResource(clazz.getSimpleName() + ".class");
        URL projectHome = null;
        if (url != null) {
            String removeString = clazz.getName().replace('.', '/') + ".class";
            String path = url.toExternalForm();
            path = path.substring(0, path.length() - removeString.length());

            File filePath = new File(path.startsWith("file:") ? path.substring("file:".length()) : path);
            if (filePath.isDirectory()) {
                filePath = filePath.getParentFile().getParentFile().getParentFile().getParentFile();
                path = "file:" + filePath.getAbsolutePath();
            }
            try {
                projectHome = new URL(path);
            }
            catch (MalformedURLException e) {
                throw new VPDARuntimeException("Cannot find module home", e);
            }
        }
        return projectHome;
    }

    /**
     * Will return instance of Project
     * 
     * @return project instance
     */
    public static Application getInstance() {
        Application application = getApplication();
        if (application == null) {
            throw new IllegalStateException("Application not yet created");
        }
        return application;
    }

    private static Application getApplication() {
        return StaticCache.get(Application.class);
    }

    /**
     * Test whether project is already created
     * 
     * @return true if project is created
     */
    public static boolean isApplicationCreated() {
        return getApplication() != null;
    }

    @Override
    public PicoContainer getRootContainer() {
        return publicRootContainer;
    }

    @Override
    public PicoContainer getAllModulesContainer() {
        return publicAllModulesContainer;
    }

    @Override
    public int getModulesHashCode() {
        return modulesHashCode;
    }

}
