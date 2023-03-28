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
package org.vpda.client.clientcommunication.protocol.embedded;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.JavaSerializationUtil;

/**
 * Communication that uses passed LoginServer.
 * 
 * @author kitko
 *
 */
public final class EmbeddedClientCommunicationImpl extends AbstractClientCommunication implements EmbeddedClientCommunication, Serializable {
    private static final long serialVersionUID = 8766439255083726104L;
    private final CommandExecutor loginServerCommandExecutor;
    private final CommadExecutorClientProxyFactory commadExecutorClientProxyFactory;
    private static final ConcurrentMap<Integer, CommandExecutor> executors = new ConcurrentHashMap<>();

    /**
     * Creates EmbeddedClientCommunication with passed login server and
     * commadExecutorClientProxyFactory
     * 
     * @param ccf
     * @param communicationId
     * @param loginServerCommandExecutor
     * @param commadExecutorClientProxyFactory
     */
    public EmbeddedClientCommunicationImpl(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor loginServerCommandExecutor,
            CommadExecutorClientProxyFactory commadExecutorClientProxyFactory) {
        super(ccf, communicationId);
        this.loginServerCommandExecutor = createResolvedExecutor(Assert.isNotNullArgument(loginServerCommandExecutor, "loginServerCommandExecutor"));
        this.commadExecutorClientProxyFactory = Assert.isNotNullArgument(commadExecutorClientProxyFactory, "commadExecutorClientProxyFactory");
    }

    /**
     * We do not want to have reference to server login executor, so rather delegate
     */
    static final class ResolvedExecutor implements CommandExecutor, Serializable {
        private static final long serialVersionUID = 6299438771536569423L;
        private final String id;
        private final int executorIdentityKey;

        ResolvedExecutor(CommandExecutor loginServerCommandExecutor) {
            this.executorIdentityKey = System.identityHashCode(loginServerCommandExecutor);
            this.id = loginServerCommandExecutor.getExecutorId();
            executors.put(executorIdentityKey, loginServerCommandExecutor);
        }

        private CommandExecutor resolveExecutor() {
            CommandExecutor executor = executors.get(executorIdentityKey);
            if (executor == null) {
                throw new VPDARuntimeException("Cannot resolve executor with id " + id);
            }
            return executor;
        }

        @Override
        public CommandExecutor getCommandExecutor() {
            return resolveExecutor();
        }

        @Override
        public void unreferenced() {
            resolveExecutor().unreferenced();
        }

        @Override
        public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
            return resolveExecutor().executeCommand(command, env, event);
        }

        @Override
        public String getExecutorId() {
            return id;
        }

    }

    private CommandExecutor createResolvedExecutor(CommandExecutor loginServerCommandExecutor) {
        return new ResolvedExecutor(loginServerCommandExecutor);
    }

    @Override
    public LoginServer connect(ClientLoginInfo loginInfo) throws CommunicationException {
        return (LoginServer) commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, loginServerCommandExecutor, EmptyCommandExecutionEnv.getInstance(), new Class[] { LoginServer.class });
    }

    @Override
    public CommunicationProtocol getProtocol() {
        return BasicCommunicationProtocol.EMBEDDED;
    }

    @Override
    public Object createStatefullProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces) {
        return commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, new SerializerExecutor(executor), env, ifaces);
    }

    @Override
    public Object createStatelessProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces, ClientLoginInfo loginInfo) {
        return commadExecutorClientProxyFactory.createStatelessProxy(ccf, commId, new SerializerExecutor(executor), env, loginInfo, ifaces);
    }

    @Override
    public CommandExecutor getStatelessClientServerEntryExecutor(ClientLoginInfo loginInfo) throws CommunicationException {
        return loginServerCommandExecutor;
    }

    @Override
    public StatelessClientServerEntry getStatelessClientServerEntry(ClientLoginInfo loginInfo) throws CommunicationException {
        return (StatelessClientServerEntry) commadExecutorClientProxyFactory.createStatelessProxy(ccf, commId, loginServerCommandExecutor, EmptyCommandExecutionEnv.getInstance(), loginInfo,
                new Class[] { StatelessClientServerEntry.class });
    }

    @Override
    public Object simpleValueReturned(Object value) {
        if (value instanceof Serializable) {
            try {
                return JavaSerializationUtil.copyObjectUsingSerialization((Serializable) value, Thread.currentThread().getContextClassLoader());
            }
            catch (Exception e) {
                throw new VPDARuntimeException("Cannot serialize return value", e);
            }
        }
        return value;
    }

    @Override
    public ClientCommunicationSettings getCommunicationSettings() {
        return new EmbeddedClientCommunicationSettings();
    }

}
