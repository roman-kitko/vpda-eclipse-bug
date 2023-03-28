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
package org.vpda.clientserver.communication.services;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.vpda.clientserver.communication.data.AuthenticationEntry;
import org.vpda.clientserver.communication.data.BaseApplicableContexts;
import org.vpda.clientserver.communication.data.ClientUILoginInfo;
import org.vpda.clientserver.communication.data.FullApplicableContexts;
import org.vpda.clientserver.communication.data.LoginInfo;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.DateContext;
import org.vpda.common.service.Service;
import org.vpda.common.util.ComponentInfo;

/**
 * Interface for login of client.
 * 
 * @author kitko
 *
 */
public interface LoginServer extends Service, ClientServerClassLoader {
    /**
     * Authenticate user by passed {@link AuthenticationEntry} entry.
     * 
     * @param entry
     * @return another more straight forward entry user can use for any required
     *         authentication request
     * @throws LoginException
     */
    public AuthenticationEntry generateSecretEntry(AuthenticationEntry entry) throws LoginException;

    /**
     * Method list all applicable contexts user can use to login. Must provide valid
     * credentials to login
     * 
     * @param loginInfo
     * @return list of all applicable contexts and locale
     * @throws LoginException
     */
    public BaseApplicableContexts getBaseApplicableContexts(LoginInfo loginInfo) throws LoginException;

    /**
     * @param loginInfo
     * @return full contexts
     * @throws LoginException
     */
    public FullApplicableContexts getFullApplicableContexts(LoginInfo loginInfo) throws LoginException;

    /**
     * @param loginInfo
     * @param ctx
     * @return locales for context
     * @throws LoginException
     */
    public List<Locale> getLocalesForContext(LoginInfo loginInfo, ApplContext ctx) throws LoginException;

    public List<DateContext> getDateContextsForContext(LoginInfo loginInfo, ApplContext ctx) throws LoginException;

    /**
     * Authenticate with full login info. Must provide also valid context
     * 
     * @param loginInfo
     * @return LoginInfo
     * @throws LoginException
     */
    public LoginInfo authenticate(LoginInfo loginInfo) throws LoginException;

    /**
     * Login method of client
     * 
     * @param loginInfo
     * @return ClientServer
     * @throws LoginException
     */
    public ClientServer login(LoginInfo loginInfo) throws LoginException;

    /**
     * 
     * @param loginInfo
     * @return component info of running server
     */
    public ComponentInfo getServerInfo(LoginInfo loginInfo);

    /**
     * Ping server
     * 
     * @param bytes
     */
    public void ping(byte[] bytes);

    /**
     * Gets user login preferences by uuid
     * 
     * @param uuid
     * @param loginInfo
     * @return login preferences or null if not found
     */
    public ClientUILoginInfo getUserLoginPreferences(UUID uuid, LoginInfo loginInfo);
}
