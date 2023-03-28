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

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public final class LazyObjectResolver implements ObjectResolver {

    private final Supplier<ObjectResolver> supplier;

    public LazyObjectResolver(Supplier<ObjectResolver> supplier) {
        this.supplier = supplier;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        return supplier.get().resolveObject(clazz, contextObjects);
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<?> getKeys() {
        // TODO Auto-generated method stub
        return null;
    }

}
