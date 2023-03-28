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
package org.vpda.common.command;

import java.util.Collection;

/**
 * Registry for Command executors.
 * 
 * @author kitko
 *
 */
public interface CommandExecutorRegistry {

    /**
     * Returns CommandExecutor by is
     * 
     * @param commandexecutorId
     * @return CommandExecutor by its id
     */
    public abstract CommandExecutor getRegisteredCommandExecutor(String commandexecutorId);

    /**
     * Register command executor by its own id
     * 
     * @param commandExecutor
     */
    public abstract void registerCommandExecutor(CommandExecutor commandExecutor);

    /**
     * Register command executor by passed id
     * 
     * @param id
     * @param commandExecutor
     */
    public abstract void registerCommandExecutor(String id, CommandExecutor commandExecutor);

    /**
     * 
     * @return all executors ids
     */
    public abstract Collection<String> getExecutorsIds();

}
