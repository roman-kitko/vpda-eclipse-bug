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
package org.vpda.common.comps.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.HorizontalAlignment;
import org.vpda.common.comps.ui.VerticalAlignment;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/**
 * Info for abstract component
 * 
 * @author kitko
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentInfo {
    /** Local id of component */
    String localId() default AnnotationConstants.UNDEFINED_STRING;

    /** Default visibility of component */
    AnnotationConstants.Boolean visible() default AnnotationConstants.Boolean.UNDEFINED;

    /** Default enable state of component */
    AnnotationConstants.Boolean enabled() default AnnotationConstants.Boolean.UNDEFINED;

    /** Horizontal alignment */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.NO_DEFINED;

    /** Vertical alignment */
    VerticalAlignment verticalAlignment() default VerticalAlignment.NO_DEFINED;

    /** Base localization key */
    LocKeyInfo locKey();

    /** Real type that should be created */
    Class<? extends Component> type() default Component.class;
}
