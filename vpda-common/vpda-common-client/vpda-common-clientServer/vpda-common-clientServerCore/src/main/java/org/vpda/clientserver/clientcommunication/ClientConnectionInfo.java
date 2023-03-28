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
import java.util.UUID;

import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.clientserver.communication.data.ClientDeploymentPlatform;
import org.vpda.clientserver.communication.data.ClientPlatform;
import org.vpda.clientserver.communication.data.ClientUILoginInfo;
import org.vpda.clientserver.communication.data.ConnectionInfo;
import org.vpda.common.util.Builder;
import org.vpda.internal.common.util.Assert;

/**
 * 
 * Connection Info for Client
 * 
 * @author kitko
 *
 */
public final class ClientConnectionInfo implements Serializable {
    private static final long serialVersionUID = -187558202126816517L;
    private final ClientPlatform clientPlatform;
    private final CommunicationId communicationId;
    private final ClientCommunicationSettings clientConnectionSettings;
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

    /**
     * @return the clientConnectionSettings
     */
    public ClientCommunicationSettings getClientCommunicationSettings() {
        return clientConnectionSettings;
    }

    private ClientConnectionInfo(ConnectionInfoBuilder connectionInfoImplBuilder) {
        this.clientPlatform = Assert.isNotNullArgument(connectionInfoImplBuilder.clientPlatform, "clientPlatform");
        this.clientDeploymentPlatform = Assert.isNotNullArgument(connectionInfoImplBuilder.clientDeploymentPlatform, "clientDeploymentPlatform");
        this.communicationId = Assert.isNotNullArgument(connectionInfoImplBuilder.communicationId, "communicationId");
        this.clientConnectionSettings = Assert.isNotNullArgument(connectionInfoImplBuilder.clientConnectionSettings, "clientConnectionSettings");
        this.clientUILoginInfo = connectionInfoImplBuilder.clientUILoginInfo;
        this.communicationStateKind = Assert.isNotNullArgument(connectionInfoImplBuilder.communicationStateKind, "communicationStateKind");
        this.instanceId = connectionInfoImplBuilder.getInstanceId() != null ? connectionInfoImplBuilder.getInstanceId() : UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ConnectionInfo [clientPlatform=").append(clientPlatform).append(", communicationId=").append(communicationId).append(", clientConnectionSettings=")
                .append(clientConnectionSettings).append("]");
        return builder.toString();
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
     * Creates new ConnectionInfo with values from client connection info
     * 
     * @return ConnectionInfo
     */
    public ConnectionInfo createConnectionInfo() {
        ConnectionInfo.ConnectionInfoBuilder builder = new ConnectionInfo.ConnectionInfoBuilder();
        builder.setClientDeploymentPlatform(clientDeploymentPlatform);
        builder.setClientUIInfo(clientUILoginInfo);
        builder.setClientPlatform(clientPlatform);
        builder.setCommunicationId(communicationId);
        builder.setCommunicationStateKind(communicationStateKind);
        builder.setInstanceId(instanceId);
        return builder.build();
    }

    /**
     * Builder for ConnectionInfo
     * 
     * @author kitko
     *
     */
    public static final class ConnectionInfoBuilder implements Builder<ClientConnectionInfo> {
        private ClientDeploymentPlatform clientDeploymentPlatform;
        private ClientPlatform clientPlatform;
        private CommunicationId communicationId;
        private ClientCommunicationSettings clientConnectionSettings;
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
        public ClientUILoginInfo getClientUILoginInfo() {
            return clientUILoginInfo;
        }

        /**
         * @param clientUILoginInfo the clientEnvironmentInfo to set
         * @return this
         */
        public ConnectionInfoBuilder setClientUILoginInfo(ClientUILoginInfo clientUILoginInfo) {
            this.clientUILoginInfo = clientUILoginInfo;
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
         * @return ClientConnectionSettings
         */
        public final ClientCommunicationSettings getClientCommunicationSettings() {
            return clientConnectionSettings;
        }

        /**
         * Sets values from {@link ClientConnectionInfo}
         * 
         * @param info
         * @return this
         */
        public ConnectionInfoBuilder setValues(ClientConnectionInfo info) {
            clientDeploymentPlatform = info.clientDeploymentPlatform;
            clientPlatform = info.clientPlatform;
            communicationId = info.communicationId;
            clientConnectionSettings = info.clientConnectionSettings;
            clientUILoginInfo = info.clientUILoginInfo;
            communicationStateKind = info.communicationStateKind;
            instanceId = info.instanceId;
            return this;
        }

        /**
         * Sets ClientConnectionSettings
         * 
         * @param clientConnectionSettings
         * @return this
         */
        public final ConnectionInfoBuilder setClientCommunicationSettings(ClientCommunicationSettings clientConnectionSettings) {
            this.clientConnectionSettings = clientConnectionSettings;
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
        public ClientConnectionInfo build() {
            return new ClientConnectionInfo(this);
        }

        @Override
        public Class<? extends ClientConnectionInfo> getTargetClass() {
            return ClientConnectionInfo.class;
        }

    }
}
