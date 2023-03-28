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
package org.vpda.client.clientcommunication.protocol.rmi;

import java.io.Serializable;
import java.rmi.server.RMIClientSocketFactory;

import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactory;

/**
 * RMI Client communication with none encryption
 * 
 * @author kitko
 *
 */
public final class RMIClientCommunicationImplNoneEncryption extends RMIClientCommunicationImpl implements RMIClientCommunicationNoneEncryption, Serializable {
    private static final long serialVersionUID = -3121278887166643891L;

    /**
     * Creates RMIClientCommunicationImplNoneEncryption
     * 
     * @param ccf
     * @param communicationId
     * @param commadExecutorClientProxyFactory
     * @param clientInitialSettings
     */
    public RMIClientCommunicationImplNoneEncryption(ClientCommunicationFactory ccf, CommunicationId communicationId, CommadExecutorClientProxyFactory commadExecutorClientProxyFactory,
            RMIClientCommunicationSettings clientInitialSettings) {
        super(ccf, communicationId, commadExecutorClientProxyFactory, clientInitialSettings);
    }

    /**
     * Creates RMIClientCommunicationImplNoneEncryption
     * 
     * @param ccf
     * @param communicationId
     * @param commadExecutorClientProxyFactory
     */
    public RMIClientCommunicationImplNoneEncryption(ClientCommunicationFactory ccf, CommunicationId communicationId, CommadExecutorClientProxyFactory commadExecutorClientProxyFactory) {
        super(ccf, communicationId, commadExecutorClientProxyFactory, new RMIClientCommunicationSettings.Builder().build());
    }

    @Override
    protected RMIClientSocketFactory createClientSocketFactory(RMIClientCommunicationSettings clientSettings) {
        return new NoneEncryptionRMIClientSocketFactoryImpl(clientSettings);
    }

}
