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
package org.vpda.common.service.localization;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.PropertiesUtil;
import org.vpda.internal.common.util.StringUtil;

/**
 * This resolver will try to read localization entries from properties files.
 * Properties files must be in sub folders of MODULE/loc
 * 
 * @author kitko
 *
 */
public final class URLsLocalizationValueResolver implements LocalizationValueResolver, Serializable {
    private static final long serialVersionUID = -1085157615598422412L;
    private final List<URL> sourceURLs;
    private transient FileSystemManager vfsManager;
    private final static String NOT_FOUND_VALUE = "notFound";
    private final static String ALREADY_SCANNED = "alreadyScanned";
    private final URLsLocalizationValueResolverCache cache;

    /**
     * Creates ModulesLocalizationValueResolver
     * 
     * @param sourceURLs
     * @param cache
     */
    public URLsLocalizationValueResolver(List<URL> sourceURLs, URLsLocalizationValueResolverCache cache) {
        if (sourceURLs == null) {
            throw new IllegalArgumentException("SourceURL argument is null");
        }
        this.sourceURLs = new ArrayList<URL>(sourceURLs);
        this.cache = cache != null ? cache : new EHCacheLikeURLsLocalizationValueResolverCache(LocalizationServiceImpl.DEF_LOC_CACHE_MANAGER_NAME, "LOCALIZATION-FILES");
        try {
            vfsManager = VFS.getManager();
        }
        catch (FileSystemException e) {
            throw new VPDARuntimeException("Cannot create FileSystemManager", e);
        }
    }

    /**
     * @param sourceURLs
     */
    public URLsLocalizationValueResolver(List<URL> sourceURLs) {
        this(sourceURLs, null);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        try {
            vfsManager = VFS.getManager();
        }
        catch (FileSystemException e) {
            throw new VPDARuntimeException("Cannot create FileSystemManager", e);
        }
    }

    @Override
    public String resolveValue(LocKey key, TenementalContext context) {
        Locale locale = context.getLocale();
        String value = resolveValueForLocale(key, locale);
        if (value == null && !Locale.US.equals(locale)) {
            value = resolveValueForLocale(key, Locale.US);
        }
        return value;
    }

    private synchronized String resolveValueForLocale(LocKey key, Locale locale) {
        URLLocCacheKey cacheKey = new URLLocCacheKey(key.getPath(), locale);
        String value = resolveCachedValue(cacheKey);
        if (value != null) {
            if (NOT_FOUND_VALUE.equals(value)) {
                return null;
            }
            return value;
        }
        LocKey tmp = key;
        while (tmp.hasParrent()) {
            tmp = tmp.createParrentKey();
            if (cache.getData(new URLLocCacheKey(tmp.getPath(), locale)) == ALREADY_SCANNED) {
                cache.putData(cacheKey, NOT_FOUND_VALUE);
                return null;
            }
        }
        // Nothing in cache, so resolve file
        try {
            searchFilesAndFillCache(key, locale, locale);
            if (StringUtil.isNotEmpty(locale.getVariant())) {
                searchFilesAndFillCache(key, new Locale(locale.getLanguage(), locale.getCountry()), locale);
            }
            if (StringUtil.isNotEmpty(locale.getCountry())) {
                searchFilesAndFillCache(key, new Locale(locale.getLanguage()), locale);
            }
        }
        catch (MalformedURLException e) {
            throw new VPDARuntimeException("Cannot search for localization file", e);
        }
        catch (IOException e) {
            throw new VPDARuntimeException("Cannot search for localization file", e);
        }
        value = resolveCachedValue(cacheKey);
        if (value == null) {
            cache.putData(cacheKey, NOT_FOUND_VALUE);
        }
        return value;
    }

    /**
     * Search for resource files
     * 
     * @param key
     * @param string
     * @throws MalformedURLException
     * @throws IOException
     */
    private void searchFilesAndFillCache(LocKey key, Locale locale, Locale requestedLocale) throws MalformedURLException, IOException {
        for (URL url : sourceURLs) {
            StringBuilder pathBuilder = new StringBuilder();
            URL prefix = url;
            for (String pathPart : key.getPathParts()) {
                pathBuilder.append(pathPart).append('/');
                URL propertyFileURL = new URL(prefix, pathPart + "_" + locale + ".properties");
                prefix = new URL(prefix, pathPart + '/');
                FileObject folder = vfsManager.resolveFile(prefix.toExternalForm());
                FileObject propertyFile = vfsManager.resolveFile(propertyFileURL.toExternalForm());
                if (propertyFile.exists()) {
                    readAndCachePropertyFile(key, locale, propertyFile, pathBuilder.toString(), requestedLocale);
                    cache.putData(new URLLocCacheKey(pathBuilder.toString(), locale), ALREADY_SCANNED);
                    if (!locale.equals(requestedLocale)) {
                        cache.putData(new URLLocCacheKey(pathBuilder.toString(), requestedLocale), ALREADY_SCANNED);
                    }
                }
                else if (!folder.exists()) {
                    // We have no file/folder, go to next URL
                    break;
                }
            }
        }
    }

    private void readAndCachePropertyFile(LocKey key, Locale locale, FileObject propertyFile, String prefixPath, Locale requestedLocale) throws IOException {
        try (InputStream propertyStream = propertyFile.getContent().getInputStream()) {
            Properties properties = PropertiesUtil.readPropertiesFromStream(propertyStream);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String k = (String) entry.getKey();
                String value = (String) entry.getValue();
                k = k.replace('.', '/');
                URLLocCacheKey cacheKey = new URLLocCacheKey(prefixPath + k, locale);
                cache.putData(cacheKey, value);
                if (!requestedLocale.equals(locale)) {
                    cache.putIfAbsent(new URLLocCacheKey(prefixPath + k, requestedLocale), value);
                }
            }
        }
    }

    private String resolveCachedValue(URLLocCacheKey cacheKey) {
        String value = cache.getData(cacheKey);
        if (value == null) {
            if (StringUtil.isNotEmpty(cacheKey.getLocale().getVariant())) {
                value = cache.getData(new URLLocCacheKey(cacheKey.getPath(), new Locale(cacheKey.getLocale().getLanguage(), cacheKey.getLocale().getCountry())));
            }
        }
        if (value == null) {
            if (StringUtil.isNotEmpty(cacheKey.getLocale().getCountry())) {
                value = cache.getData(new URLLocCacheKey(cacheKey.getPath(), new Locale(cacheKey.getLocale().getLanguage())));
            }
        }
        return value;
    }
}
