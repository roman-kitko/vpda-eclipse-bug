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

import java.util.Collection;

import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.comps.shortcuts.UIShortcuts;
import org.vpda.common.comps.ui.Component;

/**
 * This is factory for view provider components.
 * 
 * @author kitko
 *
 */
public interface ComponentUIFactory {

    /**
     * Creates new concrete ui component
     * 
     * @param <V>
     * @param <T>
     * @param <Z>
     * @param uiCompManager
     * @param comp
     * @return new instance of view provider components
     */
    public <V, T extends AbstractCompLocValue, Z> Z createUIComponent(ComponentsManager uiCompManager, Component<V, T> comp);

    /**
     * Will update created ui component from new comp
     * 
     * @param <V>
     * @param <T>
     * @param <Z>
     * @param uiCompManager
     * @param comp
     * @param uiComp
     */
    public <V, T extends AbstractCompLocValue, Z> void updateUIComponent(ComponentsManager uiCompManager, Component<V, T> comp, Z uiComp);

    /**
     * Will update component from ui
     * 
     * @param <V>
     * @param <T>
     * @param <Z>
     * @param uiCompManager
     * @param comp
     * @param uiComp
     */
    public <V, T extends AbstractCompLocValue, Z> void updateComponentFromUI(ComponentsManager uiCompManager, Component<V, T> comp, Z uiComp);

    /**
     * @return list of all component classes for which we can create components
     */
    public Collection<Class<? extends Component>> getAvailableCompClasses();

    /**
     * @param clazz
     * @return OneComponent factory by comp class
     */
    public ComponentUISingleFactory getSingleComponentFactory(Class<? extends Component> clazz);

    /**
     * @param clazz
     * @return ViewProviderOneComponentFactory by class or throws Runtime exception
     */
    public ComponentUISingleFactory getMandatorySingleComponentFactory(Class<? extends Component> clazz);

    /**
     * Finds factory for concrete component. It first looks at custom factory using
     * <code>comp.getProperty(ViewProviderComponentConstants.CUSTOM_VIEWPROVIDER_ONE_COMPONENT_FACTORY) </code>
     * then creates factory by component class
     * 
     * @param <V>
     * @param <T>
     * @param <Z>
     * @param uiCompManager
     * @param comp
     * @return factory
     */
    public <V, T extends AbstractCompLocValue, Z> ComponentUISingleFactory<Component<V, T>, Z> getOneComponentFactory(ComponentsManager uiCompManager, Component<V, T> comp);

    /**
     * Register one component factory
     * 
     * @param <T>
     * @param <V>
     * @param factory
     */
    public <T extends Component, V> void registerSingleComponentFactory(ComponentUISingleFactory<T, V> factory);

    /**
     * Register factory that will handle creation/update of components with fetch
     * 
     * @param <T>
     * @param <V>
     * @param factory
     */
    public <T extends Component, V> void registerFetchSingleComponentFactory(ComponentUISingleFactory<T, V> factory);

    /**
     * @param <T>
     * @param <V>
     * @return factory that will handle creation/update of components with fetch
     */
    public <T extends Component, V> ComponentUISingleFactory<T, V> getFetchSingleComponentFactory();

    /**
     * @return Layout UI factory
     */
    public UILayoutManagerFactory getUILayoutManagerFactory();

    /**
     * Will bind ui shortcuts
     * 
     * @param uiCompManager
     * @param shortcuts
     */
    public void bindUIShortcuts(ComponentsManager uiCompManager, UIShortcuts shortcuts);

}
