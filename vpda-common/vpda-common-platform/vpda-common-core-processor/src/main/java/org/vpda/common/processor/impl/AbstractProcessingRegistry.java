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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Abstract processing registry where all methods just returns null. Inherit
 * from this class when u need implements only a registry for special kind.
 * 
 * @author kitko
 *
 */
public abstract class AbstractProcessingRegistry implements ProcessingRegistry {

    @Override
    public <T> ClassProcessor<T> getProcessorByClassContext(ClassContext<T> classContext, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> ClassProcessor<T> getProcessorByProcessedAndTargetClass(Class<?> processedClass, Class<T> targetClass, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> ClassProcessor<T> getProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> processedClass, Class<T> targetClass, ObjectResolver context) {
        return null;
    }

    /**
     * Gets item processor by item context
     * 
     * @param <T>
     * @param annClass
     * @param processedClass
     * @param context
     * @return ClassProcessor by annotation
     */
    @Override
    public <T> ItemsClassProcessor<T> getItemProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> ItemsClassProcessor<T> getItemsProcessorByItemContext(ClassItemContext<T> itemContext, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> ItemsClassProcessor<T> getItemsProcessorByProcessedAndTargetItemClass(Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> FieldBuilder<T> getBuilderByFieldContext(FieldContext<T> fieldContext, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> FieldBuilder<T> getBuilderByFieldAnnotation(Class<? extends Annotation> annClass, Class<T> targetType, Field field, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> FieldBuilder<T> getBuilderByFieldType(Class<?> type, Class<T> targetType, Field field, ObjectResolver context) {
        return null;
    }

    @Override
    public <T> ProcessingFieldFilter getFieldFilter(ClassContext<T> classContext, ObjectResolver context) {
        ProcessingFieldFilter filter = context.resolveObject(ProcessingFieldFilter.class);
        if (filter != null) {
            return filter;
        }
        return IgnoringProcessingFieldFilter.getInstance();
    }

}
