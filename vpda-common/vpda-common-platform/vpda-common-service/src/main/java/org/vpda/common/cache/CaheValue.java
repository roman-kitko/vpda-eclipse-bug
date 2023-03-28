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
package org.vpda.common.cache;

import org.vpda.internal.common.util.Assert;

/**
 * Resolved value from cache. We can have 3 options : 1. We have not null object
 * in cache, so return that 2. We have ever asked cache for that key, so we
 * return null 3. Or we now, that object for that key does not exiist, so we
 * need to return special value for that, NOT_FOUND
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class CaheValue<T> {

    private static final CaheValue<Object> NOT_FOUND = new CaheValue<>();

    private final T value;

    /**
     * Creates CaheValue with normal resolved
     * 
     * @param value
     */
    public CaheValue(T value) {
        this.value = Assert.isNotNullArgument(value, "value");
    }

    /**
     * @param value
     * @return cached value
     */
    public static <T> CaheValue<T> create(T value) {
        return new CaheValue<T>(value);
    }

    /**
     * Creates not found
     * 
     * @param value
     * @return not found
     */
    @SuppressWarnings("unchecked")
    public static <T> CaheValue<T> createNotFound() {
        return (CaheValue<T>) NOT_FOUND;
    }

    private CaheValue() {
        value = null;
    }

    /**
     * @return value
     */
    public T getValue() {
        return value;
    }

    /**
     * @return true if this object is normal object
     */
    public boolean isNormalCachedObject() {
        return value != null;
    }

    /**
     * @return true if this object is jut like not found reference
     */
    public boolean isNotFound() {
        return value == null;
    }

}
