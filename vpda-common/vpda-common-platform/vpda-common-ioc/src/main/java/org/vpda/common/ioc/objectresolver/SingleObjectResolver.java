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
package org.vpda.common.ioc.objectresolver;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Object resolver that holds just one key with value
 * 
 * @author kitko
 * @param <V> - type of value
 *
 */
public final class SingleObjectResolver<V> implements ObjectResolver {
    private final Class<V> clazz;
    private final V value;
    private final String key;

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        if (clazz.isAssignableFrom(this.clazz)) {
            if (key.equals(this.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        if (clazz.isAssignableFrom(this.clazz)) {

            return clazz.cast(this.value);
        }
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        if (clazz.isAssignableFrom(this.clazz)) {
            return clazz.cast(this.value);
        }
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        if (clazz.isAssignableFrom(this.clazz)) {
            if (key.equals(this.key)) {
                return clazz.cast(this.value);
            }
        }
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        if (clazz.isAssignableFrom(this.clazz)) {
            if (key.equals(this.key)) {
                return clazz.cast(this.value);
            }
        }
        return null;

    }

    @Override
    public Collection<?> getKeys() {
        return Collections.singletonList(this.clazz);
    }

    /**
     * Creates SingleObjectResolver
     * 
     * @param key
     * @param clazz
     * @param value
     */
    public SingleObjectResolver(String key, Class<V> clazz, V value) {
        super();
        this.clazz = clazz;
        this.value = value;
        this.key = key;
    }

    /**
     * Creates SingleObjectResolver
     * 
     * @param clazz
     * @param value
     */
    public SingleObjectResolver(Class<V> clazz, V value) {
        super();
        this.clazz = clazz;
        this.value = value;
        this.key = null;
    }

    /**
     * @param value
     */
    @SuppressWarnings("unchecked")
    public SingleObjectResolver(V value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot determine key type for null value");
        }
        this.value = value;
        this.clazz = (Class<V>) value.getClass();
        this.key = null;
    }

    /**
     * Creates single value resolver
     * 
     * @param type
     * @param value
     * @return single value resolver
     */
    public static <V> SingleObjectResolver<V> create(Class<V> type, V value) {
        return new SingleObjectResolver<V>(type, value);
    }

    /**
     * Creates single value resolver
     * 
     * @param key
     * @param type
     * @param value
     * @return single value resolver
     */
    public static <V> SingleObjectResolver<V> create(String key, Class<V> type, V value) {
        return new SingleObjectResolver<V>(key, type, value);
    }

}
