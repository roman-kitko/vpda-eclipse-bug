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
package org.vpda.clientserver.viewprovider.preferences;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.list.ListViewProviderSettings;

/**
 * Services for {@link ListViewProviderPreferences}
 * 
 * @author kitko
 *
 */
public interface ListViewProviderPreferencesService extends AbstractViewProviderPreferencesService<ListViewProviderPreferences> {
    /**
     * Store settings for user context
     * 
     * @param def
     * @param settings
     * @param name
     * @param description
     * @return ListViewProviderPreferences
     */
    public ListViewProviderPreferences storeSettingsForCurrentUser(ViewProviderDef def, ListViewProviderSettings settings, String name, String description);

    /**
     * Store settings for user context
     * 
     * @param preferences
     * @return ListViewProviderPreferences
     */
    public ListViewProviderPreferences storeSettingsForCurrentUser(ListViewProviderPreferences preferences);

}
