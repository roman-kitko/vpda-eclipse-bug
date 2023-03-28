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
package org.vpda.common.comps.ui;

import java.util.List;

/**
 * Component that will show list of values separated by separator in single list
 * 
 * @author kitko
 * @param <E> Type ,of list item
 *
 */
public final class ListFieldAC<E> extends AbstractFieldAC<List<? extends E>> {
    private char separator;
    private Class<? extends E> clazz;

    /** */
    public ListFieldAC() {
    }

    /**
     * Creates empty field
     * 
     * @param localId
     * @param clazz
     * @param separator
     */
    public ListFieldAC(String localId, Class<? extends E> clazz, char separator) {
        super(localId);
        this.clazz = clazz;
        this.separator = separator;
    }

    /**
     * Creates comma separated list component
     * 
     * @param <T>
     * @param localId
     * @param clazz
     * @return comma separated list component
     */
    public static <T> ListFieldAC<T> commaListComponent(String localId, Class<? extends T> clazz) {
        return new ListFieldAC<T>(localId, clazz, ',');
    }

    private static final long serialVersionUID = -8204826554541953074L;

    /**
     * @return separator char
     */
    public char getSeparator() {
        return separator;
    }

    /**
     * @return class of value item
     */
    public Class<? extends E> getValueClass() {
        return clazz;
    }

    /**
     * @param separator the separator to set
     */
    public final void setSeparator(char separator) {
        this.separator = separator;
    }

    /**
     * @param clazz the clazz to set
     */
    public final void setValueCLass(Class<? extends E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || (getValue() == null || getValue().isEmpty());
    }

}
