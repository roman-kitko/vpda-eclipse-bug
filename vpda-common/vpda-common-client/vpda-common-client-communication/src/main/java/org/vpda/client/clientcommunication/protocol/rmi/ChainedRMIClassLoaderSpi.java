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
import java.rmi.server.RMIClassLoaderSpi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class ChainedRMIClassLoaderSpi extends RMIClassLoaderSpi {
    private final List<RMIClassLoaderSpi> chain;

    ChainedRMIClassLoaderSpi(List<RMIClassLoaderSpi> chain) {
        this.chain = new ArrayList<RMIClassLoaderSpi>(chain);
    }

    ChainedRMIClassLoaderSpi(RMIClassLoaderSpi... spis) {
        this.chain = new ArrayList<RMIClassLoaderSpi>(Arrays.asList(spis));
    }

    @Override
    public Class<?> loadClass(String codebase, String name, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        ClassNotFoundException lastE = null;
        for (RMIClassLoaderSpi spi : chain) {
            try {
                return spi.loadClass(codebase, name, defaultLoader);
            }
            catch (ClassNotFoundException e) {
                lastE = e;
            }
        }
        throw new ClassNotFoundException(name, lastE);
    }

    @Override
    public Class<?> loadProxyClass(String codebase, String[] interfaces, ClassLoader defaultLoader) throws MalformedURLException, ClassNotFoundException {
        ClassNotFoundException lastE = null;
        for (RMIClassLoaderSpi spi : chain) {
            try {
                return spi.loadProxyClass(codebase, interfaces, defaultLoader);
            }
            catch (ClassNotFoundException e) {
                lastE = e;
            }
        }
        throw new ClassNotFoundException(Arrays.toString(interfaces), lastE);
    }

    @Override
    public ClassLoader getClassLoader(String codebase) throws MalformedURLException {
        MalformedURLException lastE = null;
        for (RMIClassLoaderSpi spi : chain) {
            try {
                return spi.getClassLoader(codebase);
            }
            catch (MalformedURLException e) {
                lastE = e;
            }
        }
        throw lastE;
    }

    @Override
    public String getClassAnnotation(Class<?> cl) {
        return null;
    }

}
