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
package org.vpda.common.comps.autocomplete;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.util.Builder;
import org.vpda.internal.common.util.Assert;

/**
 * Reference definition to autoComplete
 * 
 * @author kitko
 *
 */
public class AutoCompleteDef implements Serializable {
    private static final long serialVersionUID = -5560310367294953421L;

    // Id of this completion
    private final String completeId;
    // ViewProviderDef code of autocompletion
    private final String defCode;
    // Id of field we will use to get value back to ui from returned results
    private final Map<String, String> valueRefMap;
    private final int suggestionLimit;
    private final boolean caseinsensitive;
    private final int minChars;
    private final Map<String, Object> hints;

    private AutoCompleteDef(AutoCompleteDefBuilder autoCompleteFieldDefBuilder) {
        this.completeId = Assert.isNotEmptyArgument(autoCompleteFieldDefBuilder.getCompleteId(), "completeId");
        this.defCode = Assert.isNotEmptyArgument(autoCompleteFieldDefBuilder.getDefCode(), "defCode");
        this.valueRefMap = new HashMap<>(autoCompleteFieldDefBuilder.valueRefMap);
        this.suggestionLimit = autoCompleteFieldDefBuilder.getSuggestionLimit();
        this.minChars = autoCompleteFieldDefBuilder.getMinChars();
        this.caseinsensitive = autoCompleteFieldDefBuilder.isCaseinsensitive();
        this.hints = createHints(autoCompleteFieldDefBuilder.hints);
    }

    private Map<String, Object> createHints(Map<String, Object> hints) {
        if (hints == null || hints.isEmpty()) {
            return Collections.emptyMap();
        }
        return new HashMap<>(hints);
    }

    /**
     * @return the hints
     */
    public Map<String, Object> getHints() {
        return hints.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(hints);
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

    /**
     * @return the defCode
     */
    public String getDefCode() {
        return defCode;
    }

    /**
     * @return the completeId
     */
    public String getCompleteId() {
        return completeId;
    }

    /**
     * @return the valueRefId
     */
    public Map<String, String> getValueRefMap() {
        return Collections.unmodifiableMap(valueRefMap);
    }

    /**
     * @param key
     * @return lookup code
     */
    public String getLookupCode(String key) {
        return valueRefMap.get(key);
    }

    /**
     * @return the minChars
     */
    public int getMinChars() {
        return minChars;
    }

    @Override
    public String toString() {
        return "AutoCompleteFieldDef [completeId=" + completeId + ", defCode=" + defCode + ", valueRefMap=" + valueRefMap + ", suggestionLimit=" + suggestionLimit + ", caseinsensitive="
                + caseinsensitive + ", minChars=" + minChars + "]";
    }

    /**
     * @return AutoCompleteFieldDefBuilder
     */
    public AutoCompleteDefBuilder createBuilderWithSameValues() {
        return new AutoCompleteDefBuilder().setValues(this);
    }

    /**
     * @author kitko
     *
     */
    public static final class AutoCompleteDefBuilder implements Builder<AutoCompleteDef> {
        private String completeId;
        private String defCode;
        private Map<String, String> valueRefMap = new HashMap<>(2);
        private int suggestionLimit = 20;
        private boolean caseinsensitive = true;
        private int minChars = 2;
        private Map<String, Object> hints;

        /**
         * @return the hints
         */
        public Map<String, Object> getHints() {
            return hints == null || hints.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(hints);
        }

        /**
         * Set new hints
         * 
         * @param hints the hints to set
         * @return this
         */
        public AutoCompleteDefBuilder setHints(Map<String, Object> hints) {
            this.hints = (hints == null || hints.isEmpty()) ? null : new HashMap<>(hints);
            return this;
        }

        /**
         * Add hints
         * 
         * @param hints
         * @return this
         */
        public AutoCompleteDefBuilder addHints(Map<String, Object> hints) {
            if (hints == null || hints.isEmpty()) {
                return this;
            }
            if (this.hints == null) {
                this.hints = new HashMap<>(hints.size());
            }
            this.hints.putAll(hints);
            return this;
        }

        /**
         * @param name
         * @param value
         * @return this
         */
        public AutoCompleteDefBuilder addHint(String name, Object value) {
            this.hints.put(name, value);
            return this;
        }

        /**
         * 
         * @param name
         * @return one hint
         */
        public AutoCompleteDefBuilder removeHint(String name) {
            if (hints == null || hints.isEmpty()) {
                return this;
            }
            hints.remove(name);
            return this;
        }

        /**
         * Remove all hints
         * 
         * @param name
         * @return this
         */
        public AutoCompleteDefBuilder clearHints(String name) {
            this.hints = null;
            return this;
        }

        /**
         * @return the completeId
         */
        public String getCompleteId() {
            return completeId;
        }

        /**
         * @param completeId the completeId to set
         * @return this
         */
        public AutoCompleteDefBuilder setCompleteId(String completeId) {
            this.completeId = completeId;
            return this;
        }

        /**
         * @return the defCode
         */
        public String getDefCode() {
            return defCode;
        }

        /**
         * @param defCode the defCode to set
         * @return this
         */
        public AutoCompleteDefBuilder setDefCode(String defCode) {
            this.defCode = defCode;
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
        public AutoCompleteDefBuilder setSuggestionLimit(int suggestionLimit) {
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
        public AutoCompleteDefBuilder setCaseinsensitive(boolean caseinsensitive) {
            this.caseinsensitive = caseinsensitive;
            return this;
        }

        /**
         * @return the minChars
         */
        public int getMinChars() {
            return minChars;
        }

        /**
         * @param minChars the minChars to set
         * @return this
         */
        public AutoCompleteDefBuilder setMinChars(int minChars) {
            this.minChars = minChars;
            return this;
        }

        /**
         * @return the valueRefMap
         */
        public Map<String, String> getValueRefMap() {
            return valueRefMap;
        }

        /**
         * @param valueRefMap the valueRefMap to set
         * @return this
         */
        public AutoCompleteDefBuilder setValueRefMap(Map<String, String> valueRefMap) {
            this.valueRefMap = new HashMap<String, String>(valueRefMap);
            return this;
        }

        /**
         * @param src
         * @param lookupCode
         * @return this
         */
        public AutoCompleteDefBuilder setValueRefMapping(String src, String lookupCode) {
            this.valueRefMap.put(src, lookupCode);
            return this;
        }

        @Override
        public Class<? extends AutoCompleteDef> getTargetClass() {
            return AutoCompleteDef.class;
        }

        @Override
        public AutoCompleteDef build() {
            return new AutoCompleteDef(this);
        }

        /**
         * @param f
         * @return this
         */
        public AutoCompleteDefBuilder setValues(AutoCompleteDef f) {
            this.caseinsensitive = f.isCaseinsensitive();
            this.completeId = f.getCompleteId();
            this.defCode = f.getDefCode();
            this.minChars = f.getMinChars();
            this.suggestionLimit = f.getSuggestionLimit();
            this.valueRefMap = new HashMap<>(f.getValueRefMap());
            setHints(f.hints);
            return this;
        }

    }

}
