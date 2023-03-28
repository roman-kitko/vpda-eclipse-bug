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
package org.vpda.common.comps.ui.def;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.annotations.Immutable;

/**
 * Values directly completed by fetch
 * 
 * @author kitko
 *
 */
@Immutable
public final class SingleFetchCompletedValues implements Serializable {
    private static final long serialVersionUID = -4262552837961688197L;
    private final String fetchId;
    private final String idField;
    private final Map<String, Object> filledValues;

    /**
     * Creates FetchCompletedValues
     * 
     * @param fetchId
     * @param idField
     * @param filledValues
     */
    public SingleFetchCompletedValues(String fetchId, String idField, Map<String, Object> filledValues) {
        this.fetchId = fetchId;
        this.idField = idField;
        this.filledValues = new HashMap<String, Object>(filledValues);
    }

    /**
     * @return keys
     */
    public Collection<String> getKeys() {
        return Collections.unmodifiableSet(filledValues.keySet());
    }

    /**
     * @param key
     * @return filled value
     */
    public Object getFilledValue(String key) {
        return filledValues.get(key);
    }

    /**
     * @return id value
     */
    public Object getIdValue() {
        return filledValues.get(idField);
    }

    /**
     * @return the fetchId
     */
    public String getFetchId() {
        return fetchId;
    }

    /**
     * @return the idField
     */
    public String getIdField() {
        return idField;
    }

    @Override
    public String toString() {
        return "SingleFetchCompletedValues [fetchId=" + fetchId + ", idField=" + idField + ", filledValues=" + filledValues + "]";
    }

}
