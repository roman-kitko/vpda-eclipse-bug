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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessingFieldFilterFactory;
import org.vpda.common.processor.ResolvingException;
import org.vpda.common.processor.annotation.ForceIncludeFilterInfo;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.ctx.ProcessingContext;

/**
 * @author kitko
 *
 */
public final class ForceIncludeProcessingFieldFilter implements ProcessingFieldFilter {

    private final ForceIncludeFilterInfo includeInfo;
    private final ClassContext<?> rootCtx;

    /**
     * Creates ForceIncludeProcessingFieldFilter
     * 
     * @param classContext
     * @param context
     */
    public ForceIncludeProcessingFieldFilter(ClassContext<?> classContext, ObjectResolver context) {
        includeInfo = classContext.getProcessedClass().getAnnotation(ForceIncludeFilterInfo.class);
        if (includeInfo == null) {
            throw new ResolvingException("Must have ForceIncludeFilterInfo for [" + classContext + "] to filter fields");
        }
        this.rootCtx = classContext;
    }

    @Override
    public boolean accept(ClassContext<?> clazzCtx, Field field, ObjectResolver context) {
        if (!rootCtx.equals(clazzCtx)) {
            ProcessingContext parent = clazzCtx.getParent();
            while (parent != null) {
                if (parent.getParent() == null) {
                    if (!rootCtx.equals(parent)) {
                        return false;
                    }
                }
                parent = parent.getParent();
            }
        }
        for (String path : includeInfo.value()) {
            String[] parts = path.split("/");
            List<String> fieldContextParentNames = new ArrayList<>();
            fieldContextParentNames.add(field.getName());
            ProcessingContext parent = clazzCtx.getParent();
            while (parent != null) {
                if (parent instanceof FieldContext) {
                    fieldContextParentNames.add(((FieldContext) parent).getField().getName());
                }
                parent = parent.getParent();
            }
            Collections.reverse(fieldContextParentNames);
            if (parts.length == fieldContextParentNames.size()) {
                if (Arrays.asList(parts).equals(fieldContextParentNames)) {
                    return true;
                }
            }
            else if (parts.length > fieldContextParentNames.size()) {
                if (Arrays.asList(parts).subList(0, fieldContextParentNames.size()).equals(fieldContextParentNames)) {
                    return true;
                }
            }
            else {
                if (fieldContextParentNames.subList(0, parts.length).equals(Arrays.asList(parts))) {
                    return true;
                }
            }
        }
        return IgnoringPathProcessingFieldFilter.getInstance().accept(clazzCtx, field, context);
    }

    /**
     * Factory of {@link ForceIncludeProcessingFieldFilter}
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
            return new ForceIncludeProcessingFieldFilter(classContext, context);
        }

    }

}
