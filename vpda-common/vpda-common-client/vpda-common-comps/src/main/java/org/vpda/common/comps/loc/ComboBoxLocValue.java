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
/**
 * 
 */
package org.vpda.common.comps.loc;

import org.vpda.common.annotations.Immutable;

/**
 * Holder of localization data for comboBox
 * 
 * @author kitko
 *
 */
@Immutable
public final class ComboBoxLocValue extends AbstractCompLocValue {
    private static final long serialVersionUID = 8184429113970452638L;

    /**
     * Creates {@link ComboBoxLocValue} with tooltip
     * 
     * @param tooltip
     */
    public ComboBoxLocValue(String tooltip) {
        super(tooltip);
    }

    private ComboBoxLocValue(Builder builder) {
        super(builder);
    }

    /** Builder for ComboBoxLocValue */
    public static final class Builder extends AbstractCompLocValue.Builder<ComboBoxLocValue> {
        @Override
        public ComboBoxLocValue build() {
            return new ComboBoxLocValue(this);
        }

        @Override
        public Class<? extends ComboBoxLocValue> getTargetClass() {
            return ComboBoxLocValue.class;
        }

    }

}
