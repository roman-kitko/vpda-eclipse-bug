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
package org.vpda.clientserver.viewprovider.list.stateless;

import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.list.ListViewProviderSettings;
import org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest;

/** Get results info param */
public final class GetResultsInfoDataStatelessRequest extends BasicStatelessRequest {
    private static final long serialVersionUID = 8603522894929435710L;
    private final ViewProviderInitData initData;
    private final ViewProviderCallerItem lastCallerItem;
    private final ListViewProviderSettings listViewSettings;

    private GetResultsInfoDataStatelessRequest(Builder builder) {
        super(builder);
        this.initData = builder.getInitData();
        this.lastCallerItem = builder.getLastCallerItem();
        this.listViewSettings = builder.getListViewSettings();
    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return initData;
    }

    /**
     * @return the lastCallerItem
     */
    public ViewProviderCallerItem getLastCallerItem() {
        return lastCallerItem;
    }

    /**
     * @return the listViewSettings
     */
    public ListViewProviderSettings getListViewSettings() {
        return listViewSettings;
    }

    @Override
    public String toString() {
        return "GetResultsInfoDataStatelessRequest [initData=" + initData + ", lastCallerItem=" + lastCallerItem + ", listViewSettings=" + listViewSettings + ", def=" + def + ", providerId="
                + providerId + "]";
    }

    /**
     * Builder for ReadDataRequest
     * 
     * @author kitko
     *
     */
    public static final class Builder extends BasicStatelessRequest.Builder<GetResultsInfoDataStatelessRequest> implements org.vpda.common.util.Builder<GetResultsInfoDataStatelessRequest> {
        private ViewProviderInitData initData;
        private ViewProviderCallerItem lastCallerItem;
        private ListViewProviderSettings listViewSettings;

        /**
         * @return the initData
         */
        public ViewProviderInitData getInitData() {
            return initData;
        }

        /**
         * @param initData the initData to set
         * @return this
         */
        public Builder setInitData(ViewProviderInitData initData) {
            this.initData = initData;
            return this;
        }

        /**
         * @return the lastCallerItem
         */
        public ViewProviderCallerItem getLastCallerItem() {
            return lastCallerItem;
        }

        /**
         * @param lastCallerItem the lastCallerItem to set
         * @return this
         */
        public Builder setLastCallerItem(ViewProviderCallerItem lastCallerItem) {
            this.lastCallerItem = lastCallerItem;
            return this;
        }

        /**
         * @return the listViewSettings
         */
        public ListViewProviderSettings getListViewSettings() {
            return listViewSettings;
        }

        /**
         * @param listViewSettings the listViewSettings to set
         * @return this
         */
        public Builder setListViewSettings(ListViewProviderSettings listViewSettings) {
            this.listViewSettings = listViewSettings;
            return this;
        }

        @Override
        public Class<? extends GetResultsInfoDataStatelessRequest> getTargetClass() {
            return GetResultsInfoDataStatelessRequest.class;
        }

        @Override
        public Builder setValues(GetResultsInfoDataStatelessRequest request) {
            super.setValues(request);
            this.initData = request.getInitData();
            this.lastCallerItem = request.getLastCallerItem();
            this.listViewSettings = request.getListViewSettings();
            return this;
        }

        @Override
        public GetResultsInfoDataStatelessRequest build() {
            return new GetResultsInfoDataStatelessRequest(this);
        }

    }

}