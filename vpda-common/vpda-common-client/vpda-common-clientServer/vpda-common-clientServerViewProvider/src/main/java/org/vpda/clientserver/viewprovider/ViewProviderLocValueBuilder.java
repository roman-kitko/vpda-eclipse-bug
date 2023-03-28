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
package org.vpda.clientserver.viewprovider;

import java.io.Serializable;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocDataArguments;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocalizationService;

/**
 * LocBuilder for ViewProviderLocValue
 * 
 * @author kitko
 *
 */
public final class ViewProviderLocValueBuilder implements LocValueBuilder<ViewProviderLocValue> {
    private static final long serialVersionUID = -2616326368050632170L;
    private static ViewProviderLocValueBuilder instance = new ViewProviderLocValueBuilder();

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        LocKey titleKey = new LocKey(locKey.getPath(), locKey.getKey() + ".title");
        return locService.localizeString(titleKey, context) != null;
    }

    @Override
    public ViewProviderLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        LocKey titleKey = locKey.createChildKey("title");
        String title = locService.localizeMessage(titleKey, context);
        LocKey descriptionKey = locKey.createChildKey("description");
        String description = locService.localizeMessage(descriptionKey, context);
        LocKey tooltipKey = locKey.createChildKey("tooltip");
        String tooltip = locService.localizeMessage(tooltipKey, context);
        return new ViewProviderLocValue(title, tooltip, description);
    }

    /**
     * Singleton pattern instance creator
     * 
     * @return instance
     */
    protected static ViewProviderLocValueBuilder getInstance() {
        return instance;
    }

    /**
     * Factory for {@link ViewProviderLocValueBuilder}
     * 
     * @author kitko
     *
     */
    public static class ViewProviderLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<ViewProviderLocValue, ViewProviderLocValueBuilder>, Serializable {
        private static final long serialVersionUID = -5106277708567452312L;

        @Override
        public ViewProviderLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<ViewProviderLocValueBuilder> getBuilderClass() {
            return ViewProviderLocValueBuilder.class;
        }

        @Override
        public Class<ViewProviderLocValue> getLocValueClass() {
            return ViewProviderLocValue.class;
        }
    }

    @Override
    public Class<ViewProviderLocValue> getLocValueClass() {
        return ViewProviderLocValue.class;
    }

}
