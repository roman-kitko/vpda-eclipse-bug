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
package org.vpda.common.dto.abstracttypes;

import java.io.Serializable;
import java.util.UUID;

import org.vpda.common.dto.PersistentDTO;
import org.vpda.common.dto.annotations.DTOMappedSuperIdentifiableClass;
import org.vpda.common.types.ExternalId;
import org.vpda.common.types.Id;
import org.vpda.common.types.IdentifiedAndStampedObject;
import org.vpda.common.types.IdentifiedObject;
import org.vpda.common.types.StampedObject;

/**
 * Abstract Persistent DTO
 * 
 * @author kitko
 *
 */
@DTOMappedSuperIdentifiableClass
public abstract class AbstractPersistentDTO implements Serializable, IdentifiedObject<Object>, PersistentDTO, StampedObject<Long>, IdentifiedAndStampedObject<Object, Long> {
    private static final long serialVersionUID = 422930170125600963L;

    @Id
    private Object id;
    
    @ExternalId
    private UUID externalId;
    
    private Long createdAt;
    private Long lastUpdatedAt;

    /**
     * @return the id
     */
    @Override
    public Object getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Object id) {
        this.id = id;
    }

    /**
     * @return the externalId
     */
    @Override
    public UUID getExternalId() {
        return externalId;
    }

    /**
     * @param externalId the externalId to set
     */
    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    /**
     * @return the createdAt
     */
    @Override
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the lastUpdatedAt
     */
    @Override
    public Long getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    /**
     * @param lastUpdatedAt the lastUpdatedAt to set
     */
    public void setLastUpdatedAt(Long lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public void setStamp(Long stamp) {
        setLastUpdatedAt(stamp);

    }

    @Override
    public Long getStamp() {
        return getLastUpdatedAt();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((externalId == null) ? 0 : externalId.hashCode());
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
        AbstractPersistentDTO other = (AbstractPersistentDTO) obj;
        if (externalId == null) {
            if (other.externalId != null)
                return false;
        }
        else if (!externalId.equals(other.externalId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    /**
     * @param id
     * @param externalId
     * @param createdAt
     * @param lastUpdatedAt
     */
    protected AbstractPersistentDTO(Object id, UUID externalId, long createdAt, long lastUpdatedAt) {
        super();
        this.id = id;
        this.externalId = externalId;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    /**
     * @param id
     */
    protected AbstractPersistentDTO(Object id) {
        super();
        this.id = id;
    }

    /**
     * 
     */
    protected AbstractPersistentDTO() {
        super();
    }

}
