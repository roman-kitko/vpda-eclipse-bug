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
import java.util.Arrays;

/**
 * One row for list view holding columns data.
 * 
 * @author kitko
 *
 */
public final class ListViewRow implements Serializable {
    private static final long serialVersionUID = 1089142182806933447L;
    private final Object[] data;

    /**
     * 
     * @param i
     * @return column value
     */
    public Object getColumnValue(int i) {
        return data[i];
    }

    /**
     * 
     * @return length of row
     */
    public int getLength() {
        return data.length;
    }

    /**
     * Constructor for row using column array
     * 
     * @param data
     */
    public ListViewRow(Object... data) {
        if (data == null) {
            throw new IllegalArgumentException("Data argument cannot be null");
        }
        this.data = Arrays.copyOf(data, data.length);
    }

    @Override
    public String toString() {
        return "ListViewRow [data=" + Arrays.toString(data) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(data);
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
        ListViewRow other = (ListViewRow) obj;
        if (!Arrays.equals(data, other.data)) {
            return false;
        }
        return true;
    }

}
