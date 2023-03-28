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
import java.net.MalformedURLException;

/**
 * Stream resolver that also holds some path information
 * 
 * @author kitko
 *
 */
public interface RelativePathInputStreamResolver extends InputStreamResolver {
    /**
     * Resolves url key
     * 
     * @param relativePath
     * @return URL
     * @throws IOException
     * @throws ResourceException
     * @throws MalformedURLException when resolving url
     */
    public Object resolveKey(String relativePath) throws IOException, ResourceException;

    /**
     * Resolve all Stream values where we can retrieve resources
     * 
     * @param relativePath
     * @return list of url value
     * @throws IOException
     * @throws ResourceException
     * @throws MalformedURLException
     */
    public InputStream resolveStream(String relativePath) throws IOException, ResourceException;
}
