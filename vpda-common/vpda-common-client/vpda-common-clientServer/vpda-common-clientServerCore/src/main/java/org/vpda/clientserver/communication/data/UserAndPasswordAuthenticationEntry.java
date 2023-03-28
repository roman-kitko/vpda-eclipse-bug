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
package org.vpda.clientserver.communication.data;

import java.io.Serializable;

import org.vpda.common.util.EncryptedString;
import org.vpda.internal.common.util.Assert;

/**
 * Basic user/password entry for authentication
 * 
 * @author kitko
 *
 */
public final class UserAndPasswordAuthenticationEntry implements AuthenticationEntry, Serializable {
    private static final long serialVersionUID = 3332221947799445407L;
    private final String login;
    private EncryptedString password;

    @Override
    public String getLoginToken() {
        return login;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return the password
     */
    public EncryptedString getPassword() {
        if (password == null) {
            throw new RuntimeException("Cannot access password, sensitive data are already cleared");
        }
        return password;
    }

    /**
     * Creates UserAndPasswordEntry using login and password
     * 
     * @param login
     * @param password
     */
    public UserAndPasswordAuthenticationEntry(String login, EncryptedString password) {
        super();
        this.login = Assert.isNotNullArgument(login, "login");
        this.password = Assert.isNotNullArgument(password, "password");
        ;
    }

    /**
     * Creates UserAndPasswordEntry using login and password
     * 
     * @param login
     * @param password
     */
    public UserAndPasswordAuthenticationEntry(String login, char[] password) {
        this(login, new EncryptedString(password));
    }

    /** Fox JAXB */
    @SuppressWarnings("unused")
    private UserAndPasswordAuthenticationEntry() {
        this.login = null;
        this.password = null;
    }

    @Override
    public void clearSensitiveData() {
        if (password == null) {
            throw new RuntimeException("Already cleared");
        }
        password.clear();
        password = null;
    }

    @Override
    public boolean areSensitiveDataClared() {
        return password == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserAndPasswordAuthenticationEntry other = (UserAndPasswordAuthenticationEntry) obj;
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        }
        else if (!login.equals(other.login)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        }
        else if (!password.equals(other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return login;
    }

}
