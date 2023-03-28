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
package org.vpda.clientserver.viewprovider.list;

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;

/** Read data request parameter */
public final class ReadDataRequestContent implements Serializable {
    private static final long serialVersionUID = 8603522894929435710L;
    private final ViewProviderCallerItem lastCallerItem;
    private final ListViewProviderSettings listViewSettings;
    private final int count;
    private final int offset;
    private final ReadingFrom readingFrom;

    private ReadDataRequestContent(Builder builder) {
        this.lastCallerItem = builder.getLastCallerItem();
        this.listViewSettings = builder.getListViewSettings();
        this.count = builder.getCount();
        this.offset = builder.getOffset();
        this.readingFrom = builder.getReadingFrom();
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
        return "ReadDataRequestContent [lastCallerItem=" + lastCallerItem + ", listViewSettings=" + listViewSettings + ", count=" + count + ", offset=" + offset + ", readingFrom=" + readingFrom + "]";
    }

    /**
     * Builder for ReadDataRequest
     * 
     * @author kitko
     *
     */
    public static final class Builder implements org.vpda.common.util.Builder<ReadDataRequestContent> {
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
        public Class<? extends ReadDataRequestContent> getTargetClass() {
            return ReadDataRequestContent.class;
        }

        /**
         * @param request
         * @return this
         */
        public Builder setValues(ReadDataRequestContent request) {
            this.lastCallerItem = request.getLastCallerItem();
            this.listViewSettings = request.getListViewSettings();
            this.count = request.getCount();
            this.offset = request.getOffset();
            this.readingFrom = request.getReadingFrom();
            return this;
        }

        @Override
        public ReadDataRequestContent build() {
            return new ReadDataRequestContent(this);
        }

    }

}