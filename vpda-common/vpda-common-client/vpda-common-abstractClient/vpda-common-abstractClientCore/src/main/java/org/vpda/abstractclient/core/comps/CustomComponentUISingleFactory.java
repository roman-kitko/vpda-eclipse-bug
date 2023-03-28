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
package org.vpda.abstractclient.core.comps;

import org.vpda.clientserver.communication.data.ClientPlatform;

/**
 * Factory that can create {@link ComponentUISingleFactory} by current user
 * platform. Each component can have set this factory that will be used
 * preferably to default platform factory
 * 
 * @author kitko
 *
 */
public interface CustomComponentUISingleFactory {

    /**
     * Creates factory by user platform
     * 
     * @param platform
     * @param manager
     * @return ViewProviderOneComponentFactory
     */
    public ComponentUISingleFactory createComponentFactory(ClientPlatform platform, ComponentsManager manager);
}
