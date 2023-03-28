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
import org.vpda.internal.common.util.ObjectUtil;

/**
 * Key to localization cache
 * 
 * @author kitko
 *
 */
public final class LocalizationCacheKeyWithClass implements Serializable {
    private static final long serialVersionUID = 8710480340231222653L;
    private final LocKey key;
    private final TenementalContext context;
    private final Class<? extends LocValue> clazz;

    /**
     * @param key
     * @param context
     * @param clazz
     */
    public LocalizationCacheKeyWithClass(LocKey key, TenementalContext context, Class<? extends LocValue> clazz) {
        super();
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        this.key = key;
        this.context = context;
        this.clazz = clazz;
    }

    /**
     * @return Returns the context.
     */
    public TenementalContext getContext() {
        return context;
    }

    /**
     * @return Returns the key.
     */
    public LocKey getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocalizationCacheKeyWithClass)) {
            return false;
        }
        LocalizationCacheKeyWithClass localizationCacheKey = (LocalizationCacheKeyWithClass) obj;
        return key.equals(localizationCacheKey.key) && ObjectUtil.equalsConsiderNull(context, localizationCacheKey.context) && ObjectUtil.equalsConsiderNull(clazz, localizationCacheKey.clazz);
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ ObjectUtil.hashCodeConsiderNull(context) ^ ObjectUtil.hashCodeConsiderNull(clazz);
    }

    @Override
    public String toString() {
        return "Key = " + key + " Context = " + context + " Class = " + clazz;
    }

}
