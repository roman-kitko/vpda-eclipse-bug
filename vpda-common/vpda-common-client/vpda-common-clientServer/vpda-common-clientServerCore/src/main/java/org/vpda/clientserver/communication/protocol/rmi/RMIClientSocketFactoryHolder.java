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
package org.vpda.clientserver.communication.protocol.rmi;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import org.vpda.clientserver.communication.module.ClientServerCoreModule;

/**
 * Holder of RMIClientSocketFactory used on server and client side both
 * 
 * @author kitko
 *
 */
public final class RMIClientSocketFactoryHolder implements RMIClientSocketFactory, Serializable {
    private static final String CLASS_NAME = RMIClientSocketFactoryHolder.class.getName();
    private static final long serialVersionUID = 1L;
    static RMIClientSocketFactory staticDelegate;
    static int count = 0;

    /**
     * Sets the delegate
     * 
     * @param delegate
     */
    public static void setDelegate(RMIClientSocketFactory delegate) {
        RMIClientSocketFactoryHolder.staticDelegate = delegate;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return resolveDelegate().createSocket(host, port);
    }

    private RMIClientSocketFactory resolveDelegate() {
        if (staticDelegate != null) {
            return staticDelegate;
        }
        RMIClientSocketFactory d = ClientServerCoreModule.getInstance().getContainer().getComponent(RMIClientSocketFactory.class);
        if (d != null) {
            return d;
        }
        throw new IllegalStateException("Cannot resolve delegate");
    }

    @Override
    public boolean equals(Object o) {
        return (o == this || o instanceof RMIClientSocketFactoryHolder);
    }

    @Override
    public int hashCode() {
        return CLASS_NAME.hashCode();
    }

}
