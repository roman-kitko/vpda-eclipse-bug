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
package org.vpda.clientserver.communication.command;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.ClientCoreTestHelper;
import org.vpda.clientserver.clientcommunication.EmptyClientCommunicationFactory;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.command.executor.impl.CommandExecutorBase;

/**
 * @author kitko
 *
 */
public class CommadExecutorClientProxyFactoryDynamicProxiesTest {

    /**
     * Test
     * 
     * @throws Exception
     */
    @Test
    public void testCreateProxyServerCommunicationFactoryCommunicationIdCommandExecutorCommandExecutionEnvClassOfT() throws Exception {
        Map map = (Map) createTestee().createStatefullProxy(new EmptyClientCommunicationFactory(), ClientCoreTestHelper.createCommunicationId(), createExecutor(),
                EmptyCommandExecutionEnv.getInstance(), new Class[] { Map.class });
        assertNotNull(map);
    }

    /**
     * Test
     * 
     * @throws Exception
     */
    @Test
    public void testCreateProxyServerCommunicationFactoryCommunicationIdCommandExecutorCommandExecutionEnvClassArray() throws Exception {
        Object o = createTestee().createStatefullProxy(new EmptyClientCommunicationFactory(), ClientCoreTestHelper.createCommunicationId(), createExecutor(), EmptyCommandExecutionEnv.getInstance(),
                new Class[] { A.class, B.class });
        assertNotNull(o);
        assertTrue(o instanceof A);
        assertTrue(o instanceof B);
    }

    /**
     * Dummy interface
     */
    public interface A {

    }

    /**
     * Dummy interface
     */
    public interface B {

    }

    private CommandExecutor createExecutor() {
        return new CommandExecutorBase("test");
    }

    private CommadExecutorClientProxyFactoryDynamicProxy createTestee() {
        return new CommadExecutorClientProxyFactoryDynamicProxy();
    }

}
