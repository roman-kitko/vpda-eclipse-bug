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
package org.vpda.clientserver.communication.data;

import java.io.Serializable;

import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.EncryptionType;

/**
 * Web based login info
 * 
 * @author kitko
 *
 */
public final class SwingBasedClientUILoginInfo extends AbstractClientUILoginInfo implements ClientUILoginInfo, Serializable {
    private static final long serialVersionUID = -6006083419711409515L;

    private final CommunicationProtocol protocol;
    private final String server;
    private final int port;
    private final String bindingName;
    private final EncryptionType encryptionType;
    private final ProxyToUse proxyToUse;

    private SwingBasedClientUILoginInfo(Builder builder) {
        super(builder);
        this.protocol = builder.getProtocol();
        this.server = builder.getServer();
        this.port = builder.getPort();
        this.bindingName = builder.getBindingName();
        this.encryptionType = builder.getEncryptionType();
        this.proxyToUse = builder.getProxyToUse();
    }

    /**
     * @return the protocol
     */
    public CommunicationProtocol getProtocol() {
        return protocol;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the bindingName
     */
    public String getBindingName() {
        return bindingName;
    }

    /**
     * @return the encryptionType
     */
    public EncryptionType getEncryptionType() {
        return encryptionType;
    }

    /**
     * @return the proxyToUse
     */
    public ProxyToUse getProxyToUse() {
        return proxyToUse;
    }

    @Override
    public SwingBasedClientUILoginInfo.Builder createBuilder() {
        return new SwingBasedClientUILoginInfo.Builder();
    }

    @Override
    public SwingBasedClientUILoginInfo.Builder createBuilderWithSameValues() {
        return (SwingBasedClientUILoginInfo.Builder) super.createBuilderWithSameValues();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((bindingName == null) ? 0 : bindingName.hashCode());
        result = prime * result + ((encryptionType == null) ? 0 : encryptionType.hashCode());
        result = prime * result + port;
        result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
        result = prime * result + ((proxyToUse == null) ? 0 : proxyToUse.hashCode());
        result = prime * result + ((server == null) ? 0 : server.hashCode());
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
        SwingBasedClientUILoginInfo other = (SwingBasedClientUILoginInfo) obj;
        if (bindingName == null) {
            if (other.bindingName != null)
                return false;
        }
        else if (!bindingName.equals(other.bindingName))
            return false;
        if (encryptionType != other.encryptionType)
            return false;
        if (port != other.port)
            return false;
        if (protocol == null) {
            if (other.protocol != null)
                return false;
        }
        else if (!protocol.equals(other.protocol))
            return false;
        if (proxyToUse != other.proxyToUse)
            return false;
        if (server == null) {
            if (other.server != null)
                return false;
        }
        else if (!server.equals(other.server))
            return false;
        return true;
    }

    /**
     * Builder for {@link WebBasedClientUILoginInfo}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractClientUILoginInfo.AbstractClientUILoginInfoBuilder<SwingBasedClientUILoginInfo>
            implements org.vpda.common.util.Builder<SwingBasedClientUILoginInfo> {
        private CommunicationProtocol protocol;
        private String server;
        private int port;
        private String bindingName;
        private EncryptionType encryptionType;
        private ProxyToUse proxyToUse;

        /**
         * Sets values from SwingBasedClientUILoginInfo
         * 
         * @param loginInfo
         * @return this
         */
        @Override
        public Builder setValues(SwingBasedClientUILoginInfo loginInfo) {
            super.setValues(loginInfo);
            this.protocol = loginInfo.getProtocol();
            this.server = loginInfo.getServer();
            this.port = loginInfo.getPort();
            this.bindingName = loginInfo.getBindingName();
            this.encryptionType = loginInfo.getEncryptionType();
            this.proxyToUse = loginInfo.getProxyToUse();
            return this;
        }

        /**
         * @return the protocol
         */
        public CommunicationProtocol getProtocol() {
            return protocol;
        }

        /**
         * @param protocol the protocol to set
         * @return this
         */
        public Builder setProtocol(CommunicationProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        /**
         * @return the server
         */
        public String getServer() {
            return server;
        }

        /**
         * @param server the server to set
         * @return this
         */
        public Builder setServer(String server) {
            this.server = server;
            return this;
        }

        /**
         * @return the port
         */
        public int getPort() {
            return port;
        }

        /**
         * @param port the port to set
         * @return this
         */
        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        /**
         * @return the bindingName
         */
        public String getBindingName() {
            return bindingName;
        }

        /**
         * @param bindingName the bindingName to set
         * @return this
         */
        public Builder setBindingName(String bindingName) {
            this.bindingName = bindingName;
            return this;
        }

        /**
         * @return the encryptionType
         */
        public EncryptionType getEncryptionType() {
            return encryptionType;
        }

        /**
         * @param encryptionType the encryptionType to set
         * @return this
         */
        public Builder setEncryptionType(EncryptionType encryptionType) {
            this.encryptionType = encryptionType;
            return this;
        }

        /**
         * @return the proxyToUse
         */
        public ProxyToUse getProxyToUse() {
            return proxyToUse;
        }

        /**
         * @param proxyToUse the proxyToUse to set
         * @return this
         */
        public Builder setProxyToUse(ProxyToUse proxyToUse) {
            this.proxyToUse = proxyToUse;
            return this;
        }

        @Override
        public SwingBasedClientUILoginInfo build() {
            return new SwingBasedClientUILoginInfo(this);
        }

        @Override
        public Class<? extends SwingBasedClientUILoginInfo> getTargetClass() {
            return SwingBasedClientUILoginInfo.class;
        }

    }

}
