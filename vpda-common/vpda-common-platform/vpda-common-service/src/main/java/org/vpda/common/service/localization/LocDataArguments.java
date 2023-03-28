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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * LocDataArguments encapsulates arguments for composite localization.
 * 
 * @author kitko
 *
 */
public final class LocDataArguments implements Serializable {
    private static final long serialVersionUID = 2747835770531793646L;
    private static final Object[] EMPTY_ARGS = new Object[0];
    /** Empty arguments */
    public static final LocDataArguments EMPTY = new LocDataArguments();

    private final Map<String, Object> arguments;

    private LocDataArguments() {
        this.arguments = new HashMap<String, Object>(0);
    }

    private LocDataArguments(Builder builder) {
        this.arguments = new HashMap<String, Object>(builder.arguments);
    }

    /**
     * @return true if args is empty
     */
    public boolean isEmpty() {
        return arguments.isEmpty();
    }

    /**
     * @return root arguments
     */
    public Object[] getRootArguments() {
        Object args = arguments.get("root");
        return args instanceof Object[] ? (Object[]) args : EMPTY_ARGS;
    }

    /**
     * @param child
     * @return child arguments
     */
    public Object[] getChildArguments(String child) {
        Object args = arguments.get(child);
        return args instanceof Object[] ? (Object[]) args : EMPTY_ARGS;
    }

    /**
     * @param child
     * @return child data
     */
    public LocDataArguments getChildData(String child) {
        Object args = arguments.get(child);
        return args instanceof LocDataArguments ? (LocDataArguments) args : EMPTY;
    }

    /**
     * Builder for LocDataArguments
     * 
     * @author kitko
     *
     */
    public static final class Builder {
        private final Map<String, Object> arguments = new HashMap<String, Object>(2);

        /**
         * Puts child args
         * 
         * @param child
         * @param args
         * @return this
         */
        public Builder putChildArgs(String child, Object... args) {
            arguments.put(child, args);
            return this;
        }

        /**
         * Puts root arguments
         * 
         * @param d
         * @param args
         * @return this
         */
        public Builder putRootArgs(Object d, Object... args) {
            return putChildArgs("root", d, args);
        }

        /**
         * Puts child data
         * 
         * @param child
         * @param data
         * @return this
         */
        public Builder putChildData(String child, LocDataArguments data) {
            arguments.put(child, data);
            return this;
        }

        /**
         * Builds the data arguments
         * 
         * @return args
         */
        public LocDataArguments build() {
            return new LocDataArguments(this);
        }

    }

}
