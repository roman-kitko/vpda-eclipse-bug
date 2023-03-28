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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.DummyCommandEvent;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.command.executor.impl.AbstractCommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorBase;

/**
 * @author kitko
 *
 */
public class CommandExecutorBaseTest {

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.AbstractCommandExecutorBase#getCommandExecutor()}.
     */
    @Test
    public final void testGetCommandExecutor() {
        CommandExecutorBase executor = new CommandExecutorBase("exc");
        assertSame(executor, executor.getCommandExecutor());
    }

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.AbstractCommandExecutorBase#executeCommand(org.vpda.common.command.Command, org.vpda.common.command.CommandExecutionEnv, org.vpda.common.command.CommandEvent)}.
     * 
     * @throws Exception
     */
    @Test
    public final void testExecuteCommand() throws Exception {
        Command<String> helloCmd = new Command<String>() {
            @Override
            public String execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                return "Hello";
            }
        };
        CommandExecutorBase executor = new CommandExecutorBase("exc");
        ExecutionResult<String> res = executor.executeCommand(helloCmd, EmptyCommandExecutionEnv.getInstance(), DummyCommandEvent.getInstance());
        assertNotNull(res);
        assertEquals("Hello", res.get());

        Command<String> exceptionCmd = new Command<String>() {
            @Override
            public String execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                throw new IOException("Something bad happened");
            }
        };
        try {
            executor.executeCommand(exceptionCmd, EmptyCommandExecutionEnv.getInstance(), DummyCommandEvent.getInstance());
            fail();
        }
        catch (IOException e) {
        }
    }

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.AbstractCommandExecutorBase#getExecutorId()}.
     */
    @Test
    public final void testGetExecutorId() {
        CommandExecutorBase executor = new CommandExecutorBase("exc");
        assertEquals("exc", executor.getExecutorId());
    }

}
