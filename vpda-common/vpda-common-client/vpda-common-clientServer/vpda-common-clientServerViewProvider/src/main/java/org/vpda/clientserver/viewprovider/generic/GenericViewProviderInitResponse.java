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
package org.vpda.clientserver.viewprovider.generic;

import java.util.Map;

import org.vpda.clientserver.viewprovider.ViewProviderInfo;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.preferences.AbstractViewProviderPreferences;
import org.vpda.common.command.Command;

/**
 * @author kitko
 *
 */
public final class GenericViewProviderInitResponse extends ViewProviderInitResponse {

    private static final long serialVersionUID = 8192448501744140291L;

    @Override
    public GenericViewProviderSettings getInitSettings() {
        return (GenericViewProviderSettings) viewProviderSettings;
    }

    @Override
    public GenericViewProviderInfo getViewProviderInfo() {
        return (GenericViewProviderInfo) viewProviderInfo;
    }

    /**
     * @param providerInfo
     * @param initSettings
     * @param properties
     */
    public GenericViewProviderInitResponse(GenericViewProviderInfo providerInfo, GenericViewProviderSettings initSettings, Map<String, Object> properties) {
        super(providerInfo, initSettings, properties);
    }

    private GenericViewProviderInitResponse(GenericViewProviderInitResponseBuilder builder) {
        super(builder);
    }

    /**
     * @param viewProviderInfo
     * @param initPreferences
     * @param properties
     * @param initCommand
     */
    public GenericViewProviderInitResponse(ViewProviderInfo viewProviderInfo, AbstractViewProviderPreferences initPreferences, Map<String, Object> properties, Command<?> initCommand) {
        super(viewProviderInfo, initPreferences, properties, initCommand);
    }

    /**
     * @param viewProviderInfo
     * @param viewProviderSettings
     * @param properties
     * @param initCommand
     */
    public GenericViewProviderInitResponse(ViewProviderInfo viewProviderInfo, ViewProviderSettings viewProviderSettings, Map<String, Object> properties, Command<?> initCommand) {
        super(viewProviderInfo, viewProviderSettings, properties, initCommand);
    }

    @SuppressWarnings("unchecked")
    @Override
    public GenericViewProviderInitResponseBuilder createBuilder() {
        return new GenericViewProviderInitResponseBuilder();
    }

    /**
     * Builder for {@link ListViewProviderInitResponse}
     * 
     * @author kitko
     *
     */
    public static final class GenericViewProviderInitResponseBuilder extends ViewProviderInitResponseBuilder<GenericViewProviderInitResponse> {

        @Override
        public Class<? extends GenericViewProviderInitResponse> getTargetClass() {
            return GenericViewProviderInitResponse.class;
        }

        @Override
        public GenericViewProviderInitResponse build() {
            return new GenericViewProviderInitResponse(this);
        }

    }

}
