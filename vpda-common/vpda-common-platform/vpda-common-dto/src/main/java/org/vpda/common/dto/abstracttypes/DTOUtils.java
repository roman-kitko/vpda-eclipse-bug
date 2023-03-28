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

import org.vpda.common.types.BackendObjectSourceAware;
import org.vpda.common.types.CrudOperation;
import org.vpda.common.types.IdentifiedObject;
import org.vpda.common.types.IdentifiedObjectReader;
import org.vpda.common.types.TypeUtils;

public final class DTOUtils {

    private DTOUtils() {
    }

    /**
     * Sets entity properties into {@link BackendObjectSourceAware} object
     * 
     * @param entity
     * @param backendObjectSourceAware
     */
    public static void setEntityToBackendObjectSourceAware(AbstractPersistentDTO entity, BackendObjectSourceAware backendObjectSourceAware) {
        TypeUtils.setEntityToBackendObjectSourceAware(entity, backendObjectSourceAware);
    }

    /**
     * Sets {@link BackendObjectSourceAware} object into entity properties
     * 
     * @param entity
     * @param backendObjectSourceAware
     * @param operation
     */
    public static void setBackendObjectSourceToEntity(AbstractPersistentDTO entity, BackendObjectSourceAware backendObjectSourceAware, CrudOperation operation) {
        if (!operation.isEditingData()) {
            return;
        }
        TypeUtils.setBackendObjectSourceToEntity(entity, backendObjectSourceAware, operation);
    }

    /**
     * Reads entity if not null
     * 
     * @param reader
     * @param entity
     */
    public static <E extends IdentifiedObject<?>> void readEntityIfNotNull(IdentifiedObjectReader<E> reader, E entity) {
        TypeUtils.readEntityIfNotNull(reader, entity);
    }
}
