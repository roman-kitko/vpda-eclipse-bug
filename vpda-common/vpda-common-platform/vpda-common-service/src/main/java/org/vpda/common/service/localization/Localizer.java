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

/**
 * Can localize passed object
 * 
 * @author kitko
 *
 * @param <T>
 */
public interface Localizer<T> {

    /**
     * Will localize passed object. It can return back same object, or if object is
     * immutable new localized instance
     * 
     * @param t
     * @param localizationService
     * @param context
     * @return localized object.
     */
    public T localize(T t, LocalizationService localizationService, TenementalContext context);
}
