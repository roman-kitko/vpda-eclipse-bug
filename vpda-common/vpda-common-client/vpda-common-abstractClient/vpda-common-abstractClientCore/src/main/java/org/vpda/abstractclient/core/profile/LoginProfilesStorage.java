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
package org.vpda.abstractclient.core.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Storage for login profiles. Implementations of {@link LoginProfileManager}
 * can use this interface to load/store profiles to some permanent storage
 * 
 * @author kitko
 *
 */
public interface LoginProfilesStorage {
    /** Value we store inside the storage */
    public static final class LoginProfilesStorageValue implements Serializable {
        private static final long serialVersionUID = -7144729872704437025L;
        final String currentProfileName;
        final List<AbstractLoginProfile> profiles;

        /**
         * Creates LoginProfilesValue
         * 
         * @param currentProfileName
         * @param profiles
         */
        public LoginProfilesStorageValue(String currentProfileName, List<? extends AbstractLoginProfile> profiles) {
            super();
            this.currentProfileName = currentProfileName;
            this.profiles = new ArrayList<>(profiles);
        }

        /**
         * @return the currentProfileName
         */
        public String getCurrentProfileName() {
            return currentProfileName;
        }

        /**
         * @return the profiles
         */
        public List<AbstractLoginProfile> getProfiles() {
            return Collections.unmodifiableList(profiles);
        }
    }

    /**
     * Load profiles from storage
     * 
     * @return loaded profiles
     * @throws LoginProfileStorageException
     */
    public LoginProfilesStorageValue loadProfiles() throws LoginProfileStorageException;

    /**
     * Store profiles to storage
     * 
     * @param profiles
     * @throws LoginProfileStorageException
     */
    public void storeProfiles(LoginProfilesStorageValue profiles) throws LoginProfileStorageException;

}
