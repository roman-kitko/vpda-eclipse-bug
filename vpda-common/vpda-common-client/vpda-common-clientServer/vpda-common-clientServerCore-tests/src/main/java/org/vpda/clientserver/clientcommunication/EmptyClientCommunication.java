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

import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;

final class EmptyClientCommunication implements ClientCommunication {

    @Override
    public LoginServer connect(ClientLoginInfo loginInfo) throws CommunicationException {
        return null;
    }

    @Override
    public CommunicationId getCommunicationId() {
        return null;
    }

    @Override
    public CommunicationProtocol getProtocol() {
        return null;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void notifyNewServerRef(Object serverRef, Object clientProxy, Class[] ifaces) {
    }

    @Override
    public Object createStatefullProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces) {
        return null;
    }

    @Override
    public Object createStatelessProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces, ClientLoginInfo loginInfo) {
        return null;
    }

    @Override
    public Object simpleValueReturned(Object value) {
        return value;
    }

    @Override
    public ClientCommunicationSettings getCommunicationSettings() {
        return null;
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

    @Override
    public Object processServiceResult(CommandExecutionEnv env, Class[] ifaces, ClientLoginInfoResolver clientLoginInfoResolver, Method method, Object[] args,
            ClientServiceInvocationResult serviceInvocationResult) {
        return null;
    }

    @Override
    public Object processValueResult(CommandExecutionEnv env, Class[] ifaces, ClientLoginInfoResolver clientLoginInfoResolver, Method method, Object[] args,
            ClientValueInvocationResult invocationResult) {
        return invocationResult.getResult();
    }

}
