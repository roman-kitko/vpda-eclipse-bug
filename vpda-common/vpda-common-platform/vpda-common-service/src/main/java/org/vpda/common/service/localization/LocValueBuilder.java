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
 * This builder will produce specific LocData for key and context using provided
 * locService
 * 
 * @author kitko
 * @param <T> - type of value builder produces
 *
 */
public interface LocValueBuilder<T extends LocValue> extends Serializable {
    /**
     * @param locValueBuilderFactory
     * @param locService
     * @param locKey
     * @param context
     * @param args
     * @return localized data or null if not found
     */
    public T localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments args);

    /**
     * @param locValueBuilderFactory
     * @param locService
     * @param locKey
     * @param context
     * @return true if loc service contains loc key
     */
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context);

    /**
     * @return LocValue that this builder will buil
     */
    public Class<T> getLocValueClass();
}
