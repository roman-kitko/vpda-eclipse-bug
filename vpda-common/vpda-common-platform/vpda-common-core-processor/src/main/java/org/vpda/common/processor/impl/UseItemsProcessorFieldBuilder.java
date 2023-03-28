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
import java.util.List;

import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingException;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.TargetItemProcessingInfo;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.util.AnnotationConstants;

/**
 * Builder that uses {@link ItemsClassProcessor} to process field type. We must
 * use {@link TargetItemProcessingInfo} or
 * {@link TargetItemProcessingInfo#targetItemClass()} on field or type to
 * resolve target type of processing
 * 
 * @author kitko
 * @param <T>
 */
public final class UseItemsProcessorFieldBuilder<T> extends AbstractFieldBuilder<List<T>> {
    @SuppressWarnings("unchecked")
    @Override
    public List<T> build(FieldContext<List<T>> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        Class<?> targetItemClass = resolveTargetItemClass(fieldContext.getField());
        ClassItemContext<?> classItemContext = ClassItemContext.createClassItemContext(fieldContext, fieldContext.getField().getType(), targetItemClass);
        ItemsClassProcessor<?> processor = resolver.resolveTargetItemsProcessor(classItemContext, context);
        if (processor == null) {
            return null;
        }
        return processor.process(classItemContext, resolver, context);
    }

    @Override
    public boolean canBuild(FieldContext<?> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        Class<?> targetItemClass = resolveTargetItemClass(fieldContext.getField());
        ClassItemContext<?> itemContext = ClassItemContext.createClassItemContext(fieldContext, fieldContext.getField().getType(), targetItemClass);
        ItemsClassProcessor<?> processor = resolver.resolveTargetItemsProcessor(itemContext, context);
        if (processor == null) {
            return false;
        }
        return processor.canProcess(itemContext, resolver, context);
    }

    private Class<?> resolveTargetItemClass(Field field) {
        Class<?> targetItemClass = field.getAnnotation(TargetItemProcessingInfo.TargetItemClass.class) != null ? field.getAnnotation(TargetItemProcessingInfo.TargetItemClass.class).value() : null;
        if (targetItemClass == null) {
            TargetItemProcessingInfo info = field.getAnnotation(TargetItemProcessingInfo.class);
            if (info != null && !AnnotationConstants.UNDEFINED_TYPE.class.equals(info.targetItemClass())) {
                targetItemClass = info.targetItemClass();
            }
        }
        if (targetItemClass == null) {
            targetItemClass = field.getType().getAnnotation(TargetItemProcessingInfo.TargetItemClass.class) != null
                    ? field.getType().getAnnotation(TargetItemProcessingInfo.TargetItemClass.class).value()
                    : null;
        }
        if (targetItemClass == null) {
            TargetItemProcessingInfo info = field.getType().getAnnotation(TargetItemProcessingInfo.class);
            if (info != null && !AnnotationConstants.UNDEFINED_TYPE.class.equals(info.targetItemClass())) {
                targetItemClass = info.targetItemClass();
            }
        }
        if (targetItemClass == null) {
            throw new ProcessingException("Cannot resolve targetItemClass for field " + field);
        }
        return targetItemClass;
    }

}
