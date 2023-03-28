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
package org.vpda.common.command.env;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.internal.common.util.Assert;

/**
 * CommandExecutionEnv that delegates all calls to {@link ObjectResolver}
 * 
 * @author kitko
 *
 */
public class CEEnvDelegate implements CommandExecutionEnv, Serializable {
    private static final long serialVersionUID = 2077403573890827279L;
    private final ObjectResolver objectResolver;

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        return objectResolver.canResolveObject(clazz);
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        return objectResolver.canResolveObject(clazz, key);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        return objectResolver.resolveObject(clazz, contextObjects);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        return objectResolver.resolveObject(clazz, key, contextObjects);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        return objectResolver.resolveObject(clazz);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        return objectResolver.resolveObject(clazz, key);
    }

    /**
     * Will create CommandExecutionEnvORDelegate with passed resolver
     * 
     * @param objectResolver
     */
    public CEEnvDelegate(ObjectResolver objectResolver) {
        this.objectResolver = Assert.isNotNull(objectResolver, "objectResolver argument is null");
    }

    @Override
    public Collection<?> getKeys() {
        return objectResolver.getKeys();
    }

}
