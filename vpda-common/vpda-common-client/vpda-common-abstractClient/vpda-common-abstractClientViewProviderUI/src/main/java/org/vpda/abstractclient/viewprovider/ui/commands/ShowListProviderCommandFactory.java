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
/**
 * 
 */
package org.vpda.abstractclient.viewprovider.ui.commands;

import org.vpda.abstractclient.core.ui.FrameInfo;
import org.vpda.abstractclient.core.ui.FrameInfo.FrameInfoBuilder;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfo;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfoImpl;
import org.vpda.abstractclient.viewprovider.ui.FrameOpenKind;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderOpenInfoDUImpl;
import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderOperation;
import org.vpda.clientserver.viewprovider.list.ListViewProvider;
import org.vpda.internal.common.util.Assert;

/**
 * Factory methods that will create {@link ShowViewProviderCommand} to show
 * {@link ListViewProvider}
 * 
 * @author kitko
 *
 */
public final class ShowListProviderCommandFactory {
    private ShowListProviderCommandFactory() {
    }

    /**
     * Creates ShowViewProviderCommand for list
     * 
     * @param viewProviderDef
     * @param initData
     * @return ShowViewProviderCommand
     */
    public static ShowViewProviderCommand showListProvider(ViewProviderDef viewProviderDef, AdjustCurrentData initData) {
        Assert.isNotNullArgument(viewProviderDef, "ViewProviderDef");
        FrameInfoBuilder builder = new FrameInfoBuilder();
        builder.setTitle(viewProviderDef.getCodeTitle());
        builder.setMaximizable(true).setIconifiable(true).setResizable(true).setShouldRegisterToFrameChooser(true).setClosable(true);
        FrameInfo frameInfo = builder.build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.NEW_FRAME, frameInfo);
        ViewProviderOpenInfoDU openInfo = new ListViewProviderOpenInfoDUImpl.Builder().setViewProviderDef(viewProviderDef).setOperation(ViewProviderOperation.READ).setUiInfo(uiInfo)
                .setAdjustCurrentData(initData).build();
        return new ShowViewProviderCommand(openInfo);
    }

    /**
     * Creates command that will show list in new modal window
     * 
     * @param viewProviderDef
     * @param initData
     * @return ShowViewProviderCommand
     */
    public static ShowViewProviderCommand showModalListProvider(ViewProviderDef viewProviderDef, AdjustCurrentData initData) {
        Assert.isNotNullArgument(viewProviderDef, "ViewProviderDef");
        FrameInfoBuilder builder = new FrameInfoBuilder();
        builder.setTitle(viewProviderDef.getCodeTitle());
        builder.setMaximizable(false).setIconifiable(false).setResizable(true).setShouldRegisterToFrameChooser(false).setClosable(true).setModal(true);
        FrameInfo frameInfo = builder.build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.NEW_FRAME, frameInfo);
        ViewProviderOpenInfoDU openInfo = new ListViewProviderOpenInfoDUImpl.Builder().setViewProviderDef(viewProviderDef).setOperation(ViewProviderOperation.READ).setUiInfo(uiInfo)
                .setAdjustCurrentData(initData).build();
        return new ShowViewProviderCommand(openInfo);
    }

    /**
     * Creates ShowViewProviderCommand for list that will use parent frame window
     * 
     * @param viewProviderDef
     * @param initData
     * @return ShowViewProviderCommand
     */
    public static ShowViewProviderCommand showListInParentFrameProvider(ViewProviderDef viewProviderDef, AdjustCurrentData initData) {
        Assert.isNotNullArgument(viewProviderDef, "ViewProviderDef");
        FrameInfoBuilder builder = new FrameInfoBuilder();
        builder.setTitle(viewProviderDef.getCodeTitle());
        builder.setMaximizable(false).setIconifiable(false).setResizable(true).setShouldRegisterToFrameChooser(false).setClosable(false);
        FrameInfo frameInfo = builder.build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.USE_PARENT_FRAME, frameInfo);
        ViewProviderOpenInfoDU openInfo = new ListViewProviderOpenInfoDUImpl.Builder().setViewProviderDef(viewProviderDef).setOperation(ViewProviderOperation.READ).setUiInfo(uiInfo)
                .setAdjustCurrentData(initData).build();
        return new ShowViewProviderCommand(openInfo);
    }

    /**
     * Creates ShowViewProviderCommand for sublist
     * 
     * @param viewProviderDef
     * @param initData
     * @return ShowViewProviderCommand
     */
    public static ShowViewProviderCommand showSubListProvider(ViewProviderDef viewProviderDef, AdjustCurrentData initData) {
        Assert.isNotNullArgument(viewProviderDef, "ViewProviderDef");
        FrameInfoBuilder builder = new FrameInfoBuilder();
        builder.setTitle(viewProviderDef.getCodeTitle());
        builder.setIconifiable(false).setResizable(true).setMaximizable(false).setClosable(false).setShouldRegisterToFrameChooser(false);
        FrameInfo frameInfo = builder.build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.USE_PARENT_FRAME, frameInfo);
        ViewProviderOpenInfoDU openInfo = new ListViewProviderOpenInfoDUImpl.Builder().setAdjustCurrentData(initData).setViewProviderDef(viewProviderDef).setOperation(ViewProviderOperation.READ)
                .setUiInfo(uiInfo).build();
        return new ShowViewProviderCommand(openInfo);
    }

    /**
     * Creates ShowViewProviderCommand for detail
     * 
     * @param viewProviderDef
     * @return ShowViewProviderCommand
     */
    public static ShowViewProviderCommand showListProvider(ViewProviderDef viewProviderDef) {
        return showListProvider(viewProviderDef, null);
    }
}
