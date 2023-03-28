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
package org.vpda.abstractclient.core;

import org.vpda.abstractclient.core.impl.ClientImpl;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.core.ui.dummy.DummyClientUI;
import org.vpda.client.clientcommunication.ClientComunicationFactoryImpl;
import org.vpda.client.clientcommunication.protocol.embedded.EmbeddedClientCommunicationTest;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationImplNoneEncryption;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientCommunicationNames;
import org.vpda.clientserver.clientcommunication.MutableClientCommunicationFactory;
import org.vpda.clientserver.communication.BasicCommunicationKind;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactoryDynamicProxy;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorRegistryImpl;
import org.vpda.common.command.executor.impl.CommandExecutorWithRegistryImpl;
import org.vpda.common.entrypoint.AppConfigurationImpl;
import org.vpda.common.entrypoint.ConcreteApplication;
import org.vpda.common.service.ServiceRegistryImpl;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * Test helper for abstract client module
 * 
 * @author kitko
 *
 */
public class AbstractClientTestHelper {
    private AbstractClientTestHelper() {
    }

    /**
     * 
     * @return testing ClientCommunicationFactory
     */
    public static ClientCommunicationFactory createsTestClientCommunicationFactory() {
        MutableClientCommunicationFactory ccf = new ClientComunicationFactoryImpl();
        ccf.registerClientCommunication(new EmbeddedClientCommunicationTest(ccf,
                new CommunicationId(BasicCommunicationProtocol.EMBEDDED, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME),
                new CommadExecutorClientProxyFactoryDynamicProxy()));
        ccf.registerClientCommunication(new RMIClientCommunicationImplNoneEncryption(ccf,
                new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME),
                new CommadExecutorClientProxyFactoryDynamicProxy()));
        return ccf;
    }

    /**
     * Creates test client
     * 
     * @return test client
     */
    public static ClientImpl createTestClient() {
        ClientImpl client = new ClientImpl(new ConcreteApplication(CommonUtilConstants.VPDA_PROJECT_NAME, new AppConfigurationImpl()), new ServiceRegistryImpl(),
                AbstractClientTestHelper.createsTestClientCommunicationFactory());
        client.setRequestCommandsExecutor(new CommandExecutorWithRegistryImpl(new CommandExecutorBase(ClientImpl.class.getName()), new CommandExecutorRegistryImpl()));
        client.setCommandExecutionEnv(EmptyCommandExecutionEnv.getInstance());
        ClientUI ui = new DummyClientUI(client);
        client.setClientUI(ui);
        return client;
    }

}
