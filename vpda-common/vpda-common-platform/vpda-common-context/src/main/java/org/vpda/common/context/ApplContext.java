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
package org.vpda.common.context;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.types.AbstractNamedCode;
import org.vpda.common.types.IdentifiedObject;
import org.vpda.common.types.NamedCode;
import org.vpda.internal.common.util.ObjectUtil;
import org.vpda.internal.common.util.OrderedMap;

/**
 * ApplContext is a collection of {@link ContextItem} items
 * 
 * @author kitko
 *
 */
@Immutable
public final class ApplContext implements Serializable, NamedCode {
    private static final long serialVersionUID = 8975593753804131717L;
    private final OrderedMap<ContextItemKey, ContextItem<?>> items;
    private final long id;
    private final String name;
    private final String code;
    private final String description;

    ApplContext(ApplContextBuilder builder) {
        this.id = builder.getId();
        this.code = builder.getCode() != null ? builder.getCode() : Long.toString(this.id);
        this.name = builder.getName();
        this.description = builder.getDescription();
        ApplContext parent = builder.getParent();
        this.items = new OrderedMap<ContextItemKey, ContextItem<?>>(parent != null ? parent.size() : 0 + builder.getInternalItems().size());
        if (parent != null) {
            for (ContextItem<?> item : parent.items.values()) {
                this.items.put(item.getKey(), item);
            }
        }
        this.items.putAll(builder.getInternalItems());
    }

    /**
     * @return size
     */
    public int size() {
        return items.size();
    }

    /**
     * @return true if context has no item
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * @return keys in context
     */
    public List<ContextItemKey> getKeys() {
        return Collections.unmodifiableList(items.keys());
    }

    /**
     * @return item entries
     */
    public Collection<Map.Entry<ContextItemKey, ContextItem<?>>> getEntries() {
        return Collections.unmodifiableCollection(Collections.unmodifiableMap(items).entrySet());
    }

    /**
     * @return item values
     */
    public List<ContextItem<?>> getItems() {
        return Collections.unmodifiableList(items.values());
    }

    /**
     * @return items
     */
    public Map<ContextItemKey, ContextItem<?>> getItemsMap() {
        return Collections.unmodifiableMap(items);
    }

    OrderedMap<ContextItemKey, ContextItem<?>> getItemsInternal() {
        return items;
    }

    /**
     * @param key
     * @return item by key or null if not found
     */
    public ContextItem getItem(ContextItemKey key) {
        return items.get(key);
    }

    /**
     * @param i
     * @return Item at i position
     */
    public ContextItem getItem(int i) {
        return items.getValue(i);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets item by key and checks that item has compatible type
     * 
     * @param <T>
     * @param key
     * @param clazz
     * @return item by key or null if not found
     * @throws ClassCastException if type is not compatible
     */
    @SuppressWarnings("unchecked")
    public <T> ContextItem<T> getItem(ContextItemKey key, Class<T> clazz) {
        ContextItem<?> item = items.get(key);
        if (item == null) {
            return null;
        }
        if (!clazz.isAssignableFrom(item.getClazz())) {
            throw new ClassCastException("Item [" + item + "] is not of type [" + clazz + "]");
        }
        return (ContextItem<T>) item;
    }

    @Override
    public String toString() {
        return items.values().toString();
    }

    /**
     * @return short string
     */
    public String toShortString() {
        StringBuilder builder = new StringBuilder();
        for (ContextItem item : items.values()) {
            if (builder.length() > 0) {
                builder.append(',');
            }
            Object value = item.getValue();
            String shortString = value.toString();
            if (value instanceof AbstractNamedCode) {
                shortString = ((AbstractNamedCode) value).getCode();
            }
            builder.append(shortString);
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ApplContext)) {
            return false;
        }
        ApplContext other = (ApplContext) obj;
        if (id != other.id) {
            return false;
        }
        if (items == null) {
            if (other.items != null) {
                return false;
            }
        }
        else if (!items.equals(other.items)) {
            return false;
        }
        return true;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return ApplContextBuilder with same values
     */
    public ApplContextBuilder createBuilderWithSameValues() {
        return new ApplContextBuilder().setValues(this);
    }

    /**
     * @param ctx
     * @return equals method but here we will look just at hierarchy
     */
    public boolean equalsConsiderIdsOnly(ApplContext ctx) {
        if (id != ctx.getId()) {
            return false;
        }
        if (items.size() != ctx.items.size()) {
            return false;
        }
        for (Map.Entry<ContextItemKey, ContextItem<?>> entry : this.items.entrySet()) {
            ContextItem other = ctx.getItem(entry.getKey());
            if (other == null) {
                return false;
            }
            Object value1 = entry.getValue().getValue();
            Object value2 = other.getValue();
            if (value1 instanceof IdentifiedObject && value2 instanceof IdentifiedObject) {
                if (!ObjectUtil.equalsConsiderNull(((IdentifiedObject) value1).getId(), ((IdentifiedObject) value2).getId())) {
                    return false;
                }
            }
            else if (!ObjectUtil.equalsConsiderNull(value1, value2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param ctx
     * @param skipTypes
     * @return true/false
     */
    public boolean equalsConsiderIdsOnlySkippingTypes(ApplContext ctx, Collection<ContextItemType> skipTypes) {
        if (id != ctx.getId()) {
            return false;
        }
        for (Map.Entry<ContextItemKey, ContextItem<?>> entry : this.items.entrySet()) {
            if (skipTypes.contains(entry.getKey().getType())) {
                continue;
            }
            ContextItem other = ctx.getItem(entry.getKey());
            if (other == null) {
                return false;
            }
            Object value1 = entry.getValue().getValue();
            Object value2 = other.getValue();
            if (value1 instanceof IdentifiedObject && value2 instanceof IdentifiedObject) {
                if (!ObjectUtil.equalsConsiderNull(((IdentifiedObject) value1).getId(), ((IdentifiedObject) value2).getId())) {
                    return false;
                }
            }
            else if (!ObjectUtil.equalsConsiderNull(value1, value2)) {
                return false;
            }
        }
        return true;
    }

}
