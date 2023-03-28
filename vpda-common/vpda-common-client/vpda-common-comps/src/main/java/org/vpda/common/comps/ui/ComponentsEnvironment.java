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
package org.vpda.common.comps.ui;

import java.util.Collection;

import org.vpda.common.ioc.objectresolver.ObjectResolver;

/**
 * Environment of components
 * 
 * @author kitko
 *
 */
public interface ComponentsEnvironment {
    /**
     * 
     * @return resolver of other objects
     */
    public ObjectResolver getEnv();

    /**
     * @return list of all component ids
     */
    public Collection<String> getComponentsIds();

    /**
     * Resolves {@link Component} by id
     * 
     * @param id
     * @return component or null if not registered
     */
    public Component<?, ?> getComponent(String id);

    /**
     * @param id
     * @param type
     * @return component or null if not registered
     */
    public <T extends Component<?, ?>> T getComponent(String id, Class<T> type);

    /**
     * Resolve ui component by id
     * 
     * @param id
     * @return ui component or null if not registered
     */
    public Object getUIComponent(String id);

    /**
     * Will create UI specific wrapper UI component of passed accesor
     * 
     * @param accesor
     * @return native ui component
     */
    public <V, U> UIComponentAccessor<V, U> createUIComponentAccesorWrapperUI(UIComponentAccessor<V, U> accesor);

}