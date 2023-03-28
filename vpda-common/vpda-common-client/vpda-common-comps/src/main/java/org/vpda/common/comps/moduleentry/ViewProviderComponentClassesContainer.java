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
package org.vpda.common.comps.moduleentry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.CheckBoxAC;
import org.vpda.common.comps.ui.ComboBoxAC;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.DateFieldAC;
import org.vpda.common.comps.ui.DecimalFieldAC;
import org.vpda.common.comps.ui.IntegerFieldAC;
import org.vpda.common.comps.ui.LabelAC;
import org.vpda.common.comps.ui.ListAC;
import org.vpda.common.comps.ui.ListFieldAC;
import org.vpda.common.comps.ui.MultiOptionAC;
import org.vpda.common.comps.ui.PasswordFieldAC;
import org.vpda.common.comps.ui.PushButtonAC;
import org.vpda.common.comps.ui.ScrollPaneAC;
import org.vpda.common.comps.ui.SeparatorAC;
import org.vpda.common.comps.ui.TabbedPaneAC;
import org.vpda.common.comps.ui.TextAreaAC;
import org.vpda.common.comps.ui.TextFieldAC;
import org.vpda.common.comps.ui.ToggleButtonAC;
import org.vpda.common.comps.ui.ToolBarAC;
import org.vpda.common.comps.ui.menu.MenuAC;
import org.vpda.common.comps.ui.menu.MenuBarAC;
import org.vpda.common.comps.ui.menu.MenuItemAC;

/**
 * This class will statically hold all classes of ViewProviderComponents
 * 
 * @author kitko
 *
 */
public final class ViewProviderComponentClassesContainer {
    private ViewProviderComponentClassesContainer() {
    }

    private static Collection<Class<? extends AbstractComponent>> classes;
    static {
        classes = new HashSet<Class<? extends AbstractComponent>>();
        classes.add(DateFieldAC.class);
        classes.add(LabelAC.class);
        classes.add(IntegerFieldAC.class);
        classes.add(PushButtonAC.class);
        classes.add(ToggleButtonAC.class);
        classes.add(CheckBoxAC.class);
        classes.add(MultiOptionAC.class);
        classes.add(DecimalFieldAC.class);
        classes.add(TextFieldAC.class);
        classes.add(TextAreaAC.class);
        classes.add(ContainerAC.class);
        classes.add(ScrollPaneAC.class);
        classes.add(PasswordFieldAC.class);
        classes.add(TabbedPaneAC.class);
        classes.add(MenuBarAC.class);
        classes.add(MenuAC.class);
        classes.add(MenuItemAC.class);
        classes.add(ToolBarAC.class);
        classes.add(ComboBoxAC.class);
        classes.add(ListAC.class);
        classes.add(ListFieldAC.class);
        classes.add(SeparatorAC.class);
    }

    /**
     * Register component class
     * 
     * @param clazz
     */
    public static void registerComponentClass(Class<? extends AbstractComponent> clazz) {
        classes.add(clazz);
    }

    /**
     * @return all registered classes
     */
    public static Collection<Class<? extends AbstractComponent>> getComponentsClasses() {
        return Collections.unmodifiableCollection(classes);
    }

}
