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
package org.vpda.client.clientcommunication.protocol.rmi;

import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoader;
import java.rmi.server.RMIClassLoaderSpi;

/**
 * Dynamic {@link RMIClassLoaderSpi}. It uses delegate to forward the call
 * 
 * @author kitko
 *
 */
public final class DynamicRMIClassLoaderSpi extends RMIClassLoaderSpi {

    private static RMIClassLoaderSpi delegate = RMIClassLoader.getDefaultProviderInstance();

    /**
     * Sets the delegate
     * 
     * @param delegate
     */
    public static void setDelegate(RMIClassLoaderSpi delegate) {
        DynamicRMIClassLoaderSpi.delegate = delegate;
    }

    /** Creates DynamicRMIClassLoaderSpi */
    public DynamicRMIClassLoaderSpi() {
    }

    @Override
    public Class<?> loadClass(String codebase, String name, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        return delegate.loadClass(codebase, name, defaultLoader);
    }

    @Override
    public Class<?> loadProxyClass(String codebase, String[] interfaces, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        return delegate.loadProxyClass(codebase, interfaces, defaultLoader);
    }

    @Override
    public ClassLoader getClassLoader(String codebase) throws MalformedURLException {
        return delegate.getClassLoader(codebase);
    }

    @Override
    public String getClassAnnotation(Class<?> cl) {
        return delegate.getClassAnnotation(cl);
    }

}
