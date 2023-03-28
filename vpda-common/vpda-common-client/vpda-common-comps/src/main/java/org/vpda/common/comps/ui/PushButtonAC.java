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
import org.vpda.common.comps.loc.PushButtonLocValue;
import org.vpda.common.context.localization.LocKey;

/**
 * Holder for button data
 * 
 * @author kitko
 *
 */
public final class PushButtonAC extends AbstractButtonAC<PushButtonLocValue> {
    private static final long serialVersionUID = 1392053381491490871L;

    /**
     * Creates PushButtonViewProviderComponentImpl with id
     * 
     * @param id
     */
    public PushButtonAC(String id) {
        this(id, id);
    }

    /** Creates a button */
    public PushButtonAC() {
    }

    /**
     * Creates PushButtonViewProviderComponentImpl with id of key.getKey and set
     * localization key
     * 
     * @param key
     */
    public PushButtonAC(LocKey key) {
        this(key.getKey(), key.getKey());
        this.locKey = key;
    }

    /**
     * @param id
     * @param key
     * @param command
     */
    public PushButtonAC(String id, LocKey key, Command command) {
        this(id, key.getKey());
        this.locKey = key;
        this.command = command;
    }

    /**
     * Creates button with label
     * 
     * @param id
     * @param label
     */
    public PushButtonAC(String id, String label) {
        this(id, label, null);
    }

    /**
     * Creates button with label and command
     * 
     * @param id
     * @param label
     * @param command
     */
    public PushButtonAC(String id, String label, Command command) {
        this(id, new PushButtonLocValue(label), command);
    }

    /**
     * @param id
     * @param locValue
     * @param command
     */
    public PushButtonAC(String id, PushButtonLocValue locValue, Command command) {
        super(id);
        if (locValue == null) {
            locValue = new PushButtonLocValue(getId());
        }
        adjustFromLocValue(locValue);
        this.command = command;
    }

    @Override
    public PushButtonLocValue createLocValue() {
        PushButtonLocValue locValue = new PushButtonLocValue(getLabel(), getIconValue(), getTooltip());
        return locValue;
    }

    @Override
    public Class<PushButtonLocValue> getLocValueClass() {
        return PushButtonLocValue.class;
    }

}
