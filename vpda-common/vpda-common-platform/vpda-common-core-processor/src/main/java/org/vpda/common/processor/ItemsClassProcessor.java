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
package org.vpda.common.processor;

import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ctx.ClassItemContext;

/**
 * Class procesor that will return list of items with specified item target
 * class
 * 
 * @author kitko
 * @param <T> type of target item
 *
 */
public interface ItemsClassProcessor<T> extends ClassProcessor<List> {

    /**
     * @return item class
     */
    public Class<T> getTargetItemClass();

    /**
     * Overridden method that has correct ClassItemContext
     * 
     * @param classContext
     * @param processorResolver
     * @param context
     * @return list of created items
     */
    public List<T> process(ClassItemContext<T> classContext, ProcessorResolver processorResolver, ObjectResolver context);
}
