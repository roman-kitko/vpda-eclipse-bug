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
package org.vpda.abstractclient.core.profile;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.UUID;

import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.clientserver.communication.EncryptionType;
import org.vpda.clientserver.communication.data.ContextPolicy;
import org.vpda.clientserver.communication.data.ProxyToUse;
import org.vpda.internal.common.util.Assert;

/**
 * Data for login profile
 * 
 * @author kitko
 *
 */
public abstract class AbstractLoginProfile implements Serializable {
    private static final long serialVersionUID = 7082446672746399364L;
    private final UUID uuid;
    private final String profileName;
    private final String user;
    private final String bindingName;
    private final Integer port;
    private final String server;
    private final Locale locale;
    private final CommunicationProtocol communicationProtocol;
    private final ProxyToUse proxyToUse;
    private final String manualProxyHost;
    private final Integer manualProxyPort;
    private final EncryptionType encryptionType;
    private final ContextPolicy contextPolicy;
    private final CommunicationStateKind communicationStateKind;
    private final Timestamp timestamp;

    /**
     * Creates new profile
     * 
     * @param builder
     */
    protected AbstractLoginProfile(AbstractLoginProfile.Builder builder) {
        this.profileName = builder.getProfileName();
        this.uuid = builder.getUuid();
        this.user = builder.getUser();
        this.bindingName = builder.getBindingName();
        this.port = builder.getPort();
        this.server = builder.getServer();
        this.locale = builder.getLocale();
        this.communicationProtocol = builder.getCommunicationProtocol();
        this.proxyToUse = builder.getProxyToUse();
        this.manualProxyHost = builder.getManualProxyHost();
        this.manualProxyPort = builder.getManualProxyPort();
        this.encryptionType = builder.getEncryptionType();
        this.contextPolicy = builder.getContextPolicy();
        this.communicationStateKind = builder.getCommunicationStateKind();
        this.timestamp = builder.getTimestamp();
    }

    /**
     * @return builder
     */
    public abstract AbstractLoginProfile.Builder createBuilder();

    /**
     * @return builder with same values set
     */
    public abstract AbstractLoginProfile.Builder createBuilderWithSameValues();

    /**
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * @return the communicationStateKind
     */
    public CommunicationStateKind getCommunicationStateKind() {
        return communicationStateKind;
    }

    /**
     * @return profile name
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @return binding name
     */
    public String getBindingName() {
        return bindingName;
    }

    /**
     * @return communication protocol
     */
    public CommunicationProtocol getCommunicationProtocol() {
        return communicationProtocol;
    }

    /**
     * @return locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @return port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @return server
     */
    public String getServer() {
        return server;
    }

    /**
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return proxy to use
     */
    public ProxyToUse getProxyToUse() {
        return proxyToUse;
    }

    @Override
    public String toString() {
        return profileName;
    }

    /**
     * @return the manualProxyHost
     */
    public final String getManualProxyHost() {
        return manualProxyHost;
    }

    /**
     * @return the manualProxyPort
     */
    public final Integer getManualProxyPort() {
        return manualProxyPort;
    }

    /**
     * @return the encryptionType
     */
    public final EncryptionType getEncryptionType() {
        return encryptionType;
    }

    /**
     * @return the context policy
     */
    public ContextPolicy getContextPolicy() {
        return contextPolicy;
    }

