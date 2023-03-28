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

import java.io.Serializable;
import java.util.UUID;

public final class BaseIdentifiedObjectReference<T extends IdentifiedObject<?>> implements IdentifiedObjectReference<T>, Serializable {

    private static final long serialVersionUID = 1670890182002337281L;
    private Class<T> type;
    private Object id;
    private UUID externalId;
    private final T value;

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public UUID getExternalId() {
        return externalId;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    /**
     * @param type
     * @param id
     * @param externalId
     * @param value
     */
    public BaseIdentifiedObjectReference(Class<T> type, Object id, UUID externalId, T value) {
        super();
        this.type = type;
        this.id = id;
        this.externalId = externalId;
        this.value = value;
    }

    public static <T extends IdentifiedObject<?>> BaseIdentifiedObjectReference<T> createAsReference(Class<T> type, Object id, UUID externalId) {
        return new BaseIdentifiedObjectReference<T>(type, id, externalId, null);
    }

    public static <T extends IdentifiedObject<?>> BaseIdentifiedObjectReference<T> createWithValue(Class<T> type, Object id, UUID externalId, T value) {
        return new BaseIdentifiedObjectReference<T>(type, id, externalId, value);
    }

    @Override
    public T getValue() {
        return value;
    }

}
