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
package org.vpda.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.internal.common.util.contants.CommonUtilConstants;

/**
 * Aggregates information which defines running component
 * 
 * @author kitko
 * 
 */
public final class ComponentInfo implements java.io.Serializable {
    private static final long serialVersionUID = -5477775069394085878L;
    private final Version version;
    private final String name;
    private final Map<String, String> environment;
    private final String id;

    /**
     * @return version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return collection of all environment keys
     */
    public Collection<String> getEnvironmentKeys() {
        return environment.keySet();
    }

    /**
     * @return id of component
     */
    public String getId() {
        return id;
    }

    private ComponentInfo(ComponentInfoBuilder builder) {
        this.id = builder.getId();
        Map<String, String> envCopy = Collections.emptyMap();
        if (!builder.environment.isEmpty()) {
            if (builder.environment.size() == 1) {
                String key = builder.environment.keySet().iterator().next();
                String value = builder.environment.get(key);
                envCopy = Collections.singletonMap(key, value);
            }
            else {
                envCopy = new HashMap<String, String>(builder.environment);
            }
        }
        this.environment = envCopy;
        this.name = builder.getName();
        this.version = builder.getVersion();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((environment == null) ? 0 : environment.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        ComponentInfo other = (ComponentInfo) obj;
        if (environment == null) {
            if (other.environment != null)
                return false;
        }
        else if (!environment.equals(other.environment))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        }
        else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(CommonUtilConstants.LINE_SEPARATOR);
        buffer.append("-------------------------------------------------------------------------------------------------------------------------");
        buffer.append(CommonUtilConstants.LINE_SEPARATOR);
        buffer.append("ID = ").append(id);
        buffer.append(CommonUtilConstants.LINE_SEPARATOR);
        buffer.append("Name = ").append(name);
        buffer.append(CommonUtilConstants.LINE_SEPARATOR);
        buffer.append("Version = ").append(version);
        buffer.append(CommonUtilConstants.LINE_SEPARATOR);
        buffer.append("environment : ");
        buffer.append(CommonUtilConstants.LINE_SEPARATOR);
        int i = 0;
        for (Map.Entry<String, String> entry : environment.entrySet()) {
            buffer.append("          ");
            buffer.append(entry.getKey()).append(" = ").append(entry.getValue());
            if (i != environment.size() - 1) {
                buffer.append(CommonUtilConstants.LINE_SEPARATOR);
            }
            i++;
        }
        buffer.append(CommonUtilConstants.LINE_SEPARATOR);
        buffer.append("-------------------------------------------------------------------------------------------------------------------------");
        return buffer.toString();
    }

    /**
     * @param key
     * @return environment key value
     */
    public String getEnvironmentProperty(String key) {
        return environment.get(key);
    }

    /**
     * Builder for ComponentInfo
     * 
     * @author kitko
     *
     */
    public static class ComponentInfoBuilder implements Builder<ComponentInfo> {
        private Version version;
        private String name;
        private Map<String, String> environment = new HashMap<String, String>(0);
        private String id;

        /**
         * @return version
         */
        public Version getVersion() {
            return version;
        }

        /**
         * Sets version
         * 
         * @param version
         * @return this
         */
        public ComponentInfoBuilder setVersion(Version version) {
            this.version = version;
            return this;
        }

        /**
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets name
         * 
         * @param name
         * @return this
         */
        public ComponentInfoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets environment key and value
         * 
         * @param key
         * @param value
         * @return this
         */
        public ComponentInfoBuilder setEnvironmentProperty(String key, String value) {
            environment.put(key, value);
            return this;
        }

        /**
         * @return collection of all environment keys
         */
        public Collection<String> getEnvironmentKeys() {
            return environment.keySet();
        }

        /**
         * @return id of component
         */
        public String getId() {
            return id;
        }

        /**
         * @param id
         * @return this
         */
        public ComponentInfoBuilder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * @param key
         * @return environment key value
         */
        public String getEnvironmentProperty(String key) {
            return environment.get(key);
        }

        @Override
        public ComponentInfo build() {
            return new ComponentInfo(this);
        }

        @Override
        public Class<? extends ComponentInfo> getTargetClass() {
            return ComponentInfo.class;
        }

        /**
         * @param values
         * @return this
         */
        public ComponentInfoBuilder setValues(ComponentInfo values) {
            this.environment = new HashMap<String, String>(values.environment);
            this.id = values.id;
            this.name = values.name;
            this.version = values.version;
            return this;
        }

    }

}
