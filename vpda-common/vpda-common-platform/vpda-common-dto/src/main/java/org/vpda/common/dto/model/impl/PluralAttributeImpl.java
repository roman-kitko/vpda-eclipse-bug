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
import org.vpda.common.dto.model.PluralAttribute;
import org.vpda.common.dto.model.Type;

public abstract class PluralAttributeImpl<X, C, E> extends AbstractAttribute<X, C> implements PluralAttribute<X, C, E> {

    @SuppressWarnings("unchecked")
    protected PluralAttributeImpl(PluralAttributeBuilder<X, C, E, PluralAttributeImpl<X, C, E>> builder) {
        super((AbstractAttribute.AbstractAttributeBuilder) builder);
        this.elementType = builder.getElementType();
    }

    private final Type<E> elementType;

    @Override
    public Type<E> getElementType() {
        return elementType;
    }

    @Override
    public Class<E> getJavaElementType() {
        return getElementType().getJavaType();
    }

    public static abstract class PluralAttributeBuilder<X, C, E, F extends PluralAttributeImpl<X, C, E>> extends AbstractAttribute.AbstractAttributeBuilder<X, C, F> {
        private Type<E> elementType;

        public PluralAttributeBuilder() {
            setAttributeType(AttributeType.COLLECTION_BASIC);
        }

        public Type<E> getElementType() {
            return elementType;
        }

        public PluralAttributeBuilder setElementType(Type<E> elementType) {
            this.elementType = elementType;
            return this;
        }

        @Override
        public PluralAttributeBuilder setValues(F attr) {
            this.elementType = attr.getElementType();
            super.setValues(attr);
            return this;
        }
    }

}
