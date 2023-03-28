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
import java.util.IdentityHashMap;
import java.util.Map;

import org.vpda.common.dto.model.ConcreteManagedType;
import org.vpda.common.dto.model.DTOMetaModel;
import org.vpda.common.dto.model.DTOMetaModelRegistry;
import org.vpda.common.dto.model.EmbeddableType;
import org.vpda.common.dto.model.EntityType;
import org.vpda.common.dto.model.ManagedType;
import org.vpda.common.dto.model.MappedSuperIdentifiedType;

public final class DTOMetaModelImpl implements DTOMetaModel, DTOMetaModelRegistry {
    private final Map<String, ManagedType<?>> allManagedTypes;
    private final Map<String, EntityType<?>> entities;
    private final Map<String, EmbeddableType<?>> embedables;
    private final Map<String, MappedSuperIdentifiedType<?>> mappedSuperIdentifiedClasses;
    private final Map<String, ConcreteManagedType<?>> concretes;
    private final Map<ManagedType<?>, Class<?>> typeToModelClass;

    private DTOMetaModelImpl(DTOMetaModelBuilder dtoMetaModelBuilder) {
        this.allManagedTypes = new HashMap<>(dtoMetaModelBuilder.allManagedTypes);
        this.entities = this.<EntityType<?>>filterManagedTypes(allManagedTypes, new HashMap<String, EntityType<?>>(), EntityType.class);
        this.embedables = this.filterManagedTypes(allManagedTypes, new HashMap<String, EmbeddableType<?>>(), EmbeddableType.class);
        this.mappedSuperIdentifiedClasses = this.filterManagedTypes(allManagedTypes, new HashMap<String, MappedSuperIdentifiedType<?>>(), MappedSuperIdentifiedType.class);
        this.concretes = this.filterManagedTypes(allManagedTypes, new HashMap<String, ConcreteManagedType<?>>(), ConcreteManagedType.class);
        this.typeToModelClass = new IdentityHashMap<>();
    }

