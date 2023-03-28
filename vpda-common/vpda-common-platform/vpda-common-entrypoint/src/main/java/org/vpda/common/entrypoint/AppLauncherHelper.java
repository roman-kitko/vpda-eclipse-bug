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
package org.vpda.common.entrypoint;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.picocontainer.PicoContainer;
import org.picocontainer.script.ContainerBuilder;
import org.picocontainer.script.xml.XMLContainerBuilder;
import org.vpda.common.util.exceptions.VPDAConfigurationRuntimeException;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.common.util.logging.SystemOutHandler;
import org.vpda.internal.common.util.ClassUtil;
import org.vpda.internal.common.util.PropertiesUtil;
import org.vpda.internal.common.util.ResourceLoader;
import org.vpda.internal.common.util.URLUtil;

/**
 * Launcher of VPDA platform. It will create {@link AppConfiguration} and
 * {@link AppEntryPoint} and launch modules gathered from
 * {@link ModulesConfigurationFactory}.
 * 
 * @author kitko
 *
 */
public final class AppLauncherHelper {
    private static boolean loggingConfigurationLoaded;

    private AppLauncherHelper() {
    }

    /** Name of logging configuration file */
    public static final String LOG_CONFIG_FILE_NAME = "vpda-logging.properties";

    /**
     * Will load logging configuration from vpda-logging.properties resource with
     * force false
     * 
     * @throws SecurityException
     * @throws IOException
     */
    public static void loadLoggingConfiguration() throws SecurityException, IOException {
        loadLoggingConfiguration(false);
    }

