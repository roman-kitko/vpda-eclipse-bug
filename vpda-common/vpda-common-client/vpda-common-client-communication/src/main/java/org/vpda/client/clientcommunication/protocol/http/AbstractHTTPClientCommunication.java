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
package org.vpda.client.clientcommunication.protocol.http;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.vpda.client.clientcommunication.AbstractClientCommunication;
import org.vpda.client.clientcommunication.CodebaseClassLoader;
import org.vpda.client.clientcommunication.LoginServerClassLoader;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientCommunicationSettings;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.BasicCommunicationProtocol;
import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.EncryptionType;
import org.vpda.clientserver.communication.command.CommadExecutorClientProxyFactory;
import org.vpda.clientserver.communication.module.ClientServerCoreModule;
import org.vpda.clientserver.communication.protocol.http.ExecutorIdentifier;
import org.vpda.clientserver.communication.protocol.http.HTTPClientServerConstants;
import org.vpda.clientserver.communication.protocol.http.HTTPCommandExecutorWrapper;
import org.vpda.clientserver.communication.protocol.http.HTTPConnectSettings;
import org.vpda.clientserver.communication.protocol.http.MultiPingCommand;
import org.vpda.clientserver.communication.services.ClientServerClassLoader;
import org.vpda.clientserver.communication.services.LoginServer;
import org.vpda.clientserver.communication.services.StatelessClientServerEntry;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.env.PicoContainerExecutionEnv;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.service.ServiceKind;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.internal.common.ChainedClassLoader;
import org.vpda.internal.common.util.Assert;

/**
 * Use apache HTTP client to communicate with server using HTTP protocol. It
 * will call servlet on server side.
 * 
 * @author kitko
 *
 */
abstract class AbstractHTTPClientCommunication extends AbstractClientCommunication implements HTTPClientCommunication, Serializable {
    private static final long serialVersionUID = 1327485216190884737L;
    private final CommadExecutorClientProxyFactory commadExecutorClientProxyFactory;
    private final List<WeakReference<HTTPCommandExecutorWrapper>> executors;
    private ScheduledExecutorService scheduler;
    private ExecutorService pinger;
    private final HTTPClientCommunicationSettings initSettings;
    private static final Logger logger = LoggerMethodTracer.getLogger(AbstractHTTPClientCommunication.class);

    private org.apache.hc.client5.http.classic.HttpClient lastHTTPClient;
    private HTTPClientCommunicationSettings lastEffectiveSettings;

    /**
     * Creates HTTPClientCommunicationImpl
     * 
     * @param ccf
     * @param communicationId
     * @param settings
     * @param commadExecutorClientProxyFactory
     */
    AbstractHTTPClientCommunication(ClientCommunicationFactory ccf, CommunicationId communicationId, HTTPClientCommunicationSettings settings,
            CommadExecutorClientProxyFactory commadExecutorClientProxyFactory) {
        super(ccf, communicationId);
        this.initSettings = Assert.isNotNullArgument(settings, "settings");
        this.commadExecutorClientProxyFactory = Assert.isNotNullArgument(commadExecutorClientProxyFactory, "commadExecutorClientProxyFactory");
        executors = new ArrayList<WeakReference<HTTPCommandExecutorWrapper>>(2);
    }

