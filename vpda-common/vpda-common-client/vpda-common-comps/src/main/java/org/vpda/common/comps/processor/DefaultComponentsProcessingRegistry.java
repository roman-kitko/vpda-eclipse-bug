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
package org.vpda.common.comps.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.vpda.common.comps.annotations.ButtonInfo;
import org.vpda.common.comps.annotations.ComboBoxInfo;
import org.vpda.common.comps.annotations.ComponentInfo;
import org.vpda.common.comps.annotations.Components;
import org.vpda.common.comps.annotations.ComponentsGroupInfo;
import org.vpda.common.comps.annotations.ComponentsGroupsDefInfo;
import org.vpda.common.comps.annotations.FieldInfo;
import org.vpda.common.comps.annotations.LabelInfo;
import org.vpda.common.comps.annotations.ListBoxInfo;
import org.vpda.common.comps.ui.AbstractButtonAC;
import org.vpda.common.comps.ui.AbstractFieldAC;
import org.vpda.common.comps.ui.ComboBoxAC;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.LabelAC;
import org.vpda.common.comps.ui.ListAC;
import org.vpda.common.comps.ui.def.ComponentsGroup;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.common.comps.ui.resolved.ResolvingUIBasedComponent;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ClassProcessor;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ItemsClassProcessor;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractProcessingRegistry;
import org.vpda.common.processor.impl.AnnotatedFieldsProcessor;

/**
 * Default ProcessingRegistry for Components
 * 
 * @author kitko
 *
 */
public final class DefaultComponentsProcessingRegistry extends AbstractProcessingRegistry implements ProcessingRegistry {

    @Override
    public <T> FieldBuilder<T> getBuilderByFieldContext(FieldContext<T> fieldContext, ObjectResolver context) {
        Field field = fieldContext.getField();
        if (Component.class.isAssignableFrom(field.getType()) && fieldContext.getFieldValue() instanceof Component) {
            Class<?> realType = fieldContext.getFieldValue().getClass();
            return getBuilderByFieldType(realType, fieldContext.getTargetClass(), field, context);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> FieldBuilder<T> getBuilderByFieldType(Class<?> type, Class<T> targetType, Field field, ObjectResolver context) {
        if (Component.class.isAssignableFrom(type)) {
            if (AbstractButtonAC.class.isAssignableFrom(type)) {
                return (FieldBuilder<T>) new ButtonBuilder();
            }
            if (LabelAC.class.isAssignableFrom(type)) {
                return (FieldBuilder<T>) new LabelBuilder();
            }
            if (AbstractFieldAC.class.isAssignableFrom(type)) {
                return (FieldBuilder<T>) new org.vpda.common.comps.processor.FieldBuilder();
            }
            if (ComboBoxAC.class.isAssignableFrom(type)) {
                return (FieldBuilder<T>) new org.vpda.common.comps.processor.ComboBoxBuilder();
            }
            if (ListAC.class.isAssignableFrom(type)) {
                return (FieldBuilder<T>) new org.vpda.common.comps.processor.ListBoxBuilder();
            }
            if (ResolvingUIBasedComponent.class.equals(type)) {
                return (FieldBuilder<T>) new ResolvingUIBasedComponentBuilder();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> FieldBuilder<T> getBuilderByFieldAnnotation(Class<? extends Annotation> annClass, Class<T> targetType, Field field, ObjectResolver context) {
        if (Component.class.isAssignableFrom(field.getType())) {
            if (ButtonInfo.class.equals(annClass)) {
                return (FieldBuilder<T>) new ButtonBuilder();
            }
            if (LabelInfo.class.equals(annClass)) {
                return (FieldBuilder<T>) new LabelBuilder();
            }
            if (FieldInfo.class.equals(annClass)) {
                return (FieldBuilder<T>) new org.vpda.common.comps.processor.FieldBuilder();
            }
            if (ComboBoxInfo.class.equals(annClass)) {
                return (FieldBuilder<T>) new org.vpda.common.comps.processor.ComboBoxBuilder();
            }
            if (ListBoxInfo.class.equals(annClass)) {
                return (FieldBuilder<T>) new org.vpda.common.comps.processor.ListBoxBuilder();
            }
        }
        if (ComponentsGroupInfo.class.equals(annClass) && ComponentsGroup.class.equals(targetType)) {
            return (FieldBuilder<T>) new ComponentsGroupFieldBuilder();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ClassProcessor<T> getProcessorByAnnotation(Class<? extends Annotation> annClass, Class<?> clazz, Class<T> targetClass, ObjectResolver context) {
        if (ComponentsGroupInfo.class.equals(annClass)) {
            return (ClassProcessor<T>) new ComponentsGroupClassProcessor();
        }
        if (ComponentsGroupsDefInfo.class.equals(annClass)) {
            return (ClassProcessor<T>) new ComponentsGroupsDefClassProcessor();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ClassProcessor<T> getProcessorByProcessedAndTargetClass(Class<?> processedClass, Class<T> targetClass, ObjectResolver context) {
        if (ComponentsGroup.class.equals(targetClass)) {
            return (ClassProcessor<T>) new ComponentsGroupClassProcessor();
        }
        if (ComponentsGroupsDef.class.equals(targetClass)) {
            return (ClassProcessor<T>) new ComponentsGroupsDefClassProcessor();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ItemsClassProcessor<T> getItemsProcessorByProcessedAndTargetItemClass(Class<?> processedClass, Class<T> targetItemClass, ObjectResolver context) {
        if (processedClass.isAnnotationPresent(Components.class)) {
            return (ItemsClassProcessor<T>) new ComponentsProcessor();
        }
        if (Component.class.equals(targetItemClass)) {
            return new AnnotatedFieldsProcessor<T>(targetItemClass, ComponentInfo.class);
        }
        return null;
    }

}
