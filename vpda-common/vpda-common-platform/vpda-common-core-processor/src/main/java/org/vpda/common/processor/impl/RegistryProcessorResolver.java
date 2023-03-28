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

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.internal.common.util.Assert;

/**
 * Resolves {@link ClassProcessor} using {@link ProcessingRegistry}
 * 
 * @author kitko
 *
 */
public final class RegistryProcessorResolver implements ProcessorResolver {
    private final ProcessingRegistry registry;

    /**
     * Creates Processor with registry
     * 
     * @param registry
     */
    public RegistryProcessorResolver(ProcessingRegistry registry) {
        this.registry = Assert.isNotNullArgument(registry, "registry");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ClassProcessor<T> resolveTargetProcessor(ClassContext<T> classContext, ObjectResolver context) {
        ClassProcessor<?> classProcessor = null;
        classProcessor = registry.getProcessorByClassContext(classContext, context);
        if (classProcessor == null) {
            classProcessor = registry.getProcessorByProcessedAndTargetClass(classContext.getProcessedClass(), classContext.getTargetClass(), context);
        }
        if (classProcessor == null) {
            Annotation a = context.resolveObject(Annotation.class);
            if (a != null) {
                classProcessor = registry.getProcessorByAnnotation(a.annotationType(), classContext.getProcessedClass(), classContext.getTargetClass(), context);
            }
            else {
                for (Annotation ai : classContext.getProcessedClass().getAnnotations()) {
                    ClassProcessor<?> aProcessor = registry.getProcessorByAnnotation(ai.annotationType(), classContext.getProcessedClass(), classContext.getTargetClass(), context);
                    if (aProcessor != null) {
                        if (classProcessor == null) {
                            classProcessor = aProcessor;
                        }
                        else if (classProcessor != aProcessor) {
                            // More processor would be resolved, break
                            classProcessor = null;
                            break;
                        }
                    }
                }
            }
        }
        ClassProcessor<T> result = (ClassProcessor<T>) classProcessor;
        return result != null ? (result.canProcess(classContext, this, context) ? result : null) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ItemsClassProcessor<T> resolveTargetItemsProcessor(ClassItemContext<T> itemContext, ObjectResolver context) {
        ItemsClassProcessor<?> itemProcessor = null;
        itemProcessor = registry.getItemsProcessorByItemContext(itemContext, context);
        if (itemProcessor == null) {
            Annotation a = context.resolveObject(Annotation.class);
            if (a != null) {
                itemProcessor = registry.getItemProcessorByAnnotation(a.annotationType(), itemContext.getProcessedClass(), itemContext.getTargetItemClass(), context);
            }
            else {
                for (Annotation ai : itemContext.getProcessedClass().getAnnotations()) {
                    ItemsClassProcessor<?> aProcessor = registry.getItemProcessorByAnnotation(ai.annotationType(), itemContext.getProcessedClass(), itemContext.getTargetItemClass(), context);
                    if (aProcessor != null) {
                        if (itemProcessor == null) {
                            itemProcessor = aProcessor;
                        }
                        else if (itemProcessor != aProcessor) {
                            // More processor would be resolved, break
                            itemProcessor = null;
                            break;
                        }
                    }
                }
            }
        }
        if (itemProcessor == null) {
            itemProcessor = registry.getItemsProcessorByProcessedAndTargetItemClass(itemContext.getProcessedClass(), itemContext.getTargetItemClass(), context);
        }
        return (ItemsClassProcessor<T>) (itemProcessor != null ? (itemProcessor.canProcess(itemContext, this, context) ? itemProcessor : null) : null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> FieldBuilder<T> resolveTargetFieldBuilder(FieldContext<T> fieldContext, ObjectResolver context) {
        FieldBuilder<?> builder = null;
        builder = registry.getBuilderByFieldContext(fieldContext, context);
        if (builder == null) {
            builder = registry.getBuilderByFieldType(fieldContext.getField().getType(), fieldContext.getTargetClass(), fieldContext.getField(), context);
        }
        if (builder == null) {
            Annotation a = context.resolveObject(Annotation.class);
            if (a != null) {
                builder = registry.getBuilderByFieldAnnotation(a.annotationType(), fieldContext.getTargetClass(), fieldContext.getField(), context);
            }
            else {
                for (Annotation ai : fieldContext.getField().getAnnotations()) {
                    FieldBuilder<?> aBuilder = registry.getBuilderByFieldAnnotation(ai.annotationType(), fieldContext.getTargetClass(), fieldContext.getField(), context);
                    if (aBuilder != null) {
                        if (builder == null) {
                            builder = aBuilder;
                        }
                        else if (builder != aBuilder) {
                            // More builder would be resolved, break
                            builder = null;
                            break;
                        }
                    }
                }
                if (builder == null) {
                    for (Annotation ai : fieldContext.getField().getType().getAnnotations()) {
                        FieldBuilder<?> aBuilder = registry.getBuilderByFieldAnnotation(ai.annotationType(), fieldContext.getTargetClass(), fieldContext.getField(), context);
                        if (aBuilder != null) {
                            if (builder == null) {
                                builder = aBuilder;
                            }
                            else if (builder != aBuilder) {
                                // More builder would be resolved, break
                                builder = null;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return (FieldBuilder<T>) (builder != null ? (builder.canBuild(fieldContext, this, context) ? builder : null) : null);
    }

    @Override
    public <T> ProcessingFieldFilter createFieldFilter(ClassContext<T> classContext, ObjectResolver context) {
        return registry.getFieldFilter(classContext, context);
    }

}
