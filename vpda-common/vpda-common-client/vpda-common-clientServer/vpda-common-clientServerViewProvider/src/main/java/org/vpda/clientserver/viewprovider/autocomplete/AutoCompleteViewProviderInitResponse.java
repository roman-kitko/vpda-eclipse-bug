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
package org.vpda.clientserver.viewprovider.autocomplete;

import java.util.Map;

import org.vpda.clientserver.viewprovider.ViewProviderInfo;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.preferences.AbstractViewProviderPreferences;
import org.vpda.clientserver.viewprovider.preferences.AutoCompleteViewProviderPreferences;
import org.vpda.common.command.Command;

/**
 * @author kitko
 *
 */
public final class AutoCompleteViewProviderInitResponse extends ViewProviderInitResponse {

    private static final long serialVersionUID = 8192448501744140291L;

    @Override
    public AutoCompleteViewProviderSettings getInitSettings() {
        return (AutoCompleteViewProviderSettings) viewProviderSettings;
    }

    @Override
    public AutoCompleteViewProviderInfo getViewProviderInfo() {
        return (AutoCompleteViewProviderInfo) viewProviderInfo;
    }

    /**
     * @param providerInfo
     * @param initSettings
     * @param properties
     */
    public AutoCompleteViewProviderInitResponse(AutoCompleteViewProviderInfo providerInfo, AutoCompleteViewProviderSettings initSettings, Map<String, Object> properties) {
        super(providerInfo, initSettings, properties);
    }

    /**
     * Creates AutoCompleteViewProviderInitResponse
     * 
     * @param providerInfo
     * @param initSettings
     */
    public AutoCompleteViewProviderInitResponse(AutoCompleteViewProviderInfo providerInfo, AutoCompleteViewProviderSettings initSettings) {
        this(providerInfo, initSettings, null);
    }

    /**
     * Creates AutoCompleteViewProviderInitResponse
     * 
     * @param providerInfo
     * @param initPreferences
     * @param properties
     */
    public AutoCompleteViewProviderInitResponse(AutoCompleteViewProviderInfo providerInfo, AutoCompleteViewProviderPreferences initPreferences, Map<String, Object> properties) {
        super(providerInfo, initPreferences, properties);
    }

    /**
     * @param viewProviderInfo
     * @param initPreferences
     * @param properties
     * @param initCommand
     */
    public AutoCompleteViewProviderInitResponse(ViewProviderInfo viewProviderInfo, AbstractViewProviderPreferences initPreferences, Map<String, Object> properties, Command<?> initCommand) {
        super(viewProviderInfo, initPreferences, properties, initCommand);
    }

    /**
     * @param viewProviderInfo
     * @param viewProviderSettings
     * @param properties
     * @param initCommand
     */
    public AutoCompleteViewProviderInitResponse(ViewProviderInfo viewProviderInfo, ViewProviderSettings viewProviderSettings, Map<String, Object> properties, Command<?> initCommand) {
        super(viewProviderInfo, viewProviderSettings, properties, initCommand);
    }

    private AutoCompleteViewProviderInitResponse(AutoCompleteViewProviderInitResponseBuilder builder) {
        super(builder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AutoCompleteViewProviderInitResponseBuilder createBuilder() {
        return new AutoCompleteViewProviderInitResponseBuilder();
    }

    /**
     * Builder for {@link AutoCompleteViewProviderInitResponse}
     * 
     * @author kitko
     *
     */
    public static final class AutoCompleteViewProviderInitResponseBuilder extends ViewProviderInitResponseBuilder<AutoCompleteViewProviderInitResponse> {

        @Override
        public Class<? extends AutoCompleteViewProviderInitResponse> getTargetClass() {
            return AutoCompleteViewProviderInitResponse.class;
        }

        @Override
        public AutoCompleteViewProviderInitResponse build() {
            return new AutoCompleteViewProviderInitResponse(this);
        }

    }

}
