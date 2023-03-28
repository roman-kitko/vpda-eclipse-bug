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

import javax.cache.Cache;

import org.vpda.common.cache.CacheBootstrap;
import org.vpda.internal.common.util.Assert;

/**
 * EHCache impl of {@link LocalizationServiceCache}
 * 
 * @author kitko
 *
 */
public final class EHCacheLikeLocalizationServiceCacheImpl implements LocalizationServiceCache {
    private static final long serialVersionUID = 1L;
    private transient Cache<LocalizationCacheKeyWithClass, LocValue> locDataCache;
    private transient Cache<LocalizationCacheKey, String> stringCache;
    private final String cacheManagerName;

    /**
     * Creates EHCacheLikeLocalizationServiceCacheImpl
     * 
     * @param cacheManagerName
     * @param stringCacheName
     * @param locDataCacheName
     */
    public EHCacheLikeLocalizationServiceCacheImpl(String cacheManagerName, String stringCacheName, String locDataCacheName) {
        this.cacheManagerName = Assert.isNotNullArgument(cacheManagerName, "cacheManagerName");
        stringCache = CacheBootstrap.getOrCreateDefaultCfgCacheForManager(cacheManagerName, stringCacheName, LocalizationCacheKey.class, String.class);
        locDataCache = CacheBootstrap.getOrCreateDefaultCfgCacheForManager(cacheManagerName, locDataCacheName, LocalizationCacheKeyWithClass.class, LocValue.class);
    }

    /**
     * Creates EHCacheLikeLocalizationServiceCacheImpl
     */
    public EHCacheLikeLocalizationServiceCacheImpl() {
        this(LocalizationServiceImpl.DEF_LOC_CACHE_MANAGER_NAME, "LOCALIZATION-STRINGS", "LOCALIZATION-DATA");
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(locDataCache.getName());
        out.writeUTF(stringCache.getName());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String locDataCacheName = in.readUTF();
        String stringCacheName = in.readUTF();
        locDataCache = CacheBootstrap.getOrCreateDefaultCfgCacheForManager(cacheManagerName, locDataCacheName, LocalizationCacheKeyWithClass.class, LocValue.class);
        stringCache = CacheBootstrap.getOrCreateDefaultCfgCacheForManager(cacheManagerName, stringCacheName, LocalizationCacheKey.class, String.class);
    }

    @Override
    public void clearData() {
        locDataCache.clear();
        stringCache.clear();

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends LocValue> T getLocData(LocalizationCacheKeyWithClass cacheKey) {
        return (T) locDataCache.get(cacheKey);
    }

    @Override
    public <T extends LocValue> void putLocData(LocalizationCacheKeyWithClass cacheKey, T value) {
        locDataCache.put(cacheKey, value);
    }

    @Override
    public String getStringData(LocalizationCacheKey cacheKey) {
        return stringCache.get(cacheKey);
    }

    @Override
    public void putStringData(LocalizationCacheKey cacheKey, String value) {
        stringCache.put(cacheKey, value);
    }

    @Override
    public boolean containsLocDataKey(LocalizationCacheKeyWithClass cacheKey) {
        return locDataCache.containsKey(cacheKey);
    }

    @Override
    public boolean containsStringData(LocalizationCacheKey cacheKey) {
        return stringCache.containsKey(cacheKey);
    }

}
