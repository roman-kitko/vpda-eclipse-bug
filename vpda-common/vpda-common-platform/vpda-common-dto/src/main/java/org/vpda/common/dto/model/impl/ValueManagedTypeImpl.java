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
import java.util.Collections;

import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.DTOType;
import org.vpda.common.dto.model.ListAttribute;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.MapAttribute;
import org.vpda.common.dto.model.SetAttribute;
import org.vpda.common.dto.model.SingleAttribute;
import org.vpda.common.dto.model.ValueManagedType;

public final class ValueManagedTypeImpl<X> implements ValueManagedType<X> {

    private final Class<X> javaClass;
    private final ManagedType<?> superType;

    public ValueManagedTypeImpl(Class<X> javaClass, ManagedType<?> superType) {
        this.javaClass = javaClass;
        this.superType = superType;
    }

    public ValueManagedTypeImpl(Class<X> javaClass) {
        this(javaClass, null);
    }

    @Override
    public Collection<Attribute<? super X, ?>> getAttributes() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Attribute<X, ?>> getDeclaredAttributes() {
        return Collections.emptySet();
    }

    @Override
    public Attribute<? super X, ?> getAttribute(String name) {
        return null;
    }

    @Override
    public <Y> SingleAttribute<? super X, Y> getSingleAttribute(String name, Class<Y> javaType) {
        return null;
    }

    @Override
    public <E> SetAttribute<? super X, E> getSetAttribute(String name, Class<E> elementType) {
        return null;
    }

    @Override
    public <E> ListAttribute<? super X, E> getListAttribute(String name, Class<E> elementType) {
        return null;
    }

    @Override
    public <K, V> MapAttribute<? super X, K, V> getMapAttribute(String name, Class<K> keyType, Class<V> valueType) {
        return null;
    }

    @Override
    public Attribute<X, ?> getDeclaredAttribute(String name) {
        return null;
    }

    @Override
    public ManagedType<?> getSuperType() {
        return superType;
    }

    @Override
    public DTOType getDTOType() {
        return DTOType.VALUE;
    }

    @Override
    public Class<X> getJavaType() {
        return javaClass;
    }

    @Override
    public <Y> SingleAttribute<X, Y> getDeclaredSingleAttribute(String name, Class<Y> javaType) {
        return null;
    }

}
