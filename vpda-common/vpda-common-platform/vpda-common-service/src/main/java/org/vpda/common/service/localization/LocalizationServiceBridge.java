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

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.Clearable;

/**
 * This is Bridge of 2 Localization Services - primary and secondary Bridge
 * tries to localize value from primary localization service, then secondary For
 * performance reasons it caches all found values from both primary and
 * secondary caches
 * 
 * @author kitko
 *
 */
public final class LocalizationServiceBridge implements LocalizationService, Serializable, Clearable {
    private static final long serialVersionUID = 9200980690276211970L;
    private final LocalizationService primaryService;
    private final LocalizationService secondaryService;
    private final LocalizationServiceCache cache;
    private static LocValue NOT_FOUND_VALUE = new StringLocValue("NotFound");
    private static String NOT_FOUND_STRING = "NOT_FOUND";
    private final LocValueBuilderFactory locValueBuilderFactory;

    /**
     * Creates LocalizationServiceBridge
     * 
     * @param primaryService
     * @param secondaryService
     * @param locValueBuilderFactory
     * @param cache
     */
    public LocalizationServiceBridge(LocalizationService primaryService, LocalizationService secondaryService, LocValueBuilderFactory locValueBuilderFactory, LocalizationServiceCache cache) {
        super();
        if (primaryService == null) {
            throw new IllegalArgumentException("primaryService argument is null");
        }
        if (secondaryService == null) {
            throw new IllegalArgumentException("secondaryService argument is null");
        }
        this.primaryService = primaryService;
        this.secondaryService = secondaryService;
        this.locValueBuilderFactory = locValueBuilderFactory;
        this.cache = cache != null ? cache : new EHCacheLikeLocalizationServiceCacheImpl(LocalizationServiceImpl.DEF_LOC_CACHE_MANAGER_NAME, "LOCALIZATION-BRIDGE-STRINGS", "LOCALIZATION-BRIDGE-DATA");
    }

    /**
     * @param primaryService
     * @param secondaryService
     * @param locValueBuilderFactory
     */
    public LocalizationServiceBridge(LocalizationService primaryService, LocalizationService secondaryService, LocValueBuilderFactory locValueBuilderFactory) {
        this(primaryService, secondaryService, locValueBuilderFactory, null);
    }

    @Override
    public boolean containsLocData(LocKey locKey, TenementalContext context, LocValueBuilder<?> builder) {
        return cache.containsLocDataKey(new LocalizationCacheKeyWithClass(locKey, context, builder.getLocValueClass())) || primaryService.containsLocData(locKey, context, builder)
                || secondaryService.containsLocData(locKey, context, builder);
    }

    @Override
    public <T extends LocValue> T localizeData(LocKey locKey, TenementalContext context, LocValueBuilder<T> builder) {
        return localizeData(locKey, context, builder, null, LocDataArguments.EMPTY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LocValue> T localizeData(LocKey locKey, TenementalContext context, LocValueBuilder<T> builder, T defaultValue, LocDataArguments args) {
        T value = null;
        LocalizationCacheKeyWithClass cacheKey = null;
        if (args == null || args.isEmpty()) {
            cacheKey = new LocalizationCacheKeyWithClass(locKey, context, builder.getLocValueClass());
            synchronized (cache) {
                value = (T) cache.getLocData(cacheKey);
            }
        }
        if (value == null) {
            value = primaryService.localizeData(locKey, context, builder, defaultValue, args);
            if (value == null) {
                value = secondaryService.localizeData(locKey, context, builder, defaultValue, args);
            }
            if (args == null || args.isEmpty()) {
                synchronized (cache) {
                    cache.putLocData(cacheKey, value != null ? value : NOT_FOUND_VALUE);
                }
            }
        }
        if (value == null || value == NOT_FOUND_VALUE) {
            value = defaultValue;
        }
        return value;
    }

    @Override
    public String localizeString(LocKey locKey, TenementalContext context) {
        return localizeString(locKey, context, null);
    }

    @Override
    public String localizeString(LocKey locKey, TenementalContext context, String defaultValue) {
        LocalizationCacheKey cacheKey = new LocalizationCacheKey(locKey, context);
        String value = null;
        synchronized (cache) {
            value = cache.getStringData(cacheKey);
        }
        if (value == null) {
            value = primaryService.localizeString(locKey, context);
            if (value == null) {
                value = secondaryService.localizeString(locKey, context);
            }
            synchronized (cache) {
                cache.putStringData(cacheKey, value != null ? value : NOT_FOUND_STRING);
            }
        }
        if (value == NOT_FOUND_STRING) {
            value = null;
        }
        return value != null ? value : defaultValue;
    }

    @Override
    public synchronized String localizeMessage(LocKey locKey, TenementalContext context, String defaultValue, Object... args) {
        String value = null;
        LocalizationCacheKey cacheKey = null;
        if (args.length == 0) {
            synchronized (cache) {
                cacheKey = new LocalizationCacheKey(locKey, context);
                value = cache.getStringData(cacheKey);
            }
        }
        if (value == null) {
            value = primaryService.localizeMessage(locKey, context, null, args);
            if (value == null) {
                value = secondaryService.localizeMessage(locKey, context, null, args);
            }
            if (args.length == 0) {
                cache.putStringData(cacheKey, value != null ? value : NOT_FOUND_STRING);
            }
        }
        if (value == null || value == NOT_FOUND_STRING) {
            value = defaultValue;
        }
        return value;
    }

    @Override
    public String localizeMessage(LocKey locKey, TenementalContext context, Object... args) {
        return localizeMessage(locKey, context, null, args);
    }

    @Override
    public LocValueBuilderFactory getLocValueBuilderFactory() {
        return locValueBuilderFactory;
    }

    @Override
    public void clearData() {
        cache.clearData();
    }

}
