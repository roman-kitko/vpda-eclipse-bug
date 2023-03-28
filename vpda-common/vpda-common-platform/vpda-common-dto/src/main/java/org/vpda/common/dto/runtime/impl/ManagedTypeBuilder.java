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
import java.util.Map;
import java.util.function.Predicate;

import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.ConcreteManagedType;
import org.vpda.common.dto.model.EmbeddableType;
import org.vpda.common.dto.model.EntityType;
import org.vpda.common.dto.model.IdentifiableType;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.MappedSuperIdentifiedType;
import org.vpda.common.dto.model.SingleAttribute;
import org.vpda.common.dto.model.ValueManagedType;
import org.vpda.common.dto.model.impl.ConcreteManagedTypeImpl;
import org.vpda.common.dto.model.impl.EmbeddableTypeImpl;
import org.vpda.common.dto.model.impl.EntityTypeImpl;
import org.vpda.common.dto.model.impl.MappedSuperIdentifiedTypeImpl;
import org.vpda.common.dto.model.impl.ValueManagedTypeImpl;

final class ManagedTypeBuilder {

    private final DTOModelBuilder modelBuilder;

    ManagedTypeBuilder(DTOModelBuilder modelBuilder) {
        super();
        this.modelBuilder = modelBuilder;
    }

    @SuppressWarnings("unchecked")
    EntityType<?> createEntityType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        if (managedClassesMap.get(clazz) != null) {
            return EntityType.class.cast((managedClassesMap.get(clazz)));
        }

