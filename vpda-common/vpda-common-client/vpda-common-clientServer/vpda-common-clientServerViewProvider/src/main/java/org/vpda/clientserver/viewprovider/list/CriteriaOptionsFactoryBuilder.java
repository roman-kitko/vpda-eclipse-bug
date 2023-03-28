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
import java.util.Arrays;
import java.util.List;

import org.vpda.clientserver.viewprovider.criteria.CriteriaOption;
import org.vpda.clientserver.viewprovider.criteria.CriteriaOptions;
import org.vpda.clientserver.viewprovider.criteria.SingleButOperatorSelection;
import org.vpda.common.comps.ui.DateFieldAC;
import org.vpda.common.comps.ui.IntegerFieldAC;
import org.vpda.common.comps.ui.ListFieldAC;
import org.vpda.common.comps.ui.TextFieldAC;
import org.vpda.common.criteria.Operator;

/**
 * Builder for {@link CriteriaOptionsFactory}
 * 
 * @author kitko
 *
 */
public final class CriteriaOptionsFactoryBuilder {

    private static final class FullCriteriaFactory implements CriteriaOptionsFactory, Serializable {
        private static final long serialVersionUID = -7567588124257364847L;

        @Override
        public CriteriaOptions createSearchingOptions(ListViewProviderInfo info) {
            List<CriteriaOption> beans = new ArrayList<CriteriaOption>();
            for (Column columnInfo : info.getColumns()) {
                if (columnInfo.isInvisible()) {
                    continue;
                }
                CriteriaOption createCriteriaOption = createCriteriaOption(columnInfo);
                if (createCriteriaOption != null) {
                    beans.add(createCriteriaOption);
                }
            }
            return new CriteriaOptions(beans);
        }
    }

    private static final class CustomCriteriaFactory implements CriteriaOptionsFactory, Serializable {
        private static final long serialVersionUID = 3825111744804831097L;
        private List<String> columns;

        private CustomCriteriaFactory(List<String> columns) {
            this.columns = new ArrayList<String>(columns);
        }

        @Override
        public CriteriaOptions createSearchingOptions(ListViewProviderInfo info) {
            List<CriteriaOption> beans = new ArrayList<CriteriaOption>(columns.size());
            for (String columnId : columns) {
                Column columnInfo = info.getColumn(columnId);
                if (columnInfo != null) {
                    beans.add(createCriteriaOption(columnInfo));
                }
            }
            return new CriteriaOptions(beans);
        }

    }

    /**
     * Creates CriteriaBean for columnInfo
     * 
     * @param columnInfo
     * @return CriteriaBean
     */
    public static CriteriaOption createCriteriaOption(Column columnInfo) {
        Class clazz = columnInfo.getType();
        String id = columnInfo.getId();
        if (String.class.equals(clazz)) {
            List<Operator> operators = Arrays.asList(Operator.EQ, Operator.NOT_EQ, Operator.GT, Operator.GT_EQ, Operator.LT, Operator.LT_EQ, Operator.IS_NULL, Operator.IS_NOT_NULL, Operator.IN,
                    Operator.CONTAINS, Operator.STARTS_WITH, Operator.ENDS_WITH);
            SingleButOperatorSelection selection = new SingleButOperatorSelection(new TextFieldAC(id), Operator.IN, ListFieldAC.commaListComponent(id, String.class));
            return new CriteriaOption(id, operators, selection);
        }
        else if (Integer.class.equals(clazz)) {
            List<Operator> operators = Arrays.asList(Operator.EQ, Operator.NOT_EQ, Operator.GT, Operator.GT_EQ, Operator.LT, Operator.LT_EQ, Operator.IS_NULL, Operator.IS_NOT_NULL, Operator.IN);
            SingleButOperatorSelection selection = new SingleButOperatorSelection(new IntegerFieldAC(id), Operator.IN, ListFieldAC.commaListComponent(id, Integer.class));
            return new CriteriaOption(id, operators, selection);

        }
        else if (Long.class.equals(clazz)) {
            List<Operator> operators = Arrays.asList(Operator.EQ, Operator.NOT_EQ, Operator.GT, Operator.GT_EQ, Operator.LT, Operator.LT_EQ, Operator.IS_NULL, Operator.IS_NOT_NULL, Operator.IN);
            SingleButOperatorSelection selection = new SingleButOperatorSelection(new IntegerFieldAC(id), Operator.IN, ListFieldAC.commaListComponent(id, Long.class));
            return new CriteriaOption(id, operators, selection);
        }
        else if (java.util.Date.class.isAssignableFrom(clazz)) {
            List<Operator> operators = Arrays.asList(Operator.EQ, Operator.NOT_EQ, Operator.GT, Operator.GT_EQ, Operator.LT, Operator.LT_EQ, Operator.IS_NULL, Operator.IS_NOT_NULL, Operator.IN);
            SingleButOperatorSelection selection = new SingleButOperatorSelection(new DateFieldAC(id), Operator.IN, ListFieldAC.commaListComponent(id, java.util.Date.class));
            return new CriteriaOption(id, operators, selection);
        }
        return null;
    }

    /**
     * @return full criteria where we can search by all columns from
     *         {@link ListViewProviderInfo}
     */
    public CriteriaOptionsFactory createFullCriteriaFactory() {
        return new FullCriteriaFactory();
    }

    /**
     * @param columns
     * @return custom criteria
     */
    public CriteriaOptionsFactory createCustomCriteriaFactory(List<String> columns) {
        return new CustomCriteriaFactory(columns);
    }

}