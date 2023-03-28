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

import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.ContainerLayout;

/**
 * Provider layout manager will setup layout for container and then it will add
 * and remove children from container
 * 
 * @author kitko
 *
 */
public interface UILayoutManager {

    /**
     * Creates ui component container
     * 
     * @param providerUI
     * @param container
     * @return ui component
     */
    public Object createUIContainer(ComponentsUI providerUI, ContainerAC container);

    /**
     * Setup layout for container
     * 
     * @param providerUI
     * @param container
     * @param containerImpl - real UI instance of container
     */
    public void setupLayout(ComponentsUI providerUI, ContainerAC container, Object containerImpl);

    /**
     * Add child component to container
     * 
     * @param providerUI
     * @param container
     * @param containerImpl
     * @param comp
     * @param componentImpl
     */
    public void addComponent(ComponentsUI providerUI, ContainerAC container, Object containerImpl, org.vpda.common.comps.ui.Component comp, Object componentImpl);

    /**
     * 
     * @param providerUI
     * @param container
     * @param containerImpl
     * @param comp
     * @param componentImpl
     */
    public void removeComponent(ComponentsUI providerUI, ContainerAC container, Object containerImpl, org.vpda.common.comps.ui.Component comp, Object componentImpl);

    /**
     * @return layout class this manager can handle
     */
    public Class<? extends ContainerLayout> getContainerLayoutClass();
}
