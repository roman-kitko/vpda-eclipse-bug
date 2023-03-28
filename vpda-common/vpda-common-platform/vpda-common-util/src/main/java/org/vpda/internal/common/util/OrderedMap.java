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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map that also holds order of keys
 * 
 * @author kitko
 * @param <K>
 * @param <V>
 */
public final class OrderedMap<K, V> implements Map<K, V>, java.io.Serializable {
    private final List<KeyValue<K, V>> list;
    private final Map<K, ValueOrder<V>> map;

    private static final long serialVersionUID = -5862963611731453495L;

    /**
     * 
     * @author kitko
     *
     * @param <K>
     * @param <V>
     */
    public static final class KeyValue<K, V> implements java.io.Serializable, Map.Entry<K, V> {
        /**
         * 
         */
        private static final long serialVersionUID = 6820204105281309425L;
        private final K key;
        private V value;

        private KeyValue(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @return key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * @return value
         */
        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof KeyValue)) {
                return false;
            }
            KeyValue entry = (KeyValue) o;
            return ObjectUtil.equalsConsiderNull(entry.key, key) && ObjectUtil.equalsConsiderNull(entry.value, value);
        }

        @Override
        public int hashCode() {
            return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }

        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /**
     * @author kitko
     * @param <V>
     */
    public static final class ValueOrder<V> implements java.io.Serializable {
        private static final long serialVersionUID = 6552666566564135063L;
        private final V value;
        private int order;

        private ValueOrder(V value, int order) {
            this.value = value;
            this.order = order;
        }

        /**
         * @return order
         */
        public int getOrder() {
            return order;
        }

        /**
         * @return value
         */
        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ValueOrder)) {
                return false;
            }
            ValueOrder valueOrder = (ValueOrder) o;
            return ObjectUtil.equalsConsiderNull(valueOrder.value, value) && valueOrder.order == order;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (value != null ? value.hashCode() : 0);
            result = prime * order;
            return result;
        }

        @Override
        public String toString() {
            return "ValueOrder [value=" + value + ", order=" + order + "]";
        }
    }

    /**
     * Empty constructor
     *
     */
    public OrderedMap() {
        list = new ArrayList<KeyValue<K, V>>();
        map = new LinkedHashMap<K, ValueOrder<V>>();
    }

    /**
     * @param map
     */
    @SuppressWarnings("unchecked")
    public OrderedMap(Map<K, V> map) {
        list = new ArrayList<KeyValue<K, V>>();
        this.map = new LinkedHashMap<K, ValueOrder<V>>();
        if (map instanceof OrderedMap) {
            OrderedMap<K, V> om = (OrderedMap<K, V>) map;
            for (int i = 0, size = map.size(); i < size; i++) {
                KeyValue<K, V> keyValue = om.getKeyValue(i);
                this.put(keyValue.key, keyValue.value);
            }
        }
        else {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }

    }

    /**
     * Empty constructor
     * 
     * @param initCapacity
     *
     */
    public OrderedMap(int initCapacity) {
        list = new ArrayList<KeyValue<K, V>>(initCapacity);
        map = new LinkedHashMap<K, ValueOrder<V>>(initCapacity);
    }

    /**
     * 
     * @param key
     * @return true if contains key
     */
    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    /**
     * @param i
     * @return KeyValue
     */
    public KeyValue getKeyValue(int i) {
        return list.get(i);
    }

    /**
     * @param i
     * @return value
     */
    public V getValue(int i) {
        KeyValue<K, V> keyValue = list.get(i);
        return keyValue.value;
    }

    /**
     * 
     * @param key
     * @return value of key
     */
    public V getValue(K key) {
        ValueOrder<V> valueOrder = map.get(key);
        if (valueOrder != null) {
            return valueOrder.value;
        }
        return null;
    }

    /**
     * 
     * @param key
     * @return value order
     */
    public ValueOrder<V> getValueOrder(K key) {
        ValueOrder<V> valueOrder = map.get(key);
        return valueOrder;
    }

    /**
     * 
     * @param key
     * @return order key or -1 if key is not found
     */
    public int getOrderOfKey(K key) {
        ValueOrder valueOrder = map.get(key);
        return valueOrder != null ? valueOrder.order : -1;
    }

    /**
     * 
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 
     * @param key
     * @param value
     * @return old value order
     */
    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new RuntimeException("NULL keys not allowed");
        }
        ValueOrder<V> valueOrder = map.get(key);
        if (valueOrder == null) {
            int order = list.size();
            map.put(key, new ValueOrder<V>(value, order));
            list.add(new KeyValue<K, V>(key, value));
        }
        else {
            map.put(key, new ValueOrder<V>(value, valueOrder.order));
            list.set(valueOrder.order, new KeyValue<K, V>(key, value));
        }
        return valueOrder == null ? null : valueOrder.value;
    }

    /**
     * Put new key and value after after key
     * 
     * @param key
     * @param value
     * @param after
     * @return true if map changed
     */
    public boolean putAfterIfNew(K key, V value, K after) {
        if (key == null) {
            throw new RuntimeException("NULL keys not allowed");
        }
        if (after == null) {
            throw new RuntimeException("NULL keys not allowed");
        }
        ValueOrder<V> valueOrder = map.get(key);
        if (valueOrder == null) {
            ValueOrder<V> afterValue = map.get(after);
            if (afterValue == null) {
                return false;
            }
            int newOrder = afterValue.order + 1;
            map.put(key, new ValueOrder<V>(value, newOrder));
            list.add(newOrder, new KeyValue<K, V>(key, value));
            correctOrder(newOrder, list.size());
            return true;
        }
        return false;
    }

    /**
     * Put new key and value before before key
     * 
     * @param key
     * @param value
     * @param before
     * @return true if map changed
     */
    public boolean putBeforeIfNew(K key, V value, K before) {
        if (key == null) {
            throw new RuntimeException("NULL keys not allowed");
        }
        if (before == null) {
            throw new RuntimeException("NULL keys not allowed");
        }
        ValueOrder<V> valueOrder = map.get(key);
        if (valueOrder == null) {
            ValueOrder<V> beforeValue = map.get(before);
            if (beforeValue == null) {
                return false;
            }
            int newOrder = beforeValue.order - 1 >= 0 ? beforeValue.order - 1 : 0;
            map.put(key, new ValueOrder<V>(value, newOrder));
            list.add(newOrder, new KeyValue<K, V>(key, value));
            correctOrder(newOrder, list.size());
            return true;
        }
        return false;
    }

    /**
     * Move Key after other key if both key and after are mapped
     * 
     * @param key
     * @param after
     * @return old mapped value
     */
    public V moveAfter(K key, K after) {
        if (get(key) == null || get(after) == null) {
            return null;
        }
        V old = remove(key);
        putAfterIfNew(key, old, after);
        return old;
    }

    /**
     * Move key before other key if both key and before are mapped
     * 
     * @param key
     * @param before
     * @return old mapped value
     */
    public V moveBefore(K key, K before) {
        if (get(key) == null || get(before) == null) {
            return null;
        }
        V old = remove(key);
        putBeforeIfNew(key, old, before);
        return old;
    }

    private void correctOrder() {
        if (!list.isEmpty()) {
            correctOrder(0, list.size());
        }
    }

    private void correctOrder(int fromIndex, int endIndex) {
        for (int i = fromIndex; i < endIndex; i++) {
            KeyValue<K, V> keyValue = list.get(i);
            ValueOrder<V> valueOrder = map.get(keyValue.getKey());
            if (valueOrder != null) {
                valueOrder.order = i;
            }
        }
    }

    /**
     * 
     * @param key
     * @param value
     * @return old value order
     */
    public V putWithNewAsFirst(K key, V value) {
        if (key == null) {
            throw new RuntimeException("NULL keys not allowed");
        }
        ValueOrder<V> valueOrder = map.get(key);
        if (valueOrder == null) {
            map.put(key, new ValueOrder<V>(value, 0));
            list.add(0, new KeyValue<K, V>(key, value));
            correctOrder();
        }
        else {
            map.put(key, new ValueOrder<V>(value, valueOrder.order));
            list.set(valueOrder.order, new KeyValue<K, V>(key, value));
        }
        return valueOrder == null ? null : valueOrder.value;
    }

    /**
     * @param i
     * @return removed value
     */
    public V remove(int i) {
        KeyValue<K, V> keyValue = list.remove(i);
        map.remove(keyValue.key);
        for (Iterator<ValueOrder<V>> it = map.values().iterator(); it.hasNext();) {
            ValueOrder<V> valueOrder = it.next();
            if (valueOrder.order > i) {
                valueOrder.order = valueOrder.order - 1;
            }
        }
        return keyValue.getValue();
    }

    /**
     * @param key
     */
    @Override
    public V remove(Object key) {
        ValueOrder<V> valueOrder = map.remove(key);
        if (valueOrder != null) {
            list.remove(valueOrder.order);
            for (Iterator<ValueOrder<V>> it = map.values().iterator(); it.hasNext();) {
                ValueOrder<V> vo = it.next();
                if (vo.order > valueOrder.order) {
                    vo.order = vo.order - 1;
                }
            }
        }
        return valueOrder == null ? null : valueOrder.value;
    }

    /**
     * 
     * @return size
     */
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append("{");
        for (int i = 0; i < list.size(); i++) {
            KeyValue kv = list.get(i);
            b.append(kv.key);
            b.append("=");
            b.append(kv.value);
            if (i + 1 < list.size()) {
                b.append(",");
            }
        }
        b.append("}");
        return b.toString();
    }

    /**
     * @return values
     */
    @Override
    public List<V> values() {
        List<V> values = new ArrayList<V>();
        for (KeyValue<K, V> keyvalue : list) {
            values.add(keyvalue.value);
        }
        return Collections.unmodifiableList(values);
    }

    /**
     * @return keys
     */
    public List<K> keys() {
        List<K> keys = new ArrayList<K>();
        for (KeyValue<K, V> keyvalue : list) {
            keys.add(keyvalue.key);
        }
        return Collections.unmodifiableList(keys);
    }

    @Override
    public void clear() {
        map.clear();
        list.clear();
    }

    @Override
    public boolean containsValue(Object value) {
        for (KeyValue<K, V> keyValue : list) {
            if (keyValue.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new LinkedHashSet<Entry<K, V>>();
        for (KeyValue<K, V> keyValue : list) {
            set.add(keyValue);
        }
        return set;
    }

    @Override
    public V get(Object key) {
        ValueOrder<V> v = map.get(key);
        return v != null ? v.value : null;
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }

    }

    /**
     * Return i-ties key
     * 
     * @param index
     * @return i-ties key
     */
    public K getKey(int index) {
        return list.get(index).key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        if (o instanceof OrderedMap) {
            OrderedMap om = (OrderedMap) o;
            return this.map.equals(om.map) && this.list.equals(om.list);
        }
        Map m = (Map) o;
        if (this.map.size() != m.size()) {
            return false;
        }
        for (Map.Entry<K, ValueOrder<V>> entry : this.map.entrySet()) {
            Object value2 = m.get(entry.getKey());
            if (!ObjectUtil.equalsConsiderNull(entry.getValue().getValue(), value2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + list.hashCode();
        result = prime * result + map.hashCode();
        return result;
    }

}
