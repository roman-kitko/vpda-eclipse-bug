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
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Builder for one particular field
 * 
 * @author kitko
 * @param <T> type of field
 *
 */
public interface FieldBuilder<T> {

    /**
     * Tests whether this builder is capable to build the object from the field
     * resulting to instance of targetClass
     * 
     * @param fieldContext
     * @param resolver
     * @param context
     * @return true if builder can be the field, otherwise returns false
     */
    public boolean canBuild(FieldContext<?> fieldContext, ProcessorResolver resolver, ObjectResolver context);

    /**
     * Builds object from annotated field
     * 
     * @param fieldContext
     * @param resolver
     * @param context
     * @return new built object
     */
    public T build(FieldContext<T> fieldContext, ProcessorResolver resolver, ObjectResolver context);

    /**
     * Builds object from field, forcing the builder to create target type
     * 
     * @param fieldContext
     * @param resolver
     * @param context
     * @return new built object of type Z
     */
    public <Z extends T> Z buildByType(FieldContext<Z> fieldContext, ProcessorResolver resolver, ObjectResolver context);

    /**
     * @return target class this builder is able to build
     */
    public Class<? extends T> getTargetClass();
}
