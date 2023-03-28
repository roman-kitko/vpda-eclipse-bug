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
package org.vpda.abstractclient.viewprovider.ui.impl;

import java.io.Serializable;

import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfo;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfoImpl;
import org.vpda.abstractclient.viewprovider.ui.FrameOpenKind;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderContext;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderInitProperties;
import org.vpda.clientserver.viewprovider.ViewProviderOpenInfo;
import org.vpda.clientserver.viewprovider.ViewProviderOperation;
import org.vpda.internal.common.util.Assert;

/**
 * OpenInfo when UI is embedded like component
 * 
 * @author kitko
 *
 */
public final class EmbeddedViewProviderOpenInfoDU implements ViewProviderOpenInfoDU, Serializable {
    private static final long serialVersionUID = 4539765374557569228L;
    private final ViewProviderOpenInfo openInfo;
    private final DefaultViewProviderUIInfo uiInfo;

    /**
     * Creates EmbeddedViewProviderOpenInfoDU
     * 
     * @param openInfo
     * @param uiInfo
     */
    public EmbeddedViewProviderOpenInfoDU(ViewProviderOpenInfo openInfo, DefaultViewProviderUIInfo uiInfo) {
        super();
        this.openInfo = Assert.isNotNullArgument(openInfo, "openInfo");
        this.uiInfo = Assert.isNotNullArgument(uiInfo, "uiInfo");
    }

    /**
     * Creates EmbeddedViewProviderOpenInfoDU with
     * {@link FrameOpenKind#USE_PARENT_FRAME} uiInfo
     * 
     * @param openInfo
     */
    public EmbeddedViewProviderOpenInfoDU(ViewProviderOpenInfo openInfo) {
        this.openInfo = Assert.isNotNullArgument(openInfo, "openInfo");
        this.uiInfo = new DefaultViewProviderUIInfoImpl(FrameOpenKind.USE_PARENT_FRAME, null);
    }

    @Override
    public DefaultViewProviderUIInfo getViewProviderUIInfo() {
        return uiInfo;
    }

    @Override
    public AdjustCurrentData getAdjustData() {
        return openInfo.getAdjustData();
    }

    @Override
    public ViewProviderOperation getOperation() {
        return openInfo.getOperation();
    }

    @Override
    public ViewProviderContext getViewProviderContext() {
        return openInfo.getViewProviderContext();
    }

    @Override
    public ViewProviderDef getViewProviderDef() {
        return openInfo.getViewProviderDef();
    }

    @Override
    public void setViewProviderContext(ViewProviderContext viewProviderContext) {
        openInfo.setViewProviderContext(viewProviderContext);
    }

    @Override
    public ViewProviderInitProperties getInitProperties() {
        return openInfo.getInitProperties();
    }

}
