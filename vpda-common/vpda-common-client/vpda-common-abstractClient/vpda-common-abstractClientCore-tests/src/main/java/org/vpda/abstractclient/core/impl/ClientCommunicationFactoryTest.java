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
package org.vpda.abstractclient.core.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.vpda.client.clientcommunication.ClientComunicationFactoryImpl;
import org.vpda.client.clientcommunication.protocol.embedded.EmbeddedClientCommunication;
import org.vpda.client.clientcommunication.protocol.embedded.EmbeddedClientCommunicationTest;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationImplNoneEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationImplSSLEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationNoneEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationSSLEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationSettings;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationImplNoneEncryption;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationImplSSLEncryption;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationNoneEncryption;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationSSLEncryption;
import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientCommunicationNames;
import org.vpda.clientserver.clientcommunication.MutableClientCommunicationFactory;
import org.vpda.clientserver.communication.BasicCommunicationKind;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.EncryptionType;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactoryDynamicProxy;

/**
 * Test of client communication
 * 
 * @author kitko
 *
 */
public class ClientCommunicationFactoryTest {

    private ClientCommunicationFactory createTestee() {
        MutableClientCommunicationFactory ccf = new ClientComunicationFactoryImpl();
        ccf.registerClientCommunication(new RMIClientCommunicationImplNoneEncryption(ccf,
                new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME),
                new CommadExecutorClientProxyFactoryDynamicProxy()));
        ccf.registerClientCommunication(new RMIClientCommunicationImplSSLEncryption(ccf,
                new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME, EncryptionType.SSL),
                new CommadExecutorClientProxyFactoryDynamicProxy()));
        ccf.registerClientCommunication(new HTTPClientCommunicationImplNoneEncryption(ccf,
                new CommunicationId(BasicCommunicationProtocol.HTTP, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME, EncryptionType.NONE),
                new HTTPClientCommunicationSettings(), new CommadExecutorClientProxyFactoryDynamicProxy()));
        ccf.registerClientCommunication(new HTTPClientCommunicationImplSSLEncryption(ccf,
                new CommunicationId(BasicCommunicationProtocol.HTTP, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME, EncryptionType.SSL),
                new HTTPClientCommunicationSettings(), new CommadExecutorClientProxyFactoryDynamicProxy()));

        ccf.registerClientCommunication(new EmbeddedClientCommunicationTest(ccf,
                new CommunicationId(BasicCommunicationProtocol.EMBEDDED, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME),
                new CommadExecutorClientProxyFactoryDynamicProxy()));
        return ccf;
    }

    /**
     * Test rmi communication
     *
     */
    @Test
    public void testRmiCommunication() {
        CommunicationId commId = new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME);
        ClientCommunication clientComunication = createTestee().createCommunication(commId);
        assertTrue(clientComunication instanceof RMIClientCommunicationNoneEncryption);
        commId = new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, "default", EncryptionType.SSL);
        clientComunication = createTestee().createCommunication(commId);
        assertTrue(clientComunication instanceof RMIClientCommunicationSSLEncryption);
    }

    /**
     * Test embedded communication
     *
     */
    @Test
    public void testEmbeddedCommunication() {
        CommunicationId commId = new CommunicationId(BasicCommunicationProtocol.EMBEDDED, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME);
        ClientCommunication clientComunication = createTestee().createCommunication(commId);
        assertTrue(clientComunication instanceof EmbeddedClientCommunication);
    }

    /**
     * Test of http communication
     */
    @Test
    public void testHttpCommunication() {
        CommunicationId commId = new CommunicationId(BasicCommunicationProtocol.HTTP, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME);
        ClientCommunication clientComunication = createTestee().createCommunication(commId);
        assertTrue(clientComunication instanceof HTTPClientCommunicationNoneEncryption);
        commId = new CommunicationId(BasicCommunicationProtocol.HTTP, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME, EncryptionType.SSL);
        clientComunication = createTestee().createCommunication(commId);
        assertTrue(clientComunication instanceof HTTPClientCommunicationSSLEncryption);
    }

}
