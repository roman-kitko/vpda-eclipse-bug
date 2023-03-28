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
package org.vpda.client.clientcommunication;

import java.io.Serializable;
import java.net.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.clientserver.clientcommunication.ClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientEncryptionSettings;
import org.vpda.clientserver.clientcommunication.ClientNoneEncryptionSettings;

/**
 * Client Communication settings when host and port are the essential part of
 * target system
 * 
 * @author kitko
 *
 */
public abstract class AbstractHostClientCommunicationSettings implements ClientCommunicationSettings, Serializable {
    private static final long serialVersionUID = -8312854333784690597L;
    private final Integer port;
    private final String host;
    private transient final List<Proxy> proxies;
    private final ClientEncryptionSettings encryptionSettings;
    private final boolean enabled;

    /**
     * Creates AbstractHostConnectionSettings using builder
     * 
     * @param builder
     */
    @SuppressWarnings("unchecked")
    protected AbstractHostClientCommunicationSettings(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.proxies = builder.proxies != null ? new ArrayList<Proxy>(builder.getProxies()) : null;
        this.encryptionSettings = builder.encryptionSettings;
        this.enabled = builder.isEnabled();
    }

    /**
     * Creates AbstractHostConnectionSettings
     * 
     * @param host
     * @param port
     * @param proxies
     * @param encryptionSettings
     */
    protected AbstractHostClientCommunicationSettings(String host, Integer port, List<Proxy> proxies, ClientEncryptionSettings encryptionSettings) {
        super();
        this.host = host;
        this.port = port;
        this.proxies = new ArrayList<Proxy>(proxies);
        this.encryptionSettings = encryptionSettings;
        this.enabled = true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @return the port
     */
    public final Integer getPort() {
        return port;
    }

    /**
     * @return the host
     */
    public final String getHost() {
        return host;
    }

    /**
     * @return the proxies
     */
    public final List<Proxy> getProxies() {
        return proxies != null ? Collections.unmodifiableList(proxies) : Collections.<Proxy>emptyList();
    }

    @Override
    public ClientEncryptionSettings getEncryptionSettings() {
        return encryptionSettings;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("AbstractHostConnectionSettings [port=").append(port).append(", host=").append(host).append(", proxies=").append(proxies).append(", encryptionSettings=")
                .append(encryptionSettings).append("]");
        return builder2.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((port == null) ? 0 : port.hashCode());
        result = prime * result + ((proxies == null) ? 0 : proxies.hashCode());
        result = prime * result + ((encryptionSettings == null) ? 0 : encryptionSettings.hashCode());
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
        AbstractHostClientCommunicationSettings other = (AbstractHostClientCommunicationSettings) obj;
        if (host == null) {
            if (other.host != null)
                return false;
        }
        else if (!host.equals(other.host))
            return false;
        if (port == null) {
            if (other.port != null)
                return false;
        }
        else if (!port.equals(other.port))
            return false;
        if (proxies == null) {
            if (other.proxies != null)
                return false;
        }
        else if (!proxies.equals(other.proxies))
            return false;
        if (encryptionSettings == null) {
            if (other.encryptionSettings != null)
                return false;
        }
        else if (!encryptionSettings.equals(other.encryptionSettings))
            return false;
        return true;
    }

    /**
     * @return URI that will be used to select system proxies
     */
    public abstract URI createSelectSystemProxyURI();

    /**
     * Creates filled builder
     * 
     * @param <T>
     * @return builder with values from settings
     */
    public abstract <T extends AbstractHostClientCommunicationSettings> AbstractHostClientCommunicationSettings.Builder<T> createBuilder();

    /**
     * Builder for AbstractHostConnectionSettings
     * 
     * @param <T> Real type of settings
     */
    public static abstract class Builder<T extends AbstractHostClientCommunicationSettings> implements org.vpda.common.util.Builder<T> {
        private Integer port;
        private String host;
        private List<Proxy> proxies;
        private ClientEncryptionSettings encryptionSettings;
        private boolean enabled;

        /** Creates builder */
        protected Builder() {
            host = "localhost";
            proxies = new ArrayList<>(1);
            encryptionSettings = new ClientNoneEncryptionSettings();
            enabled = true;
        }

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * @param enabled the enabled to set
         * @return this
         */
        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * @return the port
         */
        public final Integer getPort() {
            return port;
        }

        /**
         * @param port the port to set
         * @return this
         */
        public final Builder<T> setPort(Integer port) {
            this.port = port;
            return this;
        }

        /**
         * @return the host
         */
        public final String getHost() {
            return host;
        }

        /**
         * @param host the host to set
         * @return this
         */
        public final Builder<T> setHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * @return the encryptionSettings
         */
        public final ClientEncryptionSettings getEncryptionSettings() {
            return encryptionSettings;
        }

        /**
         * @param encryptionSettings the encryptionSettings to set
         * @return this
         */
        public final Builder<T> setEncryptionSettings(ClientEncryptionSettings encryptionSettings) {
            this.encryptionSettings = encryptionSettings;
            return this;
        }

        /**
         * @return the proxies
         */
        public final List<Proxy> getProxies() {
            return proxies == null ? Collections.<Proxy>emptyList() : Collections.unmodifiableList(proxies);
        }

        /**
         * @param proxies the proxies to set
         * @return this
         */
        public final Builder<T> setProxies(List<Proxy> proxies) {
            this.proxies = proxies != null ? new ArrayList<Proxy>(proxies) : null;
            return this;
        }

        /**
         * Adds a proxy
         * 
         * @param proxy
         * @return this
         */
        public final Builder addProxy(Proxy proxy) {
            if (proxies == null) {
                proxies = new ArrayList<Proxy>();
            }
            if (!proxies.contains(proxy)) {
                proxies.add(proxy);
            }
            return this;
        }

        /**
         * Removes a proxy
         * 
         * @param proxy
         * @return this
         */
        public final Builder removeProxy(Proxy proxy) {
            if (proxies == null) {
                return this;
            }
            proxies.remove(proxy);
            return this;
        }

        /**
         * Sets values from AbstractHostConnectionSettings
         * 
         * @param value
         * @return this
         */
        public Builder setValue(T value) {
            this.host = value.getHost();
            this.port = value.getPort();
            this.setProxies(value.getProxies());
            this.encryptionSettings = value.getEncryptionSettings();
            this.enabled = value.isEnabled();
            return this;
        }

    }

}
