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

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author kitko
 *
 */
public final class ProxyUtil {

    private ProxyUtil() {
    }

    /**
     * Creates delegating proxy
     * 
     * @param <T>
     * @param loader
     * @param type
     * @param impl
     * @return proxy that delagates all calls to passsed impl
     */
    public static <T> T createDelegatingProxy(ClassLoader loader, Class<T> type, T impl) {
        return type.cast(Proxy.newProxyInstance(loader, new Class[] { type }, new DelegatingInvocationhandler<T>(impl)));
    }

    private static final class DelegatingInvocationhandler<T> implements InvocationHandler, Serializable {
        private static final long serialVersionUID = -8649251513353385028L;
        private final T impl;

        private DelegatingInvocationhandler(T impl) {
            this.impl = impl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                return method.invoke(impl, args);
            }
            catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }

    }

}
