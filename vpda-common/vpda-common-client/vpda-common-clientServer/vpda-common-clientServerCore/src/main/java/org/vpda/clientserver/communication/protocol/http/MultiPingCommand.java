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
package org.vpda.clientserver.communication.protocol.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;

/**
 * Command used to ping several command executors on server
 * 
 * @author kitko
 *
 */
public final class MultiPingCommand implements Command<Object>, Serializable {
    private static final long serialVersionUID = -5405004713917533352L;
    private final Collection<ExecutorIdentifier> identifiers;

    @Override
    public Object execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        return null;
    }

    /**
     * @param identifiers
     */
    public MultiPingCommand(Collection<ExecutorIdentifier> identifiers) {
        super();
        this.identifiers = new ArrayList<ExecutorIdentifier>(identifiers);
    }

    /**
     * @return identifiers
     */
    public Collection<? extends ExecutorIdentifier> getIdentifiers() {
        return Collections.unmodifiableCollection(identifiers);
    }

}
