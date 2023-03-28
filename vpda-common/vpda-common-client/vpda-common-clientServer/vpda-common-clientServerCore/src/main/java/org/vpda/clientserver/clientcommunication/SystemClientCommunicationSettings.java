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

import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.common.annotations.Immutable;

/**
 * Client connection settings for system session
 * 
 * @author kitko
 *
 */
@Immutable
public final class SystemClientCommunicationSettings implements ClientCommunicationSettings {

    @Override
    public CommunicationProtocol getProtocol() {
        return BasicCommunicationProtocol.EMBEDDED;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public ClientEncryptionSettings getEncryptionSettings() {
        return new ClientNoneEncryptionSettings();
    }

}
