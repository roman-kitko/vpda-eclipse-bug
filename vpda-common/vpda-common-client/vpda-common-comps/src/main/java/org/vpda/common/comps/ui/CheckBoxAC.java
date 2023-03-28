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

import org.vpda.common.command.Command;
import org.vpda.common.comps.loc.ToggleButtonLocValue;

/**
 * @author kitko
 *
 */
public final class CheckBoxAC extends ToggleButtonAC {

    private static final long serialVersionUID = -9164573481524153099L;

    /** */
    public CheckBoxAC() {
    }

    /**
     * @param id
     * @param value
     */
    public CheckBoxAC(String id, ToggleButtonLocValue value) {
        super(id, value, null);
    }

    /**
     * @param id
     * @param locValue
     * @param command
     */
    public CheckBoxAC(String id, ToggleButtonLocValue locValue, Command command) {
        super(id, locValue, command);
    }

    /**
     * Creates CheckBoxViewProviderComponentImpl
     * 
     * @param id
     * @param locValue
     * @param command
     * @param selected
     */
    public CheckBoxAC(String id, ToggleButtonLocValue locValue, Command command, boolean selected) {
        super(id, locValue, command);
        setSelected(selected);
    }

    /**
     * @param id
     * @param label
     * @param selected
     */
    public CheckBoxAC(String id, String label, boolean selected) {
        super(id, label, selected);
    }

    /**
     * Creates CheckBoxAC from toggle button value
     * 
     * @param button
     * @return CheckBoxAC
     */
    public static CheckBoxAC createFromToggleButton(ToggleButtonAC button) {
        if (button instanceof CheckBoxAC) {
            return (CheckBoxAC) button;
        }
        CheckBoxAC chck = new CheckBoxAC();
        chck.setCaption(button.getCaption());
        chck.setCommand(button.getCommand());
        chck.setContainerId(button.getContainerId());
        chck.setDisabledIconValue(button.getDisabledIconValue());
        chck.setEnabled(button.isEnabled());
        chck.setGroupId(button.getGroupId());
        chck.setHorizontalAlignment(button.getHorizontalAlignment());
        chck.setIconValue(button.getIconValue());
        chck.setLabel(button.getLabel());
        chck.setLocalId(button.getLocalId());
        chck.setLocalizer(button.getLocalizer());
        chck.setLocKey(button.getLocKey());
        chck.setMnemonic(button.getMnemonic());
        chck.setSelected(button.isSelected());
        chck.setTooltip(button.getTooltip());
        chck.setValue(button.getValue());
        chck.setVerticalAlignment(button.getVerticalAlignment());
        chck.setVisible(button.isVisible());
        for (String key : button.getPropertiesKeys()) {
            Object propValue = button.getProperty(key);
            chck.setProperty(key, propValue);
        }
        return chck;
    }

}
