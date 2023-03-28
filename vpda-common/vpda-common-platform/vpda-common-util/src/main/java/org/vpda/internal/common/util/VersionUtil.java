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
package org.vpda.internal.common.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.vpda.common.util.logging.LoggerMethodTracer;
import org.w3c.dom.Document;

/**
 * @author kitko
 *
 */
public final class VersionUtil {
    private VersionUtil() {
    }

    private static final Logger logger = LoggerMethodTracer.getLogger(VersionUtil.class);

    /**
     * Determines vpda.version
     * 
     * @return vpda version
     */
    public static String getVpdaVersion() {
        String vpdaVersion = System.getProperty("vpda.version");
        if (vpdaVersion != null) {
            return vpdaVersion;
        }
        URL url = VersionUtil.class.getClassLoader().getResource("META-INF/commonInfo.txt");
        if (url == null) {
            throw new IllegalStateException("Cannot find commonInfo.txt to determine vpda version");
        }
        String externalForm = url.toExternalForm();
        if (externalForm.startsWith("file:") && externalForm.contains("vpda-common-util")) {
            URL pomUrl = null;
            try {
                pomUrl = new URL(externalForm.substring(0, externalForm.indexOf("vpda-common-util") + "vpda-common-util".length()) + File.separator + "pom.xml");
            }
            catch (MalformedURLException e) {
                throw new IllegalStateException("Error reading pom.xml to determine vpda version", e);
            }
            vpdaVersion = getVpdaVersionFromPom(pomUrl);
        }
        if (vpdaVersion == null) {
            Properties properties;
            try {
                properties = PropertiesUtil.readPropertiesFromURL(url);
            }
            catch (IOException e) {
                throw new IllegalStateException("Error reading commonInfo.txt to determine vpda version", e);
            }
            properties = PropertiesResolver.resolveProperties(properties, System.getProperties());
            vpdaVersion = properties.getProperty("pom.version");
        }
        if (vpdaVersion != null) {
            System.setProperty("vpda.version", vpdaVersion);
        }
        if (vpdaVersion == null) {
            throw new IllegalStateException("Cannot determine vpda version");
        }
        return vpdaVersion;
    }

    private static String getVpdaVersionFromPom(URL pomUrl) {
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = newDocumentBuilder.parse(pomUrl.openStream());
            XPath xpath = XPathFactory.newInstance().newXPath();
            String version = xpath.evaluate("/project/parent/version", doc);
            return version;
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Error reading pom.xml to determine vpda version", e);
            return null;
        }
    }

}
