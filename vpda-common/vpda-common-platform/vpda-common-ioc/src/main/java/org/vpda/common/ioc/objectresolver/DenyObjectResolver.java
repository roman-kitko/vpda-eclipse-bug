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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Resolver that will delegate to other resolver but will deny some classes and
 * keys to resolve
 * 
 * @author kitko
 *
 */
public final class DenyObjectResolver implements ObjectResolver {
    private final ObjectResolver delegate;
    private final Set<Class> classesToDeny;
    private final Set<Object> keysToDeny;

    /**
     * Creates DenyObjectResolver
     * 
     * @param delegate
     * @param classesToDeny
     */
    public DenyObjectResolver(ObjectResolver delegate, Class... classesToDeny) {
        this.delegate = delegate;
        this.classesToDeny = new HashSet<>(Arrays.asList(classesToDeny));
        this.keysToDeny = Collections.emptySet();
    }

    /**
     * Creates DenyObjectResolver
     * 
     * @param delegate
     * @param classesToDeny
     * @param keysToDeny
     */
    public DenyObjectResolver(ObjectResolver delegate, Set<Class> classesToDeny, Set<Object> keysToDeny) {
        this.delegate = delegate;
        this.classesToDeny = new HashSet<>(classesToDeny);
        this.keysToDeny = new HashSet<>(keysToDeny);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        if (classesToDeny.contains(clazz)) {
            return null;
        }
        return delegate.resolveObject(clazz, contextObjects);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        if (classesToDeny.contains(clazz)) {
            return null;
        }
        return delegate.resolveObject(clazz);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        if (classesToDeny.contains(clazz)) {
            return null;
        }
        if (keysToDeny.contains(key)) {
            return null;
        }
        return delegate.resolveObject(clazz, key, contextObjects);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        if (classesToDeny.contains(clazz)) {
            return null;
        }
        if (keysToDeny.contains(key)) {
            return null;
        }
        return delegate.resolveObject(clazz, key);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        if (classesToDeny.contains(clazz)) {
            return false;
        }
        return delegate.canResolveObject(clazz);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        if (classesToDeny.contains(clazz)) {
            return false;
        }
        if (keysToDeny.contains(key)) {
            return false;
        }
        return delegate.canResolveObject(clazz, key);
    }

    @Override
    public Collection<?> getKeys() {
        return delegate.getKeys();
    };

}
