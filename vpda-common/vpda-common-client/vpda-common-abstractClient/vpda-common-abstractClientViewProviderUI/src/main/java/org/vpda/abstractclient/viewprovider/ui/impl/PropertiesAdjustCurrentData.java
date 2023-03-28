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
package org.vpda.abstractclient.viewprovider.ui.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;

/**
 * @author kitko
 *
 */
public final class PropertiesAdjustCurrentData implements AdjustCurrentData, Serializable {
    private static final long serialVersionUID = 7923368042152379428L;
    private Map<String, Object> properties;

    /**
     * Set properties
     * 
     * @param properties
     */
    public PropertiesAdjustCurrentData(Map<String, Object> properties) {
        this.properties = new HashMap<>(properties);
    }

    /**
     * Creates properties with single key and value
     * 
     * @param key
     * @param value
     */
    public PropertiesAdjustCurrentData(String key, Object value) {
        this.properties = Collections.singletonMap(key, value);
    }

    @Override
    public void adjustCurrentData(ViewProviderCallerItem<?> item) {
        item.putProperties(properties);
    }

}
