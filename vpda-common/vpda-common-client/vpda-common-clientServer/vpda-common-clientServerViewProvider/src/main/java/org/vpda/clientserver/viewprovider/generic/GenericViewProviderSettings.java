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

import org.vpda.clientserver.viewprovider.LayoutBasedViewProviderSettings;

/**
 * Settings for detail providers
 * 
 * @author kitko
 *
 */
public final class GenericViewProviderSettings extends LayoutBasedViewProviderSettings {

    private static final long serialVersionUID = -1956141858044834388L;

    /**
     * Creates new Settings
     */
    public GenericViewProviderSettings() {
    }

    private GenericViewProviderSettings(Builder builder) {
        super(builder);
    }

    /**
     * @return Builder
     */
    @SuppressWarnings("unchecked")
    @Override
    public Builder createBuilderWithSameValues() {
        return new Builder().setValues(this);
    }

    /**
     * Builder for GenericViewProviderSettings
     * 
     * @author kitko
     *
     */
    public static final class Builder extends LayoutBasedViewProviderSettings.Builder<GenericViewProviderSettings> implements org.vpda.common.util.Builder<GenericViewProviderSettings> {

        @Override
        public Class<? extends GenericViewProviderSettings> getTargetClass() {
            return GenericViewProviderSettings.class;
        }

        @Override
        public GenericViewProviderSettings build() {
            return new GenericViewProviderSettings(this);
        }

        @Override
        public Builder setValues(GenericViewProviderSettings settings) {
            super.setValues(settings);
            return this;
        }

    }

}
