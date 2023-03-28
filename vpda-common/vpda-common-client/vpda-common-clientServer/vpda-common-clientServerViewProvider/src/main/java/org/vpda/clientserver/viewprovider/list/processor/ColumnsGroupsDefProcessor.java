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

import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupInfo;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupsDefInfo;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.Ignored;
import org.vpda.common.processor.annotation.IgnoredAsInnerClass;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractClassProcessor;
import org.vpda.common.processor.impl.ProcessorHelper;

/**
 * @author kitko
 *
 */
final class ColumnsGroupsDefProcessor extends AbstractClassProcessor<ColumnsGroupsDef> implements ClassProcessor<ColumnsGroupsDef> {

    @Override
    public Class<ColumnsGroupsDef> getTargetClass() {
        return ColumnsGroupsDef.class;
    }

    @Override
    public ColumnsGroupsDef process(ClassContext<ColumnsGroupsDef> classContext, ProcessorResolver processorResolver, ObjectResolver context) {
        ColumnsGroupsDefInfo info = context.resolveObject(ColumnsGroupsDefInfo.class);
        Class<?> clazz = classContext.getProcessedClass();
        if (info == null) {
            info = clazz.getAnnotation(ColumnsGroupsDefInfo.class);
        }
        ColumnsGroupsDef.Builder builder = new ColumnsGroupsDef.Builder();
        context = new MacroObjectResolverImpl(new SingleObjectResolver<ColumnsGroupsDef>(builder.build()), context);
        processGroups(classContext, processorResolver, context, builder);
        return builder.build();
    }

    private void processGroups(ClassContext<ColumnsGroupsDef> classContext, ProcessorResolver processorResolver, ObjectResolver context, ColumnsGroupsDef.Builder builder) {
        for (Field field : ProcessorHelper.getDeclaredFieldsConsideringProcessingInfo(classContext, context)) {
            if (field.isAnnotationPresent(ColumnsGroupInfo.class) || field.getType().isAnnotationPresent(ColumnsGroupInfo.class)) {
                ColumnsGroup group = ProcessorHelper.resolveAndBuildField(FieldContext.createFieldContext(classContext, field, ColumnsGroup.class), processorResolver, context);
                builder.addColumnsGroup(group);
            }
        }
        for (Class<?> declClass : ProcessorHelper.getDeclaredClassesConsideringProcessingInfo(classContext, context)) {
            if (declClass.isAnnotationPresent(IgnoredAsInnerClass.class) || declClass.isAnnotationPresent(Ignored.class)) {
                continue;
            }
            if (declClass.isAnnotationPresent(ColumnsGroupInfo.class)) {
                ColumnsGroup group = ProcessorHelper.resolveAndProcessClass(ClassContext.createClassContext(classContext, declClass, ColumnsGroup.class), processorResolver, context);
                builder.addColumnsGroup(group);
            }
        }

    }

}
