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

import java.lang.reflect.Array;

/**
 * Common object utilities
 * 
 * @author kitko
 *
 */
public final class ObjectUtil {
    private ObjectUtil() {
        super();
    }

    /**
     * 
     * @param o1
     * @param o2
     * @return o1 == null ? o2 == null : o1.equals(o2);
     */
    public static boolean equalsConsiderNull(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        return o1 == null ? o2 == null : (o2 == null ? false : o1.equals(o2));
    }

    /**
     * 
     * @param o
     * @return o == null ? 0 : o.hashCode();
     */
    public static int hashCodeConsiderNull(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    /**
     * Convert the given array (which may be a primitive array) to an object array
     * (if necessary of primitive wrapper objects).
     * <p>
     * A <code>null</code> source value will be converted to an empty Object array.
     * 
     * @param source the (potentially primitive) array
     * @return the corresponding object array (never <code>null</code>)
     * @throws IllegalArgumentException if the parameter is not an array
     */
    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return new Object[0];
        }
        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }
        Class wrapperType = Array.get(source, 0).getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }

}
