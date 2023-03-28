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

import java.lang.reflect.Field;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Filter for member processing
 * 
 * @author kitko
 *
 */
public interface ProcessingFieldFilter {
    /**
     * 
     * @param clazzCtx
     * @param field
     * @param context
     * @return true if we can accept and process field
     */
    public boolean accept(ClassContext<?> clazzCtx, Field field, ObjectResolver context);

    /**
     * Post build field value
     * 
     * @param fieldCtx
     * @param processorResolver
     * @param context
     * @param builtValue
     * @return by default same value
     */
    public default <T> T postBuildField(FieldContext<T> fieldCtx, ProcessorResolver processorResolver, ObjectResolver context, T builtValue) {
        return builtValue;
    }
}