    /**
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bindingName == null) ? 0 : bindingName.hashCode());
        result = prime * result + ((communicationProtocol == null) ? 0 : communicationProtocol.hashCode());
        result = prime * result + ((communicationStateKind == null) ? 0 : communicationStateKind.hashCode());
        result = prime * result + ((contextPolicy == null) ? 0 : contextPolicy.hashCode());
        result = prime * result + ((encryptionType == null) ? 0 : encryptionType.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        result = prime * result + ((manualProxyHost == null) ? 0 : manualProxyHost.hashCode());
        result = prime * result + ((manualProxyPort == null) ? 0 : manualProxyPort.hashCode());
        result = prime * result + ((port == null) ? 0 : port.hashCode());
        result = prime * result + ((profileName == null) ? 0 : profileName.hashCode());
        result = prime * result + ((proxyToUse == null) ? 0 : proxyToUse.hashCode());
        result = prime * result + ((server == null) ? 0 : server.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        AbstractLoginProfile other = (AbstractLoginProfile) obj;
        if (bindingName == null) {
            if (other.bindingName != null)
                return false;
        }
        else if (!bindingName.equals(other.bindingName))
            return false;
        if (communicationProtocol == null) {
            if (other.communicationProtocol != null)
                return false;
        }
        else if (!communicationProtocol.equals(other.communicationProtocol))
            return false;
        if (communicationStateKind != other.communicationStateKind)
            return false;
        if (contextPolicy != other.contextPolicy)
            return false;
        if (encryptionType != other.encryptionType)
            return false;
        if (locale == null) {
            if (other.locale != null)
                return false;
        }
        else if (!locale.equals(other.locale))
            return false;
        if (manualProxyHost == null) {
            if (other.manualProxyHost != null)
                return false;
        }
        else if (!manualProxyHost.equals(other.manualProxyHost))
            return false;
        if (manualProxyPort == null) {
            if (other.manualProxyPort != null)
                return false;
        }
        else if (!manualProxyPort.equals(other.manualProxyPort))
            return false;
        if (port == null) {
            if (other.port != null)
                return false;
        }
        else if (!port.equals(other.port))
            return false;
        if (profileName == null) {
            if (other.profileName != null)
                return false;
        }
        else if (!profileName.equals(other.profileName))
            return false;
        if (proxyToUse != other.proxyToUse)
            return false;
        if (server == null) {
            if (other.server != null)
                return false;
        }
        else if (!server.equals(other.server))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        }
        else if (!timestamp.equals(other.timestamp))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        }
        else if (!user.equals(other.user))
            return false;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        }
        else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

    /**
     * Builder for profile
     * 
     * @param <T>
     */
    public abstract static class Builder<T extends AbstractLoginProfile> implements org.vpda.common.util.Builder<T> {
        private UUID uuid;
        private String profileName;
        private String user;
        private String bindingName;
        private Integer port;
        private String server;
        private Locale locale;
        private CommunicationProtocol communicationProtocol;
        private ProxyToUse proxyToUse;
        private String manualProxyHost;
        private Integer manualProxyPort;
        private EncryptionType encryptionType;
        private ContextPolicy contextPolicy;
        private CommunicationStateKind communicationStateKind;
        private Timestamp timestamp;

        /**
         * Creates builder
         */
        protected Builder() {
            timestamp = new Timestamp(System.currentTimeMillis());
        }

        @Override
        public abstract T build();

        /**
         * @return the uuid
         */
        public UUID getUuid() {
            return uuid;
        }

        /**
         * @return the timestamp
         */
        public Timestamp getTimestamp() {
            return timestamp;
        }

