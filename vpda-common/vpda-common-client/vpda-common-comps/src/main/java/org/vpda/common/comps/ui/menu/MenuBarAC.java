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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Menubar component
 * 
 * @author kitko
 *
 */
public final class MenuBarAC extends AbstractComponent<Void, AbstractCompLocValue> {
    private List<MenuAC> menus;

    /** */
    public MenuBarAC() {
    }

    /**
     * Creates the menu bar
     * 
     * @param localId
     */
    public MenuBarAC(String localId) {
        super(localId);
    }

    private static final long serialVersionUID = -4817355519547857591L;

    @Override
    protected void adjustFromLocValue(AbstractCompLocValue locValue) {
    }

    @Override
    protected AbstractCompLocValue createLocValue() {
        return null;
    }

    @Override
    public Class<AbstractCompLocValue> getLocValueClass() {
        return null;
    }

    /**
     * Adds menu
     * 
     * @param menu
     * @return this
     */
    public MenuBarAC addMenu(MenuAC menu) {
        if (menus == null) {
            menus = new ArrayList<MenuAC>(2);
        }
        menu.setParentMenuComponentId(this.getId());
        menus.add(menu);
        return this;
    }

    /**
     * Remove item
     * 
     * @param item
     * @return item or null if not removed
     */
    public MenuAC removeItem(MenuAC item) {
        if (menus == null) {
            return null;
        }
        MenuAC res = menus.remove(item) ? item : null;
        if (res != null) {
            res.setParentMenuComponentId(null);
        }
        return res;
    }

    /**
     * Remove item by id
     * 
     * @param itemId
     * @return removed item or null if not removed
     */
    public MenuAC removeItem(String itemId) {
        if (menus == null) {
            return null;
        }
        for (MenuAC item : menus) {
            if (item.getId().equals(itemId)) {
                menus.remove(item);
                item.setParentMenuComponentId(null);
                return item;
            }
        }
        return null;
    }

    /**
     * Removes all items
     * 
     * @return this
     */
    public MenuBarAC removeAllItems() {
        if (menus != null) {
            menus.clear();
            menus = null;
        }
        return this;
    }

    /**
     * @return items of menubar
     */
    public List<MenuAC> getItems() {
        return menus != null ? Collections.unmodifiableList(menus) : Collections.<MenuAC>emptyList();
    }

    @Override
    public void localize(LocalizationService localizationService, TenementalContext context) {
        super.localize(localizationService, context);
        if (menus != null) {
            for (MenuAC ac : menus) {
                ac.localize(localizationService, context);
            }
        }
    }

    /**
     * @return menu ids
     */
    public List<String> getItemIds() {
        List<String> ids = new ArrayList<String>();
        for (MenuAC menu : menus) {
            ids.add(menu.getId());
        }
        return ids;
    }

    /**
     * @param id
     * @return item by id or null if not found
     */
    public MenuAC getItem(String id) {
        for (MenuAC item : menus) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

}
