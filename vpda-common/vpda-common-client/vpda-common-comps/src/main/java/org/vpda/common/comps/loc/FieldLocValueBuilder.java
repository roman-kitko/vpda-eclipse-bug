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
 * Localization data builder for fields
 * 
 * @author kitko
 *
 */
public final class FieldLocValueBuilder implements LocValueBuilder<FieldLocValue> {
    private static final long serialVersionUID = 3336587960353312671L;
    private static FieldLocValueBuilder instance = new FieldLocValueBuilder();

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        return locService.localizeString(locKey, context) != null;
    }

    @Override
    public FieldLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        FieldLocValue.Builder fieldLocValue = new FieldLocValue.Builder();
        // 1 Try tooltip
        LocKey tooltipKey = locKey.createChildKey("tooltip");
        String tooltip = locService.localizeMessage(tooltipKey, context);
        fieldLocValue.setTooltip(tooltip);
        return fieldLocValue.build();
    }

    /**
     * Singleton pattern instance creator
     * 
     * @return instance
     */
    protected static FieldLocValueBuilder getInstance() {
        return instance;
    }

    private FieldLocValueBuilder() {
    }

    @Override
    public Class<FieldLocValue> getLocValueClass() {
        return FieldLocValue.class;
    }

    /**
     * Factory
     * 
     * @author kitko
     *
     */
    public static final class FieldLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<FieldLocValue, FieldLocValueBuilder>, Serializable {
        private static final long serialVersionUID = 937613379626778073L;

        @Override
        public FieldLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<FieldLocValueBuilder> getBuilderClass() {
            return FieldLocValueBuilder.class;
        }

        @Override
        public Class<FieldLocValue> getLocValueClass() {
            return FieldLocValue.class;
        }
    }

}
