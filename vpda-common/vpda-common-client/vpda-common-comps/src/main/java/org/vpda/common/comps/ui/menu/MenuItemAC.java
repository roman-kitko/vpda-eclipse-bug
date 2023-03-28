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
package org.vpda.common.comps.ui.menu;

import org.vpda.common.command.Command;
import org.vpda.common.comps.loc.MenuItemLocValue;
import org.vpda.common.comps.ui.AbstractButtonAC;
import org.vpda.common.context.localization.LocKey;

/**
 * Menu item component
 * 
 * @author kitko
 *
 */
public class MenuItemAC extends AbstractButtonAC<MenuItemLocValue> {
    private Command command;
    private static final long serialVersionUID = 1392053381491490871L;
    private String accelerator;
    private String parentMenuComponentId;

    /** Creates a menu item */
    public MenuItemAC() {
    }

    /**
     * Creates MenuItemVPC with id
     * 
     * @param id
     */
    public MenuItemAC(String id) {
        this(id, id);
    }

    /**
     * Creates MenuItemVPC with id of key.getKey and set localization key
     * 
     * @param key
     */
    public MenuItemAC(LocKey key) {
        this(key.getKey(), key.getKey());
        this.locKey = key;
    }

    /**
     * Creates button with label
     * 
     * @param id
     * @param label
     */
    public MenuItemAC(String id, String label) {
        this(id, label, null);
    }

    /**
     * Creates MenuItemVPC with label and command
     * 
     * @param id
     * @param label
     * @param command
     */
    public MenuItemAC(String id, String label, Command command) {
        this(id, new MenuItemLocValue(label), command);
    }

    /**
     * @param id
     * 
     * 
     * 
     * @param locValue
     * @param command
     */
    public MenuItemAC(String id, MenuItemLocValue locValue, Command command) {
        super(id);
        if (locValue == null) {
            locValue = new MenuItemLocValue(getId());
        }
        adjustFromLocValue(locValue);
        this.command = command;
    }

    @Override
    public MenuItemAC setCommand(Command command) {
        this.command = command;
        return this;

    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public MenuItemLocValue createLocValue() {
        MenuItemLocValue locValue = new MenuItemLocValue(getLabel(), getIconValue(), getTooltip());
        return locValue;
    }

    @Override
    public Class<MenuItemLocValue> getLocValueClass() {
        return MenuItemLocValue.class;
    }

    /**
     * @return the accelerator
     */
    public String getAccelerator() {
        return accelerator;
    }

    /**
     * @param accelerator the accelerator to set
     * @return this
     */
    public MenuItemAC setAccelerator(String accelerator) {
        this.accelerator = accelerator;
        return this;
    }

    /**
     * Creates item
     * 
     * @param id
     * @param menuCommand
     * @param locPath
     * @return new item
     */
    public static MenuItemAC create(String id, Command menuCommand, String locPath) {
        MenuItemAC item = new MenuItemAC(id);
        item.setCommand(menuCommand);
        item.setLocKey(new LocKey(locPath, id));
        return item;
    }

    /**
     * Creates item
     * 
     * @param menuCommand
     * @param id
     * @param locPath
     * @return new item
     */
    public static MenuItemAC create(Command menuCommand, String id, String locPath) {
        MenuItemAC item = new MenuItemAC(id);
        item.setCommand(menuCommand);
        item.setLocKey(new LocKey(locPath, id));
        return item;
    }

    /**
     * @return the ParentMenuComponentId
     */
    public String getParentMenuComponentId() {
        return parentMenuComponentId;
    }

    /**
     * @param parentMenuComponentId the menuBarId to set
     */
    public void setParentMenuComponentId(String parentMenuComponentId) {
        this.parentMenuComponentId = parentMenuComponentId;
    }

}
