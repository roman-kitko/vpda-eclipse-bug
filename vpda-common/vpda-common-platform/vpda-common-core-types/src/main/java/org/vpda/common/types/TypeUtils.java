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
package org.vpda.common.types;

/**
 * Common types utils
 * 
 * @author kitko
 *
 */
public final class TypeUtils {

    private TypeUtils() {
    }

    /**
     * Sets entity properties into {@link BackendObjectSourceAware} object
     * 
     * @param entity
     * @param backendObjectSourceAware
     */
    public static void setEntityToBackendObjectSourceAware(IdentifiedAndStampedObject entity, BackendObjectSourceAware backendObjectSourceAware) {
        if (entity == null) {
            backendObjectSourceAware.setBackendObjectSource(null);
        }
        else {
            backendObjectSourceAware.setBackendObjectSource(new BasicBackendObjectSource(entity.getId(), entity.getClass().getName(), entity.getStamp()));
        }
    }

    /**
     * Sets {@link BackendObjectSourceAware} object into entity properties
     * 
     * @param entity
     * @param backendObjectSourceAware
     * @param operation
     */
    @SuppressWarnings("unchecked")
    public static void setBackendObjectSourceToEntity(IdentifiedAndStampedObject entity, BackendObjectSourceAware backendObjectSourceAware, CrudOperation operation) {
        if (entity == null) {
            return;
        }
        BackendObjectSource backendObjectSource = backendObjectSourceAware.getBackendObjectSource();
        if (backendObjectSource == null) {
            return;
        }

        if (!entity.getClass().getName().equals(backendObjectSource.getBackendObjectType())) {
            throw new IllegalArgumentException("Cannot apply properties from backend type : " + backendObjectSource.getBackendObjectType() + " into entity : " + entity.getClass().getName());
        }
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Cannot apply properties from backend type : " + backendObjectSource.getBackendObjectType() + " cause entity has no id set");
        }
        else {
            if (!entity.getId().equals(backendObjectSource.getBackendObjectId())) {
                throw new IllegalArgumentException("Cannot apply properties from backend type : " + backendObjectSource.getBackendObjectType() + " into entity cause ids do not match");
            }
        }
        entity.setStamp(backendObjectSource.getBackendObjectStamp());
    }

    /**
     * Reads entity if not null
     * 
     * @param reader
     * @param entity
     */
    public static <E extends IdentifiedObject<?>> void readEntityIfNotNull(IdentifiedObjectReader<E> reader, E entity) {
        if (entity != null) {
            reader.readEntity(entity);
        }
        else {
            reader.readNullEntity();
        }
    }

}
