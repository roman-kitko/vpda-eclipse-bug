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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/** Utilities using java serialization */
public final class JavaSerializationUtil {

    private JavaSerializationUtil() {
    }

    /**
     * Serialize object to byte array
     * 
     * @param obj
     * @return object as byte array
     * @throws IOException
     */
    public static byte[] serializeObjectToByteArray(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(64);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                }
                catch (IOException e) {
                }
            }
        }
        return bos.toByteArray();
    }

    /**
     * Serialize object to byte array returning back all used loaders
     * 
     * @param obj
     * @param loaderCollector
     * @return bytes
     * @throws IOException
     */
    public static byte[] serializeObjectToByteArray(Object obj, Set<ClassLoader> loaderCollector) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(64);
        ObjectOutputStream oos = null;
        final Map<ClassLoader, Integer> identityCollector = new IdentityHashMap<>();
        try {
            oos = new ObjectOutputStream(bos) {
                @Override
                protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
                    ClassLoader usedLoader = desc.forClass().getClassLoader();
                    super.writeClassDescriptor(desc);
                    if (usedLoader != null) {
                        identityCollector.put(usedLoader, 1);
                    }
                }
            };
            oos.writeObject(obj);
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                }
                catch (IOException e) {
                }
            }
        }
        loaderCollector.addAll(identityCollector.keySet());
        return bos.toByteArray();
    }

    /**
     * Serialize object to stream
     * 
     * @param obj
     * @param os
     * @throws IOException
     */
    public static void serializeObjectToStream(Object obj, OutputStream os) throws IOException {
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(os);
        oos.writeObject(obj);
        oos.flush();
    }

    /**
     * Read object from byte array
     * 
     * @param bytes
     * @return read object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObjectFromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        return JavaSerializationUtil.readObjectFromStream(bis);
    }

    /**
     * Read object from byte array and resolving classes using loader
     * 
     * @param bytes
     * @param loader
     * @return read object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObjectFromByteArray(byte[] bytes, ClassLoader loader) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        return JavaSerializationUtil.readObjectFromStream(bis, loader);
    }

    /**
     * Reads object from stream
     * 
     * @param in
     * @return object deserialized from stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObjectFromStream(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        ois = new ObjectInputStream(in);
        return ois.readObject();
    }

    /**
     * Copy object using io serialization and passed loader This method uses
     * serialieObjectToByteArray and readObjectFromByteArray
     * 
     * @param <T>
     * @param o
     * @param loader
     * @return copy of object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T copyObjectUsingSerialization(T o, ClassLoader loader) throws IOException, ClassNotFoundException {
        return (T) readObjectFromByteArray(serializeObjectToByteArray(o), loader);
    }

    /**
     * Copy object using io serialization This method uses serialieObjectToByteArray
     * and readObjectFromByteArray
     * 
     * @param <T> - type of object
     * @param o
     * @return copy of object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T copyObjectUsingSerialization(T o) throws IOException, ClassNotFoundException {
        return (T) readObjectFromByteArray(serializeObjectToByteArray(o));
    }

    /**
     * Test object serialization
     * 
     * @param <T>
     * @param o
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T extends Serializable> void testObjectSerialization(T o) throws IOException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        Object copy = copyObjectUsingSerialization(o);
        for (Field field : copy.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value1 = field.get(o);
            Object value2 = field.get(copy);
            if (value1 != null && value2 == null) {
                if (!Modifier.isTransient(field.getModifiers())) {
                    throw new IOException("Field not copied and is not transient : " + field.getName());
                }
            }
            else if (value1 == null && value2 != null) {
                throw new IOException("Field not null after serialization : " + field.getName());
            }
            else if (value1 != null && value2 != null) {
                if (field.getType().isArray()) {
                    Object[] a1 = ObjectUtil.toObjectArray(value1);
                    Object[] a2 = ObjectUtil.toObjectArray(value2);
                    if (!Arrays.equals(a1, a2)) {
                        throw new IOException("Array field values not equals : " + field.getName());
                    }
                }
                else if (!value1.equals(value2)) {
                    throw new IOException("Field values not equals : " + field.getName());
                }
            }
        }
    }

    /**
     * Reads object from stream and resolving classes using loader
     * 
     * @param in
     * @param loader
     * @return object deserialized from stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObjectFromStream(InputStream in, ClassLoader loader) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStreamWithClassLoader(in, loader);
        try {
            return ois.readObject();
        }
        finally {
            ois.close();
        }
    }

    /**
     * Computes size of bytes for serializable objects
     * 
     * @param obj
     * @return size of bytes
     * @throws IOException
     */
    public static int computeSize(Serializable obj) throws IOException {
        return serializeObjectToByteArray(obj).length;
    }

}
