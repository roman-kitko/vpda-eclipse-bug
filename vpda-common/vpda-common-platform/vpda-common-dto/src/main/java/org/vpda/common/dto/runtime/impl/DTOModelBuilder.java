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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.vpda.common.dto.PropertyPath;
import org.vpda.common.dto.annotations.DTOConcrete;
import org.vpda.common.dto.annotations.DTOEmbeddable;
import org.vpda.common.dto.annotations.DTOEmbeddedA;
import org.vpda.common.dto.annotations.DTOEntity;
import org.vpda.common.dto.annotations.DTOMappedSuperIdentifiableClass;
import org.vpda.common.dto.annotations.DTOValue;
import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.ConcreteManagedType;
import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.EmbeddableType;
import org.vpda.common.dto.model.EntityType;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.MappedSuperIdentifiedType;
import org.vpda.common.dto.model.ValueManagedType;
import org.vpda.common.dto.model.impl.DTOMetaModelImpl;
import org.vpda.common.dto.runtime.DTOModelConfiguration;
import org.vpda.common.dto.runtime.DTORepository;
import org.vpda.common.types.ExternalId;
import org.vpda.common.types.Id;
import org.vpda.common.types.IdentifiedObjectReference;

final class DTOModelBuilder {

    private static final Set<Class<?>> BASIC_TYPES = new HashSet<>(Arrays.asList(Boolean.class, String.class, BigDecimal.class, Long.class, Integer.class, Short.class, Double.class, Date.class,
            java.sql.Date.class, Timestamp.class, LocalDate.class, LocalDateTime.class, Instant.class, Locale.class, UUID.class));

    private static final Set<Class<?>> PRIMITIVE_TYPES = new HashSet<>(Arrays.asList(Integer.TYPE, Long.TYPE, Short.TYPE, Double.TYPE, Float.TYPE, Character.TYPE));

    private static final Set<Class<?>> BASIC_AND_PRIMITIVE_TYPES;

    static {
        BASIC_AND_PRIMITIVE_TYPES = new HashSet<>();
        BASIC_AND_PRIMITIVE_TYPES.addAll(BASIC_TYPES);
        BASIC_AND_PRIMITIVE_TYPES.addAll(PRIMITIVE_TYPES);
    }

    private final DTORepository repository;
    private final ManagedTypeBuilder managedTypeBuilder;
    private final SingleAttributeBuilder singleAttributeBuilder;
    private final CollectionAttributeBuilder collectionAttributeBuilder;
    private final DTOModelConfiguration modelConfiguration;

    DTOModelBuilder(DTORepository repository, DTOModelConfiguration modelConfiguration) {
        this.repository = repository;
        this.modelConfiguration = modelConfiguration;
        this.managedTypeBuilder = new ManagedTypeBuilder(this);
        this.singleAttributeBuilder = new SingleAttributeBuilder(this);
        this.collectionAttributeBuilder = new CollectionAttributeBuilder(this);
    }

    DTOModelConfiguration getModelConfiguration() {
        return modelConfiguration;
    }

    DTOMetaModel buildModel() {
        Map<Class<?>, ManagedType<?>> managedClassesMap = new HashMap<>();
        for (Class<?> clazz : repository.getRegisteredClasses()) {
            managedClassesMap.put(clazz, null);
        }
        DTOMetaModelImpl.DTOMetaModelBuilder dTOMetaModelBuilder = new DTOMetaModelImpl.DTOMetaModelBuilder();
        for (Class<?> clazz : repository.getRegisteredClasses()) {
            ManagedType<?> managedType = createManagedType(clazz, managedClassesMap);
            dTOMetaModelBuilder.addManagedType(managedType);
        }
        return dTOMetaModelBuilder.build();
    }


