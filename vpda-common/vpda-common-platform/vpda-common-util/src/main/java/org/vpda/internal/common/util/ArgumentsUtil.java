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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** Utilities related to command line arguments parsing */
public final class ArgumentsUtil {

    private ArgumentsUtil() {
    }

    /**
     * Will retrieve system properties from arguments
     * 
     * @param args
     * @return list of system properties
     */
    public static List<SystemProperty> retrieveSystemProperties(String[] args) {
        List<SystemProperty> result = new ArrayList<>();
        for (String arg : args) {
            SystemProperty systemProperty = parseSystemProperty(arg);
            if (systemProperty != null) {
                result.add(systemProperty);
            }
        }
        return result;
    }

    /**
     * Retrieves and apply system properties
     * 
     * @param args
     * @param override
     */
    public static void retrieveAndApplySystemProperties(String[] args, boolean override) {
        List<SystemProperty> properties = retrieveSystemProperties(args);
        applySystemProperties(properties, override);
    }

    /**
     * @param args
     * @param override
     * @return rest of properties
     */
    public static String[] retrieveApplyAndEatSystemProperties(String[] args, boolean override) {
        List<SystemProperty> properties = retrieveSystemProperties(args);
        applySystemProperties(properties, override);
        List<String> rest = new ArrayList<String>();
        for (String arg : args) {
            if (!arg.startsWith("-D")) {
                rest.add(arg);
            }
        }
        return rest.toArray(new String[rest.size()]);
    }

    /**
     * Apply system properties
     * 
     * @param properties
     * @param override
     */
    public static void applySystemProperties(List<SystemProperty> properties, boolean override) {
        Properties currentProperties = System.getProperties();
        for (SystemProperty property : properties) {
            if (!override) {
                System.setProperty(property.getName(), property.getValue());
            }
            else {
                if (!currentProperties.containsKey(property.getName())) {
                    System.setProperty(property.getName(), property.getValue());
                }

            }
        }
    }

    private static SystemProperty parseSystemProperty(String arg) {
        if (arg.startsWith("-D") && arg.length() > 2) {
            int eqIndex = arg.indexOf('=');
            String name = null;
            String value = null;
            if (eqIndex == 2) {
                return null;
            }
            name = eqIndex != -1 ? arg.substring(2, eqIndex) : arg.substring(2);
            value = (eqIndex != -1 && eqIndex < arg.length() - 1) ? arg.substring(eqIndex + 1) : "";
            return new SystemProperty(name, value);

        }
        return null;
    }

    /** Java system property */
    public static final class SystemProperty {
        private final String name;
        private final String value;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        SystemProperty(String name, String value) {
            super();
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("SystemProperty [name=");
            builder.append(name);
            builder.append(", value=");
            builder.append(value);
            builder.append("]");
            return builder.toString();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SystemProperty other = (SystemProperty) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            }
            else if (!name.equals(other.name))
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            }
            else if (!value.equals(other.value))
                return false;
            return true;
        }

    }
}
