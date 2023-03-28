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
package org.vpda.client.clientcommunication.protocol.embedded;

import org.vpda.client.clientcommunication.AbstractClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactory;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.internal.common.util.Assert;

/**
 * Embedded implementation of ClientCommunication.
 * 
 * @author kitko
 *
 */
public class EmbeddedClientCommunicationTest extends AbstractClientCommunication implements EmbeddedClientCommunication {
    private static final long serialVersionUID = 8137476516754583573L;
    private CommadExecutorClientProxyFactory commadExecutorClientProxyFactory;

    /**
     * Creates EmbeddedClientCommunicationImpl
     * 
     * @param ccf
     * @param communicationId
     * @param commadExecutorClientProxyFactory
     *
     */
    public EmbeddedClientCommunicationTest(ClientCommunicationFactory ccf, CommunicationId communicationId, CommadExecutorClientProxyFactory commadExecutorClientProxyFactory) {
        super(ccf, communicationId);
        this.commadExecutorClientProxyFactory = Assert.isNotNullArgument(commadExecutorClientProxyFactory, "commadExecutorClientProxyFactory");
    }

    /**
     * Returns loginServer executor, client can later use to make login
     * 
     * @param loginInfo
     * @return loginServer
     */
    protected CommandExecutor createLoginCommandExecutor(ClientLoginInfo loginInfo) {
        CommandExecutor loginExecutor = new TestLoginServer().getCommandExecutor();
        return loginExecutor;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public CommunicationProtocol getProtocol() {
        return BasicCommunicationProtocol.EMBEDDED;
    }

    /**
     * @return CommadExecutorClientProxyFactory
     */
    protected CommadExecutorClientProxyFactory getCommadExecutorClientProxyFactory() {
        return commadExecutorClientProxyFactory;
    }

    @Override
    public LoginServer connect(ClientLoginInfo loginInfo) throws CommunicationException {
        return (LoginServer) commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, createLoginCommandExecutor(loginInfo), EmptyCommandExecutionEnv.getInstance(),
                new Class[] { LoginServer.class });
    }

    @Override
    public void notifyNewServerRef(Object serverRef, Object clientProxy, Class[] ifaces) {
    }

    @Override
    public Object createStatefullProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces) {
        return commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, executor, env, ifaces);
    }

    @Override
    public Object createStatelessProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces, ClientLoginInfo loginInfo) {
        return commadExecutorClientProxyFactory.createStatelessProxy(ccf, commId, executor, env, loginInfo, ifaces);
    }

    @Override
    public Object simpleValueReturned(Object value) {
        return value;
    }

    @Override
    public ClientCommunicationSettings getCommunicationSettings() {
        return new EmbeddedClientCommunicationSettings();
    }

    @Override
    public CommandExecutor getStatelessClientServerEntryExecutor(ClientLoginInfo loginInfo) throws CommunicationException {
        return null;
    }

    @Override
    public StatelessClientServerEntry getStatelessClientServerEntry(ClientLoginInfo loginInfo) throws CommunicationException {
        return null;
    }

    @Override
    public boolean shouldStatelessEntryTryToReconnectOnThisFailure(Exception e) {
        return false;
    }

}