        Predicate<Field> fieldFilter = modelBuilder.getModelConfiguration().getFieldFilter();
        EntityTypeImpl.EntityTypeImplTypeBuilder<?> builder = new EntityTypeImpl.EntityTypeImplTypeBuilder<>();
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            ManagedType superType = modelBuilder.createManagedType(clazz.getSuperclass(), managedClassesMap);
            builder.setSuperType(superType);
            if (superType instanceof IdentifiableType) {
                builder.setId(((IdentifiableType) superType).getIdAttribute());
                builder.setExternalId(((IdentifiableType) superType).getExternalIdAttribute());
            }
        }
        builder.setJavaClass((Class) clazz);
        EntityType tempManagedType = builder.build();
        managedClassesMap.put(clazz, tempManagedType);
        for (Field f : clazz.getDeclaredFields()) {
            if (!fieldFilter.test(f)) {
                continue;
            }
            Attribute<?, ?> attr = modelBuilder.createAttribute(f, tempManagedType, managedClassesMap);
            builder.addDeclaredAttribute((Attribute) attr);
            if (attr instanceof SingleAttribute && ((SingleAttribute) attr).isId()) {
                builder.setId((Attribute) attr);
            }
            if (attr instanceof SingleAttribute && ((SingleAttribute) attr).isExternalId()) {
                builder.setExternalId((Attribute) attr);
            }
        }
        EntityTypeImpl<?> build = builder.build();
        managedClassesMap.put(clazz, build);
        return build;
    }

    @SuppressWarnings("unchecked")
    MappedSuperIdentifiedType<?> createMappedSuperIdentifiedType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        if (managedClassesMap.get(clazz) != null) {
            return MappedSuperIdentifiedType.class.cast(managedClassesMap.get(clazz));
        }
        Predicate<Field> fieldFilter = modelBuilder.getModelConfiguration().getFieldFilter();
        MappedSuperIdentifiedTypeImpl.MappedSuperIdentifiedTypeImplTypeBuilder<?> builder = new MappedSuperIdentifiedTypeImpl.MappedSuperIdentifiedTypeImplTypeBuilder<>();
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            ManagedType superType = modelBuilder.createManagedType(clazz.getSuperclass(), managedClassesMap);
            ;
            builder.setSuperType(superType);
            if (superType instanceof IdentifiableType) {
                builder.setId(((IdentifiableType) superType).getIdAttribute());
                builder.setExternalId(((IdentifiableType) superType).getExternalIdAttribute());
            }
        }
        builder.setJavaClass((Class) clazz);
        MappedSuperIdentifiedType tempManagedType = builder.build();
        managedClassesMap.put(clazz, tempManagedType);
        for (Field f : clazz.getDeclaredFields()) {
            if (!fieldFilter.test(f)) {
                continue;
            }
            Attribute<?, ?> attr = modelBuilder.createAttribute(f, tempManagedType, managedClassesMap);
            builder.addDeclaredAttribute((Attribute) attr);
            if (attr instanceof SingleAttribute && ((SingleAttribute) attr).isId()) {
                builder.setId((Attribute) attr);
            }
            if (attr instanceof SingleAttribute && ((SingleAttribute) attr).isExternalId()) {
                builder.setExternalId((Attribute) attr);
            }
        }
        MappedSuperIdentifiedTypeImpl<?> build = builder.build();
        managedClassesMap.put(clazz, build);
        return build;
    }

    @SuppressWarnings("unchecked")
    EmbeddableType<?> createEmbeddableType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        if (managedClassesMap.get(clazz) != null) {
            return EmbeddableType.class.cast((managedClassesMap.get(clazz)));
        }
        Predicate<Field> fieldFilter = modelBuilder.getModelConfiguration().getFieldFilter();
        EmbeddableTypeImpl.EntityTypeImplTypeBuilder builder = new EmbeddableTypeImpl.EntityTypeImplTypeBuilder();
        builder.setJavaClass(clazz);
        EmbeddableType tempManagedType = builder.build();
        managedClassesMap.put(clazz, tempManagedType);
        for (Field f : clazz.getDeclaredFields()) {
            if (!fieldFilter.test(f)) {
                continue;
            }
            Attribute<?, ?> attr = modelBuilder.createAttribute(f, tempManagedType, managedClassesMap);
            builder.addDeclaredAttribute(attr);
        }
        EmbeddableTypeImpl build = builder.build();
        managedClassesMap.put(clazz, build);
        return build;
    }

    @SuppressWarnings("unchecked")
    ConcreteManagedType<?> createConcreteType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        if (managedClassesMap.get(clazz) != null) {
            return ConcreteManagedType.class.cast((managedClassesMap.get(clazz)));
        }
        ConcreteManagedTypeImpl.ConcreteManagedTypeImplBuilder<?> builder = new ConcreteManagedTypeImpl.ConcreteManagedTypeImplBuilder<>();
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            ManagedType superType = modelBuilder.createManagedType(clazz.getSuperclass(), managedClassesMap);
            ;
            builder.setSuperType(superType);
        }
        builder.setJavaClass((Class) clazz);
        ConcreteManagedType tempManagedType = builder.build();
        managedClassesMap.put(clazz, tempManagedType);
        Predicate<Field> fieldFilter = modelBuilder.getModelConfiguration().getFieldFilter();
        for (Field f : clazz.getDeclaredFields()) {
            if (!fieldFilter.test(f)) {
                continue;
            }
            Attribute<?, ?> attr = modelBuilder.createAttribute(f, tempManagedType, managedClassesMap);
            builder.addDeclaredAttribute((Attribute) attr);
        }
        ConcreteManagedTypeImpl<?> build = builder.build();
        managedClassesMap.put(clazz, build);
        return build;

    }

    ValueManagedType<?> createValueType(Class<?> clazz, Map<Class<?>, ManagedType<?>> managedClassesMap) {
        if (managedClassesMap.get(clazz) != null) {
            return ValueManagedType.class.cast((managedClassesMap.get(clazz)));
        }
        ValueManagedType<?> valueManagedTypeImpl = new ValueManagedTypeImpl<>(clazz);
        managedClassesMap.put(clazz, valueManagedTypeImpl);
        return valueManagedTypeImpl;
    }

}
