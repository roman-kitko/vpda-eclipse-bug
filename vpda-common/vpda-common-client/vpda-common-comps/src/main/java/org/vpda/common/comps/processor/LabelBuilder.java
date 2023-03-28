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

import org.vpda.common.comps.annotations.LabelInfo;
import org.vpda.common.comps.loc.IconLocValue;
import org.vpda.common.comps.ui.LabelAC;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.util.AnnotationConstants;

final class LabelBuilder extends AbstractComponentFieldBuilder<LabelAC> implements org.vpda.common.processor.FieldBuilder<LabelAC> {

    @Override
    public LabelAC build(FieldContext<LabelAC> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        LabelAC label = super.build(fieldContext, resolver, context);
        LabelInfo info = fieldContext.getField().getAnnotation(LabelInfo.class);
        if (info != null) {
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.iconValue().path())) {
                label.setIconValue(new IconLocValue(info.iconValue().path(), null));
            }
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.label()) && label.getLabel() == null) {
                label.setLabel(info.label());
            }
        }
        if (label.getLabel() == null) {
            label.setLabel(label.getLocalId());
        }
        return label;
    }

    @Override
    public Class<? extends LabelAC> getTargetClass() {
        return LabelAC.class;
    }

}
