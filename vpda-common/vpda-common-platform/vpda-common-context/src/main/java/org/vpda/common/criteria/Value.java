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
package org.vpda.common.criteria;

import org.vpda.common.annotations.Immutable;
import org.vpda.internal.common.util.Assert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Value Criterion
 * 
 * @author kitko
 * @param <T>
 *
 */
@Immutable
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class Value<T> implements Criterion {
    private static final long serialVersionUID = -1298838998724586708L;
    private final T value;
    private final Class<T> clazz;

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @return the clazz
     */
    public Class<T> getClazz() {
        return clazz;
    }

    /**
     * Creates value with class
     * 
     * @param value
     * @param clazz
     */
    public Value(T value, Class<T> clazz) {
        super();
        this.value = Assert.isNotNullArgument(value, "value");
        this.clazz = Assert.isNotNullArgument(clazz, "clazz");
    }

    /**
     * Factory method to create new value
     * 
     * @param value
     * @return new Value
     */
    public static <T> Value<T> create(T value) {
        return new Value<T>(value);
    }

    /**
     * Factory method to create new value
     * 
     * @param value
     * @param clazz
     * @return new Value
     */
    public static <T> Value<T> create(T value, Class<T> clazz) {
        return new Value<T>(value, clazz);
    }

    /**
     * Creates Value from json string
     * 
     * @param value
     * @param clazz
     * @return Value
     */
    @JsonCreator
    public static <T> Value<T> fromJson(@JsonProperty("value") T value, @JsonProperty("clazz") Class<T> clazz) {
        return new Value<T>(value, clazz);
    }

    /**
     * Creates value with class resolved from value
     * 
     * @param value
     */
    @SuppressWarnings("unchecked")
    public Value(T value) {
        super();
        this.value = Assert.isNotNullArgument(value, "value");
        this.clazz = (Class<T>) value.getClass();
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Value other = (Value) obj;
        if (clazz == null) {
            if (other.clazz != null) {
                return false;
            }
        }
        else if (!clazz.equals(other.clazz)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        }
        else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

}
