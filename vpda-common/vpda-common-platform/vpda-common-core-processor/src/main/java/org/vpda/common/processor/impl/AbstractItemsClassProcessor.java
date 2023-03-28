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
import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;

/**
 * Abstract items procesor
 * 
 * @author kitko
 *
 * @param <T>
 */
public abstract class AbstractItemsClassProcessor<T> extends AbstractClassProcessor<List> implements ItemsClassProcessor<T> {

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> getTargetItemClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) genericSuperclass;
        Type t = pt.getActualTypeArguments()[0];
        return (Class<T>) t;
    }

    @Override
    public Class<List> getTargetClass() {
        return List.class;
    }

    @Override
    public boolean canProcess(ClassContext<?> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        if (!(classContext instanceof ClassItemContext)) {
            return false;
        }
        ClassItemContext<?> itemContext = (ClassItemContext<?>) classContext;
        return itemContext.getTargetItemClass().isAssignableFrom(getTargetItemClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List process(ClassContext<List> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        if (!(classContext instanceof ClassItemContext)) {
            throw new IllegalArgumentException("ClassContext is not instanceof ClassItemContext");
        }
        return process((ClassItemContext<T>) classContext, processorResolver, context);
    }

}
