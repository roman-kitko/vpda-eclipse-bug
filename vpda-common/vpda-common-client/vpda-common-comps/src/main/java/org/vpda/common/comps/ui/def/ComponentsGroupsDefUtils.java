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
package org.vpda.common.comps.ui.def;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.vpda.common.comps.annotations.ComponentsGroupInfo;
import org.vpda.common.comps.ui.AbstractFieldAC;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ModifiableContainer;
import org.vpda.common.types.BackendObjectSourceAware;
import org.vpda.internal.common.util.ClassUtil;

/** Utilities for ComponentsGroupsDef */
public final class ComponentsGroupsDefUtils {
    private ComponentsGroupsDefUtils() {
    }

    /** Always true predicate */
    public static Predicate<AbstractFieldAC> TRUE_FIELD_PREDICATE = (field) -> {
        return true;
    };

    /** Always true predicate */
    public static Predicate<Component> TRUE_COMPONENT_PREDICATE = (comp) -> {
        return true;
    };

    /**
     * Sets enabled to component
     * 
     * @param def
     * @param enabled
     */
    public static void setEnabledToCompsDef(ComponentsGroupsDef def, boolean enabled) {
        setEnabledToCompsDef(def, enabled, TRUE_COMPONENT_PREDICATE);
    }

    /**
     * Sets all components from def excluding ids to enabled/disabled
     * 
     * @param def
     * @param enabled
     * @param excludedComps
     */
    public static void setEnabledToCompsDef(ComponentsGroupsDef def, boolean enabled, Component... excludedComps) {
        Predicate<Component> predicate = (field) -> {
            for (Component excluded : excludedComps) {
                if (excluded == field) {
                    return false;
                }
            }
            return true;
        };
        setEnabledToCompsDef(def, enabled, predicate);
    }

    /**
     * Set enabled flags to components that pass predicate test
     * 
     * @param def
     * @param enabled
     * @param predicate
     */
    public static void setEnabledToCompsDef(ComponentsGroupsDef def, boolean enabled, Predicate<Component> predicate) {
        for (Component comp : def.getComponents()) {
            if (predicate.test(comp)) {
                comp.setEnabled(enabled);
            }
        }
    }

    /**
     * Sets editable to all {@link AbstractFieldAC} from definition
     * 
     * @param def
     * @param editable
     */
    public static void setEditableToCompsDef(ComponentsGroupsDef def, boolean editable) {
        for (Component comp : def.getComponents()) {
            if (comp instanceof AbstractFieldAC) {
                ((AbstractFieldAC) comp).setEditable(editable);
            }
        }
    }

    /**
     * Sets fields in group editable/not editable
     * 
     * @param group
     * @param editable
     */
    public static void setEditableToCompsGroup(ComponentsGroup group, boolean editable) {
        for (Component comp : group.getComponents()) {
            if (comp instanceof AbstractFieldAC) {
                ((AbstractFieldAC) comp).setEditable(editable);
            }
        }
    }

