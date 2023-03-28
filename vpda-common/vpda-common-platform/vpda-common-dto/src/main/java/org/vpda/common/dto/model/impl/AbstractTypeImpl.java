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
package org.vpda.common.dto.model.impl;

import org.vpda.common.dto.model.DTOType;
import org.vpda.common.dto.model.Type;
import org.vpda.internal.common.util.Assert;

public abstract class AbstractTypeImpl<X> implements Type<X> {

    private final Class<X> javaClass;
    private final DTOType dtoType;

    protected AbstractTypeImpl(Class<X> javaClass, DTOType dtoType) {
        this.javaClass = Assert.isNotNullArgument(javaClass, "javaClass");
        this.dtoType = Assert.isNotNullArgument(dtoType, "dtoType");
    }

    protected <A extends AbstractTypeImpl<X>> AbstractTypeImpl(AbstractTypeImplBuilder<X, A> builder) {
        this(builder.getJavaClass(), builder.getDtoType());
    }

    @Override
    public DTOType getDTOType() {
        return dtoType;
    }

    @Override
    public Class<X> getJavaType() {
        return javaClass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dtoType == null) ? 0 : dtoType.hashCode());
        result = prime * result + ((javaClass == null) ? 0 : javaClass.hashCode());
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
        AbstractTypeImpl other = (AbstractTypeImpl) obj;
        if (dtoType != other.dtoType) {
            return false;
        }
        if (javaClass == null) {
            if (other.javaClass != null) {
                return false;
            }
        }
        else if (!javaClass.equals(other.javaClass)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractTypeImpl [javaClass=" + javaClass + ", dtoType=" + dtoType + "]";
    }

    public static abstract class AbstractTypeImplBuilder<X, A extends AbstractTypeImpl<X>> implements org.vpda.common.util.Builder<A> {

        private Class<X> javaClass;
        private DTOType dtoType;

        public Class<X> getJavaClass() {
            return javaClass;
        }

        public AbstractTypeImplBuilder setJavaClass(Class<X> javaClass) {
            this.javaClass = javaClass;
            return this;
        }

        public DTOType getDtoType() {
            return dtoType;
        }

        public AbstractTypeImplBuilder setDtoType(DTOType dtoType) {
            this.dtoType = dtoType;
            return this;
        }

        public AbstractTypeImplBuilder setValues(A type) {
            this.javaClass = type.getJavaType();
            this.dtoType = type.getDTOType();
            return this;
        }

    }

}
