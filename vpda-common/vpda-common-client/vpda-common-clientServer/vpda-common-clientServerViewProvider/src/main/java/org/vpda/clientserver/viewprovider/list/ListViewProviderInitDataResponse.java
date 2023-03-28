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

/**
 * Init facade like response
 * 
 * @author kitko
 *
 */
public final class ListViewProviderInitDataResponse implements Serializable {

    private static final long serialVersionUID = -4299971108392347682L;
    private final ListViewProviderInitResponse initResponse;
    private final ListViewResultsWithInfo resultsWithInfo;

    /**
     * @param initResponse
     * @param resultsWithInfo
     */
    public ListViewProviderInitDataResponse(ListViewProviderInitResponse initResponse, ListViewResultsWithInfo resultsWithInfo) {
        super();
        this.initResponse = initResponse;
        this.resultsWithInfo = resultsWithInfo;
    }

    /**
     * @return the initResponse
     */
    public ListViewProviderInitResponse getInitResponse() {
        return initResponse;
    }

    /**
     * @return the resultsWithInfo
     */
    public ListViewResultsWithInfo getResultsWithInfo() {
        return resultsWithInfo;
    }

    @Override
    public String toString() {
        return "ListViewProviderInitDataResponse [initResponse=" + initResponse + ", resultsWithInfo=" + resultsWithInfo + "]";
    }

}
