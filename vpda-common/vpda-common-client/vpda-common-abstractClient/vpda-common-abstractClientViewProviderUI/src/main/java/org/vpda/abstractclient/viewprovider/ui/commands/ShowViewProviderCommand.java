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
package org.vpda.abstractclient.viewprovider.ui.commands;

import java.io.Serializable;

import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorWithRegistry;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.executor.impl.ExecutionResultBase;
import org.vpda.internal.common.util.Assert;

/**
 * Shows view provider command.
 * 
 * @author kitko
 *
 */
public final class ShowViewProviderCommand implements Command<ExecutionResult<ViewProviderUI>>, Serializable {
    private static final long serialVersionUID = -3421701405765468348L;
    private final ViewProviderOpenInfoDU openInfo;
    private final boolean async;

    /**
     * Creates ShowViewProviderCommand
     * 
     * @param openInfo
     * @param async
     */
    public ShowViewProviderCommand(ViewProviderOpenInfoDU openInfo, boolean async) {
        this.openInfo = Assert.isNotNullArgument(openInfo, "openInfo");
        this.async = async;
    }

    /**
     * Creates ShowViewProviderCommand
     * 
     * @param openInfo
     */
    public ShowViewProviderCommand(ViewProviderOpenInfoDU openInfo) {
        this(openInfo, true);
    }

    @Override
    public ExecutionResult<ViewProviderUI> execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        if (async) {
            ClientUI clientUI = env.resolveObject(ClientUI.class);
            CommandExecutorWithRegistry requestCommandExecutor = clientUI.getClient().getRequestCommandExecutor();
            ShowViewProviderCommandAsync asyncCommand = new ShowViewProviderCommandAsync(requestCommandExecutor, openInfo);
            ExecutionResult<ViewProviderUI> er = requestCommandExecutor.executeCommand(asyncCommand, env, event);
            return er;
        }
        else {
            ViewProviderUI ui = executor.executeCommand(new ShowViewProviderCommandSync(openInfo), env, event).get();
            return new ExecutionResultBase<ViewProviderUI>(ui);
        }
    }

    /**
     * @return the openInfo
     */
    public ViewProviderOpenInfoDU getOpenInfo() {
        return openInfo;
    }

    /**
     * @return ViewProviderDef
     */
    public ViewProviderDef getViewProviderDef() {
        return openInfo.getViewProviderDef();
    }

    /**
     * @return the async
     */
    public boolean isAsync() {
        return async;
    }

}
