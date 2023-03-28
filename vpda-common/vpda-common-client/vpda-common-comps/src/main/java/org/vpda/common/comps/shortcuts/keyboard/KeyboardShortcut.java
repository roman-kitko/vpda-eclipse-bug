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
package org.vpda.common.comps.shortcuts.keyboard;

import java.io.Serializable;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.command.Command;
import org.vpda.common.comps.shortcuts.UIShortcut;
import org.vpda.internal.common.util.Assert;

/**
 * Command keyBoard shortcut
 * 
 * @author kitko
 *
 */
@Immutable
public final class KeyboardShortcut extends UIShortcut implements Serializable {
    private static final long serialVersionUID = 8190933783483316013L;
    private final KeyStroke keyStroke;

    /**
     * @param id
     * @param keyStroke
     * @param command
     */
    public KeyboardShortcut(String id, KeyStroke keyStroke, Command<?> command) {
        super(id, command);
        this.keyStroke = Assert.isNotNullArgument(keyStroke, "keyStroke");
    }

    /**
     * @return the keyStroke
     */
    public KeyStroke getKeyStroke() {
        return keyStroke;
    }

    @Override
    public String toString() {
        return "KeyboardShortcut [keyStroke=" + keyStroke + ", command=" + command + ", getId()=" + getId() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((command == null) ? 0 : command.hashCode());
        result = prime * result + ((keyStroke == null) ? 0 : keyStroke.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyboardShortcut other = (KeyboardShortcut) obj;
        if (command == null) {
            if (other.command != null)
                return false;
        }
        else if (!command.equals(other.command))
            return false;
        if (keyStroke == null) {
            if (other.keyStroke != null)
                return false;
        }
        else if (!keyStroke.equals(other.keyStroke))
            return false;
        return true;
    }
}
