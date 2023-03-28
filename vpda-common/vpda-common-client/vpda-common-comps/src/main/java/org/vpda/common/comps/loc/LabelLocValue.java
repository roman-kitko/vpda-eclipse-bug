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
 * Localization data for labels
 * 
 * @author kitko
 *
 */
@Immutable
public final class LabelLocValue extends AbstractCompLocValue {
    private static final long serialVersionUID = 5974629775774012188L;
    private final IconLocValue icon;
    private final IconLocValue disabledIcon;
    private final String label;

    /**
     * @return icon value
     */
    public IconLocValue getIconValue() {
        return icon;
    }

    @Override
    public LabelLocValue clone() throws CloneNotSupportedException {
        return (LabelLocValue) super.clone();
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     * @param icon
     * @param tooltip
     */
    public LabelLocValue(String label, IconLocValue icon, String tooltip) {
        super(tooltip);
        this.label = label;
        this.icon = icon;
        this.disabledIcon = null;
    }

    /**
     * @param label
     */
    public LabelLocValue(String label) {
        super((String) null);
        this.label = label;
        this.icon = null;
        this.disabledIcon = null;
    }

    private LabelLocValue(Builder builder) {
        super(builder);
        this.label = builder.label;
        this.disabledIcon = builder.disabledIcon;
        this.icon = builder.icon;
    }

    /**
     * @return disabled icon value
     */
    public IconLocValue getDisabledIconValue() {
        return disabledIcon;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("LabelLocValue [label=");
        builder2.append(label);
        builder2.append("]");
        return builder2.toString();
    }

    /** Builder for FieldLocValue */
    public final static class Builder extends AbstractCompLocValue.Builder<LabelLocValue> {
        private IconLocValue icon;
        private IconLocValue disabledIcon;
        private String label;

        /**
         * @return the icon
         */
        public IconLocValue getIcon() {
            return icon;
        }

        /**
         * @param icon the icon to set
         * @return this
         */
        public Builder setIcon(IconLocValue icon) {
            this.icon = icon;
            return this;
        }

        /**
         * @return the disabledIcon
         */
        public IconLocValue getDisabledIcon() {
            return disabledIcon;
        }

        /**
         * @param disabledIcon the disabledIcon to set
         * @return this
         */
        public Builder setDisabledIcon(IconLocValue disabledIcon) {
            this.disabledIcon = disabledIcon;
            return this;
        }

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label the label to set
         * @return this
         */
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        @Override
        public LabelLocValue build() {
            return new LabelLocValue(this);
        }

        @Override
        public Class<? extends LabelLocValue> getTargetClass() {
            return LabelLocValue.class;
        }

    }

}
