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
package org.vpda.common.processor.annotation.eval;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Function;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.annotation.GroupFiltering;
import org.vpda.common.processor.annotation.TargetClassFiltering;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.ctx.ProcessingContext;

/**
 * Can evaluate annotations of fields and classes
 * 
 * @author kitko
 *
 */
public final class ProcessingContextEvalAnn {

    private ProcessingContextEvalAnn() {
    }

    /**
     * Will processes annotated element considering here {@link GroupFiltering} and
     * {@link TargetClassFiltering} to filer that annotation
     * 
     * @param annotationClass
     * @param context
     * @param processingCtx
     * @return annotation or null if not resolved
     */
    public static <A extends Annotation> A evalAnnotation(Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx) {
        if (processingCtx instanceof ClassContext) {
            return ProcessingContextEvalAnnImpl.evalAnnotation(((ClassContext) processingCtx).getProcessedClass(), annotationClass, context, processingCtx);
        }
        else if (processingCtx instanceof FieldContext) {
            return ProcessingContextEvalAnnImpl.evalAnnotation(((FieldContext) processingCtx).getField(), annotationClass, context, processingCtx);
        }
        else if (processingCtx instanceof ClassItemContext) {
            return ProcessingContextEvalAnnImpl.evalAnnotation(((ClassItemContext) processingCtx).getTargetItemClass(), annotationClass, context, processingCtx);
        }
        else {
            throw new IllegalArgumentException("Unsupported processingCtx : " + processingCtx);
        }
    }

    /**
     * Will processes annotated element considering here {@link GroupFiltering} and
     * {@link TargetClassFiltering} to filer that annotation
     * 
     * @param element
     * @param annotationClass
     * @param context
     * @param processingCtx
     * @return annotation or null if not resolved
     */
    public static <A extends Annotation> A evalAnnotation(AnnotatedElement element, Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx) {
        return ProcessingContextEvalAnnImpl.evalAnnotation(element, annotationClass, context, processingCtx);
    }

    /**
     * Will evaluate annotation using groups and target class
     * 
     * @param annotationClass
     * @param context
     * @param processingCtx
     * @param getGroupsFunction
     * @param getTargetClassFunction
     * @return annotation
     */
    public static <A extends Annotation> A evalAnnotation(Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx, Function<A, Class[]> getGroupsFunction,
            Function<A, Class> getTargetClassFunction) {
        if (processingCtx instanceof ClassContext) {
            return ProcessingContextEvalAnnImpl.evalAnnotation(((ClassContext) processingCtx).getProcessedClass(), annotationClass, context, processingCtx, getGroupsFunction, getTargetClassFunction);
        }
        else if (processingCtx instanceof FieldContext) {
            return ProcessingContextEvalAnnImpl.evalAnnotation(((FieldContext) processingCtx).getField(), annotationClass, context, processingCtx, getGroupsFunction, getTargetClassFunction);
        }
        else if (processingCtx instanceof ClassItemContext) {
            return ProcessingContextEvalAnnImpl.evalAnnotation(((ClassItemContext) processingCtx).getTargetItemClass(), annotationClass, context, processingCtx, getGroupsFunction,
                    getTargetClassFunction);
        }
        else {
            throw new IllegalArgumentException("Unsupported processingCtx : " + processingCtx);
        }
    }

    /**
     * Will evaluate annotation on element using groups and target class
     * 
     * @param element
     * @param annotationClass
     * @param context
     * @param processingCtx
     * @param getGroupsFunction
     * @param getTargetClassFunction
     * @return annotation
     */
    public static <A extends Annotation> A evalAnnotation(AnnotatedElement element, Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx,
            Function<A, Class[]> getGroupsFunction, Function<A, Class> getTargetClassFunction) {
        return ProcessingContextEvalAnnImpl.evalAnnotation(element, annotationClass, context, processingCtx, getGroupsFunction, getTargetClassFunction);
    }
}
