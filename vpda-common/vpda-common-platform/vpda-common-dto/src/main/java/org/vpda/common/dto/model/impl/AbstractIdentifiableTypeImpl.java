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

import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.IdentifiableType;

public abstract class AbstractIdentifiableTypeImpl<X> extends AbstractManagedType<X> implements IdentifiableType<X> {

    protected <A extends AbstractIdentifiableTypeImpl<X>> AbstractIdentifiableTypeImpl(AbstractIdentifiableTypeImplBuilder<X, A> builder) {
        super(builder);
        this.id = builder.getId();
        this.externalId = builder.getExternalId();
    }

    private final Attribute<X, ?> id;
    private final Attribute<X, ?> externalId;

    @SuppressWarnings("unchecked")
    @Override
    public IdentifiableType<? super X> getSuperType() {
        return (IdentifiableType<? super X>) super.getSuperType();
    }

    @Override
    public Attribute<X, ?> getIdAttribute() {
        return id;
    }

    @Override
    public Attribute<X, ?> getExternalIdAttribute() {
        return externalId;
    }

    public abstract static class AbstractIdentifiableTypeImplBuilder<X, A extends AbstractIdentifiableTypeImpl<X>> extends AbstractManagedType.AbstractManagedTypeBuilder<X, A>
            implements org.vpda.common.util.Builder<A> {
        private Attribute<X, ?> id;
        private Attribute<X, ?> externalId;

        @SuppressWarnings("unchecked")
        @Override
        public AbstractIdentifiableTypeImplBuilder setValues(A type) {
            super.setValues(type);
            this.id = ((AbstractIdentifiableTypeImpl) type).id;
            this.externalId = ((AbstractIdentifiableTypeImpl) type).externalId;
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public IdentifiableType<? super X> getSuperType() {
            return (IdentifiableType<? super X>) super.getSuperType();
        }

        public Attribute<X, ?> getId() {
            return id;
        }

        public AbstractIdentifiableTypeImplBuilder setId(Attribute<X, ?> id) {
            this.id = id;
            return this;
        }

        public Attribute<X, ?> getExternalId() {
            return externalId;
        }

        public AbstractIdentifiableTypeImplBuilder setExternalId(Attribute<X, ?> externalId) {
            this.externalId = externalId;
            return this;
        }

    }

}
