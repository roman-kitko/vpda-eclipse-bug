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

public final class MemURLsLocalizationValueResolverCacheImpl implements URLsLocalizationValueResolverCache {

    private final ConcurrentMap<URLLocCacheKey, String> cache = new ConcurrentHashMap<>();

    @Override
    public void clearData() {
        cache.clear();

    }

    @Override
    public String getData(URLLocCacheKey key) {
        return cache.get(key);
    }

    @Override
    public void putData(URLLocCacheKey key, String value) {
        cache.put(key, value);

    }

    @Override
    public boolean putIfAbsent(URLLocCacheKey key, String value) {
        String old = cache.putIfAbsent(key, value);
        return old == null;
    }

}
