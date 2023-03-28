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
/**
 * 
 */
package org.vpda.clientserver.viewprovider.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.internal.common.util.OrderedMap;

/**
 * One single row . Map of values by columns
 * 
 * @author kitko
 *
 */
public final class ListViewMappedRow implements Serializable {
    private static final long serialVersionUID = 5885862203620438438L;
    private final OrderedMap<String, Object> values;
    private final Object idValue;

    /**
     * Creates ListViewMappedRow
     * 
     * @param columns
     * @param values
     * @param idValue
     */
    public ListViewMappedRow(List<String> columns, List<? extends Object> values, Object idValue) {
        if (columns.size() != values.size()) {
            throw new IllegalArgumentException("Column names and values size do not match");
        }
        this.values = new OrderedMap<String, Object>();
        int i = 0;
        for (String col : columns) {
            Object value = values.get(i);
            this.values.put(col, value);
            i++;
        }
        this.idValue = idValue;
    }

    /**
     * Creates ListViewMappedRow
     * 
     * @param columns
     * @param values
     * @param idValue
     */
    public ListViewMappedRow(List<String> columns, ListViewRow values, Object idValue) {
        if (columns.size() != values.getLength()) {
            throw new IllegalArgumentException("Column names and values size do not match");
        }
        this.values = new OrderedMap<String, Object>();
        int i = 0;
        for (String col : columns) {
            Object columnValue = values.getColumnValue(i);
            this.values.put(col, columnValue);
            i++;
        }
        this.idValue = idValue;
    }

    /**
     * @return list of columns of row
     */
    public List<String> getColumns() {
        return Collections.unmodifiableList(values.keys());
    }

    /**
     * @param i
     * @return name of column
     */
    public String getColumnName(int i) {
        return values.getKey(i);
    }

    /**
     * @param col
     * @return column value
     */
    public Object getColumnValue(String col) {
        return values.get(col);
    }

    /**
     * @param col
     * @param type
     * @return casted value
     */
    public <T> T getColumnValue(String col, Class<T> type) {
        return type.cast(values.get(col));
    }

    /**
     * @return size of row
     */
    public int getColumnCount() {
        return values.size();
    }

    /**
     * @param col
     * @return index of column or -1 if no column
     */
    public int getColumnIndex(String col) {
        return values.getOrderOfKey(col);
    }

    /**
     * @param colId
     * @return true if row contains column
     */
    public boolean containsColumn(String colId) {
        return values.containsKey(colId);
    }

    /**
     * @param index
     * @return value for cell
     */
    public Object getColumnValue(int index) {
        return values.getValue(index);
    }

    /**
     * @param index
     * @param type
     * @return value for column
     */
    public <T> T getColumnValue(int index, Class<T> type) {
        return type.cast(values.getValue(index));
    }

    /**
     * Converts a row to different by column ids
     * 
     * @param colIds
     * @return new row
     */
    public ListViewRow convertRow(List<String> colIds) {
        List<Object> row = new ArrayList<Object>(colIds.size());
        for (String colId : colIds) {
            row.add(containsColumn(colId) ? getColumnValue(colId) : null);
        }
        return new ListViewRow(row.toArray());
    }

    /**
     * Converts a row to different by column ids
     * 
     * @param colIds
     * @return new row
     */
    public ListViewMappedRow convertToMappedRow(List<String> colIds) {
        return new ListViewMappedRow(colIds, convertRow(colIds), idValue);
    }

    /**
     * @return the idValue
     */
    public Object getIdValue() {
        return idValue;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListViewMappedRow [values=");
        builder.append(values);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        if (idValue != null) {
            return idValue.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + ((values == null) ? 0 : values.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ListViewMappedRow other = (ListViewMappedRow) obj;
        if (idValue != null) {
            return idValue.equals(other.idValue);
        }
        if (values == null) {
            if (other.values != null) {
                return false;
            }
        }
        else if (!values.equals(other.values)) {
            return false;
        }
        return true;
    }

}