        /**
         * @param timestamp the timestamp to set
         * @return this
         */
        public Builder<T> setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * @param uuid the uuid to set
         * @return this
         */
        public Builder<T> setUuid(UUID uuid) {
            this.uuid = uuid;
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
        public Builder<T> setCommunicationStateKind(CommunicationStateKind communicationStateKind) {
            this.communicationStateKind = communicationStateKind;
            return this;
        }

        /**
         * Sets profile name
         * 
         * @param profileName
         * @return this
         */
        public Builder<T> setProfileName(String profileName) {
            this.profileName = Assert.isNotNullArgument(profileName, "profileName");
            return this;
        }

        /**
         * @return profile name
         */
        public String getProfileName() {
            return profileName;
        }

        /**
         * @return binding name
         */
        public String getBindingName() {
            return bindingName;
        }

        /**
         * @return communication protocol
         */
        public CommunicationProtocol getCommunicationProtocol() {
            return communicationProtocol;
        }

        /**
         * @return locale
         */
        public Locale getLocale() {
            return locale;
        }

        /**
         * @return port
         */
        public Integer getPort() {
            return port;
        }

        /**
         * @return server
         */
        public String getServer() {
            return server;
        }

        /**
         * @return user
         */
        public String getUser() {
            return user;
        }

        /**
         * Sets binding name
         * 
         * @param bindingName
         * @return this
         */
        public Builder<T> setBindingName(String bindingName) {
            this.bindingName = bindingName;
            return this;
        }

        /**
         * Sets communication protocol
         * 
         * @param communicationProtocol
         * @return this
         */
        public Builder<T> setCommunicationProtocol(CommunicationProtocol communicationProtocol) {
            this.communicationProtocol = communicationProtocol;
            return this;
        }

        /**
         * Sets locale
         * 
         * @param locale
         * @return this
         */
        public Builder<T> setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        /**
         * Sets port
         * 
         * @param port
         * @return this
         */
        public Builder<T> setPort(Integer port) {
            this.port = port;
            return this;
        }

        /**
         * Sets server
         * 
         * @param server
         * @return this
         */

        public Builder<T> setServer(String server) {
            this.server = server;
            return this;
        }

        /**
         * Sets user
         * 
         * @param user
         * @return this
         */
        public Builder<T> setUser(String user) {
            this.user = user;
            return this;
        }

        /**
         * Sets proxy to use
         * 
         * @param proxyToUse
         * @return this
         */
        public Builder<T> setProxyToUse(ProxyToUse proxyToUse) {
            this.proxyToUse = proxyToUse;
            return this;
        }

        /**
         * @return proxy to use
         */
        public ProxyToUse getProxyToUse() {
            return proxyToUse;
        }

        @Override
        public String toString() {
            return profileName;
        }

        /**
         * @return the manualProxyHost
         */
        public final String getManualProxyHost() {
            return manualProxyHost;
        }

        /**
         * @param manualProxyHost the manualProxyHost to set
         * @return this
         */
        public final Builder<T> setManualProxyHost(String manualProxyHost) {
            this.manualProxyHost = manualProxyHost;
            return this;
        }

        /**
         * @return the manualProxyPort
         */
        public final Integer getManualProxyPort() {
            return manualProxyPort;
        }

        /**
         * @param manualProxyPort the manualProxyPort to set
         * @return this
         */
        public final Builder<T> setManualProxyPort(Integer manualProxyPort) {
            this.manualProxyPort = manualProxyPort;
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
        public final Builder<T> setEncryptionType(EncryptionType encryptionType) {
            this.encryptionType = encryptionType;
            return this;
        }

        /**
         * @return the context policy
         */
        public ContextPolicy getContextPolicy() {
            return contextPolicy;
        }

        /**
         * @param contextPolicy the contextPolicy to set
         * @return this
         */
        public Builder<T> setContextPolicy(ContextPolicy contextPolicy) {
            this.contextPolicy = contextPolicy;
            return this;
        }

        /**
         * Save values
         * 
         * @param profile
         * @return this
         */
        public Builder<T> setValues(T profile) {
            uuid = profile.getUuid();
            profileName = profile.getProfileName();
            user = profile.getUser();
            bindingName = profile.getBindingName();
            port = profile.getPort();
            server = profile.getServer();
            locale = profile.getLocale();
            communicationProtocol = profile.getCommunicationProtocol();
            proxyToUse = profile.getProxyToUse();
            manualProxyHost = profile.getManualProxyHost();
            manualProxyPort = profile.getManualProxyPort();
            encryptionType = profile.getEncryptionType();
            contextPolicy = profile.getContextPolicy();
            communicationStateKind = profile.getCommunicationStateKind();
            timestamp = profile.getTimestamp();
            return this;
        }

    }
}
