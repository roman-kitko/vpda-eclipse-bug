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
package org.vpda.common.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class PropertyPath implements Serializable {
    private static final long serialVersionUID = -1821241671227598826L;
    private final String localId;
    private final PropertyPath parent;

    private PropertyPath(PropertyPath parent, String localId) {
        this.localId = localId;
        this.parent = parent;
    }

    public List<String> getPath() {
        if (parent == null) {
            return Collections.singletonList(localId);
        }
        PropertyPath p = this;
        LinkedList<String> path = new LinkedList<>();
        while (p != null) {
            path.addFirst(p.localId);
            p = p.parent;
        }
        return path;
    }

    public String getLocalId() {
        return localId;
    }

    public String getFullId() {
        return getFullId('.');
    }

    public String getFullId(char separator) {
        if (parent == null) {
            return localId;
        }
        PropertyPath p = this;
        StringBuilder builder = new StringBuilder();
        while (p != null) {
            if (builder.length() > 0) {
                builder.insert(0, separator);
            }
            builder.insert(0, p.localId);
            p = p.parent;
        }
        return builder.toString();

    }

    public PropertyPath getParent() {
        return parent;
    }

    public PropertyPath createChild(String localId) {
        return new PropertyPath(this, localId);
    }

    public static PropertyPath createRoot(String localId) {
        return new PropertyPath(null, localId);
    }

    @Override
    public String toString() {
        return getFullId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((localId == null) ? 0 : localId.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
        PropertyPath other = (PropertyPath) obj;
        if (localId == null) {
            if (other.localId != null) {
                return false;
            }
        }
        else if (!localId.equals(other.localId)) {
            return false;
        }
        if (parent == null) {
            if (other.parent != null) {
                return false;
            }
        }
        else if (!parent.equals(other.parent)) {
            return false;
        }
        return true;
    }

}
