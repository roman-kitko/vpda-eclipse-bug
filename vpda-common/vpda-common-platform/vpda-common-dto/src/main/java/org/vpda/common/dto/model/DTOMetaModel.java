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
package org.vpda.common.dto.model;

import java.util.Collection;

public interface DTOMetaModel {
    /**
     * Return the metamodel entity type representing the entity.
     * 
     * @param cls the type of the represented entity
     * @return the metamodel entity type
     * @throws IllegalArgumentException if not an entity
     */
    <X> EntityType<X> entity(Class<X> cls);

    <X> ConcreteManagedType<X> concreete(Class<X> cls);

    /**
     * Return the metamodel managed type representing the entity, mapped superclass,
     * or embeddable class.
     * 
     * @param cls the type of the represented managed class
     * @return the metamodel managed type
     * @throws IllegalArgumentException if not a managed class
     */
    <X> ManagedType<X> managedType(Class<X> cls);

    <X, Y extends ManagedType<X>> Y managedType(Class<X> cls, Class<Y> type);

    /**
     * Return the metamodel embeddable type representing the embeddable class.
     * 
     * @param cls the type of the represented embeddable class
     * @return the metamodel embeddable type
     * @throws IllegalArgumentException if not an embeddable class
     */
    <X> EmbeddableType<X> embeddable(Class<X> cls);

    <X> MappedSuperIdentifiedType<X> mappedSuperIdentifiedType(Class<X> cls);

    /**
     * Return the metamodel managed types.
     * 
     * @return the metamodel managed types
     */
    Collection<ManagedType<?>> getManagedTypes();

    /**
     * Return the metamodel entity types.
     * 
     * @return the metamodel entity types
     */
    Collection<EntityType<?>> getEntities();

    /**
     * Return the metamodel transient types types.
     * 
     * @return the metamodel transient types
     */
    Collection<ConcreteManagedType<?>> getConcreteTypes();

    /**
     * Return the metamodel embeddable types. Returns empty set if there are no
     * embeddable types.
     * 
     * @return the metamodel embeddable types
     */
    Collection<EmbeddableType<?>> getEmbeddables();

    /**
     * Return the metamodel embeddable types. Returns empty set if there are no
     * embeddable types.
     * 
     * @return the metamodel embeddable types
     */
    Collection<MappedSuperIdentifiedType<?>> getMappedSuperIdentifiedTypes();
    
    <X,M> Class<M> getModelClassForManagedType(ManagedType<X> managedType);
    
    <X,M> Class<M> getModelClass(Class<X> dtoClass);
}
