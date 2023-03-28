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
/**
 * 
 */
package org.vpda.common.command;

/**
 * Will execute commands in future, suing separate thread
 * 
 * @author kitko
 *
 */
public interface FutureCommandExecutor extends CommandExecutor {
    @Override
    public <T> FutureExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception;

    @Override
    public <T> FutureExecutionResult<T> executeCommandWithCallback(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event, CommandCallback<T> callback) throws Exception;
}
