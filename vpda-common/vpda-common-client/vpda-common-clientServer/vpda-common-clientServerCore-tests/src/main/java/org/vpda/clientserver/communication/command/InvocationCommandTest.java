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
package org.vpda.clientserver.communication.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.clientcommunication.CommunicationInvocationHandler;
import org.vpda.clientserver.communication.BasicCommunicationKind;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.servercommunication.TestInvocationHandler;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.DummyCommandEvent;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.internal.common.util.JavaSerializationUtil;

/**
 * @author kitko
 *
 */
public class InvocationCommandTest {
    static class TestHelper {
        boolean wasCalled;

        /**
         * @param a
         * @param b
         * @return true
         */
        public Boolean m1(Integer a, Integer b) {
            wasCalled = true;
            return Boolean.TRUE;
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        TestHelper helper = new TestHelper();
        CommandExecutor executor = new CommandExecutorBase("Test");
        CommandExecutionEnv env = new CEEnvDelegate(
                new MacroObjectResolverImpl(SingleObjectResolver.create(CommunicationInvocationHandler.class, new TestInvocationHandler()), SingleObjectResolver.create(TestHelper.class, helper)));
        StatefullInvocationCommand cmd = createTestee();
        executor.executeCommand(cmd, env, DummyCommandEvent.getInstance());
        assertTrue(helper.wasCalled);
    }

    /**
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    @Test
    public void testSerialization() throws IllegalArgumentException, IOException, ClassNotFoundException, IllegalAccessException {
        StatefullInvocationCommand cmd = createTestee();
        JavaSerializationUtil.testObjectSerialization(cmd);
    }

    private StatefullInvocationCommand createTestee() {
        CommunicationId commId = new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, "default");
        return new StatefullInvocationCommand(commId, new Class[] { TestHelper.class }, "m1", new Class[] { Integer.class, Integer.class }, new Object[] { 1, 2 });
    }

}
