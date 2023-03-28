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
package org.vpda.common.service.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.io.IOUtils;
import org.vpda.common.service.Clearable;
import org.vpda.common.service.ServiceInfo;
import org.vpda.common.service.ServiceKind;

/**
 * Standard implementation of Resource service which loads and cache resources
 * 
 * @author kitko
 *
 */
@ServiceInfo(kind = ServiceKind.Stateless, clientExportTypes = { ResourceService.class })
public final class ResourceServiceImpl implements ResourceService, Clearable, Serializable {
    private static final long serialVersionUID = 465469846649817747L;
    private final ConcurrentMap<Object, byte[]> resourceCache;
    private static final byte[] NOT_FOUND = new byte[1];

    /**
     * Creates ResourceService, use service registry to obtain this service
     */
    public ResourceServiceImpl() {
        resourceCache = new ConcurrentHashMap<Object, byte[]>();
    }

    @Override
    public synchronized byte[] loadResource(LoadResourceRequest loadResourceRequest) throws IOException {
        if (loadResourceRequest.cacheLoadedResource()) {
            return loadResourceCached(loadResourceRequest);
        }
        else {
            return loadResourceUnCached(loadResourceRequest);
        }
    }

    private byte[] loadResourceCached(LoadResourceRequest loadResourceRequest) throws IOException {
        InputStreamResolver streamResolver = loadResourceRequest.getStreamResolver();
        Object key = null;
        if (streamResolver instanceof RelativePathInputStreamResolver && loadResourceRequest.getRelativePath() != null) {
            key = ((RelativePathInputStreamResolver) streamResolver).resolveKey(loadResourceRequest.getRelativePath());
        }
        else {
            key = streamResolver.resolveKey();
        }
        byte[] bytes = resourceCache.get(key);
        if (bytes == NOT_FOUND) {
            return null;
        }
        if (bytes != null) {
            return bytes;
        }
        bytes = loadResourceUnCached(loadResourceRequest);
        if (bytes == null) {
            resourceCache.put(key, NOT_FOUND);
        }
        else {
            resourceCache.put(key, bytes);
        }
        return bytes;
    }

    private byte[] loadResourceUnCached(LoadResourceRequest loadResourceRequest) throws IOException {
        byte[] bytes = null;
        InputStreamResolver streamResolver = loadResourceRequest.getStreamResolver();
        InputStream stream = null;
        if (streamResolver instanceof RelativePathInputStreamResolver && loadResourceRequest.getRelativePath() != null) {
            stream = ((RelativePathInputStreamResolver) streamResolver).resolveStream(loadResourceRequest.getRelativePath());
        }
        else {
            stream = streamResolver.resolveStream();
        }
        if (stream != null) {
            try {
                bytes = IOUtils.toByteArray(stream);
            }
            finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }
        return bytes;
    }

    @Override
    public void clearData() {
        resourceCache.clear();
    }

}
