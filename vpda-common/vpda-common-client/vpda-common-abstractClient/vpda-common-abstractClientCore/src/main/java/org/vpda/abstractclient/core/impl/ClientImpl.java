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

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;

import org.vpda.abstractclient.core.ClientStatus;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.core.ui.ClientWithUI;
import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.client.clientcommunication.protocol.embedded.EmbeddedClientCommunicationSettings;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationSettings;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientConnectionInfo;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.BasicCommunicationKind;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.data.ClientDeploymentPlatform;
import org.vpda.clientserver.communication.data.ClientPlatform;
import org.vpda.clientserver.communication.data.FrameUIDef;
import org.vpda.clientserver.communication.data.UserAndPasswordAuthenticationEntry;
import org.vpda.clientserver.communication.data.UserSession;
import org.vpda.clientserver.communication.services.ClientResourceServices;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutorWithRegistry;
import org.vpda.common.command.MethodCommandEvent;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorRegistryImpl;
import org.vpda.common.command.executor.impl.CommandExecutorWithRegistryImpl;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandBaseConst;
import org.vpda.common.context.ApplContext;
import org.vpda.common.context.ApplContextBuilder;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.User;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.service.MuttableServiceRegistry;
import org.vpda.common.util.ComponentInfo;
import org.vpda.common.util.ScmCommitInfo;
import org.vpda.common.util.Version;
import org.vpda.common.util.exceptions.ExceptionUtil;
import org.vpda.common.util.exceptions.TracedException;
import org.vpda.common.util.exceptions.TracedRuntimeExceptionWrapper;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.PropertiesUtil;
import org.vpda.internal.common.util.StringUtil;
import org.vpda.internal.common.util.VersionUtil;
import org.vpda.internal.common.util.contants.CommonUtilConstants;
import org.vpda.internal.common.util.scm.SCMUtil;

/**
 * This is abstract client class. It is not dependent to any UI and also not to
 * communication layer to server.
 * 
 * @author kitko
 *
 */
public final class ClientImpl implements ClientWithUI, Serializable {
    private static final long serialVersionUID = -4143668791122913061L;
    private static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(ClientImpl.class);
    private boolean isInitialized;
    private ClientLoginInfo loginInfo;
    private UserSession session;
    private ClientUI clientUI;
    private FrameUIDef mainFrameDef;
    private ClientServer clientServer;
    private CommandExecutorWithRegistry requestCommandsExecutor;
    private LoginServer loginServer;
    private ClientResourceServices clientServices;
    private ClientCommunicationFactory clientComunicationFactory;
    private MuttableServiceRegistry serviceRegistry;
    private Application application;
    private CommandExecutionEnv env;
    private ComponentInfo clientInfo;
    private ComponentInfo serverInfo;
    private static long clientId;
    private ClientStatus status;

