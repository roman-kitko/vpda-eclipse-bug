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
package org.vpda.clientserver.communication;

import java.io.Serializable;
import java.util.UUID;

import org.vpda.common.annotations.Immutable;
import org.vpda.internal.common.util.Assert;

/**
 * Communication Id for client and server. It must uniquely identify connection
 * point that server creates and client can connect to. It does not specify
 * concrete host and port, just set name of a connection point. Server can
 * create only one Server communication channel for one communication id.
 * 
 * @author kitko
 *
 */
@Immutable
public final class CommunicationId implements Serializable {
    private static final long serialVersionUID = -1638210345162283259L;
    private static String defaultVmIdentifier = UUID.randomUUID().toString();
    private final CommunicationKind kind;
    private final String name;
    private final String id;
    private final CommunicationProtocol protocol;
    private final EncryptionType encryptionType;
    private final String vmIdentifier;

    /**
     * Creates new CommunicationIdImpl with computed id and EncryptionType.NONE as
     * encryptionType
     * 
     * @param protocol
     * @param kind
     * @param name
     * 
     */
    public CommunicationId(CommunicationProtocol protocol, CommunicationKind kind, String name) {
        this(protocol, kind, name, EncryptionType.NONE);
    }

    /**
     * Creates new CommunicationIdImpl with computed id
     * 
     * @param protocol
     * @param kind
     * @param name
     * @param encryptionType
     */
    public CommunicationId(CommunicationProtocol protocol, CommunicationKind kind, String name, EncryptionType encryptionType) {
        super();
        this.protocol = Assert.isNotNullArgument(protocol, "protocol");
        this.name = Assert.isNotEmptyArgument(name, "name");
        this.kind = Assert.isNotNullArgument(kind, "kind");
        this.encryptionType = Assert.isNotNullArgument(encryptionType, "encryptionType");
        this.vmIdentifier = defaultVmIdentifier;
        this.id = computeId();
    }

    private CommunicationId(Builder builder) {
        this.protocol = Assert.isNotNullArgument(builder.getProtocol(), "protocol");
        this.name = Assert.isNotEmptyArgument(builder.getName(), "name");
        this.kind = Assert.isNotNullArgument(builder.getKind(), "kind");
        this.encryptionType = Assert.isNotNullArgument(builder.getEncryptionType(), "encryptionType");
        this.vmIdentifier = builder.getVmIdentifier() != null ? builder.getVmIdentifier() : defaultVmIdentifier;
        this.id = computeId();
    }

    /**
     * @return name of communication
     */
    public String getName() {
        return name;
    }

    /**
     * @return kind of communication
     */
    public CommunicationKind getKind() {
        return kind;
    }

    /**
     * @return the vmIdentifier
     */
    public String getVmIdentifier() {
        return vmIdentifier;
    }

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @return the encryptionType
     */
    public final EncryptionType getEncryptionType() {
        return encryptionType;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommunicationId)) {
            return false;
        }
        final CommunicationId other = (CommunicationId) obj;
        return id.equals(other.id);
    }

    @Override
    public String toString() {
        return id;
    }

    /**
     * @return protocol
     */
    public CommunicationProtocol getProtocol() {
        return protocol;
    }

    /**
     * Computes id from fields
     * 
     * @param kind
     * @param protocol
     * @param name
     * @param et
     * @return computed id
     */
    private String computeId() {
        return this.kind.getName() + ':' + this.protocol.getName() + ':' + this.name + ':' + this.encryptionType.getName();
    }

    /**
     * Builder for CommunicationId
     * 
     * @author kitko
     *
     */
    public static final class Builder implements org.vpda.common.util.Builder<CommunicationId> {
        private CommunicationKind kind;
        private String name;
        private CommunicationProtocol protocol;
        private EncryptionType encryptionType;
        private String vmIdentifier;

        @Override
        public Class<? extends CommunicationId> getTargetClass() {
            return CommunicationId.class;
        }

        @Override
        public CommunicationId build() {
            return new CommunicationId(this);
        }

        /**
         * @return the kind
         */
        public final CommunicationKind getKind() {
            return kind;
        }

        /**
         * @param kind the kind to set
         * @return this
         */
        public final Builder setKind(CommunicationKind kind) {
            this.kind = kind;
            return this;
        }

        /**
         * @return the name
         */
        public final String getName() {
            return name;
        }

        /**
         * @param name the name to set
         * @return this
         */
        public final Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return the protocol
         */
        public final CommunicationProtocol getProtocol() {
            return protocol;
        }

        /**
         * @param protocol the protocol to set
         * @return this
         */
        public final Builder setProtocol(CommunicationProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        /**
         * @return the encryptionType
         */
        public final EncryptionType getEncryptionType() {
            return encryptionType;
        }

        /**
         * @param encryptionType the encryptionType to set
         * @return this
         */
        public final Builder setEncryptionType(EncryptionType encryptionType) {
            this.encryptionType = encryptionType;
            return this;
        }

        /**
         * @return the vmIdentifier
         */
        public String getVmIdentifier() {
            return vmIdentifier;
        }

        /**
         * @param vmIdentifier the vmIdentifier to set
         * @return this
         */
        public Builder setVmIdentifier(String vmIdentifier) {
            this.vmIdentifier = vmIdentifier;
            return this;
        }

        /**
         * Sets values from CommunicationId
         * 
         * @param commId
         * @return this
         */
        public final Builder setValues(CommunicationId commId) {
            this.encryptionType = commId.getEncryptionType();
            this.kind = commId.getKind();
            this.name = commId.getName();
            this.protocol = commId.getProtocol();
            this.vmIdentifier = commId.getVmIdentifier();
            return this;
        }

    }

}
