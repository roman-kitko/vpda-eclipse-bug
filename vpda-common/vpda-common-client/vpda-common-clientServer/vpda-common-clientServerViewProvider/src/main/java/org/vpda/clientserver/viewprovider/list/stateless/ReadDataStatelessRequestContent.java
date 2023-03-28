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

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.list.ListViewProviderSettings;
import org.vpda.clientserver.viewprovider.list.ReadDataRequestContent;
import org.vpda.clientserver.viewprovider.list.ReadingFrom;

/** Read data request parameter */
public final class ReadDataStatelessRequestContent implements Serializable {
    private static final long serialVersionUID = 8603522894929435710L;
    private final ViewProviderInitData initData;
    private final ViewProviderCallerItem lastCallerItem;
    private final ListViewProviderSettings listViewSettings;
    private final int count;
    private final int offset;
    private final ReadingFrom readingFrom;

    private ReadDataStatelessRequestContent(Builder builder) {
        this.initData = builder.getInitData();
        this.lastCallerItem = builder.getLastCallerItem();
        this.listViewSettings = builder.getListViewSettings();
        this.count = builder.getCount();
        this.offset = builder.getOffset();
        this.readingFrom = builder.getReadingFrom();
    }

    /**
     * @return ReadDataRequestContent part
     */
    public ReadDataRequestContent toReadDataRequestContent() {
        ReadDataRequestContent.Builder builder = new ReadDataRequestContent.Builder();
        builder.setCount(count).setLastCallerItem(lastCallerItem).setListViewSettings(listViewSettings).setOffset(offset).setReadingFrom(readingFrom);
        return builder.build();
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

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return the readingFrom
     */
    public ReadingFrom getReadingFrom() {
        return readingFrom;
    }

    @Override
    public String toString() {
        return "ReadDataStatelessRequestContent [initData=" + initData + ", lastCallerItem=" + lastCallerItem + ", listViewSettings=" + listViewSettings + ", count=" + count + ", offset=" + offset
                + ", readingFrom=" + readingFrom + "]";
    }

    /**
     * Builder for ReadDataRequest
     * 
     * @author kitko
     *
     */
    public static final class Builder implements org.vpda.common.util.Builder<ReadDataStatelessRequestContent> {
        private ViewProviderInitData initData;
        private ViewProviderCallerItem lastCallerItem;
        private ListViewProviderSettings listViewSettings;
        private int count;
        private int offset;
        private ReadingFrom readingFrom = ReadingFrom.FROM_START;

        /**
         * @return the readingFrom
         */
        public ReadingFrom getReadingFrom() {
            return readingFrom;
        }

        /**
         * @param readingFrom the readingFrom to set
         * @return this
         */
        public Builder setReadingFrom(ReadingFrom readingFrom) {
            this.readingFrom = readingFrom;
            return this;
        }

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

        /**
         * @return the count
         */
        public int getCount() {
            return count;
        }

        /**
         * @param count the count to set
         * @return this
         */
        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        /**
         * @return the offset
         */
        public int getOffset() {
            return offset;
        }

        /**
         * @param offset the offset to set
         * @return this
         */
        public Builder setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        @Override
        public Class<? extends ReadDataStatelessRequestContent> getTargetClass() {
            return ReadDataStatelessRequestContent.class;
        }

        /**
         * @param request
         * @return this
         */
        public Builder setValues(ReadDataStatelessRequestContent request) {
            this.initData = request.getInitData();
            this.lastCallerItem = request.getLastCallerItem();
            this.listViewSettings = request.getListViewSettings();
            this.count = request.getCount();
            this.offset = request.getOffset();
            this.readingFrom = request.getReadingFrom();
            return this;
        }

        @Override
        public ReadDataStatelessRequestContent build() {
            return new ReadDataStatelessRequestContent(this);
        }

        /**
         * @return ReadDataRequestContent part
         */
        public ReadDataRequestContent toReadDataRequestContent() {
            ReadDataRequestContent.Builder builder = new ReadDataRequestContent.Builder();
            builder.setCount(count).setLastCallerItem(lastCallerItem).setListViewSettings(listViewSettings).setOffset(offset).setReadingFrom(readingFrom);
            return builder.build();
        }

    }

}