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
package org.vpda.common.service.localization;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vpda.common.context.localization.LocKey;
import org.vpda.common.util.AnnotationConstants;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.StringUtil;

/**
 * @author kitko Annotation to create {@link LocKey}
 */
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocKeyInfo {
    /** Path for localization */
    public String path();

    /** Localization key in context of path */
    public String key() default AnnotationConstants.UNDEFINED_STRING;

    /**
     * @author kitko
     *
     */
    static final class LocKeyInfoRuntime implements LocKeyInfo {
        private final String path;
        private final String key;

        private LocKeyInfoRuntime(Builder builder) {
            this.path = Assert.isNotEmptyArgument(builder.path, "path");
            this.key = !StringUtil.isEmpty(builder.key) ? builder.key : AnnotationConstants.UNDEFINED_STRING;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return LocKeyInfo.class;
        }

        @Override
        public String path() {
            return path;
        }

        @Override
        public String key() {
            return key;
        }

    }

    /**
     * Builder of LocKeyInfo runtime instance
     * 
     * @author kitko
     *
     */
    public static final class Builder implements org.vpda.common.util.Builder<LocKeyInfo> {
        private String path;
        private String key;

        /**
         * @return the path
         */
        public String getPath() {
            return path;
        }

        /**
         * @param path the path to set
         * @return this
         */
        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        /**
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * @param key the key to set
         * @return this
         */
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        /**
         * @param locKey
         * @return this
         */
        public Builder setLocKey(LocKey locKey) {
            this.path = locKey.getPath();
            this.key = null;
            return this;
        }

        @Override
        public Class<? extends LocKeyInfo> getTargetClass() {
            return LocKeyInfo.class;
        }

        @Override
        public LocKeyInfo build() {
            return new LocKeyInfoRuntime(this);
        }

    }
}
