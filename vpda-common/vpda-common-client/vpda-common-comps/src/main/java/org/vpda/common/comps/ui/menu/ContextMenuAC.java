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

import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Context Menu
 * 
 * @author kitko
 *
 */
public final class ContextMenuAC extends AbstractComponent<Void, AbstractCompLocValue> {

    private MenuAC menu;

    /** */
    public ContextMenuAC() {
    }

    /**
     * Creates the menu bar
     * 
     * @param localId
     */
    public ContextMenuAC(String localId) {
        super(localId);
    }

    /**
     * @param localId
     * @param menu
     */
    public ContextMenuAC(String localId, MenuAC menu) {
        super(localId);
        setMenu(menu);
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
    public ContextMenuAC setMenu(MenuAC menu) {
        this.menu = menu;
        menu.setParentMenuComponentId(this.getId());
        return this;
    }

    /**
     * @return menu
     */
    public MenuAC getMenu() {
        return menu;
    }

    @Override
    public void localize(LocalizationService localizationService, TenementalContext context) {
        super.localize(localizationService, context);
        if (menu != null) {
            menu.localize(localizationService, context);
        }
    }

}
