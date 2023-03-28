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

import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.common.comps.ui.menu.ContextMenuAC;

/**
 * Row data UI provider. Defined from DB schema,programatically or other way.
 * 
 * @author kitko
 *
 */
public interface ListViewProvider extends ViewProvider, ListViewProviderDataRetrieval {
    /** Just to have concrete result for this hierarchy */
    @Override
    public ListViewProviderInitResponse initProvider(ViewProviderInitData initData) throws ViewProviderException;

    /**
     * Open the results with passed settings. Must be called before reading the data
     * 
     * @param lastCallerItem   Current data of last caller
     * @param listViewSettings current settings
     * @return info for results
     * @throws IllegalStateException when not called initProvider before
     * @throws ViewProviderException
     *
     */
    public ListViewResultsInfo openResults(ViewProviderCallerItem lastCallerItem, ListViewProviderSettings listViewSettings) throws IllegalStateException, ViewProviderException;

    /**
     * @param row
     * @return specific context menu for row
     * @throws ViewProviderException
     */
    public ContextMenuAC getRowSpecificContextMenu(ListViewRow row) throws ViewProviderException;

    /**
     * @param lastCallerItem
     * @param listViewSettings
     * @return count of records
     * @throws ViewProviderException
     */
    public long getRowsCount(ViewProviderCallerItem lastCallerItem, ListViewProviderSettings listViewSettings) throws ViewProviderException;

    /**
     * Init provider and get data
     * 
     * @param request
     * @return ListViewProviderInitDataResponse
     * @throws ViewProviderException
     */
    public ListViewProviderInitDataResponse initAndReadData(ListViewProviderInitDataRequest request) throws ViewProviderException;

    /**
     * Calls {@link #openResults(ViewProviderCallerItem, ListViewProviderSettings)}
     * and {@link #readFirst(int)}
     * 
     * @param request
     * @return ListViewResultsWithInfo
     * @throws IllegalStateException
     * @throws ViewProviderException
     */
    public ListViewResultsWithInfo openResultsAndReadData(ListViewProviderOpenResultsAndReadDataRequest request) throws IllegalStateException, ViewProviderException;

}
