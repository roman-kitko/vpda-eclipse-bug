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

import java.util.Arrays;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.service.localization.LocValue;

/**
 * Default impl for IconLocValue
 * 
 * @author kitko
 *
 */
@Immutable
public final class IconLocValue implements LocValue {

    private static final long serialVersionUID = -5114950088465749255L;
    private final String iconPath;
    private final byte[] iconData;

    /**
     * @return icon byte data
     */
    public byte[] getIconData() {
        return iconData != null ? Arrays.copyOf(iconData, iconData.length) : null;
    }

    /**
     * @return icon path
     */
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public IconLocValue clone() throws CloneNotSupportedException {
        return new IconLocValue.Builder().setIconPath(iconPath).setIconData(iconData).build();
    }

    /**
     * Creates IconLocData
     * 
     * @param iconPath
     * @param iconData
     */
    public IconLocValue(String iconPath, byte[] iconData) {
        this.iconPath = iconPath;
        this.iconData = iconData != null ? Arrays.copyOf(iconData, iconData.length) : null;
    }

    private IconLocValue(Builder builder) {
        this.iconPath = builder.iconPath;
        this.iconData = builder.iconData != null ? Arrays.copyOf(builder.iconData, builder.iconData.length) : null;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + Arrays.hashCode(iconData);
        result = PRIME * result + ((iconPath == null) ? 0 : iconPath.hashCode());
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
        final IconLocValue other = (IconLocValue) obj;
        if (!Arrays.equals(iconData, other.iconData))
            return false;
        if (iconPath == null) {
            if (other.iconPath != null)
                return false;
        }
        else if (!iconPath.equals(other.iconPath))
            return false;
        return true;
    }

    /** Builder for IconLocValue */
    public static final class Builder implements org.vpda.common.util.Builder<IconLocValue> {
        private String iconPath;
        private byte[] iconData;

        @Override
        public IconLocValue build() {
            return new IconLocValue(this);
        }

        @Override
        public Class<? extends IconLocValue> getTargetClass() {
            return IconLocValue.class;
        }

        /**
         * @return the iconPath
         */
        public String getIconPath() {
            return iconPath;
        }

        /**
         * @param iconPath the iconPath to set
         * @return this
         */
        public Builder setIconPath(String iconPath) {
            this.iconPath = iconPath;
            return this;
        }

        /**
         * @return the iconData
         */
        public byte[] getIconData() {
            return iconData != null ? Arrays.copyOf(iconData, iconData.length) : null;
        }

        /**
         * @param iconData the iconData to set
         * @return this
         */
        public Builder setIconData(byte[] iconData) {
            this.iconData = iconData != null ? Arrays.copyOf(iconData, iconData.length) : null;
            return this;
        }

    }

}
