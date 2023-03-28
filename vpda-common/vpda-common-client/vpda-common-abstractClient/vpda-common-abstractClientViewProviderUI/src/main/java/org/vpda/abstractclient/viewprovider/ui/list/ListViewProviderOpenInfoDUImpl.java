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
package org.vpda.abstractclient.viewprovider.ui.list;

import org.vpda.abstractclient.core.ui.FrameInfo;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfo;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfoImpl;
import org.vpda.abstractclient.viewprovider.ui.FrameOpenKind;
import org.vpda.abstractclient.viewprovider.ui.impl.AbstractViewProviderOpenInfoDU;
import org.vpda.clientserver.viewprovider.AbstractViewProviderOpenInfo;
import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderOperation;
import org.vpda.clientserver.viewprovider.list.ListViewProviderOpenInfo;

/**
 * Default implementation of {@link ListViewProviderOpenInfo}
 * 
 * @author kitko
 *
 */
public final class ListViewProviderOpenInfoDUImpl extends AbstractViewProviderOpenInfoDU implements ListViewProviderOpenInfoDU {

    private static final long serialVersionUID = 1915743650061915202L;

    /**
     * Creates ListViewProviderOpenInfoDUImpl using the builder
     * 
     * @param builder
     */
    private ListViewProviderOpenInfoDUImpl(Builder builder) {
        super(builder);
    }

    /**
     * Creates new ListViewProviderOpenInfo that will open in new frame
     * 
     * @param def
     * @return new ListViewProviderOpenInfo
     */
    public static ListViewProviderOpenInfoDU newFrameOpenInfo(ViewProviderDef def) {
        return newFrameOpenInfo(def, null);
    }

    /**
     * Creates new ListViewProviderOpenInfo that will open in new frame with passed
     * AdjustCurrentData
     * 
     * @param def
     * @param adjustCurrentData
     * @return ListViewProviderOpenInfoDU
     */
    public static ListViewProviderOpenInfoDU newFrameOpenInfo(ViewProviderDef def, AdjustCurrentData adjustCurrentData) {
        FrameInfo frameInfo = new FrameInfo.FrameInfoBuilder().setTitle(def.getCodeTitle()).setShouldRegisterToFrameChooser(true).build();
        DefaultViewProviderUIInfo uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.NEW_FRAME, frameInfo);
        return new ListViewProviderOpenInfoDUImpl.Builder().setViewProviderDef(def).setOperation(ViewProviderOperation.READ).setUiInfo(uiInfo).setAdjustCurrentData(adjustCurrentData).build();
    }

    /**
     * Builder for {@link ListViewProviderOpenInfoDUImpl}
     * 
     * @author kitko
     *
     */
    public static class Builder extends AbstractViewProviderOpenInfo.Builder<ListViewProviderOpenInfoDUImpl> {

        @Override
        public ListViewProviderOpenInfoDUImpl build() {
            return new ListViewProviderOpenInfoDUImpl(this);
        }

        @Override
        public Class<? extends ListViewProviderOpenInfoDUImpl> getTargetClass() {
            return ListViewProviderOpenInfoDUImpl.class;
        }

    }

}
