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
package org.vpda.common.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Level;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.management.MBeanServer;

import org.apache.commons.io.IOUtils;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.internal.common.util.PropertiesUtil;
import org.vpda.internal.common.util.ResourceLoader;

final class CacheBootstrapConfig {
    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(CacheBootstrapConfig.class);
    private Map<String, URI> managersURI = new HashMap<>();
    private static final String DEFAULT_CFG_URL = "vpda/ehcache/config/vpda-ehcache-common.xml";
    private URI defaultUri;
    private CachingProvider cachingProvider;
    private Set<Integer> registeredManagerHashCode = new HashSet<>();
    private ClassLoader loader;
    private Map<URI, CacheManager> createManagers = new WeakHashMap<>();

    CacheBootstrapConfig(ClassLoader loader) {
        this.loader = loader;
    }

    void configure() {
        logger.info("Will configure cacheManager mappings");
        try {
            Optional<URL> resolveResourceFromLoaderAndModules = ResourceLoader.resolveResourceFromLoaderAndModules(DEFAULT_CFG_URL, loader, this.getClass());
            if (resolveResourceFromLoaderAndModules.isEmpty()) {
                throw new VPDARuntimeException("Cannot locate default cache cfg at path : " + DEFAULT_CFG_URL);
            }
            URL defUrl = resolveResourceFromLoaderAndModules.get();
            cachingProvider = Caching.getCachingProvider(loader);
            logger.info("CachingProvider loaded by classLoder : " + cachingProvider.getClass().getClassLoader());
            logger.info("Will use caching provider : " + cachingProvider.getClass().getName());
            defaultUri = defUrl.toURI();
            Collection<URL> resources = ResourceLoader.getResourcesFromLoaderAndModules("vpda/ehcache/config/vpda-ehcache-mapping.properties", loader, this.getClass());
            Map<String, URI> contentMap = new HashMap<>();
            for (URL url : resources) {
                logger.info("Will process cache manager mapping using properties : " + url);
                Properties properties = PropertiesUtil.readPropertiesFromURL(url);
                for (String cacheManagerName : properties.stringPropertyNames()) {
                    if (managersURI.containsKey(cacheManagerName)) {
                        continue;
                    }
                    String path = properties.getProperty(cacheManagerName);
                    Optional<URL> cfgURL = ResourceLoader.resolveResourceFromLoaderAndModules(path, loader, this.getClass());
                    if (cfgURL.isPresent()) {
                        logger.log(Level.INFO, "Cache manager [{0}] mapped to config url : [{1}]", new Object[] { cacheManagerName, cfgURL });
                        String configString = IOUtils.toString(cfgURL.get());
                        configString = configString.replace("${ClassLoader.id}", loader.getClass().getSimpleName() + "-" + System.identityHashCode(loader));
                        if (contentMap.containsKey(configString)) {
                            managersURI.put(cacheManagerName, contentMap.get(configString));
                        }
                        else {
                            File tmpFile = File.createTempFile(cfgURL.get().getFile(), "xml");
                            tmpFile.deleteOnExit();
                            FileOutputStream fos = new FileOutputStream(tmpFile);
                            IOUtils.write(configString, fos);
                            fos.close();
                            managersURI.put(cacheManagerName, tmpFile.toURI());
                            contentMap.put(configString, tmpFile.toURI());
                        }
                    }
                    else {
                        logger.warning("Cannot map cache manager to cfg file : " + path + " , path not found on classpath");
                    }
                }
            }
        }
        catch (IOException | URISyntaxException e) {
            throw new VPDARuntimeException("Error configuring caching", e);
        }
    }

    synchronized CacheManager getCaheManager(String name) {
        URI uri = managersURI.get(name);
        if (uri == null) {
            uri = defaultUri;
        }
        CacheManager manager = createManagers.get(uri);
        if (manager != null) {
            return manager;
        }
        manager = cachingProvider.getCacheManager(uri, loader);
        createManagers.put(uri, manager);
        registerAsMBean(manager);
        return manager;
    }

    void registerAsMBean(CacheManager manager) {
        if (!registeredManagerHashCode.contains(System.identityHashCode(manager))) {
            MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            org.ehcache.CacheManager cacheManager = manager.unwrap(org.ehcache.CacheManager.class);
            // ManagementService.registerMBeans(cacheManager, platformMBeanServer, true,
            // true, true, true, true);
            registeredManagerHashCode.add(System.identityHashCode(manager));
        }

    }
}
