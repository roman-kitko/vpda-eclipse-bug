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
import java.lang.reflect.Modifier;

import org.vpda.common.comps.annotations.ComponentInfo;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ComponentLocalizer;
import org.vpda.common.comps.ui.HorizontalAlignment;
import org.vpda.common.comps.ui.VerticalAlignment;
import org.vpda.common.comps.ui.def.ComponentsGroup;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessingException;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractFieldBuilder;
import org.vpda.common.service.localization.LocKeyFieldBuilder;
import org.vpda.common.util.AnnotationConstants;

/**
 * @author kitko
 * 
 * @param <T>
 */
abstract class AbstractComponentFieldBuilder<T extends Component> extends AbstractFieldBuilder<T> {

    @SuppressWarnings("unchecked")
    @Override
    public T build(FieldContext<T> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        ComponentsGroup group = context.resolveObject(ComponentsGroup.class);
        Field field = fieldContext.getField();
        T comp = fieldContext.getTargetFieldValue();
        if (comp != null && Modifier.isStatic(field.getModifiers())) {
            throw new IllegalStateException("Cannot use component value based on static field");
        }
        ComponentInfo info = fieldContext.getField().getAnnotation(ComponentInfo.class);
        if (comp == null) {
            // Ok, we have specified something, so try to instantiate it
            if (info != null && !Component.class.equals(info.type()) && !Modifier.isAbstract(info.type().getModifiers()) && fieldContext.getTargetClass().isAssignableFrom(info.type())) {
                try {
                    comp = (T) info.type().newInstance();
                }
                catch (Exception e) {
                    throw new ProcessingException("Cannot create component instance for fieldContext " + fieldContext, e);
                }
            }
            // Lets try to create a field based on its type
            if (!Component.class.equals(field.getType()) && !Modifier.isAbstract(field.getType().getModifiers()) && fieldContext.getTargetClass().isAssignableFrom(field.getType())) {
                try {
                    comp = (T) field.getType().newInstance();
                }
                catch (Exception e) {
                    throw new ProcessingException("Cannot create component instance for fieldContext " + fieldContext, e);
                }
            }

        }
        if (comp == null) {
            throw new IllegalArgumentException("No component instance");
        }
        if (info != null) {
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.localId())) {
                comp.setLocalId(info.localId());
            }
            comp.setEnabled(info.enabled().isConcrete() ? info.enabled().value() : comp.isEnabled());
            comp.setVisible(info.visible().isConcrete() ? info.visible().value() : comp.isVisible());
            if (!HorizontalAlignment.NO_DEFINED.equals(info.horizontalAlignment())) {
                comp.setHorizontalAlignment(info.horizontalAlignment());
            }
            if (!VerticalAlignment.NO_DEFINED.equals(info.verticalAlignment())) {
                comp.setVerticalAlignment(info.verticalAlignment());
            }
            comp.setLocKey(LocKeyFieldBuilder.createLocKey(info.locKey()));

        }
        if (comp.getLocalId() == null) {
            comp.setLocalId(field.getName());
        }
        if (comp.getGroupId() == null) {
            comp.setGroupId(group != null ? group.getId() : field.getDeclaringClass().getSimpleName());
        }
        if (group != null && group.getComponentsTitlePrefix() != null) {
            if (comp.getLocKey() != null) {
                comp.setLocKey(group.getComponentsTitlePrefix().createChildKey(comp.getLocKey().getPath()));
            }
            else {
                comp.setLocKey(group.getComponentsTitlePrefix().createChildKey(comp.getLocalId()));
            }
        }
        ComponentLocalizer localizer = context.resolveObject(ComponentLocalizer.class);
        comp.setLocalizer(localizer);

        return comp;
    }

}
