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

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Resolver of properties in UNIX/ant style. <br/>
 * Example of usage : <br/>
 * <code>
 *  Properties p = new Properties();</br>
 *  p.setProperty("p1","value1"); <br/>
 *  p.setProperty("p2","Value of p1 is ${p1}"); <br/>
 *  p = PropertiesResolver.resolveProperties(p);<br/>	
 * </code>
 * 
 * It is shield against recursion.
 * 
 * @author kitko
 *
 */
public final class PropertiesResolver {

    private PropertiesResolver() {
    }

    /**
     * Resolves properties
     * 
     * @param properties      properties we want to resolve
     * @param knownProperties already known properties
     * @return resolved properties
     */
    public static Properties resolveProperties(Properties properties, Properties knownProperties) {
        if (properties == null) {
            return null;
        }
        return resolveProperties(copyProperties(properties), copyProperties(knownProperties), new HashSet<String>(5));
    }

    /**
     * Resolves one value
     * 
     * @param value
     * @param knownProperties
     * @return resolved property
     */
    public static String resolveValue(String value, Properties knownProperties) {
        return resolveValue(value, new Properties(knownProperties), new Properties(knownProperties), new HashSet<String>(0));
    }

    /**
     * Resolve properties containing already known values
     * 
     * @param properties
     * @return resolved properties
     */
    public static Properties resolveProperties(Properties properties) {
        if (properties == null) {
            return null;
        }
        Properties copy = copyProperties(properties);
        return resolveProperties(copy, new Properties(), new HashSet<String>(5));
    }

    private static Properties resolveProperties(Properties properties, Properties resolvedProperties, Set<String> justResolving) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String value = (String) entry.getValue();
            if (value.contains("$")) {
                value = resolveName((String) entry.getKey(), properties, resolvedProperties, justResolving);
                entry.setValue(value);
            }
        }
        return properties;
    }

    private static String resolveName(String name, Properties properties, Properties resolvedProperties, Set<String> justResolving) {
        String value = null;
        if (!justResolving.isEmpty()) {
            value = resolvedProperties.getProperty(name);
        }
        if (value != null) {
            return value;
        }
        if (justResolving.contains(name)) {
            value = "RECURSION";
            return value;
        }
        value = properties.getProperty(name);
        if (value == null) {
            value = "NOT_RESOLVED";
            return value;
        }
        else {
            justResolving.add(name);
            value = resolveValue(value, properties, resolvedProperties, justResolving);
            justResolving.remove(name);
            resolvedProperties.setProperty(name, value);
            return value;
        }
    }

    private static String resolveValue(String value, Properties properties, Properties resolvedProperties, Set<String> justResolving) {
        if (value == null) {
            return null;
        }
        int index = 0;
        int length = value.length();
        StringBuffer result = new StringBuffer();
        while (index >= 0 && index < length) {
            int varStart = value.indexOf("${", index);
            if (varStart >= 0) {
                int varEnd = value.indexOf('}', varStart);
                if (varEnd >= 0) {
                    String varSubstring = value.substring(varStart, varEnd + 1);
                    String varName = varSubstring.substring(2, varSubstring.length() - 1);
                    String varValue = resolveName(varName, properties, resolvedProperties, justResolving);
                    if ("NOT_RESOLVED".equals(varValue)) {
                        varValue = varSubstring;
                    }
                    else if ("RECUSRION".equals(varValue)) {
                        varValue = "${RECURSION!!!_" + varName + "}";
                    }
                    result.append(value.substring(index, varStart));
                    result.append(varValue);
                    index = varEnd + 1;
                }
                else {
                    result.append(value.substring(index, value.length()));
                    index = value.length();
                }
            }
            else {
                result.append(value.substring(index, value.length()));
                index = value.length();
            }
        }
        return result.toString();
    }

    private static Properties copyProperties(Properties properties) {
        Properties result = new Properties();
        if (properties == null) {
            return result;
        }
        for (Map.Entry<?, ?> entry : properties.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
