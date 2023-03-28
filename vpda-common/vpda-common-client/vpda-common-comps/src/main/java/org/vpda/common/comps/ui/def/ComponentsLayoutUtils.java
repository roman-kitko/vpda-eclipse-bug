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

import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.ContainerGridLayout;
import org.vpda.common.comps.ui.ContainerWithLayout;
import org.vpda.common.comps.ui.LabelAC;
import org.vpda.internal.common.util.ClassUtil;

/**
 * @author kitko
 *
 */
public final class ComponentsLayoutUtils {

    private ComponentsLayoutUtils() {
    }

    /**
     * Adds fields from instance to grid container
     * 
     * @param instance
     * @param container
     * @return container
     */
    @SuppressWarnings("unchecked")
    public static <C extends ContainerWithLayout> C addInstanceToGridContainer(Object instance, C container) {
        // collect components
        List<AbstractComponent> components = new ArrayList<>();
        for (Field f : ClassUtil.getDeclaredAndInheritedFields(instance.getClass())) {
            f.setAccessible(true);
            Object value = null;
            try {
                value = f.get(instance);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (value instanceof AbstractComponent) {
                AbstractComponent comp = (AbstractComponent) value;
                components.add(comp);
            }
        }
        int size = components.size();
        int lastFieldIndex = 0;
        for (int i = 0; i < size; i++) {
            AbstractComponent l = components.get(i);
            if (l instanceof LabelAC) {
                for (int j = lastFieldIndex; j < size; j++) {
                    AbstractComponent f = components.get(j);
                    if (!(f instanceof LabelAC)) {
                        lastFieldIndex = j + 1;
                        container.addComponent(l);
                        container.addComponent(f);
                        break;
                    }
                }
            }

        }
        return container;
    }

    /**
     * Creates grid container and add components from group
     * 
     * @param group
     * @return container
     */
    public static ContainerAC createGridContainer(ComponentsGroup group) {
        return ComponentsLayoutUtils.addGroupToGridContainer(group, new ContainerAC(group.getId(), new ContainerGridLayout(2)));
    }

    /**
     * Creates container from fields from instance
     * 
     * @param instance
     * @return container
     */
    public static ContainerAC createGridContainer(Object instance) {
        return createGridContainer(instance, instance.getClass().getSimpleName());
    }

    /**
     * Creates container from fields from instance with sent localId
     * 
     * @param instance
     * @param localId
     * @return container
     */
    public static ContainerAC createGridContainer(Object instance, String localId) {
        return addInstanceToGridContainer(instance, new ContainerAC(localId, new ContainerGridLayout(2)));
    }

    /**
     * Adds components from group to grid container
     * 
     * @param group
     * @param container
     * @return container
     */
    @SuppressWarnings("unchecked")
    public static <C extends ContainerWithLayout> C addGroupToGridContainer(ComponentsGroup group, C container) {
        List<Component> components = group.getComponents();
        int size = components.size();
        int lastFieldIndex = 0;
        for (int i = 0; i < size; i++) {
            Component l = components.get(i);
            if (l instanceof LabelAC) {
                for (int j = lastFieldIndex; j < size; j++) {
                    Component f = components.get(j);
                    if (!(f instanceof LabelAC)) {
                        lastFieldIndex = j + 1;
                        container.addComponent(l);
                        container.addComponent(f);
                        break;
                    }
                }
            }

        }
        return container;
    }

}
