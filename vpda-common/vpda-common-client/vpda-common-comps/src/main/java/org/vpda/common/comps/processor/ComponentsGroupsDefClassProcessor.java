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
package org.vpda.common.comps.processor;

import java.lang.reflect.Field;

import org.vpda.common.comps.annotations.ComponentsGroupInfo;
import org.vpda.common.comps.annotations.ComponentsGroupsDefInfo;
import org.vpda.common.comps.ui.def.ComponentsGroup;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.ProcessingFieldFilter;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.Ignored;
import org.vpda.common.processor.annotation.IgnoredAsInnerClass;
import org.vpda.common.processor.annotation.IgnoredIfNull;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractClassProcessor;
import org.vpda.common.processor.impl.ProcessorHelper;

final class ComponentsGroupsDefClassProcessor extends AbstractClassProcessor<ComponentsGroupsDef> implements ClassProcessor<ComponentsGroupsDef> {

    @Override
    public Class<ComponentsGroupsDef> getTargetClass() {
        return ComponentsGroupsDef.class;
    }

    @Override
    public ComponentsGroupsDef process(ClassContext<ComponentsGroupsDef> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        ComponentsGroupsDefInfo info = context.resolveObject(ComponentsGroupsDefInfo.class);
        Class<?> clazz = classContext.getProcessedClass();
        if (info == null) {
            info = clazz.getAnnotation(ComponentsGroupsDefInfo.class);
        }
        ComponentsGroupsDef.Builder builder = new ComponentsGroupsDef.Builder();
        context = new MacroObjectResolverImpl(new SingleObjectResolver<ComponentsGroupsDef>(builder.build()), context);
        processGroups(classContext, processorResolver, context, builder);
        return builder.build();
    }

    private void processGroups(ClassContext<ComponentsGroupsDef> classContext, ProcessorResolver processorResolver, ObjectResolver context, ComponentsGroupsDef.Builder builder) {
        ProcessingFieldFilter processingFieldFilter = processorResolver.createFieldFilter(classContext, context);
        for (Field field : ProcessorHelper.getDeclaredFieldsConsideringProcessingInfo(classContext, context)) {
            if (!processingFieldFilter.accept(classContext, field, context)) {
                continue;
            }
            if (field.isAnnotationPresent(ComponentsGroupInfo.class) || field.getType().isAnnotationPresent(ComponentsGroupInfo.class)) {
                FieldContext<ComponentsGroup> fieldContext = FieldContext.createFieldContext(classContext, field, ComponentsGroup.class);
                if (field.isAnnotationPresent(IgnoredIfNull.class) && fieldContext.getFieldValue() == null) {
                    continue;
                }
                ComponentsGroup group = ProcessorHelper.resolveAndBuildField(fieldContext, processorResolver, context);
                builder.addGroup(group);
            }
        }
        for (Class<?> declClass : ProcessorHelper.getDeclaredClassesConsideringProcessingInfo(classContext, context)) {
            if (declClass.isAnnotationPresent(IgnoredAsInnerClass.class) || declClass.isAnnotationPresent(Ignored.class)) {
                continue;
            }
            if (declClass.isAnnotationPresent(ComponentsGroupInfo.class)) {
                ComponentsGroup group = ProcessorHelper.resolveAndProcessClass(ClassContext.createClassContext(classContext, declClass, ComponentsGroup.class), processorResolver, context);
                builder.addGroup(group);
            }
        }

    }

}
