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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.annotation.GroupFiltering;
import org.vpda.common.processor.annotation.TargetClassFiltering;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.ctx.ProcessingContext;
import org.vpda.common.util.AnnotationConstants;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.AnnotationUtil;

final class ProcessingContextEvalAnnImpl {

    private ProcessingContextEvalAnnImpl() {
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
    static <A extends Annotation> A evalAnnotation(AnnotatedElement element, Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx) {
        A a = element.getAnnotation(annotationClass);
        if (a != null) {
            return evalAnnotationByReflection(a, context, processingCtx);
        }
        if (!AnnotationUtil.isAnnotationRepeatable(annotationClass)) {
            return null;
        }
        A[] annotationsByType = element.getAnnotationsByType(annotationClass);
        for (A i : annotationsByType) {
            i = evalAnnotationByReflection(i, context, processingCtx);
            if (i != null) {
                return i;
            }
        }
        return null;
    }

    static <A extends Annotation> List<A> evalAnnotations(AnnotatedElement element, Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx) {
        A a = element.getAnnotation(annotationClass);
        if (a != null) {
            a = evalAnnotationByReflection(a, context, processingCtx);
            return a != null ? Collections.singletonList(a) : Collections.emptyList();
        }
        if (!AnnotationUtil.isAnnotationRepeatable(annotationClass)) {
            return Collections.emptyList();
        }
        A[] annotationsByType = element.getAnnotationsByType(annotationClass);
        if (annotationsByType.length == 0) {
            return Collections.emptyList();
        }
        List<A> list = new ArrayList<>(annotationsByType.length);
        for (A i : annotationsByType) {
            i = evalAnnotationByReflection(i, context, processingCtx);
            if (i != null) {
                list.add(i);
            }
        }
        return list;
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
    static <A extends Annotation> A evalAnnotation(AnnotatedElement element, Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx,
            Function<A, Class[]> getGroupsFunction, Function<A, Class> getTargetClassFunction) {
        Function<ProcessingContext, Class> getProcessingClassFunction = getProcessingTargetClassFunctionForCtx(processingCtx);
        A a = element.getAnnotation(annotationClass);
        if (a != null) {
            return evalProcessingGroupsAndTargetClass(context, a, processingCtx, getGroupsFunction, getTargetClassFunction, getProcessingClassFunction);
        }
        if (!AnnotationUtil.isAnnotationRepeatable(annotationClass)) {
            return null;
        }
        A[] annotationsByType = element.getAnnotationsByType(annotationClass);
        for (A i : annotationsByType) {
            i = evalProcessingGroupsAndTargetClass(context, i, processingCtx, getGroupsFunction, getTargetClassFunction, getProcessingClassFunction);
            if (i != null) {
                return i;
            }
        }
        return null;
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
    static <A extends Annotation> List<A> evalAnnotations(AnnotatedElement element, Class<A> annotationClass, ObjectResolver context, ProcessingContext<?> processingCtx,
            Function<A, Class[]> getGroupsFunction, Function<A, Class> getTargetClassFunction) {
        Function<ProcessingContext, Class> getProcessingClassFunction = getProcessingTargetClassFunctionForCtx(processingCtx);
        A a = element.getAnnotation(annotationClass);
        if (a != null) {
            a = evalProcessingGroupsAndTargetClass(context, a, processingCtx, getGroupsFunction, getTargetClassFunction, getProcessingClassFunction);
            return a != null ? Collections.singletonList(a) : Collections.emptyList();
        }
        if (!AnnotationUtil.isAnnotationRepeatable(annotationClass)) {
            return Collections.emptyList();
        }
        A[] annotationsByType = element.getAnnotationsByType(annotationClass);
        if (annotationsByType.length == 0) {
            return Collections.emptyList();
        }
        List<A> list = new ArrayList<>(annotationsByType.length);
        for (A i : annotationsByType) {
            i = evalProcessingGroupsAndTargetClass(context, i, processingCtx, getGroupsFunction, getTargetClassFunction, getProcessingClassFunction);
            if (i != null) {
                list.add(i);
            }
        }
        return list;
    }

    private static Function<ProcessingContext, Class> getProcessingTargetClassFunctionForCtx(ProcessingContext<?> processingCtx) {
        Function<ProcessingContext, Class> getProcessingClassFunction = null;
        if (processingCtx instanceof ClassContext) {
            getProcessingClassFunction = p -> ((ClassContext) processingCtx).getTargetClass();
        }
        else if (processingCtx instanceof FieldContext) {
            getProcessingClassFunction = p -> ((FieldContext) processingCtx).getTargetClass();
        }
        else if (processingCtx instanceof ClassItemContext) {
            getProcessingClassFunction = p -> ((ClassItemContext) processingCtx).getTargetItemClass();
        }
        else {
            throw new IllegalArgumentException("Unsupported processingCtx : " + processingCtx);
        }
        return getProcessingClassFunction;
    }

    private static <A extends Annotation> A evalAnnotationByReflection(final A a, ObjectResolver context, ProcessingContext<?> processingCtx) {
        Function<A, Class[]> getGroupsFunction = null;
        if (a.annotationType().isAnnotationPresent(GroupFiltering.class)) {
            final Method groupsFilteringMethod = ProcessingContextEvalAnnCache.getGroupsFilteringMethod(a.annotationType());
            if (groupsFilteringMethod != null) {
                getGroupsFunction = new Function<A, Class[]>() {
                    @Override
                    public Class[] apply(A t) {
                        try {
                            return (Class[]) groupsFilteringMethod.invoke(a);
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            throw new VPDARuntimeException("Cannot call groups filtering annotation method", e);
                        }
                    }
                };
            }
        }
        Function<A, Class> getTargetClassFunction = null;
        if (a.annotationType().isAnnotationPresent(TargetClassFiltering.class)) {
            final Method targetClassMethod = ProcessingContextEvalAnnCache.getTargetClassMethod(a.annotationType());
            if (targetClassMethod != null) {
                getTargetClassFunction = new Function<A, Class>() {
                    @Override
                    public Class apply(A t) {
                        try {
                            return (Class) targetClassMethod.invoke(a);
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            throw new VPDARuntimeException("Cannot call target class annotation method", e);
                        }
                    }
                };
            }
        }
        if (getGroupsFunction == null && getTargetClassFunction == null) {
            return a;
        }
        Function<ProcessingContext, Class> getProcessingClassFunction = getProcessingTargetClassFunctionForCtx(processingCtx);
        return evalProcessingGroupsAndTargetClass(context, a, processingCtx, getGroupsFunction, getTargetClassFunction, getProcessingClassFunction);
    }

    private static <A extends Annotation> A evalProcessingGroupsAndTargetClass(ObjectResolver context, final A info, ProcessingContext<?> processingCtx, Function<A, Class[]> getGroupsFunction,
            Function<A, Class> getTargetClassFunction, Function<ProcessingContext, Class> getProcessingClassFunction) {
        if (getGroupsFunction != null && evalProcessingGroups(context, info, processingCtx, getGroupsFunction) == null) {
            return null;
        }
        if (getTargetClassFunction != null && getProcessingClassFunction != null
                && evalProcessingTargetClass(context, info, getTargetClassFunction, getProcessingClassFunction, processingCtx) == null) {
            return null;
        }
        return info;
    }

    private static <A extends Annotation> A evalProcessingGroups(ObjectResolver context, final A info, ProcessingContext<?> processingCtx, Function<A, Class[]> getGroupsFunction) {
        Class[] processingGroups = processingCtx.getGroups();
        Class[] declaredGroups = getGroupsFunction.apply(info);
        if (processingGroups.length == 0 && declaredGroups.length == 0) {
            return info;
        }
        if (processingGroups.length > 0) {
            for (Class<?> c : processingGroups) {
                if (declaredGroups.length > 0) {
                    for (Class<?> g : declaredGroups) {
                        if (g.isAssignableFrom(c)) {
                            return info;
                        }
                    }
                }
                // when no groups declared and we want default group, return info
                else {
                    if (org.vpda.common.processor.ctx.DefaultGroup.class.isAssignableFrom(c)) {
                        return info;
                    }
                }
            }
            return null;
        }
        else { // means we have some groups declared, check is Default is declared
            for (Class<?> g : declaredGroups) {
                if (org.vpda.common.processor.ctx.DefaultGroup.class.isAssignableFrom(g)) {
                    return info;
                }
            }
            return null;
        }
    }

    private static <A extends Annotation> A evalProcessingTargetClass(ObjectResolver context, final A info, Function<A, Class> getTargetClassFunction,
            Function<ProcessingContext, Class> getProcessingClassFunction, ProcessingContext<?> processingCtx) {
        Class<?> declaredTargetClass = getTargetClassFunction.apply(info);
        if (!AnnotationConstants.UNDEFINED_TYPE.class.equals(declaredTargetClass)) {
            Class<?> processedClass = getProcessingClassFunction.apply(processingCtx);
            if (processedClass != null) {
                if (!processedClass.isAssignableFrom(declaredTargetClass)) {
                    return null;
                }
            }
        }
        return info;
    }

}
