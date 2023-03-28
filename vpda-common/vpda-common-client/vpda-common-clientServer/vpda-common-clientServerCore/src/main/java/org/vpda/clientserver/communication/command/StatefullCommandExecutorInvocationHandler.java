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

import java.lang.reflect.Method;

import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;

final class StatefullCommandExecutorInvocationHandler extends AbstractCommandExecutorInvocationHandler {

    StatefullCommandExecutorInvocationHandler(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor commandExecutor, CommandExecutionEnv env, Class[] ifaces) {
        super(ccf, communicationId, commandExecutor, env, CommunicationStateKind.Statefull, ifaces);
    }

    private static final long serialVersionUID = 1508833100915169150L;

    @Override
    AbstractInvocationCommand createInvocationCommand(final Method method, final Object[] args) {
        return new StatefullInvocationCommand(communicationId, ifaces, method.getName(), method.getParameterTypes(), args);
    }
}
