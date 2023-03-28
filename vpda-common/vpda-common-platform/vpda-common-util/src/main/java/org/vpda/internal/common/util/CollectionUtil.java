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
/**
 * 
 */
package org.vpda.internal.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Utilities for collections
 * 
 * @author kitko
 *
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * Returns checked collection, with items checked for type from origin
     * collection
     * 
     * @param c
     * @param type
     * @return checked collection
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> checkedCollection(Collection c, Class<T> type) {
        Collection<T> checkedCollection = Collections.checkedCollection(new ArrayList<T>(c.size()), type);
        checkedCollection.addAll(c);
        return checkedCollection;
    }

    /**
     * Creates map from pairs
     * 
     * @param pairs
     * @return map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> mapFromPairs(Pair<K, V>... pairs) {
        Map<K, V> map = new HashMap<K, V>(pairs.length);
        for (Pair<K, V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }
        return map;
    }

    /**
     * Creates set with values
     * 
     * @param values
     * @return set
     */
    public static <T> Set<T> newSet(@SuppressWarnings("unchecked") T... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    private static final class UnmodifiableIterator<T> implements Iterator<T> {
        private final Iterator<T> it;

        /**
         * @param it
         */
        UnmodifiableIterator(Iterator<T> it) {
            super();
            this.it = it;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public T next() {
            return it.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

    }

    /**
     * @param it
     * @return UnmodifiableIterator
     */
    public static <T> Iterator<T> unmodifiableIterator(Iterator<T> it) {
        return new UnmodifiableIterator<T>(it);
    }

    private static Predicate<String> TRUE_PREDICATE = (value) -> {
        return true;
    };

    /**
     * @param o
     * @param exclussions
     * @return objectGettersToFlatMap
     */
    public static Map<String, Object> objectNonEmptyGettersToFlatMapWithExclussions(Object o, String... exclussions) {
        if (exclussions.length == 0) {
            return objectNonEmptyGettersToFlatMapWithFilter(o, TRUE_PREDICATE);
        }
        Predicate<String> propertyNamePredicate = (name) -> {
            for (String e : exclussions) {
                if (name.equals(e)) {
                    return false;
                }
            }
            return true;
        };

        return objectNonEmptyGettersToFlatMapWithFilter(o, propertyNamePredicate);
    }

    /**
     * Gets flat map of object using getters
     * 
     * @param o
     * @param includePropertyNameFilter
     * @return map
     */
    public static Map<String, Object> objectNonEmptyGettersToFlatMapWithFilter(Object o, Predicate<String> includePropertyNameFilter) {
        Map<String, Object> map = new HashMap<>();
        for (Method m : o.getClass().getMethods()) {
            if (m.getDeclaringClass().equals(Object.class)) {
                continue;
            }
            if (m.getParameterTypes().length == 0 && m.getName().startsWith("get") || m.getName().startsWith("is")) {
                String name = null;
                if (m.getName().startsWith("get")) {
                    name = m.getName().substring(3);
                }
                else if (m.getName().startsWith("is")) {
                    name = m.getName().substring(2);
                }
                name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
                if (!includePropertyNameFilter.test(name)) {
                    continue;
                }
                try {
                    Object value = m.invoke(o);
                    if (value == null) {
                        continue;
                    }
                    if (value instanceof String) {
                        if (StringUtil.isEmpty((String) value)) {
                            continue;
                        }
                    }
                    map.put(name, value);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new VPDARuntimeException("cannot get value of getter", e);
                }
            }
        }
        return map;
    }

    public static <T> boolean isEmpty(Collection<T> col) {
        return col == null || col.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> col) {
        return col != null && !col.isEmpty();
    }

    public static <T> Collection<T> emptyIfNull(Collection<T> col) {
        return col != null ? col : Collections.emptyList();
    }

    public static <T> List<T> emptyIfNull(List<T> col) {
        return col != null ? col : Collections.emptyList();
    }

    public static <T> Set<T> emptyIfNull(Set<T> col) {
        return col != null ? col : Collections.emptySet();
    }

    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> map) {
        return map != null ? map : Collections.emptyMap();
    }

}
