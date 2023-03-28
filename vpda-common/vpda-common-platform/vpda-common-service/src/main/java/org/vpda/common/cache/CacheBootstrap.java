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

import java.util.IdentityHashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;

/**
 * Bootstrap of caching using jcache api
 * 
 * @author kitko
 *
 */
public final class CacheBootstrap {
    private static Map<ClassLoader, CacheBootstrapConfig> configs = new IdentityHashMap<>();

    private CacheBootstrap() {
    }

    private synchronized static CacheBootstrapConfig getOrCreateConfig() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        CacheBootstrapConfig config = configs.get(loader);
        if (config != null) {
            return config;
        }
        config = new CacheBootstrapConfig(loader);
        config.configure();
        configs.put(loader, config);
        return config;
    }

    /**
     * Will get/create cache manager for logical name
     * 
     * @param name
     * @return CacheManager
     */
    public static CacheManager getCaheManager(String name) {
        CacheBootstrapConfig cfg = getOrCreateConfig();
        return cfg.getCaheManager(name);
    }

    /**
     * Get or create cache for manager with some default configuration of that cache
     * 
     * @param managerName
     * @param cacheName
     * @param keyType
     * @param valueType
     * @return Cache
     */
    public static <K, V> Cache<K, V> getOrCreateDefaultCfgCacheForManager(String managerName, String cacheName, Class<K> keyType, Class<V> valueType) {
        CacheManager manager = getCaheManager(managerName);
        Cache<K, V> cache = manager.getCache(cacheName, keyType, valueType);
        if (cache != null) {
            return cache;
        }
        MutableConfiguration<K, V> config = new MutableConfiguration<K, V>();
        config.setTypes(keyType, valueType).setStoreByValue(false);
        cache = manager.createCache(cacheName, config);
        return cache;
    }

}
