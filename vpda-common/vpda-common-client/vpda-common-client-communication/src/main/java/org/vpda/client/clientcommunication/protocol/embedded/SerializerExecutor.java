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
package org.vpda.client.clientcommunication.protocol.embedded;

import java.io.Serializable;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.internal.common.util.JavaSerializationUtil;

/**
 * Will serialize command and result
 * 
 * @author kitko
 *
 */
final class SerializerExecutor implements CommandExecutor, Serializable {
    private static final long serialVersionUID = 7397819011331969382L;
    private final CommandExecutor realExecutor;

    @SuppressWarnings("unchecked")
    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        Command newCommand = (Command) JavaSerializationUtil.copyObjectUsingSerialization((Serializable) command, Thread.currentThread().getContextClassLoader());
        ExecutionResult<T> res = realExecutor.executeCommand(newCommand, env, event);
        return res;
    }

    @Override
    public String getExecutorId() {
        return realExecutor.getExecutorId();
    }

    @Override
    public void unreferenced() {
        realExecutor.unreferenced();
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return this;
    }

    /**
     * @param realExecutor
     */
    SerializerExecutor(CommandExecutor realExecutor) {
        super();
        this.realExecutor = realExecutor;
    }

}
