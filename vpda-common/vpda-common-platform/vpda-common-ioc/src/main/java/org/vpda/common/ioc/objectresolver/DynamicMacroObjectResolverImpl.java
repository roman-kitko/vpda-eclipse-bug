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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of DynamicMacroObjectResolver
 * 
 * @author kitko
 *
 */
public final class DynamicMacroObjectResolverImpl implements DynamicMacroObjectResolver {

    private final List<ObjectResolver> resolvers;

    @Override
    public DynamicMacroObjectResolver addResolver(ObjectResolver resolver) {
        resolvers.add(resolver);
        return this;
    }

    @Override
    public boolean removeResolver(ObjectResolver resolver) {
        return resolvers.remove(resolver);
    }

    @Override
    public synchronized <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        for (ObjectResolver resolver : resolvers) {
            T value = resolver.resolveObject(clazz, contextObjects);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        return resolveObject(clazz, null);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        return resolveObject(clazz, key, null);
    }

    /**
     * Creates MacroObjectResolver with list of delegators
     * 
     * @param resolvers
     */
    public DynamicMacroObjectResolverImpl(List<ObjectResolver> resolvers) {
        super();
        this.resolvers = new ArrayList<ObjectResolver>(resolvers);
    }

    /**
     * Creates MacroObjectResolver with list of delegators
     * 
     * @param resolvers
     */
    public DynamicMacroObjectResolverImpl(ObjectResolver... resolvers) {
        super();
        this.resolvers = new ArrayList<ObjectResolver>();
        for (ObjectResolver resolver : resolvers) {
            this.resolvers.add(resolver);
        }
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        for (ObjectResolver resolver : resolvers) {
            if (resolver.canResolveObject(clazz)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized List<ObjectResolver> getResolvers() {
        return Collections.unmodifiableList(resolvers);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        for (ObjectResolver resolver : resolvers) {
            T value = resolver.resolveObject(clazz, key, contextObjects);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        for (ObjectResolver resolver : resolvers) {
            if (resolver.canResolveObject(clazz, key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<?> getKeys() {
        Set set = new HashSet();
        for (ObjectResolver resolver : resolvers) {
            set.addAll(resolver.getKeys());
        }
        return set;
    }

    @Override
    public DynamicMacroObjectResolver addFirstResolver(ObjectResolver resolver) {
        return addResolver(0, resolver);
    }

    @Override
    public DynamicMacroObjectResolver addResolver(int index, ObjectResolver resolver) {
        this.resolvers.add(index, resolver);
        return this;
    }

}
