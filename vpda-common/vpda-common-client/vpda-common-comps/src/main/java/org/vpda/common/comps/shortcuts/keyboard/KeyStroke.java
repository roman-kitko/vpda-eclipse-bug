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
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.vpda.common.annotations.Immutable;
import org.vpda.internal.common.util.Assert;

/**
 * Keystroke is a combination of {@link KeyCode} and {@link KeyModifier}
 * 
 * @author kitko
 *
 */
@Immutable
public final class KeyStroke implements Serializable {
    private static final long serialVersionUID = -5867893451538930900L;
    private final KeyCode code;
    private final Set<KeyModifier> modifiers;

    /**
     * Creates KeyStroke
     * 
     * @param code
     * @param modifiers
     */
    public KeyStroke(KeyCode code, Collection<KeyModifier> modifiers) {
        this.code = Assert.isNotNullArgument(code, "code");
        this.modifiers = KeyModifier.createModifiers(modifiers);
    }

    /**
     * @param code
     * @return KeyStroke
     */
    public static KeyStroke create(KeyCode code) {
        return new KeyStroke(code);
    }

    /**
     * @param code
     * @param m
     * @return KeyStroke
     */
    public static KeyStroke create(KeyCode code, KeyModifier... m) {
        return new KeyStroke(code, m);
    }

    /**
     * Creates KeyStroke
     * 
     * @param code
     */
    @SuppressWarnings("unchecked")
    public KeyStroke(KeyCode code) {
        this(code, (Collection) null);
    }

    /**
     * Creates KeyStroke
     * 
     * @param code
     * @param m
     */
    public KeyStroke(KeyCode code, KeyModifier... m) {
        this.code = Assert.isNotNullArgument(code, "code");
        this.modifiers = KeyModifier.createModifiers(m);
    }

    /**
     * @return the code
     */
    public KeyCode getCode() {
        return code;
    }

    /**
     * @return the modifiers
     */
    public Set<KeyModifier> getModifiers() {
        if (modifiers == Collections.<KeyModifier>emptySet() || modifiers.isEmpty()) {
            return modifiers;
        }
        if (modifiers == KeyModifier.SET_WITH_ALT || (modifiers.size() == 1 && modifiers.iterator().next().equals(KeyModifier.ALT))) {
            return KeyModifier.SET_WITH_ALT;
        }
        if (modifiers == KeyModifier.SET_WITH_SHIFT || (modifiers.size() == 1 && modifiers.iterator().next().equals(KeyModifier.SHIFT))) {
            return KeyModifier.SET_WITH_SHIFT;
        }
        if (modifiers == KeyModifier.SET_WITH_CONTROL || (modifiers.size() == 1 && modifiers.iterator().next().equals(KeyModifier.CONTROL))) {
            return KeyModifier.SET_WITH_CONTROL;
        }
        return Collections.unmodifiableSet(modifiers);
    }

    /**
     * @return true if we have any modifier
     */
    public boolean hasAnyModifier() {
        return !modifiers.isEmpty();
    }

}
