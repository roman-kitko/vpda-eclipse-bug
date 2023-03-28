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
import org.vpda.common.types.IdentifiedObject;

/**
 * Year context item
 * 
 * @author admin
 *
 */
@Immutable
public final class Year implements Serializable, IdentifiedObject<Long> {

    private final long id;
    private final UUID externalId;

    /**
     * @param id
     * @param externalId
     */
    public Year(long id, UUID externalId) {
        this.id = id;
        this.externalId = externalId;
    }

    private static final long serialVersionUID = 3486082493848542018L;

    @Override
    public Long getId() {
        return id;
    }

    /**
     * 
     * @return yaer
     */
    public int getYear() {
        return (int) id;
    }

    @Override
    public String toString() {
        return Long.toString(id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Year other = (Year) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public UUID getExternalId() {
        return externalId;
    }

}
