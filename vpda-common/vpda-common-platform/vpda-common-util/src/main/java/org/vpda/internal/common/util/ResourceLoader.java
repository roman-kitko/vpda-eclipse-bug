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
package org.vpda.internal.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;

/**
 * Loads resource from classpath. System property value
 * <code>resources.prefix</code> is used as prefix
 */
public final class ResourceLoader {

    private static final LoggerMethodTracer LOGGER = LoggerMethodTracer.getLogger(ResourceLoader.class);

    private ResourceLoader() {
    }

    /** Name of prefix system property */
    public static final String PREFIX_PROPERTY_NAME = "vpda.resources.prefix";

    /**
     * Gets resource url using classname as path
     * 
     * @param clazz
     * @return resource url or null if no resource found
     */
    public static URL getURLOfResourceWithNameOfClass(Class<?> clazz) {
        return getResourceURL(clazz.getName());
    }

    /**
     * Get class bytes
     * 
     * @param clazz
     * @return class bytes
     */
    public static byte[] getClassBytes(Class<?> clazz) {
        URL url = getResourceURL(clazz.getName().replace('.', '/') + ".class");
        if (url != null) {
            try(InputStream stream = url.openStream()){
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                stream.transferTo(bos);
                byte[] bytes = bos.toByteArray();
                return bytes;
            }
            catch (IOException e) {
                throw new VPDARuntimeException("Error reading class bytes");
            }
        }
        return null;
    }

