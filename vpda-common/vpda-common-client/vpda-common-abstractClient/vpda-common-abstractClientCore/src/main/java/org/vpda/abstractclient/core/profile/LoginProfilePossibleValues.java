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
package org.vpda.abstractclient.core.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.clientserver.communication.EncryptionType;
import org.vpda.clientserver.communication.data.ContextPolicy;
import org.vpda.clientserver.communication.data.ProxyToUse;

/**
 * Possible values for LoginProfile
 * 
 * @author kitko
 *
 */
public class LoginProfilePossibleValues {
    private final List<String> availableServers;
    private final List<Locale> availableLocales;
    private final List<CommunicationProtocol> availableCommunicationProtocols;
    private final List<ProxyToUse> availableProxiesToUse;
    private final List<EncryptionType> availableEncryptionTypes;
    private final List<ContextPolicy> availableContextPolicies;

    /**
     * Creates LoginProfilePossibleValues
     * 
     * @param builder
     * @param <T>
     */
    protected <T extends LoginProfilePossibleValues> LoginProfilePossibleValues(org.vpda.abstractclient.core.profile.LoginProfilePossibleValues.Builder<T> builder) {
        this.availableCommunicationProtocols = new ArrayList<CommunicationProtocol>(builder.getAvailableProtocols());
        this.availableLocales = new ArrayList<Locale>(builder.getAvailableLocales());
        this.availableServers = new ArrayList<String>(builder.getAvailableServers());
        this.availableProxiesToUse = new ArrayList<ProxyToUse>(builder.getAvailableProxiesToUse());
        this.availableEncryptionTypes = new ArrayList<EncryptionType>(builder.getAvailableEncyptionTypes());
        this.availableContextPolicies = new ArrayList<ContextPolicy>(builder.getAvailableContextPolicies());
    }

    /**
     * @return availableCommunicationProtocols
     */
    public List<? extends CommunicationProtocol> getAvailableCommunicationProtocols() {
        return Collections.unmodifiableList(availableCommunicationProtocols);
    }

    /**
     * @return the availableShowContextPolicies
     */
    public final List<ContextPolicy> getAvailableContextPolicies() {
        return Collections.unmodifiableList(availableContextPolicies);
    }

    /**
     * @return availableLocales
     */
    public List<Locale> getAvailableLocales() {
        return Collections.unmodifiableList(availableLocales);
    }

    /**
     * @return availableEncryptionTypes
     */
    public List<EncryptionType> getAvailableEncryptionTypes() {
        return Collections.unmodifiableList(availableEncryptionTypes);
    }

    /**
     * @return availableServers
     */
    public List<String> getAvailableServers() {
        return Collections.unmodifiableList(availableServers);
    }

    /**
     * @return availableProxiesToUse
     */
    public List<ProxyToUse> getAvailableProxiesToUse() {
        return Collections.unmodifiableList(availableProxiesToUse);
    }

    /**
     * Builder for LoginProfilePossibleValues
     * 
     * @param <T>
     */
    public static class Builder<T extends LoginProfilePossibleValues> implements org.vpda.common.util.Builder<LoginProfilePossibleValues> {
        private List<String> availableServers;
        private List<Locale> availableLocales;
        private List<CommunicationProtocol> availableCommunicationProtocols;
        private List<ProxyToUse> availableProxiesToUse;
        private List<EncryptionType> availableEncryptionTypes;
        private List<ContextPolicy> availableContextPolicies;

        /** Creates the builder */
        public Builder() {
            this.availableServers = new ArrayList<String>(2);
            this.availableLocales = new ArrayList<Locale>(2);
            this.availableCommunicationProtocols = new ArrayList<CommunicationProtocol>(3);
            this.availableProxiesToUse = new ArrayList<ProxyToUse>(3);
            this.availableEncryptionTypes = new ArrayList<EncryptionType>(3);
            this.availableContextPolicies = new ArrayList<ContextPolicy>(5);
        }

