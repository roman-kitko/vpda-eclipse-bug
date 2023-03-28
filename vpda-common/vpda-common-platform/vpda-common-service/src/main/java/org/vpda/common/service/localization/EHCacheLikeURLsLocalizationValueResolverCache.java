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

import java.io.IOException;
import java.io.Serializable;

import javax.cache.Cache;

import org.vpda.common.cache.CacheBootstrap;
import org.vpda.internal.common.util.Assert;

final class EHCacheLikeURLsLocalizationValueResolverCache implements URLsLocalizationValueResolverCache, Serializable {

    private static final long serialVersionUID = 6509739216195687516L;
    private transient Cache<URLLocCacheKey, String> valuesCache;
    private final String cacheManagerName;

    EHCacheLikeURLsLocalizationValueResolverCache(String cacheManagerName, String cacheName) {
        this.cacheManagerName = Assert.isNotNullArgument(cacheManagerName, "cacheManagerName");
        valuesCache = CacheBootstrap.getOrCreateDefaultCfgCacheForManager(cacheManagerName, cacheName, URLLocCacheKey.class, String.class);
    }

    EHCacheLikeURLsLocalizationValueResolverCache() {
        this(LocalizationServiceImpl.DEF_LOC_CACHE_MANAGER_NAME, "LOCALIZATION-FILES");
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String cacheName = in.readUTF();
        valuesCache = CacheBootstrap.getOrCreateDefaultCfgCacheForManager(cacheManagerName, cacheName, URLLocCacheKey.class, String.class);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(valuesCache.getName());
    }

    @Override
    public String getData(URLLocCacheKey key) {
        return valuesCache.get(key);
    }

    @Override
    public void putData(URLLocCacheKey key, String value) {
        valuesCache.put(key, value);
    }

    @Override
    public boolean putIfAbsent(URLLocCacheKey key, String value) {
        return valuesCache.putIfAbsent(key, value);
    }

    @Override
    public void clearData() {
        valuesCache.clear();
    }

}
