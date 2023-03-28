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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Utility class for manipulating with classes
 * 
 * @author kitko
 *
 */
public final class ClassUtil {
    private ClassUtil() {
    }

    private static final Map<String, Class> primitiveClasses = new HashMap<>();

    static {
        primitiveClasses.put("int", int.class);
        primitiveClasses.put("long", long.class);
        primitiveClasses.put("short", short.class);
        primitiveClasses.put("boolean", boolean.class);
        primitiveClasses.put("float", float.class);
        primitiveClasses.put("double", double.class);
        primitiveClasses.put("char", char.class);
    }

    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
    }

    /**
     * Gets wrapper class for primitive
     * 
     * @param primitiveClass
     * @return wrapper class
     */
    public static Class<?> getWrapperClassForPrimitive(Class<?> primitiveClass) {
        if (primitiveClass.isPrimitive()) {
            return primitiveWrapperMap.get(primitiveClass);
        }
        throw new IllegalArgumentException("Passed argument class " + primitiveClass + " is not primitive class");
    }

    /**
     * Maps wrapper {@code Class}es to their corresponding primitive types.
     */
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();
    static {
        for (final Class<?> primitiveClass : primitiveWrapperMap.keySet()) {
            final Class<?> wrapperClass = primitiveWrapperMap.get(primitiveClass);
            if (!primitiveClass.equals(wrapperClass)) {
                wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
            }
        }
    }

    /**
     * Test whether we can assign result to that type
     * 
     * @param expected
     * @param result
     * @return true if we can assign method result into that type
     */
    public static <T> boolean isMethodResultCompatible(Class<T> expected, Object result) {
        if (result == null) {
            return true;
        }
        if (expected.equals(result.getClass())) {
            return true;
        }
        if (expected.isInstance(result)) {
            return true;
        }
        if (expected.isPrimitive() && result.getClass().equals(primitiveWrapperMap.get(expected))) {
            return true;
        }
        if (wrapperPrimitiveMap.containsKey(expected) && result.getClass().equals(wrapperPrimitiveMap.get(expected))) {
            return true;
        }
        return false;
    }

    /**
     * Creates instance from class using non param constructor
     * 
     * @param <T>
     * @param className
     * @param iface
     * @return new instance of class
     */
    public static <T> T createInstance(String className, Class<T> iface) {
        return createInstance(className, iface, null);
    }

    /**
     * Searches for suitable constructor and creates instance of class
     * 
     * @param <T>
     * @param clazz
     * @param params
     * @return instance of class
     */
    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> clazz, Object... params) {
        Constructor<?> suitableCt = null;
        for (Constructor<?> ct : clazz.getConstructors()) {
            if (ct.getParameterTypes().length == params.length) {
                suitableCt = ct;
                int i = 0;
                for (Class<?> paramType : ct.getParameterTypes()) {
                    if (!paramType.isInstance(params[i++])) {
                        suitableCt = null;
                        break;
                    }
                }
                if (suitableCt != null) {
                    break;
                }
            }
        }
        if (suitableCt != null) {
            try {
                return (T) suitableCt.newInstance(params);
            }
            catch (InstantiationException e) {
                throw new VPDARuntimeException(e);
            }
            catch (IllegalAccessException e) {
                throw new VPDARuntimeException(e);
            }
            catch (InvocationTargetException e) {
                throw new VPDARuntimeException(e);
            }
        }
        throw new IllegalArgumentException("No suitable constructor found for class : " + clazz);
    }

    /**
     * Creates instance using passed constructor
     * 
     * @param <T>
     * @param ct
     * @param params
     * @return instance
     */
    public static <T> T createInstance(Constructor<T> ct, Object... params) {
        try {
            return ct.newInstance(params);
        }
        catch (IllegalArgumentException e) {
            throw new VPDARuntimeException("error creating instance using constructor : " + ct, e);
        }
        catch (InstantiationException e) {
            throw new VPDARuntimeException("error creating instance using constructor : " + ct, e);
        }
        catch (IllegalAccessException e) {
            throw new VPDARuntimeException("error creating instance using constructor : " + ct, e);
        }
        catch (InvocationTargetException e) {
            throw new VPDARuntimeException("error creating instance using constructor : " + ct, e);
        }
    }

    /**
     * Creates instance from class using non param constructor
     * 
     * @param <T>
     * @param className
     * @param iface
     * @param classLoader
     * @return new instance of class
     */
    @SuppressWarnings("unchecked")
    public static <T> T createInstance(String className, Class<T> iface, ClassLoader classLoader) {
        T instance = null;
        Class clazz = null;
        try {
            if (classLoader != null) {
                clazz = classLoader.loadClass(className);
            }
            else {
                clazz = Class.forName(className);
            }
            if (!iface.isAssignableFrom(clazz)) {
                throw new VPDARuntimeException("Class " + iface + " is not assignable from " + className);
            }
            instance = (T) clazz.newInstance();
        }
        catch (ClassNotFoundException e) {
            throw new VPDARuntimeException("Error instantiating class", e);
        }
        catch (InstantiationException e) {
            throw new VPDARuntimeException("Error instantiating class", e);
        }
        catch (IllegalAccessException e) {
            throw new VPDARuntimeException("Error instantiating class", e);
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    private static void getInterfacesRecursive(Class clazz, Class parent, Set<Class> result) {
        Class[] ifaces = clazz.getInterfaces();
        for (int i = 0; i < ifaces.length; i++) {
            if (parent == null || parent.isAssignableFrom(ifaces[i])) {
                boolean canAdd = true;
                for (Iterator<? extends Class> j = result.iterator(); j.hasNext();) {
                    Class alreadyAdded = j.next();
                    if (alreadyAdded.equals(ifaces[i])) {
                        canAdd = false;
                        break;
                    }
                    else if (ifaces[i].isAssignableFrom(alreadyAdded)) {
                        canAdd = false;
                        break;
                    }
                    else if (alreadyAdded.isAssignableFrom(ifaces[i])) {
                        j.remove();
                    }
                }
                if (canAdd) {
                    result.add(ifaces[i]);
                }
            }
        }
        Class superClass = clazz.getSuperclass();
        if (superClass != null) {
            getInterfacesRecursive(superClass, parent, result);
        }
    }

    /**
     * 
     * @param clazz
     * @return recursive list of all interfaces for class recursive
     */
    @SuppressWarnings("unchecked")
    public static Collection<Class<?>> getInterfacesHierarchy(Class clazz) {
        return getInterfacesInHierarchy(clazz, (Class) null);
    }

    /**
     * 
     * @param <T>
     * @param clazz
     * @param parent
     * @return recursive list of all interfaces for class recursive but only
     *         subclasses of parent
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<Class<T>> getInterfacesInHierarchy(Class clazz, Class<T> parent) {
        Set result = new HashSet<Class<T>>(3);
        getInterfacesRecursive(clazz, parent, result);
        return result;
    }

    /**
     * Find method in class hierarchy
     * 
     * @param clazz
     * @param methodName
     * @param params
     * @return Method
     * @throws NoSuchMethodException
     */
    public static Method findDeclaredMethodInHierarchy(Class<?> clazz, String methodName, Class[] params) throws NoSuchMethodException {
        Method m = null;
        try {
            m = clazz.getDeclaredMethod(methodName, params);
        }
        catch (NoSuchMethodException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null) {
                m = findDeclaredMethodInHierarchy(superClass, methodName, params);
            }
            else {
                throw e;
            }
        }
        return m;

    }

    /**
     * Get all declared classes to hierarchy
     * 
     * @param clazz
     * @return list of declared classes
     */
    public static List<Class<?>> getDeclaredAndInheritedClasses(Class<?> clazz) {
        List<Class<?>> result = new ArrayList<Class<?>>(10);
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            result.addAll(getDeclaredAndInheritedClasses(superClass));
        }
        for (Class declaredClazz : clazz.getDeclaredClasses()) {
            result.add(declaredClazz);
        }
        return result;
    }

    /**
     * Get all declared classes to hierarchy in reverse order - parent last
     * 
     * @param clazz
     * @return list of declared classes
     */
    public static List<Class<?>> getDeclaredAndInheritedClassesReverse(Class<?> clazz) {
        List<Class<?>> result = new ArrayList<Class<?>>(10);
        for (Class declaredClazz : clazz.getDeclaredClasses()) {
            result.add(declaredClazz);
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            result.addAll(getDeclaredAndInheritedClassesReverse(superClass));
        }
        return result;
    }

    /**
     * Get all declared fields to hierarchy
     * 
     * @param clazz
     * @return list of declared fields
     */
    public static List<Field> getDeclaredAndInheritedFields(Class<?> clazz) {
        List<Field> result = new ArrayList<Field>(10);
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            result.addAll(getDeclaredAndInheritedFields(superClass));
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isSynthetic()) {
                result.add(field);
            }
        }
        return result;
    }

    /**
     * Get all declared fields to hierarchy in reverse order - parent last
     * 
     * @param clazz
     * @return list of declared fields
     */
    public static List<Field> getDeclaredAndInheritedFieldsReverse(Class<?> clazz) {
        List<Field> result = new ArrayList<Field>(10);
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isSynthetic()) {
                result.add(field);
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            result.addAll(getDeclaredAndInheritedFieldsReverse(superClass));
        }
        return result;
    }

    /**
     * @param clazz
     * @return short name of class just by calling class.getSimpleName() intuduced
     *         in 1.5
     */
    public static String getShortClassName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    /**
     * Get all declared fields to hierarchy
     * 
     * @param clazz
     * @param fieldType - type of field class in hierarchy
     * @return list of declared fields
     */
    public static List<Field> getDeclaredAndInheritedFields(Class<?> clazz, Class<? extends Object> fieldType) {
        List<Field> result = new ArrayList<Field>(10);
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            result.addAll(getDeclaredAndInheritedFields(superClass, fieldType));
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (fieldType.isAssignableFrom(field.getType())) {
                result.add(field);
            }
        }
        return result;
    }

    /**
     * Get all declared fields to hierarchy in reverse order - parent as last
     * 
     * @param clazz
     * @param fieldType - type of field class in hierarchy
     * @return list of declared fields
     */
    public static List<Field> getDeclaredAndInheritedFieldsReverse(Class<?> clazz, Class<? extends Object> fieldType) {
        List<Field> result = new ArrayList<Field>(10);
        for (Field field : clazz.getDeclaredFields()) {
            if (fieldType.isAssignableFrom(field.getType())) {
                result.add(field);
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            result.addAll(getDeclaredAndInheritedFieldsReverse(superClass, fieldType));
        }
        return result;
    }

    /**
     * Return all super classes of class, super class is at beginning
     * 
     * @param <T>   - type of class
     * @param clazz
     * @return list of super classes
     */
    public static <T> List<Class<? super T>> getClassHierarchy(Class<T> clazz) {
        List<Class<? super T>> result = new ArrayList<Class<? super T>>(3);
        Class<? super T> superClass = clazz.getSuperclass();
        if (superClass != null) {
            result.addAll(getClassHierarchy(superClass));
        }
        result.add(clazz);
        return result;
    }

    /**
     * Creates instance from class using non param constructor
     * 
     * @param <T>
     * @param clazz
     * @return new instance of class
     */
    public static <T> T createInstance(Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
        }
        catch (InstantiationException e) {
            throw new VPDARuntimeException("Error creating instance", e);
        }
        catch (IllegalAccessException e) {
            throw new VPDARuntimeException("Error creating instance", e);
        }
        return instance;
    }

    /**
     * @param clazz
     * @return instance
     */
    public static <T> T createInstanceEvenPrivate(Class<T> clazz) {
        T instance = null;
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor();
        }
        catch (NoSuchMethodException | SecurityException e) {
            throw new VPDARuntimeException("Error creating instance, no nonparam constructor", e);
        }

        try {
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new VPDARuntimeException("Error creating instance, no nonparam constructor", e);
        }

        return instance;
    }

    /**
     * Will return annotation value in hierarchy starting from class going up to
     * super class
     * 
     * @param <T>
     * @param clazz
     * @param annotationClass
     * @return annotation value or null if not class in hierarchy declared that
     *         annotation
     */
    public static <T extends Annotation> T getAnnotationInHierarchy(Class<?> clazz, Class<T> annotationClass) {
        T annotation = clazz.getAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        if (clazz.getSuperclass() != null) {
            return getAnnotationInHierarchy(clazz.getSuperclass(), annotationClass);
        }
        return null;
    }

    /**
     * Collects all getter methods from class
     * 
     * @param clazz
     * @return collection of all getters
     */
    public static Collection<Method> getGetters(Class<?> clazz) {
        Collection<Method> methods = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            boolean isPropertyGetter = false;
            isPropertyGetter = isMethodPropertyGetter(method);
            if (isPropertyGetter) {
                methods.add(method);
            }
        }
        return methods;
    }

    private static boolean isMethodPropertyGetter(Method method) {
        boolean isPropertyGetter = false;
        String methodName = method.getName();
        if (method.getParameterTypes().length == 0 && Modifier.isPublic(method.getModifiers())) {
            if (methodName.startsWith("get") && methodName.length() > 3) {
                isPropertyGetter = true;
            }
            if (methodName.startsWith("is") && methodName.length() > 2) {
                isPropertyGetter = true;
            }
        }
        return isPropertyGetter;
    }

    /**
     * Computes getter value by object and property name
     * 
     * @param object
     * @param propertyName
     * @return value of getter
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getGetterValue(Object object, String propertyName)
            throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String name = "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        Method method = object.getClass().getMethod(name, new Class[0]);
        return method.invoke(object);
    }

    /**
     * Gets annotation on type. If annotation is not present throws
     * IllegalArgumentException
     * 
     * @param <T>
     * @param annotationType
     * @param clazz
     * @return annotation on type
     * @throws IllegalArgumentException
     */
    public static <T extends Annotation> T getRequiredAnnotation(Class<T> annotationType, Class<?> clazz) throws IllegalArgumentException {
        T res = clazz.getAnnotation(annotationType);
        if (res == null) {
            throw new IllegalArgumentException(MessageFormat.format("Required Annotation [{0}] not present on type [{1}]", annotationType, clazz));
        }
        return res;
    }

    /**
     * Gets annotation on field. If annotation is not present throws
     * IllegalArgumentException
     * 
     * @param <T>
     * @param annotationType
     * @param field
     * @return annotation on field
     * @throws IllegalArgumentException
     */
    public static <T extends Annotation> T getRequiredAnnotation(Class<T> annotationType, Field field) {
        T res = field.getAnnotation(annotationType);
        if (res == null) {
            throw new IllegalArgumentException(MessageFormat.format("Required Annotation [{0}] not present on field [{1}]", annotationType, field));
        }
        return res;
    }

    /**
     * Check whether method can throw passed exception
     * 
     * @param m
     * @param t
     * @return true if method can throw exception
     */
    public static boolean canMethodThrowException(Method m, Throwable t) {
        if (t instanceof RuntimeException) {
            return true;
        }
        for (Class<?> throwing : m.getExceptionTypes()) {
            if (throwing.isInstance(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Try to load class by system classloader
     * 
     * @param name
     * @return loaded class
     * @throws ClassNotFoundException
     */
    public static Class<?> loadBySystemLoader(String name) throws ClassNotFoundException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        if (systemClassLoader == null) {
            throw new ClassNotFoundException("systemClassLoader is null, cannot load [" + name + "]");
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(name, false, systemClassLoader);
            return clazz;
        }
        catch (ClassNotFoundException e) {
            if (name.length() < 10) {
                return loadPrimitive(name);
            }
            throw e;
        }

    }

    private static Class<?> loadPrimitive(String name) throws ClassNotFoundException {
        Class<?> c = primitiveClasses.get(name);
        if (c != null) {
            return c;
        }
        throw new ClassNotFoundException("Cannot load " + name);
    }

    /**
     * Test whether value is immutable
     * 
     * @param value
     * @return true if value is immutable
     */
    public static boolean isImmutable(Object value) {
        Class<? extends Object> clazz = value.getClass();
        if (String.class.equals(clazz)) {
            return true;
        }
        else if (Number.class.isAssignableFrom(clazz)) {
            return true;
        }
        return clazz.isAnnotationPresent(Immutable.class);
    }

    /**
     * @param o
     * @param name
     * @param type
     * @return field value
     */
    public static <T> T getFieldValue(Object o, String name, Class<T> type) {
        Class clazz = o.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (name.equals(field.getName())) {
                    field.setAccessible(true);
                    try {
                        return type.cast(field.get(o));
                    }
                    catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new VPDARuntimeException("Cannot access field value " + field, e);
                    }
                }

            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

}
