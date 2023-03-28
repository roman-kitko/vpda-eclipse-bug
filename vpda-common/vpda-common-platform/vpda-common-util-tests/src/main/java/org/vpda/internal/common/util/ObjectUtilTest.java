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
package org.vpda.internal.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class ObjectUtilTest {

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.ObjectUtil#equalsConsiderNull(java.lang.Object, java.lang.Object)}.
     */
    @Test
    public final void testEqualsConsiderNull() {
        assertFalse(ObjectUtil.equalsConsiderNull("A", "B"));
        assertTrue(ObjectUtil.equalsConsiderNull("A", "A"));
        assertFalse(ObjectUtil.equalsConsiderNull("A", null));
        assertFalse(ObjectUtil.equalsConsiderNull(null, "A"));
        assertTrue(ObjectUtil.equalsConsiderNull(null, null));
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.ObjectUtil#hashCodeConsiderNull(java.lang.Object)}.
     */
    @Test
    public final void testHashCodeConsiderNull() {
        Object o = new Object() {
            @Override
            public int hashCode() {
                return 13;
            }
        };
        assertEquals(13, ObjectUtil.hashCodeConsiderNull(o));
        assertEquals(0, ObjectUtil.hashCodeConsiderNull(null));
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.JavaSerializationUtil#computeSize(java.io.Serializable)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testComputeSize() throws IOException {
        String s1 = "sss";
        String s2 = "sssss";
        int size1 = JavaSerializationUtil.computeSize(s1);
        int size2 = JavaSerializationUtil.computeSize(s2);
        assertEquals(2, size2 - size1);
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.JavaSerializationUtil#serializeObjectToByteArray(java.lang.Object)}.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public final void testSerializeObjectToByteArray() throws IOException, ClassNotFoundException {
        String s1 = "sss";
        byte[] bytes = JavaSerializationUtil.serializeObjectToByteArray(s1);
        String s2 = (String) JavaSerializationUtil.readObjectFromByteArray(bytes);
        assertEquals(s1, s2);
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.JavaSerializationUtil#serializeObjectToStream(java.lang.Object, java.io.OutputStream)}.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public final void testSerializeObjectToStream() throws IOException, ClassNotFoundException {
        String s1 = "sss";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JavaSerializationUtil.serializeObjectToStream(s1, bos);
        String s2 = (String) JavaSerializationUtil.readObjectFromStream(new ByteArrayInputStream(bos.toByteArray()));
        assertEquals(s1, s2);
    }

    /*
     * static final class ClassToLoad implements Serializable{ private static final
     * long serialVersionUID = 8603806355700299087L;
     * 
     * public ClassToLoad(){} }
     */

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.JavaSerializationUtil#readObjectFromByteArray(byte[], java.lang.ClassLoader)}.
     * 
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    @Test
    public final void testReadObjectFromByteArrayByteArrayClassLoader()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        // final byte[] bytes = ResourceLoader.getClassBytes(ClassToLoad.class);
        final byte[] bytes = new byte[] { -54, -2, -70, -66, 0, 0, 0, 51, 0, 27, 7, 0, 2, 1, 0, 56, 111, 114, 103, 47, 118, 112, 100, 97, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 99, 111, 109,
                109, 111, 110, 47, 117, 116, 105, 108, 47, 79, 98, 106, 101, 99, 116, 85, 116, 105, 108, 84, 101, 115, 116, 36, 67, 108, 97, 115, 115, 84, 111, 76, 111, 97, 100, 7, 0, 4, 1, 0, 16,
                106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 7, 0, 6, 1, 0, 20, 106, 97, 118, 97, 47, 105, 111, 47, 83, 101, 114, 105, 97, 108, 105, 122, 97, 98, 108, 101,
                1, 0, 16, 115, 101, 114, 105, 97, 108, 86, 101, 114, 115, 105, 111, 110, 85, 73, 68, 1, 0, 1, 74, 1, 0, 13, 67, 111, 110, 115, 116, 97, 110, 116, 86, 97, 108, 117, 101, 5, 119, 102,
                -36, 75, -85, -61, -75, 79, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 10, 0, 3, 0, 16, 12, 0, 12, 0, 13, 1, 0, 15, 76, 105, 110, 101, 78,
                117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 58, 76, 111,
                114, 103, 47, 118, 112, 100, 97, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 99, 111, 109, 109, 111, 110, 47, 117, 116, 105, 108, 47, 79, 98, 106, 101, 99, 116, 85, 116, 105, 108,
                84, 101, 115, 116, 36, 67, 108, 97, 115, 115, 84, 111, 76, 111, 97, 100, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 19, 79, 98, 106, 101, 99, 116, 85, 116, 105,
                108, 84, 101, 115, 116, 46, 106, 97, 118, 97, 1, 0, 12, 73, 110, 110, 101, 114, 67, 108, 97, 115, 115, 101, 115, 7, 0, 25, 1, 0, 44, 111, 114, 103, 47, 118, 112, 100, 97, 47, 105, 110,
                116, 101, 114, 110, 97, 108, 47, 99, 111, 109, 109, 111, 110, 47, 117, 116, 105, 108, 47, 79, 98, 106, 101, 99, 116, 85, 116, 105, 108, 84, 101, 115, 116, 1, 0, 11, 67, 108, 97, 115,
                115, 84, 111, 76, 111, 97, 100, 0, 48, 0, 1, 0, 3, 0, 1, 0, 5, 0, 1, 0, 26, 0, 7, 0, 8, 0, 1, 0, 9, 0, 0, 0, 2, 0, 10, 0, 1, 0, 1, 0, 12, 0, 13, 0, 1, 0, 14, 0, 0, 0, 47, 0, 1, 0, 1,
                0, 0, 0, 5, 42, -73, 0, 15, -79, 0, 0, 0, 2, 0, 17, 0, 0, 0, 6, 0, 1, 0, 0, 0, 91, 0, 18, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 19, 0, 20, 0, 0, 0, 2, 0, 21, 0, 0, 0, 2, 0, 22, 0, 23, 0,
                0, 0, 10, 0, 1, 0, 1, 0, 24, 0, 26, 0, 24 };
        final String loaderClassName = "org.vpda.internal.common.util.ObjectUtilTest.ClassToLoad";
        class Loader extends ClassLoader {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (loaderClassName.equals(name)) {
                    return defineClass(null, bytes, 0, bytes.length);
                }
                return super.loadClass(name);
            }

        }
        Loader loader = new Loader();
        Class<?> clazz = loader.loadClass(loaderClassName);
        Constructor constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        Object o = constructor.newInstance();
        byte[] serializedBytes = JavaSerializationUtil.serializeObjectToByteArray(o);
        try {
            JavaSerializationUtil.readObjectFromByteArray(serializedBytes);
            fail();
        }
        catch (ClassNotFoundException e) {
        }
        Object oo = JavaSerializationUtil.readObjectFromByteArray(serializedBytes, loader);
        assertNotNull(oo);
        assertEquals("org.vpda.internal.common.util.ObjectUtilTest$ClassToLoad", oo.getClass().getName());
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.JavaSerializationUtil#copyObjectUsingSerialization(java.io.Serializable)}.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public final void testCopyObjectUsingSerializationT() throws ClassNotFoundException, IOException {
        String s1 = "sss";
        String s2 = JavaSerializationUtil.copyObjectUsingSerialization(s1);
        assertNotSame(s1, s2);
        assertEquals(s1, s2);
    }

    static final class C1 implements Serializable {
        C1(String f) {
            f1 = f;
        }

        private static final long serialVersionUID = 1L;
        String f1;

        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            out.writeUTF(f1);
        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            f1 = in.readUTF();
        }
    }

    static final class C2 implements Serializable {
        C2(String f) {
            f1 = f;
        }

        private static final long serialVersionUID = 1L;
        String f1;

        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            out.writeUTF(f1);
        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            // f1 = in.readUTF();
        }
    }

    static final class C3 implements Serializable {
        C3(String f) {
            f1 = f;
        }

        private static final long serialVersionUID = 1L;
        String f1;

        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            out.writeUTF(f1);
        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            f1 = "Another value";
        }
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.JavaSerializationUtil#testObjectSerialization(java.io.Serializable)}.
     * 
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     */
    @Test
    public final void testTestObjectSerialization() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException {
        JavaSerializationUtil.testObjectSerialization(new C1("XXX"));
        try {
            JavaSerializationUtil.testObjectSerialization(new C2("XXX"));
            fail();
        }
        catch (IOException e) {
        }
        try {
            JavaSerializationUtil.testObjectSerialization(new C3("XXX"));
            fail();
        }
        catch (IOException e) {
        }
    }

    /**
     * Test method for
     * {@link org.vpda.internal.common.util.ObjectUtil#toObjectArray(java.lang.Object)}.
     */
    @Test
    public final void testToObjectArray() {
        Object[] o = ObjectUtil.toObjectArray(null);
        assertNotNull(o);
        assertEquals(0, o.length);
        o = ObjectUtil.toObjectArray(new int[] { 3, 4 });
        assertEquals(2, o.length);
        assertEquals(new Integer(3), o[0]);
        assertEquals(new Integer(4), o[1]);
    }

}
