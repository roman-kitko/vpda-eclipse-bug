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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Result of any execution with commands. This commands will be executed on
 * client side
 * 
 * @author kitko
 * @param <T> - type of result
 *
 */
public final class ResultWithCommands<T> implements Serializable {
    private static final long serialVersionUID = 2836941832672433827L;
    private final T result;
    private final List<Command> commands;
    private final boolean executeCommandsOnReturn;

    /**
     * Creates ResultWithCommands
     * 
     * @param result
     * @param cmds
     */
    public ResultWithCommands(T result, Command... cmds) {
        this(result, false, cmds);
    }

    /**
     * Creates ResultWithCommands
     * 
     * @param result
     * @param executeCommandsOnReturn
     * @param cmds
     */
    public ResultWithCommands(T result, boolean executeCommandsOnReturn, Command... cmds) {
        this(result, executeCommandsOnReturn, Arrays.asList(cmds));
    }

    /**
     * Creates ResultWithCommands
     * 
     * @param result
     * @param commands
     */
    public ResultWithCommands(T result, List<Command> commands) {
        this(result, false, commands);
    }

    /**
     * Creates ResultWithCommands
     * 
     * @param result
     * @param executeCommandsOnReturn
     * @param commands
     */
    public ResultWithCommands(T result, boolean executeCommandsOnReturn, List<Command> commands) {
        this.result = result;
        this.executeCommandsOnReturn = executeCommandsOnReturn;
        this.commands = new ArrayList<>(commands);
    }

    /**
     * @return all commnds
     */
    public List<Command> getCommands() {
        List<Command> empty = Collections.emptyList();
        return commands != null ? Collections.unmodifiableList(commands) : empty;
    }

    /**
     * @return result;
     */
    public T getResult() {
        return result;
    }

    /**
     * 
     * @return true if we contains at leas one command
     */
    public boolean containsAnyCommand() {
        return commands != null && !commands.isEmpty();
    }

    /**
     * @return the executeCommandsOnReturn
     */
    public boolean isExecuteCommandsOnReturn() {
        return executeCommandsOnReturn;
    }

}
