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

import java.util.ArrayList;
import java.util.List;

import org.vpda.common.comps.MemberUtils;


/**
 * Utility class for list views
 * 
 * @author kitko
 *
 */
public final class ListViewUtils {
    private ListViewUtils() {
    }

    /**
     * Converts list of columnInfo to list of column ids
     * 
     * @param columns
     * @return list of column ids
     */
    public static List<String> getColumnIds(final List<Column> columns) {
        List<String> result = new ArrayList<>(columns.size());
        for (Column columnInfo : columns) {
            result.add(columnInfo.getId());
        }
        return result;
    }

    /**
     * Computes columnId from group and localId
     * 
     * @param groupId
     * @param localColumnId
     * @return id of column
     */
    public static String getColumnId(String groupId, String localColumnId) {
        return MemberUtils.getMemberId(groupId, localColumnId);
    }

    /**
     * Computes local id from column
     * 
     * @param columnId
     * @return localId
     */
    public static String getLocalColumnId(String columnId) {
        return MemberUtils.getLocalMemberId(columnId);
    }

    /**
     * Determine if localId of column is valid
     * 
     * @param localId
     * @return true if local id is valid
     */
    public static boolean isValidColumnLocalId(String localId) {
        return true;
    }
    
    public static String[] resolveIdsPaths(String colId){
        return MemberUtils.resolveIdsPaths(colId);
    }

}
