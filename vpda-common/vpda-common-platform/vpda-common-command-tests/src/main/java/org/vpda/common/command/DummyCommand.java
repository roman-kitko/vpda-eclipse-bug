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

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;

/**
 * Dummy command which does nothing
 * 
 * @author kitko
 *
 */
public final class DummyCommand implements Command<Object>, Serializable {

    private static final long serialVersionUID = 8992246952302380252L;
    private static DummyCommand instance;

    /**
     * Creates command
     */
    private DummyCommand() {
        super();
    }

    @Override
    public Object execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        return null;
    }

    /**
     * @return instance of EmptyComamnd
     */
    public synchronized static DummyCommand getInstance() {
        if (instance == null) {
            instance = new DummyCommand();
        }
        return instance;
    }

}
