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
package org.vpda.common.core.shutdown;

import java.util.List;

import org.vpda.common.command.Command;

/**
 * In this registry we can register commands, that will be executed while
 * shutting down
 * 
 * @author kitko
 *
 */
public interface ShutdownRegistry {
    /**
     * Adds command to end of commands with same level
     * 
     * @param <T>     - type of command
     * @param command
     * @param level
     */
    public <T> void addShutdownCommand(Command<T> command, ShutdownLevel level);

    /**
     * Adds command to concrete position in list of commands with same level
     * 
     * @param <T>      - type of command
     * @param command
     * @param level
     * @param position
     */
    public <T> void addShutdownCommand(Command<T> command, ShutdownLevel level, int position);

    /**
     * 
     * @param level
     * @return List of commands for level
     */
    public List<Command<?>> getCommandsForLevel(ShutdownLevel level);

    /**
     * Perform shutdown
     *
     */
    public void executeShutdown();
}
