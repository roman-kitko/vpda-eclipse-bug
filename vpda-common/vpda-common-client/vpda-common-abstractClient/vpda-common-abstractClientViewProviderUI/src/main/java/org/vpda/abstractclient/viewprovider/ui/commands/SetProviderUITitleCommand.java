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

/**
 * This command will set new title to provider UI frame
 * 
 * @author kitko
 *
 */
public final class SetProviderUITitleCommand implements Command<Object>, Serializable {
    private static final long serialVersionUID = -4066249310798749152L;
    private final String title;

    /**
     * @param title
     */
    public SetProviderUITitleCommand(String title) {
        this.title = title;
    }

    @Override
    public Object execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        ViewProviderUI viewProviderUI = env.resolveObject(ViewProviderUI.class);
        viewProviderUI.getFrame().setTitle(title);
        return null;
    }

}
