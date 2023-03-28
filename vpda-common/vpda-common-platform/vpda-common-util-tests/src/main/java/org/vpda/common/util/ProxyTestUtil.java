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
package org.vpda.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Proxy utilities methods
 * 
 * @author kitko
 *
 */
public final class ProxyTestUtil {
    private ProxyTestUtil() {
    }

    private static final class EmptyIG implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }

    }

    /**
     * @param <T>
     * @param iface
     * @return empty proxy - its handler does nothing
     */
    public static <T> T createEmptyProxy(Class<T> iface) {
        return createEmptyProxy(iface.getClassLoader(), iface);
    }

    /**
     * @param <T>
     * @param loader
     * @param iface
     * @return empty proxy - its handler does nothing
     */
    @SuppressWarnings("unchecked")
    public static <T> T createEmptyProxy(ClassLoader loader, Class<T> iface) {
        T proxy = (T) Proxy.newProxyInstance(loader, new Class[] { iface }, new EmptyIG());
        return proxy;
    }

}
