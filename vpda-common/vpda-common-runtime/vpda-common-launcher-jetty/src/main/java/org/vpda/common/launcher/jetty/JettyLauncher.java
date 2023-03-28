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
package org.vpda.common.launcher.jetty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.core.shutdown.ShutdownLevel;
import org.vpda.common.core.shutdown.ShutdownRegistry;
import org.vpda.common.entrypoint.AppLauncherHelper;
import org.vpda.common.util.exceptions.VPDAConfigurationRuntimeException;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.MethodTimer;
import org.vpda.internal.common.util.PropertiesResolver;
import org.vpda.internal.common.util.ResourceLoader;
import org.xml.sax.SAXException;

/**
 * Launches embedded jetty web server.
 * 
 * @author kitko
 *
 */
public final class JettyLauncher {
    /** Classpath or file path to jetty.xml file */
    public static final String JETTY_CONF = "vpda.jetty.conf.xml";

    private static final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(JettyLauncher.class);
    private Server server;

    private static final boolean JETTY_INITIALIZED;

    static {
        try {
            staticJettyInit();
        }
        catch (Exception e) {
            throw new VPDARuntimeException("Cannot initialize jetty", e);
        }
        JETTY_INITIALIZED = true;
    }

    /**
     * Launches jetty with applications
     * 
     * @param apps
     * @param xmlConfig
     * @throws Exception
     */
    public void launch(List<WebAppContext> apps, XmlConfiguration xmlConfig) throws Exception {
        MethodTimer method = logger.methodEntry(Level.INFO);
        server = new Server();
        HandlerCollection handler = new ContextHandlerCollection();
        for (WebAppContext app : apps) {
            handler.addHandler(app);
        }
        server.setHandler(handler);
        if (xmlConfig != null) {
            xmlConfig.configure(server);
        }
        logger.methodProgress(method, "JETTY_START_BEGIN", "Starting jetty server");
        for (Connector connector : server.getConnectors()) {
            if (connector instanceof ServerConnector serverConnector) {
                if (connector.getConnectionFactory(SslConnectionFactory.class) != null) {
                    String portProperty = "org.eclipse.jetty.server.ssl.SslSelectChannelConnector.port";
                    String port = System.getProperty(portProperty);
                    if (port != null) {
                        serverConnector.setPort(Integer.valueOf(port));
                    }
                    SslConnectionFactory factory = connector.getConnectionFactory(SslConnectionFactory.class);
                    SslContextFactory sslContextFactory = factory.getSslContextFactory();

                    Resource keyStoreResource = sslContextFactory.getKeyStoreResource();
                    Resource trustStoreResource = sslContextFactory.getTrustStoreResource();
                    if (keyStoreResource != null) {
                        logger.methodProgress(method, "PRINT_SSL_KEYSTORE", "Using keystore {0}", keyStoreResource);
                    }
                    if (trustStoreResource != null) {
                        logger.methodProgress(method, "PRINT_SSL_TRUSTSTORE", "Using trustStoreResource {0}", keyStoreResource);
                    }
                }
                else if (connector.getConnectionFactory(HttpConnectionFactory.class) != null) {
                    String portProperty = "org.eclipse.jetty.server.nio.BlockingChannelConnector.port";
                    String port = System.getProperty(portProperty);
                    if (port != null) {
                        serverConnector.setPort(Integer.valueOf(port));
                    }
                    if (System.getProperty("org.vpda.webservices.port") == null) {
                        System.setProperty("org.vpda.webservices.port", "" + serverConnector.getPort());
                    }
                }
            }
        }
        final org.vpda.internal.common.util.Holder<Server> serverHolder = new org.vpda.internal.common.util.Holder<Server>(server);
        org.eclipse.jetty.util.component.LifeCycle.Listener listener = new org.eclipse.jetty.util.component.LifeCycle.Listener() {

            @Override
            public void lifeCycleStarted(LifeCycle event) {
                JettyHttpServerProvider.setServer(serverHolder.getValue());
                JettyServerHolder.setServer(serverHolder.getValue());
                logger.info("Jetty lifecycle started");
            }

        };
        server.addEventListener(listener);
        server.start();
        logger.methodProgress(method, "JETTY_START_END", "Jetty server started");
        registerShutdown();
        logger.methodExit(method);
    }

