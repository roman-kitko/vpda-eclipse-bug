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
package org.vpda.common.processor;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Resolves ClassProcessor for clazz and FieldBuilder for field
 * 
 * @author kitko
 *
 */
public interface ProcessorResolver {
    /**
     * Resolves ClassProcessor
     * 
     * @param <T>
     * @param classContext
     * @param context
     * @return ClassProcessor
     */
    public <T> ClassProcessor<T> resolveTargetProcessor(ClassContext<T> classContext, ObjectResolver context);

    /**
     * Resolves ClassProcessor
     * 
     * @param <T>
     * @param classContext
     * @param context
     * @return ClassProcessor
     */
    public <T> ItemsClassProcessor<T> resolveTargetItemsProcessor(ClassItemContext<T> classContext, ObjectResolver context);

    /**
     * @param <T>
     * @param fieldContext
     * @param context
     * @return field builder for the field
     */
    public <T> FieldBuilder<T> resolveTargetFieldBuilder(FieldContext<T> fieldContext, ObjectResolver context);

    /**
     * @param classContext
     * @param context
     * @return field filter for processing of class fields
     */
    public <T> ProcessingFieldFilter createFieldFilter(ClassContext<T> classContext, ObjectResolver context);

}
