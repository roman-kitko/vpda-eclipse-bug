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
package org.vpda.common.command.executor.impl;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.util.DelegatedObject;
import org.vpda.internal.common.util.Assert;

/**
 * 
 * This executor will just delegate execution to passed real command executor
 * 
 * @author kitko
 *
 */
public class CommandExecutorDelegate implements CommandExecutor, DelegatedObject<CommandExecutor> {
    private final CommandExecutor realExecutor;
    private final String id;

    /**
     * @param realExecutor
     */
    public CommandExecutorDelegate(CommandExecutor realExecutor) {
        this.realExecutor = Assert.isNotNull(realExecutor, "Real executor is null");
        this.id = realExecutor.getExecutorId();
    }

    /**
     * @param realExecutor
     * @param id
     */
    public CommandExecutorDelegate(CommandExecutor realExecutor, String id) {
        this.realExecutor = Assert.isNotNull(realExecutor, "Real executor is null");
        this.id = id;
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        return realExecutor.executeCommand(command, env, event);
    }

    @Override
    public void unreferenced() {
        realExecutor.unreferenced();
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return this;
    }

    @Override
    public String getExecutorId() {
        return id;
    }

    @Override
    public CommandExecutor getDelegatedObject() {
        return realExecutor;
    }

}
