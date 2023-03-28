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
import org.vpda.clientserver.communication.data.FullApplicableContexts;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandExceptionExecutionPolicy;
import org.vpda.common.util.ProgressChangeUnit;
import org.vpda.internal.common.util.Assert;

/**
 * Command that will show dialog with all contexts user may choose.
 * 
 * @author kitko
 *
 */
final class GetContextsAndLocaleCommand extends AbstractRequestCommand<FullApplicableContexts> {
    private final ClientLoginInfo loginInfo;
    private final ClientUI clientUI;
    private final ClientImpl client;
    private final LoginServer loginServer;

    /**
     * Creates GetContextsAndLocaleCommand
     * 
     * @param registry
     * @param loginInfo
     * @param client
     * @param clientUI
     * @param loginServer
     */
    public GetContextsAndLocaleCommand(CommandExecutorRegistry registry, ClientLoginInfo loginInfo, ClientImpl client, ClientUI clientUI, LoginServer loginServer) {
        super(registry);
        this.client = client;
        this.clientUI = Assert.isNotNullArgument(clientUI, "clientUI");
        this.loginServer = Assert.isNotNullArgument(loginServer, "loginServer");
        this.loginInfo = loginInfo;
    }

    @Override
    protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
        addProgressObserver(clientUI.getLoginFrame());
        clientUI.getLoginFrame().setEnabledUI(false);
        return true;
    }

    @Override
    protected FullApplicableContexts executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        FullApplicableContexts result = loginServer.getFullApplicableContexts(loginInfo.createLoginInfo());
        return result;
    }

    @Override
    protected void executeAfterRequest(FullApplicableContexts requestResult, CommandExecutionEnv env, CommandEvent event) {
        getProgressNotifier().notifyProgressChanged(10, ProgressChangeUnit.RELATIVE, "Contexts and locale ok", null);
        client.contextsAvailable();
        clientUI.getLoginFrame().setEnabledUI(true);
        clientUI.getLoginFrame().showContextChooserDialog(loginInfo, requestResult);

    }

    @Override
    protected RequestCommandExceptionExecutionPolicy executeExceptionHandling(Exception e, CommandExecutionEnv env, CommandEvent event) {
        clientUI.getLoginFrame().setEnabledUI(true);
        client.logAndShowExceptionDialog(e, getClass(), "Error while getting Appl contexts", false);
        return RequestCommandExceptionExecutionPolicy.ABORT;
    }

}
