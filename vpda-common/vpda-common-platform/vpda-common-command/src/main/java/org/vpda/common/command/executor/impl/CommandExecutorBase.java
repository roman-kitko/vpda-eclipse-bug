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
package org.vpda.common.command.executor.impl;

import java.io.Serializable;

import org.vpda.common.command.CommandExecutor;

/**
 * This is base command executor that will directly call command
 * 
 * @author kitko
 *
 */
public final class CommandExecutorBase extends AbstractCommandExecutorBase implements Serializable {
    private static final long serialVersionUID = -8284397904571067093L;
    private static final CommandExecutor DEF_INSTANCE = new CommandExecutorBase("default");

    /**
     * @return DEF_INSTANCE
     */
    public static CommandExecutor getDefaultInstance() {
        return DEF_INSTANCE;
    }

    /**
     * Do not call directly, use container
     * 
     * @param id
     */
    public CommandExecutorBase(String id) {
        super(id);
    }
}
