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
/**
 * 
 */
package org.vpda.internal.common.util;

import java.io.Serializable;
import java.util.Map;

/**
 * Static access to global cache by key and current thread. This way we can
 * access some static data but we can set some thread discriminator that switch
 * concrete cache. This is useful for sessions
 * 
 * @author kitko
 *
 */
public final class StaticCache {
    private static final StaticCacheImpl IMPL = new StaticCacheImpl();

    /** Cache associated with key */
    public static final class CurrentCache implements Serializable {
        private static final long serialVersionUID = -6927611242422879572L;
        private final Object key;
        private final Map<Object, Object> cache;

        /**
         * @return the key
         */
        public Object getKey() {
            return key;
        }

        /**
         * @return the cache
         */
        public Map<Object, Object> getCache() {
            return cache;
        }

        /**
         * 
         * @param key
         * @param cache
         */
        public CurrentCache(Object key, Map<Object, Object> cache) {
            this.key = key;
            this.cache = cache;
        }

    }

    /**
     * @return global key
     */
    public static Object getGlobalKey() {
        return IMPL.globalKey;
    }

    /**
     * @return global key creator
     */
    public static CacheKeyCreator getGlobalKeyCreator() {
        return IMPL.globalKeyCreator;
    }

    /**
     * Gets value by key of type clazz
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @return value
     */
    public static <T> T get(Class<T> clazz, Object key) {
        return IMPL.get(clazz, key);
    }

    /**
     * gets value by class as key
     * 
     * @param <T>
     * @param clazz
     * @return value
     */
    public synchronized static <T> T get(Class<T> clazz) {
        return IMPL.get(clazz);
    }

    /**
     * Puts value to current thread cache by key
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @param value
     * @return old value
     */
    public synchronized static <T> T put(Class<T> clazz, Object key, T value) {
        return IMPL.put(clazz, key, value);
    }

    /**
     * Puts value to current thread cache by clazz
     * 
     * @param <T>
     * @param clazz
     * @param value
     * @return old value
     */
    public synchronized static <T> T put(Class<T> clazz, T value) {
        return IMPL.put(clazz, value);
    }

    /**
     * Sets current thread CacheKeyCreator
     * 
     * @param creator
     * @return old current thread creator
     */
    public synchronized static CacheKeyCreator setKeyCreator(CacheKeyCreator creator) {
        return IMPL.setKeyCreator(creator);
    }

    /**
     * @return current CacheKeyCreator
     */
    public synchronized static CacheKeyCreator getKeyCreator() {
        return IMPL.getKeyCreator();
    }

    /**
     * Resets current thread key creator to global one
     * 
     * @return old creator
     */
    public synchronized static CacheKeyCreator setGlobalKeyCreator() {
        return IMPL.setGlobalKeyCreator();
    }

    /**
     * ResetKeyCreator and remove current cache
     * 
     * @return old CacheKeyCreator
     */
    public synchronized static CacheKeyCreator setGlobalKeyCreatorAndRemove() {
        return IMPL.setGlobalKeyCreatorAndRemove();
    }

    /**
     * Get value by class, if not found use class.newInstance to create
     * 
     * @param <T>
     * @param clazz
     * @return cached or new instance
     */
    public synchronized static <T> T getOrPutNewByReflection(Class<T> clazz) {
        return IMPL.getOrPutNewByReflection(clazz);
    }

    /** Clear the cache */
    public static void clearCurrentCache() {
        IMPL.clearCurrentCache();
    }

    /**
     * Gets and release current cache
     * 
     * @return current cache
     */
    public static CurrentCache releaseCurrentCache() {
        return IMPL.releaseCurrentCache();
    }

    /**
     * @return current cache
     */
    public static CurrentCache getCurrentCache() {
        return IMPL.getCurrentCache();
    }

    /**
     * Put the current cache values
     * 
     * @param cache
     * @return old value
     */
    public static CurrentCache putCurrentCache(Map<Object, Object> cache) {
        return IMPL.putCurrentCache(cache);
    }

}
