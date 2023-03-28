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
package org.vpda.internal.common.util;

/**
 * Reference counter
 * 
 * @author kitko
 *
 */
public final class RefCount {
    private int count;
    private ZeroAction zeroAction;

    /**
     * @return decremented counter
     */
    public synchronized int decrement() {
        int result = --count;
        if (zeroAction != null && result == 0) {
            zeroAction.execute();
        }
        return result;
    }

    /**
     * @return incremented counter
     */
    public synchronized int increment() {
        return ++count;
    }

    /**
     * Action to take on zero count
     * 
     * @author kitko
     *
     */
    public static interface ZeroAction {
        /** execute some action */
        void execute();
    }

    /**
     * Creates RefCount
     */
    public RefCount() {
    }

    /**
     * Creates RefCount with action to take on zero counter
     * 
     * @param zeroAction
     */
    public RefCount(ZeroAction zeroAction) {
        this.zeroAction = zeroAction;
    }
}
