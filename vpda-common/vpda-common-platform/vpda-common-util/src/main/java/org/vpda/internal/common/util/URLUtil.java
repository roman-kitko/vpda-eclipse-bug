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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.vpda.common.util.exceptions.VPDAConfigurationRuntimeException;

/**
 * URL utils
 * 
 * @author kitko
 *
 */
public final class URLUtil {
    private URLUtil() {
    }

    /**
     * Resolves url
     * 
     * @param path
     * @return URL
     */
    public static URL resolveURL(String path) {
        ClassLoader ctl = Thread.currentThread().getContextClassLoader();
        ClassLoader loader = ctl != null ? ctl : URLUtil.class.getClassLoader();
        return resolveURL(path, loader);
    }

    /**
     * Resolves url
     * 
     * @param path
     * @param loader
     * @return url
     */
    public static URL resolveURL(String path, ClassLoader loader) {
        if (path == null) {
            throw new IllegalArgumentException("path is null");
        }
        URL url = null;
        if (path.startsWith("resource:")) {
            String urlPath = path.substring("resource:".length());
            Optional<URL> urlO = ResourceLoader.resolveResourceFromLoaderAndModules(urlPath, loader, URLUtil.class);
            if (urlO.isPresent()) {
                return urlO.get();
            }
            throw new VPDAConfigurationRuntimeException("Cannot load resource from path : " + path);
        }
        else if (path.startsWith("file:")) {
            String filePath = path.substring("file:".length());
            File file = new File(filePath);
            if (!file.exists()) {
                throw new VPDAConfigurationRuntimeException("File does not exist : " + filePath);
            }
            if (!file.canRead()) {
                throw new VPDAConfigurationRuntimeException("File is not readable : " + filePath);
            }
            try {
                url = file.toURI().toURL();
            }
            catch (MalformedURLException e) {
                throw new VPDAConfigurationRuntimeException("File url error", e);
            }
        }
        else if (path.startsWith("url:")) {
            String urlPath = path.substring("url:".length());
            try {
                url = new URL(urlPath);
            }
            catch (MalformedURLException e) {
                throw new VPDAConfigurationRuntimeException("Url error", e);
            }
        }
        else {
            // It could be normal file
            File file = new File(path);
            if (file.exists() && file.canRead()) {
                try {
                    url = file.toURI().toURL();
                }
                catch (MalformedURLException e) {
                    throw new VPDAConfigurationRuntimeException("Url error", e);
                }
            }
            if (url == null) {
                url = loader.getResource(path);
            }
            if (url == null) {
                try {
                    url = new URL(path);
                }
                catch (MalformedURLException e) {
                    throw new VPDAConfigurationRuntimeException("Url error", e);
                }
            }
        }
        try (InputStream is = url.openStream()) {
        }
        catch (IOException e) {
            throw new VPDAConfigurationRuntimeException(e);
        }
        return url;

    }

    /**
     * Creates relative url
     * 
     * @param ctx
     * @param subPath
     * @return relative URL
     * @throws MalformedURLException
     */
    public static URL createRelativeURL(URL ctx, String subPath) throws MalformedURLException {
        if (StringUtil.isEmpty(subPath)) {
            return ctx;
        }
        if ("vfs".equals(ctx.getProtocol())) {
            String externalForm = ctx.toExternalForm();
            if (externalForm.startsWith("vfs://:0/content")) {
                externalForm = externalForm.replaceFirst("vfs://:0/content", "vfs:/content");
            }
            if (externalForm.charAt(externalForm.length() - 1) == '/') {
                if (subPath.charAt(0) == '/') {
                    return new URL(externalForm + subPath.substring(1));
                }
                return new URL(externalForm + subPath);
            }
            if (subPath.charAt(0) == '/') {
                return new URL(externalForm + subPath);
            }
            return new URL(externalForm + '/' + subPath);
        }
        return new URL(ctx, subPath);
    }
}
