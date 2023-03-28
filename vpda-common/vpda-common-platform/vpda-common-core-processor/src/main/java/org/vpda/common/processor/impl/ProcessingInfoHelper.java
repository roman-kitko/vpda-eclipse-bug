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

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.annotation.FieldBuilderInfo;
import org.vpda.common.processor.annotation.FieldBuildingInfo;
import org.vpda.common.processor.annotation.ProcessingInfo;
import org.vpda.common.processor.annotation.TargetItemProcessingInfo;
import org.vpda.common.processor.annotation.eval.ProcessingContextEvalAnn;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Utility class regarding {@link ProcessingInfo}, {@link FieldBuilderInfo} and
 * {@link TargetItemProcessingInfo}
 * 
 * @author kitko
 *
 */
public final class ProcessingInfoHelper {
    private ProcessingInfoHelper() {
    }

    /**
     * Will resolve active processing info for class
     * 
     * @param clazzContext
     * @param context
     * @return active info or null if not found
     */
    public static ProcessingInfo resolveActiveProcessingInfo(ClassContext<?> clazzContext, ObjectResolver context) {
        return ProcessingContextEvalAnn.evalAnnotation(ProcessingInfo.class, context, clazzContext, a -> a.processGroups(), a -> a.targetClass());
    }

    /**
     * Will resolve active builder info for field
     * 
     * @param fieldContext
     * @param context
     * @return active info or null if not found
     */
    public static FieldBuildingInfo resolveActiveBuilderInfo(FieldContext<?> fieldContext, ObjectResolver context) {
        return ProcessingContextEvalAnn.evalAnnotation(FieldBuildingInfo.class, context, fieldContext, a -> a.buildGroups(), a -> a.targetClass());
    }

    /**
     * Resolves active TargetItemProcessingInfo for class
     * 
     * @param classItemContext
     * @param context
     * @return active TargetItemProcessingInfo or null if not found or applicable
     */
    public static TargetItemProcessingInfo resolveActiveTargetItemProcessingInfo(ClassItemContext<?> classItemContext, ObjectResolver context) {
        return ProcessingContextEvalAnn.evalAnnotation(TargetItemProcessingInfo.class, context, classItemContext, a -> a.processGroups(), a -> a.targetItemClass());
    }

}
