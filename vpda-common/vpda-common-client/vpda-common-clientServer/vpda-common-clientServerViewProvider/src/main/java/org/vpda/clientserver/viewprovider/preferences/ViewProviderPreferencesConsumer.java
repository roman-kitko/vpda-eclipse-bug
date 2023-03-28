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

import java.util.List;

import org.vpda.clientserver.viewprovider.ViewProviderInfo;

/**
 * Consumer of {@link AbstractViewProviderPreferences} preferences. It has some
 * current preferences and can apply new preferences. Typical implementation can
 * be provider UI or some editor of list of preferences
 * 
 * @author kitko
 *
 * @param <V>
 * @param <S>
 */
public interface ViewProviderPreferencesConsumer<V extends ViewProviderInfo, S extends AbstractViewProviderPreferences> {

    /**
     * 
     * @return {@link ViewProviderInfo} object that can provide metadata of provider
     */
    public V getViewProviderInfo();

    /**
     * 
     * @return current preferences
     */
    public S getCurrentPreferences();

    /**
     * Apply current preferences
     * 
     * @param preferences
     */
    public void applyCurrentPreferences(S preferences);

    /**
     * Store preferences to server
     * 
     * @param settings
     * @return S
     */
    public S storePreferences(S settings);

    /**
     * 
     * @return all available preferences for current user
     */
    public List<S> findAvailablePreferencesForCurrentUser();
}
