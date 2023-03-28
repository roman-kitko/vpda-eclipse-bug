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

/**
 * Will push criteria to item
 * 
 * @author kitko
 *
 */
public final class SetSublistSimpleJoinCriteriaAdjustCurrentData implements AdjustCurrentData {
    private final String idColumn;
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
            Object id = results.getColumnValue(0, idColumn);
            Criterion join = new BinCriterion(new Reference(joinColumn), Operator.EQ, new Value<Long>((Long) id));
            CriteriaTree criteria = new CriteriaTreeImpl("sublist", join);
            item.setCurrentDataValue(ViewProviderConstants.SUBLIST_CRITERION_KEY, criteria);
        }
    }

    /**
     * @param idColumn
     * @param joinColumn
     */
    public SetSublistSimpleJoinCriteriaAdjustCurrentData(String idColumn, String joinColumn) {
        super();
        this.idColumn = Assert.isNotEmptyArgument(idColumn, "idColumn");
        this.joinColumn = Assert.isNotEmptyArgument(joinColumn, "joinColumn");
    }
}
