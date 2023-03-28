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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.DummyCommandEvent;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorWithPredefinedEnv;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;

/**
 * @author kitko
 *
 */
public class CommandExecutorWithPredefinedEnvTest {

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.CommandExecutorWithPredefinedEnv#executeCommand(org.vpda.common.command.Command, org.vpda.common.command.CommandExecutionEnv, org.vpda.common.command.CommandEvent)}.
     * 
     * @throws Exception
     */
    @Test
    public final void testExecuteCommand() throws Exception {
        CommandExecutorBase delegate = new CommandExecutorBase("exec");
        CommandExecutionEnv env1 = new CEEnvDelegate(SingleObjectResolver.create("key1", String.class, "value1"));
        CommandExecutionEnv env2 = new CEEnvDelegate(
                new MacroObjectResolverImpl(SingleObjectResolver.create("key1", String.class, "value111"), SingleObjectResolver.create("key2", String.class, "value2")));
        CommandExecutorWithPredefinedEnv executor = new CommandExecutorWithPredefinedEnv(delegate, env1);
        Command<String> helloCmd1 = new Command<String>() {
            @Override
            public String execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                assertEquals("value111", env.resolveObject(String.class, "key1"));
                assertEquals("value2", env.resolveObject(String.class, "key2"));
                return "Hello";
            }
        };
        assertEquals("Hello", executor.executeCommand(helloCmd1, env2, DummyCommandEvent.getInstance()).get());

        Command<String> helloCmd2 = new Command<String>() {
            @Override
            public String execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                assertEquals("value1", env.resolveObject(String.class, "key1"));
                assertEquals("value2", env.resolveObject(String.class, "key2"));
                return "Hello";
            }
        };
        executor = new CommandExecutorWithPredefinedEnv(delegate, env1, CommandExecutorWithPredefinedEnv.EnvironmentWinStrategy.STATIC_WIN);
        assertEquals("Hello", executor.executeCommand(helloCmd2, env2, DummyCommandEvent.getInstance()).get());

        assertEquals("exec", executor.getExecutorId());
        executor = new CommandExecutorWithPredefinedEnv(delegate, env1, "MyId");
        assertEquals("MyId", executor.getExecutorId());
    }

}
