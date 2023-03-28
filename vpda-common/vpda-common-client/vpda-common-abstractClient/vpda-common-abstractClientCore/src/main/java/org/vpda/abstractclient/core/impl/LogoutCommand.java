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

import java.util.logging.Level;

import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandExceptionExecutionPolicy;
import org.vpda.common.util.ProgressChangeUnit;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.internal.common.util.Assert;

/**
 * Logout user from server
 * 
 * @author kitko
 *
 */
final class LogoutCommand extends AbstractRequestCommand<CommandExecutor> {
    private final transient ClientServer clientServer;
    private final static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(LogoutCommand.class);
    private final ClientUI clientUI;
    private final ClientImpl client;

    LogoutCommand(CommandExecutorRegistry registry, ClientImpl client, ClientUI clientUI, ClientServer clientServer, UserSession session) {
        super(registry);
        if (clientServer == null) {
            throw new IllegalArgumentException("clientServer argument is null");
        }
        this.clientServer = clientServer;
        if (clientUI == null) {
            throw new IllegalArgumentException("clientUI argument is null");
        }
        this.clientUI = clientUI;
        this.client = Assert.isNotNullArgument(client, "client");
    }

    @Override
    protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
        addProgressObserver(clientUI.getLoginFrame());
        clientUI.getLoginFrame().setEnabledUI(false);
        return true;
    }

    @Override
    protected void executeAfterRequest(CommandExecutor requestResult, CommandExecutionEnv env, CommandEvent event) {
        getProgressNotifier().notifyProgressChanged(10, ProgressChangeUnit.ABSOLUTE, "Connected", null);
        clientUI.getMainFrame().setVisible(false);
        Frame loginFrame = clientUI.getLoginFrame();
        loginFrame.initialize();
        loginFrame.setEnabledUI(true);
        clientUI.afterLogout();
        client.loggedOut();
    }

    @Override
    protected CommandExecutor executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        // Here we will logout from server
        try {
            clientServer.logout();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Error logging out", e);
        }
        return null;
    }

    @Override
    protected RequestCommandExceptionExecutionPolicy executeExceptionHandling(Exception e, CommandExecutionEnv env, CommandEvent event) {
        super.executeExceptionHandling(e, env, event);
        return RequestCommandExceptionExecutionPolicy.CONTINUE;
    }

}
