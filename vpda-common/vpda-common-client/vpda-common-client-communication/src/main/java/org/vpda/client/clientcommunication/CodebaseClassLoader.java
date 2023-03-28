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
/**
 * 
 */
package org.vpda.client.clientcommunication;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Will get classes from codebase
 * 
 * @author kitko
 *
 */
public final class CodebaseClassLoader extends ClassLoader {

    private final List<Proxy> proxies;
    private final List<URL> urls;

    /**
     * @param codebase
     * @param proxies
     * @param parent
     */
    public CodebaseClassLoader(String codebase, List<Proxy> proxies, ClassLoader parent) {
        super(parent);
        urls = new ArrayList<URL>(2);
        StringTokenizer tokenizer = new StringTokenizer(codebase);
        while (tokenizer.hasMoreTokens()) {
            String url = tokenizer.nextToken();
            try {
                urls.add(new URL(url));
            }
            catch (MalformedURLException e) {
                throw new VPDARuntimeException(e);
            }
        }
        this.proxies = new ArrayList<Proxy>(proxies);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        for (URL url : urls) {
            URL classURL = null;
            try {
                classURL = new URL(url, name.replace('.', '/') + ".class");
            }
            catch (MalformedURLException e) {
                throw new ClassNotFoundException(name, e);
            }
            if (proxies.isEmpty()) {
                try {
                    return defineClass(classURL.openConnection(), name);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                for (Proxy proxy : proxies) {
                    try {
                        return defineClass(classURL.openConnection(proxy), name);
                    }
                    catch (IOException e) {
                    }
                }
            }
        }
        throw new ClassNotFoundException(name);
    }

    private Class<?> defineClass(URLConnection connection, String name) throws IOException {
        InputStream stream = null;
        try {
            stream = connection.getInputStream();
            byte[] bytes = IOUtils.toByteArray(stream);
            Class<?> clazz = super.defineClass(name, bytes, 0, bytes.length);
            return clazz;
        }
        finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

}
