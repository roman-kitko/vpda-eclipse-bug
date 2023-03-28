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

import java.util.Map;

import org.vpda.common.dto.model.MapAttribute;
import org.vpda.common.dto.model.Type;

public final class MapAttributeImpl<X, K, V> extends PluralAttributeImpl<X, Map<K, V>, V> implements MapAttribute<X, K, V> {

    @SuppressWarnings("unchecked")
    private MapAttributeImpl(MapAttributeImplBuilder<X, K, V> builder) {
        super((PluralAttributeImpl.PluralAttributeBuilder) builder);
        this.keyType = builder.getKeyType();
    }

    private final Type<K> keyType;

    @Override
    public CollectionType getCollectionType() {
        return CollectionType.MAP;
    }

    @Override
    public Class<K> getKeyJavaType() {
        return keyType.getJavaType();
    }

    @Override
    public Type<K> getKeyType() {
        return keyType;
    }

    @Override
    public Class<V> getValueJavaType() {
        return getElementType().getJavaType();
    }

    @Override
    public MapAttributeImplBuilder<X, K, V> createBuilder() {
        return new MapAttributeImplBuilder<X, K, V>();
    }

    public static final class MapAttributeImplBuilder<X, K, V> extends PluralAttributeImpl.PluralAttributeBuilder<X, Map<K, V>, V, MapAttributeImpl<X, K, V>> {
        private Type<K> keyType;

        @SuppressWarnings("unchecked")
        public MapAttributeImplBuilder() {
            setJavaType((Class) Map.class);
        }

        public Type<K> getKeyType() {
            return keyType;
        }

        public MapAttributeImplBuilder setKeyType(Type<K> keyType) {
            this.keyType = keyType;
            return this;
        }

        @Override
        public MapAttributeImpl<X, K, V> build() {
            return new MapAttributeImpl<X, K, V>(this);
        }

        @Override
        public PluralAttributeBuilder setValues(MapAttributeImpl<X, K, V> attr) {
            this.keyType = attr.getKeyType();
            return super.setValues(attr);
        }

    }

}
