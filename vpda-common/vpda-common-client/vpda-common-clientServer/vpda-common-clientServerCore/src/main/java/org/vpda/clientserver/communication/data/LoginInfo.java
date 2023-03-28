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
import java.net.InetAddress;
import java.util.Locale;

import org.vpda.common.context.ApplContext;
import org.vpda.common.context.DateContext;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.util.Builder;
import org.vpda.common.util.ComponentInfo;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * LoginInfo - main info client pass to server to make login request
 * 
 * @author kitko
 *
 */
public final class LoginInfo implements Serializable {
    private static final long serialVersionUID = 1262607573325803026L;
    private final TenementalContext context;
    private final InetAddress inetAddress;
    private final ConnectionInfo connectionInfo;
    private final ComponentInfo clientInfo;
    private final AuthenticationEntry authenticationEntry;

    private LoginInfo(LoginInfoBuilder builder) {
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

    public TenementalContext getContext() {
        return context;
    }

    /**
     * @return ConnectionInfo
     */
    public ConnectionInfo getConnectionInfo() {
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

    public DateContext getDateContext() {
        return context.getDateContext();
    }

    /**
     * @return client component info
     */
    public ComponentInfo getClientInfo() {
        return clientInfo;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authenticationEntry == null) ? 0 : authenticationEntry.hashCode());
        result = prime * result + ((clientInfo == null) ? 0 : clientInfo.hashCode());
        result = prime * result + ((connectionInfo == null) ? 0 : connectionInfo.hashCode());
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((inetAddress == null) ? 0 : inetAddress.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginInfo other = (LoginInfo) obj;
        if (authenticationEntry == null) {
            if (other.authenticationEntry != null)
                return false;
        }
        else if (!authenticationEntry.equals(other.authenticationEntry))
            return false;
        if (clientInfo == null) {
            if (other.clientInfo != null)
                return false;
        }
        else if (!clientInfo.equals(other.clientInfo))
            return false;
        if (connectionInfo == null) {
            if (other.connectionInfo != null)
                return false;
        }
        else if (!connectionInfo.equals(other.connectionInfo))
            return false;
        if (context == null) {
            if (other.context != null)
                return false;
        }
        else if (!context.equals(other.context))
            return false;
        if (inetAddress == null) {
            if (other.inetAddress != null)
                return false;
        }
        else if (!inetAddress.equals(other.inetAddress))
            return false;
        return true;
    }

    /**
     * Builder class for LoginInfoImpl
     * 
     * @author kitko
     *
     */
    public static final class LoginInfoBuilder implements Builder<LoginInfo> {
        private AuthenticationEntry authenticationEntry;
        private TenementalContext context;
        private InetAddress inetAddress;
        private ConnectionInfo connectionInfo;
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
        public ConnectionInfo getConnectionInfo() {
            return connectionInfo;
        }

        /**
         * @param connectionInfo the connectionInfo to set
         * @return this
         */
        public LoginInfoBuilder setConnectionInfo(ConnectionInfo connectionInfo) {
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
        public LoginInfoBuilder setValues(LoginInfo loginInfo) {
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
        public LoginInfo build() {
            return new LoginInfo(this);
        }

        @Override
        public Class<? extends LoginInfo> getTargetClass() {
            return LoginInfo.class;
        }

    }
}
