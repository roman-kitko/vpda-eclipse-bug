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
import java.util.concurrent.Callable;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Command executor that will resolve real executor just when needed using
 * passed {@link Callable}
 * 
 * @author kitko
 *
 */
public final class LazyDelegateCommandExecutor implements CommandExecutor, Serializable {
    private static final long serialVersionUID = -6466580191985879266L;
    private final Callable<CommandExecutor> realExecutorCall;
    private final boolean resolveAlways;
    private CommandExecutor resolved;

    /**
     * Creates Executor that will always resolve real executor, eg. call
     * {@link Callable#call()}
     * 
     * @param realExecutorCall
     */
    public LazyDelegateCommandExecutor(Callable<CommandExecutor> realExecutorCall) {
        this.realExecutorCall = realExecutorCall;
        this.resolveAlways = true;
    }

    /**
     * Creates Executor that will resolve real executor, eg. call
     * {@link Callable#call()} based on passed resolveAlways param.
     * 
     * @param realExecutorCall
     * @param resolveAlways
     */
    public LazyDelegateCommandExecutor(Callable<CommandExecutor> realExecutorCall, boolean resolveAlways) {
        this.realExecutorCall = realExecutorCall;
        this.resolveAlways = resolveAlways;
    }

    private CommandExecutor resolveExecutor() {
        if (resolveAlways) {
            CommandExecutor resolved;
            try {
                resolved = realExecutorCall.call();
            }
            catch (Exception e) {
                throw new VPDARuntimeException("Cannot resolve executor", e);
            }
            return resolved;
        }
        if (this.resolved != null) {
            return resolved;
        }
        try {
            resolved = realExecutorCall.call();
        }
        catch (Exception e) {
            throw new VPDARuntimeException("Cannot resolve executor", e);
        }
        return resolved;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return resolveExecutor();
    }

    @Override
    public void unreferenced() {
        resolveExecutor().unreferenced();
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        return resolveExecutor().executeCommand(command, env, event);
    }

    @Override
    public String getExecutorId() {
        return resolveExecutor().getExecutorId();
    }

}
