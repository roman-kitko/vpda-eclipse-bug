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
package org.vpda.internal.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.vpda.common.util.logging.LoggerMethodTracer;

/**
 * Load bytes from resource.
 * 
 * @author kitko
 *
 */
public final class ClassBytesResolver {

    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(ClassBytesResolver.class);
    private final ConcurrentMap<String, byte[]> classCache = new ConcurrentHashMap<String, byte[]>(1000);
    private static final byte[] NOT_EXISTS = new byte[0];

    /**
     * Loads class bytes
     * 
     * @param resName
     * @param classLoader
     * @return class bytes or null if not found
     * @throws IOException
     */
    public byte[] loadClassBytes(String resName, ClassLoader classLoader) throws IOException {
        byte[] bytes = classCache.get(resName);
        if (bytes == NOT_EXISTS) {
            return null;
        }
        if (bytes != null) {
            return bytes;
        }
        String resourceName = resName;
        resourceName = resourceName.replace('.', '/') + ".class";
        URL url = classLoader.getResource(resourceName);
        if (url != null) {
            return putIntoCache(resName, url);
        }
        else {
            logger.warning("Cannot find class resource for url : " + resName);
            classCache.putIfAbsent(resName, NOT_EXISTS);
        }
        return null;

    }

    private byte[] putIntoCache(String resName, URL url) throws IOException {
        try(InputStream is = url.openStream()){
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            is.transferTo(bos);
            byte[] bytes = bos.toByteArray();
            classCache.putIfAbsent(resName, bytes);
            return bytes;
        }
    }

    /**
     * Loads class bytes
     * 
     * @param resName
     * @param classLoaders
     * @return class bytes or null if not found
     * @throws IOException
     */
    public byte[] loadClassBytes(String resName, ClassLoader... classLoaders) throws IOException {
        byte[] bytes = classCache.get(resName);
        if (bytes == NOT_EXISTS) {
            return null;
        }
        if (bytes != null) {
            return bytes;
        }
        String resourceName = resName;
        resourceName = resourceName.replace('.', '/') + ".class";
        URL url = null;
        for (ClassLoader classLoader : classLoaders) {
            url = classLoader.getResource(resourceName);
            if (url != null) {
                break;
            }
        }
        if (url != null) {
            return putIntoCache(resName, url);
        }
        else {
            logger.warning("Cannot find class resource for url : " + resName);
            classCache.putIfAbsent(resName, NOT_EXISTS);
        }
        return null;

    }

    /**
     * Loads class bytes
     * 
     * @param resName
     * @return class bytes or null if not found
     * @throws IOException
     */
    public byte[] loadClassBytes(String resName) throws IOException {
        ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
        if (ctxClassLoader == null || ctxClassLoader == getClass().getClassLoader()) {
            return loadClassBytes(resName, getClass().getClassLoader());
        }
        else if (ctxClassLoader != null && ctxClassLoader != getClass().getClassLoader()) {
            return loadClassBytes(resName, ctxClassLoader, getClass().getClassLoader());
        }
        return loadClassBytes(resName, getClass().getClassLoader());
    }

}
