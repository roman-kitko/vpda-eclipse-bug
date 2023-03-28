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

/** Read data request parameter */
public final class ReadDataStatelessRequest extends BasicStatelessRequest {
    private static final long serialVersionUID = 8603522894929435710L;
    private final ReadDataStatelessRequestContent content;

    private ReadDataStatelessRequest(Builder builder) {
        super(builder);
        this.content = builder.getContent();
    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return content.getInitData();
    }

    /**
     * @return the lastCallerItem
     */
    public ViewProviderCallerItem getLastCallerItem() {
        return content.getLastCallerItem();
    }

    /**
     * @return the listViewSettings
     */
    public ListViewProviderSettings getListViewSettings() {
        return content.getListViewSettings();
    }

    /**
     * @return the count
     */
    public int getCount() {
        return content.getCount();
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return content.getOffset();
    }

    /**
     * @return the readingFrom
     */
    public ReadingFrom getReadingFrom() {
        return content.getReadingFrom();
    }

    /**
     * @return the content
     */
    public ReadDataStatelessRequestContent getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ReadDataStatelessRequest [content=" + content + ", def=" + def + ", providerId=" + providerId + "]";
    }

    /**
     * Builder for ReadDataRequest
     * 
     * @author kitko
     *
     */
    public static final class Builder extends BasicStatelessRequest.Builder<ReadDataStatelessRequest> implements org.vpda.common.util.Builder<ReadDataStatelessRequest> {
        private ReadDataStatelessRequestContent.Builder contentBuilder = new ReadDataStatelessRequestContent.Builder();

        /**
         * @return the readingFrom
         */
        public ReadingFrom getReadingFrom() {
            return contentBuilder.getReadingFrom();
        }

        /**
         * @param readingFrom the readingFrom to set
         * @return this
         */
        public Builder setReadingFrom(ReadingFrom readingFrom) {
            this.contentBuilder.setReadingFrom(readingFrom);
            return this;
        }

        /**
         * @return the initData
         */
        public ViewProviderInitData getInitData() {
            return contentBuilder.getInitData();
        }

        /**
         * @param initData the initData to set
         * @return this
         */
        public Builder setInitData(ViewProviderInitData initData) {
            this.contentBuilder.setInitData(initData);
            return this;
        }

        /**
         * @return the lastCallerItem
         */
        public ViewProviderCallerItem getLastCallerItem() {
            return contentBuilder.getLastCallerItem();
        }

        /**
         * @param lastCallerItem the lastCallerItem to set
         * @return this
         */
        public Builder setLastCallerItem(ViewProviderCallerItem lastCallerItem) {
            this.contentBuilder.setLastCallerItem(lastCallerItem);
            return this;
        }

        /**
         * @return the listViewSettings
         */
        public ListViewProviderSettings getListViewSettings() {
            return contentBuilder.getListViewSettings();
        }

        /**
         * @param listViewSettings the listViewSettings to set
         * @return this
         */
        public Builder setListViewSettings(ListViewProviderSettings listViewSettings) {
            this.contentBuilder.setListViewSettings(listViewSettings);
            return this;
        }

        /**
         * @return the count
         */
        public int getCount() {
            return contentBuilder.getCount();
        }

        /**
         * @param count the count to set
         * @return this
         */
        public Builder setCount(int count) {
            this.contentBuilder.setCount(count);
            return this;
        }

        /**
         * @return the offset
         */
        public int getOffset() {
            return contentBuilder.getOffset();
        }

        /**
         * @param offset the offset to set
         * @return this
         */
        public Builder setOffset(int offset) {
            this.contentBuilder.setOffset(offset);
            return this;
        }

        @Override
        public Class<? extends ReadDataStatelessRequest> getTargetClass() {
            return ReadDataStatelessRequest.class;
        }

        @Override
        public Builder setValues(ReadDataStatelessRequest request) {
            super.setValues(request);
            this.contentBuilder.setValues(request.getContent());
            return this;
        }

        /**
         * @return ReadDataStatelessRequestContent
         */
        public ReadDataStatelessRequestContent getContent() {
            return contentBuilder.build();
        }

        @Override
        public ReadDataStatelessRequest build() {
            return new ReadDataStatelessRequest(this);
        }

    }

}