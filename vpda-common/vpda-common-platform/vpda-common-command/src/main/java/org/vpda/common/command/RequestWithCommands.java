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
import java.util.Collections;
import java.util.List;

/**
 * Request for execution with commands. This commands will be executed on
 * server(caller) side
 * 
 * @author kitko
 * @param <T> - type of request
 *
 */
public final class RequestWithCommands<T> implements Serializable {
    private static final long serialVersionUID = 2836941832672433827L;
    private final T request;
    private final List<Command> commands;

    /**
     * @param request
     */
    public RequestWithCommands(T request) {
        super();
        this.request = request;
        this.commands = null;
    }

    /**
     * @param request
     * @param commands
     */
    public RequestWithCommands(T request, List<Command> commands) {
        super();
        this.request = request;
        this.commands = commands != null ? new ArrayList<Command>(commands) : null;
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
    public T getRequest() {
        return request;
    }

    /**
     * 
     * @return true if we contains at leas one command
     */
    public boolean containsAnyCommand() {
        return commands != null && !commands.isEmpty();
    }

}