    @Override
    public void startGetApplicableContextsWorkFlow(ClientLoginInfo loginInfo) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        GetContextsAndLocaleCommand command = new GetContextsAndLocaleCommand(getRequestCommandExecutor(), loginInfo, this, clientUI, loginServer);
        executeRequestCommandBase(command, new MethodCommandEvent(getClass(), "getApplicableContextsRequest"));
        logger.methodExit(method);
    }

    @Override
    public void startAuthenticateWorkflow(ClientLoginInfo loginInfo) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        GenerateSecretEntryCommand command = new GenerateSecretEntryCommand(getRequestCommandExecutor(), clientUI, this);
        executeRequestCommandBase(command, new MethodCommandEvent(getClass(), "startAuthenticateWorkflow"));
        logger.methodExit(method);
    }

    @Override
    public void startConnectWorkFlow(ClientLoginInfo loginInfo) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        ConnectCommand command = new ConnectCommand(getRequestCommandExecutor(), this, clientUI, loginInfo, clientComunicationFactory);
        executeRequestCommandBase(command, new MethodCommandEvent(getClass(), "connect"));
        logger.methodExit(method);
    }

    @Override
    public UserSession startLoginWorkFlow(ClientLoginInfo loginInfo) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        LoginCommand command = new LoginCommand(getRequestCommandExecutor(), this, clientUI, loginInfo, session);
        executeRequestCommandBase(command, new MethodCommandEvent(getClass(), "login"));
        logger.methodExit(method);
        return session;
    }

    @Override
    public void initialize() {
        doLoginUI();
        isInitialized = true;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Creates programmatic default properties
     * 
     * @return default properties
     */
    protected Map<Object, Object> createDefaultClientProperties() {
        return null;
    }

    /** UI login action */
    private void doLoginUI() {
        Frame loginFrame = clientUI.getLoginFrame();
        loginFrame.initialize();
    }

    /**
     * Creates client with project ,service registry and clientCommunicationFactory
     * 
     * @param application
     * @param serviceRegistry
     * @param clientCommunicationFactory
     */
    public ClientImpl(Application application, MuttableServiceRegistry serviceRegistry, ClientCommunicationFactory clientCommunicationFactory) {
        this.application = Assert.isNotNullArgument(application, "application");
        this.serviceRegistry = Assert.isNotNullArgument(serviceRegistry, "ServiceRegistry");
        clientComunicationFactory = Assert.isNotNullArgument(clientCommunicationFactory, "clientCommunicationFactory");
        session = createLoginInfoAndEntrySession();
        clientInfo = generateClientInfo();
        status = ClientStatus.NOT_CONNECTED;
    }

    private UserSession createLoginInfoAndEntrySession() {
        User user = new User.UserBuilder().setLogin("system").build();
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e) {
        }
        CommunicationId commId = new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, "default");
        ApplContext applContext = new ApplContextBuilder().setId(0L).setCode("system").build();
        ClientConnectionInfo connectionInfo = new ClientConnectionInfo.ConnectionInfoBuilder().setCommunicationId(commId).setClientCommunicationSettings(new EmbeddedClientCommunicationSettings())
                .setClientDeploymentPlatform(ClientDeploymentPlatform.OFFLINE).setClientPlatform(ClientPlatform.CONSOLE).build();
        loginInfo = new ClientLoginInfo.LoginInfoBuilder().setClientInfo(clientInfo).setContext(TenementalContext.create(applContext)).setClientInfo(generateClientInfo())
                .setAuthenticationEntry(new UserAndPasswordAuthenticationEntry("system", "system".toCharArray())).setConnectionInfo(connectionInfo).setInetAddress(inetAddress).build();
        return new UserSession.Builder().setSessionId("system").setUser(user).setLoginInfo(loginInfo.createLoginInfo()).setLoginTime(System.currentTimeMillis()).build();
    }

    @Override
    public void shutdown() {
        MethodTimer mt = logger.methodEntry(Level.INFO);
        clientUI.beforeShutDown();
        if (clientServer == null) {
            exitVM(0);
            return;
        }
        else {
            ShutdownCommand command = new ShutdownCommand(getRequestCommandExecutor(), this, clientUI);
            executeRequestCommandBase(command, new MethodCommandEvent(getClass(), "shutdown"));
        }
        logger.methodExit(mt);
    }

    @Override
    public void exitVM(int status) {
        logger.log(Level.INFO, "Exiting client");
        if (clientUI.canShutdownJVM()) {
            System.exit(0);
        }
    }

    @Override
    public void logout() {
        if (session != null && clientServer != null) {
            logger.log(Level.INFO, "Logging out");
            clientUI.getWindowManager().disposeAllFrames();
            LogoutCommand command = new LogoutCommand(getRequestCommandExecutor(), this, clientUI, clientServer, session);
            executeRequestCommandBase(command, new MethodCommandEvent(getClass(), "logout"));
        }
    }

    @Override
    public ClientLoginInfo getLoginInfo() {
        return loginInfo;
    }

    @Override
    public UserSession getSession() {
        return session;
    }

    @Override
    public ClientPlatform getClientPlatform() {
        return session != null ? session.getLoginInfo().getConnectionInfo().getClientPlatform() : null;
    }

    @Override
    public FrameUIDef getMainFrameDef() {
        return mainFrameDef;
    }

    @Override
    public void logAndShowExceptionDialog(Throwable e, Class clazz, String msg, boolean throwRuntime) {
        if (clazz == null) {
            clazz = getClass();
        }
        TracedException tracedException = null;
        if (e instanceof TracedException) {
            tracedException = (TracedException) e;
        }
        else {
            Throwable cause = ExceptionUtil.getRootException(e, TracedException.class);
            if (cause instanceof TracedException) {
                tracedException = (TracedException) cause;
            }
            else {
                tracedException = new TracedRuntimeExceptionWrapper(e);
            }
        }
        if (!tracedException.isUserHandled()) {
            clientUI.showExceptionDialog((Throwable) tracedException, msg);
            tracedException.setUserHandled(true);
        }
        LoggerMethodTracer cl = LoggerMethodTracer.getLogger(clazz);
        if (!tracedException.isLogged()) {
            tracedException.setLogged(true);
            if (throwRuntime) {
                cl.logAndThrowRuntimeException(Level.SEVERE, msg, (Throwable) tracedException);
            }
            else {
                cl.log(Level.SEVERE, msg, (Throwable) tracedException);
            }
        }
        else {
            if (throwRuntime) {
                if (tracedException instanceof RuntimeException) {
                    throw (RuntimeException) tracedException;
                }
                else {
                    throw new TracedRuntimeExceptionWrapper(e);
                }
            }
        }

    }

    /**
     * @return client uis
     */
    @Override
    public ClientUI getClientUI() {
        return clientUI;
    }

    @Override
    public CommandExecutorWithRegistry getRequestCommandExecutor() {
        if (requestCommandsExecutor == null) {
            CommandExecutorRegistryImpl registry = new CommandExecutorRegistryImpl();
            CommandExecutorBase executor = new CommandExecutorBase(RequestCommandBaseConst.REQUEST_COMMAND_EXECUTOR);
            registry.registerCommandExecutor(executor);
            requestCommandsExecutor = new CommandExecutorWithRegistryImpl(executor, registry);
        }
        return requestCommandsExecutor;
    }

    private void executeRequestCommandBase(AbstractRequestCommand<?> command, CommandEvent event) {
        try {
            getRequestCommandExecutor().executeCommand(command, env, event);
        }
        catch (Exception e) {
            logAndShowExceptionDialog(e, command.getClass(), "Error running request command", true);
        }
    }

    void setSession(UserSession session) {
        this.session = session;
    }

    void setMainFrameDef(FrameUIDef mainFrameDef) {
        this.mainFrameDef = mainFrameDef;
    }

    @Override
    public ClientServer getClientServer() {
        return clientServer;
    }

    @Override
    public ClientResourceServices getClientServices() {
        return clientServices;
    }

    @Override
    public LoginServer getLoginServer() {
        return loginServer;
    }

    void setClientServer(ClientServer clientServer) {
        this.clientServer = clientServer;
    }

    void setClientServices(ClientResourceServices clientServices) {
        this.clientServices = clientServices;
    }

    void setLoginServer(LoginServer loginServer) {
        this.loginServer = loginServer;
        if (loginServer != null) {
            serverInfo = loginServer.getServerInfo(loginInfo.createLoginInfo());
        }
    }

    @Override
    public void setRequestCommandsExecutor(CommandExecutorWithRegistry ce) {
        this.requestCommandsExecutor = ce;
    }

    @Override
    public void setClientUI(ClientUI clientUI) {
        this.clientUI = clientUI;
    }

    @Override
    public MuttableServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public CommandExecutionEnv getCommandExecutionEnv() {
        return env;
    }

    @Override
    public void setCommandExecutionEnv(CommandExecutionEnv env) {
        this.env = env;
    }

    private ComponentInfo generateClientInfo() {
        if (clientInfo != null) {
            return clientInfo;
        }
        String id = "" + (clientId++);
        String name = null;
        Version.VersionBuilder versionBuilder = new Version.VersionBuilder();
        // Load version from properties file
        URL clientInfoURL = getClass().getResource("/META-INF/clientInfo.properties");
        if (clientInfoURL != null) {
            try {
                versionBuilder.loadFromProperties(PropertiesUtil.readPropertiesFromURL(clientInfoURL));
            }
            catch (IOException e) {
            }
            if (versionBuilder.getVersion() == null) {
                versionBuilder.setVersion(VersionUtil.getVpdaVersion());
            }
            try {
                name = PropertiesUtil.readPropertiesFromURL(clientInfoURL).getProperty("productName");
            }
            catch (IOException e) {
            }
        }
        else {
            versionBuilder.setProductName(CommonUtilConstants.VPDA_PROJECT_NAME);
            versionBuilder.setVersion("UNKNOWN");
        }
        ScmCommitInfo lastCommit = SCMUtil.getLastCommit();
        versionBuilder.setScmLastCommitInfo(lastCommit);
        if (StringUtil.isEmpty(name) || name.startsWith("$")) {
            name = "vpda-client";
        }
        clientInfo = new ComponentInfo.ComponentInfoBuilder().setId(id).setName(name).setVersion(versionBuilder.build()).setEnvironmentProperty("java.version", System.getProperty("java.version"))
                .build();
        logger.log(Level.INFO, "Client info : {0} ", clientInfo);
        return clientInfo;
    }

    @Override
    public ComponentInfo getClientInfo() {
        return clientInfo;
    }

    @Override
    public ComponentInfo getServerInfo() {
        return serverInfo;
    }

    @Override
    public ClientCommunicationFactory getClientCommunicationFactory() {
        return clientComunicationFactory;
    }

    @Override
    public ClientStatus getStatus() {
        return status;
    }

    void connected() {
        this.status = ClientStatus.CONNECTED;
    }

    void contextsAvailable() {
        this.status = ClientStatus.CONTEXTS_AVAILABLE;
    }

    void loggedIn() {
        this.status = ClientStatus.LOGGED_IN;
    }

    void running() {
        this.status = ClientStatus.RUNNING;
    }

    void loggedOut() {
        this.status = ClientStatus.NOT_CONNECTED;
    }

    void setLoginInfo(ClientLoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    @Override
    public String getClientConnectionIdentifier() {
        if (loginInfo == null || loginInfo.getConnectionInfo() == null || loginInfo.getConnectionInfo().getClientCommunicationSettings() == null) {
            return "NOT_CONNECTED";
        }
        ClientCommunicationSettings clientCommunicationSettings = loginInfo.getConnectionInfo().getClientCommunicationSettings();
        StringBuilder builder = new StringBuilder();
        builder.append(clientCommunicationSettings.getProtocol().getName().toLowerCase());
        if (clientCommunicationSettings instanceof HTTPClientCommunicationSettings) {
            HTTPClientCommunicationSettings hTTPClientCommunicationSettings = (HTTPClientCommunicationSettings) clientCommunicationSettings;
            builder.append("://").append(hTTPClientCommunicationSettings.getHost()).append(':').append(hTTPClientCommunicationSettings.getPort());
            if (hTTPClientCommunicationSettings.getRelativeUri().length() > 0 && '/' != hTTPClientCommunicationSettings.getRelativeUri().charAt(0)) {
                builder.append('/');
            }
            builder.append(hTTPClientCommunicationSettings.getRelativeUri());
        }
        else if (clientCommunicationSettings instanceof RMIClientCommunicationSettings) {
            RMIClientCommunicationSettings rMIClientCommunicationSettings = (RMIClientCommunicationSettings) clientCommunicationSettings;
            builder.append("://").append(rMIClientCommunicationSettings.getHost()).append(':').append(rMIClientCommunicationSettings.getPort());
            if (rMIClientCommunicationSettings.getServerBindingName().length() > 0 && '/' != rMIClientCommunicationSettings.getServerBindingName().charAt(0)) {
                builder.append('/');
            }
            builder.append(rMIClientCommunicationSettings.getServerBindingName());
        }
        return builder.toString();
    }
}
