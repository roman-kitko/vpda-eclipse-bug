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
package org.vpda.common.comps.loc;

import org.vpda.common.annotations.Immutable;

/**
 * This is default implementation of ToggleButtonLocValue
 * 
 * @author kitko
 *
 */
@Immutable
public final class ToggleButtonLocValue extends AbstractButtonLocValue {
    private static final long serialVersionUID = -5039642940699478622L;

    /**
     * @param label
     * @param icon
     * @param tooltip
     */
    public ToggleButtonLocValue(String label, IconLocValue icon, String tooltip) {
        super(label, icon, tooltip);
    }

    /**
     * @param label
     */
    public ToggleButtonLocValue(String label) {
        super(label);
    }

    private ToggleButtonLocValue(Builder builder) {
        super(builder);
    }

    /**
     * Builder for ToggleButtonLocValue
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractButtonLocValue.Builder<ToggleButtonLocValue> {
        @Override
        public ToggleButtonLocValue build() {
            return new ToggleButtonLocValue(this);
        }

        @Override
        public Class<? extends ToggleButtonLocValue> getTargetClass() {
            return ToggleButtonLocValue.class;
        }

    }
}
