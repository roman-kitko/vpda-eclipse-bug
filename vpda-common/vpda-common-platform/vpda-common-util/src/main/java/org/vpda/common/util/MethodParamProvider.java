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

import java.lang.reflect.Parameter;

/**
 * Provides or replace one method/constructor argument for another
 * 
 * @author kitko
 */
public interface MethodParamProvider {

    /**
     * Type of new argument result
     * 
     * @author kitko
     *
     */
    static enum ProviderType {
        /** New value provided */
        NEW,
        /** Nothing provided */
        NONE;
    }

    /**
     * Type of provide method result
     * 
     * @author kitko
     *
     */
    public static class ProviderResult {
        final ProviderType type;
        final Object value;

        /**
         * Creates ProviderResult
         * 
         * @param type
         * @param value
         */
        public ProviderResult(ProviderType type, Object value) {
            super();
            this.type = type;
            this.value = value;
        }

        /**
         * @return the type
         */
        public ProviderType getType() {
            return type;
        }

        /**
         * @return the value
         */
        public Object getValue() {
            return value;
        }

    }

    /**
     * Provides new value for type
     * 
     * @param param
     * @return new value for type or also null if nothing provided
     */
    public ProviderResult provide(Parameter param);

    /**
     * @param param
     * @return true if we can provide value for param
     */
    public boolean canProvideParamValue(Parameter param);

    /**
     * Type of replace method
     * 
     * @author kitko
     *
     */
    static enum ReplaceType {
        /** New value provided */
        NEW,
        /** Nothing provided */
        ORIGINAL;
    }

    /**
     * Type of provide method result
     * 
     * @author kitko
     *
     */
    public static class ReplaceResult {
        final ReplaceType type;
        final Object value;

        /**
         * Crates ReplaceResult
         * 
         * @param type
         * @param value
         */
        public ReplaceResult(ReplaceType type, Object value) {
            super();
            this.type = type;
            this.value = value;
        }

        /**
         * @return the type
         */
        public ReplaceType getType() {
            return type;
        }

        /**
         * @return the value
         */
        public Object getValue() {
            return value;
        }

    }

    /**
     * Replace the argument
     * 
     * @param param
     * @param arg
     * @return replaced argument or null if nothing replaced
     */
    public ReplaceResult replace(Parameter param, Object arg);

}
