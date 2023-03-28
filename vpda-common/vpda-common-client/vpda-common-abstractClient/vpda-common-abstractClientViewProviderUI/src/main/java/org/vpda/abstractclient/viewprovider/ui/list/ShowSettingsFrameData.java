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

import java.io.Serializable;

/**
 * INitial settings data
 * 
 * @author kitko
 *
 */
public class ShowSettingsFrameData implements Serializable {

    private static final long serialVersionUID = 6915576893661138634L;

    /**
     * Settings tab
     * 
     * @author kitko
     *
     */
    public enum Tab {
        /** Columns tab */
        COLUMNS,
        /** Sort tab */
        SORT,
        /** Search tab */
        SEARCH,
        /** UI settings tab */
        UI;
    }

    private Tab initialTab;

    /**
     * Creates ShowSettingsFrameData
     * 
     * @param initialTab
     */
    public ShowSettingsFrameData(Tab initialTab) {
        this.initialTab = initialTab;
    }

    /**
     * @return the initialTab
     */
    public Tab getInitialTab() {
        return initialTab;
    }

}
