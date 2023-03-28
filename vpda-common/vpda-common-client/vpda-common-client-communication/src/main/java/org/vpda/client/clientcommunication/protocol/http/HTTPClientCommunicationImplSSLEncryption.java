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
/**
 * 
 */
package org.vpda.client.clientcommunication.protocol.http;

import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.URIScheme;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientSSLEncryptionSettings;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactory;
import org.vpda.internal.common.util.URLUtil;

/**
 * HTTPClientCommunicationImpl for SSL Encryption
 * 
 * @author kitko
 *
 */
public final class HTTPClientCommunicationImplSSLEncryption extends AbstractHTTPClientCommunication implements HTTPClientCommunicationSSLEncryption {
    private static final long serialVersionUID = -1211344131568150971L;

    /**
     * Creates HTTPClientCommunicationImplNoneEncryption
     * 
     * @param ccf
     * @param communicationId
     * @param settings
     * @param commadExecutorClientProxyFactory
     */
    public HTTPClientCommunicationImplSSLEncryption(ClientCommunicationFactory ccf, CommunicationId communicationId, HTTPClientCommunicationSettings settings,
            CommadExecutorClientProxyFactory commadExecutorClientProxyFactory) {
        super(ccf, communicationId, settings, commadExecutorClientProxyFactory);
    }

    @Override
    protected org.apache.hc.core5.http.config.Registry<org.apache.hc.client5.http.socket.ConnectionSocketFactory> createSchemeRegistry(HTTPClientCommunicationSettings settings) throws Exception {
        org.apache.hc.core5.http.config.RegistryBuilder<org.apache.hc.client5.http.socket.ConnectionSocketFactory> registryBuilder = org.apache.hc.core5.http.config.RegistryBuilder.create();
        ClientSSLEncryptionSettings clientSSLEncryptionSettings = (ClientSSLEncryptionSettings) settings.getEncryptionSettings();
        InputStream trustStoreIs = null;
        InputStream keyStoreIs = null;
        try {
            KeyStore trustStore = null;
            if (clientSSLEncryptionSettings.getTrustManagerKeyStorePath() != null) {
                URL trustStoreURL = URLUtil.resolveURL(clientSSLEncryptionSettings.getTrustManagerKeyStorePath());
                trustStoreIs = trustStoreURL.openStream();
                trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(trustStoreIs, clientSSLEncryptionSettings.getTrustManagerKeyStorePassword());
            }
            KeyStore keyStore = null;
            if (clientSSLEncryptionSettings.getKeyManagerKeyStorePath() != null) {
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                URL keyStoreURL = URLUtil.resolveURL(clientSSLEncryptionSettings.getKeyManagerKeyStorePath());
                keyStoreIs = keyStoreURL.openStream();
                keyStore.load(keyStoreIs, clientSSLEncryptionSettings.getKeyManagerKeyStorePassword());
            }
            String keyStorePassword = clientSSLEncryptionSettings.getKeyManagerKeyStorePassword() != null ? new String(clientSSLEncryptionSettings.getKeyManagerKeyStorePassword()) : null;

            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keyStorePassword != null ? keyStorePassword.toCharArray() : null);
            KeyManager[] keymanagers = kmf.getKeyManagers();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            SecureRandom secureRandom = new SecureRandom();
            sslContext.init(keymanagers, trustManagers, secureRandom);
            org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory sslSocketFactory = new org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            registryBuilder.register(URIScheme.HTTPS.getId(), sslSocketFactory);
            org.apache.hc.core5.http.config.Registry<org.apache.hc.client5.http.socket.ConnectionSocketFactory> registry = registryBuilder.build();
            return registry;
        }
        finally {
            IOUtils.closeQuietly(trustStoreIs);
            IOUtils.closeQuietly(keyStoreIs);
        }
    }

}
