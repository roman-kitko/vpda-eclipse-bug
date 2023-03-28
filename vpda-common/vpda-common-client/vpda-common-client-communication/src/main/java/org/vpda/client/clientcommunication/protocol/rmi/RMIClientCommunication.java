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
package org.vpda.client.clientcommunication.protocol.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.communication.protocol.rmi.RMIBootstrap;

/**
 * Communication used when client uses RMI protocol.
 * 
 * @author kitko
 *
 */
public interface RMIClientCommunication extends ClientCommunication {

    /**
     * Lookup RMIBootstrap from remote server
     * 
     * @param clientSettings
     * @return remote RMIBootstrap
     * @throws RemoteException
     * @throws NotBoundException
     */
    public RMIBootstrap createRMIBootstrap(RMIClientCommunicationSettings clientSettings) throws RemoteException, NotBoundException;

}
