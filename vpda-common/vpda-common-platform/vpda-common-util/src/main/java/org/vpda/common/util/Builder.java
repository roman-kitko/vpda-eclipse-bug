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
package org.vpda.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Common object builder. Builder can help clients to build more difficult
 * object or help to keep object immutable.
 * 
 * @author kitko
 * @param <T> Type of object the builder is capable to build
 *
 */
public interface Builder<T> {

    /**
     * @return class of target object being built by this builder
     */
    @SuppressWarnings("unchecked")
    public default Class<? extends T> getTargetClass() {
        Type type = getClass().getGenericInterfaces()[0];
        if (type instanceof ParameterizedType) {
            return (Class<? extends T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        throw new IllegalArgumentException("Cannot resolve Builder type");
    }

    /**
     * Builds target object
     * 
     * @return target object
     */
    public T build();
    
    public default Builder<T> setValues(T value) {
        return this;
    }
}
