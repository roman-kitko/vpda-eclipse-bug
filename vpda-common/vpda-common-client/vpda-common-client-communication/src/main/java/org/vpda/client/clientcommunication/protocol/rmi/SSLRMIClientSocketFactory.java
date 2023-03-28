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
package org.vpda.client.clientcommunication.protocol.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.server.RMIClientSocketFactory;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.io.IOUtils;
import org.vpda.clientserver.clientcommunication.ClientSSLEncryptionSettings;
import org.vpda.clientserver.communication.compression.VPDASocket;
import org.vpda.internal.common.util.URLUtil;

final class SSLRMIClientSocketFactory implements RMIClientSocketFactory {
    private final RMIClientCommunicationSettings clientSettings;
    private final Proxy proxy;

    private static final String localAddress;

    static {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            localAddress = inetAddress.getHostAddress();
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    SSLRMIClientSocketFactory(RMIClientCommunicationSettings clientSettings) {
        this.clientSettings = clientSettings;
        proxy = getSocksProxy();
    }

    private Proxy getSocksProxy() {
        for (Proxy proxy : clientSettings.getProxies()) {
            if (Proxy.Type.SOCKS.equals(proxy.type())) {
                return proxy;
            }
        }
        return null;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        if (localAddress.equals(host)) {
            host = "127.0.0.1";
        }
        if (proxy != null) {
            return createSocksSocket(host, port);
        }
        return plainSocket(host, port);
    }

    private SSLSocketFactory createSSLSocketFactory() throws IOException {
        InputStream trustStoreIs = null;
        InputStream keyStoreIs = null;
        try {
            ClientSSLEncryptionSettings clientSSLEncryptionSettings = (ClientSSLEncryptionSettings) clientSettings.getEncryptionSettings();
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            URL trustStoreURL = URLUtil.resolveURL(clientSSLEncryptionSettings.getTrustManagerKeyStorePath());
            trustStoreIs = trustStoreURL.openStream();
            trustStore.load(trustStoreIs, clientSSLEncryptionSettings.getTrustManagerKeyStorePassword());
            String algorithm = "TLSv1.3";
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = null;
            if (clientSSLEncryptionSettings.getKeyManagerKeyStorePath() != null) {
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                URL keyStoreURL = URLUtil.resolveURL(clientSSLEncryptionSettings.getKeyManagerKeyStorePath());
                keyStoreIs = keyStoreURL.openStream();
                keyStore.load(keyStoreIs, clientSSLEncryptionSettings.getKeyManagerKeyStorePassword());
            }
            kmfactory.init(keyStore, clientSSLEncryptionSettings.getKeyManagerKeyStorePassword());
            KeyManager[] keymanagers = kmfactory.getKeyManagers();
            TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmfactory.init(trustStore);
            TrustManager[] trustmanagers = tmfactory.getTrustManagers();
            SSLContext sslcontext = SSLContext.getInstance(algorithm);
            sslcontext.init(keymanagers, trustmanagers, null);
            return sslcontext.getSocketFactory();
        }
        catch (Exception e) {
            throw new IOException(e);
        }
        finally {
            IOUtils.closeQuietly(trustStoreIs);
            IOUtils.closeQuietly(keyStoreIs);
        }
    }

    private Socket plainSocket(String host, int port) throws IOException {
        SSLSocketFactory socketFactory = createSSLSocketFactory();
        Socket socket = new Socket(host, port);
        socket = new VPDASocket(socket);
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(socket, host, port, true);
        sslSocket.startHandshake();
        return sslSocket;
    }

    private Socket createSocksSocket(String host, int port) throws IOException {
        SSLSocketFactory socketFactory = createSSLSocketFactory();
        Socket socket = new Socket(proxy);
        socket = new VPDASocket(socket);
        socket.connect(new InetSocketAddress(host, port));
        return socketFactory.createSocket(socket, host, port, true);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientSettings == null) ? 0 : clientSettings.hashCode());
        result = prime * result + ((proxy == null) ? 0 : proxy.hashCode());
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
        SSLRMIClientSocketFactory other = (SSLRMIClientSocketFactory) obj;
        if (clientSettings == null) {
            if (other.clientSettings != null)
                return false;
        }
        else if (!clientSettings.equals(other.clientSettings))
            return false;
        if (proxy == null) {
            if (other.proxy != null)
                return false;
        }
        else if (!proxy.equals(other.proxy))
            return false;
        return true;
    }

}