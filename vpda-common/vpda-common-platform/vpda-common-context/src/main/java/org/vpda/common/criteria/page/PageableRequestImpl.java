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
package org.vpda.common.criteria.page;

import java.io.Serializable;

import org.vpda.common.criteria.Criteria;
import org.vpda.common.criteria.sort.Sort;
import org.vpda.common.util.Builder;
import org.vpda.internal.common.util.Assert;

public final class PageableRequestImpl<T, C extends Criteria> implements PageableRequest<T, C>, Serializable {

    private static final long serialVersionUID = -8905774086803865972L;
    private final int offset;
    private final int rows;
    private final boolean reverseDefaultOrder;
    private final Class<T> type;
    private C criteria;
    private final boolean returnTotal;
    private final Sort sort;

    public PageableRequest<T, C> withRows(Class<T> type, int rows) {
        return new PageableRequestBuilder(type).setRows(rows).build();
    }

    public PageableRequest<T, C> withRowsAndOffset(Class<T> type, int rows, int offset) {
        return new PageableRequestBuilder(type).setRows(rows).setOffset(offset).build();
    }

    private PageableRequestImpl(PageableRequestBuilder builder) {
        this.offset = builder.getOffset();
        this.rows = builder.getRows();
        this.type = Assert.isNotNullArgument(builder.getType(), "type");
        this.reverseDefaultOrder = builder.isReverseDefaultOrder();
        this.criteria = builder.getCriteria();
        this.sort = builder.getSort();
        this.returnTotal = builder.isReturnTotal();
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public boolean reverseDefaultOrder() {
        return reverseDefaultOrder;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public C getCriteria() {
        return criteria;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean returnTotal() {
        return returnTotal;
    }

    public final class PageableRequestBuilder implements Builder<PageableRequestImpl<T, C>> {

        private int offset;
        private int rows = 10;
        private boolean reverseDefaultOrder;
        private Class<T> type;
        private C criteria = (C) Criteria.getEmpty();
        private boolean returnTotal;
        private Sort sort = Sort.getEmpty();

        public PageableRequestBuilder() {
        }

        public PageableRequestBuilder(Class<T> type) {
            this.type = type;
        }

        public int getOffset() {
            return offset;
        }

        public PageableRequestBuilder setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public int getRows() {
            return rows;
        }

        public PageableRequestBuilder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        public boolean isReverseDefaultOrder() {
            return reverseDefaultOrder;
        }

        public PageableRequestBuilder setReverseDefaultOrder(boolean reverseDefaultOrder) {
            this.reverseDefaultOrder = reverseDefaultOrder;
            return this;
        }

        public Class<T> getType() {
            return type;
        }

        public PageableRequestBuilder setType(Class<T> type) {
            this.type = type;
            return this;
        }

        public C getCriteria() {
            return criteria;
        }

        public PageableRequestBuilder setCriteria(C criteria) {
            this.criteria = criteria;
            return this;
        }

        public boolean isReturnTotal() {
            return returnTotal;
        }

        public void setReturnTotal(boolean returnTotal) {
            this.returnTotal = returnTotal;
        }

        public Sort getSort() {
            return sort;
        }

        public PageableRequestBuilder setSort(Sort sort) {
            this.sort = sort;
            return this;
        }

        @Override
        public PageableRequestImpl<T, C> build() {
            return new PageableRequestImpl<>(this);
        }

    }

}
