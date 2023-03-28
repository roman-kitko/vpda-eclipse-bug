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

/**
 * 
 * Will resolve object by class or key
 * 
 * @author kitko
 *
 */
public interface ObjectResolver {
    /**
     * Resolve object by class
     * 
     * @param <T>
     * @param clazz
     * @param contextObjects
     * @return resolved object
     */
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects);

    /**
     * Resolves object by class
     * 
     * @param <T>
     * @param clazz
     * @return resolved object
     */
    public <T> T resolveObject(Class<T> clazz);

    /**
     * Resolve object by key
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @param contextObjects
     * @return resolved object
     */
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects);

    /**
     * Resolves object by key
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @return resolved object
     */
    public <T> T resolveObject(Class<T> clazz, Object key);

    /**
     * Test if resolver can resolve object
     * 
     * @param <T>
     * @param clazz
     * @return true if we can resolve object by class
     */
    public <T> boolean canResolveObject(Class<T> clazz);

    /**
     * Test if resolver can resolve object
     * 
     * @param <T>
     * @param clazz
     * @param key
     * @return true if we can resolve object by key and object is of type clazz
     */
    public <T> boolean canResolveObject(Class<T> clazz, Object key);

    /**
     * @return list of keys by which we can resolve objects
     */
    public Collection<?> getKeys();

}