    @Override
    public LoginServer connect(ClientLoginInfo loginInfo) throws CommunicationException {
        logger.info("Connecting to server using http");
        LazyClientServerClassLoader lazyLoginServer = new LazyClientServerClassLoader();
        HTTPClientCommandExecutorWrapperWithSettings wrapperWithSettings = createHTTPCommandExecutorWrapper(loginInfo, lazyLoginServer, ServiceKind.Statefull,
                HTTPClientServerConstants.HTTP_STATEFULL_LOGIN_COMMAND_EXECUTOR_ID);
        HTTPConnectSettings connectSettings = wrapperWithSettings.connectSettings;
        HTTPCommandExecutorWrapper wrapper = wrapperWithSettings.wrapper;
        CommandExecutionEnv env = new CEEnvDelegate(new MacroObjectResolverImpl(new SingleObjectResolver<>(org.apache.hc.client5.http.classic.HttpClient.class, wrapperWithSettings.httpClient),
                new SingleObjectResolver<HTTPConnectSettings>(HTTPConnectSettings.class, connectSettings), new PicoContainerExecutionEnv(ClientServerCoreModule.getInstance().getContainer())));

        LoginServer loginServer = (LoginServer) commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, wrapper, env, new Class[] { LoginServer.class });
        lazyLoginServer.setDelegate(loginServer);
        // Just resolve classloader here
        loginServer.getClassPathURL(loginInfo.createLoginInfo());
        return loginServer;
    }

    private HTTPClientCommandExecutorWrapperWithSettings createHTTPCommandExecutorWrapper(ClientLoginInfo loginInfo, ClientServerClassLoader clientServerClassLoader, ServiceKind serviceKind,
            String executorId) throws CommunicationException {
        logger.info("Connecting to server using http");
        HTTPClientCommunicationSettings effectiveSettings = null;
        HTTPClientCommunicationSettings clientSettings = (HTTPClientCommunicationSettings) loginInfo.getConnectionInfo().getClientCommunicationSettings();
        HTTPClientCommunicationSettings.Builder builder = new HTTPClientCommunicationSettings.Builder().setValue(clientSettings);
        builder.setPingPeriod(initSettings.getPingPeriod());
        effectiveSettings = builder.build();
        HTTPConnectSettings connectSettings = convertHTTPClientCommunicationSettingsToConnectSettings(effectiveSettings, serviceKind);
        logger.log(Level.INFO, "Using HTTPConnectSettings {0} to connect", connectSettings);
        org.apache.hc.client5.http.classic.HttpClient httpClient = createHttpClient(effectiveSettings);
        HTTPCommandExecutorWrapper wrapper = new HTTPCommandExecutorWrapper(new ExecutorIdentifier(executorId, 0), httpClient, connectSettings,
                new ResolveClassLoaderCall(clientServerClassLoader, loginInfo));

        lastHTTPClient = httpClient;
        lastEffectiveSettings = effectiveSettings;

        return new HTTPClientCommandExecutorWrapperWithSettings(httpClient, wrapper, connectSettings);
    }

    private org.apache.hc.client5.http.classic.HttpClient createHttpClient(HTTPClientCommunicationSettings effectiveSettings) throws CommunicationException {

        try {
            org.apache.hc.client5.http.SchemePortResolver schemePortResolver = new org.apache.hc.client5.http.impl.DefaultSchemePortResolver();
            org.apache.hc.client5.http.routing.HttpRoutePlanner routePlanner = null;
            if (!determineHttpProxies(effectiveSettings.getProxies()).isEmpty()) {
                routePlanner = new org.apache.hc.client5.http.impl.routing.SystemDefaultRoutePlanner(schemePortResolver, new MyProxySelector(determineHttpProxies(effectiveSettings.getProxies())));
            }
            else {
                routePlanner = new org.apache.hc.client5.http.impl.routing.DefaultRoutePlanner(schemePortResolver);
            }
            org.apache.hc.core5.http.config.Registry<org.apache.hc.client5.http.socket.ConnectionSocketFactory> socketFactoryRegistry = createSchemeRegistry(effectiveSettings);
            org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager polingHttpClientConnectionManager = new org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            polingHttpClientConnectionManager.setDefaultMaxPerRoute(4);
            org.apache.hc.client5.http.impl.classic.HttpClientBuilder clientBuilder = org.apache.hc.client5.http.impl.classic.HttpClientBuilder.create();
            clientBuilder.setConnectionManager(polingHttpClientConnectionManager);
            clientBuilder.setRoutePlanner(routePlanner);
            CloseableHttpClient client = clientBuilder.build();
            return client;
        }
        catch (Exception e) {
            throw new CommunicationException("Cannot create HttpClient", e);
        }
    }

    private static final class HTTPClientCommandExecutorWrapperWithSettings {
        final HTTPCommandExecutorWrapper wrapper;
        final HTTPConnectSettings connectSettings;
        final org.apache.hc.client5.http.classic.HttpClient httpClient;

        private HTTPClientCommandExecutorWrapperWithSettings(org.apache.hc.client5.http.classic.HttpClient httpClient, HTTPCommandExecutorWrapper wrapper, HTTPConnectSettings connectSettings) {
            super();
            this.httpClient = httpClient;
            this.wrapper = wrapper;
            this.connectSettings = connectSettings;
        }
    }

    @Override
    public CommandExecutor getStatelessClientServerEntryExecutor(ClientLoginInfo loginInfo) throws CommunicationException {
        LazyClientServerClassLoader lazyClientServerClassLoader = new LazyClientServerClassLoader();
        HTTPClientCommandExecutorWrapperWithSettings wrapperWithSettings = createHTTPCommandExecutorWrapper(loginInfo, lazyClientServerClassLoader, ServiceKind.Stateless,
                HTTPClientServerConstants.HTTP_STATELESS_ENTRY_COMMAND_EXECUTOR_ID);
        return wrapperWithSettings.wrapper;
    }

    @Override
    public StatelessClientServerEntry getStatelessClientServerEntry(ClientLoginInfo loginInfo) throws CommunicationException {
        LazyClientServerClassLoader lazyClientServerClassLoader = new LazyClientServerClassLoader();
        HTTPClientCommandExecutorWrapperWithSettings wrapperWithSettings = createHTTPCommandExecutorWrapper(loginInfo, lazyClientServerClassLoader, ServiceKind.Stateless,
                HTTPClientServerConstants.HTTP_STATELESS_ENTRY_COMMAND_EXECUTOR_ID);
        CommandExecutionEnv env = new CEEnvDelegate(new MacroObjectResolverImpl(new SingleObjectResolver<>(org.apache.hc.client5.http.classic.HttpClient.class, wrapperWithSettings.httpClient),
                new SingleObjectResolver<HTTPConnectSettings>(HTTPConnectSettings.class, wrapperWithSettings.connectSettings),
                new PicoContainerExecutionEnv(ClientServerCoreModule.getInstance().getContainer())));
        StatelessClientServerEntry registry = (StatelessClientServerEntry) commadExecutorClientProxyFactory.createStatelessProxy(ccf, commId, wrapperWithSettings.wrapper, env, loginInfo,
                new Class[] { StatelessClientServerEntry.class });
        lazyClientServerClassLoader.setDelegate(registry);
        registry.getClassPathURL(loginInfo.createLoginInfo());// Resolve class loader
        return registry;
    }

    private static HTTPConnectSettings convertHTTPClientCommunicationSettingsToConnectSettings(HTTPClientCommunicationSettings clientCommunicationSettings, ServiceKind servicekInd) {
        String scheme = EncryptionType.NONE.equals(clientCommunicationSettings.getEncryptionSettings().getType()) ? "http" : "https";
        HTTPConnectSettings.Builder builder = new HTTPConnectSettings.Builder();
        builder.setHost(clientCommunicationSettings.getHost()).setPort(clientCommunicationSettings.getPort()).setScheme(scheme);
        builder.setRelativeURI(clientCommunicationSettings.getRelativeUri());
        HTTPConnectSettings connectSettings = builder.build();
        return connectSettings;
    }

    private static List<Proxy> determineHttpProxies(List<Proxy> proxies) {
        List<Proxy> result = new ArrayList<Proxy>();
        for (Proxy proxy : proxies) {
            if (Proxy.Type.HTTP.equals(proxy.type())) {
                result.add(proxy);
            }
        }
        return result;
    }

    private static final class ResolveClassLoaderCall implements Callable<ClassLoader> {
        private final ClientServerClassLoader clientServerClassLoader;
        private final ClientLoginInfo loginInfo;

        private ResolveClassLoaderCall(ClientServerClassLoader clientServerClassLoader, ClientLoginInfo loginInfo) {
            this.clientServerClassLoader = clientServerClassLoader;
            this.loginInfo = loginInfo;
        }

        @Override
        public ClassLoader call() throws Exception {
            HTTPClientCommunicationSettings clientSettings = (HTTPClientCommunicationSettings) loginInfo.getConnectionInfo().getClientCommunicationSettings();
            String codebase = clientServerClassLoader.getClassPathURL(loginInfo.createLoginInfo());
            if (codebase.startsWith("$SERVER_URL")) {
                String protocol = clientSettings.getEncryptionSettings().getType().equals(EncryptionType.NONE) ? "http" : "https";
                codebase = protocol + "://" + clientSettings.getHost() + ":" + clientSettings.getPort() + codebase.substring("$SERVER_URL".length());
            }
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader codebaseClassLoader = new CodebaseClassLoader(codebase, determineHttpProxies(clientSettings.getProxies()), contextClassLoader);
            LoginServerClassLoader loginServerClassLoader = new LoginServerClassLoader(clientServerClassLoader, loginInfo.createLoginInfo(), contextClassLoader);
            return new ChainedClassLoader(contextClassLoader, loginServerClassLoader, codebaseClassLoader);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object createStatefullProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces) {
        return commadExecutorClientProxyFactory.createStatefullProxy(ccf, commId, executor, env, ifaces);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object createStatelessProxy(CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces, ClientLoginInfo loginInfo) {
        return commadExecutorClientProxyFactory.createStatelessProxy(ccf, commId, executor, env, loginInfo, ifaces);
    }

    @Override
    public BasicCommunicationProtocol getProtocol() {
        return BasicCommunicationProtocol.HTTP;
    }

    @Override
    public void start() throws CommunicationException {
    }

    protected abstract org.apache.hc.core5.http.config.Registry<org.apache.hc.client5.http.socket.ConnectionSocketFactory> createSchemeRegistry(HTTPClientCommunicationSettings settings)
            throws Exception;

    @Override
    @SuppressWarnings("rawtypes")
    public void notifyNewServerRef(Object serverRef, Object clientProxy, Class[] ifaces) {
        if (scheduler == null) {
            createScheduler();
        }
        HTTPCommandExecutorWrapper executor = (HTTPCommandExecutorWrapper) serverRef;
        synchronized (executors) {
            executors.add(new WeakReference<HTTPCommandExecutorWrapper>(executor));
        }
    }

    private void createScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(new PingAll(), lastEffectiveSettings.getPingPeriod(), lastEffectiveSettings.getPingPeriod(), TimeUnit.MILLISECONDS);
        pinger = Executors.newCachedThreadPool();
    }

    private final class PingAll implements Runnable {
        @Override
        public void run() {
            pingExecutors();
        }
    }

    private void pingExecutors() {
        synchronized (executors) {
            HTTPCommandExecutorWrapper exec = null;
            final Collection<ExecutorIdentifier> col = new ArrayList<ExecutorIdentifier>();
            for (Iterator<WeakReference<HTTPCommandExecutorWrapper>> i = executors.iterator(); i.hasNext();) {
                final HTTPCommandExecutorWrapper executor = i.next().get();
                if (executor != null) {
                    if (exec == null) {
                        exec = executor;
                    }
                    col.add(executor.getExecutorIdentifier());
                }
            }
            if (exec != null) {
                pinger.execute(new Pinger(exec, lastHTTPClient, lastEffectiveSettings, col));
            }
        }
    }

    @Override
    public ClientCommunicationSettings getCommunicationSettings() {
        return initSettings;
    }

    private static final class Pinger implements Runnable {
        private static final Logger logger = LoggerMethodTracer.getLogger(Pinger.class);
        private final HTTPCommandExecutorWrapper exec;
        private final Collection<ExecutorIdentifier> col;
        private final org.apache.hc.client5.http.classic.HttpClient httpClient;
        private final HTTPClientCommunicationSettings effectiveSettings;

        private Pinger(HTTPCommandExecutorWrapper exec, org.apache.hc.client5.http.classic.HttpClient httpClient, HTTPClientCommunicationSettings effectiveSettings,
                Collection<ExecutorIdentifier> col) {
            this.exec = exec;
            this.httpClient = httpClient;
            this.effectiveSettings = effectiveSettings;
            this.col = col;
        }

        @Override
        public void run() {
            try {
                HTTPConnectSettings connectSettings = convertHTTPClientCommunicationSettingsToConnectSettings(effectiveSettings, ServiceKind.Statefull);
                CommandExecutionEnv env = new CEEnvDelegate(new MacroObjectResolverImpl(new SingleObjectResolver<>(org.apache.hc.client5.http.classic.HttpClient.class, httpClient),
                        new SingleObjectResolver<HTTPConnectSettings>(HTTPConnectSettings.class, connectSettings), new PicoContainerExecutionEnv(ClientServerCoreModule.getInstance().getContainer())));
                logger.info("Ping server using http communication");
                exec.executeCommand(new MultiPingCommand(col), env, null);
            }
            catch (Exception e) {
                logger.log(Level.WARNING, "Error pinging server", e);
            }
        }
    }

    private static final class MyProxySelector extends ProxySelector {
        private static final Logger logger = Logger.getLogger(MyProxySelector.class.getName());
        private final List<Proxy> proxies;

        MyProxySelector(List<Proxy> proxies) {
            this.proxies = new ArrayList<Proxy>(proxies);
        }

        @Override
        public List<Proxy> select(URI uri) {
            return Collections.unmodifiableList(proxies);
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            logger.log(Level.WARNING, "Error while connecting using proxy " + sa, ioe);
        }

    }

}
