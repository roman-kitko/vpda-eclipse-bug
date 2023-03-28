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
package org.vpda.common.service.preferences;

import java.util.List;
import java.util.UUID;

import org.vpda.common.context.ApplContext;
import org.vpda.common.context.User;
import org.vpda.common.service.Service;

/**
 * Service to get and create {@link AbstractContextPreferences} preferences
 * 
 * @author kitko
 *
 * @param <T>
 */
public interface AbstractContextPreferencesService<T extends AbstractContextPreferences> extends Service {
    /**
     * Find Preferences by id
     * 
     * @param id
     * @return preferences
     */
    public T findById(Long id);

    /**
     * Find preferences by uuid
     * 
     * @param uuid
     * @return preferences
     */
    public T findByUUID(UUID uuid);

    /**
     * Find preferences by context and name
     * 
     * @param ctx
     * @param name
     * @return preferences
     */
    public List<? extends T> findByContextAndName(ApplContext ctx, String name);

    /**
     * Find all preferences created by creator
     * 
     * @param creator
     * @return all preferences
     */
    public List<? extends T> findByCreator(User creator);

    /**
     * Gets all he is creator or his context is in path of context of that settings
     * 
     * @param user
     * @return list of all preferences for user
     */
    public List<? extends T> findAllForUser(User user);

    /**
     * Creates new preferences
     * 
     * @param request
     * @return created preferences
     */
    public T create(T request);

    /**
     * Deletes preferences by uuid
     * 
     * @param uuid
     * @return deleted preferences
     */
    public T deleteByUUID(UUID uuid);

    /**
     * Updates preferences
     * 
     * @param pref
     * @return updated preferences
     */
    public T update(T pref);

}
