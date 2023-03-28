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
import java.util.UUID;

import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.common.annotations.Immutable;
import org.vpda.common.util.Builder;
import org.vpda.internal.common.util.Assert;

/**
 * 
 * Connection Info for Client
 * 
 * @author kitko
 *
 */
@Immutable
public final class ConnectionInfo implements Serializable {
    private static final long serialVersionUID = -187558202126816517L;
    private final ClientPlatform clientPlatform;
    private final CommunicationId communicationId;
    private final ClientDeploymentPlatform clientDeploymentPlatform;
    private final ClientUILoginInfo clientUILoginInfo;
    private final CommunicationStateKind communicationStateKind;
    private final String instanceId;

    /**
     * @return builder with same values
     */
    public ConnectionInfoBuilder createBuilderWithSameValues() {
        return new ConnectionInfoBuilder().setValues(this);
    }

    /**
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * 
     * @return CommunicationStateKind
     */
    public CommunicationStateKind getCommunicationStateKind() {
        return communicationStateKind;
    }

    /**
     * @return communicationId
     */
    public CommunicationId getCommunicationId() {
        return communicationId;
    }

    /**
     * @return the clientEnvironmentInfo
     */
    public ClientUILoginInfo getClientUILoginInfo() {
        return clientUILoginInfo;
    }

    private ConnectionInfo(ConnectionInfoBuilder connectionInfoImplBuilder) {
        this.clientPlatform = Assert.isNotNullArgument(connectionInfoImplBuilder.clientPlatform, "clientPlatform");
        this.clientDeploymentPlatform = Assert.isNotNullArgument(connectionInfoImplBuilder.clientDeploymentPlatform, "clientDeploymentPlatform");
        this.communicationId = Assert.isNotNullArgument(connectionInfoImplBuilder.communicationId, "communicationId");
        this.clientUILoginInfo = connectionInfoImplBuilder.clientUILoginInfo;
        this.communicationStateKind = Assert.isNotNullArgument(connectionInfoImplBuilder.communicationStateKind, "communicationStateKind");
        this.instanceId = connectionInfoImplBuilder.getInstanceId() != null ? connectionInfoImplBuilder.getInstanceId() : UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConnectionInfo [clientPlatform=").append(clientPlatform).append(", communicationId=").append(communicationId);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientDeploymentPlatform == null) ? 0 : clientDeploymentPlatform.hashCode());
        result = prime * result + ((clientPlatform == null) ? 0 : clientPlatform.hashCode());
        result = prime * result + ((clientUILoginInfo == null) ? 0 : clientUILoginInfo.hashCode());
        result = prime * result + ((communicationId == null) ? 0 : communicationId.hashCode());
        result = prime * result + ((communicationStateKind == null) ? 0 : communicationStateKind.hashCode());
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
        ConnectionInfo other = (ConnectionInfo) obj;
        if (clientDeploymentPlatform != other.clientDeploymentPlatform)
            return false;
        if (clientPlatform != other.clientPlatform)
            return false;
        if (clientUILoginInfo == null) {
            if (other.clientUILoginInfo != null)
                return false;
        }
        else if (!clientUILoginInfo.equals(other.clientUILoginInfo))
            return false;
        if (communicationId == null) {
            if (other.communicationId != null)
                return false;
        }
        else if (!communicationId.equals(other.communicationId))
            return false;
        if (communicationStateKind != other.communicationStateKind)
            return false;
        return true;
    }

    /**
     * @return the clientDeploymentPlatform
     */
    public final ClientDeploymentPlatform getClientDeploymentPlatform() {
        return clientDeploymentPlatform;
    }

    /**
     * @return clientPlatform
     */
    public ClientPlatform getClientPlatform() {
        return clientPlatform;
    }

    /**
     * Builder for ConnectionInfo
     * 
     * @author kitko
     *
     */
    public static final class ConnectionInfoBuilder implements Builder<ConnectionInfo> {
        private ClientDeploymentPlatform clientDeploymentPlatform;
        private ClientPlatform clientPlatform;
        private CommunicationId communicationId;
        private ClientUILoginInfo clientUILoginInfo;
        private CommunicationStateKind communicationStateKind;
        private String instanceId;

        /**
         * Creates the builder
         */
        public ConnectionInfoBuilder() {
            communicationStateKind = CommunicationStateKind.Statefull;
            instanceId = UUID.randomUUID().toString();
        }

        /**
         * @return the instanceId
         */
        public String getInstanceId() {
            return instanceId;
        }

        /**
         * @param instanceId the instanceId to set
         * @return this
         */
        public Builder setInstanceId(String instanceId) {
            this.instanceId = instanceId;
            return this;
        }

        /**
         * @return the communicationStateKind
         */
        public CommunicationStateKind getCommunicationStateKind() {
            return communicationStateKind;
        }

        /**
         * @param communicationStateKind the communicationStateKind to set
         * @return this
         */
        public ConnectionInfoBuilder setCommunicationStateKind(CommunicationStateKind communicationStateKind) {
            this.communicationStateKind = communicationStateKind;
            return this;
        }

        /**
         * @return the clientEnvironmentInfo
         */
        public ClientUILoginInfo getClientUIInfo() {
            return clientUILoginInfo;
        }

        /**
         * @param clientEnvironmentInfo the clientEnvironmentInfo to set
         * @return this
         */
        public ConnectionInfoBuilder setClientUIInfo(ClientUILoginInfo clientEnvironmentInfo) {
            this.clientUILoginInfo = clientEnvironmentInfo;
            return this;
        }

        /**
         * @return the clientPlatform
         */
        public ClientPlatform getClientPlatform() {
            return clientPlatform;
        }

        /**
         * @param clientPlatform the clientPlatform to set
         * @return this
         */
        public ConnectionInfoBuilder setClientPlatform(ClientPlatform clientPlatform) {
            this.clientPlatform = clientPlatform;
            return this;
        }

        /**
         * @return the clientDeploymentPlatform
         */
        public final ClientDeploymentPlatform getClientDeploymentPlatform() {
            return clientDeploymentPlatform;
        }

        /**
         * @param clientDeploymentPlatform the clientDeploymentPlatform to set
         * @return this
         */
        public final ConnectionInfoBuilder setClientDeploymentPlatform(ClientDeploymentPlatform clientDeploymentPlatform) {
            this.clientDeploymentPlatform = clientDeploymentPlatform;
            return this;
        }

        /**
         * Sets values from {@link ConnectionInfo}
         * 
         * @param info
         * @return this
         */
        public ConnectionInfoBuilder setValues(ConnectionInfo info) {
            clientDeploymentPlatform = info.clientDeploymentPlatform;
            clientPlatform = info.clientPlatform;
            communicationId = info.communicationId;
            clientUILoginInfo = info.clientUILoginInfo;
            communicationStateKind = info.communicationStateKind;
            instanceId = info.getInstanceId();
            return this;
        }

        /**
         * @return the communicationId
         */
        public CommunicationId getCommunicationId() {
            return communicationId;
        }

        /**
         * @param communicationId the communicationId to set
         * @return this
         */
        public ConnectionInfoBuilder setCommunicationId(CommunicationId communicationId) {
            this.communicationId = communicationId;
            return this;
        }

        @Override
        public ConnectionInfo build() {
            return new ConnectionInfo(this);
        }

        @Override
        public Class<? extends ConnectionInfo> getTargetClass() {
            return ConnectionInfo.class;
        }

    }
}
