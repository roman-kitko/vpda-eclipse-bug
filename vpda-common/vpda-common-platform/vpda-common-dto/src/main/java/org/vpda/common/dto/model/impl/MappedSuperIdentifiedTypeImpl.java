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
import org.vpda.common.dto.model.MappedSuperIdentifiedType;

public final class MappedSuperIdentifiedTypeImpl<X> extends AbstractIdentifiableTypeImpl<X> implements MappedSuperIdentifiedType<X> {

    private MappedSuperIdentifiedTypeImpl(MappedSuperIdentifiedTypeImplTypeBuilder<X> builder) {
        super(builder);
    }

    public final static class MappedSuperIdentifiedTypeImplTypeBuilder<X> extends AbstractIdentifiableTypeImpl.AbstractIdentifiableTypeImplBuilder<X, MappedSuperIdentifiedTypeImpl<X>>
            implements org.vpda.common.util.Builder<MappedSuperIdentifiedTypeImpl<X>> {

        public MappedSuperIdentifiedTypeImplTypeBuilder() {
            super.setDtoType(DTOType.MAPPED_SUPERCLASS);
        }

        @Override
        public MappedSuperIdentifiedTypeImpl<X> build() {
            return new MappedSuperIdentifiedTypeImpl<X>(this);
        }

        @Override
        public MappedSuperIdentifiedTypeImplTypeBuilder<X> setValues(MappedSuperIdentifiedTypeImpl<X> type) {
            super.setValues(type);
            return this;
        }
    }
}
