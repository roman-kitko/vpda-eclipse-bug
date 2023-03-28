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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingCache;
import org.vpda.common.processor.ProcessingException;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.Ignored;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.annotation.eval.ProcessingContextEvalAnn;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.internal.common.util.ClassUtil;

/**
 * Helper class for class processing
 * 
 * @author kitko
 *
 */
public final class ProcessorHelper {
    private ProcessorHelper() {
    }

    /**
     * Will handle children of class that must be annotated with passed annotation
     * 
     * @param <T>
     * @param itemContext
     * @param processorResolver
     * @param context
     * @param annClass
     * @param defProcessors
     * @return new built object built by resolved processor or null if annotation is
     *         not present on class
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> handleChildClassFieldsForAnnotatedClass(ClassItemContext<T> itemContext, ProcessorResolver processorResolver, ObjectResolver context,
            Class<? extends Annotation> annClass, ItemsClassProcessor<?>... defProcessors) {
        Annotation annotation = ProcessingContextEvalAnn.evalAnnotation(annClass, context, itemContext);
        if (annotation != null) {
            if (itemContext.getProcessedClass().isAnnotationPresent(Ignored.class)) {
                return Collections.emptyList();
            }
            ItemsClassProcessor<T> processor = processorResolver.resolveTargetItemsProcessor(itemContext,
                    new MacroObjectResolverImpl(new SingleObjectResolver<Annotation>(Annotation.class, annotation), context));
            if (processor == null) {
                for (ItemsClassProcessor i : defProcessors) {
                    if (i != null && i.canProcess(itemContext, processorResolver, context)) {
                        processor = i;
                        break;
                    }
                }
            }
            if (processor == null) {
                throw new ProcessingException(MessageFormat.format("Cannot resolve item processor for item context [{0}]", itemContext));
            }
            return processor.process(itemContext, processorResolver, context);
        }
        return null;
    }

    /**
     * Process fields of clazz considering passed annotation. Field itself or field
     * type must be annotated with passed annotation
     * 
     * @param <T>
     * @param itemContext
     * @param processorResolver
     * @param context
     * @param annClass
     * @return list of built objects
     */
    public static <T> List<T> processAnnotatedFields(ClassItemContext<T> itemContext, ProcessorResolver processorResolver, ObjectResolver context, Class<? extends Annotation> annClass) {
        List<Class<? extends Annotation>> list = annClass != null ? Collections.<Class<? extends Annotation>>singletonList(annClass) : Collections.<Class<? extends Annotation>>emptyList();
        return processAnnotatedFields(itemContext, processorResolver, context, list);
    }

    /**
     * Process fields of clazz considering passed annotations. Field itself or field
     * type must be annotated with one of passed annotation
     * 
     * @param <T>
     * @param itemContext
     * @param processorResolver
     * @param context
     * @param annClasses
     * @return list of built objects
     */
    public static <T> List<T> processAnnotatedFields(ClassItemContext<T> itemContext, ProcessorResolver processorResolver, ObjectResolver context, List<Class<? extends Annotation>> annClasses) {
        ProcessingInfo processingInfo = itemContext.getProcessedClass().getAnnotation(ProcessingInfo.class);
        List<Field> fields = null;
        if (processingInfo != null && !processingInfo.processSuper()) {
            fields = Arrays.asList(itemContext.getProcessedClass().getDeclaredFields());
        }
        else {
            fields = ClassUtil.getDeclaredAndInheritedFields(itemContext.getProcessedClass());
        }
        List<T> objects = new ArrayList<T>();
        ProcessingFieldFilter processingFieldFilter = processorResolver.createFieldFilter(itemContext, context);
        for (Field field : fields) {
            if (!processingFieldFilter.accept(itemContext, field, context)) {
                continue;
            }
            FieldBuilder<T> builder = null;
            FieldContext<T> fieldContext = FieldContext.createFieldContext(itemContext, field, itemContext.getTargetItemClass());
            for (Class<? extends Annotation> annClazz : annClasses) {
                Annotation a = ProcessingContextEvalAnn.evalAnnotation(annClazz, context, fieldContext);
                if (a != null) {
                    builder = processorResolver.resolveTargetFieldBuilder(fieldContext, new MacroObjectResolverImpl(new SingleObjectResolver<Annotation>(Annotation.class, a), context));
                }
                else {
                    a = ProcessingContextEvalAnn.evalAnnotation(field.getType(), annClazz, context, fieldContext);
                    if (a != null) {
                        builder = processorResolver.resolveTargetFieldBuilder(fieldContext, new MacroObjectResolverImpl(new SingleObjectResolver<Annotation>(Annotation.class, a), context));

                    }
                }
                if (builder != null) {
                    break;
                }
            }
            if (builder == null) {
                builder = processorResolver.resolveTargetFieldBuilder(fieldContext, context);
            }
            if (builder != null && builder.canBuild(fieldContext, processorResolver, context)) {
                T t = builder.build(fieldContext, processorResolver, context);
                t = processingFieldFilter.postBuildField(fieldContext, processorResolver, context, t);
                if (t != null) {
                    objects.add(t);
                }
            }
        }
        return objects;
    }

