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
 * Holder of any data. It can be used for anonymous classes to fetch some result
 * 
 * @author kitko
 * @param <T>
 */
public final class Holder<T> {
    T value;

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Sets value only if we are still empty
     * 
     * @param value
     */
    public void setValueIfStillNull(T value) {
        if (this.value == null) {
            this.value = value;
        }
    }

    /**
     * @param value
     */
    public Holder(T value) {
        super();
        this.value = value;
    }

    /**
     * Creates empty holder
     */
    public Holder() {
    }

}
