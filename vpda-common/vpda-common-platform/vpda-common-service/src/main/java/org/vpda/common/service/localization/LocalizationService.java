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
import org.vpda.common.context.localization.BasicLocalizationService;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.Service;

/**
 * Service for retrieving localized messages
 * 
 * @author kitko
 *
 */
public interface LocalizationService extends Service, BasicLocalizationService {

    /**
     * Localize message by key and context
     * 
     * @param locKey
     * @param context
     * @param args
     * @return Localized message or null if not found
     */
    public String localizeMessage(LocKey locKey, TenementalContext context, Object... args);

    /**
     * Localize message by key and context If result contains some {$}, it is
     * resolved against this service and systemParams service
     * 
     * @param locKey
     * @param context
     * @param defaultValue
     * @param args
     * @return localized value or defaultValue if not found
     */
    public String localizeMessage(LocKey locKey, TenementalContext context, String defaultValue, Object... args);

    /**
     * Localize data by key and context
     * 
     * @param <T>     - type of LocValue
     * @param locKey
     * @param context
     * @param builder
     * @return localized data or null if not found
     */
    public <T extends LocValue> T localizeData(LocKey locKey, TenementalContext context, LocValueBuilder<T> builder);

    /**
     * Localize data by key and context
     * 
     * @param <T>          - type of LocValue
     * @param locKey
     * @param context
     * @param builder
     * @param defaultValue
     * @param args
     * @return localized data or default if not found
     */
    public <T extends LocValue> T localizeData(LocKey locKey, TenementalContext context, LocValueBuilder<T> builder, T defaultValue, LocDataArguments args);

    /**
     * Tests whether this localization service contains key
     * 
     * @param locKey
     * @param context
     * @param builder
     * @return true if key is found on selected path and context else false
     */
    public boolean containsLocData(LocKey locKey, TenementalContext context, LocValueBuilder<?> builder);

    /**
     * 
     * @return LocValueBuilderFactory
     */
    public LocValueBuilderFactory getLocValueBuilderFactory();

}
