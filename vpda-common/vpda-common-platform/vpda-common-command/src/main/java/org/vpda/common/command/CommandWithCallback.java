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

import org.vpda.internal.common.util.Assert;

/**
 * Command with callback
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class CommandWithCallback<T> implements Command<T> {

    private final Command<? extends T> cmd;
    private final CommandCallback<T> callback;
    private final CommandExecutorRegistry callBackRegistry;

    /**
     * Creates command with callback
     * 
     * @param cmd
     * @param callback
     */
    public CommandWithCallback(Command<? extends T> cmd, CommandCallback<T> callback) {
        this(cmd, callback, null);
    }

    /**
     * @param cmd
     * @param callback
     * @param callBackRegistry
     */
    public CommandWithCallback(Command<? extends T> cmd, CommandCallback<T> callback, CommandExecutorRegistry callBackRegistry) {
        super();
        this.cmd = Assert.isNotNullArgument(cmd, "cmd");
        this.callback = Assert.isNotNullArgument(callback, "callback");
        this.callBackRegistry = callBackRegistry;
    }

    @Override
    public T execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        // We will resolve executors
        CommandExecutor beforeExecutor = resolveExecutor(executor instanceof CommandExecutorRegistry ? (CommandExecutorRegistry) executor : null, callBackRegistry,
                CommandCallback.BEFORE_COMMAND_EXECUTOR_ID, executor);
        CommandExecutor afterExecutor = resolveExecutor(executor instanceof CommandExecutorRegistry ? (CommandExecutorRegistry) executor : null, callBackRegistry,
                CommandCallback.AFTER_COMMAND_EXECUTOR_ID, executor);
        CommandExecutor itselfExecutor = resolveExecutor(executor instanceof CommandExecutorRegistry ? (CommandExecutorRegistry) executor : null, callBackRegistry,
                CommandCallback.COMMAND_ITSELF_EXECUTOR_ID, executor);
        CommandExecutor exceptionExecutor = resolveExecutor(executor instanceof CommandExecutorRegistry ? (CommandExecutorRegistry) executor : null, callBackRegistry,
                CommandCallback.EXCEPTION_COMMAND_EXECUTOR_ID, executor);
        // And do callback before, then execute command and call after
        boolean shouldContinue = callback.beforeCommandExecute(beforeExecutor, this, env, event);
        if (!shouldContinue) {
            return null;
        }
        ExecutionResult<T> result = null;
        try {
            result = itselfExecutor.executeCommand(cmd, env, event);
        }
        catch (Exception e) {
            callback.afterCommandExecuteFailed(exceptionExecutor, env, event, e);
            return null;
        }
        T newResult = callback.afterCommandExecute(afterExecutor, env, event, result.get());
        return newResult;
    }

    private CommandExecutor resolveExecutor(CommandExecutorRegistry registry1, CommandExecutorRegistry registry2, String id, CommandExecutor def) {
        CommandExecutor result = null;
        if (result == null && registry1 != null) {
            result = registry1.getRegisteredCommandExecutor(id);
        }
        if (result == null && registry2 != null) {
            result = registry2.getRegisteredCommandExecutor(id);
        }
        if (result == null) {
            result = def;
        }
        return result;
    }

}
