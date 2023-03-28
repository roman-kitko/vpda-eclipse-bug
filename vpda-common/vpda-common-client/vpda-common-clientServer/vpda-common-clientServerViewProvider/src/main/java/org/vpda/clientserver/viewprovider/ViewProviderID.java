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
package org.vpda.clientserver.viewprovider;

import java.io.Serializable;
import java.util.UUID;

import org.vpda.internal.common.util.Assert;

/**
 * Some Abstraction of ID for view providers. Every provider must have its
 * unique ID
 * 
 * @author kitko
 *
 */
public final class ViewProviderID implements Serializable {
    private static final long serialVersionUID = 7789873495991630413L;
    /** Unique name */
    private final String id;
    private final ViewProviderDef def;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((def == null) ? 0 : def.hashCode());
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
        ViewProviderID other = (ViewProviderID) obj;
        if (def == null) {
            if (other.def != null)
                return false;
        }
        else if (!def.equals(other.def))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ID = " + id + " Def = " + def;
    }

    /**
     * @param id
     * @param def
     */
    public ViewProviderID(String id, ViewProviderDef def) {
        super();
        this.id = Assert.isNotEmptyArgument(id, "id");
        this.def = Assert.isNotNullArgument(def, "def");
    }

    /**
     * @return Returns the def.
     */
    public ViewProviderDef getDef() {
        return def;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Generate id
     * 
     * @return generated id
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

}
