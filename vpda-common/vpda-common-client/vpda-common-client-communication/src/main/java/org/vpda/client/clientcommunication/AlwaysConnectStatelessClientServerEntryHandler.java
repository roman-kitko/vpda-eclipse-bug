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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;

/**
 * Handler that will reconnect on communication failure
 * 
 * @author kitko
 *
 */
public final class AlwaysConnectStatelessClientServerEntryHandler implements InvocationHandler {

    private final ClientCommunication clientCommunication;
    private final ClientLoginInfo loginInfo;

    private AlwaysConnectStatelessClientServerEntryHandler(ClientCommunication clientCommunication, ClientLoginInfo loginInfo) {
        super();
        this.clientCommunication = clientCommunication;
        this.loginInfo = loginInfo;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        StatelessClientServerEntry delegate = clientCommunication.getStatelessClientServerEntry(loginInfo);
        try {
            return method.invoke(delegate, args);
        }
        catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                e = (Exception) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Creates always connect handler proxy
     * 
     * @param clientCommunication
     * @param loginInfo
     * @return StatelessClientServerEntry that will try to reconnect in
     *         communication failure
     */
    public static StatelessClientServerEntry createReconnectStatelessClientServerEntry(ClientCommunication clientCommunication, ClientLoginInfo loginInfo) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        AlwaysConnectStatelessClientServerEntryHandler handler = new AlwaysConnectStatelessClientServerEntryHandler(clientCommunication, loginInfo);
        StatelessClientServerEntry proxy = StatelessClientServerEntry.class.cast(Proxy.newProxyInstance(loader, new Class[] { StatelessClientServerEntry.class }, handler));
        return proxy;
    }
}
