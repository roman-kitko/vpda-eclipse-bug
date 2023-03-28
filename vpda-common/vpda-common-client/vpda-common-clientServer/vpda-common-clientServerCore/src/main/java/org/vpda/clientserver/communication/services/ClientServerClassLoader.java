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
package org.vpda.clientserver.communication.services;

import java.io.IOException;

import org.vpda.clientserver.communication.data.LoginInfo;

/**
 * Publish class bytes for client
 * 
 * @author kitko
 *
 */
public interface ClientServerClassLoader {
    /**
     * Resolves classpath url needed for client
     * 
     * @param loginInfo
     * @return classpath url
     */
    public String getClassPathURL(LoginInfo loginInfo);

    /**
     * Gets bytes for class
     * 
     * @param loginInfo
     * @param name
     * @return bytes for class or null if not found
     * @throws IOException
     */
    public byte[] loadClassBytes(LoginInfo loginInfo, String name) throws IOException;
}
