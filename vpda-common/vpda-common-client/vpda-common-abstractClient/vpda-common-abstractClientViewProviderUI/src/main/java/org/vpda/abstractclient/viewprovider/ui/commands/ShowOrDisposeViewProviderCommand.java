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

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.comps.ui.ComponentCommandEvent;
import org.vpda.common.comps.ui.ToggleButtonAC;
import org.vpda.internal.common.util.Assert;

/**
 * Command that will show or dispose provider. It will check state of
 * {@link ToggleButtonAC} and if button is selected, it will show view provider,
 * else dispose it.
 * 
 * @author kitko
 *
 */
public final class ShowOrDisposeViewProviderCommand implements Command<Object>, Serializable {
    private static final long serialVersionUID = -8642319944933364910L;
    private final ShowViewProviderCommand showCommand;
    private ExecutionResult<ViewProviderUI> childUI;

    @Override
    public Object execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        if (!(event instanceof ComponentCommandEvent)) {
            throw new IllegalArgumentException("event is not ProviderComponentCommandEvent");
        }
        ComponentCommandEvent providerComponentCommandEvent = (ComponentCommandEvent) event;
        ToggleButtonAC toggleButtonViewProviderComponent = (ToggleButtonAC) providerComponentCommandEvent.getSource().getComp();
        if (toggleButtonViewProviderComponent.isSelected()) {
            ExecutionResult<ExecutionResult<ViewProviderUI>> executeCommandRes = executor.executeCommand(showCommand, env, event);
            if (executeCommandRes != null && executeCommandRes.get() != null) {
                childUI = executeCommandRes.get();
            }
            return executeCommandRes;
        }
        else {
            // Find child by childId
            ViewProviderUI childUI = this.childUI.get();
            if (childUI != null) {
                childUI.dispose();
            }
            this.childUI = null;
            return null;
        }
    }

    /**
     * Creates ShowOrDisposeSublistViewProviderCommand
     * 
     * @param showCommand
     */
    public ShowOrDisposeViewProviderCommand(ShowViewProviderCommand showCommand) {
        super();
        this.showCommand = Assert.isNotNullArgument(showCommand, "showCommand");
    }

}
