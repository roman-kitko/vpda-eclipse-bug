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

import java.util.Collection;

import org.vpda.common.dto.model.CollectionAttribute;

public final class CollectionAttributeImpl<X, E> extends PluralAttributeImpl<X, Collection<E>, E> implements CollectionAttribute<X, E> {
    @SuppressWarnings("unchecked")
    private CollectionAttributeImpl(CollectionAttributeImplBuilder<X, E> builder) {
        super((PluralAttributeImpl.PluralAttributeBuilder) builder);
    }

    @Override
    public CollectionType getCollectionType() {
        return CollectionType.COLLECTION;
    }

    @Override
    public CollectionAttributeImplBuilder<X, E> createBuilder() {
        return new CollectionAttributeImplBuilder<X, E>();
    }

    public static final class CollectionAttributeImplBuilder<X, E> extends PluralAttributeImpl.PluralAttributeBuilder<X, Collection<E>, E, CollectionAttributeImpl<X, E>> {

        @SuppressWarnings("unchecked")
        public CollectionAttributeImplBuilder() {
            setJavaType((Class) Collection.class);
        }

        @Override
        public CollectionAttributeImpl<X, E> build() {
            return new CollectionAttributeImpl<X, E>(this);
        }

        @Override
        public PluralAttributeBuilder setValues(CollectionAttributeImpl<X, E> attr) {
            return super.setValues(attr);
        }

    }
}
