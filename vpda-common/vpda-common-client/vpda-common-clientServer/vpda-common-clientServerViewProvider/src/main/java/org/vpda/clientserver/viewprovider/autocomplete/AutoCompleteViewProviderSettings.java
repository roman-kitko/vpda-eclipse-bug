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
package org.vpda.clientserver.viewprovider.autocomplete;

import org.vpda.clientserver.viewprovider.ViewProviderSettings;

/**
 * {@link ViewProviderSettings} for AutoComplete providers
 * 
 * @author kitko
 *
 */
public final class AutoCompleteViewProviderSettings extends ViewProviderSettings {

    private static final long serialVersionUID = -2483365117753945482L;
    private final int suggestionLimit;
    private final boolean caseinsensitive;

    /** Empty settings */
    public AutoCompleteViewProviderSettings() {
        this.suggestionLimit = 10;
        this.caseinsensitive = false;
    }

    private AutoCompleteViewProviderSettings(Builder builder) {
        super(builder);
        this.suggestionLimit = builder.getSuggestionLimit();
        this.caseinsensitive = builder.isCaseinsensitive();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder createBuilderWithSameValues() {
        return new Builder().setValues(this);
    }

    /**
     * @return the suggestionLimit
     */
    public int getSuggestionLimit() {
        return suggestionLimit;
    }

    /**
     * @return the caseinsensitive
     */
    public boolean isCaseinsensitive() {
        return caseinsensitive;
    }

    @Override
    public String toString() {
        return "AutoCompleteViewProviderSettings [suggestionLimit=" + suggestionLimit + ", caseinsensitive=" + caseinsensitive + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (caseinsensitive ? 1231 : 1237);
        result = prime * result + suggestionLimit;
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
        AutoCompleteViewProviderSettings other = (AutoCompleteViewProviderSettings) obj;
        if (caseinsensitive != other.caseinsensitive)
            return false;
        if (suggestionLimit != other.suggestionLimit)
            return false;
        return true;
    }

    /**
     * Builder for AutoCompleteViewProviderSettings
     * 
     * @author kitko
     *
     */
    public static final class Builder extends ViewProviderSettings.Builder<AutoCompleteViewProviderSettings> implements org.vpda.common.util.Builder<AutoCompleteViewProviderSettings> {

        private int suggestionLimit = 10;
        private boolean caseinsensitive = false;

        @Override
        public Class<? extends AutoCompleteViewProviderSettings> getTargetClass() {
            return AutoCompleteViewProviderSettings.class;
        }

        @Override
        public AutoCompleteViewProviderSettings build() {
            return new AutoCompleteViewProviderSettings(this);
        }

        @Override
        public Builder setValues(AutoCompleteViewProviderSettings settings) {
            super.setValues(settings);
            this.suggestionLimit = settings.getSuggestionLimit();
            this.caseinsensitive = settings.isCaseinsensitive();
            return this;
        }

        /**
         * @return the suggestionLimit
         */
        public int getSuggestionLimit() {
            return suggestionLimit;
        }

        /**
         * @param suggestionLimit the suggestionLimit to set
         * @return this
         */
        public Builder setSuggestionLimit(int suggestionLimit) {
            this.suggestionLimit = suggestionLimit;
            return this;
        }

        /**
         * @return the caseinsensitive
         */
        public boolean isCaseinsensitive() {
            return caseinsensitive;
        }

        /**
         * @param caseinsensitive the caseinsensitive to set
         * @return this
         */
        public Builder setCaseinsensitive(boolean caseinsensitive) {
            this.caseinsensitive = caseinsensitive;
            return this;
        }

    }

}
