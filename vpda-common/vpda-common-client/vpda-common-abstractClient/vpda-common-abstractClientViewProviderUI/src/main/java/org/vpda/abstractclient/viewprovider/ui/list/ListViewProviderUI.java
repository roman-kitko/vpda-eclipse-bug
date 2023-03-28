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
package org.vpda.abstractclient.viewprovider.ui.list;

import java.util.List;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.clientserver.viewprovider.list.ListViewPagingSize;
import org.vpda.clientserver.viewprovider.list.ListViewProvider;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInfo;
import org.vpda.clientserver.viewprovider.list.ListViewProviderSettings;
import org.vpda.clientserver.viewprovider.list.ListViewResults;
import org.vpda.clientserver.viewprovider.list.ListViewResultsInfo;
import org.vpda.clientserver.viewprovider.list.ListViewResultsWithInfo;
import org.vpda.clientserver.viewprovider.preferences.ListViewProviderPreferences;

/**
 * 
 * Interface for list view UI providers
 * 
 * @author kitko
 *
 */
public interface ListViewProviderUI extends ViewProviderUI {
    /**
     * Shows settings frame
     * 
     * @param data
     */
    public void showSettingsFrame(ShowSettingsFrameData data);

    @Override
    public ListViewProviderInfo getViewProviderInfo();

    @Override
    public ListViewProviderSettings getViewProviderSettings();

    @Override
    public ListViewProviderPreferences getViewProviderPreferences();

    /**
     * @return results info for current data
     */
    public ListViewResultsInfo getCurrentListViewResultsInfo();

    /**
     * Customize return value from super interface
     */
    @Override
    public ListViewProvider getViewProvider();

    /**
     * Append data to end of ui list
     * 
     * @param results
     */
    public void appendData(ListViewResults results);

    /**
     * Append to to start of list
     * 
     * @param results
     */
    public void appendDataFromStart(ListViewResults results);

    /**
     * Update current data with new data
     * 
     * @param results
     */
    public void updateData(ListViewResults results);

    /**
     * Delete data from list
     * 
     * @param keys
     */
    public void deleteData(List<? extends Object> keys);

    /**
     * @return event/listener support (just have sub class )
     */
    @Override
    public ListViewProviderUIListenersSupport getListenersSupport();

    @Override
    public ListViewResultsWithInfo getCurrentData();

    /**
     * 
     * @return all shown data
     */
    public ListViewResultsWithInfo getAllShownData();

    /**
     * 
     * @return size of all shown data
     */
    public int getAllShownDataSize();

    /**
     * Sets Rows per page
     * 
     * @param rowsPerPage
     */
    public void applyRowsPerPage(ListViewPagingSize rowsPerPage);
}
