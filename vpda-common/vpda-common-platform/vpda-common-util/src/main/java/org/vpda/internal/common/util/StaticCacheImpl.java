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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.StaticCache.CurrentCache;

final class StaticCacheImpl {

    StaticCacheImpl() {
    }

    private final ThreadLocal<CacheKeyCreator> cacheKeyCreator = new ThreadLocal<CacheKeyCreator>();
    private final ConcurrentMap<Object, Map<Object, Object>> caches = new ConcurrentHashMap<Object, Map<Object, Object>>();

    /** Global key */
    final Object globalKey = new Object();
    /** Global key creator */
    final CacheKeyCreator globalKeyCreator = new GlobalCacheKeyCreator(globalKey);

    static class GlobalCacheKeyCreator implements CacheKeyCreator {
        private final Object key;

        GlobalCacheKeyCreator(Object key) {
            this.key = key;
        }

        @Override
        public Object getCurrentKey() {
            return key;
        }

    }

    /**
     * Gets value by key of type clazz
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @return value
     */
    <T> T get(Class<T> clazz, Object key) {
        Map<Object, Object> currentCache = getCurrentCacheImpl();
        return clazz.cast(currentCache.get(key));
    }

    /**
     * gets value by class as key
     * 
     * @param <T>
     * @param clazz
     * @return value
     */
    <T> T get(Class<T> clazz) {
        Map<Object, Object> currentCache = getCurrentCacheImpl();
        return clazz.cast(currentCache.get(clazz));
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
    <T> T put(Class<T> clazz, Object key, T value) {
        Map<Object, Object> currentCache = getCurrentCacheImpl();
        T old = clazz.cast(currentCache.get(key));
        currentCache.put(key, value);
        return old;
    }

    /**
     * Puts value to current thread cache by clazz
     * 
     * @param <T>
     * @param clazz
     * @param value
     * @return old value
     */
    <T> T put(Class<T> clazz, T value) {
        Map<Object, Object> currentCache = getCurrentCacheImpl();
        T old = clazz.cast(currentCache.get(clazz));
        currentCache.put(clazz, value);
        return old;
    }

    private Map<Object, Object> getCurrentCacheImpl() {
        CacheKeyCreator creator = cacheKeyCreator.get();
        if (creator == null) {
            creator = globalKeyCreator;
            cacheKeyCreator.set(creator);
        }
        Object key = creator.getCurrentKey();
        Map<Object, Object> currentCache = caches.get(key);
        if (currentCache == null) {
            currentCache = new HashMap<Object, Object>(2);
            Map<Object, Object> old = caches.putIfAbsent(key, currentCache);
            currentCache = old != null ? old : currentCache;
        }
        return currentCache;
    }

    /**
     * Sets current thread CacheKeyCreator
     * 
     * @param creator
     * @return old current thread creator
     */
    CacheKeyCreator setKeyCreator(CacheKeyCreator creator) {
        CacheKeyCreator old = cacheKeyCreator.get();
        cacheKeyCreator.set(creator);
        return old;
    }

    /**
     * @return current CacheKeyCreator
     */
    CacheKeyCreator getKeyCreator() {
        return cacheKeyCreator.get();
    }

    /**
     * Resets current thread key creator to global one
     * 
     * @return old creator
     */
    CacheKeyCreator setGlobalKeyCreator() {
        CacheKeyCreator old = cacheKeyCreator.get();
        cacheKeyCreator.set(globalKeyCreator);
        return old;
    }

    /**
     * ResetKeyCreator and remove current cache
     * 
     * @return old CacheKeyCreator
     */
    CacheKeyCreator setGlobalKeyCreatorAndRemove() {
        CacheKeyCreator old = setGlobalKeyCreator();
        if (old != null) {
            Object key = old.getCurrentKey();
            caches.remove(key);
        }
        return old;
    }

    /**
     * Get value by class, if not found use class.newInstance to create
     * 
     * @param <T>
     * @param clazz
     * @return cached or new instance
     */
    <T> T getOrPutNewByReflection(Class<T> clazz) {
        T t = get(clazz);
        if (t == null) {
            try {
                Constructor<T> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                t = constructor.newInstance();
            }
            catch (Exception e) {
                throw new VPDARuntimeException("Cannot create new instance of cache value", e);
            }
            put(clazz, t);
        }
        return t;

    }

    /** Clear the cache */
    void clearCurrentCache() {
        CacheKeyCreator creator = cacheKeyCreator.get();
        if (creator == null) {
            return;
        }
        Object key = creator.getCurrentKey();
        Map<Object, Object> currentCache = caches.get(key);
        if (currentCache == null) {
            return;
        }
        currentCache.clear();
    }

    CurrentCache releaseCurrentCache() {
        CacheKeyCreator creator = cacheKeyCreator.get();
        if (creator == null) {
            return null;
        }
        Object key = creator.getCurrentKey();
        Map<Object, Object> currentCache = caches.remove(key);
        return new CurrentCache(key, currentCache);
    }

    CurrentCache getCurrentCache() {
        CacheKeyCreator creator = cacheKeyCreator.get();
        if (creator == null) {
            return null;
        }
        Object key = creator.getCurrentKey();
        Map<Object, Object> currentCache = caches.get(key);
        return new CurrentCache(key, currentCache);
    }

    CurrentCache putCurrentCache(Map<Object, Object> cache) {
        CacheKeyCreator creator = cacheKeyCreator.get();
        if (creator == null) {
            return null;
        }
        Object key = creator.getCurrentKey();
        Map<Object, Object> old = caches.put(key, cache);
        return new CurrentCache(key, old);
    }

}
