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
package org.vpda.clientserver.communication.data;

import org.vpda.common.util.EncryptedString;

/**
 * {@link AuthenticationEntry} with secret token
 * 
 * @author kitko
 *
 */
public final class SecretTokenAuthenticationEntry implements AuthenticationEntry {
    private static final long serialVersionUID = 1745080820653421948L;
    private final String login;
    private EncryptedString token;

    /**
     * Creates SecretTokenAuthenticationEntry
     * 
     * @param login
     * @param token
     */
    public SecretTokenAuthenticationEntry(String login, EncryptedString token) {
        this.login = login;
        this.token = token;
    }

    /**
     * Creates SecretTokenAuthenticationEntry
     * 
     * @param login
     * @param token
     */
    public SecretTokenAuthenticationEntry(String login, char[] token) {
        this(login, new EncryptedString(token));
    }

    @Override
    public String getLoginToken() {
        return login;
    }

    @Override
    public void clearSensitiveData() {
        if (token == null) {
            throw new RuntimeException("Already cleared");
        }
        token.clear();
        token = null;
    }

    @Override
    public boolean areSensitiveDataClared() {
        return token == null;
    }

    /**
     * @return token
     */
    public EncryptedString getSecretToken() {
        if (token == null) {
            throw new RuntimeException("Cannot access token, sensitive data are already cleared");
        }
        return token;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SecretTokenAuthenticationEntry other = (SecretTokenAuthenticationEntry) obj;
        if (login == null) {
            if (other.login != null)
                return false;
        }
        else if (!login.equals(other.login))
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        }
        else if (!token.equals(other.token))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SecretTokenAuthenticationEntry [login=" + login + ", token=" + token + "]";
    }

}
