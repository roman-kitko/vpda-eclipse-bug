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
package org.vpda.common.processor;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Cache for processing process
 * 
 * @author kitko
 *
 */
public interface ProcessingCache {

    /**
     * Gets processor by its class
     * 
     * @param clazz
     * @param context
     * @return processor or null if not cached
     */
    public ClassProcessor<?> getProcessorByClass(Class<? extends ClassProcessor> clazz, ObjectResolver context);

    /**
     * Puts processor by its class
     * 
     * @param clazz
     * @param c
     * @param context
     * @return this
     */
    public ProcessingCache putProcessorByClass(Class<? extends ClassProcessor> clazz, ClassProcessor<?> c, ObjectResolver context);

    /**
     * Gets processor by context
     * 
     * @param classContext
     * @param context
     * @return cache processor or null if not registered
     */
    public <T> ClassProcessor<T> getProcessorByContext(ClassContext<T> classContext, ObjectResolver context);

    /**
     * Puts processor by context
     * 
     * @param classContext
     * @param processor
     * @param context
     * @return this
     */
    public <T> ProcessingCache putProcessorByContext(ClassContext<T> classContext, ClassProcessor<T> processor, ObjectResolver context);

    /**
     * Gets class processing cached instance
     * 
     * @param <T>
     * @param classContext
     * @param context
     * @return cached object or null if not in cache
     */
    public <T> T getProcessedObject(ClassContext<T> classContext, ObjectResolver context);

    /**
     * Adds class processing result into cache
     * 
     * @param <T>
     * @param classContext
     * @param o
     * @param context
     * @return this
     */
    public <T> ProcessingCache putProcessedObject(ClassContext<T> classContext, T o, ObjectResolver context);

    /**
     * Gets builder by class
     * 
     * @param clazz
     * @param context
     * @return builder by class
     */
    public FieldBuilder<?> getBuilderByClass(Class<? extends FieldBuilder> clazz, ObjectResolver context);

    /**
     * Puts builder by class
     * 
     * @param clazz
     * @param builder
     * @param context
     * @return this
     */
    public ProcessingCache putBuilderByClass(Class<? extends FieldBuilder> clazz, FieldBuilder<?> builder, ObjectResolver context);

    /**
     * Gets builder by context
     * 
     * @param fieldContext
     * @param context
     * @return builder by context
     */
    public <T> FieldBuilder<T> getBuilderByContext(FieldContext<T> fieldContext, ObjectResolver context);

    /**
     * Puts builder by context
     * 
     * @param fieldContext
     * @param builder
     * @param context
     * @return this
     */
    public <T> ProcessingCache putBuilderByContext(FieldContext<T> fieldContext, FieldBuilder<T> builder, ObjectResolver context);

    /**
     * Puts built object into cache
     * 
     * @param fieldContext
     * @param o
     * @param context
     * @return this
     */
    public <T> ProcessingCache putBuiltObject(FieldContext<T> fieldContext, T o, ObjectResolver context);

    /**
     * Gets built object value
     * 
     * @param fieldContext
     * @param context
     * @return built object value or null if not registered before
     */
    public <T> T getBuiltObject(FieldContext<T> fieldContext, ObjectResolver context);

    /**
     * Clear whole cache
     * 
     * @return this
     */
    public ProcessingCache clear();

    /**
     * @param classContext
     * @param context
     * @return ProcessingFieldFilter for classContext
     */
    public <T> ProcessingFieldFilter getProcessingFieldFilterByContext(ClassContext<T> classContext, ObjectResolver context);

    /**
     * @param classContext
     * @param filter
     * @param context
     * @return this
     */
    public <T> ProcessingCache putProcessingFieldFilterByContext(ClassContext<T> classContext, ProcessingFieldFilter filter, ObjectResolver context);
}
