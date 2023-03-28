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
 * User basic data. This data are stored in session
 * 
 * @author kitko
 *
 */
public final class User implements Serializable, Actor {
    private static final long serialVersionUID = 396801676003028213L;
    private final Long id;
    private final String login;
    private final String name;
    private final String email;
    private final UUID externalId;

    /**
     * @return Login of this user
     */
    public String getLogin() {
        return login;
    }

    @Override
    public UUID getExternalId() {
        return externalId;
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

    /**
     * Creates User with login only set
     * 
     * @param login
     */
    public User(String login) {
        this.login = login;
        this.email = null;
        this.id = null;
        this.name = null;
        this.externalId = null;
    }

    private User(UserBuilder builder) {
        this.login = builder.getLogin();
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
        if (object instanceof User) {
            User user = (User) object;
            if (user.id != null && id != null) {
                return user.id.equals(id);
            }
            if (user.login != null && login != null) {
                return user.login.equals(login);
            }
            throw new IllegalStateException("Not sufficient information for equals of User");
        }
        return false;

    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        if (login != null) {
            return login.hashCode();
        }
        throw new IllegalStateException("Not sufficient information for hashCode");
    }

    @Override
    public String toString() {
        return login;
    }

    /** Builder for user */
    public final static class UserBuilder implements Builder<User> {
        private Long id;
        private String login;
        private String name;
        private String email;
        private UUID externalId;

        public UUID getExternalId() {
            return externalId;
        }

        public UserBuilder setExternalId(UUID externalId) {
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
        public UserBuilder setId(Long id) {
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
        public UserBuilder setLogin(String login) {
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
        public UserBuilder setName(String name) {
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
        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets values from user
         * 
         * @param user
         * @return this
         */
        public UserBuilder setValues(User user) {
            this.email = user.getEmail();
            this.id = user.getId();
            this.login = user.getLogin();
            this.name = user.getName();
            this.externalId = user.getExternalId();
            return this;
        }

        /**
         * Builds new user
         * 
         * @return new user
         */
        @Override
        public User build() {
            return new User(this);
        }

        @Override
        public Class<? extends User> getTargetClass() {
            return User.class;
        }
    }

    @Override
    public String getUniqueName() {
        return login;
    }

}
