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
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.data.AuthenticationEntry;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandExceptionExecutionPolicy;
import org.vpda.common.util.ProgressChangeUnit;
import org.vpda.internal.common.util.Assert;

final class GenerateSecretEntryCommand extends AbstractRequestCommand<ClientLoginInfo> {

    private final ClientLoginInfo loginInfo;
    private final LoginServer loginServer;
    private final ClientUI clientUI;
    private final ClientImpl client;

    GenerateSecretEntryCommand(CommandExecutorRegistry registry, ClientUI clientUI, ClientImpl client) {
        super(registry);
        this.client = Assert.isNotNullArgument(client, "client");
        this.clientUI = Assert.isNotNullArgument(clientUI, "clientUI");
        this.loginInfo = client.getLoginInfo();
        this.loginServer = client.getLoginServer();
    }

    @Override
    protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
        addProgressObserver(clientUI.getLoginFrame());
        clientUI.getLoginFrame().setEnabledUI(false);
        return true;

    }

    @Override
    protected ClientLoginInfo executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        AuthenticationEntry secretEntry = loginServer.generateSecretEntry(loginInfo.getAuthenticationEntry());
        ClientLoginInfo newClientLoginInfo = new ClientLoginInfo.LoginInfoBuilder().setValues(loginInfo).setAuthenticationEntry(secretEntry).build();
        return newClientLoginInfo;
    }

    @Override
    protected void executeAfterRequest(ClientLoginInfo requestResult, CommandExecutionEnv env, CommandEvent event) {
        getProgressNotifier().notifyProgressChanged(5, ProgressChangeUnit.RELATIVE, "Authenticated", null);
        client.setLoginInfo(requestResult);
        clientUI.getLoginFrame().setEnabledUI(true);
        client.startGetApplicableContextsWorkFlow(requestResult);
    }

    @Override
    protected RequestCommandExceptionExecutionPolicy executeExceptionHandling(Exception e, CommandExecutionEnv env, CommandEvent event) {
        clientUI.getLoginFrame().setEnabledUI(true);
        client.logAndShowExceptionDialog(e, getClass(), "Error while authenticating", false);
        return RequestCommandExceptionExecutionPolicy.ABORT;
    }

}
