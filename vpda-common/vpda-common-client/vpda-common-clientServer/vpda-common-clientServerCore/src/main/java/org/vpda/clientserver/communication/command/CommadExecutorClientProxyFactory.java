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
package org.vpda.clientserver.communication.command;

import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;

/**
 * Will create proxy remote objects for passed executors. All methods will be
 * delegated to passed executor with passed environment
 * 
 * @author kitko
 *
 */
public interface CommadExecutorClientProxyFactory {

    /**
     * Creates proxy for executor implementing passed interfaces.
     * 
     * @param ccf
     * @param communicationId
     * @param executor
     * @param env
     * @param ifaces
     * @return proxy that will delegate all method invocations to passed executor
     */
    public Object createStatefullProxy(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces);

    /**
     * Creates proxy for executor implementing passed interfaces.
     * 
     * @param ccf
     * @param communicationId
     * @param executor
     * @param env
     * @param loginInfo
     * @param ifaces
     * @return proxy that will delegate all method invocations to passed executor
     */
    public Object createStatelessProxy(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor executor, CommandExecutionEnv env, ClientLoginInfo loginInfo, Class[] ifaces);

}
