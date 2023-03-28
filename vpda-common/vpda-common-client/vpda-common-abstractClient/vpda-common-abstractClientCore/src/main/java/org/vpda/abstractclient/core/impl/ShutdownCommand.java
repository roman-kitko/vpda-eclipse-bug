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
import java.util.logging.Logger;

import org.vpda.abstractclient.core.Client;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandExceptionExecutionPolicy;
import org.vpda.common.util.logging.LoggerMethodTracer;

/**
 * Shutdown client
 * 
 * @author kitko
 *
 */
final class ShutdownCommand extends AbstractRequestCommand<CommandExecutor> {
    private final Client client;
    private final ClientUI clientUI;
    private final static Logger logger = LoggerMethodTracer.getLogger(ShutdownCommand.class);

    /**
     * Creates ShutdownCommand
     * 
     * @param registry
     * @param client
     * @param clientUI
     */
    public ShutdownCommand(CommandExecutorRegistry registry, Client client, ClientUI clientUI) {
        super(registry);
        this.client = client;
        if (clientUI == null) {
            throw new IllegalArgumentException("clientui argument is null");
        }
        this.clientUI = clientUI;
    }

    @Override
    protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
        clientUI.getLoginFrame().setEnabledUI(false);
        clientUI.getMainFrame().setEnabledUI(false);
        return true;
    }

    @Override
    protected void executeAfterRequest(CommandExecutor requestResult, CommandExecutionEnv env, CommandEvent event) {
        try {
            clientUI.dispose();
        }
        finally {
            client.exitVM(0);
        }
    }

    @Override
    protected CommandExecutor executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        // Here we will logout from server
        try {
            client.getClientServer().logout();
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
