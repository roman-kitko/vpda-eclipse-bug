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

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Key press modifier
 * 
 * @author kitko
 *
 */
public enum KeyModifier {
    /** Shift modifier */
    SHIFT,
    /** CTRL modifier */
    CONTROL,
    /** ALT modifier */
    ALT;

    static final Set<KeyModifier> SET_WITH_SHIFT = Collections.singleton(SHIFT);
    static final Set<KeyModifier> SET_WITH_CONTROL = Collections.singleton(CONTROL);
    static final Set<KeyModifier> SET_WITH_ALT = Collections.singleton(ALT);

    static Set<KeyModifier> createSet(KeyModifier m) {
        if (SHIFT == m) {
            return SET_WITH_SHIFT;
        }
        if (CONTROL == m) {
            return SET_WITH_CONTROL;
        }
        if (ALT == m) {
            return SET_WITH_ALT;
        }
        throw new IllegalArgumentException("Undefined modifier " + m);
    }

    static Set<KeyModifier> createModifiers(Collection<KeyModifier> modifiers2) {
        if (modifiers2 == null || modifiers2.isEmpty()) {
            return Collections.<KeyModifier>emptySet();
        }
        if (modifiers2.size() == 1) {
            KeyModifier m = modifiers2.iterator().next();
            return KeyModifier.createSet(m);
        }
        return EnumSet.copyOf(modifiers2);
    }

    static Set<KeyModifier> createModifiers(KeyModifier... modifiers2) {
        if (modifiers2.length == 0) {
            return Collections.<KeyModifier>emptySet();
        }
        if (modifiers2.length == 1) {
            KeyModifier m = modifiers2[0];
            return KeyModifier.createSet(m);
        }
        if (modifiers2.length == 2) {
            return EnumSet.of(modifiers2[0], modifiers2[1]);
        }
        if (modifiers2.length == 3) {
            return EnumSet.of(modifiers2[0], modifiers2[1], modifiers2[3]);
        }
        throw new IllegalArgumentException("Too many modifiers");
    }
}
