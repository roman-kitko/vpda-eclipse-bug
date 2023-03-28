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
package org.vpda.client.clientcommunication.protocol.rmi;

import java.io.Serializable;
import java.net.Proxy;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.vpda.client.clientcommunication.AbstractClientCommunication;
import org.vpda.client.clientcommunication.CodebaseClassLoader;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientConnectionInfo;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactory;
import org.vpda.clientserver.communication.data.ClientDeploymentPlatform;
import org.vpda.clientserver.communication.protocol.rmi.RMIBootstrap;
import org.vpda.clientserver.communication.protocol.rmi.RMIClientServerConstants;
import org.vpda.clientserver.communication.protocol.rmi.RMIClientSocketFactoryHolder;
import org.vpda.clientserver.communication.protocol.rmi.RMICommandExecutor;
import org.vpda.clientserver.communication.protocol.rmi.RMICommandExecutorFactory;
import org.vpda.clientserver.communication.protocol.rmi.RMICommandExecutorWrapper;
import org.vpda.clientserver.communication.protocol.rmi.RetryingRMICommandExecutor;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.ChainedClassLoader;
import org.vpda.internal.common.util.Assert;

/**
 * RMI implementation of ClientCommunication
 * 
 * @author kitko
 *
 */
abstract class RMIClientCommunicationImpl extends AbstractClientCommunication implements RMIClientCommunication, Serializable {
    private static final long serialVersionUID = -6209395999352180967L;

    private final CommadExecutorClientProxyFactory commadExecutorClientProxyFactory;

    protected final RMIClientCommunicationSettings clientInitialSettings;

    /**
     * Creates RMIClientComunicationImpl
     * 
     * @param ccf
     * @param communicationId
     * @param commadExecutorClientProxyFactory
     *
     */
    RMIClientCommunicationImpl(ClientCommunicationFactory ccf, CommunicationId communicationId, CommadExecutorClientProxyFactory commadExecutorClientProxyFactory,
            RMIClientCommunicationSettings clientInitialSettings) {
        super(ccf, communicationId);
        this.commadExecutorClientProxyFactory = Assert.isNotNullArgument(commadExecutorClientProxyFactory, "commadExecutorClientProxyFactory");
        this.clientInitialSettings = clientInitialSettings;
    }

