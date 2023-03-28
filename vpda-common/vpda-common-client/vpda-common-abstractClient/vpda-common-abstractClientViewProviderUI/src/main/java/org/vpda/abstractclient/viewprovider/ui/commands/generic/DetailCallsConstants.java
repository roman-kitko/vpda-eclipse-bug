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
/**
 * 
 */
package org.vpda.abstractclient.viewprovider.ui.commands.generic;

/**
 * Names of common detail calls to server
 * 
 * @author kitko
 *
 */
public final class DetailCallsConstants {
    private DetailCallsConstants() {
    }

    /** Name of refresh command call */
    public static final String REFRESH = "resfresh";

    /** Name of fire fetch command call */
    public static final String FIRE_FETCH = "fireFetch";

    /** Name of complete fetch command call */
    public static final String COMPLETE_FETCH = "completeFetch";

    /** Name of complete command call */
    public static final String COMPLETE_DETAIL = "completeDetail";

    /** Name of complete autocomplete command call */
    public static final String COMPLETE_AUTO_COMPLETE = "completeAutoComplete";

    /** Name of complete fetch command call */
    public static final String COMPLETE_FETCH_WITH_LIST_VIEW_RESULTS_WITH_INFO = "completeFetchWithListViewResultsWithInfo";

    /** Name of submit detail call */
    public static final String SUBMIT_DETAIL = "submitDetail";
}
