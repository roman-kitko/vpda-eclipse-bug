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
package org.vpda.client.clientcommunication;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.MutableClientCommunicationFactory;
import org.vpda.clientserver.communication.CommunicationId;

/**
 * Implementation of {@link ClientCommunicationFactory}.
 * 
 * @author kitko
 *
 */
public final class ClientComunicationFactoryImpl implements MutableClientCommunicationFactory, Serializable {
    private static final long serialVersionUID = 3009909214178474345L;
    private final Map<CommunicationId, ClientCommunication> communications;

    /**
     * Default constructor for factory
     */
    public ClientComunicationFactoryImpl() {
        communications = new HashMap<CommunicationId, ClientCommunication>(3);
    }

    @Override
    public ClientCommunication createCommunication(CommunicationId commId) {
        ClientCommunication cc = communications.get(commId);
        if (cc == null) {
            throw new IllegalArgumentException("ClientCommunication not registered by id " + commId);
        }
        return cc;
    }

    @Override
    public Collection<CommunicationId> getRegisteredCommunications() {
        return Collections.unmodifiableCollection(communications.keySet());
    }

    @Override
    public void registerClientCommunication(ClientCommunication clientComunication) {
        communications.put(clientComunication.getCommunicationId(), clientComunication);
    }
}
