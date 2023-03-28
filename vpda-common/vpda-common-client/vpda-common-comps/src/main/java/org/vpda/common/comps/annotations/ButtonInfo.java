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

import org.vpda.common.util.AnnotationConstants;

/**
 * Properties of button
 * 
 * @author kitko
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ButtonInfo {
    /** Sets initial selected value */
    AnnotationConstants.Boolean selected() default AnnotationConstants.Boolean.UNDEFINED;

    /** Mnemonic of button */
    String mnemonic() default AnnotationConstants.UNDEFINED_STRING;

    /** Data for default icon */
    IconData iconValue() default @IconData(path = AnnotationConstants.UNDEFINED_STRING);

    /** Data for disabled icon */
    IconData disabledIconValue() default @IconData(path = AnnotationConstants.UNDEFINED_STRING);

    /** Default label, when not localized yet */
    String label() default AnnotationConstants.UNDEFINED_STRING;

}
