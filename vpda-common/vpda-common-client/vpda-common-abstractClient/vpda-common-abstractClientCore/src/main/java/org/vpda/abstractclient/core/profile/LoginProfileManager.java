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

import java.util.List;
import java.util.Map;

/**
 * Manager for login profiles
 * 
 * @author kitko
 *
 */
public interface LoginProfileManager {

    /**
     * Test whether manager contains specified profile by its name
     * 
     * @param profileName
     * @return true if manager contains profile with the name
     */
    public boolean containsProfile(String profileName);

    /**
     * Get profile by name
     * 
     * @param profileName
     * @return Profile by name
     */
    public AbstractLoginProfile getProfile(String profileName);

    /**
     * Remove profile
     * 
     * @param profileName
     * @return removed profile
     */
    public AbstractLoginProfile removeProfile(String profileName);

    /**
     * Add profile by its name
     * 
     * @param profile
     */
    public void addProfile(AbstractLoginProfile profile);

    /**
     * Update the profile
     * 
     * @param profile
     * @return updated profile or null if not registered before
     */
    public AbstractLoginProfile updateProfile(AbstractLoginProfile profile);

    /**
     * Renames profile
     * 
     * @param oldName
     * @param newName
     * @return renamed profile if registered before, otherwise returns null
     */
    public AbstractLoginProfile renameProfile(String oldName, String newName);

    /**
     * @return list of all profiles
     */
    public List<String> getProfileNames();

    /**
     * @return count
     */
    public int getProfilesCount();

    /**
     * @param i
     * @return profile at i position
     */
    public AbstractLoginProfile getProfile(int i);

    /**
     * @return profiles mapped by name
     */
    public Map<String, AbstractLoginProfile> getProfilesByNames();

    /**
     * Load profiles from storage
     * 
     * @throws LoginProfileStorageException
     */
    public void loadProfiles() throws LoginProfileStorageException;

    /**
     * Store profiles to storage
     * 
     * @throws LoginProfileStorageException
     */
    public void storeProfiles() throws LoginProfileStorageException;

    /**
     * @return current profile
     */
    public AbstractLoginProfile getCurrentProfile();

    /**
     * @return default profile
     */
    public AbstractLoginProfile getDefaultProfile();

    /**
     * Sets current profile
     * 
     * @param profile
     */
    public void setCurrentProfile(AbstractLoginProfile profile);

    /**
     * @return possible values
     */
    public LoginProfilePossibleValues getPossibleValues();

    /**
     * @param profile
     * @return true if profile name be removed
     */
    public boolean canRemoveProfile(AbstractLoginProfile profile);

    /**
     * 
     * @param profile
     * @return true if profile can be updated
     */
    public boolean canUpdateProfile(AbstractLoginProfile profile);

    /**
     * 
     * @param profile
     * @return true if profile can be renamed
     */
    public boolean canRenameProfile(AbstractLoginProfile profile);

}
