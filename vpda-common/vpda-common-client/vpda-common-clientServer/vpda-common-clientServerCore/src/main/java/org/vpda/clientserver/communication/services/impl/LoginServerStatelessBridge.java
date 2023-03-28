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
package org.vpda.clientserver.communication.services.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.vpda.clientserver.communication.data.AuthenticationEntry;
import org.vpda.clientserver.communication.data.BaseApplicableContexts;
import org.vpda.clientserver.communication.data.ClientUILoginInfo;
import org.vpda.clientserver.communication.data.FullApplicableContexts;
import org.vpda.clientserver.communication.data.LoginInfo;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.clientserver.communication.services.LoginException;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.DateContext;
import org.vpda.common.util.ComponentInfo;
import org.vpda.internal.common.util.Assert;

/**
 * @author kitko
 *
 */
public final class LoginServerStatelessBridge implements LoginServer, Serializable {
    private static final long serialVersionUID = -1491991730766843457L;
    private final StatelessClientServerEntry statelessClientServerEntry;

    /**
     * Creates LoginServerStatelessBridge
     * 
     * @param statelessClientServerEntry
     */
    public LoginServerStatelessBridge(StatelessClientServerEntry statelessClientServerEntry) {
        this.statelessClientServerEntry = Assert.isNotNullArgument(statelessClientServerEntry, "statelessClientServerEntry");
    }

    @Override
    public String getClassPathURL(LoginInfo loginInfo) {
        return statelessClientServerEntry.getClassPathURL(loginInfo);
    }

    @Override
    public byte[] loadClassBytes(LoginInfo loginInfo, String name) throws IOException {
        return statelessClientServerEntry.loadClassBytes(loginInfo, name);
    }

    @Override
    public ClientServer login(LoginInfo loginInfo) throws LoginException {
        UserSession session = statelessClientServerEntry.authenticateAndGenerateTransientSessionForLogin(loginInfo);
        return new ClientServerStatelessBridge(session, statelessClientServerEntry);
    }

    @Override
    public BaseApplicableContexts getBaseApplicableContexts(LoginInfo loginInfo) throws LoginException {
        return statelessClientServerEntry.getBaseApplicableContexts(loginInfo);
    }

    @Override
    public FullApplicableContexts getFullApplicableContexts(LoginInfo loginInfo) throws LoginException {
        return statelessClientServerEntry.getFullApplicableContexts(loginInfo);
    }

    @Override
    public List<Locale> getLocalesForContext(LoginInfo loginInfo, ApplContext ctx) throws LoginException {
        return statelessClientServerEntry.getLocalesForContext(loginInfo, ctx);
    }

    @Override
    public ComponentInfo getServerInfo(LoginInfo loginInfo) {
        return statelessClientServerEntry.getServerInfo(loginInfo);
    }

    @Override
    public void ping(byte[] bytes) {
        statelessClientServerEntry.ping(bytes);
    }

    @Override
    public ClientUILoginInfo getUserLoginPreferences(UUID uuid, LoginInfo loginInfo) {
        return statelessClientServerEntry.getUserLoginPreferences(uuid, loginInfo);
    }

    @Override
    public AuthenticationEntry generateSecretEntry(AuthenticationEntry entry) throws LoginException {
        return statelessClientServerEntry.generateSecretEntry(entry);
    }

    @Override
    public LoginInfo authenticate(LoginInfo loginInfo) throws LoginException {
        return statelessClientServerEntry.authenticate(loginInfo);
    }

    @Override
    public List<DateContext> getDateContextsForContext(LoginInfo loginInfo, ApplContext ctx) throws LoginException {
        return statelessClientServerEntry.getDateContextsForContext(loginInfo, ctx);
    }

}
