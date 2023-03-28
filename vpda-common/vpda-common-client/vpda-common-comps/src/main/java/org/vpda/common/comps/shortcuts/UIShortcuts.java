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
package org.vpda.common.comps.shortcuts;

import java.io.Serializable;

import org.vpda.common.comps.EnvironmentDataChangeEvent;
import org.vpda.common.comps.EnvironmentInitEvent;
import org.vpda.common.comps.EnvironmentLayoutChangeEvent;
import org.vpda.common.comps.EnvironmentStructureChangeEvent;
import org.vpda.common.comps.shortcuts.keyboard.KeyboardShortcuts;

/** Provider shortcuts */
public final class UIShortcuts implements Serializable {
    private static final long serialVersionUID = -1078354680578231621L;

    private final KeyboardShortcuts keyboardShortcuts;

    /**
     * Creates shortcuts
     */
    public UIShortcuts() {
        this.keyboardShortcuts = null;
    }

    /**
     * Creates shortcuts
     * 
     * @param keyboardShortcuts
     */
    public UIShortcuts(KeyboardShortcuts keyboardShortcuts) {
        super();
        this.keyboardShortcuts = keyboardShortcuts;
    }

    /**
     * @return the keyboardShortcuts
     */
    public KeyboardShortcuts getKeyboardShortcuts() {
        return keyboardShortcuts;
    }

    @Override
    public String toString() {
        return "UIShortcuts [keyboardShortcuts=" + keyboardShortcuts + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((keyboardShortcuts == null) ? 0 : keyboardShortcuts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UIShortcuts other = (UIShortcuts) obj;
        if (keyboardShortcuts == null) {
            if (other.keyboardShortcuts != null)
                return false;
        }
        else if (!keyboardShortcuts.equals(other.keyboardShortcuts))
            return false;
        return true;
    }

    /**
     * Will fire EnvironmentChangeEvent event
     * 
     * @param changeEvent
     */
    public void fireEnvironmentDataChanged(EnvironmentDataChangeEvent changeEvent) {
        keyboardShortcuts.fireEnvironmentDataChanged(changeEvent);
    }

    /**
     * Will fire EnvironmentInitEvent
     * 
     * @param event
     */
    public void fireEnvironmentInitialized(EnvironmentInitEvent event) {
        keyboardShortcuts.fireEnvironmentInitialized(event);
    }

    /**
     * Will fire EnvironmentStructureChangeEvent
     * 
     * @param event
     */
    public void fireEnvironmentStructureChanged(EnvironmentStructureChangeEvent event) {
        keyboardShortcuts.fireEnvironmentStructureChanged(event);
    }

    /**
     * Will fire EnvironmentLayoutChangeEvent
     * 
     * @param event
     */
    public void fireEnvironmentLayoutChanged(EnvironmentLayoutChangeEvent event) {
        keyboardShortcuts.fireEnvironmentLayoutChanged(event);
    }

}
