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
package org.vpda.client.clientcommunication;

import java.io.IOException;

import org.vpda.clientserver.communication.data.LoginInfo;
import org.vpda.clientserver.communication.services.ClientServerClassLoader;

/**
 * ClassLoader that uses Login server to find class bytes
 * 
 * @author kitko
 *
 */
public final class LoginServerClassLoader extends ClassLoader {
    private final ClientServerClassLoader clientServerClassLoader;
    private final LoginInfo loginInfo;

    /**
     * @param clientServerClassLoader
     * @param loginInfo
     * @param parent
     */
    public LoginServerClassLoader(ClientServerClassLoader clientServerClassLoader, LoginInfo loginInfo, ClassLoader parent) {
        super(parent);
        this.clientServerClassLoader = clientServerClassLoader;
        this.loginInfo = loginInfo;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        try {
            bytes = clientServerClassLoader.loadClassBytes(loginInfo, name);
        }
        catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
        if (bytes != null) {
            return super.defineClass(name, bytes, 0, bytes.length);
        }
        throw new ClassNotFoundException(name);
    }

}
