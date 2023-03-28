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
package org.vpda.abstractclient.viewprovider.ui.generic;

import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderConstants;
import org.vpda.clientserver.viewprovider.list.ListViewResultsWithInfo;
import org.vpda.internal.common.util.Assert;

/**
 * Will set ID for detail
 * 
 * @author kitko
 *
 */
public final class SetIDAdjustCurrentData implements AdjustCurrentData {
    private static final long serialVersionUID = 2122597198870394673L;
    private final String idColumn;
    private final String[] keys;

    @Override
    public void adjustCurrentData(ViewProviderCallerItem<?> item) {
        ListViewResultsWithInfo results = item.castCurrrentData(ListViewResultsWithInfo.class);
        if (results == null) {
            return;
        }
        if (results.getRowsCount() == 1) {
            Object id = results.getColumnValue(0, idColumn);
            for (String key : keys) {
                item.setCurrentDataValue(key, id);
            }
        }
    }

    /**
     * Creates SetIDAdjustInitData
     * 
     * @param idColumn
     */
    public SetIDAdjustCurrentData(String idColumn) {
        this(idColumn, ViewProviderConstants.ID);
    }

    /**
     * Creates SetIDAdjustInitData
     * 
     * @param idColumn
     * @param keys     by which we will store ids
     */
    public SetIDAdjustCurrentData(String idColumn, String... keys) {
        super();
        this.idColumn = Assert.isNotEmptyArgument(idColumn, "idColumn");
        this.keys = Assert.isNotNullArgument(keys, "keys");
    }

}
