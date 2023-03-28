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
 * This is default implementation for push buttons localization values.
 * 
 * @author kitko
 *
 */
@Immutable
public final class PushButtonLocValue extends AbstractButtonLocValue {
    private static final long serialVersionUID = -8825004959225681370L;

    /**
     * @param label
     * @param icon
     * @param tooltip
     */
    public PushButtonLocValue(String label, IconLocValue icon, String tooltip) {
        super(label, icon, tooltip);
    }

    /**
     * @param label
     */
    public PushButtonLocValue(String label) {
        super(label);
    }

    private PushButtonLocValue(Builder builder) {
        super(builder);
    }

    /**
     * Builder for {@link PushButtonLocValue}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractButtonLocValue.Builder<PushButtonLocValue> {

        @Override
        public PushButtonLocValue build() {
            return new PushButtonLocValue(this);
        }

        @Override
        public Class<? extends PushButtonLocValue> getTargetClass() {
            return PushButtonLocValue.class;
        }

    }
}
