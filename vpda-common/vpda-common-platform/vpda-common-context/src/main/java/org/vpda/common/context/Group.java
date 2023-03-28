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
package org.vpda.common.context;

import java.io.Serializable;
import java.util.UUID;

import org.vpda.common.util.Builder;

/**
 * Group basic data.
 * 
 * @author kitko
 *
 */
public final class Group implements Serializable, Actor {
    private static final long serialVersionUID = 396801676003028213L;
    private final Long id;
    private final UUID externalId;
    private final String code;
    private final String name;
    private final String email;

    /**
     * @return Login of this user
     */
    public String getLogin() {
        return code;
    }

    /**
     * @return Returns the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Returns the id.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String getUniqueName() {
        return code;
    }

    @Override
    public UUID getExternalId() {
        return externalId;
    }

    private Group(GroupBuilder builder) {
        this.code = builder.getLogin();
        this.email = builder.getEmail();
        this.id = builder.getId();
        this.name = builder.getName();
        this.externalId = builder.getExternalId();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Group) {
            Group group = (Group) object;
            if (group.id != null && id != null) {
                return group.id.equals(id);
            }
            if (group.code != null && code != null) {
                return group.code.equals(code);
            }
            throw new IllegalStateException("Not sufficient information for equals of Group");
        }
        return false;

    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        if (code != null) {
            return code.hashCode();
        }
        throw new IllegalStateException("Not sufficient information for hashCode");
    }

    @Override
    public String toString() {
        return code;
    }

    /** Builder for group */
    public final static class GroupBuilder implements Builder<Group> {
        private Long id;
        private String login;
        private String name;
        private String email;
        private UUID externalId;

        public UUID getExternalId() {
            return externalId;
        }

        public GroupBuilder setExternalId(UUID externalId) {
            this.externalId = externalId;
            return this;
        }

        /**
         * @return the id
         */
        public Long getId() {
            return id;
        }

        /**
         * @param id the id to set
         * @return this
         */
        public GroupBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return the login
         */
        public String getLogin() {
            return login;
        }

        /**
         * @param login the login to set
         * @return this
         */
        public GroupBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         * @return this
         */
        public GroupBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email the email to set
         * @return this
         */
        public GroupBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets values from user
         * 
         * @param group
         * @return this
         */
        public GroupBuilder setValues(Group group) {
            this.email = group.getEmail();
            this.id = group.getId();
            this.login = group.getLogin();
            this.name = group.getName();
            this.externalId = group.getExternalId();
            return this;
        }

        /**
         * Builds new user
         * 
         * @return new user
         */
        @Override
        public Group build() {
            return new Group(this);
        }

        @Override
        public Class<? extends Group> getTargetClass() {
            return Group.class;
        }
    }

}
