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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.vpda.abstractclient.core.profile.AbstractLoginProfile;
import org.vpda.abstractclient.core.profile.LoginProfileConstants;
import org.vpda.abstractclient.core.profile.LoginProfilePossibleValues;
import org.vpda.abstractclient.core.profile.LoginProfileStorageException;

/**
 * @author kitko
 *
 */
public class DefaultLoginProfileManagerImplTest {
    private DefaultLoginProfileManagerImpl testee;
    private ByteArrayLoginProfilesStorage storage;

    private DefaultLoginProfileManagerImpl createTestee() {
        LoginProfilePossibleValues possibleValues = new LoginProfilePossibleValues.Builder<>().build();
        storage = new ByteArrayLoginProfilesStorage();
        return new DefaultLoginProfileManagerImpl(possibleValues, storage);
    }

    private DefaultLoginProfile createProfileWithName(String name) {
        return new DefaultLoginProfile.Builder().setProfileName(name).build();
    }

    /**
     * setup
     */
    @BeforeEach
    public void setup() {
        testee = createTestee();
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#addProfile(org.vpda.abstractclient.core.profile.AbstractLoginProfile)}.
     */
    @Test
    public void testAddProfile() {
        AbstractLoginProfile profile = createProfileWithName("myProfile");
        testee.addProfile(profile);
        assertNotNull(testee.getProfile(profile.getProfileName()));
        assertSame(profile, testee.getProfile(profile.getProfileName()));
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#getCurrentProfile()}.
     */
    @Test
    public void testGetCurrentProfile() {
        assertNotNull(testee.getCurrentProfile());
        assertEquals(LoginProfileConstants.CURRENT_PROFILE_NAME, testee.getCurrentProfile().getProfileName());
        AbstractLoginProfile profile = createProfileWithName("myProfile");
        testee.setCurrentProfile(profile);
        assertNotNull(testee.getCurrentProfile());
        assertEquals(profile.getProfileName(), testee.getCurrentProfile().getProfileName());
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#getProfile(java.lang.String)}.
     */
    @Test
    public void testGetProfile() {
        AbstractLoginProfile profile = createProfileWithName("myProfile");
        testee.addProfile(profile);
        assertNotNull(testee.getProfile(profile.getProfileName()));
        assertSame(profile, testee.getProfile(profile.getProfileName()));
        assertNull(testee.getProfile("XXX"));
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#getProfileNames()}.
     */
    @Test
    public void testGetProfileNames() {
        testee.addProfile(createProfileWithName("myProfile1"));
        testee.addProfile(createProfileWithName("myProfile2"));
        assertEquals(Arrays.asList(LoginProfileConstants.DEFAULT_PROFILE_NAME, LoginProfileConstants.CURRENT_PROFILE_NAME, "myProfile1", "myProfile2"), testee.getProfileNames());
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#loadProfiles()}.
     * 
     * @throws LoginProfileStorageException
     */
    @Test
    public void testLoadAndStoreProfiles() throws LoginProfileStorageException {
        testee.addProfile(createProfileWithName("myProfile1"));
        testee.addProfile(createProfileWithName("myProfile2"));
        assertNull(storage.getBytes());
        testee.storeProfiles();
        assertNotNull(storage.getBytes());
        testee.removeProfile("myProfile1");
        testee.removeProfile("myProfile2");
        assertEquals(Arrays.asList(LoginProfileConstants.DEFAULT_PROFILE_NAME, LoginProfileConstants.CURRENT_PROFILE_NAME, LoginProfileConstants.LAST_PROFILE_NAME), testee.getProfileNames());
        testee.loadProfiles();
        assertEquals(
                new HashSet<>(
                        Arrays.asList(LoginProfileConstants.DEFAULT_PROFILE_NAME, LoginProfileConstants.CURRENT_PROFILE_NAME, LoginProfileConstants.LAST_PROFILE_NAME, "myProfile1", "myProfile2")),
                new HashSet<>(testee.getProfileNames()));
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#removeProfile(java.lang.String)}.
     */
    @Test
    public void testRemoveProfile() {
        testee.addProfile(createProfileWithName("myProfile1"));
        testee.addProfile(createProfileWithName("myProfile2"));
        assertEquals(Arrays.asList(LoginProfileConstants.DEFAULT_PROFILE_NAME, LoginProfileConstants.CURRENT_PROFILE_NAME, "myProfile1", "myProfile2"), testee.getProfileNames());
        assertNotNull(testee.removeProfile("myProfile1"));
        assertEquals(Arrays.asList(LoginProfileConstants.DEFAULT_PROFILE_NAME, LoginProfileConstants.CURRENT_PROFILE_NAME, "myProfile2"), testee.getProfileNames());
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#getPossibleValues()}.
     */
    @Test
    public void testGetPossibleValues() {
        assertNotNull(testee.getPossibleValues());
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#updateProfile(org.vpda.abstractclient.core.profile.AbstractLoginProfile)}.
     */
    @Test
    public void testUpdateProfile() {
        DefaultLoginProfile profile = new DefaultLoginProfile.Builder().setProfileName("myProfile1").setPort(66).build();
        testee.addProfile(profile);
        assertEquals(Integer.valueOf(66), testee.getProfile("myProfile1").getPort());
        profile = new DefaultLoginProfile.Builder().setProfileName("myProfile1").setPort(77).build();
        assertEquals(Integer.valueOf(66), testee.getProfile("myProfile1").getPort());
        testee.updateProfile(profile);
        assertEquals(Integer.valueOf(77), testee.getProfile("myProfile1").getPort());
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#renameProfile(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testRenameProfile() {
        DefaultLoginProfile profile = new DefaultLoginProfile.Builder().setProfileName("myProfile1").setPort(66).build();
        testee.addProfile(profile);
        assertNotNull(testee.getProfile("myProfile1"));
        AbstractLoginProfile p = testee.renameProfile("myProfile1", "myProfile2");
        assertNotNull(p);
        assertEquals("myProfile2", p.getProfileName());
        assertNull(testee.getProfile("myProfile1"));
        assertNotNull(testee.getProfile("myProfile2"));
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#canRemoveProfile(org.vpda.abstractclient.core.profile.AbstractLoginProfile)}.
     */
    @Test
    public void testCanRemoveProfile() {
        DefaultLoginProfile profile = createProfileWithName("myProfile1");
        testee.addProfile(profile);
        assertTrue(testee.canRemoveProfile(profile));
        AbstractLoginProfile def = testee.getCurrentProfile();
        assertFalse(testee.canRemoveProfile(def));
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#canUpdateProfile(org.vpda.abstractclient.core.profile.AbstractLoginProfile)}.
     */
    @Test
    public void testCanUpdateProfile() {
        DefaultLoginProfile profile = createProfileWithName("myProfile1");
        testee.addProfile(profile);
        assertTrue(testee.canUpdateProfile(profile));
        AbstractLoginProfile cur = testee.getCurrentProfile();
        assertTrue(testee.canUpdateProfile(cur));
        AbstractLoginProfile def = testee.getDefaultProfile();
        assertFalse(testee.canUpdateProfile(def));
    }

    /**
     * Test method for
     * {@link org.vpda.abstractclient.core.profile.impl.AbstractLoginProfileManagerImpl#canRenameProfile(org.vpda.abstractclient.core.profile.AbstractLoginProfile)}.
     */
    @Test
    public void testCanRenameProfile() {
        DefaultLoginProfile profile = createProfileWithName("myProfile1");
        testee.addProfile(profile);
        assertTrue(testee.canRenameProfile(profile));
        AbstractLoginProfile def = testee.getCurrentProfile();
        assertFalse(testee.canRenameProfile(def));
    }

    /**
     * Test of conflic
     * 
     * @throws LoginProfileStorageException
     */
    @Test
    public void testParalelAccess() throws LoginProfileStorageException {
        ByteArrayLoginProfilesStorage storage = new ByteArrayLoginProfilesStorage();
        LoginProfilePossibleValues possibleValues = new LoginProfilePossibleValues.Builder<>().build();
        DefaultLoginProfileManagerImpl man1 = new DefaultLoginProfileManagerImpl(possibleValues, storage);
        DefaultLoginProfileManagerImpl man2 = new DefaultLoginProfileManagerImpl(possibleValues, storage);
        man1.addProfile(createProfileWithName("myProfile1"));
        man1.addProfile(createProfileWithName("myProfile2"));
        man1.addProfile(createProfileWithName("myProfile3"));
        man1.storeProfiles();
        man2.loadProfiles();
        assertEquals(6, man2.getProfileNames().size());
        assertNotNull(man2.getProfile("myProfile1"));
        assertNotNull(man2.getProfile("myProfile2"));
        assertNotNull(man2.getProfile("myProfile3"));
        man1.updateProfile(man1.getProfile("myProfile2").createBuilderWithSameValues().setBindingName("MY_BINDING_NAME1").build());
        man1.storeProfiles();
        man2.updateProfile(man2.getProfile("myProfile2").createBuilderWithSameValues().setBindingName("MY_BINDING_NAME2").build());
        man2.storeProfiles();
        assertEquals(7, man2.getProfileNames().size());
        assertTrue(man2.getProfileNames().containsAll(Arrays.asList("default", "current", "myProfile1", "myProfile2", "myProfile3", "last")));
        AbstractLoginProfile profile = man2.getProfile(6);
        assertTrue(profile.getProfileName().startsWith("myProfile2_"));
    }

}
