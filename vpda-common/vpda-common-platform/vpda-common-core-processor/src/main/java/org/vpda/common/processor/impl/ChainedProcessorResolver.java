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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * ProcessingResolver that delegates resolving to list of resolvers
 * 
 * @author kitko
 *
 */
public final class ChainedProcessorResolver implements ProcessorResolver {
    private final List<ProcessorResolver> resolvers;

    /**
     * Creates resolver with list of delegates
     * 
     * @param resolvers
     */
    public ChainedProcessorResolver(List<ProcessorResolver> resolvers) {
        this.resolvers = new ArrayList<ProcessorResolver>(resolvers);
    }

    /**
     * Creates resolver with array of resolvers
     * 
     * @param resolvers
     */
    public ChainedProcessorResolver(ProcessorResolver... resolvers) {
        this.resolvers = Arrays.asList(resolvers);
    }

    @Override
    public <T> ClassProcessor<T> resolveTargetProcessor(ClassContext<T> classContext, ObjectResolver context) {
        for (ProcessorResolver resolver : resolvers) {
            ClassProcessor<T> cp = resolver.resolveTargetProcessor(classContext, context);
            if (cp != null && cp.canProcess(classContext, resolver, context)) {
                return cp;
            }
        }
        return null;
    }

    @Override
    public <T> ItemsClassProcessor<T> resolveTargetItemsProcessor(ClassItemContext<T> classItemContext, ObjectResolver context) {
        for (ProcessorResolver resolver : resolvers) {
            ItemsClassProcessor<T> icp = resolver.resolveTargetItemsProcessor(classItemContext, context);
            if (icp != null && icp.canProcess(classItemContext, resolver, context)) {
                return icp;
            }
        }
        return null;
    }

    @Override
    public <T> FieldBuilder<T> resolveTargetFieldBuilder(FieldContext<T> fieldContext, ObjectResolver context) {
        for (ProcessorResolver resolver : resolvers) {
            FieldBuilder<T> builder = resolver.resolveTargetFieldBuilder(fieldContext, context);
            if (builder != null && builder.canBuild(fieldContext, resolver, context)) {
                return builder;
            }
        }
        return null;
    }

    @Override
    public <T> ProcessingFieldFilter createFieldFilter(ClassContext<T> classContext, ObjectResolver context) {
        for (ProcessorResolver resolver : resolvers) {
            ProcessingFieldFilter filter = resolver.createFieldFilter(classContext, context);
            if (filter != null) {
                return filter;
            }
        }
        return null;
    }

}
