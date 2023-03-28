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

import org.vpda.common.annotations.Immutable;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.internal.common.util.ObjectUtil;

/**
 * This is helper class for localizable data It holds loc key and value
 * 
 * @author kitko
 * @param <T> - type of localization value
 *
 */
@Immutable
public final class LocPair<T extends LocValue> implements LocKeyAccesible, Serializable {
    private static final long serialVersionUID = 351500821818419328L;
    private final LocKey locKey;
    private final T locValue;
    private final LocValueBuilder<T> locDataBuilder;

    /**
     * @return localized value
     */
    public T getLocValue() {
        return locValue;
    }

    @Override
    public String getKeyPart() {
        return locKey.getKey();
    }

    @Override
    public String getPathPart() {
        return locKey.getPath();
    }

    @Override
    public LocKey getLocKey() {
        return locKey;
    }

    /**
     * @param locKey
     * @return LocPair
     */
    public static LocPair<StringLocValue> createStringLocPair(LocKey locKey) {
        return new LocPair<StringLocValue>(locKey, null, StringLocValueBuilder.getInstance());
    }

    /**
     * @param locKey
     * @param locValue
     * @return LocPair<StringLocValue>
     */
    public static LocPair<StringLocValue> createStringLocPair(LocKey locKey, String locValue) {
        return new LocPair<StringLocValue>(locKey, new StringLocValue(locValue), StringLocValueBuilder.getInstance());
    }

    /**
     * @param path
     * @param key
     * @return LocPair
     */
    public static LocPair<StringLocValue> createStringLocPair(String path, String key) {
        return new LocPair<StringLocValue>(new LocKey(path, key), null, StringLocValueBuilder.getInstance());
    }

    /**
     * @param path
     * @param key
     * @param locValue
     * @return LocPair<StringLocValue>
     */
    public static LocPair<StringLocValue> createStringLocPair(String path, String key, String locValue) {
        return new LocPair<StringLocValue>(new LocKey(path, key), new StringLocValue(locValue), StringLocValueBuilder.getInstance());
    }

    /**
     * Creates LocPairImpl
     * 
     * @param locKey
     * @param locValue
     * @param locDataBuilder
     */
    public LocPair(LocKey locKey, T locValue, LocValueBuilder<T> locDataBuilder) {
        super();
        if (locKey == null) {
            throw new IllegalArgumentException("LocKey argument cannot be null");
        }
        if (locDataBuilder == null) {
            throw new IllegalArgumentException("LocDataBuilder argument cannot be null");
        }
        this.locKey = locKey;
        this.locDataBuilder = locDataBuilder;
        this.locValue = locValue;
    }

    /**
     * Creates new localized pair
     * 
     * @param localizationService
     * @param context
     * @return new LocPairImpl
     */
    public LocPair<T> localize(LocalizationService localizationService, TenementalContext context) {
        T lv = localizationService.localizeData(locKey, context, locDataBuilder, null, null);
        return new LocPair<T>(locKey, lv, locDataBuilder);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LocPair)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        LocPair locPair = (LocPair) o;
        if (!locPair.locKey.equals(locKey)) {
            return false;
        }
        return ObjectUtil.equalsConsiderNull(locValue, locPair.locValue);
    }

    @Override
    public int hashCode() {
        return locKey.hashCode();
    }

    @Override
    public String toString() {
        return locKey + " : " + locValue;
    }
}
