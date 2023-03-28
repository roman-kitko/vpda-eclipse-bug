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
import java.text.MessageFormat;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.Clearable;
import org.vpda.common.service.ServiceInfo;
import org.vpda.common.service.ServiceKind;

/**
 * Impl for {@link LocalizationService} that uses cache and
 * {@link LocValueBuilderFactory} to localize values
 * 
 * @author kitko
 *
 */
@ServiceInfo(kind = ServiceKind.Stateless, clientExportTypes = { LocalizationService.class })
public final class LocalizationServiceImpl implements LocalizationService, Clearable, Serializable {
    private static final long serialVersionUID = 3350821956036232814L;
    private final LocalizationServiceCache cache;
    private static final LocValue NOT_FOUND_VALUE = new StringLocValue("NotFound");
    private static final String NOT_FOUND_STRING = "NOT_FOUND";
    private final LocalizationValueResolver localizationValueResolver;
    private final LocValueBuilderFactory locValueBuilderFactory;

    static final String DEF_LOC_CACHE_MANAGER_NAME = "LOCALIZATION";

    /**
     * Creates LocalizationServiceImpl
     * 
     * @param localizationValueResolver
     * @param locValueBuilderFactory
     * @param cache
     * 
     */
    public LocalizationServiceImpl(LocalizationValueResolver localizationValueResolver, LocValueBuilderFactory locValueBuilderFactory, LocalizationServiceCache cache) {
        super();
        if (localizationValueResolver == null) {
            throw new IllegalArgumentException("localizationValueResolver is null");
        }
        if (locValueBuilderFactory == null) {
            locValueBuilderFactory = new LocValueBuilderRegistry();
        }
        this.localizationValueResolver = localizationValueResolver;
        this.locValueBuilderFactory = locValueBuilderFactory;
        this.cache = cache != null ? cache : new EHCacheLikeLocalizationServiceCacheImpl();
    }

    /**
     * @param localizationValueResolver
     * @param locValueBuilderFactory
     */
    public LocalizationServiceImpl(LocalizationValueResolver localizationValueResolver, LocValueBuilderFactory locValueBuilderFactory) {
        this(localizationValueResolver, locValueBuilderFactory, null);
    }

    /**
     * Creates LocalizationServiceImpl with {@link LocValueBuilderRegistry}
     * 
     * @param localizationValueResolver
     */
    public LocalizationServiceImpl(LocalizationValueResolver localizationValueResolver) {
        this(localizationValueResolver, new LocValueBuilderRegistry());
    }

    @Override
    public synchronized String localizeMessage(LocKey locKey, TenementalContext context, String defaultValue, Object... params) {
        String value = localizeString(locKey, context);
        if (value == null) {
            return defaultValue;
        }
        if (params != null && params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }

    @Override
    public synchronized String localizeMessage(LocKey locKey, TenementalContext context, Object... params) {
        return localizeMessage(locKey, context, null, params);
    }

    @Override
    public synchronized void clearData() {
        cache.clearData();
    }

    @Override
    public boolean containsLocData(LocKey locKey, TenementalContext context, LocValueBuilder<?> builder) {
        LocalizationCacheKeyWithClass key = new LocalizationCacheKeyWithClass(locKey, context, builder.getLocValueClass());
        if (cache.containsLocDataKey(key)) {
            return true;
        }
        return builder.containsLocValue(locValueBuilderFactory, this, locKey, context);
    }

    @Override
    public <T extends LocValue> T localizeData(LocKey locKey, TenementalContext context, LocValueBuilder<T> builder) {
        return localizeData(locKey, context, builder, null, LocDataArguments.EMPTY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized <T extends LocValue> T localizeData(LocKey locKey, TenementalContext context, LocValueBuilder<T> builder, T defaultValue, LocDataArguments params) {
        if (locKey == null) {
            throw new IllegalArgumentException("Key argument is null");
        }
        if (context == null) {
            throw new IllegalArgumentException("UsersContext argument is null");
        }
        T value = null;
        // 1 Look to cache if we have no params
        if (params == null || params.isEmpty()) {
            LocalizationCacheKeyWithClass cacheKey = new LocalizationCacheKeyWithClass(locKey, context, builder.getLocValueClass());
            value = (T) cache.getLocData(cacheKey);
            if (value != null) {
                if (value == NOT_FOUND_VALUE) {
                    return defaultValue;
                }
                else {
                    return value;
                }
            }
            value = builder.localizeData(locValueBuilderFactory, this, locKey, context, params);
            cache.putLocData(cacheKey, value != null ? value : NOT_FOUND_VALUE);
        }
        else {
            value = builder.localizeData(locValueBuilderFactory, this, locKey, context, params);
        }
        return value != null ? value : defaultValue;
    }

    @Override
    public synchronized String localizeString(LocKey locKey, TenementalContext context) {
        return localizeString(locKey, context, null);
    }

    @Override
    public String localizeString(LocKey locKey, TenementalContext context, String defaultValue) {
        if (locKey == null) {
            throw new IllegalArgumentException("Key argument is null");
        }
        if (context == null) {
            throw new IllegalArgumentException("UsersContext argument is null");
        }
        // 1 Look to cache
        LocalizationCacheKey cacheKey = new LocalizationCacheKey(locKey, context);
        String value = cache.getStringData(cacheKey);
        if (value != null) {
            return value == NOT_FOUND_STRING ? defaultValue : value;
        }
        else {
            value = localizationValueResolver.resolveValue(locKey, context);
            cache.putStringData(cacheKey, value != null ? value : NOT_FOUND_STRING);
            return value != null ? value : defaultValue;
        }
    }

    @Override
    public LocValueBuilderFactory getLocValueBuilderFactory() {
        return locValueBuilderFactory;
    }

}
