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

import java.io.Serializable;

import org.vpda.common.annotations.Immutable;
import org.vpda.internal.common.util.Assert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Basic {@link CriteriaTree} implementation.
 * 
 * @author kitko
 *
 */
@Immutable
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class CriteriaTreeImpl implements CriteriaTree, Serializable {
    private static final long serialVersionUID = 1168732758366106417L;
    private final String name;
    private final Criterion root;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Criterion getRoot() {
        return root;
    }

    /**
     * @param root
     * @param name
     */
    public CriteriaTreeImpl(String name, Criterion root) {
        super();
        this.name = Assert.isNotNullArgument(name, "name");
        this.root = root;
    }

    /**
     * @param name
     */
    public CriteriaTreeImpl(String name) {
        this.name = Assert.isNotNullArgument(name, "name");
        this.root = null;
    }

    /**
     * @param name
     * @param root
     * @return CriteriaTreeImpl
     */
    @JsonCreator
    public static CriteriaTreeImpl fromJson(@JsonProperty("name") String name, @JsonProperty("root") Criterion root) {
        return new CriteriaTreeImpl(name, root);
    }

    @Override
    public String toString() {
        return name + " : " + root;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((root == null) ? 0 : root.hashCode());
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
        CriteriaTreeImpl other = (CriteriaTreeImpl) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (root == null) {
            if (other.root != null) {
                return false;
            }
        }
        else if (!root.equals(other.root)) {
            return false;
        }
        return true;
    }

}
