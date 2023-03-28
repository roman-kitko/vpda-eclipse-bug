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
package org.vpda.common.command.env;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.vpda.common.command.CommandExecutionEnv;

/**
 * Dummy impl of CommandExecutionEnv which will return null for all resolutions
 * 
 * @author kitko
 *
 */
public final class EmptyCommandExecutionEnv implements CommandExecutionEnv, Serializable {
    private static final long serialVersionUID = 6591783254085727135L;
    private static EmptyCommandExecutionEnv instance;

    @Override
    public <T> boolean canResolveObject(Class<T> clazz) {
        return false;
    }

    @Override
    public <T> boolean canResolveObject(Class<T> clazz, Object key) {
        return false;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key) {
        return null;
    }

    @Override
    public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
        return null;
    }

    /**
     * Will create EmptyCommandExecutionEnv
     */
    private EmptyCommandExecutionEnv() {

    }

    /**
     * 
     * @return singleton instance
     */
    public static EmptyCommandExecutionEnv getInstance() {
        if (instance == null) {
            instance = new EmptyCommandExecutionEnv();
        }
        return instance;
    }

    @Override
    public Collection<?> getKeys() {
        return Collections.emptyList();
    }

}
