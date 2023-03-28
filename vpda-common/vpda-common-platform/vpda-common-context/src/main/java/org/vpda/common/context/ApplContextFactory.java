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
package org.vpda.common.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Helper utilities for creating context and items
 * 
 * @author kitko
 *
 */
public final class ApplContextFactory {

    private ApplContextFactory() {
    }

    /**
     * Creates new ApplContext removing some items from context. Original context is
     * not modified
     * 
     * @param context
     * @param keys
     * @return new context containg all items from original context except items
     *         with same key as keys
     */
    public static ApplContext createNewContextRemovingItems(ApplContext context, ContextItemKey... keys) {
        Collection<ContextItem<?>> newItems = new ArrayList<ContextItem<?>>();
        for (ContextItem<?> i : context.getItems()) {
            if (!Arrays.asList(keys).contains(i.getKey())) {
                newItems.add(i);
            }
        }
        return new ApplContextBuilder().setId(context.getId()).setCode(context.getCode()).setName(context.getName()).setDescription(context.getDescription()).addItems(newItems).build();
    }

}
