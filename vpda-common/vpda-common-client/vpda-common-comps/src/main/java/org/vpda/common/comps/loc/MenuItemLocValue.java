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
 * Menu items localization value
 * 
 * @author kitko
 *
 */
@Immutable
public final class MenuItemLocValue extends AbstractButtonLocValue {
    private static final long serialVersionUID = -8825004959225681370L;
    private final String accelerator;

    /**
     * @param label
     * @param icon
     * @param tooltip
     */
    public MenuItemLocValue(String label, IconLocValue icon, String tooltip) {
        super(label, icon, tooltip);
        this.accelerator = null;
    }

    /**
     * @param label
     */
    public MenuItemLocValue(String label) {
        super(label);
        this.accelerator = null;
    }

    private MenuItemLocValue(Builder builder) {
        super(builder);
        this.accelerator = builder.accelerator;
    }

    /**
     * @return the accelerator
     */
    public String getAccelerator() {
        return accelerator;
    }

    /**
     * Builder for {@link MenuItemLocValue}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractButtonLocValue.Builder<MenuItemLocValue> {
        private String accelerator;

        /**
         * @return the accelerator
         */
        public String getAccelerator() {
            return accelerator;
        }

        /**
         * @param accelerator the accelerator to set
         * @return this
         */
        public Builder setAccelerator(String accelerator) {
            this.accelerator = accelerator;
            return this;
        }

        @Override
        public MenuItemLocValue build() {
            return new MenuItemLocValue(this);
        }

        @Override
        public Class<? extends MenuItemLocValue> getTargetClass() {
            return MenuItemLocValue.class;
        }

        @Override
        public Builder setValues(MenuItemLocValue t) {
            super.setValues(t);
            this.accelerator = t.accelerator;
            return this;
        }

    }

}
