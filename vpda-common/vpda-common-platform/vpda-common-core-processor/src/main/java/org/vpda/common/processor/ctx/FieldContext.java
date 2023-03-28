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
package org.vpda.common.processor.ctx;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;

import org.vpda.internal.common.util.Assert;

/**
 * Item for field building
 * 
 * @author kitko
 * @param <T> Type of result
 *
 */
public final class FieldContext<T> extends ProcessingContext<T> {
    private final Field field;
    private final Object value;

    /**
     * @return current context field value
     */
    public Object getFieldValue() {
        return value;
    }

    /**
     * Tries to cast field value to target class T If value is null, returns null.
     * If value cannot be cast to targetclass, throws
     * {@link IllegalArgumentException}
     * 
     * @throws IllegalArgumentException if value cannot be case to targetClass
     * @return field value cast to T
     */
    public T getTargetFieldValue() {
        if (value == null) {
            return null;
        }
        if (!getTargetClass().isInstance(value)) {
            throw new IllegalArgumentException(MessageFormat.format("Field value is of type [{0}], which is not assignable to targetClass [{1}]", value.getClass(), getTargetClass()));
        }
        return getTargetClass().cast(value);
    }

    /**
     * Tries to cast field value to target field value. If field has different type,
     * will return null
     * 
     * @return value of field or null if it cannot be cast to tareg type
     */
    public T tryGetTargetFieldValue() {
        if (value == null) {
            return null;
        }
        if (!getTargetClass().isInstance(value)) {
            return null;
        }
        return getTargetClass().cast(value);
    }

    /**
     * @return field
     */
    public Field getField() {
        return field;
    }

    /**
     * @param parent
     * @param field
     * @param value
     */
    private FieldContext(ProcessingContext<?> parent, Field field, Class<T> targetClass, Object value, Class[] groups) {
        super(parent, targetClass, groups);
        this.value = value;
        this.field = Assert.isNotNullArgument(field, "field");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FieldContext other = (FieldContext) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        }
        else if (!field.equals(other.field))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        }
        else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FieldContext [field=");
        builder.append(field);
        builder.append(", value=");
        builder.append(value);
        builder.append(", Parent=");
        builder.append(getParent());
        builder.append(", TargetClass=");
        builder.append(getTargetClass());
        builder.append("]");
        return builder.toString();
    }

    /**
     * Creates new FieldContext without parent
     * 
     * @param <T>
     * @param field
     * @param targetClass
     * @param groups
     * @return FieldContext
     */
    public static <T> FieldContext<T> createRootFieldContext(Field field, Class<T> targetClass, Class<?>... groups) {
        return createFieldContext(null, field, targetClass, groups);
    }

    /**
     * Creates new FieldContext
     * 
     * @param <T>
     * @param parent
     * @param field
     * @param targetClass
     * @param groups
     * @return new Field context
     */
    public static <T> FieldContext<T> createFieldContext(ProcessingContext<?> parent, Field field, Class<T> targetClass, Class<?>... groups) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            if (Modifier.isStatic(field.getModifiers())) {
                Object value = field.get(null);
                return new FieldContext<T>(parent, field, targetClass, value, groups);
            }
            else {
                Object value = null;
                if (parent instanceof ClassContext && ((ClassContext) parent).getInstance() != null) {
                    value = field.get(((ClassContext) parent).getInstance());
                }
                return new FieldContext<T>(parent, field, targetClass, value, groups);
            }
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot acccess field " + field, e);
        }
    }

    /**
     * Create a child field context. Child will point to same field, will have this
     * context as parent, but will have own target class
     * 
     * @param <C>
     * @param targetClass
     * @return child context
     */
    public <C> FieldContext<C> createChild(Class<C> targetClass) {
        return createFieldContext(this, this.field, targetClass);
    }

}