        /**
         * Set available servers
         * 
         * @param servers
         * @return this
         */
        public Builder setAvailableServers(List<String> servers) {
            this.availableServers = servers != null ? new ArrayList<String>(servers) : new ArrayList<String>(0);
            return this;
        }

        /**
         * 
         * @return available servers
         */
        public List<String> getAvailableServers() {
            return Collections.unmodifiableList(availableServers);
        }

        /**
         * Sets available servers
         * 
         * @param locales
         * @return this
         */
        public Builder setAvailableLocales(List<Locale> locales) {
            this.availableLocales = locales != null ? new ArrayList<Locale>(locales) : new ArrayList<Locale>(0);
            return this;
        }

        /**
         * @return available locales
         */
        public List<Locale> getAvailableLocales() {
            return Collections.unmodifiableList(availableLocales);
        }

        /**
         * Sets available communication protocols
         * 
         * @param protocols
         * @return this
         */
        public Builder setAvailableProtocols(List<? extends CommunicationProtocol> protocols) {
            this.availableCommunicationProtocols = protocols != null ? new ArrayList<CommunicationProtocol>(protocols) : new ArrayList<CommunicationProtocol>(0);
            return this;
        }

        /**
         * @return available communication protocols
         */
        public List<CommunicationProtocol> getAvailableProtocols() {
            return Collections.unmodifiableList(availableCommunicationProtocols);
        }

        /**
         * Sets values from real profile
         * 
         * @param value
         * @return this
         */
        public Builder setValues(LoginProfilePossibleValues value) {
            setAvailableLocales(value.getAvailableLocales());
            setAvailableProtocols(value.getAvailableCommunicationProtocols());
            setAvailableServers(value.getAvailableServers());
            setAvailableProxiesToUse(value.getAvailableProxiesToUse());
            setAvailableContextPolicies(value.getAvailableContextPolicies());
            return this;
        }

        @Override
        public LoginProfilePossibleValues build() {
            return new LoginProfilePossibleValues(this);
        }

        @Override
        public Class<? extends LoginProfilePossibleValues> getTargetClass() {
            return LoginProfilePossibleValues.class;
        }

        /**
         * @return proxiesToUse
         */
        public List<ProxyToUse> getAvailableProxiesToUse() {
            return Collections.unmodifiableList(availableProxiesToUse);
        }

        /**
         * Sets available proxies to use
         * 
         * @param proxiesToUse
         * @return this
         */
        public Builder setAvailableProxiesToUse(List<ProxyToUse> proxiesToUse) {
            this.availableProxiesToUse = proxiesToUse != null ? new ArrayList<ProxyToUse>(proxiesToUse) : new ArrayList<ProxyToUse>(0);
            return this;
        }

        /**
         * @return encryptionTypes
         */
        public List<EncryptionType> getAvailableEncyptionTypes() {
            return Collections.unmodifiableList(availableEncryptionTypes);
        }

        /**
         * Sets available encryptionTypes
         * 
         * @param encryptionTypes
         * @return this
         */
        public Builder setAvailableEncryptionTypes(List<EncryptionType> encryptionTypes) {
            this.availableEncryptionTypes = encryptionTypes != null ? new ArrayList<EncryptionType>(encryptionTypes) : new ArrayList<EncryptionType>(0);
            return this;
        }

        /**
         * @return the availableContextPolicies
         */
        public List<ContextPolicy> getAvailableContextPolicies() {
            return Collections.unmodifiableList(availableContextPolicies);
        }

        /**
         * @param availableContextPolicies the availableContextPolicies to set
         * @return this
         */
        public Builder setAvailableContextPolicies(List<ContextPolicy> availableContextPolicies) {
            this.availableContextPolicies = availableContextPolicies != null ? new ArrayList<ContextPolicy>(availableContextPolicies) : new ArrayList<ContextPolicy>(0);
            return this;
        }

    }

}
