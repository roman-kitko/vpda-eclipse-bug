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
import java.util.List;

import org.vpda.common.comps.annotations.Components;
import org.vpda.common.comps.annotations.ComponentsGroupInfo;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.def.ComponentsGroup;
import org.vpda.common.comps.ui.def.ComponentsGroup.Builder;
import org.vpda.common.context.localization.LocKey;
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
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractClassProcessor;
import org.vpda.common.processor.impl.ProcessorHelper;
import org.vpda.common.service.localization.LocKeyFieldBuilder;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.util.AnnotationConstants;

final class ComponentsGroupClassProcessor extends AbstractClassProcessor<ComponentsGroup> implements ClassProcessor<ComponentsGroup> {

    @Override
    public ComponentsGroup process(ClassContext<ComponentsGroup> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        ComponentsGroupInfo info = context.resolveObject(ComponentsGroupInfo.class);
        ComponentsGroup parent = context.resolveObject(ComponentsGroup.class);
        Class<?> clazz = classContext.getProcessedClass();
        if (info == null) {
            info = clazz.getAnnotation(ComponentsGroupInfo.class);
        }
        ComponentsGroup.Builder builder = new ComponentsGroup.Builder();
        if (parent != null) {
            builder.setParentId(parent.getId());
        }
        LocKey locKey = null;
        if (info != null) {
            builder.setLocalId(!AnnotationConstants.UNDEFINED_STRING.equals(info.localId()) ? info.localId() : clazz.getSimpleName());
            locKey = LocKeyFieldBuilder.createLocKey(info.titleKey());
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.componentsTitleKeyPrefix().path())) {
                builder.setComponentsTitlePrefix(LocKeyFieldBuilder.createLocKey(info.componentsTitleKeyPrefix()));
            }
        }
        else {
            builder.setLocalId(clazz.getSimpleName());
            locKey = new LocKey(clazz.getName().replace('.', '/'));
        }
        builder.setTitle(LocPair.createStringLocPair(locKey, null));
        ComponentsGroup.ComponentsGroupLocalizer localizer = context.resolveObject(ComponentsGroup.ComponentsGroupLocalizer.class);
        builder.setGroupLocalizer(localizer);
        context = new MacroObjectResolverImpl(new SingleObjectResolver<ComponentsGroup>(builder.build()), context);
        processComponents(classContext, processorResolver, context, builder);
        processGroups(classContext, processorResolver, context, builder);
        return builder.build();
    }

    private void processComponents(ClassContext<ComponentsGroup> classContext, ProcessorResolver processorResolver, ObjectResolver context, Builder builder) {
        ProcessingFieldFilter processingFieldFilter = processorResolver.createFieldFilter(classContext, context);
        for (Field field : ProcessorHelper.getDeclaredFieldsConsideringProcessingInfo(classContext, context)) {
            if (!processingFieldFilter.accept(classContext, field, context)) {
                continue;
            }
            if (Component.class.isAssignableFrom(field.getType())) {
                FieldContext<Component> fieldContext = FieldContext.createFieldContext(classContext, field, Component.class);
                if (field.isAnnotationPresent(IgnoredIfNull.class) && fieldContext.getFieldValue() == null) {
                    continue;
                }
                Component comp = ProcessorHelper.resolveAndBuildField(fieldContext, processorResolver, context);
                if (comp != null) {
                    builder.addComponent(comp);
                }
            }
            else if (field.getType().isAnnotationPresent(Components.class)) {
                List<Component> components = ProcessorHelper.resolveAndProcessItems(ClassItemContext.createClassItemContext(classContext, field.getType(), Component.class), processorResolver,
                        context);
                if (components != null) {
                    builder.addComponents(components);
                }
            }
        }
        for (Class<?> declClass : ProcessorHelper.getDeclaredClassesConsideringProcessingInfo(classContext, context)) {
            if (declClass.isAnnotationPresent(IgnoredAsInnerClass.class) || declClass.isAnnotationPresent(Ignored.class)) {
                continue;
            }
            if (declClass.isAnnotationPresent(Components.class)) {
                processComponents(ClassContext.createRootClassContext(declClass, ComponentsGroup.class), processorResolver, context, builder);
            }
        }

    }

    private void processGroups(ClassContext<ComponentsGroup> classContext, ProcessorResolver processorResolver, ObjectResolver context, Builder builder) {
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
                ComponentsGroup innerGroup = ProcessorHelper.resolveAndBuildField(fieldContext, processorResolver, context);
                builder.addGroup(innerGroup);
            }
        }
        for (Class<?> declClass : ProcessorHelper.getDeclaredClassesConsideringProcessingInfo(classContext, context)) {
            if (declClass.isAnnotationPresent(IgnoredAsInnerClass.class) || declClass.isAnnotationPresent(Ignored.class)) {
                continue;
            }
            if (declClass.isAnnotationPresent(ComponentsGroupInfo.class)) {
                ObjectResolver newContext = new MacroObjectResolverImpl(SingleObjectResolver.create(ComponentsGroupInfo.class, declClass.getAnnotation(ComponentsGroupInfo.class)), context);
                ComponentsGroup innerGroup = ProcessorHelper.resolveAndProcessClass(ClassContext.createClassContext(classContext, declClass, ComponentsGroup.class), processorResolver, newContext);
                builder.addGroup(innerGroup);
            }
        }

    }

}
