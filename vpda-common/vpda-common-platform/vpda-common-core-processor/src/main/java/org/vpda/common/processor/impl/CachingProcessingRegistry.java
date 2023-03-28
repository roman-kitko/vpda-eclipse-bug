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
import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingCache;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.annotation.ClassProcessorInfo;
import org.vpda.common.processor.annotation.FieldBuilderInfo;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Processing registry, that will wrap created {@link ClassProcessor} and
 * {@link FieldBuilder} by caching decorators
 * 
 * @author kitko
 *
 */
public final class CachingProcessingRegistry implements ProcessingRegistry {
    private final ProcessingCache cache;
    private final ProcessingRegistry delegate;

    /**
     * Creates CachingProcessingRegistry
     * 
     * @param delegate
     * @param cache
     */
    public CachingProcessingRegistry(ProcessingRegistry delegate, ProcessingCache cache) {
        super();
        this.delegate = delegate;
        this.cache = cache;
    }

    @Override
    public <T> ClassProcessor<T> getProcessorByClassContext(ClassContext<T> classContext, ObjectResolver context) {
        ClassProcessor<T> processor = cache.getProcessorByContext(classContext, context);
        if (processor != null) {
            return ProcessorHelper.createCachedProcessor(processor, cache);
        }
        processor = delegate.getProcessorByClassContext(classContext, context);
        if (processor != null) {
            ClassProcessorInfo processorInfo = processor.getClass().getAnnotation(ClassProcessorInfo.class);
            boolean canCache = processorInfo != null ? processorInfo.cache() : true;
            if (canCache) {
                processor = ProcessorHelper.createCachedProcessor(processor, cache);
                cache.putProcessorByContext(classContext, processor, context);
                return processor;
            }
        }
        return ProcessorHelper.createCachedProcessor(processor, cache);
    }

    @Override
    public <T> ClassProcessor<T> getProcessorByProcessedAndTargetClass(Class<?> processedClass, Class<T> targetClass, ObjectResolver context) {
        ClassContext<T> classContext = ClassContext.createRootClassContext(processedClass, targetClass);
        ClassProcessor<T> processor = cache.getProcessorByContext(classContext, context);
        if (processor != null) {
            return ProcessorHelper.createCachedProcessor(processor, cache);
        }
        processor = delegate.getProcessorByProcessedAndTargetClass(processedClass, targetClass, context);
        if (processor != null) {
            ClassProcessorInfo processorInfo = processor.getClass().getAnnotation(ClassProcessorInfo.class);
            boolean canCache = processorInfo != null ? processorInfo.cache() : true;
            if (canCache) {
                processor = ProcessorHelper.createCachedProcessor(processor, cache);
                cache.putProcessorByContext(classContext, processor, context);
                return processor;
            }
        }
        return ProcessorHelper.createCachedProcessor(processor, cache);
    }

    @Override
    public <T> ClassProcessor<T> getProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> processedClass, Class<T> targetClass, ObjectResolver context) {
        ClassProcessor<T> processor = delegate.getProcessorByAnnotation(annClass, processedClass, targetClass, context);
        return ProcessorHelper.createCachedProcessor(processor, cache);
    }

    @Override
    public <T> ItemsClassProcessor<T> getItemProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context) {
        ItemsClassProcessor<T> processor = delegate.getItemProcessorByAnnotation(annClass, processedClass, targetItemClass, context);
        return ProcessorHelper.createCachedItemsProcessor(processor, cache);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ItemsClassProcessor<T> getItemsProcessorByItemContext(ClassItemContext<T> itemContext, ObjectResolver context) {
        ClassProcessor<List> cachedProcessor = cache.getProcessorByContext(itemContext, context);
        if (cachedProcessor instanceof ItemsClassProcessor) {
            return (ItemsClassProcessor<T>) cachedProcessor;
        }
        ItemsClassProcessor<T> processor = delegate.getItemsProcessorByItemContext(itemContext, context);
        if (processor != null) {
            ClassProcessorInfo processorInfo = processor.getClass().getAnnotation(ClassProcessorInfo.class);
            boolean canCache = processorInfo != null ? processorInfo.cache() : true;
            if (canCache) {
                processor = ProcessorHelper.createCachedItemsProcessor(processor, cache);
                cache.putProcessorByContext(itemContext, processor, context);
                return processor;
            }
        }
        return ProcessorHelper.createCachedItemsProcessor(processor, cache);
    }

    @Override
    public <T> ItemsClassProcessor<T> getItemsProcessorByProcessedAndTargetItemClass(Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context) {
        ItemsClassProcessor<T> processor = delegate.getItemsProcessorByProcessedAndTargetItemClass(processedClass, targetItemClass, context);
        return ProcessorHelper.createCachedItemsProcessor(processor, cache);
    }

    @Override
    public <T> FieldBuilder<T> getBuilderByFieldContext(FieldContext<T> fieldContext, ObjectResolver context) {
        FieldBuilder<T> builder = cache.getBuilderByContext(fieldContext, context);
        if (builder != null) {
            return ProcessorHelper.createCachedFieldBuilder(builder, cache);
        }
        builder = delegate.getBuilderByFieldContext(fieldContext, context);
        if (builder != null) {
            FieldBuilderInfo builderInfo = builder.getClass().getAnnotation(FieldBuilderInfo.class);
            boolean canCache = builderInfo != null ? builderInfo.cache() : true;
            if (canCache) {
                builder = ProcessorHelper.createCachedFieldBuilder(builder, cache);
                cache.putBuilderByContext(fieldContext, builder, context);
                return builder;
            }
        }
        return ProcessorHelper.createCachedFieldBuilder(builder, cache);
    }

    @Override
    public <T> FieldBuilder<T> getBuilderByFieldAnnotation(Class<? extends Annotation> annClass, Class<T> targetType, Field field, ObjectResolver context) {
        FieldBuilder<T> builder = delegate.getBuilderByFieldAnnotation(annClass, targetType, field, context);
        return ProcessorHelper.createCachedFieldBuilder(builder, cache);
    }

    @Override
    public <T> FieldBuilder<T> getBuilderByFieldType(Class<?> type, Class<T> targetType, Field field, ObjectResolver context) {
        FieldBuilder<T> builder = delegate.getBuilderByFieldType(type, targetType, field, context);
        return ProcessorHelper.createCachedFieldBuilder(builder, cache);
    }

    @Override
    public <T> ProcessingFieldFilter getFieldFilter(ClassContext<T> classContext, ObjectResolver context) {
        ProcessingFieldFilter filter = cache.getProcessingFieldFilterByContext(classContext, context);
        if (filter != null) {
            return filter;
        }
        filter = delegate.getFieldFilter(classContext, context);
        if (filter != null) {
            cache.putProcessingFieldFilterByContext(classContext, filter, context);
        }
        return filter;
    }

}
