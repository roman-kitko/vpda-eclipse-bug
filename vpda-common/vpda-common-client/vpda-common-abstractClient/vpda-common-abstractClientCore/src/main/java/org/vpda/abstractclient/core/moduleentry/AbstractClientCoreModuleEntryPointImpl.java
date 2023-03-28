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
package org.vpda.abstractclient.core.moduleentry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.vpda.abstractclient.core.Client;
import org.vpda.abstractclient.core.impl.ClientImpl;
import org.vpda.abstractclient.core.module.AbstractClientCoreModule;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.core.ui.ClientUIExceptionHandler;
import org.vpda.abstractclient.core.ui.ClientWithUI;
import org.vpda.client.clientcommunication.ClientComunicationFactoryImpl;
import org.vpda.client.clientcommunication.protocol.embedded.EmbeddedClientCommunication;
import org.vpda.client.clientcommunication.protocol.embedded.EmbeddedClientCommunicationImpl;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunication;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationImplNoneEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationImplSSLEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationNoneEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationSSLEncryption;
import org.vpda.client.clientcommunication.protocol.http.HTTPClientCommunicationSettings;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunication;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationImplNoneEncryption;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationImplSSLEncryption;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationNoneEncryption;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationSSLEncryption;
import org.vpda.client.clientcommunication.protocol.rmi.RMIClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientCommunicationNames;
import org.vpda.clientserver.clientcommunication.ClientSSLEncryptionSettings;
import org.vpda.clientserver.clientcommunication.MutableClientCommunicationFactory;
import org.vpda.clientserver.communication.BasicCommunicationKind;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.EncryptionType;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactoryDynamicProxy;
import org.vpda.clientserver.communication.module.ClientServerCoreModule;
import org.vpda.clientserver.communication.moduleentry.ClientServerCoreModuleEntryPoint;
import org.vpda.clientserver.communication.moduleentry.ClientServerCoreModuleEntryPointImpl;
import org.vpda.clientserver.communication.protocol.embedded.EmbeddedCommunicationConstants;
import org.vpda.clientserver.communication.protocol.http.HTTPClientServerConstants;
import org.vpda.clientserver.communication.protocol.rmi.RMIClientServerConstants;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorRegistry;
import org.vpda.common.command.CommandExecutorWithRegistry;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.executor.impl.CommandExecutorRegistryImpl;
import org.vpda.common.command.executor.impl.CommandExecutorWithRegistryImpl;
import org.vpda.common.command.request.RequestCommandBaseConst;
import org.vpda.common.comps.moduleentry.CommonComponentsModuleEntryPoint;
import org.vpda.common.comps.moduleentry.CommonComponentsModuleEntryPointImpl;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPoint;
import org.vpda.common.core.moduleEntry.CommonCoreModuleEntryPointImpl;
import org.vpda.common.entrypoint.AbstractModuleEntryPoint;
import org.vpda.common.entrypoint.AppEntryPoint;
import org.vpda.common.entrypoint.Application;
import org.vpda.common.entrypoint.Module;
import org.vpda.common.entrypoint.ModuleEntryPoint;
import org.vpda.common.ioc.objectresolver.PicoContainerObjectResolver;
import org.vpda.common.ioc.picocontainer.PicoHelper;
import org.vpda.common.service.MuttableServiceRegistry;
import org.vpda.common.service.ServiceRegistry;
import org.vpda.common.util.exceptions.ExceptionHandler;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;
import org.vpda.internal.clientserver.communication.CommunicationKeyHolder;
import org.vpda.internal.common.util.CacheKeyCreator;
import org.vpda.internal.common.util.StaticCache;

/**
 * Entry point implementation of abstract client module
 * 
 * @author rkitko
 *
 */
