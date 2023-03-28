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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.vpda.common.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Criterion with n arguments
 * 
 * @author kitko
 *
 */
@Immutable
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class NnCriterion extends OperatorCriterion {
    private static final long serialVersionUID = 4514933073753065172L;
    private final List<Criterion> operands;

    /**
     * @param operator
     */
    public NnCriterion(Operator operator) {
        super(operator);
        this.operands = Collections.emptyList();
    }

    /**
     * Creates criterion with operator and operands
     * 
     * @param operator
     * @param operands
     */
    public NnCriterion(Operator operator, List<Criterion> operands) {
        super(operator);
        this.operands = new ArrayList<Criterion>(operands);
    }

    /**
     * Creates criterion with operator and operands
     * 
     * @param operator
     * @param operands
     */
    public NnCriterion(Operator operator, Criterion... operands) {
        super(operator);
        this.operands = new ArrayList<Criterion>(Arrays.asList(operands));
    }

    /**
     * Creates NnCriterion from json string
     * 
     * @param operator
     * @param operands
     * @return BinCriterion
     */
    @JsonCreator
    public static NnCriterion fromJson(@JsonProperty("operator") Operator operator, @JsonProperty("operands") List<Criterion> operands) {
        return new NnCriterion(operator, operands);
    }

    /**
     * @return operands count
     */
    public int getOperandsCount() {
        return operands.size();
    }

    /**
     * Return operand at index position
     * 
     * @param index
     * @return operand
     */
    public Criterion getOperand(int index) {
        return operands.get(index);
    }

    /**
     * 
     * @return all operands
     */
    public List<Criterion> getOperands() {
        return Collections.unmodifiableList(operands);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((operands == null) ? 0 : operands.hashCode());
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
        NnCriterion other = (NnCriterion) obj;
        if (operands == null) {
            if (other.operands != null) {
                return false;
            }
        }
        else if (!operands.equals(other.operands)) {
            return false;
        }
        return true;
    }

}
