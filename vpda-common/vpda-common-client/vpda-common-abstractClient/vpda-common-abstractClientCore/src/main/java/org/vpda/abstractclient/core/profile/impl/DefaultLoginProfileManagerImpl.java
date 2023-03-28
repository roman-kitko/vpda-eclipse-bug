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

import org.vpda.abstractclient.core.profile.AbstractLoginProfile;
import org.vpda.abstractclient.core.profile.AbstractLoginProfile.Builder;
import org.vpda.abstractclient.core.profile.LoginProfilePossibleValues;
import org.vpda.abstractclient.core.profile.LoginProfilesStorage;

/**
 * Default login manager
 * 
 * @author kitko
 *
 */
public final class DefaultLoginProfileManagerImpl extends AbstractLoginProfileManagerImpl {

    /**
     * @param possibleValues
     * @param storage
     */
    public DefaultLoginProfileManagerImpl(LoginProfilePossibleValues possibleValues, LoginProfilesStorage storage) {
        super(possibleValues, storage);
    }

    @Override
    protected Builder<? extends AbstractLoginProfile> createProfileBuilder() {
        return new DefaultLoginProfile.Builder();
    }

}