    /**
     * Set editable flag to instance fields with predicate as filter
     * 
     * @param instance
     * @param editable
     * @param predicate
     */
    public static void setEditableToInstanceFields(Object instance, boolean editable, Predicate<AbstractFieldAC> predicate) {
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            try {
                Object comp = f.get(instance);
                if (comp instanceof AbstractFieldAC && predicate.test((AbstractFieldAC) comp)) {
                    ((AbstractFieldAC) comp).setEditable(editable);
                }
                else if (comp instanceof ComponentsGroupSelfConverter) {
                    setEditableToInstanceFields(comp, editable);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Apply some operation on components of instance
     * 
     * @param instance
     * @param predicate
     * @param consumer
     */
    public static void doOperationOnInstanceComponents(Object instance, Predicate<Component> predicate, Consumer<Component> consumer) {
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            try {
                Object comp = f.get(instance);
                if (comp instanceof Component && predicate.test((Component) comp)) {
                    consumer.accept((Component) comp);
                }
                else if (comp instanceof ComponentsGroupSelfConverter) {
                    doOperationOnInstanceComponents(comp, predicate, consumer);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets editable flag to fields of instance
     * 
     * @param instance
     * @param editable
     */
    public static void setEditableToInstanceFields(Object instance, boolean editable) {
        setEditableToInstanceFields(instance, editable, TRUE_FIELD_PREDICATE);
    }

    /**
     * Will set editable flag to instance fields excluded of some fields
     * 
     * @param instance
     * @param editable
     * @param excludededComps
     */
    public static void setEditableToInstanceFields(Object instance, boolean editable, AbstractFieldAC... excludededComps) {
        Predicate<AbstractFieldAC> predicate = (field) -> {
            for (AbstractFieldAC excluded : excludededComps) {
                if (excluded == field) {
                    return false;
                }
            }
            return true;
        };
        setEditableToInstanceFields(instance, editable, predicate);
    }

    /**
     * Converts fields values in instance from group
     * 
     * @param instance
     * @param group
     * @return instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertInstanceFromGroup(T instance, ComponentsGroup group) {
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            try {
                Object value = f.get(instance);
                if (value instanceof Component) {
                    Component comp = (Component) value;
                    Component comp2 = group.getComponent(comp.getId());
                    if (comp2 != null) {
                        comp.assignValues(comp2);
                    }

                }
                else {
                    ComponentsGroup innerGroup = group.getGroup(f.getName());
                    if (innerGroup != null) {
                        if (value instanceof ComponentsGroupSelfConverter) {
                            ComponentsGroupSelfConverter selfConverter = (ComponentsGroupSelfConverter) value;
                            selfConverter.convertFromGroup(innerGroup);
                        }
                        else {
                            value = convertInstanceFromGroup(value, innerGroup);
                            f.set(instance, value);
                        }
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (instance instanceof FetchesCompletedValuesAware) {
            ((FetchesCompletedValuesAware) instance).getFetchesCompletedValues().putSameFetches(group.getFetchesCompletedValues());
        }
        if (instance instanceof AutoCompletedValuesAware) {
            ((AutoCompletedValuesAware) instance).getAutoCompletedValues().putSameValues(group.getAutoCompletedValues());
        }
        if (instance instanceof BackendObjectSourceAware) {
            ((BackendObjectSourceAware) instance).setBackendObjectSource(group.getBackendObjectSource());
        }
        return instance;

    }

    /**
     * Converts values in group from fields
     * 
     * @param instance
     * @param group
     * @return group
     */
    @SuppressWarnings("unchecked")
    public static ComponentsGroup convertInstanceToGroup(Object instance, ComponentsGroup group) {
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            try {
                Object value = f.get(instance);
                if (value instanceof Component) {
                    Component comp = (Component) value;
                    Component comp2 = group.getComponent(comp.getId());
                    if (comp2 != null) {
                        comp2.assignValues(comp);
                    }

                }
                else {
                    ComponentsGroup innerGroup = group.getGroup(f.getName());
                    if (innerGroup != null) {
                        if (value instanceof ComponentsGroupSelfConverter) {
                            ComponentsGroupSelfConverter selfConverter = (ComponentsGroupSelfConverter) value;
                            selfConverter.convertToGroup(innerGroup);
                        }
                        else {
                            convertInstanceToGroup(value, innerGroup);
                        }
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (instance instanceof FetchesCompletedValuesAware) {
            group.getFetchesCompletedValues().putSameFetches(((FetchesCompletedValuesAware) instance).getFetchesCompletedValues());
        }
        if (instance instanceof AutoCompletedValuesAware) {
            group.getAutoCompletedValues().putSameValues(((AutoCompletedValuesAware) instance).getAutoCompletedValues());
        }
        if (instance instanceof BackendObjectSourceAware) {
            group.setBackendObjectSource(((BackendObjectSourceAware) instance).getBackendObjectSource());
        }
        return group;
    }

    /**
     * Converts fields values in instance from def
     * 
     * @param instance
     * @param def
     * @return instance
     */
    public static <T> T convertInstanceFromDef(T instance, ComponentsGroupsDef def) {
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            try {
                Object value = f.get(instance);
                if (value instanceof ComponentsGroupSelfConverter) {
                    ComponentsGroupSelfConverter converter = (ComponentsGroupSelfConverter) value;
                    ComponentsGroup group = def.getGroup(f.getName());
                    if (group != null) {
                        converter.convertFromGroup(group);
                    }
                }
                else if (value != null && value.getClass().isAnnotationPresent(ComponentsGroupInfo.class)) {
                    ComponentsGroup group = def.getGroup(f.getName());
                    if (group != null) {
                        ComponentsGroupsDefUtils.convertInstanceFromGroup(value, group);
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (instance instanceof FetchesCompletedValuesAware) {
            ((FetchesCompletedValuesAware) instance).getFetchesCompletedValues().putSameFetches(def.getFetchesCompletedValues());
        }
        if (instance instanceof AutoCompletedValuesAware) {
            ((AutoCompletedValuesAware) instance).getAutoCompletedValues().putSameValues(def.getAutoCompletedValues());
        }
        return instance;

    }

    /**
     * Converts fields values in instance to def
     * 
     * @param instance
     * @param def
     * @return def
     */
    public static ComponentsGroupsDef convertInstanceToDef(Object instance, ComponentsGroupsDef def) {
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            try {
                Object value = f.get(instance);
                if (value instanceof ComponentsGroupSelfConverter) {
                    ComponentsGroupSelfConverter converter = (ComponentsGroupSelfConverter) value;
                    ComponentsGroup group = def.getGroup(f.getName());
                    if (group != null) {
                        group = converter.convertToGroup(group);
                        if (value instanceof ComponentsGroup) {
                            f.set(instance, group);
                        }
                    }

                }
                else if (value != null && value.getClass().isAnnotationPresent(ComponentsGroupInfo.class)) {
                    ComponentsGroup group = def.getGroup(f.getName());
                    if (group != null) {
                        group = ComponentsGroupsDefUtils.convertInstanceToGroup(value, group);
                        if (value instanceof ComponentsGroup) {
                            f.set(instance, group);
                        }
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (instance instanceof FetchesCompletedValuesAware) {
            def.getFetchesCompletedValues().putSameFetches(((FetchesCompletedValuesAware) instance).getFetchesCompletedValues());
        }
        if (instance instanceof AutoCompletedValuesAware) {
            def.getAutoCompletedValues().putSameValues(((AutoCompletedValuesAware) instance).getAutoCompletedValues());
        }

        return def;

    }

    /**
     * Replaces the components in container by components from definition
     * 
     * @param container
     * @param def
     * @return container
     */
    @SuppressWarnings("unchecked")
    public static <C extends ModifiableContainer> C replaceComponentsInContainerByDef(C container, ComponentsGroupsDef def) {
        List<org.vpda.common.comps.ui.Component<?, ?>> components = new ArrayList<>(container.getComponents());
        for (org.vpda.common.comps.ui.Component comp : components) {
            if (comp instanceof ModifiableContainer) {
                // replace children
                replaceComponentsInContainerByDef((ModifiableContainer) comp, def);
                Component comp2 = def.getComponent(comp.getId());
                if (comp2 != null) {
                    container.replaceComponent(comp, comp2);
                }
                else {
                    // container.removeComponent(comp.getId());
                }
            }
            else {
                Component comp2 = def.getComponent(comp.getId());
                if (comp2 != null) {
                    container.replaceComponent(comp, comp2);
                }
                else {
                    // container.removeComponent(comp.getId());
                }
            }
        }
        return container;
    }

    /**
     * @param id
     * @param instance
     * @return component with id
     */
    public static Component<?, ?> findComponentByIdInInstance(String id, Object instance) {
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            try {
                Object comp = f.get(instance);
                if (comp instanceof Component) {
                    Component ac = (Component) comp;
                    if (id.equals(ac.getId())) {
                        return ac;
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
