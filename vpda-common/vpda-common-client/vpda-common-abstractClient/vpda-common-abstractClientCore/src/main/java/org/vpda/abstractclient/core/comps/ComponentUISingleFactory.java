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

import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;

/**
 * This factory will create one component by component class
 * 
 * @author kitko
 * @param <T> - type of componet this factory transforms to UI component
 * @param <V> - type of ui object
 *
 */
public interface ComponentUISingleFactory<T extends Component, V> {
    /**
     * @return AbstractViewProviderComponent this factory will transfer to UI
     *         component
     */
    public Class<T> getComponentClass();

    /**
     * Creates new concrete ui component
     * 
     * @param cm
     * @param comp
     * @return new instance of view provider components
     */
    public V createUIComponent(ComponentsManager cm, T comp);

    /**
     * Updates ui component from {@link AbstractComponent} component.
     * 
     * @param cm
     * @param comp
     * @param uiComp
     */
    public void updateUIComponent(ComponentsManager cm, T comp, V uiComp);

    /**
     * Will update component from ui component
     * 
     * @param uiCompManager
     * @param comp
     * @param uiComp
     */
    public void updateComponentFromUI(ComponentsManager uiCompManager, T comp, V uiComp);

}
