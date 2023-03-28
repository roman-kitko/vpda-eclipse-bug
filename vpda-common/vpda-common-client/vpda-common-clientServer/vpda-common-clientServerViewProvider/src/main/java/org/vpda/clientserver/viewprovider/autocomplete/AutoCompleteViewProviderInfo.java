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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.clientserver.viewprovider.ViewProviderInfo;

/**
 * Default impl for AutoComplete provider info.
 * 
 * @author kitko
 *
 */
public final class AutoCompleteViewProviderInfo extends ViewProviderInfo {

    private final List<AutoCompleteField> fields;
    private final Class<?> itemType;

    private static final long serialVersionUID = 591855287380014100L;

    private AutoCompleteViewProviderInfo(AutoCompleteViewProviderInfoBuilder builder) {
        super(builder);
        this.fields = new ArrayList<>(builder.fields);
        this.itemType = builder.getItemType();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T extends ViewProviderInfo> Builder<T> createBuilder() {
        return (Builder<T>) new AutoCompleteViewProviderInfoBuilder();
    }

    /**
     * @return the fields
     */
    public List<AutoCompleteField> getFields() {
        return Collections.unmodifiableList(fields);
    }

    /**
     * @return the itemType
     */
    public Class<?> getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return "AutoCompleteViewProviderInfo [fields=" + fields + ", itemType=" + itemType + ", viewProviderID=" + viewProviderID + "]";
    }

    /**
     * Builder for DetailInfo
     * 
     * @author kitko
     *
     */
    public static final class AutoCompleteViewProviderInfoBuilder extends Builder<AutoCompleteViewProviderInfo> {
        private List<AutoCompleteField> fields = new ArrayList<AutoCompleteField>(3);
        private Class<?> itemType;

        /**
         * Creates empty builder
         */
        public AutoCompleteViewProviderInfoBuilder() {
        }

        @Override
        public AutoCompleteViewProviderInfoBuilder setValues(AutoCompleteViewProviderInfo info) {
            super.setValues(info);
            this.itemType = info.getItemType();
            this.fields = new ArrayList<>(info.getFields());
            return this;
        }

        /**
         * Builds info
         * 
         * @return new created info
         */
        @Override
        public AutoCompleteViewProviderInfo build() {
            return new AutoCompleteViewProviderInfo(this);
        }

        @Override
        public Class<AutoCompleteViewProviderInfo> getTargetClass() {
            return AutoCompleteViewProviderInfo.class;
        }

        /**
         * @return the fields
         */
        public List<AutoCompleteField> getFields() {
            return Collections.unmodifiableList(fields);
        }

        /**
         * @param fields the fields to set
         * @return this
         */
        public AutoCompleteViewProviderInfoBuilder setFields(List<AutoCompleteField> fields) {
            this.fields = fields != null ? new ArrayList<>(fields) : new ArrayList<>(2);
            return this;
        }

        /**
         * @param field
         * @return field
         */
        public AutoCompleteViewProviderInfoBuilder addField(AutoCompleteField field) {
            this.fields.add(field);
            return this;
        }

        /**
         * Add fields
         * 
         * @param fields
         * @return this
         */
        public AutoCompleteViewProviderInfoBuilder addFields(AutoCompleteField... fields) {
            for (AutoCompleteField f : fields) {
                addField(f);
            }
            return this;
        }

        /**
         * @param fields
         * @return this
         */
        public AutoCompleteViewProviderInfoBuilder addFields(List<AutoCompleteField> fields) {
            for (AutoCompleteField f : fields) {
                addField(f);
            }
            return this;
        }

        /**
         * @return the itemType
         */
        public Class<?> getItemType() {
            return itemType;
        }

        /**
         * @param itemType the itemType to set
         * @return this
         */
        public AutoCompleteViewProviderInfoBuilder setItemType(Class<?> itemType) {
            this.itemType = itemType;
            return this;
        }

    }

}
