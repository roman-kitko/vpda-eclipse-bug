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

import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ClassUtil;

final class ForceCodebaseRMIClassLoaderSpi extends RMIClassLoaderSpi {
    private final String codebase;
    private final RMIClassLoaderSpi delegate;

    ForceCodebaseRMIClassLoaderSpi(String codebase, RMIClassLoaderSpi delegate) {
        this.codebase = Assert.isNotEmptyArgument(codebase, "codebase");
        this.delegate = Assert.isNotNullArgument(delegate, "delegate");
    }

    ForceCodebaseRMIClassLoaderSpi(String codebase) {
        this.codebase = Assert.isNotEmptyArgument(codebase, "codebase");
        this.delegate = RMIClassLoader.getDefaultProviderInstance();
    }

    @Override
    public Class<?> loadClass(String codebase, String name, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        try {
            return ClassUtil.loadBySystemLoader(name);
        }
        catch (ClassNotFoundException e) {
        }
        return delegate.loadClass(this.codebase, name, defaultLoader);
    }

    @Override
    public Class<?> loadProxyClass(String codebase, String[] interfaces, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        return delegate.loadProxyClass(this.codebase, interfaces, defaultLoader);
    }

    @Override
    public ClassLoader getClassLoader(String codebase) throws MalformedURLException {
        return delegate.getClassLoader(this.codebase);
    }

    @Override
    public String getClassAnnotation(Class<?> cl) {
        return codebase;
    }

}
