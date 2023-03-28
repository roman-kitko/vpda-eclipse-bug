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
package org.vpda.common.service.localization;

import org.vpda.common.context.localization.LocKey;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.processor.FieldBuilder;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.AbstractFieldBuilder;
import org.vpda.common.util.AnnotationConstants;

/**
 * @author kitko
 *
 */
public final class LocKeyFieldBuilder extends AbstractFieldBuilder<LocKey> implements FieldBuilder<LocKey> {

    @Override
    public LocKey build(FieldContext<LocKey> fieldContext, ProcessorResolver resolver, ObjectResolver context) {
        LocKeyInfo annotation = fieldContext.getField().getAnnotation(LocKeyInfo.class);
        if (annotation == null) {
            throw new IllegalStateException("No LocKeyInfo annotation present");
        }
        return createLocKey(annotation);
    }

    /**
     * Creates {@link LocKey} from {@link LocKeyInfo}
     * 
     * @param annotation
     * @return new LocKey
     */
    public static LocKey createLocKey(LocKeyInfo annotation) {
        if (AnnotationConstants.UNDEFINED_STRING.equals(annotation.key())) {
            return new LocKey(annotation.path());
        }
        return new LocKey(annotation.path(), annotation.key());
    }

    @Override
    public Class<? extends LocKey> getTargetClass() {
        return LocKey.class;
    }

}
