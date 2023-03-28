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
package org.vpda.clientserver.viewprovider.preferences;

import org.vpda.clientserver.viewprovider.generic.GenericViewProvider;
import org.vpda.clientserver.viewprovider.generic.GenericViewProviderSettings;

/**
 * Settings for {@link GenericViewProvider}
 * 
 * @author kitko
 *
 */
public final class GenericViewProviderPreferences extends AbstractViewProviderPreferences {
    private static final long serialVersionUID = 2174154056288949321L;

    private final GenericViewProviderSettings genericViewProviderSettings;

    private GenericViewProviderPreferences(Builder builder) {
        super(builder);
        this.genericViewProviderSettings = builder.getGenericViewProviderSettings();
    }

    /**
     * @return the listViewProviderSettings
     */
    public GenericViewProviderSettings getGenericViewProviderSettings() {
        return genericViewProviderSettings;
    }

    @Override
    public GenericViewProviderSettings getViewProviderSettings() {
        return getGenericViewProviderSettings();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Builder createBuilderWithSameValues() {
        return new Builder().setValues(this);
    }

    /**
     * Builder for {@link GenericViewProviderPreferences}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractViewProviderPreferences.Builder<GenericViewProviderPreferences> {

        private GenericViewProviderSettings genericViewProviderSettings;

        /**
         * @return the genericViewProviderSettings
         */
        public GenericViewProviderSettings getGenericViewProviderSettings() {
            return genericViewProviderSettings;
        }

        /**
         * @param genericViewProviderSettings the genericViewProviderSettings to set
         * @return this
         */
        public Builder setGenericViewProviderSettings(GenericViewProviderSettings genericViewProviderSettings) {
            this.genericViewProviderSettings = genericViewProviderSettings;
            return this;
        }

        @Override
        public GenericViewProviderPreferences build() {
            return new GenericViewProviderPreferences(this);
        }

        @Override
        public Builder setValues(GenericViewProviderPreferences pref) {
            super.setValues(pref);
            this.genericViewProviderSettings = pref.getGenericViewProviderSettings();
            return this;

        }

        @Override
        public Class<? extends GenericViewProviderPreferences> getTargetClass() {
            return GenericViewProviderPreferences.class;
        }

    }
}
