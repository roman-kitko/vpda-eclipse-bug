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
package org.vpda.common.processor.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Abstract FieldBuilder
 * 
 * @author kitko
 * @param <T>
 *
 */
public abstract class AbstractFieldBuilder<T> implements FieldBuilder<T> {

    @Override
    public boolean canBuild(FieldContext<?> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        return fieldContext.getTargetClass().isAssignableFrom(getTargetClass());
    }

    @Override
    public <Z extends T> Z buildByType(FieldContext<Z> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        @SuppressWarnings("unchecked")
        Object result = build((FieldContext<T>) fieldContext, resolver, context);
        if (result == null) {
            return null;
        }
        return fieldContext.getTargetClass().cast(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T> getTargetClass() {
        if (!AbstractFieldBuilder.class.equals(getClass().getSuperclass())) {
            return (Class<? extends T>) Object.class;
        }
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) genericSuperclass;
        Type t = pt.getActualTypeArguments()[0];
        return (Class<? extends T>) t;
    }

}