    @SuppressWarnings("unchecked")
    private <M extends ManagedType<?>> Map<String, M> filterManagedTypes(Map<String, ManagedType<?>> allManagedTypes, Map<String, M> targetTypes, Class<?> clazz) {
        allManagedTypes.forEach((k, v) -> {
            if (clazz.isInstance(v)) {
                targetTypes.put(k, (M) v);
            }
        });
        return targetTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> EntityType<X> entity(Class<X> cls) {
        ManagedType<?> managedType = getRequiredManagedType(cls, entities);
        if (!(managedType instanceof EntityType)) {
            throw new IllegalArgumentException("Managed type is not entity");
        }
        if (!managedType.getJavaType().equals(cls)) {
            throw new IllegalArgumentException("Entity type is of different type");
        }
        return EntityType.class.cast(managedType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> ManagedType<X> managedType(Class<X> cls) {
        ManagedType<?> managedType = getRequiredManagedType(cls, allManagedTypes);
        if (!managedType.getJavaType().equals(cls)) {
            throw new IllegalArgumentException("Managed type is of different type");
        }
        return (ManagedType<X>) managedType;
    }

    @Override
    public <X, Y extends ManagedType<X>> Y managedType(Class<X> cls, Class<Y> type) {
        ManagedType<?> managedType = getRequiredManagedType(cls, allManagedTypes);
        if (type.isInstance(managedType)) {
            return type.cast(managedType);
        }
        throw new IllegalArgumentException("Managed type is of type " + managedType.getClass().getName() + " which is not compatible with requested : " + type.getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> EmbeddableType<X> embeddable(Class<X> cls) {
        ManagedType<?> managedType = getRequiredManagedType(cls, embedables);
        if (!(managedType instanceof EmbeddableType)) {
            throw new IllegalArgumentException("Managed type is not EmbeddableType");
        }
        if (!managedType.getJavaType().equals(cls)) {
            throw new IllegalArgumentException("EmbeddableType is of different type");
        }
        return EmbeddableType.class.cast(managedType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> MappedSuperIdentifiedType<X> mappedSuperIdentifiedType(Class<X> cls) {
        ManagedType<?> managedType = getRequiredManagedType(cls, mappedSuperIdentifiedClasses);
        if (!(managedType instanceof MappedSuperIdentifiedType)) {
            throw new IllegalArgumentException("Managed type is not MappedSuperType");
        }
        if (!managedType.getJavaType().equals(cls)) {
            throw new IllegalArgumentException("MappedSuperType is of different type");
        }
        return MappedSuperIdentifiedType.class.cast(managedType);
    }

    @Override
    public Collection<ManagedType<?>> getManagedTypes() {
        return Collections.unmodifiableCollection(allManagedTypes.values());
    }

    @Override
    public Collection<EntityType<?>> getEntities() {
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public Collection<EmbeddableType<?>> getEmbeddables() {
        return Collections.unmodifiableCollection(embedables.values());
    }

    @Override
    public Collection<MappedSuperIdentifiedType<?>> getMappedSuperIdentifiedTypes() {
        return Collections.unmodifiableCollection(mappedSuperIdentifiedClasses.values());
    }

    private ManagedType<?> getRequiredManagedType(Class c, Map<String, ? extends ManagedType<?>> map) {
        ManagedType<?> managedType = map.get(c.getName());
        if (managedType == null) {
            throw new IllegalArgumentException("No managed type by class : " + c.getName());
        }
        return managedType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> ConcreteManagedType<X> concreete(Class<X> cls) {
        ManagedType<?> managedType = getRequiredManagedType(cls, concretes);
        if (!(managedType instanceof ConcreteManagedType)) {
            throw new IllegalArgumentException("Managed type is not ConcreteManagedType");
        }
        if (!managedType.getJavaType().equals(cls)) {
            throw new IllegalArgumentException("ConcreteManagedType is of different type");
        }
        return ConcreteManagedType.class.cast(managedType);
    }

    @Override
    public Collection<ConcreteManagedType<?>> getConcreteTypes() {
        return Collections.unmodifiableCollection(concretes.values());
    }
    
    

    @Override
    public <X, M> void registerMetaModelClassForManagedType(ManagedType<X> managedType, Class<M> modelClass) {
        typeToModelClass.put(managedType, modelClass);
    }


    @SuppressWarnings("unchecked")
    @Override
    public <X, M> Class<M> getModelClassForManagedType(ManagedType<X> managedType) {
        return (Class<M>) typeToModelClass.get(managedType);
    }
    
    public <X,M> Class<M> getModelClass(Class<X> dtoClass){
        ManagedType<X> managedType = managedType(dtoClass);
        if(managedType == null) {
            return null;
        }
        return getModelClassForManagedType(managedType);
    }

    public static final class DTOMetaModelBuilder implements org.vpda.common.util.Builder<DTOMetaModel> {

        private Map<String, ManagedType<?>> allManagedTypes = new HashMap<>();

        public <X> DTOMetaModelBuilder addEntityType(EntityType<X> entity) {
            allManagedTypes.put(entity.getJavaType().getName(), entity);
            return this;
        }

        public <X> DTOMetaModelBuilder addEmbeddableType(EmbeddableType<X> embeddable) {
            allManagedTypes.put(embeddable.getJavaType().getName(), embeddable);
            return this;
        }

        public <X> DTOMetaModelBuilder addMappedSuperIdentifiedType(MappedSuperIdentifiedType<X> mappedSuperType) {
            allManagedTypes.put(mappedSuperType.getJavaType().getName(), mappedSuperType);
            return this;
        }

        public <X> DTOMetaModelBuilder addManagedType(ManagedType<X> type) {
            allManagedTypes.put(type.getJavaType().getName(), type);
            return this;
        }

        public <X> DTOMetaModelBuilder addConcreteType(ManagedType<X> type) {
            allManagedTypes.put(type.getJavaType().getName(), type);
            return this;
        }

        @Override
        public DTOMetaModel build() {
            return new DTOMetaModelImpl(this);
        }

    }

}
