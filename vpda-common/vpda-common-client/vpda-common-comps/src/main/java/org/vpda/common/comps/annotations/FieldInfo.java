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
 * Info for text fields
 * 
 * @author kitko
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldInfo {
    /** Number of columns */
    int columns() default 0;

    /** Default flag whether field should be editable */
    AnnotationConstants.Boolean editable() default AnnotationConstants.Boolean.UNDEFINED;

    /** Number of rows */
    int rows() default 2;

    /** Format pattern for integer, decimal and date fields */
    String formatPattern() default AnnotationConstants.UNDEFINED_STRING;
}
