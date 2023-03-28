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
package org.vpda.common.criteria;

import org.vpda.internal.common.util.Assert;

/**
 * Criterion with one operator.
 * 
 * @author kitko
 *
 */
public abstract class OperatorCriterion implements Criterion {
    private static final long serialVersionUID = -8627364504032881642L;
    /** Operator */
    protected final Operator operator;

    /**
     * @param operator
     */
    protected OperatorCriterion(Operator operator) {
        super();
        this.operator = Assert.isNotNullArgument(operator, "operator");
    }

    /**
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((operator == null) ? 0 : operator.hashCode());
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
        OperatorCriterion other = (OperatorCriterion) obj;
        if (operator != other.operator) {
            return false;
        }
        return true;
    }

}
