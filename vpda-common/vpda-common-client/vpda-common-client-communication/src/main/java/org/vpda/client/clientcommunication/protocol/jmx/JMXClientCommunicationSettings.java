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
package org.vpda.client.clientcommunication.protocol.jmx;

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
import org.vpda.clientserver.communication.protocol.jmx.JMXClientServerConstants;
import org.vpda.common.annotations.Immutable;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Settings when JMX protocol is used
 * 
 * @author kitko
 *
 */
@Immutable
public final class JMXClientCommunicationSettings extends AbstractHostClientCommunicationSettings {
    private static final long serialVersionUID = -6865032308112122511L;
    private final String serverBindingName;

    /**
     * Creates JMXClientCommunicationSettings with defaults
     */
    public JMXClientCommunicationSettings() {
        super(JMXClientServerConstants.LOCALHOST, JMXClientServerConstants.RMI_PORT, Collections.<Proxy>emptyList(), new ClientNoneEncryptionSettings());
        this.serverBindingName = JMXClientServerConstants.SERVER_BINDING_NAME;
    }

    /**
     * Creates JMXClientCommunicationSettings
     * 
     * @param host
     * @param port
     * @param serverBindingName
     */
    public JMXClientCommunicationSettings(String host, Integer port, String serverBindingName) {
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
    public JMXClientCommunicationSettings(String host, Integer port, String serverBindingName, List<Proxy> proxies, ClientEncryptionSettings encryptionSettings) {
        super(host, port, proxies, encryptionSettings);
        this.serverBindingName = JMXClientServerConstants.SERVER_BINDING_NAME;
    }

    private JMXClientCommunicationSettings(Builder builder) {
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
        return BasicCommunicationProtocol.JMX;
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
    public JMXClientCommunicationSettings.Builder createBuilder() {
        JMXClientCommunicationSettings.Builder builder = new JMXClientCommunicationSettings.Builder();
        builder.setValue(this);
        return builder;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("JMXClientCommunicationSettings [serverBindingName=").append(serverBindingName).append(' ').append(super.toString()).append("]");
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
        JMXClientCommunicationSettings other = (JMXClientCommunicationSettings) obj;
        if (serverBindingName == null) {
            if (other.serverBindingName != null)
                return false;
        }
        else if (!serverBindingName.equals(other.serverBindingName))
            return false;
        return true;
    }

    /**
     * Builder for JMXClientCommunicationSettings
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractHostClientCommunicationSettings.Builder<JMXClientCommunicationSettings> {

        private String serverBindingName;

        /** Creates builder with defaults */
        public Builder() {
            this.serverBindingName = JMXClientServerConstants.SERVER_BINDING_NAME;
            this.setPort(JMXClientServerConstants.RMI_PORT);
            this.setHost(JMXClientServerConstants.LOCALHOST);
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
        public Class<? extends JMXClientCommunicationSettings> getTargetClass() {
            return JMXClientCommunicationSettings.class;
        }

        @Override
        public JMXClientCommunicationSettings build() {
            return new JMXClientCommunicationSettings(this);
        }

        /**
         * Sets values from AbstractHostConnectionSettings
         * 
         * @param value
         * @return this
         */
        @Override
        public Builder setValue(JMXClientCommunicationSettings value) {
            super.setValue(value);
            this.serverBindingName = value.getServerBindingName();
            return this;
        }

    }

}
