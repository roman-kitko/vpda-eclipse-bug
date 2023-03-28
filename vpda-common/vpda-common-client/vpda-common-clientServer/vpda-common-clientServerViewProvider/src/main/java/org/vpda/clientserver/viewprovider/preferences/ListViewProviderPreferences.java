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

import org.vpda.clientserver.viewprovider.list.ListViewProvider;
import org.vpda.clientserver.viewprovider.list.ListViewProviderSettings;

/**
 * Settings for {@link ListViewProvider}
 * 
 * @author kitko
 *
 */
public final class ListViewProviderPreferences extends AbstractViewProviderPreferences {
    private static final long serialVersionUID = 2174154056288949321L;

    private final ListViewProviderSettings listViewProviderSettings;

    private ListViewProviderPreferences(Builder builder) {
        super(builder);
        this.listViewProviderSettings = builder.getListViewProviderSettings();
    }

    @Override
    public ListViewProviderSettings getViewProviderSettings() {
        return listViewProviderSettings;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Builder createBuilderWithSameValues() {
        return new Builder().setValues(this);
    }

    @Override
    public String toString() {
        return "ListViewProviderPreferences [listViewProviderSettings=" + listViewProviderSettings + ", super()=" + super.toString() + "]";
    }

    /**
     * Builder for {@link ListViewProviderPreferences}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractViewProviderPreferences.Builder<ListViewProviderPreferences> {

        private ListViewProviderSettings listViewProviderSettings;

        /**
         * @return the listViewProviderSettings
         */
        public ListViewProviderSettings getListViewProviderSettings() {
            return listViewProviderSettings;
        }

        /**
         * @param listViewProviderSettings the listViewProviderSettings to set
         * @return this
         */
        public Builder setListViewProviderSettings(ListViewProviderSettings listViewProviderSettings) {
            this.listViewProviderSettings = listViewProviderSettings;
            return this;
        }

        @Override
        public ListViewProviderPreferences build() {
            return new ListViewProviderPreferences(this);
        }

        @Override
        public Builder setValues(ListViewProviderPreferences pref) {
            super.setValues(pref);
            this.listViewProviderSettings = pref.getViewProviderSettings();
            return this;

        }

        @Override
        public Class<? extends ListViewProviderPreferences> getTargetClass() {
            return ListViewProviderPreferences.class;
        }

    }
}
