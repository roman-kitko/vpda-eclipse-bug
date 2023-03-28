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
import org.vpda.clientserver.viewprovider.list.ReadingFrom;
import org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest;

/** Get results info param */
public final class GetResultsInfoAndReadDataStatelessRequest extends BasicStatelessRequest {
    private static final long serialVersionUID = 8603522894929435710L;

    private final ReadDataStatelessRequestContent readDataContent;

    private GetResultsInfoAndReadDataStatelessRequest(Builder builder) {
        super(builder);
        this.readDataContent = builder.getReadDataContent();
    }

    /**
     * @return readDataContent
     */
    public ReadDataStatelessRequestContent getReadDataContent() {
        return readDataContent;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return readDataContent.getCount();
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return readDataContent.getOffset();
    }

    /**
     * @return the readingFrom
     */
    public ReadingFrom getReadingFrom() {
        return readDataContent.getReadingFrom();
    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return readDataContent.getInitData();
    }

    /**
     * @return the lastCallerItem
     */
    public ViewProviderCallerItem getLastCallerItem() {
        return readDataContent.getLastCallerItem();
    }

    /**
     * @return the listViewSettings
     */
    public ListViewProviderSettings getListViewSettings() {
        return readDataContent.getListViewSettings();
    }

    @Override
    public String toString() {
        return "GetResultsInfoAndReadDataStatelessRequest [readDataContent=" + readDataContent + ", def=" + def + ", providerId=" + providerId + "]";
    }

    /**
     * Builder for ReadDataRequest
     * 
     * @author kitko
     *
     */
    public static final class Builder extends BasicStatelessRequest.Builder<GetResultsInfoAndReadDataStatelessRequest>
            implements org.vpda.common.util.Builder<GetResultsInfoAndReadDataStatelessRequest> {

        private ReadDataStatelessRequestContent.Builder readDataContentBuilder = new ReadDataStatelessRequestContent.Builder();

        /**
         * @return the readingFrom
         */
        public ReadingFrom getReadingFrom() {
            return readDataContentBuilder.getReadingFrom();
        }

        /**
         * @param readingFrom the readingFrom to set
         * @return this
         */
        public Builder setReadingFrom(ReadingFrom readingFrom) {
            this.readDataContentBuilder.setReadingFrom(readingFrom);
            return this;
        }

        /**
         * @return the count
         */
        public int getCount() {
            return readDataContentBuilder.getCount();
        }

        /**
         * @param count the count to set
         * @return this
         */
        public Builder setCount(int count) {
            this.readDataContentBuilder.setCount(count);
            return this;
        }

        /**
         * @return the offset
         */
        public int getOffset() {
            return readDataContentBuilder.getOffset();
        }

        /**
         * @param offset the offset to set
         * @return this
         */
        public Builder setOffset(int offset) {
            this.readDataContentBuilder.setOffset(offset);
            return this;
        }

        /**
         * @return the initData
         */
        public ViewProviderInitData getInitData() {
            return readDataContentBuilder.getInitData();
        }

        /**
         * @param initData the initData to set
         * @return this
         */
        public Builder setInitData(ViewProviderInitData initData) {
            this.readDataContentBuilder.setInitData(initData);
            return this;
        }

        /**
         * @return the lastCallerItem
         */
        public ViewProviderCallerItem getLastCallerItem() {
            return readDataContentBuilder.getLastCallerItem();
        }

        /**
         * @param lastCallerItem the lastCallerItem to set
         * @return this
         */
        public Builder setLastCallerItem(ViewProviderCallerItem lastCallerItem) {
            this.readDataContentBuilder.setLastCallerItem(lastCallerItem);
            return this;
        }

        /**
         * @return the listViewSettings
         */
        public ListViewProviderSettings getListViewSettings() {
            return readDataContentBuilder.getListViewSettings();
        }

        /**
         * @param listViewSettings the listViewSettings to set
         * @return this
         */
        public Builder setListViewSettings(ListViewProviderSettings listViewSettings) {
            this.readDataContentBuilder.setListViewSettings(listViewSettings);
            return this;
        }

        @Override
        public Class<? extends GetResultsInfoAndReadDataStatelessRequest> getTargetClass() {
            return GetResultsInfoAndReadDataStatelessRequest.class;
        }

        @Override
        public Builder setValues(GetResultsInfoAndReadDataStatelessRequest request) {
            super.setValues(request);
            this.readDataContentBuilder.setValues(request.getReadDataContent());
            return this;
        }

        /**
         * @return ReadDataStatelessRequestContent
         */
        public ReadDataStatelessRequestContent getReadDataContent() {
            return readDataContentBuilder.build();
        }

        /**
         * @param readRequestcontent the readDataRequest to set
         * @return this
         */
        public Builder setReadDataRequestContent(ReadDataStatelessRequestContent readRequestcontent) {
            this.readDataContentBuilder.setValues(readRequestcontent);
            return this;
        }

        @Override
        public GetResultsInfoAndReadDataStatelessRequest build() {
            return new GetResultsInfoAndReadDataStatelessRequest(this);
        }

    }

}