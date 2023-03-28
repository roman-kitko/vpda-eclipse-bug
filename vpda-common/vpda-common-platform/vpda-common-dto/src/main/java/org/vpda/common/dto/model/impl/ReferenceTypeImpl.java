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
import org.vpda.common.dto.model.IdentifiableType;
import org.vpda.common.dto.model.ReferenceType;
import org.vpda.common.types.IdentifiedObjectReference;
import org.vpda.common.util.Builder;
import org.vpda.internal.common.util.Assert;

public final class ReferenceTypeImpl<X, Y extends IdentifiedObjectReference<?>> implements ReferenceType<X, Y> {

    private final Class<Y> referenceTypeClass; // Either Basic or persistence reference
    private final IdentifiableType<X> reference;

    public ReferenceTypeImpl(Class<Y> referenceTypeClass, IdentifiableType<X> reference) {
        this.referenceTypeClass = Assert.isNotNullArgument(referenceTypeClass, "referenceTypeClass");
        this.reference = Assert.isNotNullArgument(reference, "reference");
        ;
    }

    private ReferenceTypeImpl(ReferenceTypeImplBuilder<X, Y> builder) {
        this.referenceTypeClass = Assert.isNotNullArgument(builder.getReferenceTypeClass(), "referenceTypeClass");
        this.reference = Assert.isNotNullArgument(builder.getReference(), "reference");
        ;
    }

    @Override
    public DTOType getDTOType() {
        return DTOType.REFERENCE;
    }

    @Override
    public Class<Y> getJavaType() {
        return referenceTypeClass;
    }

    @Override
    public IdentifiableType<X> getRefereceToType() {
        return reference;
    }

    public static final class ReferenceTypeImplBuilder<X, Y extends IdentifiedObjectReference<?>> implements Builder<ReferenceType<X, Y>> {
        private Class<Y> referenceTypeClass; // Either Basic or persistence reference
        private IdentifiableType<X> reference;

        public Class<Y> getReferenceTypeClass() {
            return referenceTypeClass;
        }

        public ReferenceTypeImplBuilder setReferenceTypeClass(Class<Y> referenceTypeClass) {
            this.referenceTypeClass = referenceTypeClass;
            return this;
        }

        public IdentifiableType<X> getReference() {
            return reference;
        }

        public ReferenceTypeImplBuilder setReference(IdentifiableType<X> reference) {
            this.reference = reference;
            return this;
        }

        @Override
        public ReferenceType<X, Y> build() {
            return new ReferenceTypeImpl<X, Y>(this);
        }

    }

}
