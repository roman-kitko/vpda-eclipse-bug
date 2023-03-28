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
package org.vpda.abstractclient.viewprovider.ui;

import java.util.List;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderID;

/**
 * Not defined yet if all ui providers should be also containers
 * 
 * @author rki
 *
 */
public interface ContainerViewProviderUI extends ViewProviderUI {
    /**
     * 
     * @return list of available providers
     */
    public List<ViewProviderDef> getAvailableProviders();

    /**
     * 
     * @return list of active providers
     */
    public List<ViewProviderID> getActiveProviders();

    /**
     * @param id
     * @param active
     */
    /**
     * Activate or deactivate provider with given id
     * 
     * @param id
     * @param active
     */
    public void setProviderActive(ViewProviderID id, boolean active);

}
