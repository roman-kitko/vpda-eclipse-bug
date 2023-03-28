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
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderOpenInfoDUImpl;
import org.vpda.clientserver.viewprovider.fetch.FetchDef;
import org.vpda.clientserver.viewprovider.list.ListViewProviderOpenInfo;

/**
 * Implementation of {@link FetchDefDU}
 * 
 * @author kitko
 *
 */
public final class FetchDefDUImpl implements FetchDefDU, Serializable {
    private static final long serialVersionUID = -4100480839792368231L;
    private final ListViewProviderOpenInfoDU openInfo;
    private final String fetchId;

    @Override
    public ListViewProviderOpenInfoDU getOpenInfo() {
        return openInfo;
    }

    @Override
    public String getFetchId() {
        return fetchId;
    }

    /**
     * Creates FetchDefDUImpl
     * 
     * @param def
     * @param uiInfo
     */
    public FetchDefDUImpl(FetchDef def, DefaultViewProviderUIInfo uiInfo) {
        this.fetchId = def.getFetchId();
        ListViewProviderOpenInfo oi = def.getOpenInfo();
        this.openInfo = new ListViewProviderOpenInfoDUImpl.Builder().setValues(oi).setUiInfo(uiInfo).build();
    }

}
