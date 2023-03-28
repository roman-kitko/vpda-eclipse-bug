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
package org.vpda.clientserver.clientcommunication;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Locale;

import org.vpda.clientserver.communication.data.AuthenticationEntry;
import org.vpda.clientserver.communication.data.LoginInfo;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.util.Builder;
import org.vpda.common.util.ComponentInfo;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * LoginInfo - main info client passes to server to make login request
 * 
 * @author kitko
 *
 */
public final class ClientLoginInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1262607573325803026L;
    private final TenementalContext context;
    private final InetAddress inetAddress;
    private final ClientConnectionInfo connectionInfo;
    private final ComponentInfo clientInfo;
    private final AuthenticationEntry authenticationEntry;

    private ClientLoginInfo(LoginInfoBuilder builder) {
        this.clientInfo = Assert.isNotNullArgument(builder.getClientInfo(), "clientInfo");
        this.connectionInfo = Assert.isNotNullArgument(builder.getConnectionInfo(), "connectionInfo");
        this.context = Assert.isNotNullArgument(builder.getContext(), "context");
        this.inetAddress = Assert.isNotNullArgument(builder.getInetAddress(), "inetAddress");
        this.authenticationEntry = Assert.isNotNullArgument(builder.getAuthenticationEntry(), "authenticationEntry");
    }

    /**
     * @return AuthenticationEntry
     */
    public AuthenticationEntry getAuthenticationEntry() {
        return authenticationEntry;
    }

    /**
     * @return ApplContext
     */
    public ApplContext getApplContext() {
        return context.getApplContext();
    }

    /**
     * @return ApplContext
     */
    public TenementalContext getContext() {
        return context;
    }

    /**
     * @return ConnectionInfo
     */
    public ClientConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    /**
     * @return InetAddress
     */
    public InetAddress getInetAddress() {
        return inetAddress;
    }

    /**
     * @return locale
     */
    public Locale getLocale() {
        return context.getLocale();
    }

    /**
     * @return client component info
     */
    public ComponentInfo getClientInfo() {
        return clientInfo;
    }

    /**
     * 
     * @return new LoginInfo
     */
    public LoginInfo createLoginInfo() {
        LoginInfo.LoginInfoBuilder builder = new LoginInfo.LoginInfoBuilder();
        builder.setAuthenticationEntry(authenticationEntry);
        builder.setClientInfo(clientInfo);
        builder.setContext(context);
        builder.setInetAddress(inetAddress);
        builder.setConnectionInfo(connectionInfo.createConnectionInfo());
        return builder.build();
    }

    /**
     * @return LoginInfoBuilder with same values
     */
    public LoginInfoBuilder createBuilderWithSameValues() {
        return new LoginInfoBuilder().setValues(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Login = ");
        builder.append(authenticationEntry.getLoginToken());
        builder.append(CommonUtilConstants.LINE_SEPARATOR);

        builder.append("ConnectionInfo = ");
        builder.append(connectionInfo);
        builder.append(CommonUtilConstants.LINE_SEPARATOR);

        builder.append("InetAddress = ");
        builder.append(inetAddress);
        builder.append(CommonUtilConstants.LINE_SEPARATOR);

        return builder.toString();
    }

    /**
     * Builder class for LoginInfoImpl
     * 
     * @author kitko
     *
     */
    public static final class LoginInfoBuilder implements Builder<ClientLoginInfo> {
        private AuthenticationEntry authenticationEntry;
        private TenementalContext context;
        private InetAddress inetAddress;
        private ClientConnectionInfo connectionInfo;
        private ComponentInfo clientInfo;

        /**
         * @return the authenticationEntry
         */
        public AuthenticationEntry getAuthenticationEntry() {
            return authenticationEntry;
        }

        /**
         * @param authenticationEntry the authenticationEntry to set
         * @return this
         */
        public LoginInfoBuilder setAuthenticationEntry(AuthenticationEntry authenticationEntry) {
            this.authenticationEntry = authenticationEntry;
            return this;
        }

        /**
         * @return the dataApplContext
         */
        public TenementalContext getContext() {
            return context;
        }

        /**
         * @param context the context to set
         * @return this
         */
        public LoginInfoBuilder setContext(TenementalContext context) {
            this.context = context;
            return this;
        }

        /**
         * @return the inetAddress
         */
        public InetAddress getInetAddress() {
            return inetAddress;
        }

        /**
         * @param inetAddress the inetAddress to set
         * @return this
         */
        public LoginInfoBuilder setInetAddress(InetAddress inetAddress) {
            this.inetAddress = inetAddress;
            return this;
        }

        /**
         * @return the connectionInfo
         */
        public ClientConnectionInfo getConnectionInfo() {
            return connectionInfo;
        }

        /**
         * @param connectionInfo the connectionInfo to set
         * @return this
         */
        public LoginInfoBuilder setConnectionInfo(ClientConnectionInfo connectionInfo) {
            this.connectionInfo = connectionInfo;
            return this;
        }

        /**
         * @return the clientInfo
         */
        public ComponentInfo getClientInfo() {
            return clientInfo;
        }

        /**
         * @param clientInfo the clientInfo to set
         * @return this
         */
        public LoginInfoBuilder setClientInfo(ComponentInfo clientInfo) {
            this.clientInfo = clientInfo;
            return this;
        }

        /**
         * Sets values from loginInfo
         * 
         * @param loginInfo
         * @return this
         */
        public LoginInfoBuilder setValues(ClientLoginInfo loginInfo) {
            this.clientInfo = loginInfo.getClientInfo();
            this.connectionInfo = loginInfo.getConnectionInfo();
            this.context = loginInfo.getContext();
            this.inetAddress = loginInfo.getInetAddress();
            this.authenticationEntry = loginInfo.getAuthenticationEntry();
            return this;
        }

        /**
         * Builds loginInfo
         * 
         * @return new LoginInfo
         */
        @Override
        public ClientLoginInfo build() {
            return new ClientLoginInfo(this);
        }

        @Override
        public Class<? extends ClientLoginInfo> getTargetClass() {
            return ClientLoginInfo.class;
        }

    }
}
