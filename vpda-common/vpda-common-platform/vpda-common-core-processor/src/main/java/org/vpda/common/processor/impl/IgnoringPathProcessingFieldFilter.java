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

import java.lang.reflect.Field;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessingFieldFilterFactory;
import org.vpda.common.processor.annotation.Ignored;
import org.vpda.common.processor.annotation.IgnoredIfNotRoot;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.ctx.ProcessingContext;

/**
 * {@link ProcessingFieldFilter} that will use {@link Ignored} and
 * {@link IgnoredIfNotRoot} annotations for all path fields leading to current
 * field only
 * 
 * @author kitko
 *
 */
public final class IgnoringPathProcessingFieldFilter implements ProcessingFieldFilter {

    private static final IgnoringPathProcessingFieldFilter INSTANCE = new IgnoringPathProcessingFieldFilter();

    /**
     * @return instance
     */
    public static IgnoringPathProcessingFieldFilter getInstance() {
        return INSTANCE;
    }

    /**
     * Creates IgnoringProcessingFieldFilter
     */
    public IgnoringPathProcessingFieldFilter() {
    }

    @Override
    public boolean accept(ClassContext<?> clazzCtx, Field field, ObjectResolver context) {
        IgnoringProcessingFieldFilter ignInstance = IgnoringProcessingFieldFilter.getInstance();
        boolean accept = ignInstance.accept(clazzCtx, field, context);
        if (!accept) {
            return false;
        }

        ProcessingContext ctx = clazzCtx;
        while (ctx != null) {
            if (ctx instanceof FieldContext) {
                Field f = ((FieldContext) ctx).getField();
                if (ctx.getParent() instanceof ClassContext) {
                    // This is actually class where field should be defined, but we will pass our
                    // processing class
                    @SuppressWarnings("unused")
                    ClassContext cCtx = (ClassContext) ctx.getParent();
                    accept = ignInstance.accept(clazzCtx, f, context);
                    if (!accept) {
                        return false;
                    }
                }
            }
            ctx = ctx.getParent();
        }
        return true;
    }

    /**
     * Factory of {@link IgnoringPathProcessingFieldFilter}
     * 
     * @author kitko
     *
     */
    public static final class Factory implements ProcessingFieldFilterFactory {
        private static final Factory INSTANCE = new Factory();

        /**
         * @return instance
         */
        public static Factory getInstance() {
            return INSTANCE;
        }

        @Override
        public <T> ProcessingFieldFilter createFilter(ClassContext<T> classContext, ObjectResolver context) {
            return IgnoringPathProcessingFieldFilter.getInstance();
        }

    }

}
