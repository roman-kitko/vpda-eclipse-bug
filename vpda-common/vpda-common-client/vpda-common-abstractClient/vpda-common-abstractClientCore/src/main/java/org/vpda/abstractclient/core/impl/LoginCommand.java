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
package org.vpda.abstractclient.core.impl;

import java.io.Serializable;
import java.util.UUID;

import org.vpda.abstractclient.core.profile.AbstractLoginProfile;
import org.vpda.abstractclient.core.profile.LoginProfileConstants;
import org.vpda.abstractclient.core.profile.LoginProfileManager;
import org.vpda.abstractclient.core.profile.LoginProfileStorageException;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo.LoginInfoBuilder;
import org.vpda.clientserver.communication.data.AbstractClientUILoginInfo;
import org.vpda.clientserver.communication.data.ClientUILoginInfo;
import org.vpda.clientserver.communication.data.FrameUIDef;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.services.ClientResourceServices;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.clientserver.communication.services.ClientUIService;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.service.MuttableServiceRegistry;
import org.vpda.common.service.ServiceRegistry;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.LocalizationServiceBridge;
import org.vpda.common.util.ProgressChangeUnit;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.ObjectUtil;

/**
 * 
 * Login process when we have all needed information to login user. Here we will
 * resolve menu and other services client can call on server.
 * 
 * @author kitko
 *
 */
final class LoginCommand extends AbstractRequestCommand<Object> {
    private ClientLoginInfo loginInfo;
    private final ClientUI clientUI;
    private final ClientImpl client;

    LoginCommand(CommandExecutorRegistry registry, ClientImpl client, ClientUI clientUI, ClientLoginInfo loginInfo, UserSession session) {
        super(registry);
        this.loginInfo = loginInfo;
        this.clientUI = clientUI;
        this.client = client;
    }

