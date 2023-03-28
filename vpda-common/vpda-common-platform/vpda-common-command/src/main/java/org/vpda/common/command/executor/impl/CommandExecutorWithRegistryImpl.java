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
package org.vpda.common.command.executor.impl;

import java.io.Serializable;
import java.util.Collection;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.CommandExecutorWithRegistry;
import org.vpda.common.command.ExecutionResult;
import org.vpda.internal.common.util.Assert;

/**
 * Delegate imp, for CommandExecutorWithRegistry It will delegate calls to
 * {@link CommandExecutor} and {@link CommandExecutorRegistry}
 * 
 * @author kitko
 *
 */
public final class CommandExecutorWithRegistryImpl implements CommandExecutorWithRegistry, Serializable {
    private static final long serialVersionUID = -5777240098354972869L;
    private final CommandExecutor executor;
    private final CommandExecutorRegistry registry;

    /**
     * Creates CommandExecutorWithRegistryImpl
     * 
     * @param executor
     * @param registry
     */
    public CommandExecutorWithRegistryImpl(CommandExecutor executor, CommandExecutorRegistry registry) {
        super();
        this.executor = Assert.isNotNullArgument(executor, "executor");
        this.registry = Assert.isNotNullArgument(registry, "registry");
    }

    @Override
    public String getExecutorId() {
        return executor.getExecutorId();
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        return executor.executeCommand(command, env, event);
    }

    @Override
    public CommandExecutor getRegisteredCommandExecutor(String commandexecutorId) {
        return registry.getRegisteredCommandExecutor(commandexecutorId);
    }

    @Override
    public void registerCommandExecutor(String id, CommandExecutor commandExecutor) {
        registry.registerCommandExecutor(id, commandExecutor);
    }

    @Override
    public Collection<String> getExecutorsIds() {
        return registry.getExecutorsIds();
    }

    @Override
    public void registerCommandExecutor(CommandExecutor commandExecutor) {
        registry.registerCommandExecutor(commandExecutor);
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return executor.getCommandExecutor();
    }

    @Override
    public void unreferenced() {
        executor.unreferenced();
    }

}
