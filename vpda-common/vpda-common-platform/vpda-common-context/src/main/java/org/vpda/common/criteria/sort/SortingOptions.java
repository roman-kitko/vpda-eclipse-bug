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
package org.vpda.common.criteria.sort;

import java.io.Serializable;
import java.util.List;

/**
 * Sorting support defines options how user can define sort. User can either
 * choose from predefined sort or define custom sort
 * 
 * @author kitko
 *
 */
public abstract class SortingOptions implements Serializable {
    private static final long serialVersionUID = 941196046085114803L;

    /** */
    protected SortingOptions() {
    }

    /**
     * @return list of possible sorting columns
     */
    public abstract List<String> getSortingColumns();

    /**
     * @return true if user can define custom sort
     */
    public abstract boolean canDefineCustomSort();

    /**
     * @return list of predefined sorts
     */
    public abstract List<Sort> getPredefinedSorts();

    /**
     * @param columnId
     * @return true if we can define sort by column
     */
    public abstract boolean canSortByColumn(String columnId);
}
