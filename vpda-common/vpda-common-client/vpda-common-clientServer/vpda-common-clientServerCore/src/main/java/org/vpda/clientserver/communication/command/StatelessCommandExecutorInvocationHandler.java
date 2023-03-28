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
package org.vpda.clientserver.communication.command;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.clientcommunication.ClientLoginInfoResolver;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.clientserver.communication.ServiceInvocationResult;
import org.vpda.clientserver.communication.ValueInvocationResult;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;

final class StatelessCommandExecutorInvocationHandler extends AbstractCommandExecutorInvocationHandler {
    private static final long serialVersionUID = 1508833100915169150L;
    private ClientLoginInfo loginInfo;

    private final MyLoginResolver myLoginResolver = new MyLoginResolver();

    StatelessCommandExecutorInvocationHandler(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor commandExecutor, CommandExecutionEnv env, Class[] ifaces,
            ClientLoginInfo loginInfo) {
        super(ccf, communicationId, commandExecutor, env, CommunicationStateKind.Stateless, ifaces);
        this.loginInfo = loginInfo;
    }

    @Override
    AbstractInvocationCommand createInvocationCommand(final Method method, final Object[] args) {
        return new StatelessInvocationCommand(communicationId, ifaces, method.getName(), method.getParameterTypes(), args, loginInfo.createLoginInfo());
    }

    private final class MyLoginResolver implements ClientLoginInfoResolver, Serializable {
        private static final long serialVersionUID = -3945944824240403025L;

        @Override
        public ClientLoginInfo resolveLoginInfoForServiceReturned(Class[] ifaces, Method method, Object[] args, ServiceInvocationResult result) {
            for (int i = 0; i < args.length; i++) {
                if (ClientLoginInfo.class.isInstance(args[i])) {
                    return (ClientLoginInfo) args[i];
                }
            }
            if (loginInfo != null) {
                return loginInfo;
            }
            throw new IllegalStateException("Cannot resolve logininfo");
        }

        @Override
        public void serviceValueReturned(Class[] ifaces, Method method, Object[] args, ValueInvocationResult invocationResult) {
            if (org.vpda.clientserver.communication.services.StatelessClientServerEntry.class.equals(method.getDeclaringClass())
                    && "authenticateAndGenerateTransientSession".equals(method.getName())) {
                if (UserSession.class.isInstance(invocationResult.getResult())) {
                    UserSession userSession = (UserSession) invocationResult.getResult();
                    ClientLoginInfo.LoginInfoBuilder builder = new ClientLoginInfo.LoginInfoBuilder();
                    builder.setValues(loginInfo);
                    builder.setContext(userSession.getContext());
                    StatelessCommandExecutorInvocationHandler.this.loginInfo = builder.build();
                }
            }
        }
    }

    @Override
    ClientLoginInfoResolver createLoginResolver() {
        return myLoginResolver;
    }
}
