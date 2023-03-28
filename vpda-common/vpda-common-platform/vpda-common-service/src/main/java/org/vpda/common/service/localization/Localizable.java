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
 * Objects implementing this interface declare, that they are localizable. That
 * means object will set its internal properties localized by localization
 * service. Such objects are by definition mutable. Object does not need to
 * remember contexts it was localized by
 * 
 * @author kitko
 *
 */
public interface Localizable {
    /**
     * 
     * @return true if object is already localized
     */
    public boolean isLocalized();

    /**
     * Localize object
     * 
     * @param localizationService
     * @param context
     */
    public void localize(LocalizationService localizationService, TenementalContext context);

    /**
     * Reset localized, so new call to localize will really localize object
     *
     */
    public void resetLocalized();
}
