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

import org.vpda.common.dto.DomainObject;
import org.vpda.common.dto.annotations.DTOMappedSuperIdentifiableClass;
import org.vpda.common.types.IdentifiedObject;

/**
 * Abstract DTO
 * 
 * @author kitko
 *
 */
@DTOMappedSuperIdentifiableClass
public abstract class AbstractDomainObject extends AbstractPersistentDTO implements Serializable, IdentifiedObject<Object>, DomainObject {
    private static final long serialVersionUID = 422930170125600963L;

    /**
     * 
     */
    protected AbstractDomainObject() {
        super();
    }

    /**
     * @param id
     * @param externalId
     * @param createdAt
     * @param lastUpdatedAt
     */
    protected AbstractDomainObject(Object id, UUID externalId, long createdAt, long lastUpdatedAt) {
        super(id, externalId, createdAt, lastUpdatedAt);
    }

    /**
     * @param id
     */
    protected AbstractDomainObject(Object id) {
        super(id);
    }
}
