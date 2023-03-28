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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vpda.common.comps.loc.LabelLocValue;
import org.vpda.common.comps.loc.MultiOptionLocValue;
import org.vpda.common.comps.loc.ToggleButtonLocValue;

/**
 * Multi option components group
 * 
 * @author kitko
 *
 */
public final class MultiOptionAC extends AbstractComponent<Map<String, ToggleButtonAC>, MultiOptionLocValue> {
    private static final long serialVersionUID = 5636271496269381789L;

    private LabelLocValue title;
    private boolean multiSelect = false;
    private ACOrientation orientation = ACOrientation.VERTICAL;

    /**
     * Creates multi option component
     * 
     * @param id
     */
    public MultiOptionAC(String id) {
        super(id);
        value = new HashMap<String, ToggleButtonAC>();
    }

    /**
     * Creates options
     * 
     * @param id
     * @param buttons
     */
    public MultiOptionAC(String id, List<ToggleButtonAC> buttons) {
        super(id);
        value = new HashMap<String, ToggleButtonAC>();
        for (ToggleButtonAC button : buttons) {
            value.put(button.getId(), button);
        }
    }

    /**
     * Set value to button
     * 
     * @param id
     * @param b
     */
    public void setOptionValue(String id, Boolean b) {
        ToggleButtonAC button = value.get(id);
        if (button != null) {
            button.setValue(b);
        }
    }

    /**
     * 
     * @param id
     * @return option single value
     */
    public Boolean getOptionValue(String id) {
        ToggleButtonAC button = value.get(id);
        return button != null ? button.getValue() : null;
    }

    /**
     * Set one option
     * 
     * @param btn
     */
    public void setOption(ToggleButtonAC btn) {
        value.put(btn.getId(), btn);
    }

    @Override
    public Class<MultiOptionLocValue> getLocValueClass() {
        return MultiOptionLocValue.class;
    }

    @Override
    protected void adjustFromLocValue(MultiOptionLocValue locValue) {
        this.title = locValue.getTitle();
        for (Map.Entry<String, ToggleButtonLocValue> entry : locValue.getItems().entrySet()) {
            ToggleButtonAC toggleButton = new ToggleButtonAC(entry.getKey());
            toggleButton.adjustFromLocValue(entry.getValue());
            this.value.put(entry.getKey(), toggleButton);
        }
    }

    @Override
    protected MultiOptionLocValue createLocValue() {
        MultiOptionLocValue.Builder builder = new MultiOptionLocValue.Builder();
        builder.setTitle(title);
        if (value != null) {
            for (Map.Entry<String, ToggleButtonAC> entry : value.entrySet()) {
                ToggleButtonAC button = entry.getValue();
                ToggleButtonLocValue butttonLocValue = button.createLocValue();
                builder.addItem(entry.getKey(), butttonLocValue);
            }
        }
        return builder.build();
    }

    /**
     * @return the title
     */
    public LabelLocValue getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(LabelLocValue title) {
        this.title = title;
    }

    /**
     * @return the multiSelect
     */
    public boolean isMultiSelect() {
        return multiSelect;
    }

    /**
     * @param multiSelect the multiSelect to set
     */
    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    /**
     * @return the orientation
     */
    public ACOrientation getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(ACOrientation orientation) {
        this.orientation = orientation;
    }

}
