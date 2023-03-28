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
import java.lang.reflect.Type;
import java.util.Map;

import org.vpda.common.dto.PropertyPath;
import org.vpda.common.dto.model.AttributeType;
import org.vpda.common.dto.model.IdentifiableType;
import org.vpda.common.dto.model.ListAttribute;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.MapAttribute;
import org.vpda.common.dto.model.ReferenceType;
import org.vpda.common.dto.model.SetAttribute;
import org.vpda.common.dto.model.impl.BasicTypeImpl;
import org.vpda.common.dto.model.impl.ListAttributeImpl;
import org.vpda.common.dto.model.impl.MapAttributeImpl;
import org.vpda.common.dto.model.impl.ReferenceTypeImpl;
import org.vpda.common.dto.model.impl.SetAttributeImpl;
import org.vpda.common.types.IdentifiedObjectReference;

final class CollectionAttributeBuilder {

    private final DTOModelBuilder modelBuilder;

    CollectionAttributeBuilder(DTOModelBuilder modelBuilder) {
        super();
        this.modelBuilder = modelBuilder;
    }

    @SuppressWarnings("unchecked")
    ListAttribute<?, ?> createListAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap, PropertyPath rootPath) {
        ListAttributeImpl.ListAttributeImplBuilder builder = new ListAttributeImpl.ListAttributeImplBuilder();
        builder.setCollection(true).setDeclaringType(declaringType).setJavaMember(field).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        if (field.getGenericType() instanceof ParameterizedType) {
            if (((ParameterizedType) field.getGenericType()).getActualTypeArguments().length != 1) {
                throw new IllegalArgumentException("List field " + field + " must have exactly 1 generic type param");
            }
            Type paramType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            Class paramRawType = null;
            if (paramType instanceof ParameterizedType) {
                paramRawType = (Class) ((ParameterizedType) paramType).getRawType();
            }
            else if (paramType instanceof Class) {
                paramRawType = (Class) paramType;
            }
            else {
                throw new IllegalArgumentException("List field " + field + " must have exactly 1 generic type param with type class");
            }
            if (modelBuilder.isBasicOrPrimitiveTypeOrEnum(paramRawType)) {
                builder.setElementType(new BasicTypeImpl(paramRawType));
                builder.setAttributeType(AttributeType.COLLECTION_BASIC);
            }
            else if (IdentifiedObjectReference.class.isAssignableFrom(paramRawType)) {
                Class listType = (Class) ((ParameterizedType) paramType).getActualTypeArguments()[0];
                ManagedType referenceType = modelBuilder.createManagedType(listType, managedClassesMap);
                if (!(referenceType instanceof IdentifiableType)) {
                    throw new IllegalArgumentException("Reference attribute " + field.getName() + " in declaringType " + declaringType.getJavaType() + " must point to IdentifiableType type");
                }
                ReferenceType<?, ?> type = new ReferenceTypeImpl(paramRawType, (IdentifiableType) referenceType);
                builder.setElementType(type);
                builder.setAttributeType(AttributeType.COLLECTION_REFERENCE);
            }
            else {
                ManagedType<?> managedType = modelBuilder.createManagedType(paramRawType, managedClassesMap);
                builder.setElementType(managedType);
                builder.setAttributeType(AttributeType.COLLECTION_ASSOCIATION);
            }
            return builder.build();
        }
        else {
            throw new IllegalArgumentException("List field " + field + " must specify list type");
        }
    }

    @SuppressWarnings("unchecked")
    SetAttribute<?, ?> createSetAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap, PropertyPath rootPath) {
        SetAttributeImpl.SetAttributeImplBuilder builder = new SetAttributeImpl.SetAttributeImplBuilder();
        builder.setCollection(true).setDeclaringType(declaringType).setJavaMember(field).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        if (field.getGenericType() instanceof ParameterizedType) {
            if (((ParameterizedType) field.getGenericType()).getActualTypeArguments().length != 1) {
                throw new IllegalArgumentException("Set field " + field + " must have exactly 1 generic type param");
            }
            Type paramType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            Class paramRawType = null;
            if (paramType instanceof ParameterizedType) {
                paramRawType = (Class) ((ParameterizedType) paramType).getRawType();
            }
            else if (paramType instanceof Class) {
                paramRawType = (Class) paramType;
            }
            else {
                throw new IllegalArgumentException("Set field " + field + " must have exactly 1 generic type param with type class");
            }
            if (modelBuilder.isBasicOrPrimitiveTypeOrEnum(paramRawType)) {
                builder.setElementType(new BasicTypeImpl(paramRawType));
                builder.setAttributeType(AttributeType.COLLECTION_BASIC);
            }
            else if (IdentifiedObjectReference.class.isAssignableFrom(paramRawType)) {
                Class listType = (Class) ((ParameterizedType) paramType).getActualTypeArguments()[0];
                ManagedType referenceType = modelBuilder.createManagedType(listType, managedClassesMap);
                if (!(referenceType instanceof IdentifiableType)) {
                    throw new IllegalArgumentException("Reference attribute " + field.getName() + " in declaringType " + declaringType.getJavaType() + " must point to IdentifiableType type");
                }
                ReferenceType<?, ?> type = new ReferenceTypeImpl(paramRawType, (IdentifiableType) referenceType);
                builder.setElementType(type);
                builder.setAttributeType(AttributeType.COLLECTION_REFERENCE);
            }
            else {
                ManagedType<?> managedType = modelBuilder.createManagedType(paramRawType, managedClassesMap);
                builder.setElementType(managedType);
                builder.setAttributeType(AttributeType.COLLECTION_ASSOCIATION);
            }
            return builder.build();
        }
        else {
            throw new IllegalArgumentException("Set field " + field + " must specify set type");
        }
    }

    @SuppressWarnings("unchecked")
    MapAttribute<?, ?, ?> createMapAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap, PropertyPath rootPath) {
        MapAttributeImpl.MapAttributeImplBuilder builder = new MapAttributeImpl.MapAttributeImplBuilder();
        builder.setCollection(true).setDeclaringType(declaringType).setJavaMember(field).setName(field.getName());
        builder.setPath(rootPath.createChild(field.getName()));
        if (field.getGenericType() instanceof ParameterizedType) {
            if (((ParameterizedType) field.getGenericType()).getActualTypeArguments().length != 2) {
                throw new IllegalArgumentException("Map field " + field + " must have exactly 2 generic type params");
            }
            if (!(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] instanceof Class)) {
                throw new IllegalArgumentException("Map field " + field + " must have key type param with type class");
            }
            Class keyType = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            if (modelBuilder.isBasicOrPrimitiveTypeOrEnum(keyType)) {
                builder.setKeyType(new BasicTypeImpl<>(keyType));
            }
            else {
                throw new IllegalArgumentException("Map field " + field + " must have basic key type");
            }
            Type valueType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];
            Class valueRawType = null;
            if (valueType instanceof ParameterizedType) {
                valueRawType = (Class) ((ParameterizedType) valueType).getRawType();
            }
            else if (valueType instanceof Class) {
                valueRawType = (Class) valueType;
            }
            else {
                throw new IllegalArgumentException("Map field " + field + " must have exactly generic type value param with type class");
            }
            if (modelBuilder.isBasicOrPrimitiveTypeOrEnum(valueRawType)) {
                builder.setElementType(new BasicTypeImpl(valueRawType));
                builder.setAttributeType(AttributeType.COLLECTION_BASIC);
            }
            else if (IdentifiedObjectReference.class.isAssignableFrom(valueRawType)) {
                Class mapType = (Class) ((ParameterizedType) valueType).getActualTypeArguments()[0];
                ManagedType referenceType = modelBuilder.createManagedType(mapType, managedClassesMap);
                if (!(referenceType instanceof IdentifiableType)) {
                    throw new IllegalArgumentException("Reference attribute " + field.getName() + " in declaringType " + declaringType.getJavaType() + " must point to IdentifiableType type");
                }
                ReferenceType<?, ?> type = new ReferenceTypeImpl(valueRawType, (IdentifiableType) referenceType);
                builder.setElementType(type);
                builder.setAttributeType(AttributeType.COLLECTION_REFERENCE);
            }
            else {
                ManagedType<?> managedType = modelBuilder.createManagedType(valueRawType, managedClassesMap);
                builder.setElementType(managedType);
                builder.setAttributeType(AttributeType.COLLECTION_ASSOCIATION);
            }
            return builder.build();
        }
        else {
            throw new IllegalArgumentException("Map field " + field + " must specify map type");
        }
    }

}
