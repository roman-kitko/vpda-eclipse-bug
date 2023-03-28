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

import org.vpda.common.types.IdentifiedObject;

/**
 * Domain object. It has valid persisted state in storage
 * 
 * @author kitko
 *
 */
public interface PersistentDTO extends IdentifiedObject<Object> {

    /**
     * time what object this DTO represent was created
     * 
     * @return time
     */
    public Long getCreatedAt();

    /**
     * 
     * @return time the object this DTO represent was last updated
     */
    public Long getLastUpdatedAt();

    public default <T extends PersistentDTO> PersistentDTOReference<T> createSelfReference() {
        @SuppressWarnings("unchecked")
        T t = (T) this;
        @SuppressWarnings("unchecked")
        PersistentDTOReference<T> ref = new PersistentDTOReference<T>((Class<T>) t.getClass(), t.getId(), t.getExternalId(), t.getExternalId() != null ? t.getExternalId().toString() : null, t);
        return ref;
    }
}
