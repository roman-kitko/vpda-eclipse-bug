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

import org.vpda.common.comps.annotations.ButtonInfo;
import org.vpda.common.comps.loc.IconLocValue;
import org.vpda.common.comps.ui.AbstractButtonAC;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.util.AnnotationConstants;

final class ButtonBuilder extends AbstractComponentFieldBuilder<AbstractButtonAC> implements org.vpda.common.processor.FieldBuilder<AbstractButtonAC> {

    @Override
    public AbstractButtonAC build(FieldContext<AbstractButtonAC> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        AbstractButtonAC button = super.build(fieldContext, resolver, context);
        ButtonInfo info = fieldContext.getField().getAnnotation(ButtonInfo.class);
        if (info != null) {
            button.setSelected(info.selected().isConcrete() ? info.selected().value() : button.isSelected());
            if (info.mnemonic().length() == 1) {
                button.setMnemonic(Character.valueOf(info.mnemonic().charAt(0)));
            }
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.iconValue().path())) {
                button.setIconValue(new IconLocValue(info.iconValue().path(), null));
            }
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.disabledIconValue().path())) {
                button.setDisabledIconValue(new IconLocValue(info.disabledIconValue().path(), null));
            }
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.label()) && button.getLabel() == null) {
                button.setLabel(info.label());
            }
        }
        if (button.getLabel() == null) {
            button.setLabel(button.getLocalId());
        }
        return button;
    }

    @Override
    public Class<? extends AbstractButtonAC> getTargetClass() {
        return AbstractButtonAC.class;
    }

}
