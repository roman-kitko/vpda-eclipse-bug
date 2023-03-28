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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;

/**
 * Handler that will reconnect on communication failure
 * 
 * @author kitko
 *
 */
public final class ReconnectStatelessClientServerEntryHandler implements InvocationHandler, Serializable {
    private static final long serialVersionUID = 7717353430908693140L;
    private final ClientCommunication clientCommunication;
    private StatelessClientServerEntry delegate;
    private final ClientLoginInfo loginInfo;

    private ReconnectStatelessClientServerEntryHandler(StatelessClientServerEntry delegate, ClientCommunication clientCommunication, ClientLoginInfo loginInfo) {
        super();
        this.delegate = delegate;
        this.clientCommunication = clientCommunication;
        this.loginInfo = loginInfo;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        }
        catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                e = (Exception) e.getCause();
            }
            if (clientCommunication.shouldStatelessEntryTryToReconnectOnThisFailure(e)) {
                delegate = clientCommunication.getStatelessClientServerEntry(loginInfo);
                try {
                    return method.invoke(delegate, args);
                }
                catch (Exception e1) {
                    if (e1 instanceof InvocationTargetException) {
                        e1 = (Exception) e1.getCause();
                    }
                    throw e1;
                }
            }
            else {
                throw e;
            }
        }
    }

    /**
     * Creates reconnect handler proxy
     * 
     * @param clientCommunication
     * @param delegate
     * @param loginInfo
     * @return StatelessClientServerEntry that will try to reconnect in
     *         communication failure
     */
    public static StatelessClientServerEntry createReconnectStatelessClientServerEntry(ClientCommunication clientCommunication, ClientLoginInfo loginInfo, StatelessClientServerEntry delegate) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ReconnectStatelessClientServerEntryHandler handler = new ReconnectStatelessClientServerEntryHandler(delegate, clientCommunication, loginInfo);
        StatelessClientServerEntry proxy = StatelessClientServerEntry.class.cast(Proxy.newProxyInstance(loader, new Class[] { StatelessClientServerEntry.class }, handler));
        return proxy;
    }

    /**
     * Creates reconnect handler proxy
     * 
     * @param clientCommunication
     * @param loginInfo
     * @return StatelessClientServerEntry that will try to reconnect in
     *         communication failure
     * @throws CommunicationException
     */
    public static StatelessClientServerEntry createReconnectStatelessClientServerEntry(ClientCommunication clientCommunication, ClientLoginInfo loginInfo) throws CommunicationException {
        StatelessClientServerEntry delegate = clientCommunication.getStatelessClientServerEntry(loginInfo);
        return createReconnectStatelessClientServerEntry(clientCommunication, loginInfo, delegate);

    }

}
