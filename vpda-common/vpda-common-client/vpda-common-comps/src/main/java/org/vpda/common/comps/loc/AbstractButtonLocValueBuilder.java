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

import org.vpda.common.context.ApplContext;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocDataArguments;
import org.vpda.common.service.localization.LocValueBuilder;
import org.vpda.common.service.localization.LocValueBuilderFactory;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Abstract Localization data builder for any buttons
 * 
 * @author kitko
 * @param <T> - kind of buttonLocvalue this builder will create
 *
 */
public abstract class AbstractButtonLocValueBuilder<T extends AbstractButtonLocValue> implements LocValueBuilder<T> {
    private static final long serialVersionUID = 3336587960353312671L;

    @Override
    public boolean containsLocValue(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context) {
        return locService.localizeString(locKey, context) != null;
    }

    @Override
    public T localizeData(LocValueBuilderFactory locValueBuilderFactory, LocalizationService locService, LocKey locKey, TenementalContext context, LocDataArguments params) {
        // Now we should have enough information to localize Button
        AbstractButtonLocValue.Builder<T> abstractButtonBuilder = createButtonBuilder();
        // 1 Try label
        String label = locService.localizeMessage(locKey, context);
        if (label == null) {
            return null;
        }
        abstractButtonBuilder.setLabel(label);
        // 2 Try tooltip
        LocKey tooltipKey = locKey.createChildKey("tooltip");
        String tooltip = locService.localizeMessage(tooltipKey, context);
        abstractButtonBuilder.setTooltip(tooltip);
        // 3 Try icon
        IconLocValueBuilder iconLocValueBuilder = locValueBuilderFactory.createBuilderByBuilderClass(IconLocValueBuilder.class);
        LocKey iconKey = locKey.createChildKey("icon");
        IconLocValue iconValue = locService.localizeData(iconKey, context, iconLocValueBuilder, null, params);
        abstractButtonBuilder.setIcon(iconValue);
        // 4 Try disabled icon
        LocKey disabledIconKey = locKey.createChildKey("disIcon");
        IconLocValue disIconValue = locService.localizeData(disabledIconKey, context, iconLocValueBuilder, null, params);
        abstractButtonBuilder.setDisabledIcon(disIconValue);
        // 5 Try rollover icon
        // try mnemonic
        LocKey mnemonicKey = locKey.createChildKey("mnemonic");
        String mnemonic = locService.localizeMessage(mnemonicKey, context);
        if (mnemonic != null && mnemonic.length() == 1) {
            abstractButtonBuilder.setMnemonic(mnemonic.charAt(0));
        }
        return abstractButtonBuilder.build();
    }

    /**
     * Creates concrete empty instance of ButtonLocValue -
     * (PushButtonLocValue,ToggleButtonLocValue)
     * 
     * @return new instance of button loc value
     */
    protected abstract AbstractButtonLocValue.Builder<T> createButtonBuilder();

    /**
     * Default Constructor
     */
    protected AbstractButtonLocValueBuilder() {
    }

}
