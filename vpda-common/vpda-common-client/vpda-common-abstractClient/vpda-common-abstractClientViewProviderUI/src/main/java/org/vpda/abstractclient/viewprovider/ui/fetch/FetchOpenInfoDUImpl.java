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
package org.vpda.abstractclient.viewprovider.ui.fetch;

import java.io.Serializable;

import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfo;
import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderContext;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderInitProperties;
import org.vpda.clientserver.viewprovider.ViewProviderOperation;
import org.vpda.internal.common.util.Assert;

/**
 * Implementation of {@link FetchOpenInfoDU}
 * 
 * @author kitko
 *
 */
public final class FetchOpenInfoDUImpl implements FetchOpenInfoDU, Serializable {
    private static final long serialVersionUID = 4918432004032966200L;
    private final FetchDefDU fethDef;
    private final String fetchFieldId;

    /**
     * Creates FetchOpenInfoDUImpl
     * 
     * @param fethDef
     * @param fetchFieldId
     */
    public FetchOpenInfoDUImpl(FetchDefDU fethDef, String fetchFieldId) {
        this.fethDef = Assert.isNotNullArgument(fethDef, "fethDef");
        this.fetchFieldId = Assert.isNotEmptyArgument(fetchFieldId, "fetchFieldId");
    }

    @Override
    public FetchDefDU getFetchDef() {
        return fethDef;
    }

    @Override
    public String getFetchField() {
        return fetchFieldId;
    }

    @Override
    public AdjustCurrentData getAdjustData() {
        return fethDef.getOpenInfo().getAdjustData();
    }

    @Override
    public ViewProviderOperation getOperation() {
        return fethDef.getOpenInfo().getOperation();
    }

    @Override
    public ViewProviderContext getViewProviderContext() {
        return fethDef.getOpenInfo().getViewProviderContext();
    }

    @Override
    public ViewProviderDef getViewProviderDef() {
        return fethDef.getOpenInfo().getViewProviderDef();
    }

    @Override
    public DefaultViewProviderUIInfo getViewProviderUIInfo() {
        return fethDef.getOpenInfo().getViewProviderUIInfo();
    }

    @Override
    public void setViewProviderContext(ViewProviderContext viewProviderContext) {
        fethDef.getOpenInfo().setViewProviderContext(viewProviderContext);
    }

    @Override
    public ViewProviderInitProperties getInitProperties() {
        return fethDef.getOpenInfo().getInitProperties();
    }

}
