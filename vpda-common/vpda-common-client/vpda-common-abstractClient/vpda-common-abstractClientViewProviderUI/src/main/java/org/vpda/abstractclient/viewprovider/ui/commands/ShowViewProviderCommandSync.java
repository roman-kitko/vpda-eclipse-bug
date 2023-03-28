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

import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIManager;
import org.vpda.clientserver.viewprovider.ViewProviderContext;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.comps.CurrentData;
import org.vpda.internal.common.util.Assert;

/**
 * Shows view provider command in sync manner.
 * 
 * @author kitko
 *
 */
final class ShowViewProviderCommandSync implements Command<ViewProviderUI>, Serializable {
    private static final long serialVersionUID = -3421701405765468348L;
    private final ViewProviderOpenInfoDU openInfo;

    /**
     * Creates ShowViewProviderCommand
     * 
     * @param openInfo
     */
    ShowViewProviderCommandSync(ViewProviderOpenInfoDU openInfo) {
        this.openInfo = Assert.isNotNullArgument(openInfo, "openInfo");
    }

    @Override
    public ViewProviderUI execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        boolean resetContext = false;
        try {
            if (openInfo.getViewProviderContext() == null) {
                resetContext = true;
                ViewProviderUI callerProviderUI = env.resolveObject(ViewProviderUI.class);
                if (callerProviderUI != null) {
                    ViewProviderContext viewProviderContext = callerProviderUI.getCurrentProviderContext();
                    if (openInfo.getAdjustData() != null) {
                        openInfo.getAdjustData().adjustCurrentData(viewProviderContext.getLastCaller());
                    }
                    CurrentData currentData = viewProviderContext.getLastCaller().getCurrentData();
                    if (currentData == null || currentData.isEmpty()) {
                        if (openInfo.getOperation().needCurrentData()) {
                            return null;
                        }
                    }
                    openInfo.setViewProviderContext(viewProviderContext);
                }
            }
            ViewProviderUIManager viewProviderUIFactory = env.resolveObject(ViewProviderUIManager.class);
            ViewProviderUI contextCaller = env.resolveObject(ViewProviderUI.class);
            ViewProviderUI viewProviderUI = viewProviderUIFactory.createViewProviderUIAndRegister(contextCaller, openInfo);
            viewProviderUI.initialize();
            viewProviderUI.startInitFlow();
            return viewProviderUI;
        }
        finally {
            if (resetContext) {
                openInfo.setViewProviderContext(null);
            }
        }
    }
}
