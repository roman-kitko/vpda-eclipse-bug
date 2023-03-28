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
package org.vpda.abstractclient.core.profile.impl;

import java.io.IOException;
import java.util.Collections;

import org.vpda.abstractclient.core.profile.LoginProfileStorageException;
import org.vpda.abstractclient.core.profile.LoginProfilesStorage;
import org.vpda.internal.common.util.JavaSerializationUtil;

final class ByteArrayLoginProfilesStorage implements LoginProfilesStorage {
    private byte[] bytes;

    ByteArrayLoginProfilesStorage() {
    }

    public ByteArrayLoginProfilesStorage(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public LoginProfilesStorageValue loadProfiles() throws LoginProfileStorageException {
        if (bytes == null) {
            return new LoginProfilesStorageValue(null, Collections.emptyList());
        }
        try {
            return (LoginProfilesStorageValue) JavaSerializationUtil.readObjectFromByteArray(bytes);
        }
        catch (ClassNotFoundException | IOException e) {
            throw new LoginProfileStorageException("Canot read LoginProfilesStorageValue from byte array", e);
        }
    }

    @Override
    public void storeProfiles(LoginProfilesStorageValue profiles) throws LoginProfileStorageException {
        try {
            bytes = JavaSerializationUtil.serializeObjectToByteArray(profiles);
        }
        catch (IOException e) {
            throw new LoginProfileStorageException("Canot store LoginProfilesStorageValue to byte array", e);
        }

    }

    byte[] getBytes() {
        return bytes;
    }

}
