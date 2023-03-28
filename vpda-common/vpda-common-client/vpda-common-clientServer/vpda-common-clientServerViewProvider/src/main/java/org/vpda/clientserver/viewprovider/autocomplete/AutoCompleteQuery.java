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

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Query for new Suggestions
 * 
 * @author kitko
 *
 */
public final class AutoCompleteQuery implements Serializable {

    private static final long serialVersionUID = 1635536328832044716L;
    private final Object term;
    private final int offset;
    private final int suggestionLimit;
    private final boolean caseinsensitive;
    private final Map<String, Object> hints;

    /**
     * Creates AutoCompleteQuery
     * 
     * @param term
     * @param offset
     * @param suggestionLimit
     * @param caseinsensitive
     * @param hints
     */
    public AutoCompleteQuery(Object term, int offset, int suggestionLimit, boolean caseinsensitive, Map<String, Object> hints) {
        super();
        this.term = term;
        this.offset = offset;
        this.suggestionLimit = suggestionLimit;
        this.caseinsensitive = caseinsensitive;
        this.hints = createHints(hints);
    }

    /**
     * Creates AutoCompleteQuery
     * 
     * @param term
     * @param offset
     * @param suggestionLimit
     * @param caseinsensitive
     */
    public AutoCompleteQuery(Object term, int offset, int suggestionLimit, boolean caseinsensitive) {
        this(term, offset, suggestionLimit, caseinsensitive, null);
    }

    private Map<String, Object> createHints(Map<String, Object> hints) {
        if (hints == null || hints.isEmpty()) {
            return Collections.emptyMap();
        }
        return new HashMap<>(hints);
    }

    /**
     * @param term
     * @param suggestionLimit
     * @return AutoCompleteQuery
     */
    public static AutoCompleteQuery caseinsensitiveQueryWithLimit(Object term, int suggestionLimit) {
        return new AutoCompleteQuery(term, 0, suggestionLimit, true, Collections.emptyMap());
    }

    /**
     * @return the term
     */
    public Object getTerm() {
        return term;
    }

    /**
     * @return string term
     */
    public String getStringTerm() {
        return term.toString();
    }

    /**
     * @return the suggestionLimit
     */
    public int getSuggestionLimit() {
        return suggestionLimit;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return the caseinsensitive
     */
    public boolean isCaseinsensitive() {
        return caseinsensitive;
    }

    /**
     * 
     * @return hints for query
     */
    public Map<String, Object> getHints() {
        return hints.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(hints);
    }

    @Override
    public String toString() {
        return "AutoCompleteQuery [term=" + term + ", offset=" + offset + ", suggestionLimit=" + suggestionLimit + ", caseinsensitive=" + caseinsensitive + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (caseinsensitive ? 1231 : 1237);
        result = prime * result + offset;
        result = prime * result + suggestionLimit;
        result = prime * result + ((term == null) ? 0 : term.hashCode());
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
        AutoCompleteQuery other = (AutoCompleteQuery) obj;
        if (caseinsensitive != other.caseinsensitive)
            return false;
        if (offset != other.offset)
            return false;
        if (suggestionLimit != other.suggestionLimit)
            return false;
        if (term == null) {
            if (other.term != null)
                return false;
        }
        else if (!term.equals(other.term))
            return false;
        return true;
    }

}
