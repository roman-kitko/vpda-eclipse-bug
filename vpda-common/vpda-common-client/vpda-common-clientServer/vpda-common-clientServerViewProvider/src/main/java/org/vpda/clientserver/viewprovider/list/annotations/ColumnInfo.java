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
package org.vpda.clientserver.viewprovider.list.annotations;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/** Information using which we can build Column */
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnInfo {
    /** Local id in group, default to field name */
    public String localId() default AnnotationConstants.UNDEFINED_STRING;

    /** Localization title key */
    public LocKeyInfo titleKey() default @LocKeyInfo(path = AnnotationConstants.UNDEFINED_STRING);

    /** Java type of column */
    public Class<?> type() default AnnotationConstants.UNDEFINED_TYPE.class;

    /** Visibility flag */
    public Visibility visibility() default Visibility.NOT_DEFINED;

    /** State of column visibility */
    public enum Visibility {
        /** Normally visible */
        VISIBLE,
        /** Invisible */
        INVISIBLE,
        /** Not defined, will default to other settings, usually visible */
        NOT_DEFINED;
    };

    /** Simple mark column to be invisible by default */
    @Target({ FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Invisible {
    }

}
