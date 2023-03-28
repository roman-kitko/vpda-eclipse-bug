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
package org.vpda.common.util;

/** Common constants regarding java annotations */
public final class AnnotationConstants {
    private AnnotationConstants() {
    }

    /** Undefined type */
    public static class UNDEFINED_TYPE {
    }

    /** Undefined String as default value for String constants */
    public static final String UNDEFINED_STRING = "UNDEFINED";

    /** Three-state boolean */
    public static enum Boolean {
        /** True */
        TRUE {
            @Override
            public boolean isConcrete() {
                return true;
            }

            @Override
            public boolean value() {
                return true;
            }
        },
        /** False */
        FALSE {
            @Override
            public boolean isConcrete() {
                return true;
            }

            @Override
            public boolean value() {
                return false;
            }
        },
        /** Undefined, usually default */
        UNDEFINED {
            @Override
            public boolean isConcrete() {
                return false;
            }

            @Override
            public boolean value() {
                throw new IllegalStateException("Cannot convert undefined Boolean to boolean value");
            }
        };

        /**
         * 
         * @return true if a constant means real concrete value
         */
        public abstract boolean isConcrete();

        /**
         * @return boolean value
         */
        public abstract boolean value();

    }

}
