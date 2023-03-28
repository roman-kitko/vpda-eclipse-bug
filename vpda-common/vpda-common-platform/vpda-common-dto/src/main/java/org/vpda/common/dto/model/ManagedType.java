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

public interface ManagedType<X> extends Type<X> {
    /**
     * Return the attributes of the managed type.
     * 
     * @return attributes of the managed type
     */
    Collection<Attribute<? super X, ?>> getAttributes();

    /**
     * Return the attributes declared by the managed type. Returns empty set if the
     * managed type has no declared attributes.
     * 
     * @return declared attributes of the managed type
     */
    Collection<Attribute<X, ?>> getDeclaredAttributes();

    /**
     * Return the attribute of the managed type that corresponds to the specified
     * name.
     * 
     * @param name the name of the represented attribute
     * @return attribute with given name
     * @throws IllegalArgumentException if attribute of the given name is not
     *                                  present in the managed type
     */
    Attribute<? super X, ?> getAttribute(String name);

    <Y> SingleAttribute<? super X, Y> getSingleAttribute(String name, Class<Y> javaType);

    <E> SetAttribute<? super X, E> getSetAttribute(String name, Class<E> elementType);

    <E> ListAttribute<? super X, E> getListAttribute(String name, Class<E> elementType);

    <K, V> MapAttribute<? super X, K, V> getMapAttribute(String name, Class<K> keyType, Class<V> valueType);

    /**
     * Return the attribute declared by the managed type that corresponds to the
     * specified name.
     * 
     * @param name the name of the represented attribute
     * @return attribute with given name
     * @throws IllegalArgumentException if attribute of the given name is not
     *                                  declared in the managed type
     */
    Attribute<X, ?> getDeclaredAttribute(String name);
    
    <Y> SingleAttribute<X, Y> getDeclaredSingleAttribute(String name, Class<Y> javaType);

    public ManagedType<?> getSuperType();

}
