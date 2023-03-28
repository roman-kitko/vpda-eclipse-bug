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

import org.vpda.clientserver.communication.EncryptionType;
import org.vpda.common.annotations.Immutable;

/** Settings for SSL encryption */
@Immutable
public final class ClientSSLEncryptionSettings implements ClientEncryptionSettings, Serializable {
    private static final long serialVersionUID = -5661474926409283996L;

    private ClientSSLEncryptionSettings(Builder builder) {
        this.keyManagerKeyStorePath = builder.getKeyManagerKeyStorePath();
        this.keyManagerKeyStorePassword = builder.getKeyManagerKeyStorePassword();
        this.trustManagerKeyStorePath = builder.getTrustManagerKeyStorePath();
        this.trustManagerKeyStorePassword = builder.getTrustManagerKeyStorePassword();
    }

    /**
     * Creates ClientSSLEncryptionSettings with defaults
     */
    public ClientSSLEncryptionSettings() {
        this.keyManagerKeyStorePath = null;
        this.keyManagerKeyStorePassword = null;
        this.trustManagerKeyStorePath = ClientSSLEncryptionSettings.class.getResource("vpda-client.jks").toExternalForm();
        this.trustManagerKeyStorePassword = "vpdapasswd".toCharArray();
    }

    @Override
    public EncryptionType getType() {
        return EncryptionType.SSL;
    }

    private final String keyManagerKeyStorePath;
    private final char[] keyManagerKeyStorePassword;

    private final String trustManagerKeyStorePath;
    private final char[] trustManagerKeyStorePassword;

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the keyManagerKeyStorePath
     */
    public String getKeyManagerKeyStorePath() {
        return keyManagerKeyStorePath;
    }

    /**
     * @return the keyManagerKeyStorePassword
     */
    public char[] getKeyManagerKeyStorePassword() {
        return keyManagerKeyStorePassword;
    }

    /**
     * @return the trustManagerKeyStorePath
     */
    public String getTrustManagerKeyStorePath() {
        return trustManagerKeyStorePath;
    }

    /**
     * @return the trustManagerKeyStorePassword
     */
    public char[] getTrustManagerKeyStorePassword() {
        return trustManagerKeyStorePassword;
    }

    /** Builder for ClientSSLEncryptionSettings */
    public static final class Builder implements org.vpda.common.util.Builder<ClientSSLEncryptionSettings> {
        private String keyManagerKeyStorePath;
        private char[] keyManagerKeyStorePassword;

        private String trustManagerKeyStorePath;
        private char[] trustManagerKeyStorePassword;

        /**
         * @return the keyManagerKeyStorePath
         */
        public String getKeyManagerKeyStorePath() {
            return keyManagerKeyStorePath;
        }

        /**
         * @param keyManagerKeyStorePath the keyManagerKeyStorePath to set
         * @return this
         */
        public Builder setKeyManagerKeyStorePath(String keyManagerKeyStorePath) {
            this.keyManagerKeyStorePath = keyManagerKeyStorePath;
            return this;
        }

        /**
         * @return the keyManagerKeyStorePassword
         */
        public char[] getKeyManagerKeyStorePassword() {
            return keyManagerKeyStorePassword;
        }

        /**
         * @param keyManagerKeyStorePassword the keyManagerKeyStorePassword to set
         * @return this
         */
        public Builder setKeyManagerKeyStorePassword(char[] keyManagerKeyStorePassword) {
            this.keyManagerKeyStorePassword = keyManagerKeyStorePassword;
            return this;
        }

        /**
         * @return the trustManagerKeyStorePath
         */
        public String getTrustManagerKeyStorePath() {
            return trustManagerKeyStorePath;
        }

        /**
         * @param trustManagerKeyStorePath the trustManagerKeyStorePath to set
         * @return this
         */
        public Builder setTrustManagerKeyStorePath(String trustManagerKeyStorePath) {
            this.trustManagerKeyStorePath = trustManagerKeyStorePath;
            return this;
        }

        /**
         * @return the trustManagerKeyStorePassword
         */
        public char[] getTrustManagerKeyStorePassword() {
            return trustManagerKeyStorePassword;
        }

        /**
         * @param trustManagerKeyStorePassword the trustManagerKeyStorePassword to set
         * @return this
         */
        public Builder setTrustManagerKeyStorePassword(char[] trustManagerKeyStorePassword) {
            this.trustManagerKeyStorePassword = trustManagerKeyStorePassword;
            return this;
        }

        @Override
        public Class<? extends ClientSSLEncryptionSettings> getTargetClass() {
            return ClientSSLEncryptionSettings.class;
        }

        @Override
        public ClientSSLEncryptionSettings build() {
            return new ClientSSLEncryptionSettings(this);
        }

        /**
         * @param v
         * @return this
         */
        public Builder setValues(ClientSSLEncryptionSettings v) {
            keyManagerKeyStorePath = v.getKeyManagerKeyStorePath();
            keyManagerKeyStorePassword = v.getKeyManagerKeyStorePassword();
            trustManagerKeyStorePath = v.getTrustManagerKeyStorePath();
            trustManagerKeyStorePassword = v.getTrustManagerKeyStorePassword();
            return this;
        }

    }
}
