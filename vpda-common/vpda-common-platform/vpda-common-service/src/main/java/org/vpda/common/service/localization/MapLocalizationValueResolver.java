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

import java.util.HashMap;
import java.util.Map;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;

/**
 * This localization value resolver will resolve values from map
 * 
 * @author rki
 *
 */
public final class MapLocalizationValueResolver implements LocalizationValueResolver {

    private static final class Key {
        private TenementalContext context;
        private LocKey key;

        private Key(LocKey key, TenementalContext context) {
            super();
            this.key = key;
            this.context = context;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((context == null) ? 0 : context.hashCode());
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
            final Key other = (Key) obj;
            if (key == null) {
                if (other.key != null)
                    return false;
            }
            else if (!key.equals(other.key))
                return false;
            if (context == null) {
                if (other.context != null)
                    return false;
            }
            else if (!context.equals(other.context))
                return false;
            return true;
        }
    }

    private final Map<Key, String> cache;

    /**
     * Creates empty resolver
     */
    public MapLocalizationValueResolver() {
        cache = new HashMap<Key, String>();
    }

    @Override
    public String resolveValue(LocKey key, TenementalContext context) {
        return cache.get(new Key(key, context));
    }

    /**
     * Puts value by key and context
     * 
     * @param key
     * @param context
     * @param value
     */
    public void put(LocKey key, TenementalContext context, String value) {
        cache.put(new Key(key, context), value);

    }

}
