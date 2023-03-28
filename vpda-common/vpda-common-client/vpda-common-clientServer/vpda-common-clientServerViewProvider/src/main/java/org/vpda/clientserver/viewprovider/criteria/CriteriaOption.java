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
package org.vpda.clientserver.viewprovider.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.common.comps.ui.Component;
import org.vpda.common.criteria.Operator;
import org.vpda.internal.common.util.Assert;

/**
 * One searching option row
 * 
 * @author kitko
 *
 */
public final class CriteriaOption implements Serializable {
    private static final long serialVersionUID = -5442701829000177883L;
    private static final List<Operator> EMPTY = Collections.emptyList();
    private final String columnId;
    private final List<Operator> operators;
    private final OperatorComponentSelection componentSelection;

    /**
     * Id of column
     * 
     * @return column id
     */
    public String getColumnId() {
        return columnId;
    }

    /**
     * @return list of operators for bean
     */
    public List<Operator> getOperators() {
        return operators == null ? EMPTY : Collections.unmodifiableList(operators);
    }

    /**
     * @param operator
     * @return value component
     */
    public Component getComponent(Operator operator) {
        return componentSelection.getComponent(operator);
    }

    /**
     * Creates criteria beam
     * 
     * @param columnId
     * @param operators
     * @param componentSelection
     */
    public CriteriaOption(String columnId, List<Operator> operators, OperatorComponentSelection componentSelection) {
        super();
        this.columnId = Assert.isNotNull(columnId);
        this.operators = new ArrayList<Operator>(operators);
        this.componentSelection = Assert.isNotNull(componentSelection);
    }

    @Override
    public String toString() {
        return columnId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columnId == null) ? 0 : columnId.hashCode());
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
        CriteriaOption other = (CriteriaOption) obj;
        if (columnId == null) {
            if (other.columnId != null)
                return false;
        }
        else if (!columnId.equals(other.columnId))
            return false;
        return true;
    }

}