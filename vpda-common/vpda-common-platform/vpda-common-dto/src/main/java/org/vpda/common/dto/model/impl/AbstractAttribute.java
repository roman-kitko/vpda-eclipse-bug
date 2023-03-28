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

import java.lang.reflect.Member;

import org.vpda.common.dto.PropertyPath;
import org.vpda.common.dto.model.Attribute;
import org.vpda.common.dto.model.AttributeType;
import org.vpda.common.dto.model.ManagedType;

public abstract class AbstractAttribute<X, Y> implements Attribute<X, Y> {

    private final String name;
    private final Class<Y> javaType;
    private final Member javaMember;
    private final PropertyPath path;
    private final AttributeType attributeType;
    private final ManagedType<X> declaringType;
    private final boolean isAssociation;
    private final boolean isCollection;

    protected AbstractAttribute(AbstractAttributeBuilder<X, Y, AbstractAttribute<X, Y>> builder) {
        this.name = builder.getName();
        this.javaType = builder.getJavaType();
        this.javaMember = builder.getJavaMember();
        this.path = builder.getPath();
        this.attributeType = builder.getAttributeType();
        this.declaringType = builder.getDeclaringType();
        this.isAssociation = builder.isAssociation();
        this.isCollection = builder.isCollection();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Y> getJavaType() {
        return javaType;
    }

    @Override
    public Member getJavaMember() {
        return javaMember;
    }

    @Override
    public PropertyPath getPath() {
        return path;
    }

    @Override
    public AttributeType getAttributeType() {
        return attributeType;
    }

    @Override
    public ManagedType<X> getDeclaringType() {
        return declaringType;
    }

    @Override
    public boolean isAssociation() {
        return isAssociation;
    }

    @Override
    public boolean isCollection() {
        return isCollection;
    }

    public abstract <A extends AbstractAttribute<X, Y>> AbstractAttributeBuilder<X, Y, ? extends A> createBuilder();

    public static abstract class AbstractAttributeBuilder<X, Y, A extends AbstractAttribute<X, Y>> implements org.vpda.common.util.Builder<A> {
        private String name;
        private Class<Y> javaType;
        private Member javaMember;
        private PropertyPath path;
        private AttributeType attributeType;
        private ManagedType<X> declaringType;
        private boolean isAssociation;
        private boolean isCollection;

        public AbstractAttributeBuilder setValues(A attr) {
            this.name = attr.getName();
            this.javaType = attr.getJavaType();
            this.javaMember = attr.getJavaMember();
            this.path = attr.getPath();
            this.attributeType = attr.getAttributeType();
            this.declaringType = attr.getDeclaringType();
            this.isAssociation = attr.isAssociation();
            this.isCollection = attr.isCollection();
            return this;
        }

        public String getName() {
            return name;
        }

        public AbstractAttributeBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Class<Y> getJavaType() {
            return javaType;
        }

        public AbstractAttributeBuilder setJavaType(Class<Y> javaType) {
            this.javaType = javaType;
            return this;
        }

        public Member getJavaMember() {
            return javaMember;
        }

        public AbstractAttributeBuilder setJavaMember(Member javaMember) {
            this.javaMember = javaMember;
            return this;
        }

        public PropertyPath getPath() {
            return path;
        }

        public AbstractAttributeBuilder setPath(PropertyPath path) {
            this.path = path;
            return this;
        }

        public AttributeType getAttributeType() {
            return attributeType;
        }

        public AbstractAttributeBuilder setAttributeType(AttributeType attributeType) {
            this.attributeType = attributeType;
            return this;
        }

        public ManagedType<X> getDeclaringType() {
            return declaringType;
        }

        public AbstractAttributeBuilder setDeclaringType(ManagedType<X> declaringType) {
            this.declaringType = declaringType;
            return this;
        }

        public boolean isAssociation() {
            return isAssociation;
        }

        public AbstractAttributeBuilder setAssociation(boolean isAssociation) {
            this.isAssociation = isAssociation;
            return this;
        }

        public boolean isCollection() {
            return isCollection;
        }

        public AbstractAttributeBuilder setCollection(boolean isCollection) {
            this.isCollection = isCollection;
            return this;
        }

    }

}
