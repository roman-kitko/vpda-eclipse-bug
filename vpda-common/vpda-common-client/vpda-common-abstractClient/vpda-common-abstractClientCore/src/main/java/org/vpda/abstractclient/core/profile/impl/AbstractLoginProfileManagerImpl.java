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
package org.vpda.abstractclient.core.profile.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.vpda.abstractclient.core.profile.AbstractLoginProfile;
import org.vpda.abstractclient.core.profile.AbstractLoginProfile.Builder;
import org.vpda.abstractclient.core.profile.LoginProfileConstants;
import org.vpda.abstractclient.core.profile.LoginProfileManager;
import org.vpda.abstractclient.core.profile.LoginProfilePossibleValues;
import org.vpda.abstractclient.core.profile.LoginProfileStorageException;
import org.vpda.abstractclient.core.profile.LoginProfilesStorage;
import org.vpda.abstractclient.core.profile.LoginProfilesStorage.LoginProfilesStorageValue;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ObjectUtil;
import org.vpda.internal.common.util.OrderedMap;

/**
 * Login profile manager for swing platform. Now it does not provide any gui
 * support, but later nice dialog with table of available profiles will be
 * created.
 * 
 * @author kitko
 *
 */
public abstract class AbstractLoginProfileManagerImpl implements LoginProfileManager {
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(AbstractLoginProfileManagerImpl.class.getName());
    private OrderedMap<String, AbstractLoginProfile> profiles;
    private OrderedMap<String, AbstractLoginProfile> loadedProfiles;
    private AbstractLoginProfile currentProfile;
    /** Default profile */
    protected final AbstractLoginProfile defaultProfile;
    /** All possible values profile */
    protected final LoginProfilePossibleValues possibleValues;
    /** Profile storage */
    protected final LoginProfilesStorage storage;

    /**
     * Creates AbstractLoginProfileManagerImpl
     * 
     * @param possibleValues
     * @param storage
     */
    protected AbstractLoginProfileManagerImpl(LoginProfilePossibleValues possibleValues, LoginProfilesStorage storage) {
        profiles = new OrderedMap<String, AbstractLoginProfile>(2);
        loadedProfiles = new OrderedMap<>();
        this.possibleValues = possibleValues;
        this.storage = Assert.isNotNullArgument(storage, "storage");
        this.defaultProfile = createDefaultProfile();
        addProfile(defaultProfile);
        @SuppressWarnings("unchecked")
        Builder<AbstractLoginProfile> builder = (Builder<AbstractLoginProfile>) createProfileBuilder();
        builder.setValues(defaultProfile);
        builder.setProfileName(LoginProfileConstants.CURRENT_PROFILE_NAME);
        setCurrentProfile(builder.build());
    }

    /**
     * Create new profile instance
     * 
     * @return new profile
     */
    protected abstract AbstractLoginProfile.Builder<? extends AbstractLoginProfile> createProfileBuilder();

    /**
     * Creates default profile
     * 
     * @return default profile
     */
    protected AbstractLoginProfile createDefaultProfile() {
        AbstractLoginProfile.Builder<? extends AbstractLoginProfile> builder = createProfileBuilder();
        builder.setProfileName(LoginProfileConstants.DEFAULT_PROFILE_NAME);
        builder.setBindingName(null);
        builder.setCommunicationProtocol(!possibleValues.getAvailableCommunicationProtocols().isEmpty() ? possibleValues.getAvailableCommunicationProtocols().get(0) : null);
        builder.setLocale(!possibleValues.getAvailableLocales().isEmpty() ? possibleValues.getAvailableLocales().get(0) : null);
        builder.setServer(!possibleValues.getAvailableServers().isEmpty() ? possibleValues.getAvailableServers().get(0) : null);
        builder.setContextPolicy(!possibleValues.getAvailableContextPolicies().isEmpty() ? possibleValues.getAvailableContextPolicies().get(0) : null);
        return builder.build();
    }

    @Override
    public boolean containsProfile(String profileName) {
        return profiles.containsKey(profileName);
    }

    @Override
    public void addProfile(AbstractLoginProfile profile) {
        profile = adjustProfileValues(profile);
        profiles.put(profile.getProfileName(), profile);
    }

    @Override
    public AbstractLoginProfile getCurrentProfile() {
        return currentProfile;
    }

    @Override
    public AbstractLoginProfile getDefaultProfile() {
        return defaultProfile;
    }

    @Override
    public AbstractLoginProfile getProfile(String profileName) {
        AbstractLoginProfile p = profiles.get(profileName);
        return p;
    }

    @Override
    public List<String> getProfileNames() {
        return profiles.keys();
    }

    @Override
    public int getProfilesCount() {
        return profiles.size();
    }

