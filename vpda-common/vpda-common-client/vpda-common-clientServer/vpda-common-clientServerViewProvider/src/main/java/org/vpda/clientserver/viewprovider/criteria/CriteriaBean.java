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

import org.vpda.common.criteria.Operator;

/**
 * Searching bean filled by user
 * 
 * @author kitko
 *
 */
public final class CriteriaBean implements Serializable {
    private static final long serialVersionUID = 6762273837813091865L;

    private final String columnId;
    private final Operator operator;
    private final Object value;

    /**
     * @return the id
     */
    public String getColumnId() {
        return columnId;
    }

    /**
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * @return the value
     */
    public Object getCompValue() {
        return value;
    }

    /**
     * Creates new bean
     * 
     * @param id
     * @param operator
     * @param value
     */
    public CriteriaBean(String id, Operator operator, Object value) {
        this.columnId = id;
        this.operator = operator;
        this.value = value;
    }

    private CriteriaBean(CriteriaBean.Builder builder) {
        this.columnId = builder.getColumnId();
        this.operator = builder.getOperator();
        this.value = builder.getValue();
    }

    /** Builder for CriteriaBean */
    public static final class Builder implements org.vpda.common.util.Builder<CriteriaBean> {
        private String columnId;
        private Operator operator;
        private Object value;

        /**
         * Sets values from bean
         * 
         * @param bean
         * @return this
         */
        public CriteriaBean.Builder setValues(CriteriaBean bean) {
            this.columnId = bean.getColumnId();
            this.operator = bean.getOperator();
            this.value = bean.getCompValue();
            return this;
        }

        /**
         * @return the columnId
         */
        public String getColumnId() {
            return columnId;
        }

        /**
         * @param columnId the columnId to set
         * @return this
         */
        public CriteriaBean.Builder setColumnId(String columnId) {
            this.columnId = columnId;
            return this;
        }

        /**
         * @return the operator
         */
        public Operator getOperator() {
            return operator;
        }

        /**
         * @param operator the operator to set
         * @return this
         */
        public CriteriaBean.Builder setOperator(Operator operator) {
            this.operator = operator;
            return this;
        }

        /**
         * @return the value
         */
        public Object getValue() {
            return value;
        }

        /**
         * @param value the value to set
         * @return this
         */
        public CriteriaBean.Builder setValue(Object value) {
            this.value = value;
            return this;
        }

        @Override
        public Class<? extends CriteriaBean> getTargetClass() {
            return CriteriaBean.class;
        }

        @Override
        public CriteriaBean build() {
            return new CriteriaBean(this);
        }

    }

}