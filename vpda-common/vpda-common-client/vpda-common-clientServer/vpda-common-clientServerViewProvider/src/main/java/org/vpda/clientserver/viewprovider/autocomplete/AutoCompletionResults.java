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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vpda.internal.common.util.Assert;

/**
 * Result of autocompletion query
 * 
 * @author kitko
 *
 */
public final class AutoCompletionResults implements Serializable {

    private static final long serialVersionUID = -7296077952286520087L;

    private enum Flags {
        FIRST, LAST
    }

    private final List<AutoCompletionItem> items;
    private final Set<Flags> flags;
    private final AutoCompleteQuery query;

    /**
     * Creates AutoCompletionResults
     * 
     * @param items
     * @param isFirst
     * @param isLast
     * @param query
     */
    public AutoCompletionResults(List<AutoCompletionItem> items, boolean isFirst, boolean isLast, AutoCompleteQuery query) {
        this.items = new ArrayList<>(Assert.isNotNullArgument(items, "items"));
        this.query = Assert.isNotNullArgument(query, "query");
        Set<Flags> aFlags = new HashSet<>(2);
        if (isFirst) {
            aFlags.add(Flags.FIRST);
        }
        if (isLast) {
            aFlags.add(Flags.LAST);
        }
        this.flags = !aFlags.isEmpty() ? EnumSet.copyOf(aFlags) : EnumSet.noneOf(Flags.class);
    }

    /**
     * @param query
     * @return empty results
     */
    public static AutoCompletionResults emptyResults(AutoCompleteQuery query) {
        return new AutoCompletionResults(Collections.emptyList(), query.getOffset() == 0, true, query);
    }

    /**
     * @param item
     * @param query
     * @return results
     */
    public static AutoCompletionResults singleResult(AutoCompletionItem item, AutoCompleteQuery query) {
        return new AutoCompletionResults(Collections.singletonList(item), query.getOffset() == 0, true, query);
    }

    /**
     * @param query
     * @param items
     * @return AutoCompletionResults
     */
    public static AutoCompletionResults results(AutoCompleteQuery query, AutoCompletionItem... items) {
        return results(query, Arrays.asList(items));
    }

    /**
     * @param query
     * @param asList
     * @return AutoCompletionResults
     */
    public static AutoCompletionResults results(AutoCompleteQuery query, List<AutoCompletionItem> asList) {
        boolean isLast = true;
        if (asList.size() > query.getSuggestionLimit()) {
            asList = asList.subList(0, query.getSuggestionLimit());
            isLast = true;
        }
        return new AutoCompletionResults(asList, query.getOffset() == 0, isLast, query);
    }

    /**
     * @return the query
     */
    public AutoCompleteQuery getQuery() {
        return query;
    }

    /**
     * 
     * @return true if this are first results
     */
    public boolean isFirst() {
        return flags.contains(Flags.FIRST);
    }

    /**
     * @return true if this are last results
     */
    public boolean isLast() {
        return flags.contains(Flags.LAST);
    }

    /**
     * @return true if results is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * @param index
     * @return row by index
     */
    public AutoCompletionItem getItem(int index) {
        return items.get(index);
    }

    /**
     * 
     * @return rowsCount
     */
    public int getItemsCount() {
        return items.size();
    }

    /**
     * @return rows
     */
    public List<AutoCompletionItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public String toString() {
        return "AutoCompletionResults [items=" + items + ", query=" + query + ", isFirst()=" + isFirst() + ", isLast()=" + isLast() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flags == null) ? 0 : flags.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((query == null) ? 0 : query.hashCode());
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
        AutoCompletionResults other = (AutoCompletionResults) obj;
        if (flags == null) {
            if (other.flags != null)
                return false;
        }
        else if (!flags.equals(other.flags))
            return false;
        if (items == null) {
            if (other.items != null)
                return false;
        }
        else if (!items.equals(other.items))
            return false;
        if (query == null) {
            if (other.query != null)
                return false;
        }
        else if (!query.equals(other.query))
            return false;
        return true;
    }

}
