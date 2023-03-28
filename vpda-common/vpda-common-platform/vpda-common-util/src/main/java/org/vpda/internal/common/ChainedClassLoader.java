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
package org.vpda.internal.common;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Chained ClassLoader
 * 
 * @author kitko
 *
 */
public final class ChainedClassLoader extends ClassLoader {
    private final List<ClassLoader> chain;

    /**
     * @param parent
     * @param chain
     */
    public ChainedClassLoader(ClassLoader parent, List<ClassLoader> chain) {
        super(parent);
        this.chain = new ArrayList<ClassLoader>(chain);
    }

    /**
     * @param parent
     * @param loaders
     */
    public ChainedClassLoader(ClassLoader parent, ClassLoader... loaders) {
        super(parent);
        this.chain = new ArrayList<ClassLoader>(Arrays.asList(loaders));
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        ClassNotFoundException lastE = null;
        for (ClassLoader cl : chain) {
            try {
                return Class.forName(name, false, cl);
            }
            catch (ClassNotFoundException e) {
                lastE = e;
            }
        }
        throw new ClassNotFoundException(name, lastE);
    }

    @Override
    protected URL findResource(String name) {
        for (ClassLoader cl : chain) {
            URL url = cl.getResource(name);
            if (url != null) {
                return url;
            }
        }
        return super.findResource(name);
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        for (ClassLoader cl : chain) {
            Enumeration<URL> elements = cl.getResources(name);
            if (elements != null && elements.hasMoreElements()) {
                return elements;
            }
        }
        return super.findResources(name);
    }

}
