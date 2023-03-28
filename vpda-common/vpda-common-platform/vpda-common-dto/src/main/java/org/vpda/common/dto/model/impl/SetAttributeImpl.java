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

import java.util.Set;

import org.vpda.common.dto.model.SetAttribute;

public final class SetAttributeImpl<X, E> extends PluralAttributeImpl<X, Set<E>, E> implements SetAttribute<X, E> {
    @SuppressWarnings("unchecked")
    private SetAttributeImpl(SetAttributeImplBuilder<X, E> builder) {
        super((PluralAttributeImpl.PluralAttributeBuilder) builder);
    }

    @Override
    public CollectionType getCollectionType() {
        return CollectionType.SET;
    }

    @Override
    public SetAttributeImplBuilder<X, E> createBuilder() {
        return new SetAttributeImplBuilder<X, E>();
    }

    public static final class SetAttributeImplBuilder<X, E> extends PluralAttributeImpl.PluralAttributeBuilder<X, Set<E>, E, SetAttributeImpl<X, E>> {

        @SuppressWarnings("unchecked")
        public SetAttributeImplBuilder() {
            setJavaType((Class) Set.class);
        }

        @Override
        public SetAttributeImpl<X, E> build() {
            return new SetAttributeImpl<X, E>(this);
        }

    }
}
