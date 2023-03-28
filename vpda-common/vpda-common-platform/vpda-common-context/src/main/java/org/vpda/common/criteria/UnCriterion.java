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
package org.vpda.common.criteria;

import org.vpda.common.annotations.Immutable;
import org.vpda.internal.common.util.Assert;

/**
 * Criterion with one operand
 * 
 * @author kitko
 *
 */
@Immutable
public final class UnCriterion extends OperatorCriterion {
    private static final long serialVersionUID = -3746947493393389945L;

    private final Criterion operand;

    /**
     * Creates Unary criterion
     * 
     * @param operator
     * @param operand
     */
    public UnCriterion(Operator operator, Criterion operand) {
        super(operator);
        this.operand = Assert.isNotNullArgument(operand, "operand");
    }

    /**
     * @return the operand
     */
    public Criterion getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return operand + " " + operator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((operand == null) ? 0 : operand.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UnCriterion other = (UnCriterion) obj;
        if (operand == null) {
            if (other.operand != null) {
                return false;
            }
        }
        else if (!operand.equals(other.operand)) {
            return false;
        }
        return true;
    }

}
