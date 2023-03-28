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
package org.vpda.internal.clientserver.communication;

import org.vpda.internal.common.util.CacheKeyCreator;
import org.vpda.internal.common.util.CacheKeyCreatorHolder;
import org.vpda.internal.common.util.StaticCache;

/**
 * Internal holder for module cache key
 * 
 * @author kitko
 *
 */
public final class CommunicationKeyHolder {

    /** CacheKeyCreatorHolder for Server */
    public static final CacheKeyCreatorHolder serverCacheKeyCreatorHolder = new CacheKeyCreatorHolder() {

        private CacheKeyCreator cacheKeyCreator = StaticCache.getGlobalKeyCreator();

        @Override
        public CacheKeyCreator getCacheKeyCreator() {
            return cacheKeyCreator;
        }

        @Override
        public void setCacheKeyCreator(CacheKeyCreator cacheKeyCreator) {
            this.cacheKeyCreator = cacheKeyCreator;
        }
    };

    /** CacheKeyCreatorHolder for Client */
    public static final CacheKeyCreatorHolder clientCacheKeyCreatorHolder = new CacheKeyCreatorHolder() {
        private CacheKeyCreator cacheKeyCreator = StaticCache.getGlobalKeyCreator();

        @Override
        public CacheKeyCreator getCacheKeyCreator() {
            return cacheKeyCreator;
        }

        @Override
        public void setCacheKeyCreator(CacheKeyCreator cacheKeyCreator) {
            this.cacheKeyCreator = cacheKeyCreator;
        }
    };

    private CommunicationKeyHolder() {
    }

}
