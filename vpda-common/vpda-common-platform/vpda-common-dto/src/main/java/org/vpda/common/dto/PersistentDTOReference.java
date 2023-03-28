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
import java.util.UUID;

import org.vpda.common.types.BaseIdentifiedObjectReference;
import org.vpda.common.types.IdentifiedObject;
import org.vpda.common.types.IdentifiedObjectReference;

/**
 * Reference to DTO by id
 * 
 * @author kitko
 * @param <T>
 *
 */
public final class PersistentDTOReference<T extends PersistentDTO> implements Serializable, IdentifiedObjectReference<T> {
    private static final long serialVersionUID = 6791948214814585727L;
    private Class<T> type;
    private Object id;
    private UUID externalId;
    private String display;
    private T value;

    public PersistentDTOReference(Class<T> type, Object id, UUID externalId, String display, T value) {
        this.type = type;
        this.id = id;
        this.externalId = externalId;
        this.display = display;
        this.value = value;
    }

    public static <T extends PersistentDTO> PersistentDTOReference<T> create(Class<T> type, Object id, UUID externalId, String display, T reference) {
        return new PersistentDTOReference<>(type, id, externalId, display, reference);
    }

    public static <T extends PersistentDTO> PersistentDTOReference<T> create(Class<T> type, Object id, UUID externalId, String display) {
        return new PersistentDTOReference<>(type, id, externalId, display, null);
    }

    public static <T extends PersistentDTO> PersistentDTOReference<T> createWithIdOnly(Class<T> type, Object id) {
        return new PersistentDTOReference<>(type, id, null, null, null);
    }

    public static <T extends PersistentDTO> PersistentDTOReference<T> createReferenceOnly(Class<T> type, IdentifiedObject<?> identifiedObject, String display) {
        return new PersistentDTOReference<>(type, identifiedObject.getId(), identifiedObject.getExternalId(), display, null);
    }

    public static <T extends PersistentDTO> PersistentDTOReference<T> createRefAndValue(Class<T> type, String display, T reference) {
        return new PersistentDTOReference<>(type, reference.getId(), reference.getExternalId(), display, reference);
    }

    public <I extends IdentifiedObject<?>> IdentifiedObjectReference<I> createIdentifiedObjectReference(Class<I> type) {
        return BaseIdentifiedObjectReference.createAsReference(type, id, externalId);
    }

    public PersistentDTOReference() {
        super();
    }

    /**
     * @return the type
     */
    @Override
    public Class<T> getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Class<T> type) {
        this.type = type;
    }

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
     * @return the display
     */
    public String getDisplay() {
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * @return the reference
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * @param reference the reference to set
     */
    public void setValue(T reference) {
        this.value = reference;
    }

}
