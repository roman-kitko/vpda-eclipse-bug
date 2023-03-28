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
 * Localization data builder for labels
 * 
 * @author kitko
 *
 */
public final class LabelLocValueBuilder implements LocValueBuilder<LabelLocValue> {
    private static final long serialVersionUID = 3336587960353312671L;
    private static final LabelLocValueBuilder instance = new LabelLocValueBuilder();

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        return locService.localizeString(locKey, context) != null;
    }

    @Override
    public LabelLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        // Now we should have enough information to localize Button
        LabelLocValue.Builder labelLocValue = new LabelLocValue.Builder();
        // 1 Try label
        String label = locService.localizeMessage(locKey, context, locKey.getKey());
        if (label == null) {
            return null;
        }
        labelLocValue.setLabel(label);
        // 2 Try tooltip
        LocKey tooltipKey = locKey.createChildKey("tooltip");
        String tooltip = locService.localizeMessage(tooltipKey, context);
        labelLocValue.setTooltip(tooltip);
        // 3 Try icon
        LocKey iconKey = locKey.createChildKey("icon");
        IconLocValueBuilder iconLocValueBuilder = locValueBuilderFactory.createBuilderByBuilderClass(IconLocValueBuilder.class);
        // In case builder for icons is not registered
        if (iconLocValueBuilder != null) {
            IconLocValue iconValue = locService.localizeData(iconKey, context, iconLocValueBuilder, null, params);
            labelLocValue.setIcon(iconValue);
        }
        // 4 Try disabled icon
        // 5 Try rollover icon
        return labelLocValue.build();
    }

    /**
     * Singleton patter instance creator
     * 
     * @return instance
     */
    protected static LabelLocValueBuilder getInstance() {
        return instance;
    }

    private LabelLocValueBuilder() {
    }

    @Override
    public Class<LabelLocValue> getLocValueClass() {
        return LabelLocValue.class;
    }

    /**
     * Factory
     * 
     * @author kitko
     *
     */
    public static final class LabelLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<LabelLocValue, LabelLocValueBuilder>, Serializable {
        private static final long serialVersionUID = 1668686106162612742L;

        @Override
        public LabelLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<LabelLocValueBuilder> getBuilderClass() {
            return LabelLocValueBuilder.class;
        }

        @Override
        public Class<LabelLocValue> getLocValueClass() {
            return LabelLocValue.class;
        }

    }

}
