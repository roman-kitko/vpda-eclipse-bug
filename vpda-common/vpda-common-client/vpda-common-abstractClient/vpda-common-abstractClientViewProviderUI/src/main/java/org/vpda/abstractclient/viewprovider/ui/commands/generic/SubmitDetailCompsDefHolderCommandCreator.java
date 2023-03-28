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
package org.vpda.abstractclient.viewprovider.ui.commands.generic;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.commands.ViewProviderUICommandCreator;
import org.vpda.abstractclient.viewprovider.ui.generic.GenericViewProviderUI;
import org.vpda.common.command.Command;
import org.vpda.common.command.call.CommandCall;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;

/**
 * Creator of command created at ui side and executed on provider side that will
 * submit detail data.
 * 
 * @author kitko
 *
 */
public final class SubmitDetailCompsDefHolderCommandCreator implements ViewProviderUICommandCreator {

    private static final long serialVersionUID = 6636935866165647773L;
    private static SubmitDetailCompsDefHolderCommandCreator instance;

    @Override
    public Command createCommand(ViewProviderUI providerUI) {
        GenericViewProviderUI detailViewProviderUI = (GenericViewProviderUI) providerUI;
        ComponentsGroupsDef def = detailViewProviderUI.getCurrentData();
        return new CommandCall(new SubmitDetailCall(def));
    }

    /**
     * @return instance of instance
     */
    public synchronized static SubmitDetailCompsDefHolderCommandCreator getInstance() {
        if (instance == null) {
            instance = new SubmitDetailCompsDefHolderCommandCreator();
        }
        return instance;
    }

}
