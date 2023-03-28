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
package org.vpda.common.comps.loc;

import java.io.Serializable;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocDataArguments;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Localization data builder for menu items
 * 
 * @author kitko
 *
 */
public final class MenuItemLocValueBuilder extends AbstractButtonLocValueBuilder<MenuItemLocValue> implements LocValueBuilder<MenuItemLocValue> {
    private static final long serialVersionUID = 3336587960353312671L;
    private static MenuItemLocValueBuilder instance = new MenuItemLocValueBuilder();

    /**
     * Singleton pattern instance creator
     * 
     * @return instance
     */
    protected static MenuItemLocValueBuilder getInstance() {
        return instance;
    }

    /**
     * Default constructor
     */
    protected MenuItemLocValueBuilder() {
    }

    @Override
    protected MenuItemLocValue.Builder createButtonBuilder() {
        return new MenuItemLocValue.Builder();
    }

    @Override
    public MenuItemLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        MenuItemLocValue locValue = super.localizeData(locValueBuilderFactory, locService, locKey, context, params);
        if (locValue == null) {
            return null;
        }
        // try accelerator
        LocKey acceleratorKey = locKey.createChildKey("accelerator");
        String accelerator = locService.localizeMessage(acceleratorKey, context);
        if (accelerator != null) {
            MenuItemLocValue.Builder b = new MenuItemLocValue.Builder();
            b.setValues(locValue);
            b.setAccelerator(accelerator);
            locValue = b.build();
        }
        return locValue;
    }

    /**
     * Factory
     * 
     * @author kitko
     *
     */
    public static final class MenuItemLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<MenuItemLocValue, MenuItemLocValueBuilder>, Serializable {
        private static final long serialVersionUID = -5365907235561665713L;

        @Override
        public MenuItemLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<MenuItemLocValueBuilder> getBuilderClass() {
            return MenuItemLocValueBuilder.class;
        }

        @Override
        public Class<MenuItemLocValue> getLocValueClass() {
            return MenuItemLocValue.class;
        }
    }

    @Override
    public Class<MenuItemLocValue> getLocValueClass() {
        return MenuItemLocValue.class;
    }

}
