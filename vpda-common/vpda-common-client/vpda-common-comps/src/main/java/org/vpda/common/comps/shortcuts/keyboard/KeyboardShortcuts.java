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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.comps.EnvironmentDataChangeEvent;
import org.vpda.common.comps.EnvironmentInitEvent;
import org.vpda.common.comps.EnvironmentLayoutChangeEvent;
import org.vpda.common.comps.EnvironmentStructureChangeEvent;
import org.vpda.common.comps.shortcuts.UIShortcut;
import org.vpda.internal.common.util.CollectionUtil;

/**
 * List of {@link KeyboardShortcut} shortcuts
 * 
 * @author kitko
 *
 */
@Immutable
public final class KeyboardShortcuts implements Serializable {
    private static final long serialVersionUID = 7741633304309416185L;

    private final List<KeyboardShortcut> shortcuts;

    /**
     * @param shortcuts
     */
    public KeyboardShortcuts(List<KeyboardShortcut> shortcuts) {
        super();
        this.shortcuts = new ArrayList<KeyboardShortcut>(shortcuts);
    }

    /**
     * @param shortcuts
     */
    public KeyboardShortcuts(KeyboardShortcut... shortcuts) {
        super();
        this.shortcuts = new ArrayList<KeyboardShortcut>(shortcuts.length);
        for (KeyboardShortcut s : shortcuts) {
            this.shortcuts.add(s);
        }
    }

    /**
     * @return the shortcuts
     */
    public List<KeyboardShortcut> getShortcuts() {
        return Collections.unmodifiableList(shortcuts);
    }

    /**
     * @return shortcuts.size()
     */
    public int getSize() {
        return shortcuts.size();
    }

    /**
     * @param index
     * @return ViewProviderKeyboardShortcut at index
     */
    public UIShortcut getShortcut(int index) {
        return shortcuts.get(index);
    }

    /**
     * @return iterator
     */
    public Iterator<KeyboardShortcut> getIterator() {
        return CollectionUtil.unmodifiableIterator(shortcuts.iterator());
    }

    @Override
    public String toString() {
        return "KeyboardShortcuts [shortcuts=" + shortcuts + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((shortcuts == null) ? 0 : shortcuts.hashCode());
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
        KeyboardShortcuts other = (KeyboardShortcuts) obj;
        if (shortcuts == null) {
            if (other.shortcuts != null)
                return false;
        }
        else if (!shortcuts.equals(other.shortcuts))
            return false;
        return true;
    }

    /**
     * Will fire EnvironmentChangeEvent
     * 
     * @param changeEvent
     */
    public void fireEnvironmentDataChanged(EnvironmentDataChangeEvent changeEvent) {
        for (KeyboardShortcut ks : shortcuts) {
            ks.getMemberListenerSupport().fireEnvironmentDataChanged(changeEvent);
        }
    }

    /**
     * Will fire EnvironmentInitEvent
     * 
     * @param event
     */
    public void fireEnvironmentInitialized(EnvironmentInitEvent event) {
        for (KeyboardShortcut ks : shortcuts) {
            ks.getMemberListenerSupport().fireEnvironmentInitialized(event);
        }
    }

    /**
     * Will fire EnvironmentStructureChangeEvent event
     * 
     * @param event
     */
    public void fireEnvironmentStructureChanged(EnvironmentStructureChangeEvent event) {
        for (KeyboardShortcut ks : shortcuts) {
            ks.getMemberListenerSupport().fireEnvironmentStructureChanged(event);
        }
    }

    /**
     * Will fire EnvironmentLayoutChangeEvent event
     * 
     * @param event
     */
    public void fireEnvironmentLayoutChanged(EnvironmentLayoutChangeEvent event) {
        for (KeyboardShortcut ks : shortcuts) {
            ks.getMemberListenerSupport().fireEnvironmentLayoutChanged(event);
        }
    }

}
