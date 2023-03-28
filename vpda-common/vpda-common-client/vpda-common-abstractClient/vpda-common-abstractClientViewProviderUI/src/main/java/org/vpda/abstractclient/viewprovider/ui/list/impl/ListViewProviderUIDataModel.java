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
package org.vpda.abstractclient.viewprovider.ui.list.impl;

import java.util.List;

import org.vpda.clientserver.viewprovider.list.ListViewMappedRow;
import org.vpda.clientserver.viewprovider.list.ListViewResultsWithInfo;

/** Optional interface for internal data models of list ui providers */
public interface ListViewProviderUIDataModel {

    /** Clears all data from model */
    public void clear();

    /**
     * Add rows to model
     * 
     * @param results
     */
    public void addRows(ListViewResultsWithInfo results);

    /**
     * Adds rows from start
     * 
     * @param results
     */
    public void addRowsFromStart(ListViewResultsWithInfo results);

    /**
     * @return count of columns inside model
     */
    public int getColumnsCount();

    /**
     * @return number of rows
     */
    public int getRowsCount();

    /**
     * @param rowIndex
     * @return one row
     */
    public ListViewMappedRow getRow(int rowIndex);

    /**
     * Gets row by id
     * 
     * @param id Id of row
     * @return row or null if not found
     */
    public ListViewMappedRow getRow(Object id);

    /**
     * @param offset
     * @param limit
     * @return rows starting at offset with specified limit
     */
    public List<ListViewMappedRow> getRows(int offset, int limit);

    /**
     * @param offset
     * @param limit
     * @return number of rows
     */
    public int getRowsCount(int offset, int limit);

}
