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
package org.vpda.common.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Registry of class processors and builders
 * 
 * @author kitko
 *
 */
public interface ProcessingRegistry {

    /**
     * Gets processor by class context.
     * 
     * @param <T>
     * @param classContext
     * @param context
     * @return processor for context or null if not appropriate
     */
    public <T> ClassProcessor<T> getProcessorByClassContext(ClassContext<T> classContext, ObjectResolver context);

    /**
     * Gets processor for class being processed
     * 
     * @param <T>
     * @param processedClass
     * @param targetClass
     * @param context
     * @return processor or null if this registry cannot retrieve processor for
     *         class
     */
    public <T> ClassProcessor<T> getProcessorByProcessedAndTargetClass(Class<?> processedClass, Class<T> targetClass, ObjectResolver context);

    /**
     * @param <T>
     * @param annClass
     * @param processedClass
     * @param targetClass
     * @param context
     * @return ClassProcessor by annotation
     */
    public <T> ClassProcessor<T> getProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> processedClass, Class<T> targetClass, ObjectResolver context);

    /**
     * Gets item processor by item context
     * 
     * @param <T>
     * @param itemContext
     * @param context
     * @return processor for context or null if not appropriate
     */
    public <T> ItemsClassProcessor<T> getItemsProcessorByItemContext(ClassItemContext<T> itemContext, ObjectResolver context);

    /**
     * Gets item processor by item context
     * 
     * @param <T>
     * @param annClass
     * @param processedClass
     * @param targetItemClass
     * @param context
     * @return ClassProcessor by annotation
     */
    public <T> ItemsClassProcessor<T> getItemProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context);

    /**
     * Gets processor for class being processed
     * 
     * @param <T>
     * @param processedClass
     * @param targetItemClass
     * @param context
     * @return processor or null if this registry cannot retrieve processor for
     *         class
     */
    public <T> ItemsClassProcessor<T> getItemsProcessorByProcessedAndTargetItemClass(Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context);

    /**
     * Gets Field builder for context
     * 
     * @param <T>
     * @param fieldContext
     * @param context
     * @return field builder for context or null if not appropriate
     */
    public <T> FieldBuilder<T> getBuilderByFieldContext(FieldContext<T> fieldContext, ObjectResolver context);

    /**
     * @param <T>
     * @param annClass
     * @param targetType
     * @param field
     * @param context
     * @return Field builder by annotation
     */
    public <T> FieldBuilder<T> getBuilderByFieldAnnotation(Class<? extends Annotation> annClass, Class<T> targetType, Field field, ObjectResolver context);

    /**
     * @param <T>
     * @param type
     * @param targetType
     * @param field
     * @param context
     * @return FieldBuilder by builder type
     */
    public <T> FieldBuilder<T> getBuilderByFieldType(Class<?> type, Class<T> targetType, Field field, ObjectResolver context);

    /**
     * @param classContext
     * @param context
     * @return ProcessingFieldFilter for classContext
     */
    public <T> ProcessingFieldFilter getFieldFilter(ClassContext<T> classContext, ObjectResolver context);

}
