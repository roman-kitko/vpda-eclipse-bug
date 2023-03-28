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

import java.lang.reflect.Method;

import org.vpda.clientserver.communication.Communication;
import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;

/**
 * Client communication is some abstraction of communication between client and
 * server.
 * 
 * @author kitko
 *
 */
public interface ClientCommunication extends Communication {
    /**
     * Connects to server in statefull way. Will create login server entry point.
     * 
     * @param loginInfo
     * @return login server
     * @throws CommunicationException
     */
    public LoginServer connect(ClientLoginInfo loginInfo) throws CommunicationException;

    /**
     * Gets executor for StatelessServiceRegistry
     * 
     * @param loginInfo
     * @return executor for StatelessServiceRegistry
     * @throws CommunicationException
     */
    public CommandExecutor getStatelessClientServerEntryExecutor(ClientLoginInfo loginInfo) throws CommunicationException;

    /**
     * Gets stateless service registry
     * 
     * @param loginInfo
     * @return StatelessServiceRegistry
     * @throws CommunicationException
     */
    public StatelessClientServerEntry getStatelessClientServerEntry(ClientLoginInfo loginInfo) throws CommunicationException;

    /**
     * Creates new proxy object
     * 
     * @param executor
     * @param env
     * @param ifaces
     * @return new proxy
     */
    public Object createStatefullProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces);

    /**
     * Creates new proxy object
     * 
     * @param executor
     * @param env
     * @param ifaces
     * @param loginInfo
     * @return new proxy
     */
    public Object createStatelessProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces, ClientLoginInfo loginInfo);

    /**
     * @param value
     * @return simple value to client
     */
    public Object simpleValueReturned(Object value);

    /**
     * Callback method that new server ref was passed to client
     * 
     * @param serverRef
     * @param clientProxy
     * @param ifaces
     */
    public void notifyNewServerRef(Object serverRef, Object clientProxy, Class[] ifaces);

    /**
     * @return client initial settings, that he can use to connect
     */
    @Override
    public ClientCommunicationSettings getCommunicationSettings();

    /**
     * Should stateless entry try to reconnect on this exception
     * 
     * @param e
     * @return true if we should reconnect
     */
    public boolean shouldStatelessEntryTryToReconnectOnThisFailure(Exception e);

    /**
     * @param env
     * @param ifaces
     * @param clientLoginInfoResolver
     * @param method
     * @param args
     * @param serviceInvocationResult
     * @return service result
     */
    public Object processServiceResult(CommandExecutionEnv env, Class[] ifaces, ClientLoginInfoResolver clientLoginInfoResolver, final Method method, final Object[] args,
            ClientServiceInvocationResult serviceInvocationResult);

    /**
     * @param env
     * @param ifaces
     * @param clientLoginInfoResolver
     * @param method
     * @param args
     * @param invocationResult
     * @return value result
     */
    public Object processValueResult(CommandExecutionEnv env, Class[] ifaces, ClientLoginInfoResolver clientLoginInfoResolver, final Method method, final Object[] args,
            ClientValueInvocationResult invocationResult);

}
