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

import org.vpda.common.service.localization.LocValue;

/**
 * Abstract component localization value
 * 
 * @author kitko
 *
 */
public abstract class AbstractCompLocValue implements LocValue {
    private static final long serialVersionUID = 2958379636415703625L;
    private final String tooltip;

    /**
     * @param tooltip
     */
    protected AbstractCompLocValue(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * @param b
     */
    protected AbstractCompLocValue(Builder b) {
        this.tooltip = b.tooltip;
    }

    /**
     * 
     * @return tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Builder for AbstractCompLocValue
     * 
     * @author kitko
     * @param <T>
     */
    public static abstract class Builder<T extends AbstractCompLocValue> implements org.vpda.common.util.Builder<T> {
        /** Tooltip */
        private String tooltip;

        /**
         * @return the tooltip
         */
        public String getTooltip() {
            return tooltip;
        }

        /**
         * @param tooltip the tooltip to set
         * @return this
         */
        public Builder<T> setTooltip(String tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        /**
         * @param l
         * @return this
         */
        protected Builder<T> setValues(MultiOptionLocValue l) {
            this.tooltip = l.getTooltip();
            return this;

        }

    }

}
