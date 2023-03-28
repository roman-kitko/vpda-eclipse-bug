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

import java.io.Serializable;
import java.util.Arrays;

import org.vpda.common.context.localization.LocKey;
import org.vpda.internal.common.util.Assert;

/**
 * Localization key plus arguments
 * 
 * @author kitko
 *
 */
public final class LocKeyArguments implements Serializable {
    private static final long serialVersionUID = 4188537055107705923L;
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final LocKey locKey;
    private final Object[] args;

    /**
     * Creates LocKeyArguments
     * 
     * @param key
     * @param args
     */
    public LocKeyArguments(LocKey key, Object... args) {
        this.locKey = Assert.isNotNullArgument(key, "key");
        if (args.length == 0) {
            this.args = EMPTY_ARRAY;
        }
        else {
            this.args = Arrays.copyOf(args, args.length);
        }
    }

    /**
     * @return the locKey
     */
    public LocKey getLocKey() {
        return locKey;
    }

    /**
     * @return the args
     */
    public Object[] getArgs() {
        if (args == EMPTY_ARRAY) {
            return args;
        }
        return Arrays.copyOf(args, args.length);
    }

}
