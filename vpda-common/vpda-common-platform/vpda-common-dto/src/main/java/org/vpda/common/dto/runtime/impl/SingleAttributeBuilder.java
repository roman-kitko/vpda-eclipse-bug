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
package org.vpda.common.dto.runtime.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.vpda.common.dto.PropertyPath;
import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.AttributeType;
import org.vpda.common.dto.model.EmbeddableType;
import org.vpda.common.dto.model.IdentifiableType;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.ReferenceType;
import org.vpda.common.dto.model.ValueManagedType;
import org.vpda.common.dto.model.impl.BasicTypeImpl;
import org.vpda.common.dto.model.impl.ReferenceTypeImpl;
import org.vpda.common.dto.model.impl.SingleAttributeImpl.SingleAttributeImplBuilder;

final class SingleAttributeBuilder {

    private final DTOModelBuilder modelBuilder;

    SingleAttributeBuilder(DTOModelBuilder modelBuilder) {
        super();
        this.modelBuilder = modelBuilder;
    }

    @SuppressWarnings("unchecked")
    Attribute<?, ?> createSingleAssocationAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap, PropertyPath rootPath) {
        ManagedType<?> managedType = modelBuilder.createManagedType(field.getType(), managedClassesMap);
        SingleAttributeImplBuilder builder = new SingleAttributeImplBuilder();
        builder.setJavaMember(field).setDeclaringType(declaringType).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        builder.setType(managedType).setAttributeType(AttributeType.SINGLE_ASSOCIATION);
        builder.setJavaType(field.getType());
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    Attribute<?, ?> createBasicAttribute(Field field, ManagedType declaringType, PropertyPath rootPath) {
        SingleAttributeImplBuilder builder = new SingleAttributeImplBuilder();
        builder.setJavaMember(field).setDeclaringType(declaringType).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        builder.setType(new BasicTypeImpl<>(field.getType())).setAttributeType(AttributeType.SINGLE_BASIC);
        builder.setJavaType(field.getType());
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    Attribute<?, ?> createReferenceAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap, PropertyPath rootPath) {
        SingleAttributeImplBuilder builder = new SingleAttributeImplBuilder();
        builder.setJavaMember(field).setDeclaringType(declaringType).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        if (((ParameterizedType) field.getGenericType()).getActualTypeArguments().length != 1) {
            throw new IllegalArgumentException("Reference field " + field + " must have exactly 1 generic type param");
        }
        if (!(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] instanceof Class)) {
            throw new IllegalArgumentException("Reference field " + field + " must have exactly 1 generic type param with type class");
        }
        Class referenceManagedType = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        ManagedType referenceType = modelBuilder.createManagedType(referenceManagedType, managedClassesMap);
        if (!(referenceType instanceof IdentifiableType)) {
            throw new IllegalArgumentException("Reference attribute " + field.getName() + " in declaringType " + declaringType.getJavaType() + " must point to IdentifiableType type");
        }
        ReferenceType<?, ?> type = new ReferenceTypeImpl(field.getType(), (IdentifiableType) referenceType);
        builder.setType(type).setAttributeType(AttributeType.SINGLE_REFERENCE);
        builder.setJavaType(field.getType());
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    Attribute<?, ?> createEmbeddedAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap, PropertyPath rootPath) {
        EmbeddableType<?> embType = modelBuilder.createEmbeddableType(field.getType(), managedClassesMap);
        SingleAttributeImplBuilder builder = new SingleAttributeImplBuilder();
        builder.setAttributeType(AttributeType.SINGLE_EMBEDDED);
        builder.setDeclaringType(declaringType);
        builder.setType(embType);
        builder.setJavaMember(field);
        builder.setPath(rootPath.createChild(field.getName()));
        builder.setJavaType(field.getType());
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    Attribute<?, ?> createExternalIdAttribute(Field field, ManagedType declaringType, PropertyPath rootPath) {
        SingleAttributeImplBuilder builder = new SingleAttributeImplBuilder();
        builder.setJavaMember(field).setDeclaringType(declaringType).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        builder.setExternalId(true);
        builder.setType(new BasicTypeImpl<>(field.getType())).setAttributeType(AttributeType.SINGLE_BASIC);
        builder.setJavaType(field.getType());
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    Attribute<?, ?> createIdAttribute(Field field, ManagedType declaringType, PropertyPath rootPath) {
        SingleAttributeImplBuilder builder = new SingleAttributeImplBuilder();
        builder.setJavaMember(field).setDeclaringType(declaringType).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        builder.setId(true);
        builder.setType(new BasicTypeImpl<>(field.getType())).setAttributeType(AttributeType.SINGLE_BASIC);
        builder.setJavaType(field.getType());
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    Attribute<?, ?> createValueAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap, PropertyPath rootPath) {
        SingleAttributeImplBuilder builder = new SingleAttributeImplBuilder();
        builder.setJavaMember(field).setDeclaringType(declaringType).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        builder.setJavaType(field.getType());
        builder.setAttributeType(AttributeType.SINGLE_VALUE);
        ValueManagedType<?> valueType = modelBuilder.createValueType(field.getType(), managedClassesMap);
        builder.setType(valueType);
        return builder.build();

    }

}
