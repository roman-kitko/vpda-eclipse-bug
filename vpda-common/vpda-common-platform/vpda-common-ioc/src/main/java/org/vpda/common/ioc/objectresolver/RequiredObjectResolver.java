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
import java.util.Map;

import org.vpda.internal.common.util.Assert;

/**
 * {@link ObjectResolver} that will throw {@link RuntimeException} when cannot
 * resolve object
 * 
 * @author kitko
 *
 */
public final class RequiredObjectResolver implements ObjectResolver {
    private final ObjectResolver delegate;

    /**
     * Creates RequiredObjectResolver with passed delegate
     * 
     * @param delegate
     */
    public RequiredObjectResolver(ObjectResolver delegate) {
        this.delegate = Assert.isNotNullArgument(delegate, "delegate");
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        return delegate.canResolveObject(clazz, key);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        return delegate.canResolveObject(clazz);
    }

    @Override
    public Collection<?> getKeys() {
        return delegate.getKeys();
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        T object = delegate.resolveObject(clazz, contextObjects);
        if (object != null) {
            return object;
        }
        throw new RuntimeException("Cannot resolve object by class [" + clazz + "]");
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        T object = delegate.resolveObject(clazz, key, contextObjects);
        if (object != null) {
            return object;
        }
        throw new RuntimeException("Cannot resolve object by class [" + clazz + "]");
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        T object = delegate.resolveObject(clazz, key);
        if (object != null) {
            return object;
        }
        throw new RuntimeException("Cannot resolve object by class [" + clazz + "]");
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        T object = delegate.resolveObject(clazz);
        if (object != null) {
            return object;
        }
        throw new RuntimeException("Cannot resolve object by class [" + clazz + "]");
    }
}
