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

import org.vpda.common.service.Service;

/**
 * Registry of all AbstractContextPreferencesService
 * 
 * @author kitko
 *
 */
public interface ContextPreferencesServiceRegistry extends Service {
    /**
     * Creates AbstractContextPreferencesService for type
     * 
     * @param type
     * @return AbstractContextPreferences
     */
    public <T extends AbstractContextPreferences> AbstractContextPreferencesService<T> createConcretePreferencesService(Class<T> type);

    /**
     * @return list of all types
     */
    public List<Class<? extends AbstractContextPreferences>> getAllServiceTypes();
}
