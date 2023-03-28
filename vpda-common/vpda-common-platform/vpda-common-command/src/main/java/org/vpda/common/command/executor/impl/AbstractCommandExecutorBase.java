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

import java.io.Serializable;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;

/**
 * This is base command executor
 * 
 * @author kitko
 *
 */
public abstract class AbstractCommandExecutorBase implements CommandExecutor, Serializable {
    private static final long serialVersionUID = 1805268809393921252L;
    private final String id;

    /**
     * Do not call directly, use container
     * 
     * @param id
     */
    protected AbstractCommandExecutorBase(String id) {
        this.id = id;
    }

    @Override
    public void unreferenced() {
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return this;
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        T result = command.execute(this, env, event);
        return new ExecutionResultBase<>(result);
    }

    @Override
    public String getExecutorId() {
        return id;
    }

}
