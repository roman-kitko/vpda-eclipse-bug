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
package org.vpda.common.command.executor.impl;

import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.internal.common.util.Assert;

/**
 * Executor with predefined static environment
 * 
 * @author kitko
 *
 */
public final class CommandExecutorWithPredefinedEnv extends CommandExecutorDelegate implements CommandExecutor {

    /** Preference strategy between passed and static environment */
    public static enum EnvironmentWinStrategy {
        /** Static environment win */
        STATIC_WIN,
        /** Passed environment wins, default one */
        PASSED_WIN;
    }

    private final CommandExecutionEnv staticEnv;
    private final EnvironmentWinStrategy environmentWinStrategy;

    /**
     * Creates CommandExecutorWithEnv
     * 
     * @param realExecutor
     * @param env
     */
    public CommandExecutorWithPredefinedEnv(CommandExecutor realExecutor, CommandExecutionEnv env) {
        this(realExecutor, env, EnvironmentWinStrategy.PASSED_WIN);
    }

    /**
     * Creates CommandExecutorWithEnv
     * 
     * @param realExecutor
     * @param env
     * @param id
     */
    public CommandExecutorWithPredefinedEnv(CommandExecutor realExecutor, CommandExecutionEnv env, String id) {
        this(realExecutor, env, EnvironmentWinStrategy.PASSED_WIN, id);
    }

    /**
     * Creates CommandExecutorWithPredefinedEnv
     * 
     * @param realExecutor
     * @param env
     * @param environmentWinStrategy
     */
    public CommandExecutorWithPredefinedEnv(CommandExecutor realExecutor, CommandExecutionEnv env, EnvironmentWinStrategy environmentWinStrategy) {
        super(realExecutor);
        this.staticEnv = Assert.isNotNullArgument(env, "env");
        this.environmentWinStrategy = Assert.isNotNullArgument(environmentWinStrategy, "environmentWinStrategy");
    }

    /**
     * Creates CommandExecutorWithEnv
     * 
     * @param realExecutor
     * @param env
     * @param environmentWinStrategy
     * @param id
     */
    public CommandExecutorWithPredefinedEnv(CommandExecutor realExecutor, CommandExecutionEnv env, EnvironmentWinStrategy environmentWinStrategy, String id) {
        super(realExecutor, id);
        this.staticEnv = Assert.isNotNullArgument(env, "env");
        this.environmentWinStrategy = Assert.isNotNullArgument(environmentWinStrategy, "environmentWinStrategy");
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        CommandExecutionEnv finalEnv = null;
        if (env != null) {
            finalEnv = EnvironmentWinStrategy.PASSED_WIN.equals(environmentWinStrategy) ? new CEEnvDelegate(new MacroObjectResolverImpl(env, staticEnv))
                    : new CEEnvDelegate(new MacroObjectResolverImpl(staticEnv, env));
        }
        else {
            finalEnv = staticEnv;
        }
        return super.executeCommand(command, finalEnv, event);
    }

}
