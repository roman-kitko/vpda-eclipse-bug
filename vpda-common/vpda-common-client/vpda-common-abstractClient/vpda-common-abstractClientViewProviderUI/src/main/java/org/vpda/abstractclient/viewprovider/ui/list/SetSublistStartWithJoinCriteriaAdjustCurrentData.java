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
package org.vpda.abstractclient.viewprovider.ui.list;

import org.vpda.clientserver.viewprovider.AdjustCurrentData;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderConstants;
import org.vpda.clientserver.viewprovider.list.ListViewResultsWithInfo;
import org.vpda.common.criteria.BinCriterion;
import org.vpda.common.criteria.CriteriaTree;
import org.vpda.common.criteria.CriteriaTreeImpl;
import org.vpda.common.criteria.Criterion;
import org.vpda.common.criteria.Operator;
import org.vpda.common.criteria.Reference;
import org.vpda.common.criteria.Value;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.StringUtil;

/**
 * Will push criteria to item
 * 
 * @author kitko
 *
 */
public final class SetSublistStartWithJoinCriteriaAdjustCurrentData implements AdjustCurrentData {
    private final String startWithValueColumn;
    private final String joinColumn;
    /**
     * 
     */
    private static final long serialVersionUID = 5475904809222942261L;

    @SuppressWarnings("unchecked")
    @Override
    public void adjustCurrentData(ViewProviderCallerItem<?> item) {
        ListViewResultsWithInfo results = item.castCurrrentData(ListViewResultsWithInfo.class);
        if (results == null) {
            return;
        }
        if (results.getRowsCount() == 1) {
            Object value = results.getColumnValue(0, startWithValueColumn);
            if (!(value instanceof String)) {
                return;
            }
            if (StringUtil.isEmpty((String) value)) {
                return;
            }
            Criterion join = new BinCriterion(new Reference(joinColumn), Operator.STARTS_WITH, new Value<String>((String) value));
            CriteriaTree criteria = new CriteriaTreeImpl("sublist", join);
            item.setCurrentDataValue(ViewProviderConstants.SUBLIST_CRITERION_KEY, criteria);
        }
    }

    /**
     * @param idColumn
     * @param joinColumn
     */
    public SetSublistStartWithJoinCriteriaAdjustCurrentData(String idColumn, String joinColumn) {
        super();
        this.startWithValueColumn = Assert.isNotEmptyArgument(idColumn, "idColumn");
        this.joinColumn = Assert.isNotEmptyArgument(joinColumn, "joinColumn");
    }
}
