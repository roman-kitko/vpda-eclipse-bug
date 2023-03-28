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
package org.vpda.clientserver.viewprovider.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.vpda.common.comps.CurrentData;
import org.vpda.internal.common.util.Assert;

/**
 * Results of data retrieval
 * 
 * @author kitko
 *
 */
public final class ListViewResults implements Serializable, Iterable<ListViewRow>, CurrentData {
    private static final long serialVersionUID = -4742177601168096443L;
    private final List<ListViewRow> rows;

    private final ReadingFrom readingFrom;
    private final Integer offset;

    private enum Flags {
        FIRST, LAST;
    }

    private final Set<Flags> flags;

    /**
     * @param index
     * @return row by index
     */
    public ListViewRow getRow(int index) {
        return rows.get(index);
    }

    /**
     * 
     * @return rowsCount
     */
    public int getRowsCount() {
        return rows.size();
    }

    /**
     * Creates empty results with now row
     */
    public ListViewResults() {
        rows = new ArrayList<ListViewRow>(1);
        flags = EnumSet.of(Flags.FIRST);
        readingFrom = ReadingFrom.FROM_START;
        offset = Integer.valueOf(0);
    }

    /**
     * @return rows
     */
    public List<ListViewRow> getRows() {
        return Collections.unmodifiableList(rows);
    }

    /**
     * Constructor using rows and info
     * 
     * @param rows
     * @param isFirst
     * @param isLast
     * @param readingFrom
     * @param offset
     */
    public ListViewResults(List<ListViewRow> rows, boolean isFirst, boolean isLast, ReadingFrom readingFrom, Integer offset) {
        this.rows = new ArrayList<ListViewRow>(Assert.isNotNullArgument(rows, "rows"));
        Set<Flags> aFlags = new HashSet<>();
        if (isFirst) {
            aFlags.add(Flags.FIRST);
        }
        if (isLast) {
            aFlags.add(Flags.LAST);
        }
        this.flags = !aFlags.isEmpty() ? EnumSet.copyOf(aFlags) : EnumSet.noneOf(Flags.class);
        this.readingFrom = readingFrom;
        this.offset = offset;
    }

    /**
     * Constructor using rows and info
     * 
     * @param rows
     * @param isFirst
     * @param isLast
     */
    public ListViewResults(List<ListViewRow> rows, boolean isFirst, boolean isLast) {
        this(rows, isFirst, isLast, ReadingFrom.FROM_START, 0);
    }

    private ListViewResults(List<ListViewRow> rows, Set<Flags> aFlags, ReadingFrom readingFrom, Integer offset) {
        this.rows = rows;
        this.flags = aFlags;
        this.readingFrom = readingFrom;
        this.offset = offset;
    }

    /**
     * Creates new {@link ListViewResults} with reading info
     * 
     * @param readingFrom
     * @param offset
     * @return ListViewResults
     */
    public ListViewResults createWithReadingInfo(ReadingFrom readingFrom, Integer offset) {
        return new ListViewResults(this.rows, flags, readingFrom, offset);
    }

    @Override
    public Iterator<ListViewRow> iterator() {
        return rows.iterator();
    }

    /**
     * Creates subList of results
     * 
     * @param fromIndex - inclusive
     * @param toIndex   - exclusive
     * @return subresults of this result
     */
    public ListViewResults subResults(int fromIndex, int toIndex) {
        List<ListViewRow> subRows = new ArrayList<ListViewRow>(toIndex - fromIndex + 1);
        for (int i = fromIndex; i < toIndex; i++) {
            subRows.add(rows.get(i));
        }
        boolean isFirst = (fromIndex == 0) && isFirst();
        boolean isLast = (toIndex >= rows.size() - 1) && isLast();
        ListViewResults result = new ListViewResults(subRows, isFirst, isLast);
        return result;
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
     * Gets column value from row
     * 
     * @param row
     * @param column
     * @return column value for row
     */
    public Object getColumnValue(int row, int column) {
        return getRow(row).getColumnValue(column);
    }

    /**
     * @return true if results is empty
     */
    @Override
    public boolean isEmpty() {
        return rows.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListViewResults : flags=").append(flags);
        builder.append(", rowsCount=").append(rows.size());
        if (!rows.isEmpty()) {
            builder.append('\n');
        }
        for (int i = 0, size = rows.size(); i < size; i++) {
            ListViewRow row = rows.get(i);
            builder.append(i).append(' ').append('[');
            for (int j = 0, length = row.getLength(); j < length; j++) {
                builder.append(String.valueOf(row.getColumnValue(j)));
                if (j < length - 1) {
                    builder.append(',');
                }
            }
            builder.append(']');
            if (i < size - 1) {
                builder.append('\n');
            }
            if (i > 100) {
                builder.append(".......");
                break;
            }
        }
        return builder.toString();
    }

    @Override
    public int size() {
        return rows.size();
    }

    /**
     * @return readingFrom
     */
    public ReadingFrom getReadingFrom() {
        return readingFrom;
    }

    /**
     * @return offset
     */
    public Integer getOffset() {
        return offset;
    }

}
