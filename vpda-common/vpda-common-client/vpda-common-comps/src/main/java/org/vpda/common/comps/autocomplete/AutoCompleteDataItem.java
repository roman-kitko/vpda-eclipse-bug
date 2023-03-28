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
package org.vpda.common.comps.autocomplete;

import java.util.Collection;
import java.util.Map;

/**
 * AutoCompletetion single item
 * 
 * @author kitko
 *
 */
public interface AutoCompleteDataItem {
    /**
     * @return the id
     */
    public Object getId();

    /**
     * @return the description
     */
    public String getDescription();

    /**
     * @return data values
     */
    public Map<String, Object> getValuesMap();

    /**
     * @return keys
     */
    public Collection<String> getKeys();

    /**
     * @param key
     * @return key mapping value or null if not mapped
     */
    public Object getKeyMappingValue(String key);

    /**
     * @return the dataObject
     */
    public Object getDataObject();

}
