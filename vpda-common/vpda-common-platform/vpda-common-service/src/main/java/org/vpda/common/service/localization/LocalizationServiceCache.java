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
package org.vpda.common.service.localization;

import java.io.Serializable;

import org.vpda.common.service.Clearable;

/**
 * Cache for localization service
 * 
 * @author kitko
 *
 */
public interface LocalizationServiceCache extends Serializable, Clearable {
    /**
     * Will lookup Localization data
     * 
     * @param cacheKey
     * @return localization data or null if not found
     */
    public <T extends LocValue> T getLocData(LocalizationCacheKeyWithClass cacheKey);

    /**
     * @param cacheKey
     * @return true if we contain key
     */
    public boolean containsLocDataKey(LocalizationCacheKeyWithClass cacheKey);

    /**
     * Will store localization data
     * 
     * @param cacheKey
     * @param value
     */
    public <T extends LocValue> void putLocData(LocalizationCacheKeyWithClass cacheKey, T value);

    /**
     * Will get String data
     * 
     * @param cacheKey
     * @return String data
     */
    public String getStringData(LocalizationCacheKey cacheKey);

    /**
     * @param cacheKey
     * @return true if cache contains string data
     */
    public boolean containsStringData(LocalizationCacheKey cacheKey);

    /**
     * Will store String data
     * 
     * @param cacheKey
     * @param value
     */
    public void putStringData(LocalizationCacheKey cacheKey, String value);

}