    @Override
    protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
        addProgressObserver(clientUI.getLoginFrame());
        clientUI.getLoginFrame().setEnabledUI(false);
        return true;
    }

    @Override
    protected void executeAfterRequest(Object requestResult, CommandExecutionEnv env, CommandEvent event) {
        clientUI.getLoginFrame().setEnabledUI(true);
        clientUI.getLoginFrame().dispose();
        clientUI.getMainFrame().initialize();
        clientUI.afterLogin();
    }

    @Override
    protected Object executeRequest(final CommandExecutor executor, final CommandExecutionEnv env, final CommandEvent event) throws Exception {
        final class SetFinalLoginInfoCommand extends AbstractRequestCommand<LoginServer> {
            SetFinalLoginInfoCommand() {
                super(LoginCommand.this.getRegistry());
            }

            @Override
            protected LoginServer executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ClientCommunicationFactory clientComunicationFactory = client.getClientCommunicationFactory();
                ConnectCommand connectCmd = new ConnectCommand(LoginCommand.this.getRegistry(), client, clientUI, loginInfo, clientComunicationFactory);
                LoginServer executeRequest = connectCmd.executeRequest(executor, env, event);
                return executeRequest;
            }

            @Override
            protected void executeAfterRequest(LoginServer loginServer, CommandExecutionEnv env, CommandEvent event) {
                client.setLoginServer(loginServer);
                client.setLoginInfo(loginInfo);
                LoginCommand.this.getProgressNotifier().notifyProgressChanged(10, ProgressChangeUnit.RELATIVE, "Final loginInfo set", null);
            }

        }

        final class RequestResult implements Serializable {
            private static final long serialVersionUID = 1885567294826274532L;
            UserSession session;
            ClientServer clientServer;

            RequestResult(UserSession session, ClientServer clientServer) {
                super();
                this.session = session;
                this.clientServer = clientServer;
            }
        }

        final class LoginCmd extends AbstractRequestCommand<RequestResult> {
            LoginCmd() {
                // We will execute it on same executor we are executed upon
                super(LoginCommand.this.getRegistry());
            }

            @Override
            protected RequestResult executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                LoginServer loginServer = client.getLoginServer();
                ClientServer result = loginServer.login(loginInfo.createLoginInfo());
                ClientServer clientServer = result;
                UserSession session = clientServer.getUserSession();
                RequestResult requestResult = new RequestResult(session, clientServer);
                return requestResult;
            }

            @Override
            protected void executeAfterRequest(RequestResult requestResult, CommandExecutionEnv env, CommandEvent event) {
                client.setSession(requestResult.session);
                client.setClientServer(requestResult.clientServer);
                if (loginInfo.getConnectionInfo().getClientUILoginInfo() != null && requestResult.session.getLoginInfo().getConnectionInfo().getClientUILoginInfo() != null) {
                    // If server sent us new uuid, we need to store that
                    if (!ObjectUtil.equalsConsiderNull(loginInfo.getConnectionInfo().getClientUILoginInfo().getUuid(),
                            requestResult.session.getLoginInfo().getConnectionInfo().getClientUILoginInfo().getUuid())) {
                        AbstractClientUILoginInfo clientUIInfo = (AbstractClientUILoginInfo) loginInfo.getConnectionInfo().getClientUILoginInfo();
                        ClientUILoginInfo newUIInfo = (ClientUILoginInfo) clientUIInfo.createBuilderWithSameValues()
                                .setUuid(requestResult.session.getLoginInfo().getConnectionInfo().getClientUILoginInfo().getUuid()).build();
                        LoginInfoBuilder builder = loginInfo.createBuilderWithSameValues()
                                .setConnectionInfo(loginInfo.getConnectionInfo().createBuilderWithSameValues().setClientUILoginInfo(newUIInfo).build());
                        ClientLoginInfo newLoginInfo = builder.build();
                        loginInfo = newLoginInfo;
                    }
                }
                client.setLoginInfo(loginInfo);
                client.loggedIn();
                LoginCommand.this.getProgressNotifier().notifyProgressChanged(10, ProgressChangeUnit.RELATIVE, "Login OK", null);
            }
        }

        final class ResolveServicesRegistryCmd extends AbstractRequestCommand<ServiceRegistry> {
            ResolveServicesRegistryCmd() {
                super(LoginCommand.this.getRegistry());
            }

            @Override
            protected ServiceRegistry executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ClientServer clientServer = client.getClientServer();
                ServiceRegistry servicesRegistry = clientServer.getServiceRegistry();
                return servicesRegistry;
            }

            @Override
            protected void executeAfterRequest(ServiceRegistry serverServiceRegistry, CommandExecutionEnv env, CommandEvent event) {
                LocalizationService serverLocService = serverServiceRegistry.getService(LocalizationService.class);
                ClientResourceServices serverClientServices = serverServiceRegistry.getService(ClientResourceServices.class);
                MuttableServiceRegistry clientServiceRegistry = client.getServiceRegistry();
                LocalizationService clientLocService = clientServiceRegistry.getService(LocalizationService.class);
                // Now create bridge for services
                if (clientLocService != null) {
                    LocalizationService newLocService = new LocalizationServiceBridge(clientLocService, serverLocService, clientLocService.getLocValueBuilderFactory());
                    clientServiceRegistry.registerService(LocalizationService.class, newLocService);
                }
                clientServiceRegistry.registerService(ClientResourceServices.class, serverClientServices);
                clientServiceRegistry.registerNotRegisteredServices(serverServiceRegistry);
                // client.setServiceRegistry(serviceRegistry);
                LoginCommand.this.getProgressNotifier().notifyProgressChanged(10, ProgressChangeUnit.RELATIVE, "Service registry OK", null);
            }
        }

        final class MainFrameDefCmd extends AbstractRequestCommand<FrameUIDef> {
            MainFrameDefCmd() {
                super(LoginCommand.this.getRegistry());
            }

            @Override
            protected FrameUIDef executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ClientServer clientServer = client.getClientServer();
                ClientUIService uiService = clientServer.getService(ClientUIService.class);
                FrameUIDef def = uiService.getMainFrameDef();
                return def;
            }

            @Override
            protected void executeAfterRequest(FrameUIDef requestResult, CommandExecutionEnv env, CommandEvent event) {
                LoginCommand.this.getProgressNotifier().notifyProgressChanged(10, ProgressChangeUnit.RELATIVE, "Menu OK", null);
                client.setMainFrameDef(requestResult);
            }
        }

        final class StoreProfilesCmd extends AbstractRequestCommand<Object> {
            StoreProfilesCmd() {
                super(LoginCommand.this.getRegistry());
            }

            @Override
            protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
                LoginProfileManager loginProfileManager = clientUI.getLoginProfileManager();
                if (loginProfileManager == null) {
                    return true;
                }
                UserSession session = client.getSession();
                if (session.getLoginInfo() != null && session.getLoginInfo().getConnectionInfo().getClientUILoginInfo() != null
                        && session.getLoginInfo().getConnectionInfo().getClientUILoginInfo().getUuid() != null) {
                    UUID uuid = session.getLoginInfo().getConnectionInfo().getClientUILoginInfo().getUuid();
                    AbstractLoginProfile saveToProfile = loginProfileManager.getCurrentProfile();
                    if (!loginProfileManager.canUpdateProfile(saveToProfile)) {
                        saveToProfile = loginProfileManager.getProfile(LoginProfileConstants.CURRENT_PROFILE_NAME);
                    }
                    saveToProfile = saveToProfile.createBuilderWithSameValues().setUuid(uuid).build();
                    loginProfileManager.updateProfile(saveToProfile);
                    try {
                        loginProfileManager.storeProfiles();
                    }
                    catch (LoginProfileStorageException e) {
                        throw new VPDARuntimeException("Error storing profiles", e);
                    }
                    LoginCommand.this.getProgressNotifier().notifyProgressChanged(100, ProgressChangeUnit.ABSOLUTE, "Profiles stored", null);
                }
                return true;
            }

        }

        final class ShowSuccessMessageCmd extends AbstractRequestCommand<Object> {
            ShowSuccessMessageCmd() {
                super(LoginCommand.this.getRegistry());
            }

            @Override
            protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
                LoginCommand.this.getProgressNotifier().notifyProgressChanged(100, ProgressChangeUnit.ABSOLUTE, "Login Success", null);
                LoginCommand.this.client.running();
                return true;
            }

            @Override
            protected Object executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                Thread.sleep(100);
                return null;
            }
        }

        executor.executeCommand(new SetFinalLoginInfoCommand(), env, event);
        executor.executeCommand(new LoginCmd(), env, event);
        executor.executeCommand(new ResolveServicesRegistryCmd(), env, event);
        executor.executeCommand(new MainFrameDefCmd(), env, event);
        executor.executeCommand(new StoreProfilesCmd(), env, event);
        executor.executeCommand(new ShowSuccessMessageCmd(), env, event);
        return null;
    }

}
