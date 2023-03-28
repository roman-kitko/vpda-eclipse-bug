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

import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProvider;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProviderSettings;

/**
 * Settings for {@link AutoCompleteViewProvider}
 * 
 * @author kitko
 *
 */
public final class AutoCompleteViewProviderPreferences extends AbstractViewProviderPreferences {
    private static final long serialVersionUID = 2174154056288949321L;

    private final AutoCompleteViewProviderSettings autoCompleteViewProviderSettings;

    private AutoCompleteViewProviderPreferences(Builder builder) {
        super(builder);
        this.autoCompleteViewProviderSettings = builder.getAutoCompleteViewProviderSettings();
    }

    /**
     * @return the listViewProviderSettings
     */
    public AutoCompleteViewProviderSettings getAutoCompleteViewProviderSettings() {
        return autoCompleteViewProviderSettings;
    }

    @Override
    public AutoCompleteViewProviderSettings getViewProviderSettings() {
        return getAutoCompleteViewProviderSettings();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Builder createBuilderWithSameValues() {
        return new Builder().setValues(this);
    }

    /**
     * Builder for {@link AutoCompleteViewProviderPreferences}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractViewProviderPreferences.Builder<AutoCompleteViewProviderPreferences> {

        private AutoCompleteViewProviderSettings autoCompleteViewProviderSettings;

        /**
         * @return the genericViewProviderSettings
         */
        public AutoCompleteViewProviderSettings getAutoCompleteViewProviderSettings() {
            return autoCompleteViewProviderSettings;
        }

        /**
         * @param autoCompleteViewProviderSettings the genericViewProviderSettings to
         *                                         set
         * @return this
         */
        public Builder setAutoCompleteViewProviderSettings(AutoCompleteViewProviderSettings autoCompleteViewProviderSettings) {
            this.autoCompleteViewProviderSettings = autoCompleteViewProviderSettings;
            return this;
        }

        @Override
        public AutoCompleteViewProviderPreferences build() {
            return new AutoCompleteViewProviderPreferences(this);
        }

        @Override
        public Builder setValues(AutoCompleteViewProviderPreferences pref) {
            super.setValues(pref);
            this.autoCompleteViewProviderSettings = pref.getAutoCompleteViewProviderSettings();
            return this;

        }

        @Override
        public Class<? extends AutoCompleteViewProviderPreferences> getTargetClass() {
            return AutoCompleteViewProviderPreferences.class;
        }

    }
}
