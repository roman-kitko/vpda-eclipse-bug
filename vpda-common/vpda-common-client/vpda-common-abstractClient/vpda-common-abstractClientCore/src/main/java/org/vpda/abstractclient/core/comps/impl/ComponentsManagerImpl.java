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
package org.vpda.abstractclient.core.comps.impl;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vpda.abstractclient.core.comps.ComponentUIFactory;
import org.vpda.abstractclient.core.comps.ComponentsManager;
import org.vpda.abstractclient.core.comps.ComponentsUI;
import org.vpda.common.comps.EnvironmentDataChangeEvent;
import org.vpda.common.comps.EnvironmentInitEvent;
import org.vpda.common.comps.EnvironmentLayoutChangeEvent;
import org.vpda.common.comps.EnvironmentStructureChangeEvent;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.comps.shortcuts.UIShortcuts;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.UIComponentAccessor;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.common.ioc.objectresolver.ArrayObjectResolver;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.internal.common.util.Assert;

/**
 * Abstract platform manager for components from one {@link ComponentsUI}
 * 
 * @author rkitko
 *
 */
public final class ComponentsManagerImpl implements ComponentsManager, Serializable {
    private static final long serialVersionUID = -3550709715805990941L;
    private final ComponentsUI providerUI;
    private final ComponentUIFactory viewProviderComponentsUIFactory;

    private final Map<String, Component> comps;
    private final Map<String, Object> uiComps;
    private final Map<String, Object> buttonGroups;
    private final List<UIShortcuts> boundShortcuts;
    private final Map<ComponentPropertyKey<?>, Object> propertiesForComponents;

    private static class ComponentPropertyKey<T> implements Serializable {
        private static final long serialVersionUID = -4967002777833940170L;
        private final String componentId;
        private final String propertyKey;
        private final Class<T> type;

        ComponentPropertyKey(String componentId, String propertyKey, Class<T> type) {
            super();
            this.componentId = componentId;
            this.propertyKey = propertyKey;
            this.type = type;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((componentId == null) ? 0 : componentId.hashCode());
            result = prime * result + ((propertyKey == null) ? 0 : propertyKey.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ComponentPropertyKey other = (ComponentPropertyKey) obj;
            if (componentId == null) {
                if (other.componentId != null)
                    return false;
            }
            else if (!componentId.equals(other.componentId))
                return false;
            if (propertyKey == null) {
                if (other.propertyKey != null)
                    return false;
            }
            else if (!propertyKey.equals(other.propertyKey))
                return false;
            if (type == null) {
                if (other.type != null)
                    return false;
            }
            else if (!type.equals(other.type))
                return false;
            return true;
        }

    }

    /**
     * Creates ViewProviderComponentsManagerImpl
     * 
     * @param providerUI
     * @param viewProviderComponentsUIFactory
     */
    public ComponentsManagerImpl(ComponentsUI providerUI, ComponentUIFactory viewProviderComponentsUIFactory) {
        this.providerUI = Assert.isNotNullArgument(providerUI, "providerUI");
        this.viewProviderComponentsUIFactory = Assert.isNotNullArgument(viewProviderComponentsUIFactory, "viewProviderComponentsUIFactory");
        comps = new HashMap<String, Component>();
        uiComps = new HashMap<String, Object>();
        propertiesForComponents = new HashMap<>(2);
        buttonGroups = new HashMap<String, Object>(1);
        boundShortcuts = new ArrayList<UIShortcuts>(2);
    }

    @Override
    public Object createUIComponent(Component<?, ?> comp) {
        Object o = viewProviderComponentsUIFactory.createUIComponent(this, comp);
        comps.put(comp.getId(), comp);
        uiComps.put(comp.getId(), o);
        return o;
    }

    @Override
    public <T> T createUIComponent(Component<?, ?> comp, Class<T> expectedClass) {
        Object o = viewProviderComponentsUIFactory.createUIComponent(this, comp);
        if (!expectedClass.isInstance(o)) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Created object is not compatible with expected class. Expected [{0}], got [{1}} ", expectedClass.getName(), o.getClass().getName()));
        }
        comps.put(comp.getId(), comp);
        uiComps.put(comp.getId(), o);
        return expectedClass.cast(o);
    }

    @Override
    public Component<?, ?> getComponent(String id) {
        return comps.get(id);
    }

    @Override
    public ComponentsUI getUI() {
        return providerUI;
    }

    @Override
    public Object getUIComponent(String id) {
        return uiComps.get(id);
    }

