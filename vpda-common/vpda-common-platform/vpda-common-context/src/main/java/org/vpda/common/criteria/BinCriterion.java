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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Criterion with two operands.
 * 
 * @author kitko
 *
 */
@Immutable
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class BinCriterion extends OperatorCriterion {
    private static final long serialVersionUID = -3746947493393389945L;

    private final Criterion leftOperand;
    private final Criterion rightOperand;

    /**
     * Creates Unary criterion
     * 
     * @param operator
     * @param leftOperand
     * @param rightOperand
     */
    public BinCriterion(Criterion leftOperand, Operator operator, Criterion rightOperand) {
        super(operator);
        this.leftOperand = Assert.isNotNullArgument(leftOperand, "leftOperand");
        this.rightOperand = Assert.isNotNullArgument(rightOperand, "rightOperand");
    }

    /**
     * Creates BinCriterion from json string
     * 
     * @param leftOperand
     * @param operator
     * @param rightOperand
     * @return BinCriterion
     */
    @JsonCreator
    public static BinCriterion fromJson(@JsonProperty("leftOperand") Criterion leftOperand, @JsonProperty("operator") Operator operator, @JsonProperty("rightOperand") Criterion rightOperand) {
        return new BinCriterion(leftOperand, operator, rightOperand);
    }

    /**
     * Factory method to create new Bin criterion
     * 
     * @param leftOperand
     * @param operator
     * @param rightOperand
     * @return BinCriterion
     */
    public static BinCriterion create(Criterion leftOperand, Operator operator, Criterion rightOperand) {
        return new BinCriterion(leftOperand, operator, rightOperand);
    }

    /**
     * @return the leftOperand
     */
    public Criterion getLeftOperand() {
        return leftOperand;
    }

    /**
     * @return the rightOperand
     */
    public Criterion getRightOperand() {
        return rightOperand;
    }

    @Override
    public String toString() {
        return leftOperand + " " + operator + " " + rightOperand;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((leftOperand == null) ? 0 : leftOperand.hashCode());
        result = prime * result + ((rightOperand == null) ? 0 : rightOperand.hashCode());
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
        BinCriterion other = (BinCriterion) obj;
        if (leftOperand == null) {
            if (other.leftOperand != null) {
                return false;
            }
        }
        else if (!leftOperand.equals(other.leftOperand)) {
            return false;
        }
        if (rightOperand == null) {
            if (other.rightOperand != null) {
                return false;
            }
        }
        else if (!rightOperand.equals(other.rightOperand)) {
            return false;
        }
        return true;
    }

}
