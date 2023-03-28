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
package org.vpda.clientserver.communication.pref;

import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.EncryptionType;
import org.vpda.clientserver.communication.data.ProxyToUse;
import org.vpda.clientserver.communication.data.SwingBasedClientUILoginInfo;

public final class SwingLoginPreferences extends AbstractLoginPreferences {
    private static final long serialVersionUID = -6894533722303815376L;

    private BasicCommunicationProtocol protocol;

    private final String server;

    private final int port;

    private final String bindingName;

    private final EncryptionType encryptionType;

    private final ProxyToUse proxyToUse;

    private SwingLoginPreferences(Builder builder) {
        super(builder);
        this.server = builder.getServer();
        this.port = builder.getPort();
        this.protocol = builder.getProtocol();
        this.bindingName = builder.getBindingName();
        this.encryptionType = builder.getEncryptionType();
        this.proxyToUse = builder.getProxyToUse();
    }

    @Override
    public SwingBasedClientUILoginInfo createClientUILoginInfo() {
        SwingBasedClientUILoginInfo.Builder uiBuilder = new SwingBasedClientUILoginInfo.Builder();
        fillClientUILoginInfoBuilder(uiBuilder);
        uiBuilder.setBindingName(bindingName).setProtocol(protocol).setEncryptionType(encryptionType).setPort(port).setProxyToUse(proxyToUse).setServer(server);
        return uiBuilder.build();
    }

    public BasicCommunicationProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(BasicCommunicationProtocol protocol) {
        this.protocol = protocol;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getBindingName() {
        return bindingName;
    }

    public EncryptionType getEncryptionType() {
        return encryptionType;
    }

    public ProxyToUse getProxyToUse() {
        return proxyToUse;
    }

    public final class Builder extends AbstractLoginPreferences.Builder<SwingLoginPreferences> implements org.vpda.common.util.Builder<SwingLoginPreferences> {

        private String server;

        private int port;

        private BasicCommunicationProtocol protocol;

        private String bindingName;

        private EncryptionType encryptionType;

        private ProxyToUse proxyToUse;

        @Override
        public Builder setValues(SwingLoginPreferences pref) {
            super.setValues(pref);
            this.server = pref.getServer();
            this.port = pref.getPort();
            this.protocol = pref.getProtocol();
            this.bindingName = pref.getBindingName();
            this.encryptionType = pref.getEncryptionType();
            this.proxyToUse = pref.getProxyToUse();
            return this;
        }

        public BasicCommunicationProtocol getProtocol() {
            return protocol;
        }

        public Builder setProtocol(BasicCommunicationProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public String getServer() {
            return server;
        }

        public Builder setServer(String server) {
            this.server = server;
            return this;
        }

        public int getPort() {
            return port;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public String getBindingName() {
            return bindingName;
        }

        public Builder setBindingName(String bindingName) {
            this.bindingName = bindingName;
            return this;
        }

        public EncryptionType getEncryptionType() {
            return encryptionType;
        }

        public Builder setEncryptionType(EncryptionType encryptionType) {
            this.encryptionType = encryptionType;
            return this;
        }

        public ProxyToUse getProxyToUse() {
            return proxyToUse;
        }

        public Builder setProxyToUse(ProxyToUse proxyToUse) {
            this.proxyToUse = proxyToUse;
            return this;
        }

        @Override
        public Class<? extends SwingLoginPreferences> getTargetClass() {
            return SwingLoginPreferences.class;
        }

        @Override
        public SwingLoginPreferences build() {
            return new SwingLoginPreferences(this);
        }

    }

}
