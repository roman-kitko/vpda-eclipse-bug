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
import org.vpda.internal.common.util.Assert;

/**
 * @author kitko
 *
 */
public final class ListViewProviderOpenResultsAndReadDataRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 821586383520015277L;
    private final ReadDataRequestContent readDataRequestContent;

    /**
     * @param readDataRequestContent
     */
    public ListViewProviderOpenResultsAndReadDataRequest(ReadDataRequestContent readDataRequestContent) {
        super();
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
     * @return the listViewSettings
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
     * @return the offset
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
        return "ListViewProviderOpenResultsAndReadDataRequest [readDataRequestContent=" + readDataRequestContent + "]";
    }

}
