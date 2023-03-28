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

import java.rmi.server.RMIClientSocketFactory;

import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientSSLEncryptionSettings;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactory;
import org.vpda.clientserver.communication.protocol.rmi.RMIClientServerConstants;

/**
 * RMI Client communication with ssl encryption
 * 
 * @author kitko
 *
 */
public final class RMIClientCommunicationImplSSLEncryption extends RMIClientCommunicationImpl implements RMIClientCommunicationSSLEncryption {

    /**
     * 
     */
    private static final long serialVersionUID = -8469869542334930013L;

    /**
     * Creates RMIClientCommunicationImplSSLEncryption
     * 
     * @param ccf
     * @param communicationId
     * @param commadExecutorClientProxyFactory
     * @param clientInitialSettings
     */
    public RMIClientCommunicationImplSSLEncryption(ClientCommunicationFactory ccf, CommunicationId communicationId, CommadExecutorClientProxyFactory commadExecutorClientProxyFactory,
            RMIClientCommunicationSettings clientInitialSettings) {
        super(ccf, communicationId, commadExecutorClientProxyFactory, clientInitialSettings);
    }

    /**
     * Creates RMIClientCommunicationImplSSLEncryption
     * 
     * @param ccf
     * @param communicationId
     * @param commadExecutorClientProxyFactory
     */
    public RMIClientCommunicationImplSSLEncryption(ClientCommunicationFactory ccf, CommunicationId communicationId, CommadExecutorClientProxyFactory commadExecutorClientProxyFactory) {
        super(ccf, communicationId, commadExecutorClientProxyFactory, new RMIClientCommunicationSettings.Builder().setServerBindingName(RMIClientServerConstants.LOGIN_SERVER_BINDING_NAME_SSL)
                .setEncryptionSettings(new ClientSSLEncryptionSettings()).setPort(RMIClientServerConstants.RMI_PORT_SSL).setHost(RMIClientServerConstants.LOCALHOST).build());
    }

    @Override
    protected RMIClientSocketFactory createClientSocketFactory(RMIClientCommunicationSettings clientSettings) {
        return new SSLRMIClientSocketFactory(clientSettings);
    }

}
