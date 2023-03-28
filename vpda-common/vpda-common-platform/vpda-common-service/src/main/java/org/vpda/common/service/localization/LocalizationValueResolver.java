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

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;

/**
 * This is resolver of localization values by key and context. It can get values
 * from any source - files,maps,db
 * 
 * @author kitko
 *
 */
public interface LocalizationValueResolver {

    /**
     * Will resolve value with key and context
     * 
     * @param key
     * @param context
     * @return localized value
     */
    public String resolveValue(LocKey key, TenementalContext context);
}
