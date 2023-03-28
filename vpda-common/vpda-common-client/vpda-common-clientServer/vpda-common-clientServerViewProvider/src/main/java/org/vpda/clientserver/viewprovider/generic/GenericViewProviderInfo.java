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
package org.vpda.clientserver.viewprovider.generic;

import org.vpda.clientserver.viewprovider.ViewProviderInfo;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.internal.common.util.Assert;

/**
 * Default impl for DetailViewProvider info.
 * 
 * @author kitko
 *
 */
public final class GenericViewProviderInfo extends ViewProviderInfo {

    private static final long serialVersionUID = 591855287380014100L;
    private final ComponentsGroupsDef detailCompsDef;

    private GenericViewProviderInfo(GenericViewProviderInfoBuilder builder) {
        super(builder);
        this.detailCompsDef = Assert.isNotNullArgument(builder.detailCompsDef, "detailCompssDef");
    }

    /**
     * @return raw components access
     */
    public ComponentsGroupsDef getDetailCompsDef() {
        return detailCompsDef;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T extends ViewProviderInfo> Builder<T> createBuilder() {
        return (Builder<T>) new GenericViewProviderInfoBuilder();
    }

    /**
     * Builder for DetailInfo
     * 
     * @author kitko
     *
     */
    public static final class GenericViewProviderInfoBuilder extends Builder<GenericViewProviderInfo> {
        private ComponentsGroupsDef detailCompsDef;

        /**
         * Creates empty builder
         */
        public GenericViewProviderInfoBuilder() {
        }

        @Override
        public GenericViewProviderInfoBuilder setValues(GenericViewProviderInfo info) {
            super.setValues(info);
            this.detailCompsDef = info.getDetailCompsDef();
            return this;
        }

        /**
         * @return detailCompsDef
         */
        public ComponentsGroupsDef getDetailCompsDef() {
            return detailCompsDef;
        }

        /**
         * @param detailCompsDef
         * @return this
         */
        public GenericViewProviderInfoBuilder setDetailCompsDef(ComponentsGroupsDef detailCompsDef) {
            this.detailCompsDef = detailCompsDef;
            return this;
        }

        /**
         * Builds info
         * 
         * @return new created info
         */
        @Override
        public GenericViewProviderInfo build() {
            return new GenericViewProviderInfo(this);
        }

        @Override
        public Class<GenericViewProviderInfo> getTargetClass() {
            return GenericViewProviderInfo.class;
        }

    }

}
