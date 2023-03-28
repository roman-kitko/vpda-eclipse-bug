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
package org.vpda.common.comps;

/**
 * Single value current data
 * 
 * @author kitko
 * @param <T>
 */
public final class SingleData<T> implements CurrentData {
    private static final long serialVersionUID = 1L;
    private T value;

    /**
     * Creates ViewProviderSingleData
     * 
     * @param value
     */
    public SingleData(T value) {
        this.value = value;
    }

    /**
     * Creates new SingleData
     * 
     * @param value
     * @return new SingleData
     */
    public static <T> SingleData<T> create(T value) {
        return new SingleData<>(value);
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public int size() {
        return value != null ? 1 : 0;
    }

    /**
     * @return value
     */
    public T getValue() {
        return value;
    }

}
