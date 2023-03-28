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
import java.util.List;

import org.vpda.common.comps.CurrentData;
import org.vpda.internal.common.util.Assert;

/**
 * Results and mapping of columns together
 * 
 * @author kitko
 *
 */
public final class ListViewResultsWithInfo implements Serializable, CurrentData {

    private static final long serialVersionUID = 5546728492707844749L;
    private final ListViewResults results;
    private final ListViewResultsInfo info;

    /**
     * @return results
     */
    public ListViewResults getResults() {
        return results;
    }

    /**
     * @return info
     */
    public ListViewResultsInfo getInfo() {
        return info;
    }

    /**
     * Creates ListViewResultsWithInfo
     * 
     * @param results
     * @param info
     */
    public ListViewResultsWithInfo(ListViewResults results, ListViewResultsInfo info) {
        this.results = Assert.isNotNullArgument(results, "results");
        this.info = Assert.isNotNullArgument(info, "info");
    }

    /**
     * Creates ListViewResultsWithInfo
     * 
     * @param results
     * @param info
     * @return ListViewResultsWithInfo
     */
    public static ListViewResultsWithInfo create(ListViewResults results, ListViewResultsInfo info) {
        return new ListViewResultsWithInfo(results, info);
    }

    /**
     * Returns cell with given row and index of column
     * 
     * @param row
     * @param columnId
     * @return column value
     */
    public Object getColumnValue(int row, String columnId) {
        int columnIndex = info.getColumnIndex(columnId);
        return columnIndex != -1 ? results.getColumnValue(row, columnIndex) : null;
    }

    /**
     * @param row
     * @param column
     * @return column value by row and column index
     */
    public Object getColumnValue(int row, int column) {
        return results.getColumnValue(row, column);
    }

    /**
     * Returns cell with given row and index of column
     * 
     * @param row
     * @param columnId
     * @return column value
     */
    public Object getMandatoryColumnValue(int row, String columnId) {
        int columnIndex = info.getMandatoryColumnIndex(columnId);
        return results.getColumnValue(row, columnIndex);
    }

    /**
     * @return rows count of results
     */
    public int getRowsCount() {
        return results.getRowsCount();
    }

    /**
     * @param row
     * @return row
     */
    public ListViewRow getRow(int row) {
        return results.getRow(row);
    }

    /**
     * Gets one mapped row
     * 
     * @param row
     * @return mapped row
     */
    public ListViewMappedRow getMappedRow(int row) {
        ListViewRow row2 = results.getRow(row);
        Object idValue = row2.getColumnValue(info.getIdColumnIndex());
        return new ListViewMappedRow(info.getColumnsIds(), row2, idValue);
    }

    /**
     * Gets mapped row with concrete columns only
     * 
     * @param row
     * @param columns
     * @return new converted mapped row
     */
    public ListViewMappedRow getMappedRow(int row, List<String> columns) {
        List<Object> values = new ArrayList<Object>(columns.size());
        Object idValue = getColumnValue(row, info.getIdColumnIndex());
        for (String col : columns) {
            Object value = getColumnValue(row, col);
            values.add(value);
        }
        return new ListViewMappedRow(columns, values, idValue);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListViewResultsWithInfo : ").append('\n');
        builder.append(" info = ").append(info).append('\n');
        builder.append(" results = ").append(results);
        return builder.toString();
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public int size() {
        return results.getRowsCount();
    }

}
