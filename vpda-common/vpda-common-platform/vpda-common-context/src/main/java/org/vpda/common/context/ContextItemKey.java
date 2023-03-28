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
 * Key for {@link ContextItem}
 * 
 * @author kitko
 *
 */
@Immutable
public final class ContextItemKey implements Serializable {
    private static final long serialVersionUID = 8882947206530957610L;
    private final ContextItemType type;
    private final String name;

    /**
     * Creates key
     * 
     * @param type
     * @param name
     */
    public ContextItemKey(ContextItemType type, String name) {
        this.type = Assert.isNotNullArgument(type, "type");
        this.name = Assert.isNotNullArgument(name, "name");
    }

    /**
     * Creates key with name 'default'
     * 
     * @param type
     */
    public ContextItemKey(ContextItemType type) {
        this.type = Assert.isNotNullArgument(type, "type");
        this.name = "default";
    }

    /**
     * @return default java class
     */
    public Class getJavaClass() {
        return type.getJavaClass();
    }

    /**
     * @return the type
     */
    public ContextItemType getType() {
        return type;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ContextItemKey [name=" + name + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        ContextItemKey other = (ContextItemKey) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }

}
