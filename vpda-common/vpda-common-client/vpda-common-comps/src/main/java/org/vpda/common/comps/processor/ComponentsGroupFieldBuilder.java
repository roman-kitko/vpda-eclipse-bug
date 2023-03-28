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
import org.vpda.common.comps.annotations.ComponentsPrefixLocalizerInfo;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.ComponentLocalizer;
import org.vpda.common.comps.ui.def.ComponentsGroup;
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

final class ComponentsGroupFieldBuilder extends AbstractFieldBuilder<ComponentsGroup> {

    @Override
    public ComponentsGroup build(FieldContext<ComponentsGroup> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        Field field = fieldContext.getField();
        ComponentsGroupInfo info = context.resolveObject(ComponentsGroupInfo.class);
        if (info == null) {
            info = field.getAnnotation(ComponentsGroupInfo.class);
        }
        if (info == null) {
            info = field.getType().getAnnotation(ComponentsGroupInfo.class);
        }
        ComponentsGroupInfo.Builder effectiveInfoBuilder = new ComponentsGroupInfo.Builder();
        effectiveInfoBuilder.setLocalId(field.getName());
        if (info != null && info == field.getAnnotation(ComponentsGroupInfo.class) && !AnnotationConstants.UNDEFINED_STRING.equals(info.localId())) {
            effectiveInfoBuilder.setLocalId(info.localId());
        }
        if (info != null) {
            effectiveInfoBuilder.setComponentsTitleKeyPrefix(LocKeyFieldBuilder.createLocKey(info.componentsTitleKeyPrefix()));
            effectiveInfoBuilder.setTitleKey(LocKeyFieldBuilder.createLocKey(info.titleKey()));
        }
        else {
            // Builder will set defaults
        }
        ComponentsGroupInfo effectiveInfo = effectiveInfoBuilder.build();

        ComponentsPrefixLocalizerInfo prefixLocalizerInfo = field.getAnnotation(ComponentsPrefixLocalizerInfo.class);
        if (prefixLocalizerInfo == null) {
            prefixLocalizerInfo = effectiveInfo.componentsTitlePrefixInfo();
        }
        if (prefixLocalizerInfo == null) {
            prefixLocalizerInfo = field.getType().getAnnotation(ComponentsPrefixLocalizerInfo.class);
        }
        ComponentLocalizer localizer = null;
        if (prefixLocalizerInfo != null) {
            if (!AnnotationConstants.UNDEFINED_STRING.equals(prefixLocalizerInfo.prefixKey().path())) {
                localizer = new AbstractComponent.PrefixLocalizer(LocKeyFieldBuilder.createLocKey(prefixLocalizerInfo.prefixKey()), prefixLocalizerInfo.separator());
            }
            else {
                localizer = new AbstractComponent.PrefixLocalizer(LocKeyFieldBuilder.createLocKey(effectiveInfo.titleKey()), prefixLocalizerInfo.separator());
            }
        }
        else {
        }

        DynamicMacroObjectResolverImpl newContext = new DynamicMacroObjectResolverImpl(context);
        if (localizer != null) {
            newContext.addResolver(new SingleObjectResolver<ComponentLocalizer>(ComponentLocalizer.class, localizer));
        }
        newContext.addResolver(new SingleObjectResolver<ComponentsGroupInfo>(ComponentsGroupInfo.class, effectiveInfo));
        ClassContext<ComponentsGroup> groupClassContext = ClassContext.createClassInstanceContext(fieldContext, field.getType(), ComponentsGroup.class, fieldContext.getFieldValue());
        ClassProcessor<ComponentsGroup> groupProcessor = resolver.resolveTargetProcessor(groupClassContext, context);
        ComponentsGroup group = groupProcessor.process(groupClassContext, resolver, newContext);
        return group;
    }

}
