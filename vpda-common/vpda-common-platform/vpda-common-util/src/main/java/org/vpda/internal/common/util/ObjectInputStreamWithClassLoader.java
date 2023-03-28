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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;

final class ObjectInputStreamWithClassLoader extends ObjectInputStream {
    private ClassLoader loader;

    ObjectInputStreamWithClassLoader(InputStream in, ClassLoader loader) throws IOException, StreamCorruptedException {
        super(in);
        this.loader = Assert.isNotNullArgument(loader, "loader");
    }

    /**
     * Make a primitive array class
     */
    private Class primitiveType(char type) {
        switch (type) {
        case 'B':
            return byte.class;
        case 'C':
            return char.class;
        case 'D':
            return double.class;
        case 'F':
            return float.class;
        case 'I':
            return int.class;
        case 'J':
            return long.class;
        case 'S':
            return short.class;
        case 'Z':
            return boolean.class;
        default:
            return null;
        }
    }

    /**
     * Use the given ClassLoader rather than using the system class
     */
    @Override
    @SuppressWarnings("unchecked")
    protected Class resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
        String cname = classDesc.getName();
        if (cname.startsWith("[")) {
            // An array
            Class component; // component class
            int dcount; // dimension
            for (dcount = 1; cname.charAt(dcount) == '['; dcount++) {
            }
            if (cname.charAt(dcount) == 'L') {
                component = loader.loadClass(cname.substring(dcount + 1, cname.length() - 1));
            }
            else {
                if (cname.length() != dcount + 1) {
                    throw new ClassNotFoundException(cname);// malformed
                }
                component = primitiveType(cname.charAt(dcount));
            }
            int dim[] = new int[dcount];
            for (int i = 0; i < dcount; i++) {
                dim[i] = 0;
            }
            return Array.newInstance(component, dim).getClass();
        }
        else {
            try {
                Class c = loadPrimitive(cname);
                if (c != null) {
                    return c;
                }
                return loader.loadClass(cname);
            }
            catch (ClassFormatError e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private Class loadPrimitive(String cname) {
        if ("int".equals(cname))
            return Integer.TYPE;
        if ("long".equals(cname))
            return Long.TYPE;
        if ("boolean".equals(cname))
            return Boolean.TYPE;
        if ("double".equals(cname))
            return Double.TYPE;
        if ("float".equals(cname))
            return Float.TYPE;
        if ("char".equals(cname))
            return Character.TYPE;
        if ("byte".equals(cname))
            return Byte.TYPE;
        if ("short".equals(cname))
            return Short.TYPE;
        return null;
    }

}
