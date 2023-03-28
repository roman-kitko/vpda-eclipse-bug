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

/** Localization data builder for {@link MultiOptionLocValue} */
public final class MultiOptionLocValueBuilder implements LocValueBuilder<MultiOptionLocValue> {
    private static final long serialVersionUID = -6318643712232598698L;
    private static final MultiOptionLocValueBuilder instance = new MultiOptionLocValueBuilder();

    private MultiOptionLocValueBuilder() {
    }

    /**
     * Create new instance
     * 
     * @return instance of builder
     */
    protected static MultiOptionLocValueBuilder getInstance() {
        return instance;
    }

    @Override
    public MultiOptionLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        MultiOptionLocValue.Builder locValueBuilder = new MultiOptionLocValue.Builder();
        LabelLocValueBuilder labelLocValueBuilder = locValueBuilderFactory.createBuilderByBuilderClass(LabelLocValueBuilder.class);
        ToggleButtonLocValueBuilder toggleButtonLocValueBuilder = locValueBuilderFactory.createBuilderByBuilderClass(ToggleButtonLocValueBuilder.class);
        LocKey titleKey = locKey.createChildKey("title");
        LabelLocValue titleValue = locService.localizeData(titleKey, context, labelLocValueBuilder, null, params);
        locValueBuilder.setTitle(titleValue);
        LocKey itemsKey = locKey.createChildKey("items");

        for (int i = 0;; i++) {
            String key = "item" + i;
            LocKey itemKey = itemsKey.createChildKey(key);
            String label = locService.localizeString(itemKey, context);
            if (label == null) {
                break;
            }
            String realKey = locService.localizeString(itemKey.createChildKey("key"), context);
            ToggleButtonLocValue toggleButtonLocValue = toggleButtonLocValueBuilder.localizeData(locValueBuilderFactory, locService, locKey, context, params);
            locValueBuilder.addItem(realKey != null ? realKey : key, toggleButtonLocValue);
        }
        /**
         * myOption.title = My options myOption.title.tooltip = My option tooltip
         * myOption.items.item0=My item0 myOption.items.item0.tooltip=My item0 tooltip
         * myOption.items.item1=My item1 myOption.items.item1.key=xxx
         * myOption.items.item0.tooltip=My item0 tooltip
         */
        return locValueBuilder.build();
    }

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        return locService.localizeString(locKey, context) != null;
    }

    @Override
    public Class<MultiOptionLocValue> getLocValueClass() {
        return MultiOptionLocValue.class;
    }

    /**
     * Factory
     * 
     * @author kitko
     *
     */
    public static final class MultiOptionLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<MultiOptionLocValue, MultiOptionLocValueBuilder>, Serializable {

        private static final long serialVersionUID = 7517612467636778594L;

        @Override
        public MultiOptionLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<MultiOptionLocValueBuilder> getBuilderClass() {
            return MultiOptionLocValueBuilder.class;
        }

        @Override
        public Class<MultiOptionLocValue> getLocValueClass() {
            return MultiOptionLocValue.class;
        }

    }

}
