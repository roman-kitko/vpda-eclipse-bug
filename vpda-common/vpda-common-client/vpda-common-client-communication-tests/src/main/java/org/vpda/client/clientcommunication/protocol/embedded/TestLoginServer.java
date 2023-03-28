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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.vpda.clientserver.clientcommunication.CommunicationInvocationHandler;
import org.vpda.clientserver.communication.data.AuthenticationEntry;
import org.vpda.clientserver.communication.data.BaseApplicableContexts;
import org.vpda.clientserver.communication.data.ClientUILoginInfo;
import org.vpda.clientserver.communication.data.FullApplicableContexts;
import org.vpda.clientserver.communication.data.LoginInfo;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.data.impl.FullApplicableContextsImpl;
import org.vpda.clientserver.communication.protocol.embedded.TestClientServerImpl;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.clientserver.communication.services.LoginException;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.servercommunication.TestInvocationHandler;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorAccessor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.ApplContextBuilder;
import org.vpda.common.context.DateContext;
import org.vpda.common.context.User;
import org.vpda.common.entrypoint.ConcreteApplication;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.MapObjectResolver;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.PicoContainerObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.service.ServiceRegistry;
import org.vpda.common.service.ServiceRegistryImpl;
import org.vpda.common.util.ComponentInfo;

/**
 * Embedded Login server, now for testing purposes
 * 
 * @author rki
 *
 */
public class TestLoginServer implements LoginServer, CommandExecutorAccessor {
    private CommandExecutor commandExecutor;

    /** Default empty constructor */
    public TestLoginServer() {
        commandExecutor = new CommandExecutorDelegate(new CommandExecutorBase(getClass().getName()));
    }

    @Override
    public BaseApplicableContexts getBaseApplicableContexts(LoginInfo loginInfo) {
        ApplContext applContext1 = new ApplContextBuilder().setId(1L).build();
        ApplContext applContext2 = new ApplContextBuilder().setId(2L).build();
        return new BaseApplicableContexts(Arrays.asList(applContext1, applContext2), 0);
    }

    @Override
    public FullApplicableContexts getFullApplicableContexts(LoginInfo loginInfo) throws LoginException {
        ApplContext applContext1 = new ApplContextBuilder().setId(1L).build();
        ApplContext applContext2 = new ApplContextBuilder().setId(2L).build();
        Map<Long, List<Locale>> localesMap = new HashMap<>();
        localesMap.put(applContext1.getId(), Collections.singletonList(Locale.US));
        localesMap.put(applContext2.getId(), Collections.singletonList(new Locale("sk", "SK")));
        DateContext dateCtx = DateContext.createBuilder().setYear(LocalDate.now().getYear()).build();
        Map<Long, List<DateContext>> dateContextsmap = Collections.singletonMap(loginInfo.getApplContext().getId(), Collections.singletonList(dateCtx));
        return new FullApplicableContextsImpl(Arrays.asList(applContext1, applContext2), 0, localesMap, Locale.US, dateContextsmap, dateCtx);
    }

    @Override
    public List<Locale> getLocalesForContext(LoginInfo loginInfo, ApplContext ctx) throws LoginException {
        return getFullApplicableContexts(loginInfo).getLocalesForContext(ctx);
    }

    @Override
    public AuthenticationEntry generateSecretEntry(AuthenticationEntry entry) throws LoginException {
        return entry;
    }

    @Override
    public LoginInfo authenticate(LoginInfo loginInfo) throws LoginException {
        return loginInfo;
    }

    @Override
    public ClientServer login(LoginInfo loginInfo) {
        User user = new User.UserBuilder().setLogin(loginInfo.getAuthenticationEntry().getLoginToken()).build();
        UserSession userSession = new UserSession.Builder().setSessionId("test").setUser(user).setLoginInfo(loginInfo).setLoginTime(System.currentTimeMillis()).build();
        ServiceRegistry sr = new ServiceRegistryImpl();
        ClientServer clientServerImpl = new TestClientServerImpl(userSession, sr);
        return clientServerImpl;
    }

    private class CommandExecutorDelegate extends org.vpda.common.command.executor.impl.CommandExecutorDelegate {
        private CommandExecutionEnv env;

        private CommandExecutorDelegate(CommandExecutor realExecutor) {
            super(realExecutor);
            List<ObjectResolver> resolvers = new ArrayList<ObjectResolver>(2);
            resolvers.add(SingleObjectResolver.create(CommunicationInvocationHandler.class, new TestInvocationHandler()));
            resolvers.add(new PicoContainerObjectResolver(ConcreteApplication.getInstance().getAllModulesContainer()));
            resolvers.add(new MapObjectResolver(Collections.singletonMap(LoginServer.class, TestLoginServer.this)));
            env = new CEEnvDelegate(new MacroObjectResolverImpl(resolvers));
        }

        @Override
        public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
            return super.executeCommand(command, this.env, event);
        }

    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    @Override
    public ComponentInfo getServerInfo(LoginInfo loginInfo) {
        return null;
    }

    @Override
    public String getClassPathURL(LoginInfo loginInfo) {
        return null;
    }

    @Override
    public byte[] loadClassBytes(LoginInfo loginInfo, String name) throws IOException {
        return null;
    }

    @Override
    public void ping(byte[] bytes) {
    }

    @Override
    public ClientUILoginInfo getUserLoginPreferences(UUID uuid, LoginInfo loginInfo) {
        return null;
    }

    @Override
    public List<DateContext> getDateContextsForContext(LoginInfo loginInfo, ApplContext ctx) throws LoginException {
        return getFullApplicableContexts(loginInfo).getDateContextsForApplContext(ctx);
    }
}
