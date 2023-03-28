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
 * Default impl of Toggle button field
 * 
 * @author kitko
 *
 */
public class ToggleButtonAC extends AbstractButtonAC<ToggleButtonLocValue> {

    /** */
    public ToggleButtonAC() {
    }

    /**
     * Creates button with id only
     * 
     * @param id
     */
    public ToggleButtonAC(String id) {
        super(id);
    }

    /**
     * Creates abstract Toggle button component
     * 
     * @param id
     * @param locValue
     * @param command
     */
    public ToggleButtonAC(String id, ToggleButtonLocValue locValue, Command command) {
        super(id);
        adjustFromLocValue(locValue);
        setCommand(command);
    }

    /**
     * Creates abstract Toggle button component
     * 
     * @param id
     * @param label
     * @param selected
     */
    public ToggleButtonAC(String id, String label, boolean selected) {
        this(id, new ToggleButtonLocValue(label), null);
        setSelected(selected);
    }

    private static final long serialVersionUID = -1136056419162670124L;

    @Override
    public ToggleButtonLocValue createLocValue() {
        ToggleButtonLocValue t = new ToggleButtonLocValue(label, iconLocValue, tooltip);
        ToggleButtonLocValue.Builder b = new ToggleButtonLocValue.Builder();
        b.setValues(t);
        b.setDisabledIcon(disabledIconLocValue);
        return b.build();
    }

    @Override
    public Class<ToggleButtonLocValue> getLocValueClass() {
        return ToggleButtonLocValue.class;
    }

}
