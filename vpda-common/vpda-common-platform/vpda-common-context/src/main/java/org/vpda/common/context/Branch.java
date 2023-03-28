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
package org.vpda.common.context;

import java.io.Serializable;
import java.util.UUID;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.types.AbstractNamedCode;
import org.vpda.common.types.IdentifiedObject;
import org.vpda.common.types.NamedCode;

/**
 * Branch inside the organization
 * 
 * @author admin
 *
 */
@Immutable
public final class Branch extends AbstractNamedCode implements Serializable, NamedCode, IdentifiedObject<Long> {
    private final Organization organization;
    private final long id;
    private final UUID externalId;

    /**
     * @param id
     * @param externalId
     * @param organization
     * @param code
     * @param name
     * @param description
     */
    public Branch(long id, UUID externalId, Organization organization, String code, String name, String description) {
        super(code, name, description);
        this.organization = organization;
        this.id = id;
        this.externalId = externalId;
    }

    private static final long serialVersionUID = 3486082493848542018L;

    /**
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Branch other = (Branch) obj;
        if (id != other.id)
            return false;
        if (organization == null) {
            if (other.organization != null)
                return false;
        }
        else if (!organization.equals(other.organization))
            return false;
        return true;
    }

    @Override
    public UUID getExternalId() {
        return externalId;
    }

}
