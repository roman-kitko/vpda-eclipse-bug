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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class MemLocalizationServiceCacheImpl implements LocalizationServiceCache {

    private static final long serialVersionUID = -4887210870915604047L;

    private final ConcurrentMap<LocalizationCacheKeyWithClass, LocValue> locValueCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<LocalizationCacheKey, String> stringCache = new ConcurrentHashMap<>();

    @Override
    public void clearData() {
        locValueCache.clear();
        stringCache.clear();
    }

    @Override
    public <T extends LocValue> T getLocData(LocalizationCacheKeyWithClass cacheKey) {
        return (T) locValueCache.get(cacheKey);
    }

    @Override
    public boolean containsLocDataKey(LocalizationCacheKeyWithClass cacheKey) {
        return locValueCache.containsKey(cacheKey);
    }

    @Override
    public <T extends LocValue> void putLocData(LocalizationCacheKeyWithClass cacheKey, T value) {
        locValueCache.put(cacheKey, value);
    }

    @Override
    public String getStringData(LocalizationCacheKey cacheKey) {
        return stringCache.get(cacheKey);
    }

    @Override
    public boolean containsStringData(LocalizationCacheKey cacheKey) {
        return stringCache.containsKey(cacheKey);
    }

    @Override
    public void putStringData(LocalizationCacheKey cacheKey, String value) {
        stringCache.put(cacheKey, value);

    }

}
