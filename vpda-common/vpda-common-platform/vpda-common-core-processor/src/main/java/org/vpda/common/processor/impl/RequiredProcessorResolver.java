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
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ResolvingException;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.util.exceptions.VPDAConfigurationRuntimeException;
import org.vpda.internal.common.util.Assert;

/**
 * ProcessorResolver that throws {@link VPDAConfigurationRuntimeException} when
 * we cannot resolve resolver or field builder
 * 
 * @author kitko
 *
 */
public final class RequiredProcessorResolver implements ProcessorResolver {
    private final ProcessorResolver delegate;

    /**
     * @param delegate
     */
    public RequiredProcessorResolver(ProcessorResolver delegate) {
        this.delegate = Assert.isNotNullArgument(delegate, "delegate");
    }

    @Override
    public <T> ClassProcessor<T> resolveTargetProcessor(ClassContext<T> classContext, ObjectResolver context) {
        ClassProcessor<T> processor = delegate.resolveTargetProcessor(classContext, context);
        if (processor != null) {
            return processor;
        }
        throw new ResolvingException("Cannot resolve target processor for [" + classContext + "]");
    }

    @Override
    public <T> FieldBuilder<T> resolveTargetFieldBuilder(FieldContext<T> fieldContext, ObjectResolver context) {
        FieldBuilder<T> builder = delegate.resolveTargetFieldBuilder(fieldContext, context);
        if (builder != null) {
            return builder;
        }
        throw new ResolvingException("Cannot resolve builder for field [" + fieldContext.getField() + "]");
    }

    @Override
    public <T> ItemsClassProcessor<T> resolveTargetItemsProcessor(ClassItemContext<T> classItemContext, ObjectResolver context) {
        ItemsClassProcessor<T> processor = delegate.resolveTargetItemsProcessor(classItemContext, context);
        if (processor != null) {
            return processor;
        }
        throw new ResolvingException("Cannot resolve target item processor for [" + classItemContext + "]");
    }

    ProcessorResolver getDelegate() {
        return delegate;
    }

    @Override
    public <T> ProcessingFieldFilter createFieldFilter(ClassContext<T> classContext, ObjectResolver context) {
        ProcessingFieldFilter filter = delegate.createFieldFilter(classContext, context);
        if (filter != null) {
            return filter;
        }
        throw new ResolvingException("Cannot resolve ProcessingFieldFilter for classContext [" + classContext + "]");
    }
}
