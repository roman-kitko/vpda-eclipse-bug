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
 * Collection of {@link SingleFetchCompletedValues}
 * 
 * @author kitko
 *
 */
public final class FetchesCompletedValues implements Serializable {
    private static final long serialVersionUID = 2729709674494158068L;
    private Map<String, SingleFetchCompletedValues> fetches;

    /**
     * Creates FetchesCompletedValues
     * 
     * @param fetches
     */
    public FetchesCompletedValues(Map<String, SingleFetchCompletedValues> fetches) {
        this.fetches = new ConcurrentHashMap<String, SingleFetchCompletedValues>();
    }

    /**
     * Creates FetchesCompletedValues
     */
    public FetchesCompletedValues() {
    }

    /**
     * Adds single SingleFetchCompletedValues
     * 
     * @param v
     */
    public void addSingleFetchCompletedValues(SingleFetchCompletedValues v) {
        if (fetches == null) {
            fetches = new ConcurrentHashMap<String, SingleFetchCompletedValues>();
        }
        fetches.put(v.getFetchId(), v);
    }

    /**
     * @param v
     */
    public void putSameFetches(FetchesCompletedValues v) {
        if (v == null || v.fetches == null) {
            fetches = null;
            return;
        }
        if (fetches == null) {
            fetches = new ConcurrentHashMap<String, SingleFetchCompletedValues>();
        }
        this.fetches.putAll(v.fetches);
    }

    /**
     * Get SingleFetchCompletedValues by id
     * 
     * @param id
     * @return SingleFetchCompletedValues
     */
    public SingleFetchCompletedValues getSingleFetchCompletedValues(String id) {
        return fetches != null ? fetches.get(id) : null;
    }

    /**
     * Remove SingleFetchCompletedValues by id
     * 
     * @param id
     * @return SingleFetchCompletedValues
     */
    public SingleFetchCompletedValues removeSingleFetchCompletedValues(String id) {
        return fetches != null ? fetches.remove(id) : null;
    }

    /**
     * @param id
     * @return SingleFetchCompletedValues
     */
    public SingleFetchCompletedValues getRequiredSingleFetchCompletedValues(String id) {
        if (fetches == null) {
            throw new VPDARuntimeException("No SingleFetchCompletedValues with id " + id);
        }
        SingleFetchCompletedValues v = fetches.get(id);
        if (v == null) {
            throw new VPDARuntimeException("No SingleFetchCompletedValues with id " + id);
        }
        return v;
    }

    /**
     * @return fetch ids
     */
    public Collection<String> getFetchIds() {
        return fetches != null ? Collections.unmodifiableCollection(fetches.keySet()) : Collections.emptySet();
    }

    @Override
    public String toString() {
        return "FetchesCompletedValues [fetches=" + fetches + "]";
    }

}
