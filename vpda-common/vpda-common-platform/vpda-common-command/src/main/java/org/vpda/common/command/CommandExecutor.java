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

/**
 * Executor for commands. All commands should be executed through this
 * interface. Each executor has its Id in {@link CommandExecutorRegistry}.
 * Executor responsibility is just to execute commands, it is not execution
 * environment holder.
 * 
 * @author kitko
 *
 */
public interface CommandExecutor extends CommandExecutorAccessor, Unreferenced {
    /**
     * Executes passed command using environment
     * 
     * @param <T>     type of command
     * @param command command to execute
     * @param env     execution environment command can use to fetch objects
     * @param event   Event that fired execution
     * @return result of command execution, never null
     * @throws Exception thrown during command execution
     */
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception;

    /**
     * 
     * @return id of this executor
     */
    public String getExecutorId();

    /**
     * Will execute command with callback
     * 
     * @param command
     * @param env
     * @param event
     * @param callback
     * @return execution result
     * @throws Exception
     */
    public default <T> ExecutionResult<T> executeCommandWithCallback(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event, CommandCallback<T> callback) throws Exception {
        CommandWithCallback<T> commandWithCallback = new CommandWithCallback<T>(command, callback);
        return executeCommand(commandWithCallback, env, event);
    }

}
