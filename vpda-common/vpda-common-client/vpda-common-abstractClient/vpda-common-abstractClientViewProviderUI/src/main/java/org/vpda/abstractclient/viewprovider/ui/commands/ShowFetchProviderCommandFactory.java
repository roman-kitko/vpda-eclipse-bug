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

import org.vpda.abstractclient.core.ui.FrameInfo;
import org.vpda.abstractclient.core.ui.FrameInfo.FrameInfoBuilder;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfo;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfoImpl;
import org.vpda.abstractclient.viewprovider.ui.FrameOpenKind;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.fetch.FetchDefDU;
import org.vpda.abstractclient.viewprovider.ui.fetch.FetchDefDUImpl;
import org.vpda.abstractclient.viewprovider.ui.fetch.FetchOpenInfoDUImpl;
import org.vpda.clientserver.viewprovider.fetch.FetchDef;
import org.vpda.internal.common.util.Assert;

/**
 * Factory methods that will create {@link ShowViewProviderCommand} to show
 * Fetch
 * 
 * @author kitko
 *
 */
public final class ShowFetchProviderCommandFactory {
    private ShowFetchProviderCommandFactory() {
    }

    /**
     * Creates command that will open fetch on client
     * 
     * @param fetchDef
     * @param fetchField
     * @return command that will open fetch
     */
    public static ShowViewProviderCommand showFetchProvider(FetchDef fetchDef, String fetchField) {
        Assert.isNotNullArgument(fetchDef, "def");
        FrameInfoBuilder builder = new FrameInfoBuilder();
        builder.setTitle(fetchDef.getOpenInfo().getViewProviderDef().getCodeTitle());
        builder.setResizable(true).setMaximizable(false).setModal(true).setIconifiable(false).setShouldRegisterToFrameChooser(false).setClosable(true);
        FrameInfo frameInfo = builder.build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.NEW_FRAME, frameInfo);
        FetchDefDU fetchDefDU = new FetchDefDUImpl(fetchDef, uiInfo);
        ViewProviderOpenInfoDU openInfo = new FetchOpenInfoDUImpl(fetchDefDU, fetchField);
        return new ShowViewProviderCommand(openInfo);
    }

    /**
     * Creates command that shows fetch with passed ui
     * 
     * @param fetchDef
     * @param fetchField
     * @return command that will open fetch on client with passed ui
     */
    public static ShowViewProviderCommand showFetchProvider(FetchDefDU fetchDef, String fetchField) {
        Assert.isNotNullArgument(fetchDef, "def");
        ViewProviderOpenInfoDU openInfo = new FetchOpenInfoDUImpl(fetchDef, fetchField);
        return new ShowViewProviderCommand(openInfo);
    }

}
