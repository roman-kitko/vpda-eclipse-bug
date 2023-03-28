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

import org.vpda.common.dto.model.AttributeType;
import org.vpda.common.dto.model.SingleAttribute;
import org.vpda.common.dto.model.Type;
import org.vpda.common.util.Builder;

public final class SingleAttributeImpl<X, Y> extends AbstractAttribute<X, Y> implements SingleAttribute<X, Y> {

    @SuppressWarnings("unchecked")
    private SingleAttributeImpl(SingleAttributeImplBuilder<X, Y> builder) {
        super((AbstractAttribute.AbstractAttributeBuilder) builder);
        this.type = builder.getType();
        this.isId = builder.isId();
        this.isVersion = builder.isVersion();
        this.isExternalId = builder.isExternalId();
    }

    private final Type<X> type;
    private final boolean isId;
    private final boolean isVersion;
    private final boolean isExternalId;

    @Override
    public Type<X> getType() {
        return type;
    }

    @Override
    public boolean isId() {
        return isId;
    }

    @Override
    public boolean isVersion() {
        return isVersion;
    }

    @Override
    public boolean isExternalId() {
        return isExternalId;
    }

    @Override
    public SingleAttributeImplBuilder<X, Y> createBuilder() {
        return new SingleAttributeImplBuilder<X, Y>();
    }

    public static final class SingleAttributeImplBuilder<X, Y> extends AbstractAttribute.AbstractAttributeBuilder<X, Y, SingleAttributeImpl<X, Y>> implements Builder<SingleAttributeImpl<X, Y>> {

        private Type<X> type;
        private boolean isId;
        private boolean isVersion;
        private boolean isExternalId;

        public SingleAttributeImplBuilder() {
            setAttributeType(AttributeType.SINGLE_BASIC);
        }

        @Override
        public SingleAttributeImplBuilder<X, Y> setValues(SingleAttributeImpl<X, Y> attr) {
            super.setValues(attr);
            this.type = attr.getType();
            this.isId = attr.isId();
            this.isVersion = attr.isVersion();
            this.isExternalId = attr.isExternalId();
            return this;
        }

        public Type<X> getType() {
            return type;
        }

        public SingleAttributeImplBuilder<X, Y> setType(Type<X> type) {
            this.type = type;
            return this;
        }

        public boolean isId() {
            return isId;
        }

        public SingleAttributeImplBuilder<X, Y> setId(boolean isId) {
            this.isId = isId;
            return this;
        }

        public boolean isVersion() {
            return isVersion;
        }

        public SingleAttributeImplBuilder<X, Y> setVersion(boolean isVersion) {
            this.isVersion = isVersion;
            return this;
        }

        public boolean isExternalId() {
            return isExternalId;
        }

        public SingleAttributeImplBuilder<X, Y> setExternalId(boolean isExternalId) {
            this.isExternalId = isExternalId;
            return this;
        }

        @Override
        public SingleAttributeImpl<X, Y> build() {
            return new SingleAttributeImpl<X, Y>(this);
        }

    }

}