public abstract class AbstractClientCoreModuleEntryPointImpl extends AbstractModuleEntryPoint implements AbstractClientCoreModuleEntryPoint {
    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AbstractClientCoreModuleEntryPointImpl.class);

    /** Executor for menu commands */
    public static final String MENU_EXECUTOR = "MenuExecutor";

    /**
     * 
     */
    public AbstractClientCoreModuleEntryPointImpl() {
    }

    @Override
    protected Module createModule() {
        return AbstractClientCoreModule.getInstance();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ModuleEntryPoint> Class<T> getDefImplClassForModule(Class<T> moduleIface) {
        if (CommonCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) CommonCoreModuleEntryPointImpl.class;
        }
        else if (ClientServerCoreModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) ClientServerCoreModuleEntryPointImpl.class;
        }
        else if (CommonComponentsModuleEntryPoint.class.equals(moduleIface)) {
            return (Class<T>) CommonComponentsModuleEntryPointImpl.class;
        }
        return null;
    }

    @Override
    public void entryPoint(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        ((AbstractClientCoreModule) AbstractClientCoreModule.getInstance()).setModuleContainer(module.getApplication().getRootContainer(), module.getContainer());
        Application application = appEntryPoint.getApplication();
        MuttableServiceRegistry serviceRegistry = (MuttableServiceRegistry) PicoHelper.getMandatoryComponent(module.getContainer(), ServiceRegistry.class);
        ClientWithUI client = module.getContainer().getComponent(ClientWithUI.class);
        if (client == null) {
            ClientCommunicationFactory clientCommunicationFactory = registerClientCommunicationFactory(appEntryPoint, module);
            client = new ClientImpl(application, serviceRegistry, clientCommunicationFactory);
            module.getContainer().addComponent(ClientWithUI.class, client);
            module.getContainer().addComponent(Client.class, client);
        }
        try {
            startConnectors(appEntryPoint, module);
        }
        catch (CommunicationException e) {
            throw new VPDARuntimeException(e);
        }
        ClientUI clientUI = module.getContainer().getComponent(ClientUI.class);
        if (clientUI == null) {
            clientUI = createClientUI(client, appEntryPoint, module);
            // Abstract client will not create ui
            if (clientUI != null) {
                module.getContainer().addComponent(ClientUI.class, clientUI);
            }
        }
        if (module.getContainer().getComponent(ExceptionHandler.class) == null) {
            module.getContainer().addComponent(ExceptionHandler.class, new ClientUIExceptionHandler(clientUI));
        }

        createCommandExecutors(client, appEntryPoint, module);
        createCommandEnv(client, appEntryPoint, module);
        if (clientUI != null) {
            client.initialize();
        }
        logger.methodExit(method);
    }

    /**
     * Register ClientCommunicationFactory
     * 
     * @param appEntryPoint
     * @param module
     * @return ClientCommunicationFactory
     */
    protected ClientCommunicationFactory registerClientCommunicationFactory(AppEntryPoint appEntryPoint, Module module) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        ClientCommunicationFactory ccf = module.getContainer().getComponent(ClientCommunicationFactory.class);
        if (ccf == null) {
            @SuppressWarnings("unchecked")
            List<CommunicationId> communicationIds = (List<CommunicationId>) module.getContainer().getComponent("clientCommunicationProtocols");
            if (communicationIds == null) {
                communicationIds = createDefaultCommunicationIds();
            }
            MutableClientCommunicationFactory mccf = new ClientComunicationFactoryImpl();
            for (CommunicationId communicationId : communicationIds) {
                if (BasicCommunicationProtocol.RMI.equals(communicationId.getProtocol())) {
                    registerRMIClientCommunication(mccf, module, communicationId);
                }
                if (BasicCommunicationProtocol.HTTP.equals(communicationId.getProtocol())) {
                    registerHTTPClientCommunication(mccf, module, communicationId);
                }
                if (BasicCommunicationProtocol.EMBEDDED.equals(communicationId.getProtocol())) {
                    registerEmbeddedClientCommunication(mccf, module, communicationId);
                }
            }
            ccf = mccf;
            module.getContainer().addComponent(ClientCommunicationFactory.class, ccf);
        }
        logger.methodExit(method);
        return ccf;
    }

    private List<CommunicationId> createDefaultCommunicationIds() {
        List<CommunicationId> result = new ArrayList<>();
        result.add(new CommunicationId(BasicCommunicationProtocol.EMBEDDED, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME));
        result.add(new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME));
        result.add(new CommunicationId(BasicCommunicationProtocol.RMI, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME, EncryptionType.SSL));
        result.add(new CommunicationId(BasicCommunicationProtocol.HTTP, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME));
        result.add(new CommunicationId(BasicCommunicationProtocol.HTTP, BasicCommunicationKind.CLIENT_SERVER_COMMUNICATION, ClientCommunicationNames.DEFAULT_NAME, EncryptionType.SSL));
        return result;
    }

    /**
     * Register http client communication
     * 
     * @param mccf
     * @param module
     * @param communicationId
     */
    protected void registerHTTPClientCommunication(MutableClientCommunicationFactory mccf, Module module, CommunicationId communicationId) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        HTTPClientCommunication httClientCommunication = null;
        if (EncryptionType.NONE.equals(communicationId.getEncryptionType())) {
            httClientCommunication = module.getContainer().getComponent(HTTPClientCommunicationNoneEncryption.class);
            if (httClientCommunication == null) {
                HTTPClientCommunicationSettings settings = PicoHelper.getTypedComponent(module.getContainer(), HTTPClientCommunicationSettings.class, "HTTPClientCommunicationSettings-None");
                if (settings == null) {
                    settings = new HTTPClientCommunicationSettings();
                }
                httClientCommunication = new HTTPClientCommunicationImplNoneEncryption(mccf, communicationId, settings, new CommadExecutorClientProxyFactoryDynamicProxy());
                module.getContainer().addComponent(HTTPClientCommunicationNoneEncryption.class, httClientCommunication);
            }
        }
        else if (EncryptionType.SSL.equals(communicationId.getEncryptionType())) {
            httClientCommunication = module.getContainer().getComponent(HTTPClientCommunicationSSLEncryption.class);
            if (httClientCommunication == null) {
                HTTPClientCommunicationSettings settings = PicoHelper.getTypedComponent(module.getContainer(), HTTPClientCommunicationSettings.class, "HTTPClientCommunicationSettings-SSL");
                if (settings == null) {
                    settings = new HTTPClientCommunicationSettings.Builder().setEncryptionSettings(new ClientSSLEncryptionSettings()).setHost(HTTPClientServerConstants.LOCALHOST)
                            .setPort(HTTPClientServerConstants.HTTP_PORT_SSL).build();
                }
                httClientCommunication = new HTTPClientCommunicationImplSSLEncryption(mccf, communicationId, settings, new CommadExecutorClientProxyFactoryDynamicProxy());
                module.getContainer().addComponent(HTTPClientCommunicationSSLEncryption.class, httClientCommunication);
            }
        }
        mccf.registerClientCommunication(httClientCommunication);
        logger.methodExit(method);
    }

    /**
     * Register rmi client communication
     * 
     * @param mccf
     * @param module
     * @param communicationId
     */
    protected void registerRMIClientCommunication(MutableClientCommunicationFactory mccf, Module module, CommunicationId communicationId) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        RMIClientCommunication rMIClientComunication = null;
        if (EncryptionType.NONE.equals(communicationId.getEncryptionType())) {
            rMIClientComunication = module.getContainer().getComponent(RMIClientCommunicationNoneEncryption.class);
            if (rMIClientComunication == null) {
                RMIClientCommunicationSettings clientInitialSettings = PicoHelper.getTypedComponent(module.getContainer(), RMIClientCommunicationSettings.class, "RMIClientCommunicationSettings-None");
                ;
                if (clientInitialSettings == null) {
                    clientInitialSettings = new RMIClientCommunicationSettings.Builder().build();
                }
                rMIClientComunication = new RMIClientCommunicationImplNoneEncryption(mccf, communicationId, new CommadExecutorClientProxyFactoryDynamicProxy(), clientInitialSettings);
                module.getContainer().addComponent(RMIClientCommunicationNoneEncryption.class, rMIClientComunication);
            }
        }
        else if (EncryptionType.SSL.equals(communicationId.getEncryptionType())) {
            rMIClientComunication = module.getContainer().getComponent(RMIClientCommunicationSSLEncryption.class);
            if (rMIClientComunication == null) {
                RMIClientCommunicationSettings clientInitialSettings = PicoHelper.getTypedComponent(module.getContainer(), RMIClientCommunicationSettings.class, "RMIClientCommunicationSettings-SSL");
                ;
                if (clientInitialSettings == null) {
                    clientInitialSettings = new RMIClientCommunicationSettings.Builder().setPort(RMIClientServerConstants.RMI_PORT_SSL).setEncryptionSettings(new ClientSSLEncryptionSettings())
                            .build();
                }
                rMIClientComunication = new RMIClientCommunicationImplSSLEncryption(mccf, communicationId, new CommadExecutorClientProxyFactoryDynamicProxy(), clientInitialSettings);
                module.getContainer().addComponent(RMIClientCommunicationSSLEncryption.class, rMIClientComunication);
            }
        }
        mccf.registerClientCommunication(rMIClientComunication);
        logger.methodExit(method);
    }

    /**
     * Register embedded client communication
     * 
     * @param mccf
     * @param module
     * @param communicationId
     */
    protected void registerEmbeddedClientCommunication(MutableClientCommunicationFactory mccf, Module module, CommunicationId communicationId) {
        MethodTimer method = logger.methodEntry(Level.INFO);
        EmbeddedClientCommunication embeddedClientCommunication = module.getContainer().getComponent(EmbeddedClientCommunication.class);
        if (embeddedClientCommunication == null) {
            CommandExecutor loginServerCommandExecutor = (CommandExecutor) ClientServerCoreModule.getInstance().getContainer()
                    .getComponent(EmbeddedCommunicationConstants.LOGIN_SERVER_COMMAND_EXECUTOR_KEY);
            if (loginServerCommandExecutor == null) {
                // Change key to static cache to server one for current thread
                CacheKeyCreator oldCreator = null;
                try {
                    oldCreator = StaticCache.setKeyCreator(CommunicationKeyHolder.serverCacheKeyCreatorHolder.getCacheKeyCreator());
                    loginServerCommandExecutor = (CommandExecutor) ClientServerCoreModule.getInstance().getContainer().getComponent(EmbeddedCommunicationConstants.LOGIN_SERVER_COMMAND_EXECUTOR_KEY);
                }
                finally {
                    StaticCache.setKeyCreator(oldCreator);
                }
            }
            if (loginServerCommandExecutor != null) {
                embeddedClientCommunication = new EmbeddedClientCommunicationImpl(mccf, communicationId, loginServerCommandExecutor, new CommadExecutorClientProxyFactoryDynamicProxy());
                module.getContainer().addComponent(EmbeddedClientCommunication.class, embeddedClientCommunication);
            }
        }
        if (embeddedClientCommunication != null) {
            mccf.registerClientCommunication(embeddedClientCommunication);
        }
        logger.methodExit(method);
    }

    private CommandExecutionEnv createCommandEnv(ClientWithUI client, AppEntryPoint appEntryPoint, Module module) {
        CEEnvDelegate env = new CEEnvDelegate(new PicoContainerObjectResolver(appEntryPoint.getApplication().getAllModulesContainer()));
        client.setCommandExecutionEnv(env);
        return env;
    }

    /**
     * Creates client command executors
     * 
     * @param client
     * @param appEntryPoint
     * @param module
     */
    protected void createCommandExecutors(ClientWithUI client, AppEntryPoint appEntryPoint, Module module) {
        CommandExecutorRegistry commandExecutorRegistry = module.getContainer().getComponent(CommandExecutorRegistry.class);
        CommandExecutor menuExecutor = new CommandExecutorBase(MENU_EXECUTOR);
        commandExecutorRegistry.registerCommandExecutor(MENU_EXECUTOR, menuExecutor);
        CommandExecutorWithRegistry requestCommandExecutor = createRequestCommandExecutor(client);
        commandExecutorRegistry.registerCommandExecutor(RequestCommandBaseConst.RCB_EXECUTOR, requestCommandExecutor);
        client.setRequestCommandsExecutor(requestCommandExecutor);
    }

    /**
     * Creates request executor
     * 
     * @param client
     * @return request command executor with registry
     */
    protected CommandExecutorWithRegistry createRequestCommandExecutor(Client client) {
        CommandExecutorWithRegistry requestCommandExecutor = new CommandExecutorWithRegistryImpl(new CommandExecutorBase(RequestCommandBaseConst.RCB_EXECUTOR), new CommandExecutorRegistryImpl());
        CommandExecutor requestExecutor = new CommandExecutorBase(RequestCommandBaseConst.REQUEST_COMMAND_EXECUTOR);
        CommandExecutor exceptionHandlingExecutor = new CommandExecutorBase(RequestCommandBaseConst.EXCEPTION_HANDLING_COMMAND_EXECUTOR);
        CommandExecutor beforeRequestExecutor = new CommandExecutorBase(RequestCommandBaseConst.BEFORE_REQUEST_COMMAND_EXECUTOR);
        CommandExecutor afterRequestExecutor = new CommandExecutorBase(RequestCommandBaseConst.AFTER_REQUEST_COMMAND_EXECUTOR);
        requestCommandExecutor.registerCommandExecutor(RequestCommandBaseConst.REQUEST_COMMAND_EXECUTOR, requestExecutor);
        requestCommandExecutor.registerCommandExecutor(RequestCommandBaseConst.EXCEPTION_HANDLING_COMMAND_EXECUTOR, exceptionHandlingExecutor);
        requestCommandExecutor.registerCommandExecutor(RequestCommandBaseConst.BEFORE_REQUEST_COMMAND_EXECUTOR, beforeRequestExecutor);
        requestCommandExecutor.registerCommandExecutor(RequestCommandBaseConst.AFTER_REQUEST_COMMAND_EXECUTOR, afterRequestExecutor);
        return requestCommandExecutor;
    }

    @Override
    public List<Class<? extends ModuleEntryPoint>> getRequiredModuleEntryPointsClasses(AppEntryPoint appEntryPoint) {
        List<Class<? extends ModuleEntryPoint>> reqModules = new ArrayList<Class<? extends ModuleEntryPoint>>(2);
        reqModules.add(CommonCoreModuleEntryPoint.class);
        reqModules.add(ClientServerCoreModuleEntryPoint.class);
        reqModules.add(CommonComponentsModuleEntryPoint.class);
        return reqModules;
    }

    /**
     * Creates ClientUI
     * 
     * @param client
     * @param appEntryPoint
     * @param module
     * @return ClientUI
     */
    protected ClientUI createClientUI(ClientWithUI client, AppEntryPoint appEntryPoint, Module module) {
        return null;
    }

    /**
     * Start all connectors
     * 
     * @param appEntryPoint
     * @param module
     * @throws CommunicationException
     */
    protected void startConnectors(AppEntryPoint appEntryPoint, Module module) throws CommunicationException {
        MethodTimer method = logger.methodEntry(Level.INFO);
        // here launch connector
        MutableClientCommunicationFactory scf = (MutableClientCommunicationFactory) PicoHelper.getMandatoryComponent(module.getContainer(), ClientCommunicationFactory.class);
        for (CommunicationId commId : scf.getRegisteredCommunications()) {
            ClientCommunication communication = scf.createCommunication(commId);
            communication.start();
        }
        logger.methodExit(method);
    }

}
