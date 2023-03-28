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
/**
 * 
 */
package org.vpda.abstractclient.core.profile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import org.apache.commons.io.IOUtils;

/**
 * Load/Store profiles to filesystem
 * 
 * @author kitko
 *
 */
public final class FileSystemXStreamLoginProfilesStorage implements LoginProfilesStorage {
    private final File file;
    private final XStreamLoginProfilesStorageHelper helper;

    /**
     * Creates FileSystemXStreamLoginProfilesStorage
     * 
     * @param file
     * @param helper
     */
    public FileSystemXStreamLoginProfilesStorage(File file, XStreamLoginProfilesStorageHelper helper) {
        this.file = file;
        this.file.mkdirs();
        this.helper = helper;
    }

    /**
     * Creates FileSystemXStreamLoginProfilesStorage
     */
    public FileSystemXStreamLoginProfilesStorage() {
        File dir = new File(System.getProperty("user.home") + File.separatorChar + ".vpda" + File.separatorChar + "swingClient");
        dir.mkdirs();
        this.file = new File(dir, "login.profiles");
        this.helper = new XStreamLoginProfilesStorageHelper();
    }

    @Override
    public LoginProfilesStorageValue loadProfiles() throws LoginProfileStorageException {
        if (!file.canRead()) {
            return new LoginProfilesStorageValue(null, Collections.emptyList());
        }
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));
            return helper.loadFromStream(stream);
        }
        catch (IOException e) {
            throw new LoginProfileStorageException("Error loading from stream", e);
        }
        finally {
            IOUtils.closeQuietly(stream);
        }
    }

    @Override
    public void storeProfiles(LoginProfilesStorageValue profiles) throws LoginProfileStorageException {
        OutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(file));
            helper.storeToStream(profiles, stream);
        }
        catch (IOException e) {
            throw new LoginProfileStorageException("Error storing to stream", e);
        }
        finally {
            IOUtils.closeQuietly(stream);
        }

    }

}
