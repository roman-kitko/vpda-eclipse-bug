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
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.internal.common.util.Assert;

/**
 * Initial data retrieval request
 * 
 * @author kitko
 *
 */
public final class ListViewProviderInitDataRequest implements Serializable {
    private static final long serialVersionUID = -5519888560309792809L;
    private final ViewProviderInitData initData;
    private final ReadDataRequestContent readDataRequestContent;

    /**
     * @param initData
     * @param readDataRequestContent
     */
    public ListViewProviderInitDataRequest(ViewProviderInitData initData, ReadDataRequestContent readDataRequestContent) {
        super();
        this.initData = Assert.isNotNullArgument(initData, "initData");
        this.readDataRequestContent = Assert.isNotNullArgument(readDataRequestContent, "readDataRequestContent");
    }

    /**
     * @return the readDataRequestContent
     */
    public ReadDataRequestContent getReadDataRequestContent() {
        return readDataRequestContent;
    }

    /**
     * @return the lastCallerItem
     */
    public ViewProviderCallerItem getLastCallerItem() {
        return readDataRequestContent.getLastCallerItem();
    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return initData;
    }

    /**
     * @return the initSettings
     */
    public ListViewProviderSettings getListViewSettings() {
        return readDataRequestContent.getListViewSettings();
    }

    /**
     * @return the count
     */
    public int getCount() {
        return readDataRequestContent.getCount();
    }

    /**
     * @return offset
     */
    public int getOffset() {
        return readDataRequestContent.getOffset();
    }

    /**
     * @return the readingFrom
     */
    public ReadingFrom getReadingFrom() {
        return readDataRequestContent.getReadingFrom();
    }

    @Override
    public String toString() {
        return "ListViewProviderInitDataRequest [initData=" + initData + ", readDataRequestContent=" + readDataRequestContent + "]";
    }

}