    /**
     * Gets resource url using classname as path
     * 
     * @param path to resource
     * @return resource url or null if no resource found
     */
    public static URL getResourceURL(String path) {
        URL url = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ResourceLoader.class.getClassLoader();
        }
        String prefix = System.getProperty(PREFIX_PROPERTY_NAME);
        if (prefix != null) {
            url = loader.getResource(prefix + '/' + path);
            if (url != null) {
                return url;
            }
        }
        url = loader.getResource(path);
        return url;
    }

    /**
     * Gets property bag using the class name as path
     * 
     * @param clazz Name of resource
     * @return the Property bag
     * @throws IOException
     * 
     */
    public static PropertyBag getPropertyBag(Class<?> clazz) throws IOException {
        return getPropertyBag(clazz.getName());
    }

    /**
     * Gets property bag using the class name as path
     * 
     * @param path
     * @return the Property bag
     * @throws IOException
     * 
     */
    public static PropertyBag getPropertyBag(String path) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ResourceLoader.class.getClassLoader();
        }
        Properties specificProperties = null;
        String prefix = System.getProperty(PREFIX_PROPERTY_NAME);
        URL specificURL = null;
        if (prefix != null) {
            specificURL = loader.getResource(prefix + '/' + path);
            specificProperties = specificURL != null ? PropertiesUtil.readPropertiesFromURL(specificURL) : null;
        }
        URL url = loader.getResource(path);
        if (url == null && specificURL == null) {
            return PropertyBag.emptyBag();
        }
        Properties defaultProperties = url != null ? PropertiesUtil.readPropertiesFromURL(url) : null;
        Properties p = new Properties();
        if (defaultProperties != null) {
            p.putAll(defaultProperties);
        }
        if (specificProperties != null) {
            p.putAll(specificProperties);
        }
        if (p.isEmpty()) {
            return PropertyBag.emptyBag();
        }
        p = PropertiesResolver.resolveProperties(p, System.getProperties());
        Map<String, Object> map = new HashMap<String, Object>(p.size());
        for (Map.Entry<Object, Object> entry : p.entrySet()) {
            map.put((String) entry.getKey(), entry.getValue());
        }
        return new PropertyBag(map);

    }

    public static Optional<URL> resolveResourceFromLoaderAndModules(String path, Class<?> clazz) {
        URL url = clazz.getResource(path);
        if (url != null) {
            return Optional.of(url);
        }
        return resolveResourceFromLoaderAndModules(path, clazz.getClassLoader(), clazz);
    }

    public static Optional<URL> resolveResourceFromLoaderAndModules(String path, ClassLoader loader, Class<?> defaultModuleClass) {
        URL url = null;
        url = loader.getResource(path);
        if (url != null) {
            return Optional.of(url);
        }
        Module javaDefaultModule = defaultModuleClass.getModule();
        String javaDefaultModuleName = javaDefaultModule.getName();
        if (!javaDefaultModule.isNamed()) {
            // Here this should be true only for tests
            URL defaultClassUrl = defaultModuleClass.getResource('/' + defaultModuleClass.getName().replace('.', '/') + ".class");
            if (defaultClassUrl != null) {
                String removeString = defaultModuleClass.getName().replace('.', '/') + ".class";
                String moduleInfoPath = defaultClassUrl.toExternalForm();
                moduleInfoPath = moduleInfoPath.substring(0, moduleInfoPath.length() - removeString.length()) + "module-info.class";
                try {
                    URL moduleInfoURL = new URL(moduleInfoPath);
                    try (InputStream is = moduleInfoURL.openStream()) {
                        ModuleDescriptor descriptor = ModuleDescriptor.read(is);
                        javaDefaultModuleName = descriptor.name();
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }

            }
        }
        String qualifiedPath = javaDefaultModuleName.replace('.', '/') + "/" + path;
        if (javaDefaultModule.getClassLoader() != null) {
            url = javaDefaultModule.getClassLoader().getResource(path);
            if (url != null) {
                return Optional.of(url);
            }
            url = javaDefaultModule.getClassLoader().getResource(qualifiedPath);
            if (url != null) {
                return Optional.of(url);
            }
        }
        if (javaDefaultModule.getLayer() == null) {
            return Optional.empty();
        }
        Set<Module> modules = javaDefaultModule.getLayer().modules();
        LOGGER.log(Level.INFO, "Searching for path {0} in following modules {1}", new Object[] { path, modules });
        for (Module module : modules) {
            if (module.getName().startsWith("java.") || module.getName().startsWith("jdk.") || module.getClassLoader() == null) {
                continue;
            }
            qualifiedPath = module.getName().replace('.', '/') + "/" + path;
            url = module.getClassLoader().getResource(qualifiedPath);
            if (url != null) {
                return Optional.of(url);
            }
        }
        return Optional.empty();
    }

    public static Collection<URL> getResourcesFromLoaderAndModules(String path, ClassLoader loader, Class<?> defaultModuleClass) {
        Collection<URL> urlList = new LinkedHashSet<>();
        URL url = loader.getResource(path);
        if (url != null) {
            urlList.add(url);
        }
        Module javaDefaultModule = defaultModuleClass.getModule();
        String javaDefaultModuleName = javaDefaultModule.getName();
        if (!javaDefaultModule.isNamed()) {
            // Here this should be true only for tests
            URL defaultClassUrl = defaultModuleClass.getResource('/' + defaultModuleClass.getName().replace('.', '/') + ".class");
            if (defaultClassUrl != null) {
                String removeString = defaultModuleClass.getName().replace('.', '/') + ".class";
                String moduleInfoPath = defaultClassUrl.toExternalForm();
                moduleInfoPath = moduleInfoPath.substring(0, moduleInfoPath.length() - removeString.length()) + "module-info.class";
                try {
                    URL moduleInfoURL = new URL(moduleInfoPath);
                    try (InputStream is = moduleInfoURL.openStream()) {
                        ModuleDescriptor descriptor = ModuleDescriptor.read(is);
                        javaDefaultModuleName = descriptor.name();
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                    return urlList;
                }

            }
        }
        String qualifiedPath = javaDefaultModuleName.replace('.', '/') + "/" + path;
        // /vpda-common-core/src/main/resources/org/vpda/common/core/ehcache/config/vpda-ehcache-common.xml
        if (javaDefaultModule.getClassLoader() != null) {
            url = javaDefaultModule.getClassLoader().getResource(path);
            if (url != null) {
                urlList.add(url);
            }
            url = javaDefaultModule.getClassLoader().getResource(qualifiedPath);
            if (url != null) {
                urlList.add(url);
            }
        }
        if (javaDefaultModule.getLayer() == null) {
            return urlList;
        }
        Set<Module> modules = javaDefaultModule.getLayer().modules();
        for (Module module : modules) {
            if (!module.isNamed() || module.getName().startsWith("java.") || module.getName().startsWith("jdk.") || module.getClassLoader() == null) {
                continue;
            }
            qualifiedPath = module.getName().replace('.', '/') + "/" + path;
            url = module.getClassLoader().getResource(qualifiedPath);
            if (url != null) {
                urlList.add(url);
            }
        }
        return urlList;
    }

}
