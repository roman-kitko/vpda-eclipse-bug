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
package org.vpda.abstractclient.core;

import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.data.ClientPlatform;
import org.vpda.clientserver.communication.data.FrameUIDef;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.services.ClientResourceServices;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutorWithRegistry;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.service.MuttableServiceRegistry;
import org.vpda.common.util.ComponentInfo;
import org.vpda.common.util.Initializee;

/**
 * Public Client interface
 * 
 * @author kitko
 *
 */
public interface Client extends Initializee {

    /**
     * Lists all applicable aplication contexts for loginInfo
     * 
     * @param loginInfo
     */
    public abstract void startGetApplicableContextsWorkFlow(ClientLoginInfo loginInfo);

    /**
     * Connects client to server
     * 
     * @param loginInfo
     */
    public abstract void startConnectWorkFlow(ClientLoginInfo loginInfo);

    /**
     * Will start authenticate workflow
     * 
     * @param loginInfo
     */
    public void startAuthenticateWorkflow(ClientLoginInfo loginInfo);

    /**
     * Makes login request to server
     * 
     * @param loginInfo
     * @return session
     */
    public abstract UserSession startLoginWorkFlow(ClientLoginInfo loginInfo);

    /** ShutDown the client */
    public abstract void shutdown();

    /**
     * This will be really executed only when platform allows this
     * 
     * @param status
     * 
     */
    public abstract void exitVM(int status);

    /**
     * Logout client - remove its session from server and dispose gui except of
     * login frame
     */
    public abstract void logout();

    /**
     * @return loginInfo if succesfully logged in else null
     */
    public abstract ClientLoginInfo getLoginInfo();

    /**
     * @return Returns the session.
     */
    public abstract UserSession getSession();

    /**
     * @return current client platform
     */
    public ClientPlatform getClientPlatform();

    /**
     * @return Returns the main frame def
     */
    public abstract FrameUIDef getMainFrameDef();

    /**
     * Logs and shows exception message dialog
     * 
     * @param e
     * @param clazz
     * @param msg
     * @param throwRuntime
     */
    public abstract void logAndShowExceptionDialog(Throwable e, Class clazz, String msg, boolean throwRuntime);

    /**
     * 
     * @return ClientServer interface once connected
     */
    public ClientServer getClientServer();

    /**
     * 
     * @return login server
     */
    public LoginServer getLoginServer();

    /**
     * @return ClientServices
     */
    public ClientResourceServices getClientServices();

    /**
     * @return ClientCommunicationFactory
     */
    public ClientCommunicationFactory getClientCommunicationFactory();

    /**
     * 
     * @return RequestCommandExecutor
     */
    public CommandExecutorWithRegistry getRequestCommandExecutor();

    /**
     * 
     * @return common client CommandExecutionEnv
     */
    public CommandExecutionEnv getCommandExecutionEnv();

    /**
     * Sets common client CommandExecutionEnv
     * 
     * @param env
     */
    public void setCommandExecutionEnv(CommandExecutionEnv env);

    /**
     * Sets RequestCommandsExecutor
     * 
     * @param ce
     */
    public void setRequestCommandsExecutor(CommandExecutorWithRegistry ce);

    /**
     * @return ServiceRegistry
     */
    public MuttableServiceRegistry getServiceRegistry();

    /**
     * 
     * @return project
     */
    public Application getApplication();

    /**
     * 
     * @return component info of running client
     */
    public ComponentInfo getClientInfo();

    /**
     * @return identifier of server client is connected to
     */
    public String getClientConnectionIdentifier();

    /**
     * @return component info of running server
     */
    public ComponentInfo getServerInfo();

    /**
     * @return the status of client
     */
    public ClientStatus getStatus();

}
