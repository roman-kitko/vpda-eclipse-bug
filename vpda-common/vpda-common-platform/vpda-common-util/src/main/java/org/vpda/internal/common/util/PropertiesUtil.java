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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * Properties helper utilities
 * 
 * @author kitko
 *
 */
public final class PropertiesUtil {
    private PropertiesUtil() {
    }

    /**
     * Loads properties from file
     * 
     * @param file
     * @return IOException while reading file
     * @throws IOException
     */
    public static Properties readPropertiesFromFile(File file) throws IOException {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            return readPropertiesFromStream(bis);
        }
        finally {
            if (bis != null) {
                try {
                    bis.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

    /**
     * Loads properties from stream
     * 
     * @param stream
     * @return IOException while reading file
     * @throws IOException
     */
    public static Properties readPropertiesFromStream(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        return properties;
    }

    /**
     * Read properties from URL
     * 
     * @param url
     * @return properties
     * @throws IOException
     */
    public static Properties readPropertiesFromURL(URL url) throws IOException {
        InputStream is = url.openStream();
        try {
            return readPropertiesFromStream(is);
        }
        finally {
            is.close();
        }
    }

    /**
     * Reads properties from string
     * 
     * @param s
     * @return read properties
     * @throws IOException
     */
    public static Properties readPropertiesFromString(String s) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader(s));
        return properties;
    }

    /**
     * Stores properties into String
     * 
     * @param properties
     * @return properties in String format
     * @throws IOException
     */
    public static String storePropertiesToString(Properties properties) throws IOException {
        StringWriter writer = new StringWriter();
        properties.store(writer, "properties");
        writer.close();
        return writer.getBuffer().toString();
    }

    /**
     * Copy the passed properties
     * 
     * @param properties
     * @return new Properties with same key=value pairs from passed one
     */
    public static Properties copyProperties(Properties properties) {
        Properties p = new Properties();
        if (properties != null) {
            p.putAll(properties);
        }
        return p;
    }

}
