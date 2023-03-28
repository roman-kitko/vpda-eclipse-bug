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
import java.util.ArrayList;
import java.util.List;

import org.vpda.common.comps.annotations.Components;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.IgnoredIfNull;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractItemsClassProcessor;
import org.vpda.common.processor.impl.ProcessorHelper;

final class ComponentsProcessor extends AbstractItemsClassProcessor<Component> implements ItemsClassProcessor<Component> {

    @Override
    public Class<Component> getTargetItemClass() {
        return Component.class;
    }

    @Override
    public List<Component> process(ClassItemContext<Component> classItem, ProcessorResolver processorResolver, ObjectResolver context) {
        List<Component> components = new ArrayList<Component>();
        for (Field iField : ProcessorHelper.getDeclaredFieldsConsideringProcessingInfo(classItem, context)) {
            if (iField.getType().equals(AbstractComponent.class)) {
                FieldContext<AbstractComponent> fieldContext = FieldContext.createFieldContext(classItem, iField, AbstractComponent.class);
                if (iField.isAnnotationPresent(IgnoredIfNull.class) && fieldContext.getFieldValue() == null) {
                    continue;
                }
                AbstractComponent comp = ProcessorHelper.resolveAndBuildField(fieldContext, processorResolver, context);
                components.add(comp);
            }
            else if (iField.getType().equals(Component.class)) {
                FieldContext<Component> fieldContext = FieldContext.createFieldContext(classItem, iField, Component.class);
                if (iField.isAnnotationPresent(IgnoredIfNull.class) && fieldContext.getFieldValue() == null) {
                    continue;
                }
                Component comp = ProcessorHelper.resolveAndBuildField(fieldContext, processorResolver, context);
                components.add(comp);
            }
            else if (iField.getType().isAnnotationPresent(Components.class)) {
                List<Component> nestedComponents = process(ClassItemContext.createClassItemContext(classItem, iField.getType(), Component.class), processorResolver, context);
                components.addAll(nestedComponents);
            }
        }
        return components;
    }

}
