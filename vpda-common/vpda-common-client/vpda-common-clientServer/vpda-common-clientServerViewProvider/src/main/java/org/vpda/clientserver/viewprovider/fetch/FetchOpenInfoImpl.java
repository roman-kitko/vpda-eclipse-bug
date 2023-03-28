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
package org.vpda.clientserver.viewprovider.fetch;

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderContext;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderInitProperties;
import org.vpda.clientserver.viewprovider.ViewProviderOperation;
import org.vpda.clientserver.viewprovider.ViewProviderUIInfo;
import org.vpda.internal.common.util.Assert;

/**
 * Default implementation of {@link FetchOpenInfo}
 * 
 * @author kitko
 *
 */
public final class FetchOpenInfoImpl implements FetchOpenInfo, Serializable {
    private static final long serialVersionUID = 8010001871767720624L;
    private final FetchDef fetchDef;
    private final String fetchField;

    @Override
    public FetchDef getFetchDef() {
        return fetchDef;
    }

    @Override
    public String getFetchField() {
        return fetchField;
    }

    @Override
    public AdjustCurrentData getAdjustData() {
        return fetchDef.getOpenInfo().getAdjustData();
    }

    @Override
    public ViewProviderOperation getOperation() {
        return fetchDef.getOpenInfo().getOperation();
    }

    @Override
    public ViewProviderContext getViewProviderContext() {
        return fetchDef.getOpenInfo().getViewProviderContext();
    }

    @Override
    public ViewProviderDef getViewProviderDef() {
        return fetchDef.getOpenInfo().getViewProviderDef();
    }

    @Override
    public ViewProviderUIInfo getViewProviderUIInfo() {
        return fetchDef.getOpenInfo().getViewProviderUIInfo();
    }

    @Override
    public void setViewProviderContext(ViewProviderContext viewProviderContext) {
        fetchDef.getOpenInfo().setViewProviderContext(viewProviderContext);
    }

    /**
     * @param fetchDef
     * @param fetchField
     */
    public FetchOpenInfoImpl(FetchDef fetchDef, String fetchField) {
        super();
        this.fetchDef = Assert.isNotNullArgument(fetchDef, "fetchDef");
        this.fetchField = Assert.isNotEmptyArgument(fetchField, "fetchField");
    }

    @Override
    public ViewProviderInitProperties getInitProperties() {
        return fetchDef.getOpenInfo().getInitProperties();
    }

}
