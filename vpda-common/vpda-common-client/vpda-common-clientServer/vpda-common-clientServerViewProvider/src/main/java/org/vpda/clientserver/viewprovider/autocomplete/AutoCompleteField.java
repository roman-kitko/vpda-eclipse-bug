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
package org.vpda.clientserver.viewprovider.autocomplete;

import java.io.Serializable;

/**
 * Single auto completion field description
 * 
 * @author kitko
 *
 */
public final class AutoCompleteField implements Serializable {
    private static final long serialVersionUID = 4549530587997554016L;
    private final String name;
    private final Class<?> type;

    /**
     * @param name
     * @param type
     */
    public AutoCompleteField(String name, Class<?> type) {
        super();
        this.name = name;
        this.type = type;
    }

    /**
     * factory method
     * 
     * @param name
     * @param type
     * @return AutoCompleteField
     */
    public static AutoCompleteField create(String name, Class<?> type) {
        return new AutoCompleteField(name, type);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public Class<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AutoCompleteField [name=" + name + ", type=" + type + "]";
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
        AutoCompleteField other = (AutoCompleteField) obj;
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
