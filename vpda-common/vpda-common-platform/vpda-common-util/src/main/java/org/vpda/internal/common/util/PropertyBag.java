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
package org.vpda.internal.common.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.vpda.common.annotations.Immutable;

/** Readonly bag of properties */
@Immutable
public final class PropertyBag implements Serializable {
    private static final long serialVersionUID = -3943768724463783046L;
    private final Map<String, Object> bag;

    private static final PropertyBag EMPTY_BAG = new PropertyBag(Collections.<String, Object>emptyMap());

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Creates a new bag from Map
     * 
     * @param values
     **/
    public PropertyBag(Map<String, Object> values) {
        this.bag = new HashMap<String, Object>(values);
    }

    /**
     * @return empty bag
     */
    public static PropertyBag emptyBag() {
        return EMPTY_BAG;
    }

    /**
     * Merge Two bags, second wins, returning new bag
     * 
     * @param first
     * @param secondWins
     * @return merge of two bags, second wins
     */
    public static PropertyBag merge(PropertyBag first, PropertyBag secondWins) {
        if (first == null && secondWins == null) {
            return EMPTY_BAG;
        }
        if (first == null) {
            return secondWins;
        }
        if (secondWins == null) {
            return first;
        }
        Map<String, Object> values = new HashMap<>();
        values.putAll(first.bag);
        values.putAll(secondWins.bag);
        return new PropertyBag(values);
    }

    /**
     * Merge this bag with another, second one wins
     * 
     * @param secondWins
     * @return merged new bag
     */
    public PropertyBag merge(PropertyBag secondWins) {
        return PropertyBag.merge(this, secondWins);
    }

    /**
     * Gets value for key, checking its type
     * 
     * @param key
     * @param type
     * @return value for key
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> type) {
        return getValue(key, type, (T[]) EMPTY_OBJECT_ARRAY);
    }

    /**
     * Gets value for key, checking its type
     * 
     * @param key
     * @param type
     * @param defaultValues Firts not null value returned when key is not contained
     *                      in bag
     * @return value for key
     */
    public <T> T getValue(String key, Class<T> type, @SuppressWarnings("unchecked") T... defaultValues) {
        Object value = bag.get(key);
        if (value == null) {
            if (bag.containsKey(key)) {
                return null;
            }
            if (defaultValues.length == 0) {
                return null;
            }
            for (T def : defaultValues) {
                if (def != null) {
                    return def;
                }
            }
            return null;
        }
        if (value instanceof String) {
            String stringValue = (String) value;
            if (!stringValue.isEmpty() && stringValue.startsWith("${") && stringValue.endsWith("}")) {
                if (defaultValues.length == 0) {
                    return null;
                }
                for (T def : defaultValues) {
                    if (def != null) {
                        return def;
                    }
                }
            }
        }
        if (!type.isAssignableFrom(value.getClass())) {
            return convert(key, value, type);
        }
        return type.cast(value);
    }

    /**
     * Gets String value for key, checking its type
     * 
     * @param key
     * @return String value for key
     */
    public String getStringValue(String key) {
        return getValue(key, String.class, EMPTY_STRING_ARRAY);
    }

    /**
     * Gets String value for key, checking its type
     * 
     * @param key
     * @param defaultValues
     * @return String value for key
     */
    public String getStringValue(String key, String... defaultValues) {
        return getValue(key, String.class, defaultValues);
    }

    /**
     * Gets required value for key, checking its type
     * 
     * @param key
     * @param type
     * @return value for key, if not present throws RuntimeException
     */
    public <T> T getRequiredValue(String key, Class<T> type) {
        Object value = bag.get(key);
        if (value == null) {
            if (!bag.containsKey(key)) {
                throw new RuntimeException(MessageFormat.format("Value for key [{0}] is not in bag", key));
            }
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            return convert(key, value, type);
        }
        return type.cast(value);
    }

    private <T> T convert(String key, Object value, Class<T> type) {
        if (Integer.class.equals(type) && String.class.equals(value.getClass())) {
            return type.cast(Integer.valueOf(value.toString()));
        }
        if (Long.class.equals(type) && String.class.equals(value.getClass())) {
            return type.cast(Long.valueOf(value.toString()));
        }
        if (Double.class.equals(type) && String.class.equals(value.getClass())) {
            return type.cast(Double.valueOf(value.toString()));
        }
        throw new ClassCastException(MessageFormat.format("Required value for key [{0}] is of type [{1}], but [{2}] was required", key, value.getClass(), type));
    }

    /**
     * Gets String value for key, checking its type
     * 
     * @param key
     * @return required string value
     */
    public String getRequiredStringValue(String key) {
        return getRequiredValue(key, String.class);
    }

    /**
     * Test whether bag contains key
     * 
     * @param key
     * @return true if bag contains key
     */
    public boolean containsKey(String key) {
        return bag.containsKey(key);
    }

    /**
     * @return true if bag is empty
     */
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    /**
     * @return size of bag
     */
    public int size() {
        return bag.size();
    }

    /**
     * @return all keys inside bag
     */
    public Set<String> keys() {
        return Collections.unmodifiableSet(bag.keySet());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bag == null) ? 0 : bag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PropertyBag other = (PropertyBag) obj;
        if (bag == null) {
            if (other.bag != null)
                return false;
        }
        else if (!bag.equals(other.bag))
            return false;
        return true;
    }

}