    /**
     * Initialize jetty
     * 
     * @throws Exception
     */
    public static void staticJettyInit() throws Exception {
        if (JETTY_INITIALIZED) {
            return;
        }
        AppLauncherHelper.loadLoggingConfiguration();
        System.setProperty("com.sun.net.httpserver.HttpServerProvider", org.vpda.common.launcher.jetty.JettyHttpServerProvider.class.getName());

    }

    /**
     * Launches jetty , configuration is loaded by system property
     * vpda.jetty.conf.xml
     * 
     * @param apps
     * @throws Exception
     */
    public void launch(List<WebAppContext> apps) throws Exception {
        XmlConfiguration cfg = loadJettyConfigurationBySystemProperty();
        launch(apps, cfg);
    }

    private void registerShutdown() {
        Command<Object> cmd = new Command<Object>() {
            @Override
            public Object execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                stop();
                return null;
            }
        };
        ShutdownRegistry sr = org.vpda.common.core.module.CommonCoreModule.getInstance().getContainer().getComponent(ShutdownRegistry.class);
        if (sr != null) {
            sr.addShutdownCommand(cmd, ShutdownLevel.CLIENT_CONNECTORS);
        }
    }

    private void stop() {
        try {
            server.stop();
        }
        catch (Exception e) {
        }
    }

    private XmlConfiguration loadJettyConfigurationBySystemProperty() {
        String path = System.getProperty(JETTY_CONF);
        if (path == null) {
            throw new IllegalStateException("Missing [" + JETTY_CONF + "] property");
        }
        return loadJettyConfigurationFromPath(path, Thread.currentThread().getContextClassLoader());
    }

    /**
     * Will load jetty configuration from path
     * 
     * @param xmlPath
     * @param loader
     * @return XmlConfiguration
     */
    public static XmlConfiguration loadJettyConfigurationFromPath(String xmlPath, ClassLoader loader) {
        xmlPath = PropertiesResolver.resolveValue(xmlPath, System.getProperties());
        URL url = null;
        if (xmlPath.startsWith("resource:")) {
            String path = xmlPath.substring("resource:".length());
            url = loader.getResource(path);
            if (url == null) {
                url = loader.getResource("/" + path);
            }
            if (url == null) {
                Optional<URL> resolveResourceFromLoaderAndModules = ResourceLoader.resolveResourceFromLoaderAndModules(path, loader, JettyLauncher.class);
                if (resolveResourceFromLoaderAndModules.isPresent()) {
                    url = resolveResourceFromLoaderAndModules.get();
                }
            }
            if (url == null) {
                Collection<URL> resourcesFromLoaderAndModules = ResourceLoader.getResourcesFromLoaderAndModules(path, loader, JettyLauncher.class);

                throw new VPDAConfigurationRuntimeException("Cannot load jetty configuration resource from path : " + path);
            }
        }
        else if (xmlPath.startsWith("file:")) {
            String path = xmlPath.substring("file:".length());
            File file = new File(path);
            if (!file.exists()) {
                throw new VPDAConfigurationRuntimeException("Jetty xml configuration file does not exist : " + path);
            }
            if (!file.canRead()) {
                throw new VPDAConfigurationRuntimeException("Jetty xml cnfiguration file is not readable : " + path);
            }
            try {
                url = file.toURI().toURL();
            }
            catch (MalformedURLException e) {
                throw new VPDAConfigurationRuntimeException("Jetty xml url error", e);
            }
        }
        else if (xmlPath.startsWith("url:")) {
            String path = xmlPath.substring("url:".length());
            try {
                url = new URL(path);
            }
            catch (MalformedURLException e) {
                throw new VPDAConfigurationRuntimeException("Jetty xml url error", e);
            }
        }
        else {
            throw new VPDAConfigurationRuntimeException("Cannot load Jetty xml from " + xmlPath);
        }
        try {
            InputStream stream = url.openStream();
            stream.close();
        }
        catch (IOException e) {
            throw new VPDAConfigurationRuntimeException("Cannot open Jetty xml stream", e);
        }
        try {
            return new XmlConfiguration(Resource.newResource(url));
        }
        catch (SAXException e) {
            throw new VPDARuntimeException(e);
        }
        catch (IOException e) {
            throw new VPDARuntimeException(e);
        }

    }

}