    @Override
    public AbstractLoginProfile getProfile(int i) {
        return profiles.getValue(i);
    }

    @Override
    public Map<String, AbstractLoginProfile> getProfilesByNames() {
        return Collections.unmodifiableMap(profiles);
    }

    @Override
    public void loadProfiles() throws LoginProfileStorageException {
        LoginProfilesStorageValue value = storage.loadProfiles();
        if (value == null) {
            return;
        }
        for (AbstractLoginProfile profile : value.getProfiles()) {
            profile = adjustProfileValues(profile);
            this.profiles.put(profile.getProfileName(), profile);
            if (profile.getProfileName().equals(value.getCurrentProfileName())) {
                this.currentProfile = profile;
            }
        }
        loadedProfiles = new OrderedMap<>(this.profiles);
    }

    private AbstractLoginProfile adjustProfileValues(AbstractLoginProfile profile) {
        if (profile.getTimestamp() == null) {
            profile = profile.createBuilderWithSameValues().setTimestamp(new Timestamp(System.currentTimeMillis())).build();
        }
        return profile;
    }

    @Override
    public AbstractLoginProfile removeProfile(String profileName) {
        AbstractLoginProfile p = profiles.get(profileName);
        if (p != null) {
            if (!canRemoveProfile(p)) {
                throw new IllegalStateException("Cannot remove profile");
            }
            AbstractLoginProfile removed = profiles.remove(profileName);
            if (removed.getProfileName().equals(currentProfile.getProfileName())) {
                currentProfile = defaultProfile;
            }
            return removed;
        }
        return null;
    }

    @Override
    public void storeProfiles() throws LoginProfileStorageException {
        OrderedMap<String, AbstractLoginProfile> profilesToStore = new OrderedMap<String, AbstractLoginProfile>(this.profiles);
        @SuppressWarnings("unchecked")
        AbstractLoginProfile.Builder<AbstractLoginProfile> builder = (Builder<AbstractLoginProfile>) createProfileBuilder();
        AbstractLoginProfile current = this.profiles.get(LoginProfileConstants.CURRENT_PROFILE_NAME);
        if (current == null) {
            current = currentProfile;
        }
        AbstractLoginProfile lastProfile = builder.setValues(current).setProfileName(LoginProfileConstants.LAST_PROFILE_NAME).build();
        if (profiles.get(LoginProfileConstants.LAST_PROFILE_NAME) != null) {
            lastProfile = lastProfile.createBuilderWithSameValues().setTimestamp(profiles.get(LoginProfileConstants.LAST_PROFILE_NAME).getTimestamp()).build();
        }
        profilesToStore.put(lastProfile.getProfileName(), lastProfile);
        profilesToStore.remove(LoginProfileConstants.DEFAULT_PROFILE_NAME);
        profilesToStore.remove(LoginProfileConstants.CURRENT_PROFILE_NAME);

        Map<String, AbstractLoginProfile> storedProfiles = createProfileMap(storage.loadProfiles().getProfiles());
        Map<String, AbstractLoginProfile> conflictProfiles = new LinkedHashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        for (Iterator<Map.Entry<String, AbstractLoginProfile>> it = profilesToStore.entrySet().iterator(); it.hasNext();) {
            AbstractLoginProfile profile = it.next().getValue();
            AbstractLoginProfile currentlyStored = storedProfiles.get(profile.getProfileName());
            AbstractLoginProfile loadedProfile = loadedProfiles.get(profile.getProfileName());
            // If it is not stored, maybe it was removed
            if (currentlyStored == null) {
                if (loadedProfile != null) {
                    boolean doRemove = ObjectUtil.equalsConsiderNull(profile.getTimestamp(), loadedProfile.getTimestamp());
                    doRemove = doRemove || equalsExceptTimestamp(profile, loadedProfile);
                    if (doRemove) {
                        it.remove();
                        continue;
                    }
                }
                // else it means we have added the profile, so store him
            }
            else {
                if (ObjectUtil.equalsConsiderNull(profile.getTimestamp(), currentlyStored.getTimestamp())) {
                    continue;
                }
                if (!equalsExceptTimestamp(profile, currentlyStored)) {
                    // There is some conflict, so we need to do copy with timestamp based name and
                    // delete current from list
                    AbstractLoginProfile copy = profile.createBuilderWithSameValues()
                            .setProfileName(profile.getProfileName() + "_" + sdf.format(profile.getTimestamp() != null ? profile.getTimestamp() : new Date())).build();
                    conflictProfiles.put(copy.getProfileName(), copy);
                    it.remove();
                }
            }
        }
        profilesToStore.putAll(conflictProfiles);
        // We will update timestamp to current time
        for (Iterator<Map.Entry<String, AbstractLoginProfile>> it = profilesToStore.entrySet().iterator(); it.hasNext();) {
            Entry<String, AbstractLoginProfile> entry = it.next();
            AbstractLoginProfile profile = entry.getValue();
            AbstractLoginProfile currentlyStored = storedProfiles.get(profile.getProfileName());
            if (currentlyStored == null || !equalsExceptTimestamp(profile, currentlyStored)) {
                profile = profile.createBuilderWithSameValues().setTimestamp(new Timestamp(System.currentTimeMillis())).build();
            }
            entry.setValue(profile);
        }
        LoginProfilesStorageValue value = new LoginProfilesStorageValue(
                currentProfile != null && !LoginProfileConstants.CURRENT_PROFILE_NAME.equals(currentProfile.getProfileName()) ? currentProfile.getProfileName() : lastProfile.getProfileName(),
                profilesToStore.values());
        storage.storeProfiles(value);
        loadProfiles();
    }

