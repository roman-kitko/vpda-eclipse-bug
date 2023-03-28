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

import java.io.Serializable;

/**
 * Default impl of LoadResourceRequest
 * 
 * @author kitko
 *
 */
public final class LoadResourceRequest implements Serializable {
    private static final long serialVersionUID = -112487467335592709L;
    private final boolean cacheResource;
    private final InputStreamResolver uRLResolver;
    private final String relativePath;

    /**
     * @return true if we should cache loaded resource
     */
    public boolean cacheLoadedResource() {
        return cacheResource;
    }

    /**
     * @return url resolver for this request
     */
    public InputStreamResolver getStreamResolver() {
        return uRLResolver;
    }

    /**
     * @param cacheResource
     * @param uRLResolver
     */
    public LoadResourceRequest(InputStreamResolver uRLResolver, boolean cacheResource) {
        this(uRLResolver, cacheResource, null);
    }

    /**
     * @param uRLResolver
     * @param cacheResource
     * @param relativePath
     */
    public LoadResourceRequest(InputStreamResolver uRLResolver, boolean cacheResource, String relativePath) {
        if (uRLResolver == null) {
            throw new IllegalArgumentException("uRLResolver argument is null");
        }
        this.uRLResolver = uRLResolver;
        this.cacheResource = cacheResource;
        this.relativePath = relativePath;
    }

    /**
     * @return relative path
     */
    public String getRelativePath() {
        return relativePath;
    }

}
