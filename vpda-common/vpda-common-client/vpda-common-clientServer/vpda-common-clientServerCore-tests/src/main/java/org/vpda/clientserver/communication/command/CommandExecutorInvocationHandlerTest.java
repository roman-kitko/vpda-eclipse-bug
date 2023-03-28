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

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.ClientCoreTestHelper;
import org.vpda.clientserver.clientcommunication.CommunicationInvocationHandler;
import org.vpda.clientserver.clientcommunication.EmptyClientCommunicationFactory;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.servercommunication.TestInvocationHandler;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;

/**
 * @author kitko
 *
 */
public class CommandExecutorInvocationHandlerTest {
    static interface ITestHelper {
        /**
         * @param a
         * @param b
         * @return anything
         */
        public Boolean m1(Integer a, Integer b);
    }

    static class TestHelper implements ITestHelper {
        boolean wasCalled;

        /**
         * @param a
         * @param b
         * @return true
         */
        @Override
        public Boolean m1(Integer a, Integer b) {
            wasCalled = true;
            return Boolean.TRUE;
        }
    }

    /**
     * Test method for invoke
     * 
     * @throws Throwable
     */
    @Test
    public void testInvokeExplicit() throws Throwable {
        CommunicationId communicationId = ClientCoreTestHelper.createCommunicationId();
        CommandExecutor executor = new CommandExecutorBase("test");
        TestHelper helper = new TestHelper();
        CommandExecutionEnv env = new CEEnvDelegate(
                new MacroObjectResolverImpl(SingleObjectResolver.create(CommunicationInvocationHandler.class, new TestInvocationHandler()), SingleObjectResolver.create(ITestHelper.class, helper)));
        AbstractCommandExecutorInvocationHandler commandExecutorInvocationHandler = new StatefullCommandExecutorInvocationHandler(new EmptyClientCommunicationFactory(), communicationId, executor, env,
                new Class[] { ITestHelper.class });
        commandExecutorInvocationHandler.invoke(helper, ITestHelper.class.getMethod("m1", new Class[] { Integer.class, Integer.class }), new Object[] { 1, 2 });
        assertTrue(helper.wasCalled);
    }

}
