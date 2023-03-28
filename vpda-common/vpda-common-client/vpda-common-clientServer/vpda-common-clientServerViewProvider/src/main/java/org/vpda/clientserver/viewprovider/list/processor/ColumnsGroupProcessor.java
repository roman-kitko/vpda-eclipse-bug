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
package org.vpda.clientserver.viewprovider.list.processor;

import java.lang.reflect.Field;
import java.util.List;

import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup.Builder;
import org.vpda.clientserver.viewprovider.list.annotations.Columns;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupInfo;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.Ignored;
import org.vpda.common.processor.annotation.IgnoredAsInnerClass;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractClassProcessor;
import org.vpda.common.processor.impl.ProcessorHelper;
import org.vpda.common.service.localization.LocKeyFieldBuilder;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.util.AnnotationConstants;

/**
 * @author kitko
 *
 */
final class ColumnsGroupProcessor extends AbstractClassProcessor<ColumnsGroup> implements ClassProcessor<ColumnsGroup> {

    @Override
    public Class<ColumnsGroup> getTargetClass() {
        return ColumnsGroup.class;
    }

    @Override
    public ColumnsGroup process(ClassContext<ColumnsGroup> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        ColumnsGroupInfo info = context.resolveObject(ColumnsGroupInfo.class);
        ColumnsGroup outerGroup = context.resolveObject(ColumnsGroup.class);
        Class<?> clazz = classContext.getProcessedClass();
        if (info == null) {
            info = clazz.getAnnotation(ColumnsGroupInfo.class);
        }
        ColumnsGroup.Builder builder = new ColumnsGroup.Builder();
        LocKey locKey = null;
        if (info != null) {
            builder.setLocalId(!AnnotationConstants.UNDEFINED_STRING.equals(info.localId()) ? info.localId() : clazz.getSimpleName());
            locKey = LocKeyFieldBuilder.createLocKey(info.titleKey());
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.columnsTitleKeyPrefix().path())) {
                builder.setColumnsTitleKeyPrefix(LocKeyFieldBuilder.createLocKey(info.columnsTitleKeyPrefix()));
            }
        }
        else {
            builder.setLocalId(clazz.getSimpleName());
            locKey = new LocKey(clazz.getName().replace('.', '/'));
        }
        if (outerGroup != null) {
            builder.setParentId(outerGroup.getId());
        }
        builder.setTitle(LocPair.createStringLocPair(locKey, null));
        ColumnsGroup.GroupLocalizer localizer = context.resolveObject(ColumnsGroup.GroupLocalizer.class);
        builder.setLocalizer(localizer);
        context = new MacroObjectResolverImpl(new SingleObjectResolver<ColumnsGroup>(builder.build()), context);
        processColumns(classContext, processorResolver, context, builder);
        processGroups(classContext, processorResolver, context, builder);
        return builder.build();
    }

    private void processGroups(ClassContext<ColumnsGroup> classContext, ProcessorResolver processorResolver, ObjectResolver context, Builder builder) {
        for (Field field : ProcessorHelper.getDeclaredFieldsConsideringProcessingInfo(classContext, context)) {
            if (field.isAnnotationPresent(ColumnsGroupInfo.class) || field.getType().isAnnotationPresent(ColumnsGroupInfo.class)) {
                ColumnsGroup innerGroup = ProcessorHelper.resolveAndBuildField(FieldContext.createFieldContext(classContext, field, ColumnsGroup.class), processorResolver, context);
                builder.addGroup(innerGroup);
            }
        }
        for (Class<?> declClass : ProcessorHelper.getDeclaredClassesConsideringProcessingInfo(classContext, context)) {
            if (declClass.isAnnotationPresent(IgnoredAsInnerClass.class) || declClass.isAnnotationPresent(Ignored.class)) {
                continue;
            }
            if (declClass.isAnnotationPresent(ColumnsGroupInfo.class)) {
                ColumnsGroup innerGroup = ProcessorHelper.resolveAndProcessClass(ClassContext.createClassContext(classContext, declClass, ColumnsGroup.class), processorResolver, context);
                builder.addGroup(innerGroup);
            }
        }

    }

    private void processColumns(ClassContext<ColumnsGroup> classContext, ProcessorResolver processorResolver, ObjectResolver context, ColumnsGroup.Builder builder) {
        for (Field field : ProcessorHelper.getDeclaredFieldsConsideringProcessingInfo(classContext, context)) {
            if (field.getType().equals(Column.class)) {
                Column col = ProcessorHelper.resolveAndBuildField(FieldContext.createFieldContext(classContext, field, Column.class), processorResolver, context);
                if (col != null) {
                    builder.addColumns(col);
                }
            }
            else if (field.getType().isAnnotationPresent(Columns.class)) {
                List<Column> columns = ProcessorHelper.resolveAndProcessItems(ClassItemContext.createClassItemContext(classContext, field.getType(), Column.class), processorResolver, context);
                if (columns != null) {
                    builder.addColumns(columns);
                }
            }
        }
        for (Class<?> declClass : ProcessorHelper.getDeclaredClassesConsideringProcessingInfo(classContext, context)) {
            if (declClass.isAnnotationPresent(IgnoredAsInnerClass.class) || declClass.isAnnotationPresent(Ignored.class)) {
                continue;
            }
            if (declClass.isAnnotationPresent(Columns.class)) {
                processColumns(ClassContext.createRootClassContext(declClass, ColumnsGroup.class), processorResolver, context, builder);
            }
        }
    }

}
