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
package org.vpda.common.comps.ui.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vpda.common.comps.MemberContainer;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.SeparatorAC;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.internal.common.util.CollectionUtil;

/**
 * Menu component
 * 
 * @author kitko
 *
 */
public final class MenuAC extends MenuItemAC implements MemberContainer<org.vpda.common.comps.ui.Component> {
    private static final long serialVersionUID = 3735578889763459431L;
    private List<AbstractComponent> items;

    /** Creates a menu */
    public MenuAC() {

    }

    /**
     * @param key
     */
    public MenuAC(LocKey key) {
        super(key);
    }

    /**
     * @param id
     * @param label
     */
    public MenuAC(String id, String label) {
        super(id, label);
    }

    /**
     * @param id
     */
    public MenuAC(String id) {
        super(id);
    }

    /**
     * Adds item
     * 
     * @param item
     * @return this
     */
    public MenuAC addItem(MenuItemAC item) {
        return addItem((AbstractComponent) item);
    }

    /**
     * Adds item onli if not null
     * 
     * @param item
     * @return this
     */
    public MenuAC addItemIfNotNull(MenuItemAC item) {
        if (item == null) {
            return this;
        }
        return addItem((AbstractComponent) item);
    }

    /**
     * Adds more items
     * 
     * @param items
     * @return this
     */
    public MenuAC addItems(MenuItemAC... items) {
        for (MenuItemAC item : items) {
            addItem(item);
        }
        return this;
    }

    /**
     * Adds item
     * 
     * @param comp
     * @return this
     */
    public MenuAC addItem(AbstractComponent comp) {
        if (items == null) {
            items = new ArrayList<AbstractComponent>(2);
        }
        if (comp instanceof MenuItemAC) {
            ((MenuItemAC) comp).setParentMenuComponentId(getParentMenuComponentId());
        }
        items.add(comp);
        return this;
    }

    /**
     * @return this
     */
    public MenuAC addSeparator() {
        return addItem(new SeparatorAC());
    }

    /**
     * Remove item
     * 
     * @param item
     * @return item or null if not removed
     */
    public MenuItemAC removeItem(MenuItemAC item) {
        if (items == null) {
            return null;
        }
        MenuItemAC res = items.remove(item) ? item : null;
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
    public Component removeItem(String itemId) {
        if (items == null) {
            return null;
        }
        for (AbstractComponent item : items) {
            if (item.getId().equals(itemId)) {
                items.remove(item);
                if (item instanceof MenuItemAC) {
                    ((MenuItemAC) item).setParentMenuComponentId(null);
                }
                return item;
            }
        }
        return null;
    }

    /**
     * @return items of menu
     */
    public List<Component> getItems() {
        return items != null ? Collections.unmodifiableList(items) : Collections.emptyList();
    }
    
    

    @Override
    public List<Component> getAllMembers() {
        if(CollectionUtil.isEmpty(items)) {
            return Collections.emptyList();
        }
        List<Component> all = new ArrayList<>();
        for(AbstractComponent comp : items) {
            all.add(comp);
            if(comp instanceof MenuAC menu) {
                all.addAll(menu.getAllMembers());
            }
        }
        return all;
    }

    /**
     * Removes all items
     * 
     * @return this
     */
    public MenuAC removeAllItems() {
        if (items != null) {
            items.clear();
            items = null;
        }
        return this;
    }

    /**
     * Creates menu
     * 
     * @param id
     * @param locPath
     * @return new Menu
     */
    public static MenuAC create(String id, String locPath) {
        MenuAC menu = new MenuAC(id);
        menu.setLocKey(new LocKey(locPath, id));
        return menu;
    }

    @Override
    public void localize(LocalizationService localizationService, TenementalContext context) {
        super.localize(localizationService, context);
        if (items != null) {
            for (AbstractComponent item : items) {
                item.localize(localizationService, context);
            }
        }
    }

    /**
     * @param menuBarId the menuBarId to set
     */
    @Override
    public void setParentMenuComponentId(String menuBarId) {
        super.setParentMenuComponentId(menuBarId);
        if (items != null) {
            for (Component item : items) {
                if (item instanceof MenuItemAC) {
                    ((MenuItemAC) item).setParentMenuComponentId(menuBarId);
                }
            }
        }
    }

    @Override
    public List<Component> getMembers() {
        return new ArrayList<>(items);
    }

    @Override
    public Map<String, ? extends AbstractComponent> getMembersMapping() {
        Map<String, AbstractComponent> result = new HashMap<String, AbstractComponent>();
        for (AbstractComponent c : items) {
            result.put(c.getId(), c);
        }
        return result;
    }

    @Override
    public Component getMember(String id) {
        for (AbstractComponent c : items) {
            if (id.equals(c.getId())) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<String> getMembersLocalIds() {
        List<String> ids = new ArrayList<String>(items.size());
        for (AbstractComponent c : items) {
            ids.add(c.getLocalId());
        }
        return ids;
    }

    @Override
    public List<String> getMembersIds() {
        List<String> ids = new ArrayList<String>(items.size());
        for (AbstractComponent c : items) {
            ids.add(c.getId());
        }
        return ids;
    }

}
