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
package org.vpda.clientserver.communication.protocol.embedded;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorAccessor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorDelegate;
import org.vpda.common.ioc.objectresolver.MapObjectResolver;
import org.vpda.common.service.Service;
import org.vpda.common.service.ServiceEnvironment;
import org.vpda.common.service.ServiceRegistry;

/**
 * Embedded client server when we have client and server in same JVM, for
 * testing only
 * 
 * @author kitko
 *
 */
public class TestClientServerImpl implements ClientServer, CommandExecutorAccessor {
    private CommandExecutor commandExecutor;
    private UserSession userSession;
    private ServiceRegistry serviceRegistry;

    @Override
    public UserSession getUserSession() {
        return userSession;
    }

    @Override
    public void logout() {
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    /**
     * Creates ClientServerEmbedded
     * 
     * @param session
     * @param serviceRegistry
     */
    public TestClientServerImpl(UserSession session, ServiceRegistry serviceRegistry) {
        this.userSession = session;
        commandExecutor = new ClientServerCommandExecutorDelegate(new CommandExecutorBase(getClass().getName()));
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    private class ClientServerCommandExecutorDelegate extends CommandExecutorDelegate {
        private CommandExecutionEnv env;

        private ClientServerCommandExecutorDelegate(CommandExecutor realExecutor) {
            super(realExecutor);
            Map<Class<?>, ClientServer> objects = new HashMap<Class<?>, ClientServer>();
            objects.put(ClientServer.class, TestClientServerImpl.this);
            env = new CEEnvDelegate(new MapObjectResolver(objects));
        }

        @Override
        public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
            return super.executeCommand(command, this.env, event);
        }

        @Override
        public void unreferenced() {
            logout();
        }
    }

    @Override
    public <T extends Service> T getService(Class<T> serviceClass) {
        return serviceRegistry.getService(serviceClass);
    }

    @Override
    public Collection<Class<? extends Service>> getServicesKeys() {
        return serviceRegistry.getServicesKeys();
    }

    @Override
    public <T extends Service> T getService(Class<T> serviceClass, ServiceEnvironment env) {
        return serviceRegistry.getService(serviceClass, env);
    }

}
