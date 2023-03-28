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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Collection of static builder methods.
 * 
 * @author kitko
 *
 */
public final class CriterionBuilder {
    private CriterionBuilder() {
    }

    /**
     * Creates and expression
     * 
     * @param left
     * @param right
     * @return and expression
     */
    public static BinCriterion and(Criterion left, Criterion right) {
        return new BinCriterion(left, Operator.AND, right);
    }

    /**
     * Creates and expression
     * 
     * @param one
     * @param two
     * @param others
     * @return and expression
     */
    public static OperatorCriterion and(Criterion one, Criterion two, Criterion... others) {
        if (others.length == 0) {
            return new BinCriterion(one, Operator.AND, two);
        }
        List<Criterion> operands = new ArrayList<>(2 + others.length);
        operands.add(one);
        operands.add(two);
        operands.addAll(Arrays.asList(others));
        return new NnCriterion(Operator.AND, operands);
    }

    /**
     * Creates or expression
     * 
     * @param left
     * @param right
     * @return or expression
     */
    public static BinCriterion or(Criterion left, Criterion right) {
        return new BinCriterion(left, Operator.OR, right);
    }

    /**
     * Creates or expression
     * 
     * @param one
     * @param two
     * @param others
     * @return and expression
     */
    public static OperatorCriterion or(Criterion one, Criterion two, Criterion... others) {
        if (others.length == 0) {
            return new BinCriterion(one, Operator.OR, two);
        }
        List<Criterion> operands = new ArrayList<>(2 + others.length);
        operands.add(one);
        operands.add(two);
        operands.addAll(Arrays.asList(others));
        return new NnCriterion(Operator.OR, operands);
    }

    /**
     * Creates new Equals comparison
     * 
     * @param <T>
     * @param c
     * @param value
     * @return equals comparison
     */
    public static <T> BinCriterion eq(Criterion c, T value) {
        return new BinCriterion(c, Operator.EQ, new Value<T>(value));
    }

    /**
     * Creates negate of criteria
     * 
     * @param c
     * @return negate of criteria
     */
    public static UnCriterion not(Criterion c) {
        return new UnCriterion(Operator.NOT, c);
    }

    /**
     * Creates criterion greater than value
     * 
     * @param <T>
     * @param left
     * @param value
     * @return BinCriterion (left > value)
     */
    public static <T> BinCriterion valueGreater(Criterion left, T value) {
        return new BinCriterion(left, Operator.GT, new Value<T>(value));
    }

    /**
     * Creates criterion less than value
     * 
     * @param <T>
     * @param left
     * @param value
     * @return BinCriterion (left < value)
     */
    public static <T> BinCriterion valueLess(Criterion left, T value) {
        return new BinCriterion(left, Operator.LT, new Value<T>(value));
    }

    /**
     * Creates criterion equals than value
     * 
     * @param <T>
     * @param left
     * @param value
     * @return BinCriterion (left = value)
     */
    public static <T> BinCriterion valueEquals(Criterion left, T value) {
        return new BinCriterion(left, Operator.EQ, new Value<T>(value));
    }

    /**
     * Merge two criteria by and
     * 
     * @param criteria1
     * @param criteria2
     * @param newName
     * @return merged criteria
     */
    public static CriteriaTree mergeByAnd(CriteriaTree criteria1, CriteriaTree criteria2, String newName) {
        return mergeByOperator(Operator.AND, criteria1, criteria2, newName);
    }

    /**
     * Merge two criteria by or
     * 
     * @param criteria1
     * @param criteria2
     * @param newName
     * @return merged criteria
     */
    public static CriteriaTree mergeByOr(CriteriaTree criteria1, CriteriaTree criteria2, String newName) {
        return mergeByOperator(Operator.OR, criteria1, criteria2, newName);
    }

    private static CriteriaTree mergeByOperator(Operator operator, CriteriaTree criteria1, CriteriaTree criteria2, String newName) {
        if (criteria1.getRoot() == null && criteria2.getRoot() == null) {
            return new CriteriaTreeImpl(newName);
        }
        else if (criteria1.getRoot() == null && criteria2.getRoot() != null) {
            return new CriteriaTreeImpl(newName, criteria2.getRoot());
        }
        else if (criteria1.getRoot() != null && criteria2.getRoot() == null) {
            return new CriteriaTreeImpl(newName, criteria1.getRoot());
        }
        Criterion root = new BinCriterion(criteria1.getRoot(), operator, criteria2.getRoot());
        return new CriteriaTreeImpl(newName, root);
    }

}