    /**
     * Gets declared classes of class considering {@link ProcessingInfo} whether to
     * look also at parent class
     * 
     * @param classContext
     * @param context
     * @return list of declared classes
     */
    public static List<Class<?>> getDeclaredClassesConsideringProcessingInfo(ClassContext<?> classContext, ObjectResolver context) {
        ProcessingInfo processingInfo = ProcessingInfoHelper.resolveActiveProcessingInfo(classContext, context);
        List<Class<?>> classes = null;
        if (processingInfo != null && !processingInfo.processSuper()) {
            classes = Arrays.asList(classContext.getProcessedClass().getDeclaredClasses());
        }
        else {
            classes = ClassUtil.getDeclaredAndInheritedClasses(classContext.getProcessedClass());
        }
        List<Class<?>> result = new ArrayList<Class<?>>();
        for (Class<?> i : classes) {
            if (!i.isAnnotationPresent(Ignored.class)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Collect declared fields in class considering {@link ProcessingInfo}
     * annotation also in super class
     * 
     * @param classContext
     * @param context
     * @return list of declared fields in that class and possible super classes
     */
    public static List<Field> getDeclaredFieldsConsideringProcessingInfo(ClassContext<?> classContext, ObjectResolver context) {
        ProcessingInfo processingInfo = ProcessingInfoHelper.resolveActiveProcessingInfo(classContext, context);
        List<Field> fields = null;
        if (processingInfo != null && !processingInfo.processSuper()) {
            fields = Arrays.asList(classContext.getProcessedClass().getDeclaredFields());
        }
        else {
            fields = ClassUtil.getDeclaredAndInheritedFields(classContext.getProcessedClass());
        }
        List<Field> result = new ArrayList<Field>();
        for (Field i : fields) {
            if (!i.isAnnotationPresent(Ignored.class)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Resolves processor for given class and process that class
     * 
     * @param <T>
     * @param classContext
     * @param resolver
     * @param context
     * @param defProcessors
     * @return result of class processing
     */
    @SuppressWarnings("unchecked")
    public static <T> T resolveAndProcessClass(ClassContext<T> classContext, ProcessorResolver resolver, ObjectResolver context, ClassProcessor<?>... defProcessors) {
        ClassProcessor<T> processor = resolver.resolveTargetProcessor(classContext, context);
        if (processor == null) {
            for (ClassProcessor<?> c : defProcessors) {
                if (c != null && c.canProcess(classContext, resolver, context)) {
                    processor = (ClassProcessor<T>) c;
                }
            }
        }
        if (processor == null) {
            throw new ProcessingException(MessageFormat.format("Cannot resolve processor for class context [{0}]", classContext));
        }
        return processor.process(classContext, resolver, context);
    }

    /**
     * Resolves items processor and process that class for items
     * 
     * @param <T>
     * @param classItemContext
     * @param resolver
     * @param context
     * @param defProcessors
     * @return list of items
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> resolveAndProcessItems(ClassItemContext<T> classItemContext, ProcessorResolver resolver, ObjectResolver context, ItemsClassProcessor<?>... defProcessors) {
        ItemsClassProcessor<T> processor = resolver.resolveTargetItemsProcessor(classItemContext, context);
        if (processor == null) {
            for (ItemsClassProcessor<?> c : defProcessors) {
                if (c != null && c.canProcess(classItemContext, resolver, context)) {
                    processor = (ItemsClassProcessor<T>) c;
                }
            }
        }
        if (processor == null) {
            throw new ProcessingException(MessageFormat.format("Cannot resolve item processor for context [{0}]", classItemContext));
        }
        return processor.process(classItemContext, resolver, context);
    }

    /**
     * Resolves field builder and builds object using it
     * 
     * @param <T>
     * @param fieldContext
     * @param resolver
     * @param context
     * @param defBuilders
     * @return built object
     */
    @SuppressWarnings("unchecked")
    public static <T> T resolveAndBuildField(FieldContext<T> fieldContext, ProcessorResolver resolver, ObjectResolver context, FieldBuilder<?>... defBuilders) {
        FieldBuilder<T> builder = resolver.resolveTargetFieldBuilder(fieldContext, context);
        if (builder == null) {
            for (FieldBuilder def : defBuilders) {
                if (def != null && def.canBuild(fieldContext, resolver, context)) {
                    builder = def;
                }
            }
        }
        if (builder == null) {
            throw new ProcessingException(MessageFormat.format("Cannot resolve builder  for field [{0}]", fieldContext));
        }
        return builder.build(fieldContext, resolver, context);
    }

    /**
     * Creates required resolver that delegates to info resolver and registry
     * resolvers
     * 
     * @param registries array of all registries we can delegate to
     * @param cache
     * @return resolver
     */
    public static ProcessorResolver createReqInfoAndRegistryCheckedCachingResolver(ProcessingCache cache, ProcessingRegistry... registries) {
        List<ProcessorResolver> resolvers = new ArrayList<ProcessorResolver>();
        resolvers.add(new InfoProcessorResolver());
        for (ProcessingRegistry registry : registries) {
            resolvers.add(new RegistryProcessorResolver(new CachingProcessingRegistry(registry, cache)));
        }
        ProcessorResolver chain = new ChainedProcessorResolver(resolvers);
        ProcessorResolver checked = new ReturnNullOnCannotProcessProcessorResolver(chain);
        ProcessorResolver required = new RequiredProcessorResolver(checked);
        ProcessorResolver cached = new CachingProcessorResolver(required, cache);
        return cached;
    }

    /**
     * Creates required resolver that delegates to info resolver and registry
     * resolvers with no caching
     * 
     * @param registries
     * @return no caching
     */
    public static ProcessorResolver createReqInfoAndRegistryCheckedNOCachingResolver(ProcessingRegistry... registries) {
        List<ProcessorResolver> resolvers = new ArrayList<ProcessorResolver>();
        resolvers.add(new InfoProcessorResolver());
        for (ProcessingRegistry registry : registries) {
            resolvers.add(new RegistryProcessorResolver(registry));
        }
        ProcessorResolver chain = new ChainedProcessorResolver(resolvers);
        ProcessorResolver checked = new ReturnNullOnCannotProcessProcessorResolver(chain);
        ProcessorResolver required = new RequiredProcessorResolver(checked);
        return required;
    }

    /**
     * Returns delegate of Required resolver
     * 
     * @param resolver
     * @return delegate of Required resolver or same resolver
     */
    public static ProcessorResolver createNotRequiredResolver(ProcessorResolver resolver) {
        if (resolver instanceof RequiredProcessorResolver) {
            return ((RequiredProcessorResolver) resolver).getDelegate();
        }
        return resolver;
    }

    static <T> ClassProcessor<T> createCachedProcessor(ClassProcessor<T> processor, ProcessingCache cache) {
        if (processor == null) {
            return null;
        }
        if (processor instanceof CachingClassProcessor) {
            return processor;
        }
        return new CachingClassProcessor<T>(processor, cache);
    }

    static <T> ItemsClassProcessor<T> createCachedItemsProcessor(ItemsClassProcessor<T> processor, ProcessingCache cache) {
        if (processor == null) {
            return null;
        }
        if (processor instanceof CachingItemsClassProcessor) {
            return processor;
        }
        return new CachingItemsClassProcessor<T>(processor, cache);
    }

    static <T> FieldBuilder<T> createCachedFieldBuilder(FieldBuilder<T> builder, ProcessingCache cache) {
        if (builder == null) {
            return null;
        }
        if (builder instanceof CachingFieldBuilder) {
            return builder;
        }
        return new CachingFieldBuilder<T>(builder, cache);
    }

}
