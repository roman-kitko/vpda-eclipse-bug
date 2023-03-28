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

/**
 * Key to localization cache
 * 
 * @author kitko
 *
 */
public final class LocalizationCacheKey implements Serializable {
    private static final long serialVersionUID = 6206380407791926104L;
    private final LocKey key;
    private final TenementalContext context;

    /**
     * @param key
     * @param context
     */
    public LocalizationCacheKey(LocKey key, TenementalContext context) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        this.key = key;
        this.context = context;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocalizationCacheKey other = (LocalizationCacheKey) obj;
        if (context == null) {
            if (other.context != null)
                return false;
        }
        else if (!context.equals(other.context))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        }
        else if (!key.equals(other.key))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Key = " + key + " Context = " + context;
    }

}