    @Override
    public Collection<String> getComponentsIds() {
        return Collections.unmodifiableCollection(comps.keySet());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateAllComponentsFromUI() {
        for (Component comp : comps.values()) {
            updateComponentFromUI(comp);
        }
    }

    @Override
    public Object updateUIComponent(Component<?, ?> comp) {
        Component<?, ?> oldComp = comps.get(comp.getId());
        if (oldComp == null) {
            throw new IllegalArgumentException("Component not registred : " + comp.getId());
        }
        Object uiComp = uiComps.get(comp.getId());
        if (uiComp == null) {
            throw new IllegalArgumentException("No ui Component registred : " + comp.getId());
        }
        viewProviderComponentsUIFactory.updateUIComponent(this, comp, uiComp);
        comps.put(comp.getId(), comp);
        return uiComp;
    }

    @Override
    public Object updateUIComponent(String compId) {
        Component<?, ?> comp = comps.get(compId);
        if (comp == null) {
            throw new IllegalArgumentException("Component not registred : " + comp.getId());
        }
        return updateUIComponent(comp);
    }

    @Override
    public Component<?, ?> unregisterComponent(String id) {
        Component<?, ?> comp = comps.remove(id);
        uiComps.remove(id);
        return comp;
    }

    @Override
    public ComponentUIFactory getViewProviderComponentsUIFactory() {
        return viewProviderComponentsUIFactory;
    }

    @Override
    public <V, T extends AbstractCompLocValue> Component<V, T> updateComponentFromUI(org.vpda.common.comps.ui.Component<V, T> comp) {
        Object uiComp = uiComps.get(comp.getId());
        if (uiComp == null) {
            throw new IllegalArgumentException("No ui Component registred : " + comp.getId());
        }
        viewProviderComponentsUIFactory.updateComponentFromUI(this, comp, uiComp);
        return comp;
    }

    @Override
    public void updateComponents(ComponentsGroupsDef def) {
        buttonGroups.clear();
        for (Component comp : comps.values()) {
            Component newComp = def.getComponent(comp.getId());
            if (newComp != null) {
                updateUIComponent(newComp);
            }
        }
    }

    @Override
    public void clearAll() {
        comps.clear();
        uiComps.clear();
        buttonGroups.clear();
    }

    @Override
    public void rebuildUI(ContainerAC rootContainer) {
        providerUI.rebuildUI(rootContainer);
    }

    @Override
    public void registerUIComponent(Component<?, ?> comp, Object uiComp) {
        comps.put(comp.getId(), comp);
        uiComps.put(comp.getId(), uiComp);
    }

    @Override
    public Component<?, ?> getMandatoryComponent(String id) {
        Component<?, ?> comp = getComponent(id);
        if (comp == null) {
            throw new IllegalArgumentException("Component with id " + id + " not registered");
        }
        return comp;
    }

    @Override
    public <T extends Component<?, ?>> T getComponent(String id, Class<T> type) {
        Component<?, ?> comp = getComponent(id);
        if (comp == null) {
            return null;
        }
        if (!type.isInstance(comp)) {
            throw new IllegalArgumentException(MessageFormat.format("Registered component is of different type. Id = [{0}] , required type = [{1}], real type = [{2}]", id, type, comp.getClass()));
        }
        return type.cast(comp);
    }

    @Override
    public <T extends Component<?, ?>> T getManadatoryComponent(String id, Class<T> type) {
        T comp = getComponent(id, type);
        if (comp == null) {
            throw new IllegalArgumentException("Component with id " + id + " not registered");
        }
        return comp;
    }

    @Override
    public void fireEnvironmentDataChanged(EnvironmentDataChangeEvent changeEvent) {
        for (Component comp : comps.values()) {
            comp.getMemberListenerSupport().fireEnvironmentDataChanged(changeEvent);
        }
        for (UIShortcuts shortcuts : boundShortcuts) {
            shortcuts.fireEnvironmentDataChanged(changeEvent);
        }
    }

    @Override
    public void fireEnvironmentInitialized(EnvironmentInitEvent event) {
        for (Component comp : comps.values()) {
            comp.getMemberListenerSupport().fireEnvironmentInitialized(event);
        }
        for (UIShortcuts shortcuts : boundShortcuts) {
            shortcuts.fireEnvironmentInitialized(event);
        }
    }

    @Override
    public void fireEnvironmentStructureChanged(EnvironmentStructureChangeEvent event) {
        for (Component comp : comps.values()) {
            comp.getMemberListenerSupport().fireEnvironmentStructureChanged(event);
        }
        for (UIShortcuts shortcuts : boundShortcuts) {
            shortcuts.fireEnvironmentStructureChanged(event);
        }
    }

    @Override
    public void fireEnvironmentLayoutChanged(EnvironmentLayoutChangeEvent event) {
        for (Component comp : comps.values()) {
            comp.getMemberListenerSupport().fireEnvironmentLayoutChanged(event);
        }
        for (UIShortcuts shortcuts : boundShortcuts) {
            shortcuts.fireEnvironmentLayoutChanged(event);
        }
    }

    @Override
    public List<UIShortcuts> getBoundShortcuts() {
        return Collections.unmodifiableList(boundShortcuts);
    }

    @Override
    public void bindUIShortcuts(UIShortcuts shortcuts) {
        viewProviderComponentsUIFactory.bindUIShortcuts(this, shortcuts);
        this.boundShortcuts.add(shortcuts);
    }

    @Override
    public ObjectResolver getEnv() {
        return new ArrayObjectResolver(providerUI, viewProviderComponentsUIFactory);
    }

    @Override
    public <V, U> UIComponentAccessor<V, U> createUIComponentAccesorWrapperUI(UIComponentAccessor<V, U> accesor) {
        return providerUI.createUIComponentAccesorWrapperUI(accesor);
    }

    @Override
    public <T> void setPropertyForComponent(String componentId, String propertyKey, Class<T> type, T value) {
        propertiesForComponents.put(new ComponentPropertyKey<T>(componentId, propertyKey, type), value);
    }

    @Override
    public <T> T removePropertyForComponent(String componentId, String propertyKey, Class<T> type) {
        return type.cast(propertiesForComponents.remove(new ComponentPropertyKey<T>(componentId, propertyKey, type)));
    }

    @Override
    public <T> T getPropertyForComponent(String componentId, String propertyKey, Class<T> type) {
        return type.cast(propertiesForComponents.get(new ComponentPropertyKey<T>(componentId, propertyKey, type)));
    }
}
