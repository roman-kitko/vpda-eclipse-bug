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
package org.vpda.abstractclient.viewprovider.ui.commands;

import java.io.Serializable;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.ResultWithCommands;

/**
 * This command will try to resolve provider executor and call execute. It will
 * pass command to server created with {@link ViewProviderUICommandCreator} It
 * is executed on client side.
 * 
 * @author kitko
 * @param <T> - kind of comamnd to execute
 *
 */
public final class ProviderExecuteCommand<T> implements Command<T>, Serializable {
    private static final long serialVersionUID = -3421701405765468348L;
    private final ViewProviderUICommandCreator viewProviderUICommandCreator;

    /**
     * @param viewProviderUICommandCreator
     * 
     */
    public ProviderExecuteCommand(ViewProviderUICommandCreator viewProviderUICommandCreator) {
        if (viewProviderUICommandCreator == null) {
            throw new IllegalArgumentException("Command creator argument is null");
        }
        this.viewProviderUICommandCreator = viewProviderUICommandCreator;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        ViewProviderUI viewProviderUI = env.resolveObject(ViewProviderUI.class);
        if (viewProviderUI == null || viewProviderUI.isDisposed()) {
            return null;
        }
        Command commandToExecute = viewProviderUICommandCreator.createCommand(viewProviderUI);
        ExecutionResult<T> result = viewProviderUI.getViewProvider().executeCommand(commandToExecute, null, event);
        if (result == null) {
            return null;
        }
        T resGet = result.get();
        if (resGet instanceof Command) {
            viewProviderUI.getCommandExecutor().executeCommand((Command<? extends T>) resGet, env, event);
        }
        else if (resGet instanceof ResultWithCommands) {
            ResultWithCommands<?> resWithCommands = (ResultWithCommands) resGet;
            if (resWithCommands.isExecuteCommandsOnReturn()) {
                for (Command cmd : resWithCommands.getCommands()) {
                    viewProviderUI.getCommandExecutor().executeCommand((Command<? extends T>) cmd, env, event);
                }
            }
        }
        return resGet;
    }

}
