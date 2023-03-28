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
import org.vpda.abstractclient.viewprovider.ui.generic.GenericViewProviderOpenInfoDUImpl;
import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderOperation;
import org.vpda.clientserver.viewprovider.generic.GenericViewProvider;
import org.vpda.internal.common.util.Assert;

/**
 * Factory methods that will create {@link ShowViewProviderCommand} to show
 * {@link GenericViewProvider}
 * 
 * @author kitko
 *
 */
public final class ShowDetailProviderCommandFactory {
    private ShowDetailProviderCommandFactory() {
    }

    /**
     * new
     * DetailViewProviderOpenInfoDUImpl(def,ViewProviderOperation.READ,uiInfo,null,initData);
     * 
     * Creates ShowViewProviderCommand for detail
     * 
     * @param viewProviderDef
     * @param operation
     * @param initData
     * @return ShowViewProviderCommand
     */
    public static ShowViewProviderCommand showDetailProvider(ViewProviderDef viewProviderDef, ViewProviderOperation operation, AdjustCurrentData initData) {
        Assert.isNotNullArgument(viewProviderDef, "ViewProviderDef");
        FrameInfoBuilder builder = new FrameInfoBuilder();
        builder.setTitle(viewProviderDef.getCodeTitle()).setResizable(true).setMaximizable(false).setResizable(true).setClosable(false).setIconifiable(false).setModal(true);
        FrameInfo frameInfo = builder.build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.NEW_FRAME, frameInfo);
        ViewProviderOpenInfoDU openInfo = new GenericViewProviderOpenInfoDUImpl.Builder().setAdjustCurrentData(initData).setUiInfo(uiInfo).setOperation(operation).setViewProviderDef(viewProviderDef)
                .build();
        return new ShowViewProviderCommand(openInfo);
    }

    /**
     * Creates command which will show embedded detail
     * 
     * @param def
     * @param initData
     * @return new command which show embedded detail typically showing current row
     *         detail record
     */
    public static ShowViewProviderCommand showSubdetailProvider(ViewProviderDef def, AdjustCurrentData initData) {
        Assert.isNotNullArgument(def, "def");
        FrameInfoBuilder builder = new FrameInfoBuilder();
        builder.setTitle(def.getCodeTitle());
        builder.setResizable(false).setIconifiable(false).setMaximizable(false).setClosable(false).setShouldRegisterToFrameChooser(false);
        FrameInfo frameInfo = builder.build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.USE_PARENT_FRAME, frameInfo);
        ViewProviderOpenInfoDU openInfo = new GenericViewProviderOpenInfoDUImpl.Builder().setViewProviderDef(def).setOperation(ViewProviderOperation.READ).setAdjustCurrentData(initData)
                .setUiInfo(uiInfo).build();
        return new ShowViewProviderCommand(openInfo);
    }

    /**
     * Creates ShowViewProviderCommand for detail
     * 
     * @param viewProviderDef
     * @param operation
     * @return ShowViewProviderCommand
     */
    public static ShowViewProviderCommand showDetailProvider(ViewProviderDef viewProviderDef, ViewProviderOperation operation) {
        return showDetailProvider(viewProviderDef, operation, null);
    }
}