    /**
     * Will load logging configuration from vpda-logging.properties resource
     * 
     * @param force Will force even when already loaded
     * @throws SecurityException
     * @throws IOException
     */
    public static void loadLoggingConfiguration(boolean force) throws SecurityException, IOException {
        if (!force && loggingConfigurationLoaded) {
            return;
        }
        URL logUrl = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            logUrl = loader.getResource(LOG_CONFIG_FILE_NAME);
        }
        if (logUrl == null) {
            logUrl = AppLauncherHelper.class.getClassLoader().getResource(LOG_CONFIG_FILE_NAME);
        }
        if (logUrl == null) {
            Optional<URL> urlOptional = ResourceLoader.resolveResourceFromLoaderAndModules(LOG_CONFIG_FILE_NAME, loader, AppLauncherHelper.class);
            if (urlOptional.isPresent()) {
                logUrl = urlOptional.get();
            }
        }
        if (logUrl != null) {
            System.out.println("Loading logging properties from : " + logUrl);
            LogManager.getLogManager().readConfiguration(logUrl.openStream());
            System.out.println("LogManager configured from : " + logUrl);
        }
        else {
            ;
            System.out.println("Cannot find " + LOG_CONFIG_FILE_NAME + " !!!. Will not configure logging.");
        }
        boolean hasAnyHandler = false;
        LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AppLauncherHelper.class);
        Logger parrentLogger = logger.getParent();
        while (parrentLogger != null) {
            if (parrentLogger.getHandlers().length > 0) {
                hasAnyHandler = true;
                break;
            }
            parrentLogger = parrentLogger.getParent();
        }
        if (!hasAnyHandler) {
            Logger vpdaLogger = Logger.getLogger("org.vpda");
            vpdaLogger.addHandler(new SystemOutHandler());
        }
        logger.info("First log entry after logging initialized");
        loggingConfigurationLoaded = true;
    }

    /**
     * Tries to find Resource like SPI. It will look for URL
     * <code> services/spiClass.getName() and META-INF/services/spiClass.getName() </code>
     * 
     * @param spiClass Class by which we determine SPI resource name
     * @param loader
     * @return URL of found Resource or null if not found
     * @throws IOException
     */
    static List<URL> getSPIResourceURLs(Class spiClass, ClassLoader loader) throws IOException {
        return getSPIResourceURLs(spiClass.getName(), loader);
    }

    static List<URL> getSPIResourceURLs(String name, ClassLoader loader) throws IOException {
        Enumeration<URL> urls = loader.getResources("META-INF/services/" + name);
        if (urls.hasMoreElements()) {
            return Collections.list(urls);
        }
        urls = loader.getResources("/META-INF/services/" + name);
        if (urls.hasMoreElements()) {
            return Collections.list(urls);
        }
        return new ArrayList<URL>(0);
    }

    /**
     * Loads application container from applicationXml file
     * 
     * @param xmlPath Path to application xml
     * @param loader
     * @param parent
     * @return container or null
     */
    public static PicoContainer loadXMLPicoContainer(String xmlPath, ClassLoader loader, PicoContainer parent) {
        if (xmlPath == null) {
            throw new IllegalArgumentException("xmlPath path is null");
        }
        URL url = null;
        try {
            url = URLUtil.resolveURL(xmlPath, loader);
        }
        catch (RuntimeException e) {
            throw new VPDAConfigurationRuntimeException("Cannot load application configuration from path : " + xmlPath, e);
        }
        try {
            InputStream stream = url.openStream();
            stream.close();
        }
        catch (IOException e) {
            throw new VPDAConfigurationRuntimeException("Cannot open application xml stream", e);
        }
        XMLContainerBuilder containerBuilder = new XMLContainerBuilder(url, loader);
        PicoContainer container = containerBuilder.buildContainer(parent, null, false);
        return container;
    }

    /**
     * Finds ContainerBuilder like SPI
     * 
     * @param key
     * @param loader
     * @return ContainerBuilder or null if not found
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static ContainerBuilder getApplicationContainerBuilderLikeSPI(String key, ClassLoader loader) throws Exception {
        String className = null;
        List<URL> urls = getSPIResourceURLs(ContainerBuilder.class, loader);
        for (URL url : urls) {
            Properties properties = PropertiesUtil.readPropertiesFromURL(url);
            className = properties.getProperty(key);
            if (className != null) {
                Class<ContainerBuilder> clazz = (Class<ContainerBuilder>) loader.loadClass(className);
                try {
                    return ClassUtil.createInstance(clazz, loader);
                }
                catch (Exception e) {
                    return ClassUtil.createInstance(clazz);
                }
            }
        }
        return null;
    }

    private static String findApplicationXmlPathLikeResource(String applicationXMLKey, ClassLoader loader) throws Exception {
        Enumeration<URL> eUrls = loader.getResources("META-INF/applicationContainer.path");
        List<URL> urls = new ArrayList<>();
        if (eUrls.hasMoreElements()) {
            urls = Collections.list(eUrls);
        }
        eUrls = loader.getResources("/META-INF/applicationContainer.path");
        if (eUrls.hasMoreElements()) {
            urls = Collections.list(eUrls);
        }

        for (URL url : urls) {
            Properties properties = PropertiesUtil.readPropertiesFromURL(url);
            String path = properties.getProperty(applicationXMLKey);
            if (path != null) {
                return path;
            }
        }
        return null;
    }

    /**
     * Builds application container. First tries to get containerBuilder from SPI
     * and create container. If no ContainerBuilder, calls
     * loadApplicationXMLPicoContainer to load application container from xml file
     * 
     * @param builderKey
     * @param applicationXMLKey
     * @param loader
     * @param parentContainer
     * @return Container
     * @throws Exception
     */
    public static PicoContainer loadApplicationContainerLikeSPI(String builderKey, String applicationXMLKey, ClassLoader loader, PicoContainer parentContainer) throws Exception {
        ContainerBuilder builder = null;
        if (builderKey != null) {
            builder = getApplicationContainerBuilderLikeSPI(builderKey, loader);
        }
        if (builder != null) {
            return builder.buildContainer(parentContainer, null, false);
        }
        if (applicationXMLKey != null) {
            String applicationXml = findApplicationXmlPathLikeResource(applicationXMLKey, loader);
            if (applicationXml != null) {
                return loadXMLPicoContainer(applicationXml, loader, parentContainer);
            }
        }
        return null;
    }

    /**
     * Loads ContainerBuilder from value of system property builderKey. If we cannot
     * create builder, we will find xmlPath from applicationXMLKey system property.
     * This path will be used to load application.xml container calling
     * {@link #loadXMLPicoContainer(String, ClassLoader, PicoContainer)}.
     * 
     * @param builderKey
     * @param applicationXMLKey
     * @param loader
     * @param parentContainer
     * @return load PicoContainer or null if no Builder nor application.xml is found
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static PicoContainer loadApplicationContainerLikeSystemProperty(String builderKey, String applicationXMLKey, ClassLoader loader, PicoContainer parentContainer) throws Exception {
        String builderClassName = System.getProperty(builderKey);
        if (builderClassName != null) {
            Class<ContainerBuilder> builderClass = (Class<ContainerBuilder>) loader.loadClass(builderClassName);
            ContainerBuilder builder = null;
            try {
                builder = ClassUtil.createInstance(builderClass, loader);
            }
            catch (Exception e) {
                builder = ClassUtil.createInstance(builderClass);
            }
            return builder.buildContainer(parentContainer, null, false);
        }
        String xmlPath = System.getProperty(applicationXMLKey);
        if (xmlPath != null) {
            return loadXMLPicoContainer(xmlPath, loader, parentContainer);
        }
        return null;
    }

    /**
     * Loads PicoContainer first from system properties using
     * {@link #loadApplicationContainerLikeSystemProperty(String, String, ClassLoader, PicoContainer)}
     * then from SPI using
     * {@link #loadApplicationContainerLikeSPI(String, String, ClassLoader, PicoContainer)}
     * 
     * @param sysBuilderKey
     * @param sysApplicationXMLKey
     * @param spiBuilderKey
     * @param spiApplicationXMLKey
     * @param loader
     * @param parentContainer
     * @return container or null if not found
     * @throws Exception
     */
    public static PicoContainer loadApplicationContainer(String sysBuilderKey, String sysApplicationXMLKey, String spiBuilderKey, String spiApplicationXMLKey, ClassLoader loader,
            PicoContainer parentContainer) throws Exception {
        PicoContainer container = loadApplicationContainerLikeSystemProperty(sysBuilderKey, sysApplicationXMLKey, loader, parentContainer);
        if (container == null) {
            container = loadApplicationContainerLikeSPI(spiBuilderKey, spiApplicationXMLKey, loader, container);
        }
        return container;
    }

}
