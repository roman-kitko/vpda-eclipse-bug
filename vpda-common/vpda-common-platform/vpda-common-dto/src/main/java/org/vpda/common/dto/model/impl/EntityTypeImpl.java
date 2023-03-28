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
package org.vpda.common.dto.model.impl;

import org.vpda.common.dto.model.DTOType;
import org.vpda.common.dto.model.EntityType;

public final class EntityTypeImpl<X> extends AbstractIdentifiableTypeImpl<X> implements EntityType<X> {

    private EntityTypeImpl(EntityTypeImplTypeBuilder<X> builder) {
        super(builder);
    }

    public final static class EntityTypeImplTypeBuilder<X> extends AbstractIdentifiableTypeImpl.AbstractIdentifiableTypeImplBuilder<X, EntityTypeImpl<X>>
            implements org.vpda.common.util.Builder<EntityTypeImpl<X>> {

        public EntityTypeImplTypeBuilder() {
            super.setDtoType(DTOType.ENTITY);
        }

        @Override
        public EntityTypeImplTypeBuilder<X> setValues(EntityTypeImpl<X> type) {
            super.setValues(type);
            return this;
        }

        @Override
        public EntityTypeImpl<X> build() {
            return new EntityTypeImpl<X>(this);
        }
    }

}
