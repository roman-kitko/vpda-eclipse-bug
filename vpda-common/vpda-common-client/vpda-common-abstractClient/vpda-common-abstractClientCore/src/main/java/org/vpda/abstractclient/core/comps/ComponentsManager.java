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
/**
 * 
 */
package org.vpda.abstractclient.core.comps;

import java.util.Collection;
import java.util.List;

import org.vpda.common.comps.EnvironmentDataChangeEvent;
import org.vpda.common.comps.EnvironmentInitEvent;
import org.vpda.common.comps.EnvironmentLayoutChangeEvent;
import org.vpda.common.comps.EnvironmentStructureChangeEvent;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.comps.shortcuts.UIShortcuts;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ComponentsEnvironment;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;

/**
 * Will create/update components and reflected ui objects for one view provider
 * ui. It uses ViewProviderComponentsUIFactory to create/update components
 * 
 * @author kitko
 *
 */
public interface ComponentsManager extends ComponentsEnvironment {
    /**
     * Will create and register component
     * 
     * @param comp
     * @return created component
     */
    public Object createUIComponent(Component<?, ?> comp);

    /**
     * Create ui component expecting explicit type
     * 
     * @param comp
     * @param expectedClass
     * @return ui component
     */
    public <T> T createUIComponent(Component<?, ?> comp, Class<T> expectedClass);

    /**
     * Update Component ui using new values from comp
     * 
     * @param comp
     * @return updated ui component
     */
    public Object updateUIComponent(Component<?, ?> comp);

    /**
     * Update UI Component ui using values from comp with id compId
     * 
     * @param compId
     * @return updated ui component
     */
    public Object updateUIComponent(String compId);

    /**
     * Register a component and its created ui.
     * 
     * @param comp
     * @param uiComp
     */
    public void registerUIComponent(Component<?, ?> comp, Object uiComp);

    /**
     * Will update component from ui component
     * 
     * @param <V>
     * @param <T>
     * @param comp
     * @return updated component
     */
    public <V, T extends AbstractCompLocValue> Component<V, T> updateComponentFromUI(Component<V, T> comp);

    /**
     * Will update all components from ui objects
     */
    public void updateAllComponentsFromUI();

    /**
     * @return associated ui
     */
    public ComponentsUI getUI();

    /**
     * @return ViewProviderComponentsUIFactory
     */
    public ComponentUIFactory getViewProviderComponentsUIFactory();

    /**
     * Resolves {@link AbstractComponent} by id
     * 
     * @param id
     * @return component or null if not registered
     */
    @Override
    public Component<?, ?> getComponent(String id);

    /**
     * Resolves {@link AbstractComponent} by id. Throws IllegalArgument exception of
     * not registered
     * 
     * @param id
     * @return component or null if not registered
     */
    public Component<?, ?> getMandatoryComponent(String id);

    /**
     * @param id
     * @param type
     * @return component or null if not registered
     */
    @Override
    public <T extends Component<?, ?>> T getComponent(String id, Class<T> type);

    /**
     * Gets component by id
     * 
     * @param id
     * @param type
     * @return components
     */
    public <T extends Component<?, ?>> T getManadatoryComponent(String id, Class<T> type);

    /**
     * @return list of all component ids
     */
    @Override
    public Collection<String> getComponentsIds();

    /**
     * Resolve ui component by id
     * 
     * @param id
     * @return ui component or null if not registered
     */
    @Override
    public Object getUIComponent(String id);

    /**
     * Unregister component and its mapping to ui component
     * 
     * @param id
     * @return old registered comp or null if not registered
     */
    public Component<?, ?> unregisterComponent(String id);

    /**
     * Updates components in current ui from def
     * 
     * @param def
     */
    public void updateComponents(ComponentsGroupsDef def);

    /**
     * Clear all components and maping
     */
    public void clearAll();

    /**
     * Rebuilds ui, dispose components and create new main container
     * 
     * @param rootContainer
     */
    public void rebuildUI(ContainerAC rootContainer);

    /**
     * Fire event about environment change
     * 
     * @param changeEvent
     */
    public void fireEnvironmentDataChanged(EnvironmentDataChangeEvent changeEvent);

    /**
     * Fire event that env around this components manager was initialized
     * 
     * @param event
     */
    public void fireEnvironmentInitialized(EnvironmentInitEvent event);

    /**
     * Fire event that there was change of structure in environment
     * 
     * @param event
     */
    public void fireEnvironmentStructureChanged(EnvironmentStructureChangeEvent event);

    /**
     * Fire event about layout change
     * 
     * @param event
     */
    public void fireEnvironmentLayoutChanged(EnvironmentLayoutChangeEvent event);

    /**
     * Binds shortcuts
     * 
     * @param shortcuts
     */
    public void bindUIShortcuts(UIShortcuts shortcuts);

    /**
     * @return bound shortcuts
     */
    public List<UIShortcuts> getBoundShortcuts();

    /**
     * Allows storing some property for component
     * 
     * @param componentId
     * @param propertyKey
     * @param type
     * @param value
     */
    public <T> void setPropertyForComponent(String componentId, String propertyKey, Class<T> type, T value);

    /**
     * Removes property for component
     * 
     * @param componentId
     * @param propertyKey
     * @param type
     * @return old value
     */
    public <T> T removePropertyForComponent(String componentId, String propertyKey, Class<T> type);

    /**
     * 
     * @param componentId
     * @param propertyKey
     * @param type
     * @return property for component
     */
    public <T> T getPropertyForComponent(String componentId, String propertyKey, Class<T> type);

}
