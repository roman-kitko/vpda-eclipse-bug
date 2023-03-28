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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ProcessingCache;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Default implementation of {@link ProcessingCache} that stores processors and
 * generated objects in memory
 * 
 * @author kitko
 *
 */
public final class ProcessingCacheImpl implements ProcessingCache {

    private final ConcurrentMap<ClassContext<?>, Object> instanceCache;
    private final ConcurrentMap<Class<? extends ClassProcessor>, ClassProcessor> processors;
    private final ConcurrentMap<ClassContext<?>, ClassProcessor> processorsByContext;
    private final ConcurrentMap<Class<? extends FieldBuilder>, FieldBuilder> builders;
    private final ConcurrentMap<FieldContext<?>, FieldBuilder> buildersByContext;
    private final ConcurrentMap<FieldContext<?>, Object> fieldValuesCache;
    private final ConcurrentMap<ClassContext<?>, ProcessingFieldFilter> processingFieldFilters;

    /**
     * Creates cache
     */
    public ProcessingCacheImpl() {
        instanceCache = new ConcurrentHashMap<>();
        processors = new ConcurrentHashMap<>();
        builders = new ConcurrentHashMap<>();
        fieldValuesCache = new ConcurrentHashMap<>();
        processorsByContext = new ConcurrentHashMap<>();
        buildersByContext = new ConcurrentHashMap<>();
        processingFieldFilters = new ConcurrentHashMap<>();
    }

    @Override
    public ClassProcessor<?> getProcessorByClass(Class<? extends ClassProcessor> clazz, ObjectResolver context) {
        return processors.get(clazz);
    }

    @Override
    public ProcessingCache putProcessorByClass(Class<? extends ClassProcessor> clazz, ClassProcessor<?> c, ObjectResolver context) {
        processors.put(clazz, c);
        return this;
    }

    @Override
    public <T> T getProcessedObject(ClassContext<T> key, ObjectResolver context) {
        return key.getTargetClass().cast(instanceCache.get(key));
    }

    @Override
    public <T> ProcessingCache putProcessedObject(ClassContext<T> key, T o, ObjectResolver context) {
        instanceCache.putIfAbsent(key, o);
        return this;
    }

    @Override
    public FieldBuilder<?> getBuilderByClass(Class<? extends FieldBuilder> clazz, ObjectResolver context) {
        return builders.get(clazz);
    }

    @Override
    public ProcessingCache putBuilderByClass(Class<? extends FieldBuilder> clazz, FieldBuilder<?> builder, ObjectResolver context) {
        builders.put(clazz, builder);
        return this;
    }

    @Override
    public ProcessingCache clear() {
        this.instanceCache.clear();
        this.processors.clear();
        this.builders.clear();
        this.fieldValuesCache.clear();
        return this;
    }

    @Override
    public <T> ProcessingCache putBuiltObject(FieldContext<T> fieldContext, T o, ObjectResolver context) {
        fieldValuesCache.putIfAbsent(fieldContext, o);
        return this;
    }

    @Override
    public <T> T getBuiltObject(FieldContext<T> fieldContext, ObjectResolver context) {
        return fieldContext.getTargetClass().cast(fieldValuesCache.get(fieldContext));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ClassProcessor<T> getProcessorByContext(ClassContext<T> classContext, ObjectResolver context) {
        return processorsByContext.get(classContext);
    }

    @Override
    public <T> ProcessingCache putProcessorByContext(ClassContext<T> classContext, ClassProcessor<T> processor, ObjectResolver context) {
        processorsByContext.putIfAbsent(classContext, processor);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> FieldBuilder<T> getBuilderByContext(FieldContext<T> fieldContext, ObjectResolver context) {
        return buildersByContext.get(fieldContext);
    }

    @Override
    public <T> ProcessingCache putBuilderByContext(FieldContext<T> fieldContext, FieldBuilder<T> builder, ObjectResolver context) {
        buildersByContext.putIfAbsent(fieldContext, builder);
        return this;
    }

    @Override
    public <T> ProcessingFieldFilter getProcessingFieldFilterByContext(ClassContext<T> classContext, ObjectResolver context) {
        return processingFieldFilters.get(classContext);
    }

    @Override
    public <T> ProcessingCache putProcessingFieldFilterByContext(ClassContext<T> classContext, ProcessingFieldFilter filter, ObjectResolver context) {
        processingFieldFilters.putIfAbsent(classContext, filter);
        return this;
    }

}
