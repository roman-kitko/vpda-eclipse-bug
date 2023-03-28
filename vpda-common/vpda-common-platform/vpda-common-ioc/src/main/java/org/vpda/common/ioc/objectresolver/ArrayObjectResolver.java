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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.vpda.internal.common.util.Assert;

/**
 * Can resolve objects from passed array
 * 
 * @author kitko
 *
 */
public final class ArrayObjectResolver implements ObjectResolver {

    private final Object[] array;

    /**
     * @param array
     * 
     */
    public ArrayObjectResolver(Object[] array) {
        this.array = Assert.isNotNullArgument(array, "array");
    }

    /**
     * @param o1
     * @param o2
     * 
     */
    public ArrayObjectResolver(Object o1, Object o2) {
        this(new Object[] { o1, o2 });
    }

    /**
     * @param o1
     * @param o2
     * @param o3
     * 
     */
    public ArrayObjectResolver(Object o1, Object o2, Object o3) {
        this(new Object[] { o1, o2, o3 });
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        return resolveObject(clazz, null, null);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        return resolveObject(clazz, null, null);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        for (Object arg : array) {
            if (arg != null && clazz.isInstance(arg)) {
                return clazz.cast(arg);
            }
        }
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        return resolveObject(clazz, null, null);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        return canResolveObject(clazz, null);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        for (Object arg : array) {
            if (arg != null && clazz.isInstance(arg)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<?> getKeys() {
        List<Class> keys = new ArrayList<>();
        for (Object arg : array) {
            keys.add(arg.getClass());
        }
        return keys;
    }

}
