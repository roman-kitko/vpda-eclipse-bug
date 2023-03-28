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
import java.util.Collections;
import java.util.List;

import org.vpda.internal.common.util.OrderedMap;

/**
 * Info for rows - mainly columns
 * 
 * @author kitko
 *
 */
public final class ListViewResultsInfo implements Serializable {
    private static final long serialVersionUID = 1192403855826217750L;
    private final OrderedMap<String, String> columns;
    private final String idColumn;
    private final int idColumnIndex;

    /**
     * Returns id of i-ties column
     * 
     * @param index
     * @return i-ties column
     */
    public String getColumn(int index) {
        return columns.getKey(index);
    }

    /**
     * Tries to get index for column
     * 
     * @param column id
     * @return index of column , if not found -1
     */
    public int getColumnIndex(String column) {
        int order = columns.getOrderOfKey(column);
        return order;
    }

    /**
     * Tries to find index of column , if column is not found throws exception
     * 
     * @param column id
     * @return column index or throws exception if not found
     */
    public int getMandatoryColumnIndex(String column) {
        int order = columns.getOrderOfKey(column);
        if (order == -1) {
            throw new IllegalArgumentException("Column is not found in this info");
        }
        return order;
    }

    /**
     * 
     * @return number of columns
     */
    public int getColumnsCount() {
        return columns.size();
    }

    /**
     * @return list of all columns ids
     */
    public List<String> getColumnsIds() {
        return Collections.unmodifiableList(columns.keys());
    }

    /**
     * Test method whether this info contains column
     * 
     * @param column id
     * @return true if info contains column
     */
    public boolean containsColumn(String column) {
        return columns.containsKey(column);
    }

    /**
     * Construct and add columns to results info
     * 
     * @param columns
     * @param idColumn
     */
    public ListViewResultsInfo(List<String> columns, String idColumn) {
        if (!columns.contains(idColumn)) {
            throw new IllegalArgumentException("Columns do not contain idColumn [" + idColumn + "]");
        }
        this.columns = new OrderedMap<String, String>(columns.size());
        for (String column : columns) {
            this.columns.put(column, column);
        }
        this.idColumn = idColumn;
        idColumnIndex = getColumnIndex(idColumn);
    }

    /**
     * Construct and add columns to results info
     * 
     * @param columns
     * @param idColumn
     * @param dummy    - dummy parameter - we can have list<String> and
     *                 List<ColumnInfo>
     */
    public ListViewResultsInfo(List<? extends Column> columns, String idColumn, String dummy) {
        this.columns = new OrderedMap<String, String>(columns.size());
        boolean idColumnOk = false;
        for (Column column : columns) {
            this.columns.put(column.getId(), column.getId());
            if (column.getId().equals(idColumn)) {
                idColumnOk = true;
            }
        }
        if (!idColumnOk) {
            throw new IllegalArgumentException("Columns do not contain idColumn [" + idColumn + "]");
        }
        this.idColumn = idColumn;
        idColumnIndex = getColumnIndex(idColumn);
    }

    /**
     * Test method whether this info contains column
     * 
     * @param groupId
     * @param columnLocalId
     * @return true if info contains column
     */
    public boolean containsColumn(String groupId, String columnLocalId) {
        return containsColumn(ListViewUtils.getColumnId(groupId, columnLocalId));
    }

    /**
     * Tries to get index for column by group and column local id
     * 
     * @param groupId
     * @param columnLocalId
     * @return index of column , if not found -1
     */
    public int getColumnIndex(String groupId, String columnLocalId) {
        return getColumnIndex(ListViewUtils.getColumnId(groupId, columnLocalId));
    }

    /**
     * Tries to find index of column by group and column local id , if column is not
     * found throws exception
     * 
     * @param groupId
     * @param columnLocalId
     * @return column index or throws exception if not found
     */
    public int getMandatoryColumnIndex(String groupId, String columnLocalId) {
        return getMandatoryColumnIndex(ListViewUtils.getColumnId(groupId, columnLocalId));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columns == null) ? 0 : columns.hashCode());
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
        final ListViewResultsInfo other = (ListViewResultsInfo) obj;
        if (columns == null) {
            if (other.columns != null)
                return false;
        }
        else if (!columns.equals(other.columns))
            return false;
        return true;
    }

    /**
     * 
     * @return id of key column (usually 'id')
     */
    public String getIdColumn() {
        return idColumn;
    }

    /**
     * @return index of id column
     */
    public int getIdColumnIndex() {
        return idColumnIndex;
    }

    @Override
    public String toString() {
        return "ListViewResultsInfo : " + columns.toString();
    }

}
