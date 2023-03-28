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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.picocontainer.PicoContainer;
import org.vpda.internal.common.util.Assert;

/**
 * {@link ObjectResolver} that resolves instances from {@link PicoContainer}
 * 
 * @author kitko
 *
 */
public final class PicoContainerObjectResolver implements ObjectResolver, Serializable {
    private static final long serialVersionUID = -4434309121241729000L;
    private final PicoContainer container;

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        return container.getComponent(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        T result = null;
        if (key != null) {
            result = (T) container.getComponent(key);
        }
        else {
            result = container.getComponent(clazz);
        }
        return result;
    }

    /**
     * Creates PicoContainerObjectResolver
     * 
     * @param container
     */
    public PicoContainerObjectResolver(PicoContainer container) {
        super();
        this.container = Assert.isNotNull(container);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        return container.getComponent(clazz) != null;
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        Object res = container.getComponent(key);
        return res != null && clazz.isInstance(res);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        return resolveObject(clazz, null);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        return resolveObject(clazz, key, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<?> getKeys() {
        Set keys = new HashSet();
        for (org.picocontainer.ComponentAdapter adapter : container.getComponentAdapters()) {
            keys.add(adapter.getComponentKey());
        }
        return keys;
    }

}
