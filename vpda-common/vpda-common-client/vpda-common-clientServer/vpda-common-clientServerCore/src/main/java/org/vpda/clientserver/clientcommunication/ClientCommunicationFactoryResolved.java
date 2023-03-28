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
package org.vpda.clientserver.clientcommunication;

import java.io.Serializable;
import java.util.Collection;

import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.module.ClientServerCoreModule;
import org.vpda.common.ioc.picocontainer.PicoHelper;

/**
 * {@link ClientCommunicationFactory} factory that resolves real factory from
 * container and delegates create to resolved factory
 * 
 * @author kitko
 *
 */
public final class ClientCommunicationFactoryResolved implements ClientCommunicationFactory, Serializable {
    private static final long serialVersionUID = 2069377253935942098L;

    @Override
    public ClientCommunication createCommunication(CommunicationId commId) {
        return resolveRealFactory().createCommunication(commId);
    }

    private ClientCommunicationFactory resolveRealFactory() {
        return PicoHelper.getMandatoryComponent(ClientServerCoreModule.getInstance().getContainer(), ClientCommunicationFactory.class);
    }

    @Override
    public Collection<CommunicationId> getRegisteredCommunications() {
        return resolveRealFactory().getRegisteredCommunications();
    }

}
