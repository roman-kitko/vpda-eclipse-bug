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
package org.vpda.clientserver.viewprovider;

import org.vpda.internal.common.util.Assert;

/**
 * Settings for detail providers
 * 
 * @author kitko
 *
 */
public abstract class LayoutBasedViewProviderSettings extends ViewProviderSettings {

    private static final long serialVersionUID = -1956141858044834388L;

    /** Children UI orientation */
    protected final ViewProviderUIChildrenOrientation childrenOrientation;

    /**
     * Creates new Settings
     */
    public LayoutBasedViewProviderSettings() {
        childrenOrientation = ViewProviderUIChildrenOrientation.VERTICAL;
    }

    /**
     * @return the childrenOrientation
     */
    public ViewProviderUIChildrenOrientation getChildrenOrientation() {
        return childrenOrientation;
    }

    /**
     * @param builder
     */
    protected LayoutBasedViewProviderSettings(Builder builder) {
        super(builder);
        this.childrenOrientation = Assert.isNotNullArgument(builder.getChildrenOrientation(), "childrenOrientation");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((childrenOrientation == null) ? 0 : childrenOrientation.hashCode());
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
        LayoutBasedViewProviderSettings other = (LayoutBasedViewProviderSettings) obj;
        if (childrenOrientation != other.childrenOrientation)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LayoutBasedViewProviderSettings [childrenOrientation=" + childrenOrientation + "]";
    }

    /**
     * Builder for GenericViewProviderSettings
     * 
     * @author kitko
     * @param <T>
     *
     */
    public static abstract class Builder<T extends LayoutBasedViewProviderSettings> extends ViewProviderSettings.Builder<T> implements org.vpda.common.util.Builder<T> {

        /** Children UI orientation */
        protected ViewProviderUIChildrenOrientation childrenOrientation;

        /**
         * Creates Builder
         */
        protected Builder() {
            childrenOrientation = ViewProviderUIChildrenOrientation.VERTICAL;
        }

        @Override
        public Builder setValues(T values) {
            super.setValues(values);
            this.childrenOrientation = values.getChildrenOrientation();
            return this;
        }

        /**
         * @param childrenOrientation the childrenOrientation to set
         * @return this
         */
        public Builder setChildrenOrientation(ViewProviderUIChildrenOrientation childrenOrientation) {
            this.childrenOrientation = childrenOrientation;
            return this;
        }

        /**
         * @return the childrenOrientation
         */
        public ViewProviderUIChildrenOrientation getChildrenOrientation() {
            return childrenOrientation;
        }

    }

}
