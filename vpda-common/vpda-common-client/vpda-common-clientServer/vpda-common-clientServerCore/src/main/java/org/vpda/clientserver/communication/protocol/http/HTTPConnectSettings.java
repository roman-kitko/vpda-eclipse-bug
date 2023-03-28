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
package org.vpda.clientserver.communication.protocol.http;

/** HTTP client connection settings */
public final class HTTPConnectSettings {
    private final String host;
    private final Integer port;
    private final String scheme;
    private final String relativeURI;

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @return the scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * @return the relativeURI
     */
    public String getRelativeURI() {
        return relativeURI;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("HTTPConnectSettings [host=");
        builder2.append(host);
        builder2.append(", port=");
        builder2.append(port);
        builder2.append(", scheme=");
        builder2.append(scheme);
        builder2.append(", relativeURI=");
        builder2.append(relativeURI);
        builder2.append("]");
        return builder2.toString();
    }

    /**
     * Creates HTTPConnectSettings
     * 
     * @param host
     * @param port
     * @param scheme
     * @param relativeURI
     */
    public HTTPConnectSettings(String host, Integer port, String scheme, String relativeURI) {
        super();
        this.host = host;
        this.port = port;
        this.scheme = scheme;
        this.relativeURI = relativeURI;
    }

    private HTTPConnectSettings(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.scheme = builder.scheme;
        this.relativeURI = builder.relativeURI;
    }

    /** Builder for HTTPConnectSettings */
    public static final class Builder implements org.vpda.common.util.Builder<HTTPConnectSettings> {
        private String host;
        private Integer port;
        private String scheme;
        private String relativeURI;

        /**
         * @return the host
         */
        public String getHost() {
            return host;
        }

        /**
         * @param host the host to set
         * @return this
         */
        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * @return the port
         */
        public Integer getPort() {
            return port;
        }

        /**
         * @param port the port to set
         * @return this
         */
        public Builder setPort(Integer port) {
            this.port = port;
            return this;
        }

        /**
         * @return the scheme
         */
        public String getScheme() {
            return scheme;
        }

        /**
         * @param scheme the scheme to set
         * @return this
         */
        public Builder setScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * @return the relativeURI
         */
        public String getRelativeURI() {
            return relativeURI;
        }

        /**
         * @param relativeURI the relativeURI to set
         * @return this
         */
        public Builder setRelativeURI(String relativeURI) {
            this.relativeURI = relativeURI;
            return this;
        }

        @Override
        public Class<? extends HTTPConnectSettings> getTargetClass() {
            return HTTPConnectSettings.class;
        }

        @Override
        public HTTPConnectSettings build() {
            return new HTTPConnectSettings(this);
        }

    }

}
