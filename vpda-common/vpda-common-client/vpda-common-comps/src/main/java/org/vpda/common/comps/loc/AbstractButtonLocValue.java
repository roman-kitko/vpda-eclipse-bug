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

/**
 * Localization data for buttons
 * 
 * @author kitko
 *
 */
public abstract class AbstractButtonLocValue extends AbstractCompLocValue {
    private static final long serialVersionUID = 5974629775774012188L;
    private final IconLocValue icon;
    private final String label;
    private final IconLocValue disabledIcon;
    private final Character mnemonic;

    /**
     * @return icon value
     */
    public IconLocValue getIconValue() {
        return icon;
    }

    @Override
    public AbstractButtonLocValue clone() throws CloneNotSupportedException {
        return (AbstractButtonLocValue) super.clone();
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return disabled icon value
     */
    public IconLocValue getDisabledIconValue() {
        return disabledIcon;
    }

    /**
     * @return the mnemonic
     */
    public Character getMnemonic() {
        return mnemonic;
    }

    /**
     * @param label
     * @param icon
     * @param tooltip
     */
    protected AbstractButtonLocValue(String label, IconLocValue icon, String tooltip) {
        super(tooltip);
        this.label = label;
        this.icon = icon;
        this.disabledIcon = null;
        this.mnemonic = null;
    }

    /**
     * @param label
     */
    protected AbstractButtonLocValue(String label) {
        super((String) null);
        this.label = label;
        this.disabledIcon = null;
        this.mnemonic = null;
        this.icon = null;
    }

    /**
     * @param <T>
     * @param b
     */
    protected <T extends AbstractButtonLocValue> AbstractButtonLocValue(Builder<T> b) {
        super(b);
        icon = b.icon;
        label = b.label;
        disabledIcon = b.disabledIcon;
        mnemonic = b.mnemonic;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((icon == null) ? 0 : icon.hashCode());
        result = PRIME * result + ((label == null) ? 0 : label.hashCode());
        result = PRIME * result + ((getTooltip() == null) ? 0 : getTooltip().hashCode());
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
        final AbstractButtonLocValue other = (AbstractButtonLocValue) obj;
        if (icon == null) {
            if (other.icon != null)
                return false;
        }
        else if (!icon.equals(other.icon))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        }
        else if (!label.equals(other.label))
            return false;
        if (getTooltip() == null) {
            if (other.getTooltip() != null)
                return false;
        }
        else if (!getTooltip().equals(other.getTooltip()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("AbstractButtonLocValue [label=");
        builder2.append(label);
        builder2.append("]");
        return builder2.toString();
    }

    /**
     * Generic Abstract button builder
     * 
     * @param <T> type of button localization value
     */
    protected static abstract class Builder<T extends AbstractButtonLocValue> extends AbstractCompLocValue.Builder<T> {
        private IconLocValue icon;
        private String label;
        private IconLocValue disabledIcon;
        private Character mnemonic;

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
        public Builder<T> setIcon(IconLocValue icon) {
            this.icon = icon;
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
        public Builder<T> setLabel(String label) {
            this.label = label;
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
        public Builder<T> setDisabledIcon(IconLocValue disabledIcon) {
            this.disabledIcon = disabledIcon;
            return this;
        }

        /**
         * @return the mnemonic
         */
        public Character getMnemonic() {
            return mnemonic;
        }

        /**
         * @param mnemonic the mnemonic to set
         * @return this
         */
        public Builder<T> setMnemonic(Character mnemonic) {
            this.mnemonic = mnemonic;
            return this;
        }

        /**
         * Sets values
         * 
         * @param t
         * @return this
         */
        public Builder<T> setValues(T t) {
            this.disabledIcon = t.getDisabledIconValue();
            this.icon = t.getIconValue();
            this.label = t.getLabel();
            this.setTooltip(t.getTooltip());
            this.mnemonic = t.getMnemonic();
            return this;
        }
    }
}
