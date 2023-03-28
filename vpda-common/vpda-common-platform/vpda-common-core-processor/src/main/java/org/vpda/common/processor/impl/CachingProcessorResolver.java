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

import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingCache;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.ClassProcessorInfo;
import org.vpda.common.processor.annotation.FieldBuilderInfo;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * {@link ProcessorResolver} that will cache created processors and builders and
 * will return cached decorators for created processors and builders
 */
public final class CachingProcessorResolver implements ProcessorResolver {
    private final ProcessorResolver delegate;
    private final ProcessingCache cache;

    /**
     * Creates CachingProcessorResolver
     * 
     * @param delegate
     * @param cache
     */
    public CachingProcessorResolver(ProcessorResolver delegate, ProcessingCache cache) {
        super();
        this.delegate = delegate;
        this.cache = cache;
    }

    @Override
    public <T> ClassProcessor<T> resolveTargetProcessor(ClassContext<T> classContext, ObjectResolver context) {
        ClassProcessor<T> processor = cache.getProcessorByContext(classContext, context);
        if (processor != null) {
            return processor;
        }
        processor = delegate.resolveTargetProcessor(classContext, context);
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> ItemsClassProcessor<T> resolveTargetItemsProcessor(ClassItemContext<T> classContext, ObjectResolver context) {
        ClassProcessor<List> processor = cache.getProcessorByContext(classContext, context);
        if (processor instanceof ItemsClassProcessor) {
            return (ItemsClassProcessor<T>) processor;
        }
        processor = delegate.resolveTargetItemsProcessor(classContext, context);
        if (processor != null) {
            ClassProcessorInfo processorInfo = processor.getClass().getAnnotation(ClassProcessorInfo.class);
            boolean canCache = processorInfo != null ? processorInfo.cache() : true;
            if (canCache) {
                processor = ProcessorHelper.createCachedItemsProcessor((CachingItemsClassProcessor<T>) processor, cache);
                cache.putProcessorByContext(classContext, processor, context);
                return (CachingItemsClassProcessor<T>) processor;
            }
        }
        return ProcessorHelper.createCachedItemsProcessor((CachingItemsClassProcessor<T>) processor, cache);
    }

    @Override
    public <T> FieldBuilder<T> resolveTargetFieldBuilder(FieldContext<T> fieldContext, ObjectResolver context) {
        FieldBuilder<T> builder = cache.getBuilderByContext(fieldContext, context);
        if (builder != null) {
            return builder;
        }
        builder = delegate.resolveTargetFieldBuilder(fieldContext, context);
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
    public <T> ProcessingFieldFilter createFieldFilter(ClassContext<T> classContext, ObjectResolver context) {
        ProcessingFieldFilter filter = cache.getProcessingFieldFilterByContext(classContext, context);
        if (filter != null) {
            return filter;
        }
        filter = delegate.createFieldFilter(classContext, context);
        if (filter == null) {
            return null;
        }
        cache.putProcessingFieldFilterByContext(classContext, filter, context);
        return filter;
    }

}
