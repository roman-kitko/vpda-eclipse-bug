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

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.common.service.preferences.AbstractContextPreferences;

/**
 * @author kitko
 *
 */
public abstract class AbstractViewProviderPreferences extends AbstractContextPreferences {

    private static final long serialVersionUID = 7531805145044937061L;
    private final ViewProviderDef viewProviderDef;

    /**
     * @param builder
     */
    protected AbstractViewProviderPreferences(Builder builder) {
        super(builder);
        this.viewProviderDef = builder.getViewProviderDef();
    }

    /**
     * @return the viewProviderDef
     */
    public ViewProviderDef getViewProviderDef() {
        return viewProviderDef;
    }

    /**
     * @return ViewProviderSettings
     */
    public abstract ViewProviderSettings getViewProviderSettings();

    /**
     * @return builder with same values
     */
    public abstract <T extends AbstractViewProviderPreferences> Builder<T> createBuilderWithSameValues();

    @Override
    public String toString() {
        return "AbstractViewProviderPreferences [viewProviderDef=" + viewProviderDef + ", super()=" + super.toString() + "]";
    }

    /**
     * @author kitko
     *
     * @param <T>
     */
    public static abstract class Builder<T extends AbstractViewProviderPreferences> extends AbstractContextPreferences.Builder<T> {
        private ViewProviderDef viewProviderDef;

        /**
         * @return the viewProviderDef
         */
        public ViewProviderDef getViewProviderDef() {
            return viewProviderDef;
        }

        /**
         * @param viewProviderDef the viewProviderDef to set
         * @return this
         */
        public Builder<T> setViewProviderDef(ViewProviderDef viewProviderDef) {
            this.viewProviderDef = viewProviderDef;
            return this;
        }

        @Override
        public Builder<T> setValues(T pref) {
            super.setValues(pref);
            this.viewProviderDef = pref.getViewProviderDef();
            return this;
        }

    }

}
