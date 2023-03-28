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

import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.vpda.client.clientcommunication.AbstractHostClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientEncryptionSettings;
import org.vpda.clientserver.clientcommunication.ClientNoneEncryptionSettings;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.protocol.rmi.RMIClientServerConstants;
import org.vpda.common.annotations.Immutable;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Simple holder of RMI client communication settings
 * 
 * @author kitko
 *
 */
@Immutable
public final class RMIClientCommunicationSettings extends AbstractHostClientCommunicationSettings {
    private static final long serialVersionUID = -6865032308112122511L;
    private final String serverBindingName;

    /**
     * Creates RMIClientCommunicationSettingsImpl with defaults
     */
    public RMIClientCommunicationSettings() {
        super(RMIClientServerConstants.LOCALHOST, RMIClientServerConstants.RMI_PORT, Collections.<Proxy>emptyList(), new ClientNoneEncryptionSettings());
        this.serverBindingName = RMIClientServerConstants.LOGIN_SERVER_BINDING_NAME;
    }

    /**
     * Creates RMIClientCommunicationSettingsImpl
     * 
     * @param host
     * @param port
     * @param serverBindingName
     */
    public RMIClientCommunicationSettings(String host, Integer port, String serverBindingName) {
        super(host, port, Collections.<Proxy>emptyList(), new ClientNoneEncryptionSettings());
        this.serverBindingName = serverBindingName;
    }

    /**
     * @param host
     * @param port
     * @param serverBindingName
     * @param proxies
     * @param encryptionSettings
     */
    public RMIClientCommunicationSettings(String host, Integer port, String serverBindingName, List<Proxy> proxies, ClientEncryptionSettings encryptionSettings) {
        super(host, port, proxies, encryptionSettings);
        this.serverBindingName = RMIClientServerConstants.LOGIN_SERVER_BINDING_NAME;
    }

    private RMIClientCommunicationSettings(Builder builder) {
        super(builder);
        this.serverBindingName = builder.getServerBindingName();
    }

    /**
     * @return the serverBindingName
     */
    public final String getServerBindingName() {
        return serverBindingName;
    }

    @Override
    public CommunicationProtocol getProtocol() {
        return BasicCommunicationProtocol.RMI;
    }

    @Override
    public URI createSelectSystemProxyURI() {
        try {
            return new URI("socket://" + getHost() + ":" + getPort());
        }
        catch (URISyntaxException e) {
            throw new VPDARuntimeException("Invalid URI", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public RMIClientCommunicationSettings.Builder createBuilder() {
        RMIClientCommunicationSettings.Builder builder = new RMIClientCommunicationSettings.Builder();
        builder.setValue(this);
        return builder;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("RMIClientCommunicationSettings [serverBindingName=").append(serverBindingName).append(' ').append(super.toString()).append("]");
        return builder2.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((serverBindingName == null) ? 0 : serverBindingName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        RMIClientCommunicationSettings other = (RMIClientCommunicationSettings) obj;
        if (serverBindingName == null) {
            if (other.serverBindingName != null)
                return false;
        }
        else if (!serverBindingName.equals(other.serverBindingName))
            return false;
        return true;
    }

    /**
     * Builder for RMIClientCommunicationSettings
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractHostClientCommunicationSettings.Builder<RMIClientCommunicationSettings> {

        private String serverBindingName;

        /** Creates a builder */
        public Builder() {
            setPort(RMIClientServerConstants.RMI_PORT);
            setHost(RMIClientServerConstants.LOCALHOST);
            setEncryptionSettings(new ClientNoneEncryptionSettings());
            setServerBindingName(RMIClientServerConstants.LOGIN_SERVER_BINDING_NAME);
        }

        /**
         * @return the serverBindingName
         */
        public final String getServerBindingName() {
            return serverBindingName;
        }

        /**
         * @param serverBindingName the serverBindingName to set
         * @return this
         */
        public final Builder setServerBindingName(String serverBindingName) {
            this.serverBindingName = serverBindingName;
            return this;
        }

        @Override
        public Class<? extends RMIClientCommunicationSettings> getTargetClass() {
            return RMIClientCommunicationSettings.class;
        }

        @Override
        public RMIClientCommunicationSettings build() {
            return new RMIClientCommunicationSettings(this);
        }

        /**
         * Sets values from AbstractHostConnectionSettings
         * 
         * @param value
         * @return this
         */
        @Override
        public Builder setValue(RMIClientCommunicationSettings value) {
            super.setValue(value);
            this.serverBindingName = value.getServerBindingName();
            return this;
        }

    }

}
