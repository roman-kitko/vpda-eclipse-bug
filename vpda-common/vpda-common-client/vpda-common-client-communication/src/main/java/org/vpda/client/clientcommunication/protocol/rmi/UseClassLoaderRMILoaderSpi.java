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

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoaderSpi;
import java.util.Arrays;

import org.vpda.internal.common.util.Assert;

final class UseClassLoaderRMILoaderSpi extends RMIClassLoaderSpi {

    private final ClassLoader loader;

    UseClassLoaderRMILoaderSpi(ClassLoader rmiLoader) {
        this.loader = Assert.isNotNullArgument(rmiLoader, "loader");
    }

    @Override
    public Class<?> loadClass(String codebase, String name, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        ClassNotFoundException lastE = null;
        try {
            return Class.forName(name, false, loader);
        }
        catch (ClassNotFoundException e) {
            lastE = e;
        }
        if (defaultLoader != null && defaultLoader != loader) {
            return Class.forName(name, false, defaultLoader);
        }
        throw new ClassNotFoundException(name, lastE);
    }

    @Override
    public Class<?> loadProxyClass(String codebase, String[] interfaces, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        ClassNotFoundException lastE = null;
        Class[] ifcClasses = new Class[interfaces.length];
        try {
            for (int i = 0; i < interfaces.length; i++) {
                ifcClasses[i] = loader.loadClass(interfaces[0]);
            }
            return Proxy.getProxyClass(loader, ifcClasses);
        }
        catch (ClassNotFoundException e) {
            lastE = e;
        }
        if (defaultLoader != null) {
            for (int i = 0; i < interfaces.length; i++) {
                ifcClasses[i] = defaultLoader.loadClass(interfaces[0]);
            }
            return Proxy.getProxyClass(defaultLoader, ifcClasses);
        }
        throw new ClassNotFoundException(Arrays.toString(interfaces), lastE);
    }

    @Override
    public ClassLoader getClassLoader(String codebase) throws MalformedURLException {
        return loader;
    }

    @Override
    public String getClassAnnotation(Class<?> cl) {
        return "rmiLoader";
    }

}
