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
 * Localization data builder for listBox
 * 
 * @author kitko
 *
 */
public final class ListBoxLocValueBuilder implements LocValueBuilder<ListLocValue> {
    private static final long serialVersionUID = 3336587960353312671L;
    private static ListBoxLocValueBuilder instance = new ListBoxLocValueBuilder();

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        return locService.localizeString(locKey, context) != null;
    }

    @Override
    public ListLocValue localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        ListLocValue.Builder listBoxLocValue = new ListLocValue.Builder();
        // 1 Try tooltip
        LocKey tooltipKey = locKey.createChildKey("tooltip");
        String tooltip = locService.localizeMessage(tooltipKey, context);
        listBoxLocValue.setTooltip(tooltip);
        return listBoxLocValue.build();
    }

    /**
     * Singleton pattern instance creator
     * 
     * @return instance
     */
    protected static ListBoxLocValueBuilder getInstance() {
        return instance;
    }

    private ListBoxLocValueBuilder() {
    }

    /**
     * Factory
     * 
     * @author kitko
     *
     */
    public static final class ListBoxLocValueBuilderFactory implements LocValueBuilderFactory.OneLocValueBuilderFactory<ListLocValue, ListBoxLocValueBuilder>, Serializable {
        private static final long serialVersionUID = 3052752681564509712L;

        @Override
        public ListBoxLocValueBuilder createBuilder() {
            return getInstance();
        }

        @Override
        public Class<ListBoxLocValueBuilder> getBuilderClass() {
            return ListBoxLocValueBuilder.class;
        }

        @Override
        public Class<ListLocValue> getLocValueClass() {
            return ListLocValue.class;
        }
    }

    @Override
    public Class<ListLocValue> getLocValueClass() {
        return ListLocValue.class;
    }

}
