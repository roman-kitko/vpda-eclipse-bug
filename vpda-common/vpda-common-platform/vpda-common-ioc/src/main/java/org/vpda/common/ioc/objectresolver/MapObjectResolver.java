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
package org.vpda.common.ioc.objectresolver;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Resolving object from {@link Map}
 * 
 * @author kitko
 *
 */
public final class MapObjectResolver implements ObjectResolver, Serializable {
    private static final long serialVersionUID = -6994403078785758810L;
    private final Map<Object, Object> map;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        return (T) map.get(clazz);
    }

    /**
     * Creates MapObjectResolver with passed map
     * 
     * @param map
     */
    public MapObjectResolver(Map<?, ?> map) {
        super();
        this.map = new HashMap<Object, Object>(map);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        return map.containsKey(clazz);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        T result = null;
        if (key != null) {
            result = clazz.cast(map.get(key));
        }
        else {
            result = clazz.cast(map.get(clazz));
        }
        return result;
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        Object result = map.get(key);
        return result != null && clazz.isInstance(result);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        return resolveObject(clazz, null);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        return resolveObject(clazz, key, null);
    }

    @Override
    public Collection<?> getKeys() {
        return map.keySet();
    }

}
