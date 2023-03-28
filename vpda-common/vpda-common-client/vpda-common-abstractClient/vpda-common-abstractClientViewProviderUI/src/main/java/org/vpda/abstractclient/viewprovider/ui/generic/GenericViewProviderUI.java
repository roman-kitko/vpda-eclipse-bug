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
package org.vpda.abstractclient.viewprovider.ui.generic;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.clientserver.viewprovider.generic.GenericViewProvider;
import org.vpda.clientserver.viewprovider.generic.GenericViewProviderInfo;
import org.vpda.clientserver.viewprovider.generic.GenericViewProviderSettings;
import org.vpda.clientserver.viewprovider.preferences.GenericViewProviderPreferences;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;

/**
 * Detail ViewProviderUI is abstract interface for client ui of details
 * providers
 * 
 * @author kitko
 *
 */
public interface GenericViewProviderUI extends ViewProviderUI {

    @Override
    public GenericViewProviderInfo getViewProviderInfo();

    @Override
    public GenericViewProviderSettings getViewProviderSettings();

    /**
     * Customize return value from super interface
     */
    @Override
    public GenericViewProvider getViewProvider();

    @Override
    public ComponentsGroupsDef getCurrentData();

    /**
     * Will fire fetch
     * 
     * @param compId
     */
    @Override
    public void fireFetch(String compId);

    @Override
    public GenericViewProviderPreferences getViewProviderPreferences();
}
