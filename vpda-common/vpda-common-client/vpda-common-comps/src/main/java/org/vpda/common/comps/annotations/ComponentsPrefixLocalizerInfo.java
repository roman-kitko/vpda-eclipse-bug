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

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/**
 * Annotation that can be used to create PrefixLocalizer
 * 
 * @author kitko
 *
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentsPrefixLocalizerInfo {
    /**
     * Localization key that can be localized to create a prefix for component title
     */
    LocKeyInfo prefixKey() default @LocKeyInfo(path = AnnotationConstants.UNDEFINED_STRING);

    /** Separator used to separate localized prefix and column title */
    String separator() default " - ";

    /** Runtime representation of ColumnsPrefixLocalizerInfo */
    public static final class ComponentsPrefixLocalizerInfoRuntime implements ComponentsPrefixLocalizerInfo {
        private final LocKeyInfo prefixKey;
        private final String separator;

        private ComponentsPrefixLocalizerInfoRuntime(Builder builder) {
            this.separator = builder.getSeparator();
            this.prefixKey = builder.prefixKey != null ? new LocKeyInfo.Builder().setLocKey(builder.prefixKey).build()
                    : new LocKeyInfo.Builder().setLocKey(new LocKey(AnnotationConstants.UNDEFINED_STRING)).build();

        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ComponentsPrefixLocalizerInfo.class;
        }

        @Override
        public LocKeyInfo prefixKey() {
            return prefixKey;
        }

        @Override
        public String separator() {
            return separator;
        }

    }

    /** Builder for ColumnsPrefixLocalizerInfoRuntime */
    public static final class Builder implements org.vpda.common.util.Builder<ComponentsPrefixLocalizerInfo> {
        private LocKey prefixKey;
        private String separator;

        /**
         * @return the prefixKey
         */
        public LocKey getPrefixKey() {
            return prefixKey;
        }

        /**
         * @return the separator
         */
        public String getSeparator() {
            return separator;
        }

        /**
         * @param prefixKey the prefixKey to set
         * @return this
         */
        public Builder setPrefixKey(LocKey prefixKey) {
            this.prefixKey = prefixKey;
            return this;
        }

        /**
         * @param separator the separator to set
         * @return this
         */
        public Builder setSeparator(String separator) {
            this.separator = separator;
            return this;
        }

        @Override
        public Class<? extends ComponentsPrefixLocalizerInfo> getTargetClass() {
            return ComponentsPrefixLocalizerInfo.class;
        }

        @Override
        public ComponentsPrefixLocalizerInfo build() {
            return new ComponentsPrefixLocalizerInfoRuntime(this);
        }

    }
}