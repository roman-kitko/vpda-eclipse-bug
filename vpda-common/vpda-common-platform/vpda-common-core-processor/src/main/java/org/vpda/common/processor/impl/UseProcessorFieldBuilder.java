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

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.MapObjectResolver;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.FieldBuildingInfo;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;

/**
 * Field builder that will try to resolve {@link ClassProcessor} for field type
 * and process that type. All annotations found on field are put on context when
 * processing field type. Usually we annotate field with
 * {@link FieldBuildingInfo} and use this builder class in
 * {@link FieldBuildingInfo#builderClass()} property.
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class UseProcessorFieldBuilder<T> extends AbstractFieldBuilder<T> implements FieldBuilder<T> {

    @Override
    public T build(FieldContext<T> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        @SuppressWarnings("unchecked")
        ClassContext<T> classContext = ClassContext.<T>createClassContext(fieldContext, fieldContext.getTargetClass(), (Class<T>) fieldContext.getField().getType());
        ClassProcessor<T> processor = resolver.resolveTargetProcessor(classContext, context);
        if (processor == null) {
            return null;
        }
        if (fieldContext.getField().getAnnotations().length > 0) {
            Map<Class<? extends Annotation>, Annotation> anns = new HashMap<Class<? extends Annotation>, Annotation>();
            for (Annotation an : fieldContext.getField().getAnnotations()) {
                anns.put(an.annotationType(), an);
            }
            context = MacroObjectResolverImpl.createPair(context, new MapObjectResolver(anns));
        }
        processor.process(classContext, resolver, context);
        return processor.process(classContext, resolver, context);
    }

    @Override
    public boolean canBuild(FieldContext<?> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        ClassContext<?> classContext = ClassContext.createRootClassContext(fieldContext.getField().getType(), fieldContext.getTargetClass());
        ClassProcessor<?> processor = resolver.resolveTargetProcessor(classContext, context);
        if (processor == null) {
            return false;
        }
        return processor.canProcess(classContext, resolver, context);
    }

}
