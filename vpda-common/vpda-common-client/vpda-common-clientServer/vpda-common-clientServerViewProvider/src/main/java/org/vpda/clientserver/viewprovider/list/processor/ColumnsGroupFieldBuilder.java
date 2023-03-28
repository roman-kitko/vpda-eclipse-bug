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
package org.vpda.clientserver.viewprovider.list.processor;

import java.lang.reflect.Field;

import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupInfo;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsPrefixLocalizerInfo;
import org.vpda.common.ioc.objectresolver.DynamicMacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractFieldBuilder;
import org.vpda.common.service.localization.LocKeyFieldBuilder;
import org.vpda.common.util.AnnotationConstants;

final class ColumnsGroupFieldBuilder extends AbstractFieldBuilder<ColumnsGroup> {

    @Override
    public ColumnsGroup build(FieldContext<ColumnsGroup> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        Field field = fieldContext.getField();
        ColumnsGroupInfo info = context.resolveObject(ColumnsGroupInfo.class);
        if (info == null) {
            info = field.getAnnotation(ColumnsGroupInfo.class);
        }
        if (info == null) {
            info = field.getType().getAnnotation(ColumnsGroupInfo.class);
        }
        ColumnsGroupInfo.Builder effectiveInfoBuilder = new ColumnsGroupInfo.Builder();
        effectiveInfoBuilder.setLocalId(field.getName());
        if (info != null && info == field.getAnnotation(ColumnsGroupInfo.class) && !AnnotationConstants.UNDEFINED_STRING.equals(info.localId())) {
            effectiveInfoBuilder.setLocalId(info.localId());
        }
        if (info != null) {
            effectiveInfoBuilder.setColumnsTitlePrefix(LocKeyFieldBuilder.createLocKey(info.columnsTitleKeyPrefix()));
            effectiveInfoBuilder.setTitleKey(LocKeyFieldBuilder.createLocKey(info.titleKey()));
            effectiveInfoBuilder.setColumnsPrefixLocalizerInfo(info.columnsTitlePrefixInfo());
        }
        else {
            // Builder will set defaults
        }
        ColumnsGroupInfo effectiveInfo = effectiveInfoBuilder.build();
        ColumnsPrefixLocalizerInfo prefixLocalizerInfo = field.getAnnotation(ColumnsPrefixLocalizerInfo.class);
        if (prefixLocalizerInfo == null) {
            prefixLocalizerInfo = effectiveInfo.columnsTitlePrefixInfo();
        }
        Column.ColumnLocalizer localizer = null;
        if (prefixLocalizerInfo != null) {
            if (!AnnotationConstants.UNDEFINED_STRING.equals(prefixLocalizerInfo.prefixKey().path())) {
                localizer = new Column.PrefixLocalizer(LocKeyFieldBuilder.createLocKey(prefixLocalizerInfo.prefixKey()), prefixLocalizerInfo.separator());
            }
            else {
                localizer = new Column.PrefixLocalizer(LocKeyFieldBuilder.createLocKey(effectiveInfo.titleKey()), prefixLocalizerInfo.separator());
            }
        }
        else {
            localizer = new Column.PrefixLocalizer(LocKeyFieldBuilder.createLocKey(effectiveInfo.titleKey()));
        }

        DynamicMacroObjectResolverImpl newContext = new DynamicMacroObjectResolverImpl(context);
        if (localizer != null) {
            newContext.addResolver(new SingleObjectResolver<Column.ColumnLocalizer>(Column.ColumnLocalizer.class, localizer));
        }
        newContext.addResolver(new SingleObjectResolver<ColumnsGroupInfo>(ColumnsGroupInfo.class, effectiveInfo));

        ClassContext<ColumnsGroup> groupClassContext = ClassContext.createClassContext(fieldContext, field.getType(), ColumnsGroup.class);
        ClassProcessor<ColumnsGroup> groupProcessor = resolver.resolveTargetProcessor(groupClassContext, context);
        ColumnsGroup group = groupProcessor.process(groupClassContext, resolver, newContext);
        return group;
    }

}
