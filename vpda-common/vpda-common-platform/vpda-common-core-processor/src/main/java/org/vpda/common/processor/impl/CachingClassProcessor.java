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
import org.vpda.common.processor.ProcessingCache;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ClassUtil;

/**
 * Classprocessor that uses cache to optimize processing of classes
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class CachingClassProcessor<T> extends AbstractClassProcessor<T> implements ClassProcessor<T> {
    private final ClassProcessor<T> delegate;
    private final ProcessingCache cache;

    /**
     * Creates caching processor
     * 
     * @param delegate
     * @param cache
     */
    public CachingClassProcessor(ClassProcessor<T> delegate, ProcessingCache cache) {
        super();
        this.delegate = Assert.isNotNullArgument(delegate, "delegate");
        this.cache = Assert.isNotNullArgument(cache, "cache");
    }

    @Override
    public T process(ClassContext<T> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        boolean hasParentOrInstance = classContext.getParent() != null || classContext.getInstance() != null;
        ProcessingInfo processingInfo = ProcessingInfoHelper.resolveActiveProcessingInfo(classContext, context);
        boolean getFromCache = processingInfo != null ? processingInfo.cacheProcessedObject() : !hasParentOrInstance;
        if (getFromCache) {
            Object result = cache.getProcessedObject(classContext, context);
            if (result != null) {
                if (getTargetClass().isInstance(result)) {
                    return getTargetClass().cast(result);
                }
            }
        }
        T t = delegate.process(classContext, processorResolver, context);
        Boolean putToCache = null;
        if (t == null || hasParentOrInstance) {
            putToCache = false;
        }
        else if (processingInfo != null) {
            putToCache = processingInfo.cacheProcessedObject();
        }
        else {
            putToCache = ClassUtil.isImmutable(t);
        }
        if (putToCache) {
            cache.putProcessedObject(classContext, t, context);
        }
        return t;
    }

    @Override
    public Class<? extends T> getTargetClass() {
        return delegate.getTargetClass();
    }

}