    ManagedType<?> createManagedType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        ManagedType<?> result = null;
        if (clazz.isAnnotationPresent(DTOEntity.class)) {
            result = createEntityType(clazz, managedClassesMap);
        }
        else if (clazz.isAnnotationPresent(DTOEmbeddable.class)) {
            result = createEmbeddableType(clazz, managedClassesMap);
        }
        else if (clazz.isAnnotationPresent(DTOMappedSuperIdentifiableClass.class)) {
            result = createMappedSuperIdentifiedType(clazz, managedClassesMap);
        }
        else if (clazz.isAnnotationPresent(DTOConcrete.class)) {
            result = createConcreteType(clazz, managedClassesMap);
        }
        else if (clazz.isAnnotationPresent(DTOValue.class)) {
            result = createValueType(clazz, managedClassesMap);
        }
        else {
            throw new IllegalArgumentException("Unknown class to process : " + clazz);
        }
        managedClassesMap.put(clazz, result);
        return result;
    }

    ValueManagedType<?> createValueType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        return managedTypeBuilder.createValueType(clazz, managedClassesMap);
    }

    ConcreteManagedType<?> createConcreteType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        return managedTypeBuilder.createConcreteType(clazz, managedClassesMap);
    }

    EntityType<?> createEntityType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        return managedTypeBuilder.createEntityType(clazz, managedClassesMap);
    }

    MappedSuperIdentifiedType<?> createMappedSuperIdentifiedType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        return managedTypeBuilder.createMappedSuperIdentifiedType(clazz, managedClassesMap);
    }

    EmbeddableType<?> createEmbeddableType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        return managedTypeBuilder.createEmbeddableType(clazz, managedClassesMap);
    }

    Attribute<?, ?> createAttribute(Field field, ManagedType declaringType, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        PropertyPath rootPath = PropertyPath.createRoot(declaringType.getJavaType().getSimpleName());
        if (field.isAnnotationPresent(Id.class)) {
            return singleAttributeBuilder.createIdAttribute(field, declaringType, rootPath);
        }
        else if (field.isAnnotationPresent(ExternalId.class)) {
            return singleAttributeBuilder.createExternalIdAttribute(field, declaringType, rootPath);
        }
        if (field.isAnnotationPresent(DTOEmbeddedA.class)) {
            return singleAttributeBuilder.createEmbeddedAttribute(field, declaringType, managedClassesMap, rootPath);
        }
        if (field.isAnnotationPresent(DTOValue.class)) {
            return singleAttributeBuilder.createValueAttribute(field, declaringType, managedClassesMap, rootPath);
        }
        else if (field.getType().equals(List.class)) {
            return collectionAttributeBuilder.createListAttribute(field, declaringType, managedClassesMap, rootPath);
        }
        else if (field.getType().equals(Set.class)) {
            return collectionAttributeBuilder.createSetAttribute(field, declaringType, managedClassesMap, rootPath);
        }
        else if (field.getType().equals(Map.class)) {
            return collectionAttributeBuilder.createMapAttribute(field, declaringType, managedClassesMap, rootPath);
        }
        else if (IdentifiedObjectReference.class.isAssignableFrom(field.getType())) {
            return singleAttributeBuilder.createReferenceAttribute(field, declaringType, managedClassesMap, rootPath);
        }
        else if (isBasicOrPrimitiveTypeOrEnum(field.getType())) {
            return singleAttributeBuilder.createBasicAttribute(field, declaringType, rootPath);
        }
        else if (field.getType().isAnnotationPresent(DTOEntity.class) || field.getType().isAnnotationPresent(DTOConcrete.class)) {
            return singleAttributeBuilder.createSingleAssocationAttribute(field, declaringType, managedClassesMap, rootPath);
        }
        throw new IllegalArgumentException("Cannot create attribute for field : " + field);
    }

    boolean isBasicOrPrimitiveTypeOrEnum(Class<?> type) {
        return BASIC_AND_PRIMITIVE_TYPES.contains(type) || type.isEnum();
    }

}
