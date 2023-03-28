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
import org.vpda.common.processor.annotation.IgnoredIfNull;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * {@link ProcessingFieldFilter} that will use {@link Ignored} and
 * {@link IgnoredIfNotRoot} annotations for current field only
 * 
 * @author kitko
 *
 */
public final class IgnoringProcessingFieldFilter implements ProcessingFieldFilter {

    private static final IgnoringProcessingFieldFilter INSTANCE = new IgnoringProcessingFieldFilter();

    /**
     * @return instance
     */
    public static IgnoringProcessingFieldFilter getInstance() {
        return INSTANCE;
    }

    /**
     * Creates IgnoringProcessingFieldFilter
     */
    public IgnoringProcessingFieldFilter() {
    }

    @Override
    public boolean accept(ClassContext<?> clazzCtx, Field field, ObjectResolver context) {
        if (field.isAnnotationPresent(Ignored.class)) {
            return false;
        }
        if (clazzCtx.getParent() != null && field.isAnnotationPresent(IgnoredIfNotRoot.class)) {
            return false;
        }
        if (field.isAnnotationPresent(IgnoredIfNull.class)) {
            if (clazzCtx.getInstance() == null) {
                return false;
            }
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(clazzCtx.getInstance());
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                throw new VPDARuntimeException("Cannot get field value", e);
            }
            if (value == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Factory of {@link IgnoringProcessingFieldFilter}
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
            return IgnoringProcessingFieldFilter.getInstance();
        }

    }

}
