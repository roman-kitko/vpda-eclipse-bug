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
import java.util.UUID;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.User;
import org.vpda.common.service.preferences.AbstractContextPreferencesService;

/**
 * Services for {@link AbstractViewProviderPreferences}
 * 
 * @author kitko
 *
 * @param <T>
 */
public interface AbstractViewProviderPreferencesService<T extends AbstractViewProviderPreferences> extends AbstractContextPreferencesService<T> {

    /**
     * 
     * @param def
     * @return all settings for def
     */
    public List<T> findProviderPreferencesByDef(ViewProviderDef def);

    /**
     * 
     * @param def
     * @param user
     * @return settings for def and user
     */
    public List<T> findProviderPreferencesByDefAndCreator(ViewProviderDef def, User user);

    /**
     * 
     * @param def
     * @param name
     * @return settings for def and name
     */
    public List<T> findProviderPreferencesByDefAndName(ViewProviderDef def, String name);

    /**
     * 
     * @param def
     * @param ctx
     * @return settings for def and ctx
     */
    public List<T> findProviderPreferencesByDefAndContext(ViewProviderDef def, ApplContext ctx);

    /**
     * @param def
     * @param user
     * @param name
     * @return settings for def, user and name
     */
    public List<T> findProviderPreferencesByDefAndCreatorAndName(ViewProviderDef def, User user, String name);

    /**
     * @param def
     * @param ctx
     * @param name
     * @return settings for def, user and name
     */
    public T findProviderPreferencesByDefAndContextAndName(ViewProviderDef def, ApplContext ctx, String name);

    /**
     * Will find last used or best matched preferences for current user
     * 
     * @param def
     * @return last user/best matched preferences
     */
    public T findLastOrBestProviderPreferencesForCurrentUser(ViewProviderDef def);

    /**
     * Will set last used preferences uuid for user
     * 
     * @param def
     * @param uuid
     */
    public void setLastUsedPreferencesForCurrentUser(ViewProviderDef def, UUID uuid);

    /**
     * @param def
     * @return available preferences uuids for current user
     */
    public List<T> findAvailablePreferencesForCurrentUser(ViewProviderDef def);

}
