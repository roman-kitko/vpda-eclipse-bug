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

import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitDataResponse;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.list.ListViewResultsInfo;
import org.vpda.clientserver.viewprovider.list.ListViewResultsWithInfo;
import org.vpda.clientserver.viewprovider.stateless.InitStatelessRequest;
import org.vpda.clientserver.viewprovider.stateless.StatelessViewProviderServices;
import org.vpda.common.comps.ui.menu.ContextMenuAC;

/** ListViewProvider stateless services */
public interface StatelessListViewProviderServices extends StatelessViewProviderServices {

    @Override
    public ListViewProviderInitResponse getProviderInfo(InitStatelessRequest request) throws ViewProviderException;

    /**
     * Reads data starting at offset
     * 
     * @param request
     * @return ListViewResults of read data
     * @throws ViewProviderException
     */
    public ListViewResultsWithInfo read(ReadDataStatelessRequest request) throws ViewProviderException;

    /**
     * 
     * @param request
     * @return results info for read request
     * @throws ViewProviderException
     */
    public ListViewResultsInfo getResultsInfo(GetResultsInfoDataStatelessRequest request) throws ViewProviderException;

    /**
     * 
     * @param request
     * @return row specific context menu
     * @throws ViewProviderException
     */
    public ContextMenuAC getRowSpecificContextMenu(GetRowSpecificContextMenuStatelessRequest request) throws ViewProviderException;

    /**
     * @param request
     * @return rows count
     * @throws ViewProviderException
     */
    public long getRowsCount(GetResultsInfoDataStatelessRequest request) throws ViewProviderException;

    /**
     * @param request
     * @return ListViewProviderInitDataResponse
     * @throws ViewProviderException
     */
    public ListViewProviderInitDataResponse initAndRead(InitAndReadDataStatelessRequest request) throws ViewProviderException;

    /**
     * @param request
     * @return ListViewResultsInfo
     * @throws ViewProviderException
     */
    public ListViewResultsWithInfo getResultsInfoAndRead(GetResultsInfoAndReadDataStatelessRequest request) throws ViewProviderException;

}
