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
package org.vpda.clientserver.viewprovider;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.comps.CurrentData;
import org.vpda.internal.common.util.Assert;

/**
 * View provider caller item
 * 
 * @author kitko
 * @param <T> - type of current data
 *
 */
public final class ViewProviderCallerItem<T extends CurrentData> implements Serializable {
    private static final Collection<String> EMPTY_KEYS = Collections.emptySet();
    private static final long serialVersionUID = -1072854998693552884L;
    private T currentData;
    private final ViewProviderID providerID;
    private Map<String, Object> formattedCurrentData;
    private Map<String, Object> properties;

    /**
     * @return current data
     */
    public T getCurrentData() {
        return currentData;
    }

    /**
     * @return provider id
     */
    public ViewProviderID getProviderId() {
        return providerID;
    }

    /**
     * @param providerID
     * @param currentData
     */
    public ViewProviderCallerItem(ViewProviderID providerID, T currentData) {
        this.providerID = Assert.isNotNull(providerID, "ProviderId argument is null");
        this.currentData = currentData;
    }

    /**
     * @param <E>
     * @param clazz
     * @return cast data to class
     */
    public <E> E castCurrrentData(Class<E> clazz) {
        if (currentData == null) {
            return null;
        }
        if (clazz.isAssignableFrom(currentData.getClass())) {
            return clazz.cast(currentData);
        }
        throw new ClassCastException("Cannot cast curent data from class " + currentData.getClass() + " to class " + clazz);
    }

    /**
     * 
     * @return static properties
     */
    public Map<String, Object> getProperties() {
        return properties != null ? Collections.unmodifiableMap(properties) : Collections.emptyMap();
    }

    /**
     * @param properties
     */
    public void putProperties(Map<String, Object> properties) {
        if (properties == null) {
            return;
        }
        if (this.properties == null) {
            this.properties = new HashMap<>(properties);
        }
        else {
            this.properties.putAll(properties);
        }
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((currentData == null) ? 0 : currentData.hashCode());
        result = PRIME * result + ((providerID == null) ? 0 : providerID.hashCode());
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
        final ViewProviderCallerItem other = (ViewProviderCallerItem) obj;
        if (currentData == null) {
            if (other.currentData != null)
                return false;
        }
        else if (!currentData.equals(other.currentData))
            return false;
        if (providerID == null) {
            if (other.providerID != null)
                return false;
        }
        else if (!providerID.equals(other.providerID))
            return false;
        return true;
    }

    /**
     * @param key
     * @return current data value
     */
    public Object getCurrentDataValue(String key) {
        return formattedCurrentData != null ? formattedCurrentData.get(key) : null;
    }

    /**
     * @param key
     * @param data
     */
    public void setCurrentDataValue(String key, Object data) {
        if (formattedCurrentData == null) {
            formattedCurrentData = new HashMap<String, Object>(2);
        }
        formattedCurrentData.put(key, data);
    }

    /**
     * @param currentData
     */
    public void setCurrentData(T currentData) {
        this.currentData = currentData;
    }

    /**
     * @return current data keys
     */
    public Collection<String> getCurrentDataKeys() {
        return formattedCurrentData != null ? Collections.unmodifiableCollection(formattedCurrentData.keySet()) : EMPTY_KEYS;
    }

    /**
     * Updates current data from item
     * 
     * @param item
     */
    public void updateCurrentData(ViewProviderCallerItem<T> item) {
        if (item == this) {
            return;
        }
        this.currentData = item.getCurrentData();
        formattedCurrentData = null;
        if (!item.getCurrentDataKeys().isEmpty()) {
            for (String key : item.getCurrentDataKeys()) {
                setCurrentDataValue(key, item.getCurrentDataValue(key));
            }
        }
    }

    @Override
    public String toString() {
        return "ViewProviderCallerItem [currentData=" + currentData + ", providerID=" + providerID + ", formattedCurrentData=" + formattedCurrentData + "]";
    }

}