    private boolean equalsExceptTimestamp(AbstractLoginProfile p1, AbstractLoginProfile p2) {
        p1 = p1.createBuilderWithSameValues().setTimestamp(null).build();
        p2 = p2.createBuilderWithSameValues().setTimestamp(null).build();
        return p1.equals(p2);
    }

    private Map<String, AbstractLoginProfile> createProfileMap(List<AbstractLoginProfile> profiles) {
        Map<String, AbstractLoginProfile> map = profiles.stream().collect(Collectors.toMap((p -> p.getProfileName()), Function.identity()));
        return map;
    }

    @Override
    public void setCurrentProfile(AbstractLoginProfile profile) {
        if (profile == null) {
            @SuppressWarnings("unchecked")
            Builder<AbstractLoginProfile> builder = (Builder<AbstractLoginProfile>) createProfileBuilder();
            builder.setProfileName(LoginProfileConstants.CURRENT_PROFILE_NAME);
            builder.setValues(defaultProfile);
            this.currentProfile = builder.build();
        }
        else {
            if (LoginProfileConstants.DEFAULT_PROFILE_NAME.equals(profile.getProfileName())) {
                @SuppressWarnings("unchecked")
                Builder<AbstractLoginProfile> builder = (Builder<AbstractLoginProfile>) createProfileBuilder();
                builder.setProfileName(LoginProfileConstants.CURRENT_PROFILE_NAME);
                builder.setValues(profile);
                profile = builder.build();
            }
            this.currentProfile = profile;
        }
        addProfile(currentProfile);
    }

    @Override
    public LoginProfilePossibleValues getPossibleValues() {
        return possibleValues;
    }

    @Override
    public AbstractLoginProfile updateProfile(AbstractLoginProfile profile) {
        if (!canUpdateProfile(profile)) {
            throw new IllegalStateException("Cannot update profile " + profile.getProfileName());
        }
        AbstractLoginProfile existingProfile = profiles.get(profile.getProfileName());
        if (existingProfile == null) {
            return null;
        }
        AbstractLoginProfile newProfile = profile.createBuilderWithSameValues().build();
        newProfile = adjustProfileValues(newProfile);
        profiles.put(profile.getProfileName(), newProfile);
        if (currentProfile != null && currentProfile.getProfileName().equals(profile.getProfileName())) {
            currentProfile = newProfile;
        }
        return newProfile;
    }

    @Override
    public AbstractLoginProfile renameProfile(String oldName, String newName) {
        AbstractLoginProfile p = profiles.get(oldName);
        if (p == null) {
            return null;
        }
        if (!canRenameProfile(p)) {
            return null;
        }
        @SuppressWarnings("unchecked")
        AbstractLoginProfile.Builder<AbstractLoginProfile> builder = (Builder<AbstractLoginProfile>) createProfileBuilder();
        AbstractLoginProfile clone = builder.setValues(p).setProfileName(newName).build();
        profiles.remove(oldName);
        profiles.put(clone.getProfileName(), clone);
        return clone;
    }

    @Override
    public boolean canRemoveProfile(AbstractLoginProfile profile) {
        if (LoginProfileConstants.DEFAULT_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        if (LoginProfileConstants.CURRENT_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        if (LoginProfileConstants.LAST_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        return profiles.containsKey(profile.getProfileName());
    }

    @Override
    public boolean canUpdateProfile(AbstractLoginProfile profile) {
        if (LoginProfileConstants.DEFAULT_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        if (LoginProfileConstants.LAST_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        return profiles.containsKey(profile.getProfileName());
    }

    @Override
    public boolean canRenameProfile(AbstractLoginProfile profile) {
        if (LoginProfileConstants.DEFAULT_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        if (LoginProfileConstants.CURRENT_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        if (LoginProfileConstants.LAST_PROFILE_NAME.equals(profile.getProfileName())) {
            return false;
        }
        return profiles.containsKey(profile.getProfileName());
    }

}