    @Override
    public Object createStatefullProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces) {
        return commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, executor, env, ifaces);
    }

    @Override
    public Object createStatelessProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces, ClientLoginInfo loginInfo) {
        return commadExecutorClientProxyFactory.createStatelessProxy(ccf, commId, executor, env, loginInfo, ifaces);
    }

    @Override
    public ClientCommunicationSettings getCommunicationSettings() {
        return clientInitialSettings;
    }

    private RMICommandExecutor createRMICommandExecutor(ClientLoginInfo loginInfo) {
        RMICommandExecutor rmiCommandExecutor = null;
        if (isWebstart(loginInfo)) {
            // Webstart comes with some JNLP RMI classloader that will not work with
            // application server
            System.clearProperty("java.rmi.server.RMIClassLoaderSpi");
        }
        try {
            ClientConnectionInfo connectionInfo = loginInfo.getConnectionInfo();
            RMIClientCommunicationSettings clientSettings = (RMIClientCommunicationSettings) connectionInfo.getClientCommunicationSettings();
            RMIBootstrap bootstrap = createRMIBootstrap(clientSettings);
            rmiCommandExecutor = bootstrap.getRMICommandExecutor(null);
        }
        catch (NotBoundException e) {
            throw new VPDARuntimeException(e);
        }
        catch (RemoteException e) {
            throw new VPDARuntimeException(e);
        }
        return rmiCommandExecutor;
    }

    private RMICommandExecutorFactory createRMICommandExecutorFactory(final ClientLoginInfo loginInfo) {
        return new RMICommandExecutorFactory() {
            @Override
            public RMICommandExecutor createRMICommandExecutor() {
                return RMIClientCommunicationImpl.this.createRMICommandExecutor(loginInfo);
            }
        };
    }

    @Override
    public RMIBootstrap createRMIBootstrap(RMIClientCommunicationSettings clientSettings) throws RemoteException, NotBoundException {
        RMIClientSocketFactory clientSocketFactory = createClientSocketFactory(clientSettings);
        RMIClientSocketFactoryHolder.setDelegate(clientSocketFactory);
        Registry registry = LocateRegistry.getRegistry(clientSettings.getHost(), clientSettings.getPort(), clientSocketFactory);
        RMIBootstrap bootstrap = (RMIBootstrap) registry.lookup(clientSettings.getServerBindingName());
        return bootstrap;
    }

    protected abstract RMIClientSocketFactory createClientSocketFactory(RMIClientCommunicationSettings clientSettings);

    private boolean isWebstart(ClientLoginInfo loginInfo) {
        return ClientDeploymentPlatform.WEBSTART.equals(loginInfo.getConnectionInfo().getClientDeploymentPlatform());
    }

    @Override
    public CommunicationProtocol getProtocol() {
        return BasicCommunicationProtocol.RMI;
    }

    /**
     * @return CommadExecutorClientProxyFactory
     */
    protected CommadExecutorClientProxyFactory getCommadExecutorClientProxyFactory() {
        return commadExecutorClientProxyFactory;
    }

    @Override
    public LoginServer connect(ClientLoginInfo loginInfo) throws CommunicationException {
        RMICommandExecutor rmiCommandExecutor = createRMICommandExecutor(loginInfo);
        CommandExecutor loginExecutor = new RMICommandExecutorWrapper(RMIClientServerConstants.RMI_LOGIN_COMMAND_EXECUTOR_ID, rmiCommandExecutor);
        LoginServer loginServer = (LoginServer) commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, loginExecutor, EmptyCommandExecutionEnv.getInstance(),
                new Class[] { LoginServer.class });
        String codebase = loginServer.getClassPathURL(loginInfo.createLoginInfo());

        final ClassLoader ctxLoader = Thread.currentThread().getContextClassLoader();
        ChainedRMIClassLoaderSpi delegateSpi = new ChainedRMIClassLoaderSpi(new UseClassLoaderRMILoaderSpi(new RMICommandExecutorClassLoader(rmiCommandExecutor, ctxLoader)),
                new ForceCodebaseRMIClassLoaderSpi(codebase));
        DynamicRMIClassLoaderSpi.setDelegate(delegateSpi);
        return loginServer;
    }

    @Override
    public StatelessClientServerEntry getStatelessClientServerEntry(ClientLoginInfo loginInfo) throws CommunicationException {
        RMICommandExecutor rmiCommandExecutor = createRMICommandExecutor(loginInfo);
        CommandExecutor loginExecutor = new RMICommandExecutorWrapper(RMIClientServerConstants.RMI_LOGIN_COMMAND_EXECUTOR_ID, rmiCommandExecutor);
        StatelessClientServerEntry registry = (StatelessClientServerEntry) commadExecutorClientProxyFactory.createStatelessProxy(ccf, commId, loginExecutor, EmptyCommandExecutionEnv.getInstance(),
                loginInfo, new Class[] { StatelessClientServerEntry.class });

        final ClassLoader ctxLoader = Thread.currentThread().getContextClassLoader();
        ChainedRMIClassLoaderSpi delegateSpi = new ChainedRMIClassLoaderSpi(
                new UseClassLoaderRMILoaderSpi(new RMICommandExecutorClassLoader(new RetryingRMICommandExecutor(createRMICommandExecutorFactory(loginInfo), rmiCommandExecutor), ctxLoader))

        );
        DynamicRMIClassLoaderSpi.setDelegate(delegateSpi);
        return registry;
    }

    @Override
    public CommandExecutor getStatelessClientServerEntryExecutor(ClientLoginInfo loginInfo) throws CommunicationException {
        RMICommandExecutor rmiCommandExecutor = createRMICommandExecutor(loginInfo);
        CommandExecutor loginExecutor = new RMICommandExecutorWrapper(RMIClientServerConstants.RMI_LOGIN_COMMAND_EXECUTOR_ID, rmiCommandExecutor);
        return loginExecutor;
    }

    @Override
    public boolean shouldStatelessEntryTryToReconnectOnThisFailure(Exception e) {
        if (e instanceof java.rmi.NoSuchObjectException || e.getCause() instanceof java.rmi.NoSuchObjectException) {
            return true;
        }
        return super.shouldStatelessEntryTryToReconnectOnThisFailure(e);
    }

}
