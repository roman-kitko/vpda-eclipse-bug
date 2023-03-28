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
package org.vpda.common.core.shutdown;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.vpda.common.command.BasicCommandEvent;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;
import org.vpda.internal.common.util.Assert;

/**
 * Defualt simple impl of ShutdownRegistry
 * 
 * @author kitko
 *
 */
public final class ShutdownRegistryImpl implements ShutdownRegistry, Serializable {
    private static final long serialVersionUID = 2043527490674772284L;
    private static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(ShutdownRegistryImpl.class);
    private final Map<ShutdownLevel, List<Command<?>>> commands;
    private final CommandExecutor executor;
    private final CommandExecutionEnv env;

    /**
     * Creates ShutdownRegistryImpl
     * 
     * @param executor
     * @param env
     *
     */
    public ShutdownRegistryImpl(CommandExecutor executor, CommandExecutionEnv env) {
        this.executor = Assert.isNotNull(executor, "Executor argument is null");
        this.env = Assert.isNotNull(env, "Env argument is null");
        commands = new HashMap<ShutdownLevel, List<Command<?>>>(6);
    }

    @Override
    public synchronized <T> void addShutdownCommand(Command<T> command, ShutdownLevel level) {
        List<Command<?>> levelCommands = commands.get(level);
        if (levelCommands == null) {
            levelCommands = new ArrayList<Command<?>>(3);
            commands.put(level, levelCommands);
        }
        levelCommands.add(command);
    }

    @Override
    public synchronized <T> void addShutdownCommand(Command<T> command, ShutdownLevel level, int position) {
        List<Command<?>> levelCommands = commands.get(level);
        if (levelCommands == null) {
            levelCommands = new ArrayList<Command<?>>(3);
            commands.put(level, levelCommands);
        }
        levelCommands.add(position, command);
    }

    @Override
    public synchronized List<Command<?>> getCommandsForLevel(ShutdownLevel level) {
        List<Command<?>> levelCommands = commands.get(level);
        List<Command<?>> emptyList = null;
        if (levelCommands == null) {
            emptyList = Collections.emptyList();
        }
        return levelCommands != null ? Collections.unmodifiableList(levelCommands) : emptyList;
    }

    @Override
    public synchronized void executeShutdown() {
        MethodTimer method = logger.methodEntry(Level.INFO);
        for (ShutdownLevel level : ShutdownLevel.values()) {
            List<Command<?>> commands = getCommandsForLevel(level);
            for (Command<?> command : commands) {
                try {
                    executor.executeCommand(command, env, new BasicCommandEvent("SHUTDOWN"));
                }
                catch (Exception e) {
                    logger.log(Level.WARNING, "Executing shutdown failed for command", e);
                }
            }

        }
        logger.methodExit(method);
    }
}
