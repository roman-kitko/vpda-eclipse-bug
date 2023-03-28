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
package org.vpda.common.context;

import java.io.Serializable;

import org.vpda.common.annotations.Immutable;
import org.vpda.internal.common.util.Assert;

/**
 * Item in context
 * 
 * @author kitko
 *
 * @param <T> type of data
 */
@Immutable
public final class ContextItem<T> implements Serializable {
    private static final long serialVersionUID = 2323401573319672787L;
    private final ContextItemKey key;
    private final T value;
    private final Class<? extends T> clazz;

    /**
     * Creates item
     * 
     * @param key
     * @param value
     */
    @SuppressWarnings("unchecked")
    public ContextItem(ContextItemKey key, T value) {
        this.key = Assert.isNotNullArgument(key, "key");
        this.value = Assert.isNotNullArgument(value, "value");
        this.clazz = (Class<? extends T>) value.getClass();
    }

    /**
     * Creates item with value and type
     * 
     * @param key
     * @param value
     * @param clazz
     */
    public ContextItem(ContextItemKey key, T value, Class<T> clazz) {
        this.key = Assert.isNotNullArgument(key, "key");
        this.value = value;
        this.clazz = Assert.isNotNullArgument(clazz, "clazz");
        ;
    }

    /**
     * @return the key
     */
    public ContextItemKey getKey() {
        return key;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @return the clazz
     */
    public Class<? extends T> getClazz() {
        return clazz;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContextItem other = (ContextItem) obj;
        if (clazz == null) {
            if (other.clazz != null)
                return false;
        }
        else if (!clazz.equals(other.clazz))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        }
        else if (!key.equals(other.key))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        }
        else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value.toString();
        }
        return "ContextItem [type=" + key + ", value=" + value + ", clazz=" + clazz + "]";
    }

}
