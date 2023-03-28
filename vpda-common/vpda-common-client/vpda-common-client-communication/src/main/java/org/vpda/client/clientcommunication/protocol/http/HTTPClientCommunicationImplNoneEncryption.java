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
package org.vpda.client.clientcommunication.protocol.http;

import org.apache.hc.core5.http.URIScheme;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactory;

/**
 * HTTPClientCommunicationImpl for None Encryption
 * 
 * @author kitko
 *
 */
public final class HTTPClientCommunicationImplNoneEncryption extends AbstractHTTPClientCommunication implements HTTPClientCommunicationNoneEncryption {
    private static final long serialVersionUID = 4215016248814940482L;

    /**
     * Creates HTTPClientCommunicationImplNoneEncryption
     * 
     * @param ccf
     * @param communicationId
     * @param settings
     * @param commadExecutorClientProxyFactory
     */
    public HTTPClientCommunicationImplNoneEncryption(ClientCommunicationFactory ccf, CommunicationId communicationId, HTTPClientCommunicationSettings settings,
            CommadExecutorClientProxyFactory commadExecutorClientProxyFactory) {
        super(ccf, communicationId, settings, commadExecutorClientProxyFactory);
    }

    @Override
    protected org.apache.hc.core5.http.config.Registry<org.apache.hc.client5.http.socket.ConnectionSocketFactory> createSchemeRegistry(HTTPClientCommunicationSettings settings) {
        org.apache.hc.core5.http.config.RegistryBuilder<org.apache.hc.client5.http.socket.ConnectionSocketFactory> registryBuilder = org.apache.hc.core5.http.config.RegistryBuilder.create();
        registryBuilder.register(URIScheme.HTTP.getId(), new org.apache.hc.client5.http.socket.PlainConnectionSocketFactory());
        org.apache.hc.core5.http.config.Registry<org.apache.hc.client5.http.socket.ConnectionSocketFactory> registry = registryBuilder.build();
        return registry;
    }

}
