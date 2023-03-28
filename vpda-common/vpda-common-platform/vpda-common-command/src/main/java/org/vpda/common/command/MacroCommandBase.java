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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Macro command impl
 * 
 * @author kitko
 *
 */
public final class MacroCommandBase implements Command<List<ExecutionResult>>, Serializable {
    private static final long serialVersionUID = 4802567898790173128L;
    private final List<Command> commands;

    /**
     * Creates MacroCommand
     * 
     * @param cmds
     * 
     */
    public MacroCommandBase(Command... cmds) {
        super();
        commands = new ArrayList<Command>(2);
        for (Command command : cmds) {
            commands.add(command);
        }
    }

    /**
     * @param commands
     */
    public MacroCommandBase(List<? extends Command> commands) {
        super();
        this.commands = new ArrayList<Command>(commands);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final List<ExecutionResult> execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        List<ExecutionResult> result = new ArrayList<ExecutionResult>(commands.size());
        for (Command command : commands) {
            ExecutionResult res = executor.executeCommand(command, env, event);
            result.add(res);
        }
        return result;
    }
}
