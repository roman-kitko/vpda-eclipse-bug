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
import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.internal.common.util.Assert;

/**
 * 
 * @author kitko
 *
 */
public final class AcceptWinsChainingProcessingFieldFilter implements ProcessingFieldFilter {

    private final List<ProcessingFieldFilter> filters;

    /**
     * Creates AcceptWinsChainingProcessingFieldFilter
     * 
     * @param filters
     */
    public AcceptWinsChainingProcessingFieldFilter(List<ProcessingFieldFilter> filters) {
        this.filters = new ArrayList<>(Assert.isNotEmptyArgument(filters, "filters"));
    }

    /**
     * Creates AcceptWinsChainingProcessingFieldFilter
     * 
     * @param filters
     */
    public AcceptWinsChainingProcessingFieldFilter(ProcessingFieldFilter... filters) {
        this.filters = Arrays.asList(Assert.isNotEmptyArgument(filters, "filters"));
    }

    @Override
    public boolean accept(ClassContext<?> clazzCtx, Field field, ObjectResolver context) {
        for (ProcessingFieldFilter filter : filters) {
            boolean accept = filter.accept(clazzCtx, field, context);
            if (accept) {
                return true;
            }
        }
        return false;
    }

}
