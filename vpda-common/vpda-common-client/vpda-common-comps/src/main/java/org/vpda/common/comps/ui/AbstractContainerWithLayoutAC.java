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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vpda.common.comps.Member;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.internal.common.util.OrderedMap;

/**
 * 
 * Container with layout
 * 
 * @author kitko
 *
 */
public abstract class AbstractContainerWithLayoutAC extends AbstractComponent<Void, AbstractCompLocValue>
        implements ModifiableContainer<Void, AbstractCompLocValue>, ContainerWithLayout<Void, AbstractCompLocValue> {

    private OrderedMap<String, org.vpda.common.comps.ui.Component<?, ?>> components;
    private Map<String, ComponentLayoutConstraint> contraints;
    private ContainerLayout containerLayout;

    /** */
    public AbstractContainerWithLayoutAC() {
    }

    /**
     * Creates empty container
     * 
     * @param id
     */
    public AbstractContainerWithLayoutAC(String id) {
        super(id);
        components = new OrderedMap<>(3);
        contraints = new HashMap<>(3);
        containerLayout = new ContainerHorizontalLayout(this, HorizontalAlignment.LEFT);
    }

    /**
     * Creates ViewProviderComponentContainerImpl with id and layout
     * 
     * @param id
     * @param layout
     */
    public AbstractContainerWithLayoutAC(String id, ContainerLayout layout) {
        super(id);
        components = new OrderedMap<>(3);
        contraints = new HashMap<>(3);
        containerLayout = layout;
        if (containerLayout != null) {
            containerLayout.setTargetContainer(this);
        }
    }

    private static final long serialVersionUID = 4153041006025275100L;

    /**
     * @param childId
     * @return child component or null if not found
     */
    @Override
    public org.vpda.common.comps.ui.Component getComponent(String childId) {
        org.vpda.common.comps.ui.Component component = components.get(childId);
        if (component == null) {
            component = components.get(ComponentUtils.getComponentId(id, childId));
        }
        return component;
    }

    @Override
    public <X extends Component> X getComponent(String childId, Class<X> targetType) {
        Object o = getComponent(childId);
        return targetType.cast(o);
    }

    /**
     * @return layout of container
     */
    @Override
    public ContainerLayout getContainerLayout() {
        return containerLayout;
    }

    /**
     * Sets layout
     * 
     * @param layout
     * @return this
     */
    @Override
    public AbstractContainerWithLayoutAC setContainerLayout(ContainerLayout layout) {
        if (layout == null) {
            throw new IllegalArgumentException("Layout is null");
        }
        this.containerLayout = layout;
        if (this.containerLayout.getTargetContainer() != this) {
            this.containerLayout.setTargetContainer(this);
        }
        return this;
    }

    /**
     * Adds component to container
     * 
     * @param <V>
     * @param <T>
     * @param component
     * @param contraint
     * @return this
     */
    @Override
    public <V, T extends AbstractCompLocValue> AbstractContainerWithLayoutAC addComponent(Component<V, T> component, ComponentLayoutConstraint contraint) {
        if (!components.containsKey(component.getId())) {
            component.setContainerId(id);
            components.put(component.getId(), component);
            if (contraint != null) {
                contraints.put(component.getId(), contraint);
            }
            containerLayout.componentAdded(component, contraint);
        }
        return this;

    }

    /**
     * Adds component
     * 
     * @param <V>
     * @param <T>
     * @param component
     * @return this
     */
    @Override
    public <V, T extends AbstractCompLocValue> AbstractContainerWithLayoutAC addComponent(Component<V, T> component) {
        return addComponent(component, null);
    }

    /**
     * Adds component only if it is not null
     * 
     * @param <V>
     * @param <T>
     * @param component
     * @return this
     */
    @Override
    public <V, T extends AbstractCompLocValue> AbstractContainerWithLayoutAC addComponentIfNotNull(Component<V, T> component) {
        if (component == null) {
            return this;
        }
        return addComponent(component, null);
    }

    /**
     * @return number of components
     */
    @Override
    public int getComponentsCount() {
        return components.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.vpda.common.comps.ui.Component getComponent(int i) {
        return components.getValue(i);
    }

    /**
     * Gets all components
     * 
     * @return all components
     */
    @Override
    public List<org.vpda.common.comps.ui.Component<?, ?>> getComponents() {
        return components == null ? Collections.<Component<?, ?>>emptyList() : Collections.unmodifiableList(components.values());
    }
    
    

    @Override
    public List<org.vpda.common.comps.ui.Component<?, ?>> getAllMembers() {
        List<org.vpda.common.comps.ui.Component<?, ?>> all = new ArrayList<>();
        for(org.vpda.common.comps.ui.Component<?, ?> comp : components.values()) {
            all.add(comp);
            if(comp instanceof org.vpda.common.comps.ui.Container<?, ?> cont) {
                all.addAll(cont.getAllMembers());
            }
        }
        return all;
    }

    /**
     * Removes all components
     */
    public void removeAllComponents() {
        Collection<String> ids = new ArrayList<String>(components.keys());
        for (String id : ids) {
            removeComponent(id);
        }
    }

    /**
     * @param component
     * @return removed component if contained
     */
    @Override
    public Component removeComponent(org.vpda.common.comps.ui.Component component) {
        return removeComponent(component.getId());
    }

    /**
     * Replace one component with another in this container
     * 
     * @param oldComp
     * @param newComp
     * @return current component
     */
    @Override
    public Component<?, ?> replaceComponent(org.vpda.common.comps.ui.Component<?, ?> oldComp, Component<?, ?> newComp) {
        if (newComp == null || oldComp == null) {
            return null;
        }
        if (!newComp.getId().equals(oldComp.getId())) {
            return null;
        }
        Component current = components.get(oldComp.getId());
        if (current == null) {
            return null;
        }
        if (current != oldComp) {
            return null;
        }
        components.put(newComp.getId(), newComp);
        newComp.setContainerId(getId());
        return current;

    }

    /**
     * @param index
     * @return emoved component at inedx
     */
    @Override
    public org.vpda.common.comps.ui.Component removeComponent(int index) {
        org.vpda.common.comps.ui.Component c = components.remove(index);
        ComponentLayoutConstraint constraint = contraints.remove(c.getId());
        containerLayout.componentRemoved(c, constraint);
        c.setContainerId(null);
        return c;
    }

    /**
     * @param id
     * @return removed component
     */
    @Override
    public org.vpda.common.comps.ui.Component removeComponent(String id) {
        org.vpda.common.comps.ui.Component c = components.remove(id);
        if (c == null) {
            return null;
        }
        contraints.remove(c.getId());
        c.setContainerId(null);
        return c;
    }

    /**
     * @param comp
     * @return constraint of component
     */
    public ComponentLayoutConstraint getComponentLayoutConstraint(org.vpda.common.comps.ui.Component comp) {
        return contraints.get(comp.getId());
    }

    /**
     * @param compId
     * @return constaint of component
     */
    public ComponentLayoutConstraint getComponentLayoutConstraint(String compId) {
        return contraints.get(compId);
    }

    @Override
    public void adjustFromLocValue(AbstractCompLocValue locValue) {
        if (locValue == null) {
            return;
        }
        setTooltip(locValue.getTooltip());
    }

    @Override
    public AbstractCompLocValue createLocValue() {
        throw new UnsupportedOperationException("createLocValue not supported for " + getClass());
    }

    @Override
    public Class<AbstractCompLocValue> getLocValueClass() {
        return AbstractCompLocValue.class;
    }

    @Override
    public void localize(LocalizationService localizationService, TenementalContext context) {
        if (components != null) {
            for (org.vpda.common.comps.ui.Component ac : components.values()) {
                ac.localize(localizationService, context);
            }
        }
    }

    @Override
    public List<org.vpda.common.comps.ui.Component> getMembers() {
        return Collections.unmodifiableList(components.values());
    }

    @Override
    public Map<String, ? extends org.vpda.common.comps.ui.Component> getMembersMapping() {
        return Collections.unmodifiableMap(components);
    }

    @Override
    public Component getMember(String id) {
        Component<?, ?> comp = components.get(id);
        if (comp == null) {
            comp = components.get(ComponentUtils.getComponentId(getId(), id));
        }
        return comp;
    }

    @Override
    public List<String> getMembersLocalIds() {
        List<String> ids = new ArrayList<String>(components.size());
        for (org.vpda.common.comps.ui.Component c : components.values()) {
            ids.add(c.getLocalId());
        }
        return ids;
    }

    @Override
    public List<String> getMembersIds() {
        return Collections.unmodifiableList(components.keys());
    }

}
