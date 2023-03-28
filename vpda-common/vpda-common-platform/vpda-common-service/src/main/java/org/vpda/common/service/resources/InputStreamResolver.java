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
import java.net.MalformedURLException;

/**
 * InpuStream Resolver will try to resolve Stream. This way we can pass Stream
 * Resolver while real Stream can be resolved context and data specific. We will
 * use Stream resolver in two steps : 1) resolve key 2) try to resolve Stream
 * 
 * @author kitko
 */
public interface InputStreamResolver extends Serializable {
    /**
     * Resolves url key
     * 
     * @return URL
     * @throws IOException
     * @throws ResourceException
     * @throws MalformedURLException when resolving url
     */
    public Object resolveKey() throws IOException, ResourceException;

    /**
     * Resolve all Stream values where we can retrieve resources
     * 
     * @return list of url value
     * @throws IOException
     * @throws ResourceException
     * @throws MalformedURLException
     */
    public InputStream resolveStream() throws IOException, ResourceException;
}
