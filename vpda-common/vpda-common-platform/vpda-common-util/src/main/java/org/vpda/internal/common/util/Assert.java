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
import java.util.Collection;

/**
 * Assertion utilities
 * 
 * @author kitko
 *
 */
public final class Assert {
    private Assert() {
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>false</code>.
     * 
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
     * </pre>
     * 
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @return true
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static boolean isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
        return true;
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>false</code>.
     * 
     * <pre class="code">
     * Assert.isTrue(i &gt; 0);
     * </pre>
     * 
     * @param expression a boolean expression
     * @return true
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static boolean isTrue(boolean expression) {
        return isTrue(expression, "[Assertion failed] - this expression must be true");

    }

    /**
     * Assert that an object is <code>null</code> .
     * 
     * <pre class="code">
     * Assert.isNull(value, "The value must be null");
     * </pre>
     * 
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is <code>null</code> .
     * 
     * <pre class="code">
     * Assert.isNull(value);
     * </pre>
     * 
     * @param object the object to check
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not <code>null</code> .
     * 
     * <pre class="code">
     * Assert.notNull(clazz, "The class must not be null");
     * </pre>
     * 
     * @param <T>
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @return same object
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static <T> T isNotNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    /**
     * Assert that argument is not null else throws <code>
     * IllegalArgumentException("Passed argument [" + argumentName + "] is null");
     * </code>
     * 
     * @param <T>
     * @param object
     * @param argumentName
     * @return same argument
     */
    public static <T> T isNotNullArgument(T object, String argumentName) {
        if (object == null) {
            throw new IllegalArgumentException("Passed argument [" + argumentName + "] is null");
        }
        return object;
    }

    /**
     * Assert that an object is not <code>null</code> .
     * 
     * <pre class="code">
     * Assert.notNull(clazz);
     * </pre>
     * 
     * @param <T>
     * @param object the object to check
     * @return same object
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static <T> T isNotNull(T object) {
        return isNotNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * Assert that the given String is not empty; that is, it must not be
     * <code>null</code> and not the empty String.
     * 
     * <pre class="code">
     * Assert.hasLength(name, "Name must not be empty");
     * </pre>
     * 
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @return same text
     */
    public static String isNotEmpty(String text, String message) {
        if (StringUtil.isEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
        return text;
    }

    /**
     * Assert that the given String is not empty; that is, it must not be
     * <code>null</code> and not the empty String.
     * 
     * <pre class="code">
     * Assert.hasLength(name);
     * </pre>
     * 
     * @param text the String to check
     * @return same text
     */
    public static String isNotEmpty(String text) {
        return isNotEmpty(text, "[Assertion failed] - this String argument must have length; it must not be <code>null</code> or empty");
    }

    /**
     * Assert that argument is not blank String else throws <code>
     * IllegalArgumentException("Passed string argument [" + argumentName + "] is empty or null ");
     * </code>
     * 
     * @param object
     * @param argumentName
     * @return same argument
     */
    public static String isNotEmptyArgument(String object, String argumentName) {
        if (object == null) {
            throw new IllegalArgumentException("Passed argument [" + argumentName + "] is null");
        }
        if (object.isEmpty()) {
            throw new IllegalArgumentException("Passed string argument [" + argumentName + "] is empty");
        }
        return object;
    }

    /**
     * Assert that argument is not blank String else throws <code>
     * IllegalArgumentException("Passed string argument [" + argumentName + "] is empty or null ");
     * </code>
     * 
     * @param coll
     * @param argumentName
     * @return same argument
     */
    public static <T> Collection<T> isNotEmptyArgument(Collection<T> coll, String argumentName) {
        if (coll == null) {
            throw new IllegalArgumentException("Passed collection argument [" + argumentName + "] is null");
        }
        if (coll.isEmpty()) {
            throw new IllegalArgumentException("Passed collection argument [" + argumentName + "] is empty");
        }
        return coll;
    }

    /**
     * Assert that argument is not blank String else throws <code>
     * IllegalArgumentException("Passed string argument [" + argumentName + "] is empty or null ");
     * </code>
     * 
     * @param coll
     * @param argumentName
     * @return same argument
     */
    public static <T> T[] isNotEmptyArgument(T[] coll, String argumentName) {
        if (coll == null) {
            throw new IllegalArgumentException("Passed array argument [" + argumentName + "] is null");
        }
        if (coll.length == 0) {
            throw new IllegalArgumentException("Passed array argument [" + argumentName + "] is empty");
        }
        return coll;
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * 
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     * 
     * @param clazz the required class
     * @param obj   the object to check
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * 
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     * 
     * @param type    the type to check against
     * @param obj     the object to check
     * @param message a message which will be prepended to the message produced by
     *                the function itself, and which may be used to provide context.
     *                It should normally end in a ": " or ". " so that the function
     *                generate message looks ok when prepended to it.
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class type, Object obj, String message) {
        isNotNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message + "Object of class [" + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
        }
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is
     * <code>true</code>.
     * 
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     * 
     * @param superType the super type to check
     * @param subType   the sub type to check
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isAssignable(Class superType, Class subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is
     * <code>true</code>.
     * 
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     * 
     * @param superType the super type to check against
     * @param subType   the sub type to check
     * @param message   a message which will be prepended to the message produced by
     *                  the function itself, and which may be used to provide
     *                  context. It should normally end in a ": " or ". " so that
     *                  the function generate message looks ok when prepended to it.
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        isNotNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalStateException</code> if
     * the test result is <code>false</code>. Call isTrue if you wish to throw
     * IllegalArgumentException on an assertion failure.
     * 
     * <pre class="code">
     * Assert.state(id == null, "The id property must not already be initialized");
     * </pre>
     * 
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @return true
     * @throws IllegalStateException if expression is <code>false</code>
     */
    public static boolean state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
        return true;
    }

    /**
     * Assert a boolean expression, throwing {@link IllegalStateException} if the
     * test result is <code>false</code>.
     * <p>
     * Call {@link #isTrue(boolean)} if you wish to throw
     * {@link IllegalArgumentException} on an assertion failure.
     * 
     * <pre class="code">
     * Assert.state(id == null);
     * </pre>
     * 
     * @param expression a boolean expression
     * @throws IllegalStateException if the supplied expression is
     *                               <code>false</code>
     */
    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

    /**
     * Asserts that passed array is not empty
     * 
     * @param array
     * @return same array
     */
    public static Object arrayNotEmpty(Object array) {
        if (Array.getLength(array) == 0) {
            throw new IllegalArgumentException("Passed array is empty");
        }
        return array;
    }

    /**
     * Asserts that passed array is not empty
     * 
     * @param array
     * @param msg   message in thrown exception
     * @return same array
     */
    public static Object arrayNotEmpty(Object array, String msg) {
        if (Array.getLength(array) == 0) {
            throw new IllegalArgumentException(msg);
        }
        return array;
    }

    /**
     * Asserts that passed array is not empty
     * 
     * @param array
     * @param argumentName
     * @return same array
     */
    public static Object arrayArgumentNotEmpty(Object array, String argumentName) {
        if (Array.getLength(array) == 0) {
            throw new IllegalArgumentException("Passes argument [" + argumentName + "] is empty");
        }
        return array;
    }

}
