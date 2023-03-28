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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Localization values for multi options
 * 
 * @author kitko
 *
 */
public final class MultiOptionLocValue extends AbstractCompLocValue {

    private static final long serialVersionUID = 7985159186108261213L;

    private final Map<String, ToggleButtonLocValue> items;
    private final LabelLocValue title;

    private MultiOptionLocValue(Builder b) {
        super(b);
        this.title = b.getTitle();
        this.items = new HashMap<>(b.getItems());
    }

    /**
     * @return the items
     */
    public Map<String, ToggleButtonLocValue> getItems() {
        return items;
    }

    /**
     * @return the title
     */
    public LabelLocValue getTitle() {
        return title;
    }

    /**
     * Builder for MultiOptionLocValue
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractCompLocValue.Builder<MultiOptionLocValue> implements org.vpda.common.util.Builder<MultiOptionLocValue> {
        private Map<String, ToggleButtonLocValue> items;
        private LabelLocValue title;

        @Override
        public Class<? extends MultiOptionLocValue> getTargetClass() {
            return MultiOptionLocValue.class;
        }

        /** Creates builder */
        public Builder() {
            this.items = new HashMap<>();
        }

        @Override
        public Builder setValues(MultiOptionLocValue l) {
            super.setValues(l);
            this.title = l.getTitle();
            this.items = new HashMap<>(l.getItems());
            return this;
        }

        /**
         * @return the items
         */
        public Map<String, ToggleButtonLocValue> getItems() {
            return Collections.unmodifiableMap(items);
        }

        /**
         * Add one item
         * 
         * @param key
         * @param locValue
         * @return this
         */
        public Builder addItem(String key, ToggleButtonLocValue locValue) {
            this.items.put(key, locValue);
            return this;
        }

        /**
         * @param items the items to set
         * @return this
         */
        public Builder setItems(Map<String, ToggleButtonLocValue> items) {
            this.items = new HashMap<>(items);
            return this;
        }

        /**
         * @return the title
         */
        public LabelLocValue getTitle() {
            return title;
        }

        /**
         * @param title the title to set
         * @return this
         */
        public Builder setTitle(LabelLocValue title) {
            this.title = title;
            return this;
        }

        @Override
        public MultiOptionLocValue build() {
            return new MultiOptionLocValue(this);
        }
    }

}
