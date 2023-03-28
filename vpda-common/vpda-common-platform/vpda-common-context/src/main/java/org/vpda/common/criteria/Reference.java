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
 * Reference to any object (column e.g)
 * 
 * @author kitko
 * @param <T> type of reference
 *
 */
@Immutable
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class Reference<T> implements Criterion {
    private static final long serialVersionUID = 1359234527691574647L;
    private final T id;

    /**
     * @return object
     */
    public T getId() {
        return id;
    }

    /**
     * Creates Reference
     * 
     * @param id
     */
    public Reference(T id) {
        this.id = Assert.isNotNullArgument(id, "id");
    }

    /**
     * @param id
     * @return new Reference
     */
    public static <T> Reference<T> create(T id) {
        return new Reference<T>(id);
    }

    /**
     * Creates Reference from json string
     * 
     * @param id
     * @return Reference
     */
    @JsonCreator
    public static <T> Reference<T> fromJson(@JsonProperty("id") T id) {
        return new Reference<T>(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        final Reference other = (Reference) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

}
