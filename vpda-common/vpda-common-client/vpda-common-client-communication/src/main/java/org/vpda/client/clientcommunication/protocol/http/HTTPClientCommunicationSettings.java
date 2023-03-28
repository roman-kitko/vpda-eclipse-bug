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
package org.vpda.client.clientcommunication.protocol.http;

import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.vpda.client.clientcommunication.AbstractHostClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientNoneEncryptionSettings;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.protocol.http.HTTPClientServerConstants;
import org.vpda.common.annotations.Immutable;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Simple holder of HTTP client communication settings
 * 
 * @author kitko
 *
 */
@Immutable
public final class HTTPClientCommunicationSettings extends AbstractHostClientCommunicationSettings {
    private static final long serialVersionUID = 492578570616090598L;
    private final String relativeUri;
    private final int pingPeriod;

    /**
     * Creates HTTPClientCommunicationSettingsImpl with defaults
     */
    public HTTPClientCommunicationSettings() {
        super(HTTPClientServerConstants.LOCALHOST, HTTPClientServerConstants.HTTP_PORT, Collections.<Proxy>emptyList(), new ClientNoneEncryptionSettings());
        relativeUri = HTTPClientServerConstants.STATEFULL_SERVER_URI;
        pingPeriod = HTTPClientServerConstants.CLIENT_PING_PERIOD;
    }

    private HTTPClientCommunicationSettings(Builder builder) {
        super(builder);
        this.pingPeriod = builder.pingPeriod;
        this.relativeUri = builder.relativeUri;
    }

    @Override
    public URI createSelectSystemProxyURI() {
        try {
            return new URI("http://" + getHost() + ":" + getPort() + "/" + relativeUri);
        }
        catch (URISyntaxException e) {
            throw new VPDARuntimeException("Invalid URI", e);
        }
    }

    /**
     * @return ping period to keep connection alive
     */
    public int getPingPeriod() {
        return pingPeriod;
    }

    /**
     * @return the relativeUri
     */
    public String getRelativeUri() {
        return relativeUri;
    }

    @Override
    public CommunicationProtocol getProtocol() {
        return BasicCommunicationProtocol.HTTP;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("HTTPClientCommunicationSettings [getHost()=");
        builder2.append(getHost());
        builder2.append(", getPort()=");
        builder2.append(getPort());
        builder2.append(", getEncryptionSettings()=");
        builder2.append(getEncryptionSettings());
        builder2.append(", relativeUri=");
        builder2.append(relativeUri);
        builder2.append("]");
        return builder2.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + pingPeriod;
        result = prime * result + ((relativeUri == null) ? 0 : relativeUri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        HTTPClientCommunicationSettings other = (HTTPClientCommunicationSettings) obj;
        if (pingPeriod != other.pingPeriod) {
            return false;
        }
        if (relativeUri == null) {
            if (other.relativeUri != null) {
                return false;
            }
        }
        else if (!relativeUri.equals(other.relativeUri)) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HTTPClientCommunicationSettings.Builder createBuilder() {
        HTTPClientCommunicationSettings.Builder builder = new HTTPClientCommunicationSettings.Builder();
        builder.setValue(this);
        return builder;
    }

    /**
     * Builder for HTTPClientCommunicationSettings
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractHostClientCommunicationSettings.Builder<HTTPClientCommunicationSettings> {
        private String relativeUri;
        private int pingPeriod;

        /** Creates Builder */
        public Builder() {
            super();
            relativeUri = HTTPClientServerConstants.STATEFULL_SERVER_URI;
            pingPeriod = HTTPClientServerConstants.CLIENT_PING_PERIOD;
        }

        @Override
        public Class<? extends HTTPClientCommunicationSettings> getTargetClass() {
            return HTTPClientCommunicationSettings.class;
        }

        @Override
        public HTTPClientCommunicationSettings build() {
            return new HTTPClientCommunicationSettings(this);
        }

        /**
         * @return the relativeUri
         */
        public final String getRelativeUri() {
            return relativeUri;
        }

        /**
         * @param relativeUri the relativeUri to set
         * @return this
         */
        public final Builder setRelativeUri(String relativeUri) {
            this.relativeUri = relativeUri;
            return this;
        }

        /**
         * @return the pingPeriod
         */
        public final int getPingPeriod() {
            return pingPeriod;
        }

        /**
         * @param pingPeriod the pingPeriod to set
         * @return this
         */
        public final Builder setPingPeriod(int pingPeriod) {
            this.pingPeriod = pingPeriod;
            return this;
        }

        @Override
        public Builder setValue(HTTPClientCommunicationSettings value) {
            super.setValue(value);
            this.pingPeriod = value.getPingPeriod();
            this.relativeUri = value.getRelativeUri();
            return this;
        }

    }

}
