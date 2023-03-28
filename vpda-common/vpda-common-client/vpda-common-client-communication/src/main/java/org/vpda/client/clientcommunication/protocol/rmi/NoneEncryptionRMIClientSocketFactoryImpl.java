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
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.RMIClientSocketFactory;

import org.vpda.clientserver.communication.compression.VPDASocket;

final class NoneEncryptionRMIClientSocketFactoryImpl implements RMIClientSocketFactory, Serializable {
    private static final long serialVersionUID = -1019896620375402159L;

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

    private final RMIClientCommunicationSettings clientSettings;
    private final Proxy proxy;

    NoneEncryptionRMIClientSocketFactoryImpl(RMIClientCommunicationSettings clientSettings) {
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

    private Socket createSocksSocket(String host, int port) throws IOException {
        Socket socket = new Socket(proxy);
        socket = new VPDASocket(socket);
        socket.connect(new InetSocketAddress(host, port));
        return socket;
    }

    private Socket plainSocket(String host, int port) throws UnknownHostException, IOException {
        Socket socket = new Socket(host, port);
        socket = new VPDASocket(socket);
        return socket;
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
        NoneEncryptionRMIClientSocketFactoryImpl other = (NoneEncryptionRMIClientSocketFactoryImpl) obj;
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