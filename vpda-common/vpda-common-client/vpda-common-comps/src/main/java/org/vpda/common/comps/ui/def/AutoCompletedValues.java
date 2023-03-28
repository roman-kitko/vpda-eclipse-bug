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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Collection of {@link SingleAutoCompletedValues}
 * 
 * @author kitko
 *
 */
public final class AutoCompletedValues implements Serializable {
    private static final long serialVersionUID = 2729709674494158068L;
    private Map<String, SingleAutoCompletedValues> completedValues;

    /**
     * Creates AutoCompletedValues
     * 
     * @param completedValues
     */
    public AutoCompletedValues(Map<String, SingleAutoCompletedValues> completedValues) {
        this.completedValues = new ConcurrentHashMap<String, SingleAutoCompletedValues>();
    }

    /**
     * Creates FetchesCompletedValues
     */
    public AutoCompletedValues() {
    }

    /**
     * Adds single SingleAutoCompletedValues
     * 
     * @param v
     */
    public void addSingleAutoCompletedValues(SingleAutoCompletedValues v) {
        if (completedValues == null) {
            completedValues = new ConcurrentHashMap<String, SingleAutoCompletedValues>();
        }
        completedValues.put(v.getAutoCompleteId(), v);
    }

    /**
     * @param v
     */
    public void putSameValues(AutoCompletedValues v) {
        if (v == null || v.completedValues == null) {
            completedValues = null;
            return;
        }
        if (completedValues == null) {
            completedValues = new ConcurrentHashMap<String, SingleAutoCompletedValues>();
        }
        this.completedValues.putAll(v.completedValues);
    }

    /**
     * @param id
     * @return SingleAutoCompletedValues
     */
    public SingleAutoCompletedValues getSingleFetchCompletedValues(String id) {
        return completedValues != null ? completedValues.get(id) : null;
    }

    /**
     * @param id
     * @return SingleAutoCompletedValues
     */
    public SingleAutoCompletedValues getRequiredSingleAutoCompletedValues(String id) {
        if (completedValues == null) {
            throw new VPDARuntimeException("No SingleAutoCompletedValues with id " + id);
        }
        SingleAutoCompletedValues v = completedValues.get(id);
        if (v == null) {
            throw new VPDARuntimeException("No SingleAutoCompletedValues with id " + id);
        }
        return v;
    }

    /**
     * @return fetch ids
     */
    public Collection<String> getCompleteIds() {
        return completedValues != null ? Collections.unmodifiableCollection(completedValues.keySet()) : Collections.emptySet();
    }

    @Override
    public String toString() {
        return "AutoCompletedValues [completedValues=" + completedValues + "]";
    }

}
