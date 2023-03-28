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

import java.io.Serializable;

import org.vpda.abstractclient.core.profile.AbstractLoginProfile;

/** Default profile */
public final class DefaultLoginProfile extends AbstractLoginProfile implements Serializable {
    private static final long serialVersionUID = -7636739731083652829L;

    private DefaultLoginProfile(Builder builder) {
        super(builder);
    }

    @Override
    public DefaultLoginProfile.Builder createBuilder() {
        return new DefaultLoginProfile.Builder();
    }

    @Override
    public DefaultLoginProfile.Builder createBuilderWithSameValues() {
        return createBuilder().setValues(this);
    }

    /**
     * Builder for default login profile
     * 
     * @author kitko
     *
     */
    public final static class Builder extends AbstractLoginProfile.Builder<DefaultLoginProfile> implements org.vpda.common.util.Builder<DefaultLoginProfile> {

        @Override
        public Class<? extends DefaultLoginProfile> getTargetClass() {
            return DefaultLoginProfile.class;
        }

        @Override
        public DefaultLoginProfile build() {
            return new DefaultLoginProfile(this);
        }

        @Override
        public DefaultLoginProfile.Builder setValues(DefaultLoginProfile profile) {
            return (Builder) super.setValues(profile);
        }

    }

}
