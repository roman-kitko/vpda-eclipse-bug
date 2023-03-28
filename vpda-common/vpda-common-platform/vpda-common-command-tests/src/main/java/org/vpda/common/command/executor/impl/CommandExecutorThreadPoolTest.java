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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandCallback;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.DummyCommandEvent;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.command.executor.impl.CommandExecutorRegistryImpl;
import org.vpda.common.command.executor.impl.CommandExecutorThreadPool;
import org.vpda.internal.common.util.StaticCache;

/**
 * @author kitko
 *
 */
public class CommandExecutorThreadPoolTest {

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.CommandExecutorThreadPool#executeCommand(org.vpda.common.command.Command, org.vpda.common.command.CommandExecutionEnv, org.vpda.common.command.CommandEvent)}.
     * 
     * @throws Exception
     */
    @Test
    public final void testExecuteCommandCommandOfQextendsTCommandExecutionEnvCommandEvent() throws Exception {

        ExecutorService service = Executors.newSingleThreadExecutor();
        CommandExecutorThreadPool executor = new CommandExecutorThreadPool("exec", service, new CommandExecutorRegistryImpl(), StaticCache.getKeyCreator());
        Command<String> helloCmd = new Command<String>() {
            @Override
            public String execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                return "Hello";
            }
        };

        Future<String> execResult = executor.executeCommand(helloCmd, EmptyCommandExecutionEnv.getInstance(), DummyCommandEvent.getInstance());
        assertNotNull(execResult);
        assertEquals("Hello", execResult.get());
        ;
    }

    /**
     * Test
     * 
     * @throws Exception
     */
    @Test
    public void testWithCallback() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        CommandExecutorThreadPool executor = new CommandExecutorThreadPool("exec", service, new CommandExecutorRegistryImpl(), StaticCache.getKeyCreator());
        Command<String> helloCmd = new Command<String>() {
            @Override
            public String execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                return "Hello";
            }
        };
        CommandCallback<String> callback = new CommandCallback<String>() {

            @Override
            public boolean beforeCommandExecute(CommandExecutor executor, Command<? extends String> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
                return CommandCallback.super.beforeCommandExecute(executor, command, env, event);
            }

            @Override
            public String afterCommandExecute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event, String result) throws Exception {
                return CommandCallback.super.afterCommandExecute(executor, env, event, result);
            }

        };

        Future<String> execResult = executor.executeCommandWithCallback(helloCmd, EmptyCommandExecutionEnv.getInstance(), DummyCommandEvent.getInstance(), callback);
        assertNotNull(execResult);
        assertEquals("Hello", execResult.get());
        ;

    }

}
