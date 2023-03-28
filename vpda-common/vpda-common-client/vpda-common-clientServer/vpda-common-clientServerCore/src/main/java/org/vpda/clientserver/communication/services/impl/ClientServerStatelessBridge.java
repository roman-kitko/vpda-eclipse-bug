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
package org.vpda.clientserver.communication.services.impl;

import java.io.Serializable;
import java.util.Collection;

import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.clientserver.communication.services.LoginException;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.common.service.Service;
import org.vpda.common.service.ServiceEnvironment;
import org.vpda.common.service.ServiceRegistry;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;

/**
 * {@link ClientServer} stateless bridge using
 * {@link StatelessClientServerEntry}
 * 
 * @author kitko
 *
 */
public final class ClientServerStatelessBridge implements ClientServer, Serializable {
    private static final long serialVersionUID = -3281388679371531009L;
    private final UserSession userSession;
    private final StatelessClientServerEntry statelessClientServerEntry;

    /**
     * Creates ClientServerStatelessBridge
     * 
     * @param userSession
     * @param statelessClientServerEntry
     */
    public ClientServerStatelessBridge(UserSession userSession, StatelessClientServerEntry statelessClientServerEntry) {
        this.userSession = Assert.isNotNullArgument(userSession, "userSession");
        this.statelessClientServerEntry = Assert.isNotNullArgument(statelessClientServerEntry, "statelessClientServerEntry");
    }

    @Override
    public <T extends Service> T getService(Class<T> serviceClass) {
        try {
            T service = statelessClientServerEntry.getService(userSession.getLoginInfo(), serviceClass);
            return service;
        }
        catch (LoginException e) {
            throw new VPDARuntimeException("Error getting service", e);
        }
    }

    @Override
    public <T extends Service> T getService(Class<T> serviceClass, ServiceEnvironment env) {
        try {
            return statelessClientServerEntry.getService(userSession.getLoginInfo(), serviceClass, env);
        }
        catch (LoginException e) {
            throw new VPDARuntimeException("Error getting service", e);
        }
    }

    @Override
    public Collection<Class<? extends Service>> getServicesKeys() {
        try {
            return statelessClientServerEntry.getServicesKeys(userSession.getLoginInfo());
        }
        catch (LoginException e) {
            throw new VPDARuntimeException("Error getting service keys", e);
        }
    }

    @Override
    public void logout() {
        statelessClientServerEntry.logout(userSession.getLoginInfo());
    }

    @Override
    public UserSession getUserSession() {
        return userSession;
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return this;
    }

}
