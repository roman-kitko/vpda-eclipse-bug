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

import java.util.List;

import org.vpda.common.dto.model.ListAttribute;

public final class ListAttributeImpl<X, E> extends PluralAttributeImpl<X, List<E>, E> implements ListAttribute<X, E> {
    @SuppressWarnings("unchecked")
    private ListAttributeImpl(ListAttributeImplBuilder<X, E> builder) {
        super((PluralAttributeImpl.PluralAttributeBuilder) builder);
    }

    @Override
    public CollectionType getCollectionType() {
        return CollectionType.LIST;
    }

    @Override
    public ListAttributeImplBuilder<X, E> createBuilder() {
        return new ListAttributeImplBuilder<X, E>();
    }

    public static final class ListAttributeImplBuilder<X, E> extends PluralAttributeImpl.PluralAttributeBuilder<X, List<E>, E, ListAttributeImpl<X, E>> {

        @SuppressWarnings("unchecked")
        public ListAttributeImplBuilder() {
            setJavaType((Class) List.class);
        }

        @Override
        public ListAttributeImpl<X, E> build() {
            return new ListAttributeImpl<X, E>(this);
        }

        @Override
        public PluralAttributeBuilder setValues(ListAttributeImpl<X, E> attr) {
            return super.setValues(attr);
        }

    }
}
