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
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Resolver of output stream is responsible for resolving outputstream in any
 * way. This way we can pass some bytes to server and this resolver will create
 * concrete outputstream
 * 
 * @author kitko
 *
 */
public interface OutputStreamResolver extends Serializable {

    /**
     * Resolves stream
     * 
     * @return stream
     * @throws IOException
     * @throws ResourceException
     */
    public OutputStream resolveStream() throws IOException, ResourceException;
}
