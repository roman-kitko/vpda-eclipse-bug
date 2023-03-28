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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.ListAttribute;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.MapAttribute;
import org.vpda.common.dto.model.SetAttribute;
import org.vpda.common.dto.model.SingleAttribute;
import org.vpda.common.dto.model.impl.AbstractAttribute.AbstractAttributeBuilder;

public abstract class AbstractManagedType<X> extends AbstractTypeImpl<X> implements ManagedType<X> {

    private final Map<String, Attribute<? super X, ?>> allAttributes;
    private final Map<String, Attribute<X, ?>> declaredAttributes;
    private final ManagedType<?> superType;

    @SuppressWarnings("unchecked")
    protected <A extends AbstractManagedType<X>> AbstractManagedType(AbstractManagedTypeBuilder<X, A> builder) {
        super(builder);
        this.superType = builder.getSuperType();
        this.declaredAttributes = new HashMap<>(builder.getDeclaredAttributes());

        for (Map.Entry<String, Attribute<X, ?>> entry : builder.getDeclaredAttributes().entrySet()) {
            if (entry.getValue() instanceof AbstractAttribute) {
                AbstractAttributeBuilder attrBuilder = ((AbstractAttribute) entry.getValue()).createBuilder();
                AbstractAttribute attr = (AbstractAttribute) attrBuilder.setValues((AbstractAttribute) entry.getValue()).setDeclaringType(this).build();
                declaredAttributes.put(attr.getName(), attr);
            }
        }
        this.allAttributes = new HashMap<>(this.declaredAttributes);
        if (this.superType != null) {
            this.allAttributes.putAll((Map) this.superType.getAttributes().stream().collect(Collectors.toMap((attr -> attr.getName()), attr -> attr)));
        }

    }

    @Override
    public ManagedType<?> getSuperType() {
        return superType;
    }

    @Override
    public Collection<Attribute<? super X, ?>> getAttributes() {
        return Collections.unmodifiableCollection(allAttributes.values());
    }

    @Override
    public Collection<Attribute<X, ?>> getDeclaredAttributes() {
        return Collections.unmodifiableCollection(declaredAttributes.values());
    }

    @Override
    public Attribute<? super X, ?> getAttribute(String name) {
        return allAttributes.get(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Y> SingleAttribute<? super X, Y> getSingleAttribute(String name, Class<Y> javaType) {
        Attribute<? super X, ?> attr = getAttribute(name);
        if (attr == null) {
            return null;
        }
        if (!(attr instanceof SingleAttribute)) {
            throw new IllegalArgumentException("Requested attribute " + name + " is not Single attribute, but is " + attr.getAttributeType());
        }
        if (javaType.isAssignableFrom(attr.getJavaType())) {
            return (SingleAttribute<? super X, Y>) attr;
        }
        throw new IllegalArgumentException("Attribute " + attr.getName() + " is of type " + attr.getName() + " which is not compatibe with requested : " + javaType.getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> SetAttribute<? super X, E> getSetAttribute(String name, Class<E> elementType) {
        Attribute<? super X, ?> attr = getAttribute(name);
        if (attr == null) {
            return null;
        }
        if (!(attr instanceof SetAttribute)) {
            throw new IllegalArgumentException("Requested attribute " + name + " is not Set attribute, but is " + attr.getAttributeType());
        }
        SetAttribute setAttr = (SetAttribute) attr;
        if (!elementType.isAssignableFrom(setAttr.getJavaElementType())) {
            throw new IllegalArgumentException(
                    "Requested set attribute " + name + " has element java type " + setAttr.getJavaElementType() + " which is not compatible with requested type " + elementType);
        }
        return setAttr;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> ListAttribute<? super X, E> getListAttribute(String name, Class<E> elementType) {
        Attribute<? super X, ?> attr = getAttribute(name);
        if (attr == null) {
            return null;
        }
        if (!(attr instanceof ListAttribute)) {
            throw new IllegalArgumentException("Requested attribute " + name + " is not List attribute, but is " + attr.getAttributeType());
        }
        ListAttribute listAttr = (ListAttribute) attr;
        if (!elementType.isAssignableFrom(listAttr.getJavaElementType())) {
            throw new IllegalArgumentException(
                    "Requested list attribute " + name + " has element java type " + listAttr.getJavaElementType() + " which is not compatible with requested type " + elementType);
        }
        return listAttr;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> MapAttribute<? super X, K, V> getMapAttribute(String name, Class<K> keyType, Class<V> valueType) {
        Attribute<? super X, ?> attr = getAttribute(name);
        if (attr == null) {
            return null;
        }
        if (!(attr instanceof MapAttribute)) {
            throw new IllegalArgumentException("Requested attribute " + name + " is not Map attribute, but is " + attr.getAttributeType());
        }
        @SuppressWarnings("rawtypes")
        MapAttribute mapAttr = (MapAttribute) attr;
        if (!keyType.isAssignableFrom(mapAttr.getKeyJavaType())) {
            throw new IllegalArgumentException("Requested map attribute " + name + " has key java type " + mapAttr.getKeyJavaType() + " which is not compatible with requested type " + keyType);
        }
        if (!valueType.isAssignableFrom(mapAttr.getValueJavaType())) {
            throw new IllegalArgumentException("Requested map attribute " + name + " has value java type " + mapAttr.getValueJavaType() + " which is not compatible with requested type " + valueType);
        }
        return mapAttr;
    }

    @Override
    public Attribute<X, ?> getDeclaredAttribute(String name) {
        return declaredAttributes.get(name);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <Y> SingleAttribute<X, Y> getDeclaredSingleAttribute(String name, Class<Y> javaType){
        Attribute<X, ?> attribute = declaredAttributes.get(name);
        if(javaType.isAssignableFrom(attribute.getJavaType())) {
            return (SingleAttribute<X, Y>) attribute;
        }
        throw new ClassCastException(String.format("Given attribute with java type %s is not compatible with requested type %s", attribute.getJavaType(), javaType));
    }

    public abstract static class AbstractManagedTypeBuilder<X, A extends AbstractManagedType<X>> extends AbstractTypeImpl.AbstractTypeImplBuilder<X, A> implements org.vpda.common.util.Builder<A> {
        private Map<String, Attribute<X, ?>> declaredAttributes = new HashMap<>();
        private ManagedType<?> superType;

        @SuppressWarnings("unchecked")
        @Override
        public AbstractManagedTypeBuilder setValues(A type) {
            super.setValues(type);
            this.declaredAttributes = new HashMap<>(((AbstractManagedType) type).declaredAttributes);
            this.superType = type.getSuperType();
            return this;
        }

        public Map<String, Attribute<X, ?>> getDeclaredAttributes() {
            return declaredAttributes;
        }

        public AbstractManagedTypeBuilder setDeclaredAttributes(Map<String, Attribute<X, ?>> declaredAttributes) {
            this.declaredAttributes = declaredAttributes;
            return this;
        }

        public AbstractManagedTypeBuilder addDeclaredAttribute(Attribute<X, ?> attr) {
            this.declaredAttributes.put(attr.getName(), attr);
            return this;
        }

        public ManagedType<?> getSuperType() {
            return superType;
        }

        public AbstractManagedTypeBuilder setSuperType(ManagedType<?> superType) {
            this.superType = superType;
            return this;
        }

    }

}
