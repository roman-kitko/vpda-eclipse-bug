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
package org.vpda.common.comps.ui;

import org.vpda.common.comps.loc.IconLocValue;
import org.vpda.common.comps.loc.LabelLocValue;
import org.vpda.common.context.localization.LocKey;

/**
 * LabelViewProviderComponent holds label data.
 * 
 * @author rki
 *
 */
public final class LabelAC extends AbstractComponent<String, LabelLocValue> {
    private static final long serialVersionUID = -2220576033071140568L;
    private IconLocValue iconLocValue;

    /** */
    public LabelAC() {
    }

    /**
     * Creates LabelViewProviderComponent with id
     * 
     * @param id
     */
    public LabelAC(String id) {
        this(id, id);
    }

    /**
     * Creates LabelViewProviderComponent with localization key
     * 
     * @param locKey
     */
    public LabelAC(LocKey locKey) {
        this(locKey.getKey(), locKey.getKey());
        this.locKey = locKey;
    }

    /**
     * Creates LabelViewProviderComponent with id and label
     * 
     * @param id
     * @param label
     */
    public LabelAC(String id, String label) {
        this(id, new LabelLocValue(label));
    }

    /**
     * Creates LabelViewProviderComponent with id and locValue
     * 
     * @param id
     * @param locValue
     */
    public LabelAC(String id, LabelLocValue locValue) {
        super(id);
        if (locValue == null) {
            locValue = new LabelLocValue(getId());
        }
        adjustFromLocValue(locValue);
        setValue(locValue.getLabel());
    }

    /**
     * @return icon value
     */
    public IconLocValue getIconValue() {
        return iconLocValue;
    }

    /**
     * @return label
     */
    public String getLabel() {
        return value;
    }

    @Override
    public String getCaption() {
        return value;
    }

    @Override
    public void setCaption(String caption) {
        this.value = caption;
    }

    @Override
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Sets icon
     * 
     * @param icon
     */
    public void setIconValue(IconLocValue icon) {
        this.iconLocValue = icon;
    }

    /**
     * @param label
     */
    public void setLabel(String label) {
        this.value = label;
    }

    @Override
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public void adjustFromLocValue(LabelLocValue locValue) {
        if (locValue == null) {
            return;
        }
        setLabel(locValue.getLabel());
        setTooltip(locValue.getTooltip());
        setIconValue(locValue.getIconValue());
    }

    @Override
    public void assignValues(org.vpda.common.comps.ui.Component<String, LabelLocValue> c) {
        super.assignValues(c);
        LabelAC label = (LabelAC) c;
        this.value = label.getLabel();
        this.iconLocValue = label.getIconValue();
    }

    @Override
    public LabelLocValue createLocValue() {
        LabelLocValue l = new LabelLocValue(value, iconLocValue, tooltip);
        return l;
    }

    @Override
    public Class<LabelLocValue> getLocValueClass() {
        return LabelLocValue.class;
    }

}
