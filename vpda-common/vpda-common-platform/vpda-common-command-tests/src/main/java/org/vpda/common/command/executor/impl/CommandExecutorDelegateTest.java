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
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.DummyCommandEvent;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.command.executor.impl.AbstractCommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorDelegate;
import org.vpda.internal.common.util.Holder;

/**
 * @author kitko
 *
 */
public class CommandExecutorDelegateTest {

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.CommandExecutorDelegate#executeCommand(org.vpda.common.command.Command, org.vpda.common.command.CommandExecutionEnv, org.vpda.common.command.CommandEvent)}.
     * 
     * @throws Exception
     */
    @Test
    public final void testExecuteCommand() throws Exception {
        final CommandExecutorBase exc = new CommandExecutorBase("exc");
        CommandExecutorDelegate delegate = new CommandExecutorDelegate(exc);
        Command<String> helloCmd = new Command<String>() {
            @Override
            public String execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                assertSame(exc, executor);
                return "Hello";
            }
        };
        assertEquals("Hello", delegate.executeCommand(helloCmd, EmptyCommandExecutionEnv.getInstance(), DummyCommandEvent.getInstance()).get());
    }

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.CommandExecutorDelegate#unreferenced()}.
     */
    @Test
    public final void testUnreferenced() {
        final Holder<Boolean> unrefHolder = new Holder<>(false);
        final AbstractCommandExecutorBase exc = new AbstractCommandExecutorBase("exc") {
            /**
             * 
             */
            private static final long serialVersionUID = 880678407800145471L;

            @Override
            public void unreferenced() {
                unrefHolder.setValue(true);
            }
        };
        CommandExecutorDelegate delegate = new CommandExecutorDelegate(exc);
        delegate.unreferenced();
        assertEquals(true, unrefHolder.getValue());
    }

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.CommandExecutorDelegate#getCommandExecutor()}.
     */
    @Test
    public final void testGetCommandExecutor() {
        CommandExecutorBase exc = new CommandExecutorBase("exc");
        CommandExecutorDelegate delegate = new CommandExecutorDelegate(exc);
        assertSame(delegate, delegate.getCommandExecutor());
    }

    /**
     * Test method for
     * {@link org.vpda.common.command.executor.impl.CommandExecutorDelegate#getExecutorId()}.
     */
    @Test
    public final void testGetExecutorId() {
        CommandExecutorBase exc = new CommandExecutorBase("exc");
        CommandExecutorDelegate delegate = new CommandExecutorDelegate(exc);
        assertEquals("exc", delegate.getExecutorId());
        delegate = new CommandExecutorDelegate(exc, "otherId");
        assertEquals("otherId", delegate.getExecutorId());
    }

}
