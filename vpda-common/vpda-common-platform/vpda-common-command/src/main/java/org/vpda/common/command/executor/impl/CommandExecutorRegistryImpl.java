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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.command.CommandConst;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;

/**
 * Registry for command executors
 * 
 * @author kitko
 *
 */
public final class CommandExecutorRegistryImpl implements CommandExecutorRegistry, Serializable {
    private static final long serialVersionUID = 6522761493408767466L;
    private final Map<String, CommandExecutor> executors;

    /**
     * Creates new registry
     * 
     */
    public CommandExecutorRegistryImpl() {
        super();
        executors = new HashMap<String, CommandExecutor>();
    }

    @Override
    public CommandExecutor getRegisteredCommandExecutor(String commandexecutorId) {
        if (commandexecutorId == null) {
            commandexecutorId = CommandConst.DEFAULT_COMMON_EXECUTOR;
        }
        return executors.get(commandexecutorId);
    }

    @Override
    public void registerCommandExecutor(String id, CommandExecutor commandExecutor) {
        executors.put(id, commandExecutor);
    }

    @Override
    public Collection<String> getExecutorsIds() {
        return Collections.unmodifiableCollection(executors.keySet());
    }

    @Override
    public void registerCommandExecutor(CommandExecutor commandExecutor) {
        registerCommandExecutor(commandExecutor.getExecutorId(), commandExecutor);
    }

}
