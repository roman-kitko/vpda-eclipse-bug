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

/**
 * Identifier of exported executor
 * 
 * @author kitko
 *
 */
public final class ExecutorIdentifier implements Serializable {
    private static final long serialVersionUID = 4695109246371392672L;
    private final String commandExecutorId;
    private final int calledId;

    /**
     * @return the commandExecutorId
     */
    public String getCommandExecutorId() {
        return commandExecutorId;
    }

    /**
     * @return the calledId
     */
    public int getCalledId() {
        return calledId;
    }

    /**
     * @param commandExecutorId
     * @param calledId
     */
    public ExecutorIdentifier(String commandExecutorId, int calledId) {
        super();
        this.commandExecutorId = commandExecutorId;
        this.calledId = calledId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + calledId;
        result = prime * result + ((commandExecutorId == null) ? 0 : commandExecutorId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ExecutorIdentifier other = (ExecutorIdentifier) obj;
        if (calledId != other.calledId)
            return false;
        if (commandExecutorId == null) {
            if (other.commandExecutorId != null)
                return false;
        }
        else if (!commandExecutorId.equals(other.commandExecutorId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return commandExecutorId + calledId;
    }

}
