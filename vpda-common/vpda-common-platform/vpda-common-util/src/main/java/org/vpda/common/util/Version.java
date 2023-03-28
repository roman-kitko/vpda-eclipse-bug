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
package org.vpda.common.util;

import java.io.Serializable;
import java.util.Properties;

/**
 * Version identifier
 * 
 * @author kitko
 *
 */
public final class Version implements Serializable {
    private static final long serialVersionUID = 4062289019307917749L;
    private final String productName;
    private final String version;
    private final ScmCommitInfo scmLastCommitInfo;

    private Version(VersionBuilder versionBuilder) {
        this.productName = versionBuilder.getProductName();
        this.version = versionBuilder.getVersion();
        this.scmLastCommitInfo = versionBuilder.getScmLastCommitInfo();
    }

    /**
     * @return Returns the productName.
     */
    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        if (scmLastCommitInfo == null) {
            return productName + "-" + version;
        }
        return productName + "-" + version + ";" + "lastCommit : " + scmLastCommitInfo;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the scmLastCommitInfo
     */
    public ScmCommitInfo getScmLastCommitInfo() {
        return scmLastCommitInfo;
    }

    /**
     * Builder for Version
     * 
     * @author kitko
     *
     */
    public static class VersionBuilder implements Builder<Version> {
        private String productName;
        private String version;
        private ScmCommitInfo scmLastCommitInfo;

        /**
         * @return the productName
         */
        public String getProductName() {
            return productName;
        }

        /**
         * @param productName the productName to set
         * @return this
         */
        public VersionBuilder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        /**
         * Loads info from properties
         * 
         * @param properties
         */
        public void loadFromProperties(Properties properties) {
            // Load product name
            productName = properties.getProperty("pom.productName");
            if (productName == null || productName.startsWith("$")) {
                productName = properties.getProperty("productName");
            }
            String pomVersion = properties.getProperty("pom.version");
            if (pomVersion != null && !pomVersion.startsWith("$")) {
                this.version = pomVersion;
            }

        }

        /**
         * @return the version
         */
        public String getVersion() {
            return version;
        }

        /**
         * @param version the version to set
         * @return this
         */
        public VersionBuilder setVersion(String version) {
            this.version = version;
            return this;
        }

        /**
         * @return the scmLastCommitInfo
         */
        public ScmCommitInfo getScmLastCommitInfo() {
            return scmLastCommitInfo;
        }

        /**
         * @param scmLastCommitInfo the scmLastCommitInfo to set
         * @return this
         */
        public VersionBuilder setScmLastCommitInfo(ScmCommitInfo scmLastCommitInfo) {
            this.scmLastCommitInfo = scmLastCommitInfo;
            return this;
        }

        @Override
        public Version build() {
            return new Version(this);
        }

        @Override
        public Class<? extends Version> getTargetClass() {
            return Version.class;
        }

        @Override
        public String toString() {
            return build().toString();
        }

    }

}
