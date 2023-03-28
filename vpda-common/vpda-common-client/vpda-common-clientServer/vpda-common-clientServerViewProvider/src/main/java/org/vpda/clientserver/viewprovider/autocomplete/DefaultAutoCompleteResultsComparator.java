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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.vpda.internal.common.util.Assert;

/**
 * Default comparator for results of autocomplete
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class DefaultAutoCompleteResultsComparator<T> implements Comparator<T> {

    private final AutoCompleteQuery query;
    private final List<Function<T, String>> getters;

    /**
     * Creates DefaultAutoCompleteResultsComparator
     * 
     * @param query
     * @param getterrs
     */
    public DefaultAutoCompleteResultsComparator(AutoCompleteQuery query, @SuppressWarnings("unchecked") Function<T, String>... getterrs) {
        this.query = Assert.isNotNullArgument(query, "query");
        this.getters = Arrays.asList(getterrs);
    }

    /**
     * @param query
     * @param getters
     */
    public DefaultAutoCompleteResultsComparator(AutoCompleteQuery query, List<Function<T, String>> getters) {
        this.query = Assert.isNotNullArgument(query, "query");
        this.getters = new ArrayList<>(getters);
    }

    @Override
    public int compare(T o1, T o2) {
        String term = query.isCaseinsensitive() ? query.getStringTerm().toUpperCase() : query.getStringTerm();
        int minDiff1 = getMinDiffForGetters(o1, term);
        int minDiff2 = getMinDiffForGetters(o2, term);
        if (minDiff1 == minDiff2) {
            return compareByGetters(o1, o2);
        }
        return minDiff1 < minDiff2 ? -1 : 1;
    }

    private int compareByGetters(T o1, T o2) {
        for (Function<T, String> getter : getters) {
            String value1 = getter.apply(o1);
            value1 = query.isCaseinsensitive() ? value1.toUpperCase() : value1;
            String value2 = getter.apply(o2);
            value2 = query.isCaseinsensitive() ? value2.toUpperCase() : value2;
            int compare = value1.compareTo(value2);
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }

    private int getMinDiffForGetters(T o, String term) {
        int min = Integer.MAX_VALUE;
        for (Function<T, String> getter : getters) {
            String value = getter.apply(o);
            value = query.isCaseinsensitive() ? value.toUpperCase() : value;
            int diff = getDiffChars(value, term);
            min = Math.min(min, diff);
        }
        return min;
    }

    private int getDiffChars(String s, String term) {
        if (s.contains(term)) {
            return s.length() - term.length();
        }
        return s.length();
    }

}
