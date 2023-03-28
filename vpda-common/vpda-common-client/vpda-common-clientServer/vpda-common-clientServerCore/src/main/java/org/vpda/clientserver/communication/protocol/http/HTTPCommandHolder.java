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
package org.vpda.clientserver.communication.protocol.http;

import java.io.Serializable;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.internal.common.util.Assert;

/**
 * Command and event that fired execution HTTP base communication execution
 * 
 * @author kitko
 *
 */
public final class HTTPCommandHolder implements Serializable {
    private static final long serialVersionUID = 4961942555344770646L;
    private final Command command;
    private final CommandEvent event;
    private final ExecutorIdentifier identifier;

    /**
     * Creates CommandWithEvent
     * 
     * @param command
     * @param event
     * @param identifier
     */
    public HTTPCommandHolder(Command command, CommandEvent event, ExecutorIdentifier identifier) {
        super();
        this.command = command;
        this.event = event;
        this.identifier = Assert.isNotNullArgument(identifier, "identifier");
    }

    /**
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * @return the event
     */
    public CommandEvent getEvent() {
        return event;
    }

    /**
     * @return the ExecutorIdentifier
     */
    public ExecutorIdentifier getExecutorIdentifier() {
        return identifier;
    }

}
