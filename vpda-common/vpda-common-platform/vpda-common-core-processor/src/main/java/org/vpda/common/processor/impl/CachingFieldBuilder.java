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
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ProcessingCache;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.FieldBuilderInfo;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.internal.common.util.ClassUtil;

/**
 * Will try to cache built values for specified field builder
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class CachingFieldBuilder<T> implements FieldBuilder<T> {
    private final FieldBuilder<T> delegate;
    private final ProcessingCache cache;

    /**
     * @param delegate
     * @param cache
     */
    public CachingFieldBuilder(FieldBuilder<T> delegate, ProcessingCache cache) {
        super();
        this.delegate = delegate;
        this.cache = cache;
    }

    @Override
    public boolean canBuild(FieldContext<?> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        return delegate.canBuild(fieldContext, resolver, context);
    }

    @Override
    public T build(FieldContext<T> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        boolean hasParent = fieldContext.getParent() != null;
        FieldBuilderInfo activeBuilderInfo = fieldContext.getField().getAnnotation(FieldBuilderInfo.class);
        boolean getFromCache = activeBuilderInfo != null ? activeBuilderInfo.cache() : !hasParent;
        if (getFromCache) {
            T value = cache.getBuiltObject(fieldContext, context);
            if (value != null) {
                return value;
            }
        }
        T value = delegate.build(fieldContext, resolver, context);
        Boolean putToCache = null;
        if (value == null || hasParent) {
            putToCache = false;
        }
        else if (activeBuilderInfo != null) {
            putToCache = activeBuilderInfo.cache();
        }
        else {
            putToCache = ClassUtil.isImmutable(value);
        }
        if (putToCache) {
            cache.putBuiltObject(fieldContext, value, context);
        }
        return value;
    }

    @Override
    public <Z extends T> Z buildByType(FieldContext<Z> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        boolean hasParent = fieldContext.getParent() != null;
        FieldBuilderInfo activeBuilderInfo = fieldContext.getField().getAnnotation(FieldBuilderInfo.class);
        boolean getFromCache = activeBuilderInfo != null ? activeBuilderInfo.cache() : !hasParent;
        if (getFromCache) {
            T value = cache.getBuiltObject(fieldContext, context);
            if (fieldContext.getTargetClass().isInstance(value)) {
                return fieldContext.getTargetClass().cast(value);
            }
        }
        Z value = delegate.buildByType(fieldContext, resolver, context);
        Boolean putToCache = null;
        if (value == null || hasParent) {
            putToCache = false;
        }
        else if (activeBuilderInfo != null) {
            putToCache = activeBuilderInfo.cache();
        }
        else {
            putToCache = ClassUtil.isImmutable(value);
        }
        if (putToCache) {
            cache.putBuiltObject(fieldContext, value, context);
        }
        return value;
    }

    @Override
    public Class<? extends T> getTargetClass() {
        return delegate.getTargetClass();
    }

}
