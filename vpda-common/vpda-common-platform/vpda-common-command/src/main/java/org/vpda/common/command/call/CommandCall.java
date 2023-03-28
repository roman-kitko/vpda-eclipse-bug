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
package org.vpda.common.command.call;

import java.io.Serializable;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.internal.common.util.Assert;

/**
 * Command that will resolve another command from {@link CommandExecutionEnv}
 * and will execute this command
 * 
 * @author kitko
 * @param <T> type of called command
 *
 */
public final class CommandCall<T> implements Command<T>, Serializable {
    private static final long serialVersionUID = 661575941098482505L;
    private final Call call;

    /**
     * Creates command call
     * 
     * @param call
     */
    public CommandCall(Call call) {
        this.call = Assert.isNotNullArgument(call, "call");
    }

    @SuppressWarnings("unchecked")
    @Override
    public T execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        CommandFactory commandFactory = env.resolveObject(CommandFactory.class);
        if (commandFactory == null) {
            throw new IllegalArgumentException("Cannot resolve commandFactory");
        }
        Command<?> createCommand = commandFactory.createCommand(call);
        return (T) executor.executeCommand(createCommand, env, event);
    }

    /**
     * @return the call
     */
    public Call getCall() {
        return call;
    }

}
