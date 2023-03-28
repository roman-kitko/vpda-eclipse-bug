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

import org.vpda.common.comps.annotations.ComboBoxInfo;
import org.vpda.common.comps.ui.ComboBoxAC;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;

final class ComboBoxBuilder extends AbstractComponentFieldBuilder<ComboBoxAC> implements org.vpda.common.processor.FieldBuilder<ComboBoxAC> {

    @Override
    public ComboBoxAC build(FieldContext<ComboBoxAC> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        ComboBoxAC combo = super.build(fieldContext, resolver, context);
        ComboBoxInfo info = fieldContext.getField().getAnnotation(ComboBoxInfo.class);
        if (info != null) {
        }
        return combo;
    }

    @Override
    public Class<? extends ComboBoxAC> getTargetClass() {
        return ComboBoxAC.class;
    }

}
