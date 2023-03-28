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
package org.vpda.abstractclient.core.impl;

import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.client.clientcommunication.ReconnectStatelessClientServerEntryHandler;
import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.clientserver.communication.services.impl.LoginServerStatelessBridge;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandExceptionExecutionPolicy;
import org.vpda.common.util.ProgressChangeUnit;
import org.vpda.internal.common.util.Assert;

/**
 * Command that will establish connection to server
 * 
 * @author kitko
 *
 */
final class ConnectCommand extends AbstractRequestCommand<LoginServer> {
    private final ClientLoginInfo loginInfo;
    private final ClientUI clientUI;
    private final ClientImpl client;
    private final ClientCommunicationFactory clientComunicationFactory;

    ConnectCommand(CommandExecutorRegistry registry, ClientImpl client, ClientUI clientUI, ClientLoginInfo loginInfo, ClientCommunicationFactory clientComunicationFactory) {
        super(registry);
        this.loginInfo = loginInfo;
        this.client = Assert.isNotNull(client);
        this.clientUI = Assert.isNotNull(clientUI);
        this.clientComunicationFactory = Assert.isNotNull(clientComunicationFactory);
    }

    @Override
    protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
        addProgressObserver(clientUI.getLoginFrame());
        clientUI.getLoginFrame().setEnabledUI(false);
        return true;
    }

    @Override
    protected LoginServer executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        // Here we will Connect to server using communication
        ClientCommunication clientCommunication = clientComunicationFactory.createCommunication(loginInfo.getConnectionInfo().getCommunicationId());
        LoginServer loginServer = null;
        if (CommunicationStateKind.Statefull.equals(loginInfo.getConnectionInfo().getCommunicationStateKind())) {
            loginServer = clientCommunication.connect(loginInfo);
        }
        else {
            StatelessClientServerEntry statelessClientServerEntry = ReconnectStatelessClientServerEntryHandler.createReconnectStatelessClientServerEntry(clientCommunication, loginInfo);
            loginServer = new LoginServerStatelessBridge(statelessClientServerEntry);
        }
        return loginServer;
    }

    @Override
    protected void executeAfterRequest(LoginServer loginServer, CommandExecutionEnv env, CommandEvent event) {
        getProgressNotifier().notifyProgressChanged(10, ProgressChangeUnit.RELATIVE, "Connected", null);
        client.setLoginServer(loginServer);
        client.setLoginInfo(loginInfo);
        client.connected();
        clientUI.getLoginFrame().setEnabledUI(true);
        client.startAuthenticateWorkflow(loginInfo);
    }

    @Override
    protected RequestCommandExceptionExecutionPolicy executeExceptionHandling(Exception e, CommandExecutionEnv env, CommandEvent event) {
        clientUI.getLoginFrame().setEnabledUI(true);
        client.logAndShowExceptionDialog(e, getClass(), "Error while connecting", false);
        return RequestCommandExceptionExecutionPolicy.ABORT;
    }
}
