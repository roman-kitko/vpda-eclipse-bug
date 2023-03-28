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

import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.common.service.Service;
import org.vpda.common.service.ServiceRegistry;

/**
 * Client server interface. This is client view of server
 * 
 * @author kitko
 *
 */
public interface ClientServer extends Service, ServiceRegistry {
    /**
     * logout method
     *
     */
    public void logout();

    /**
     * 
     * @return UserSession
     */
    public UserSession getUserSession();

    /**
     * Returns serviceFactory for client. Basically services are cached view to
     * server services.
     * 
     * @return ServiceFactory interface to access services from client
     */
    public ServiceRegistry getServiceRegistry();
}
