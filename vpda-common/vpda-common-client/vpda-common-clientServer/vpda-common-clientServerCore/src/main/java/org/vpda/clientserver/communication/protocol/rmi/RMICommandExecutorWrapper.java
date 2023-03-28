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
package org.vpda.clientserver.communication.protocol.rmi;

import java.io.Serializable;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;

/**
 * RMI Client to server communication wrapper. It delegates all calls to
 * rmiCommandExecutor. At first connect phase, it is constructed by client. Then
 * it is constructed by server when we need to export a remote service
 * 
 * @author kitko
 *
 */
public final class RMICommandExecutorWrapper implements CommandExecutor, Serializable {
    private static final long serialVersionUID = 578743274797088017L;
    private final RMICommandExecutor rmiCommandExecutor;
    private final String executorId;

    /**
     * @param executorId
     * @param rmiCommandExecutor
     * 
     */
    public RMICommandExecutorWrapper(String executorId, RMICommandExecutor rmiCommandExecutor) {
        this.executorId = executorId;
        this.rmiCommandExecutor = rmiCommandExecutor;
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        return rmiCommandExecutor.executeCommand(command, event);
    }

    /**
     * 
     * @return RMICommandExecutor
     */
    public RMICommandExecutor getRMICommandExecutor() {
        return rmiCommandExecutor;
    }

    @Override
    public String toString() {
        return "RMICommandExecutorWrapper";
    }

    @Override
    public String getExecutorId() {
        return executorId;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return this;
    }

    @Override
    public void unreferenced() {
    }

}
