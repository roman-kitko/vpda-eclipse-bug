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
package org.vpda.common.processor.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;

/**
 * Abstract class processor
 * 
 * @author kitko
 *
 * @param <T>
 */
public abstract class AbstractClassProcessor<T> implements ClassProcessor<T> {

    @Override
    public boolean canProcess(ClassContext<?> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        return classContext.getTargetClass().isAssignableFrom(getTargetClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends T> getTargetClass() {
        if (!AbstractClassProcessor.class.equals(getClass().getSuperclass())) {
            return (Class<? extends T>) Object.class;
        }
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) genericSuperclass;
        Type t = pt.getActualTypeArguments()[0];
        return (Class<? extends T>) t;
    }

}
