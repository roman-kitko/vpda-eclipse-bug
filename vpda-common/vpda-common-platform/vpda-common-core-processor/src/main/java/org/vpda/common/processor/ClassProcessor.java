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

/**
 * Will process class using reflection or any other manner
 * 
 * @author kitko
 *
 * @param <T> Type of target object we build while processing the class
 */
public interface ClassProcessor<T> {
    /**
     * Process given Class
     * 
     * @param classContext
     * @param processorResolver
     * @param context
     * @return new built object or some metadata for the object
     */
    public T process(ClassContext<T> classContext, ProcessorResolver processorResolver, ObjectResolver context);

    /**
     * @return target class of result object
     */
    public Class<? extends T> getTargetClass();

    /**
     * Test whether this processor is capable to process a class resulting then to
     * creation instance of targetClass
     * 
     * @param classContext
     * @param processorResolver
     * @param context
     * @return true if processor can process the class, else returns false
     */
    public boolean canProcess(ClassContext<?> classContext, ProcessorResolver processorResolver, ObjectResolver context);

}
