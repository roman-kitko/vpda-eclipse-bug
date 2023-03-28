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

import java.util.Map;

import org.vpda.clientserver.viewprovider.ViewProviderInfo;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.preferences.AbstractViewProviderPreferences;
import org.vpda.clientserver.viewprovider.preferences.ListViewProviderPreferences;
import org.vpda.common.command.Command;

/**
 * Init response for {@link ListViewProvider}
 * 
 * @author kitko
 *
 */
public final class ListViewProviderInitResponse extends ViewProviderInitResponse {
    private static final long serialVersionUID = 2425692913644750119L;

    @Override
    public ListViewProviderSettings getInitSettings() {
        return (ListViewProviderSettings) viewProviderSettings;
    }

    @Override
    public ListViewProviderInfo getViewProviderInfo() {
        return (ListViewProviderInfo) viewProviderInfo;
    }

    /**
     * @param viewProviderInfo
     * @param viewProviderSettings
     * @param properties
     */
    public ListViewProviderInitResponse(ListViewProviderInfo viewProviderInfo, ListViewProviderSettings viewProviderSettings, Map<String, Object> properties) {
        super(viewProviderInfo, viewProviderSettings, properties);
    }

    /**
     * @param viewProviderInfo
     * @param preferences
     * @param properties
     */
    public ListViewProviderInitResponse(ListViewProviderInfo viewProviderInfo, ListViewProviderPreferences preferences, Map<String, Object> properties) {
        super(viewProviderInfo, preferences, properties);
    }

    /**
     * @param viewProviderInfo
     * @param initPreferences
     * @param properties
     * @param initCommand
     */
    public ListViewProviderInitResponse(ViewProviderInfo viewProviderInfo, AbstractViewProviderPreferences initPreferences, Map<String, Object> properties, Command<?> initCommand) {
        super(viewProviderInfo, initPreferences, properties, initCommand);
    }

    /**
     * @param viewProviderInfo
     * @param viewProviderSettings
     * @param properties
     * @param initCommand
     */
    public ListViewProviderInitResponse(ViewProviderInfo viewProviderInfo, ViewProviderSettings viewProviderSettings, Map<String, Object> properties, Command<?> initCommand) {
        super(viewProviderInfo, viewProviderSettings, properties, initCommand);
    }

    private ListViewProviderInitResponse(ListViewProviderInitResponseBuilder builder) {
        super(builder);
    }

    @Override
    public ListViewProviderPreferences getInitPreferences() {
        return (ListViewProviderPreferences) super.getInitPreferences();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListViewProviderInitResponseBuilder createBuilder() {
        return new ListViewProviderInitResponseBuilder();
    }

    /**
     * Builder for {@link ListViewProviderInitResponse}
     * 
     * @author kitko
     *
     */
    public static final class ListViewProviderInitResponseBuilder extends ViewProviderInitResponseBuilder<ListViewProviderInitResponse> {

        @Override
        public Class<? extends ListViewProviderInitResponse> getTargetClass() {
            return ListViewProviderInitResponse.class;
        }

        @Override
        public ListViewProviderInitResponse build() {
            return new ListViewProviderInitResponse(this);
        }

    }

}
