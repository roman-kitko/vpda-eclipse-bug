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

import org.vpda.common.comps.annotations.FieldInfo;
import org.vpda.common.comps.ui.AbstractFieldAC;
import org.vpda.common.comps.ui.AbstractFormatttedFieldAC;
import org.vpda.common.comps.ui.TextAreaAC;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.util.AnnotationConstants;

final class FieldBuilder extends AbstractComponentFieldBuilder<AbstractFieldAC> implements org.vpda.common.processor.FieldBuilder<AbstractFieldAC> {

    @Override
    public Class<? extends AbstractFieldAC> getTargetClass() {
        return AbstractFieldAC.class;
    }

    @Override
    public AbstractFieldAC build(FieldContext<AbstractFieldAC> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        AbstractFieldAC field = super.build(fieldContext, resolver, context);
        FieldInfo info = fieldContext.getField().getAnnotation(FieldInfo.class);
        if (info != null) {
            if (info.columns() != 0) {
                field.setColumns(info.columns());
            }
            if (info.rows() != 0) {
                if (field instanceof TextAreaAC) {
                    ((TextAreaAC) field).setRows(info.rows());
                }
            }
            if (!AnnotationConstants.Boolean.UNDEFINED.equals(info.editable())) {
                field.setEditable(info.editable().value());
            }
            if (!AnnotationConstants.UNDEFINED_STRING.equals(info.formatPattern())) {
                if (field instanceof AbstractFormatttedFieldAC) {
                    ((AbstractFormatttedFieldAC) field).setFormatPattern(info.formatPattern());
                }
            }
        }
        return field;
    }

}
