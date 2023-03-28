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
package org.vpda.clientserver.viewprovider.autocomplete;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.comps.autocomplete.AutoCompleteDataItem;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.CollectionUtil;

/**
 * One suggestion item
 * 
 * @author kitko
 *
 */
public class AutoCompletionItem implements Serializable, AutoCompleteDataItem {
    private static final long serialVersionUID = 8161290477315080543L;
    private final Object id;
    private final String description;
    private final Map<String, Object> dataValues;
    private final Object dataObject;

    /**
     * @param id
     * @param description
     * @param dataObject
     * @param dataValues
     */
    public AutoCompletionItem(Object id, String description, Object dataObject, Map<String, Object> dataValues) {
        super();
        this.id = Assert.isNotNullArgument(id, "id");
        this.description = description;
        this.dataObject = dataObject;
        this.dataValues = dataValues != null ? new HashMap<>(dataValues) : (dataObject != null ? CollectionUtil.objectNonEmptyGettersToFlatMapWithExclussions(dataObject) : null);
    }

    /**
     * @param id
     * @param description
     * @param dataValues
     */
    public AutoCompletionItem(Object id, String description, Map<String, Object> dataValues) {
        this(id, description, null, dataValues);
    }

    /**
     * @param id
     * @param description
     * @param dataObject
     */
    public AutoCompletionItem(Object id, String description, Object dataObject) {
        this(id, description, dataObject, null);
    }

    /**
     * @return the id
     */
    @Override
    public Object getId() {
        return id;
    }

    /**
     * @return the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return data values
     */
    @Override
    public Map<String, Object> getValuesMap() {
        if (dataValues == null || dataValues.isEmpty()) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(dataValues);
    }

    /**
     * @return keys
     */
    @Override
    public Collection<String> getKeys() {
        if (dataValues == null || dataValues.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableCollection(dataValues.keySet());
    }

    /**
     * @param key
     * @return key mapping value or null if not mapped
     */
    @Override
    public Object getKeyMappingValue(String key) {
        if (dataValues == null || dataValues.isEmpty()) {
            return null;
        }
        return dataValues.get(key);
    }

    /**
     * @return the dataObject
     */
    @Override
    public Object getDataObject() {
        return dataObject;
    }

    @Override
    public String toString() {
        return "AutoCompletionItem [id=" + id + ", description=" + description + ", dataValues=" + dataValues + ", dataObject=" + dataObject + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataObject == null) ? 0 : dataObject.hashCode());
        result = prime * result + ((dataValues == null) ? 0 : dataValues.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        AutoCompletionItem other = (AutoCompletionItem) obj;
        if (dataObject == null) {
            if (other.dataObject != null)
                return false;
        }
        else if (!dataObject.equals(other.dataObject))
            return false;
        if (dataValues == null) {
            if (other.dataValues != null)
                return false;
        }
        else if (!dataValues.equals(other.dataValues))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

}
