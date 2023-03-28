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

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessingFieldFilterFactory;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.FieldBuildingInfo;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.annotation.TargetItemProcessingInfo;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.internal.common.util.ClassUtil;

/**
 * Will use {@link ProcessingInfo} and {@link FieldBuildingInfo} to resolve
 * {@link ClassProcessor} and {@link FieldBuilder}
 * 
 * @author kitko
 *
 */
public final class InfoProcessorResolver implements ProcessorResolver {

    /**
     * Creates InfoProcessorResolver
     */
    public InfoProcessorResolver() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ClassProcessor<T> resolveTargetProcessor(ClassContext<T> classContext, ObjectResolver context) {
        ProcessingInfo processingInfo = ProcessingInfoHelper.resolveActiveProcessingInfo(classContext, context);
        if (processingInfo != null && !ClassProcessor.class.equals(processingInfo.processorClass())) {
            ClassProcessor<T> processor = (ClassProcessor<T>) createProcessor(processingInfo.processorClass(), context);
            if (processor.canProcess(classContext, this, context)) {
                return processor;
            }
        }
        return null;
    }

    private ClassProcessor<?> createProcessor(Class<? extends ClassProcessor> processorClass, ObjectResolver context) {
        return ClassUtil.createInstance(processorClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ItemsClassProcessor<T> resolveTargetItemsProcessor(ClassItemContext<T> classItemContext, ObjectResolver context) {
        TargetItemProcessingInfo info = ProcessingInfoHelper.resolveActiveTargetItemProcessingInfo(classItemContext, context);
        if (info != null && !info.processorClass().equals(ItemsClassProcessor.class)) {
            ItemsClassProcessor<T> processor = (ItemsClassProcessor<T>) createProcessor(info.processorClass(), context);
            if (processor.canProcess(classItemContext, this, context)) {
                return processor;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> FieldBuilder<T> resolveTargetFieldBuilder(FieldContext<T> fieldContext, ObjectResolver context) {
        FieldBuildingInfo info = ProcessingInfoHelper.resolveActiveBuilderInfo(fieldContext, context);
        if (info != null && !info.builderClass().equals(FieldBuilder.class)) {
            FieldBuilder<T> builder = (FieldBuilder<T>) createBuilder(info.builderClass(), context);
            if (builder.canBuild(fieldContext, this, context)) {
                return builder;
            }
        }
        return null;
    }

    private FieldBuilder<?> createBuilder(Class<? extends FieldBuilder> builderClass, ObjectResolver context) {
        return ClassUtil.createInstance(builderClass);
    }

    @Override
    public <T> ProcessingFieldFilter createFieldFilter(ClassContext<T> classContext, ObjectResolver context) {
        ProcessingInfo processingInfo = ProcessingInfoHelper.resolveActiveProcessingInfo(classContext, context);
        if (processingInfo == null) {
            ProcessingFieldFilter filter = context.resolveObject(ProcessingFieldFilter.class);
            if (filter != null) {
                return filter;
            }
            return IgnoringProcessingFieldFilter.getInstance();
        }
        ProcessingFieldFilter ctxFilter = context.resolveObject(ProcessingFieldFilter.class);
        if (processingInfo.fieldFilterFactory() != ProcessingFieldFilterFactory.class) {
            ProcessingFieldFilterFactory factory = ClassUtil.createInstanceEvenPrivate(processingInfo.fieldFilterFactory());
            ProcessingFieldFilter filter = factory.createFilter(classContext, context);
            return ctxFilter != null ? new AcceptWinsChainingProcessingFieldFilter(filter, ctxFilter) : filter;
        }
        return ctxFilter != null ? ctxFilter : IgnoringProcessingFieldFilter.getInstance();
    }

}
