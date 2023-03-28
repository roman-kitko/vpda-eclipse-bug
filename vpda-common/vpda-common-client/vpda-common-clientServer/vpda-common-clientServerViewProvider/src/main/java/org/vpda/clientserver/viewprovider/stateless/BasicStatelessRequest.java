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
package org.vpda.clientserver.viewprovider.stateless;

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderID;

/** basic stateless request */
public abstract class BasicStatelessRequest implements Serializable {

    private static final long serialVersionUID = 63274425735411981L;
    /** Provider def */
    protected final ViewProviderDef def;
    /** Id of request */
    protected final ViewProviderID providerId;

    /**
     * Creates Request
     * 
     * @param def
     */
    protected BasicStatelessRequest(ViewProviderDef def) {
        this.def = def;
        this.providerId = null;
    }

    /**
     * Creates Request
     * 
     * @param providerId
     */
    protected BasicStatelessRequest(ViewProviderID providerId) {
        this.providerId = providerId;
        this.def = providerId.getDef();
    }

    /**
     * Creates request from builder
     * 
     * @param builder
     */
    protected BasicStatelessRequest(Builder builder) {
        this.def = builder.getDef();
        this.providerId = builder.getProviderId();
    }

    /**
     * @return the def
     */
    public ViewProviderDef getDef() {
        return def;
    }

    /**
     * @return the id
     */
    public ViewProviderID getProviderId() {
        return providerId;
    }

    /**
     * Builder class for BasicStatelessRequest
     * 
     * @param <T>
     */
    public static abstract class Builder<T extends BasicStatelessRequest> implements org.vpda.common.util.Builder<T> {
        /** Definition of view provider */
        protected ViewProviderDef def;
        /** Provider id to use */
        protected ViewProviderID providerId;

        /**
         * @return the def
         */
        public ViewProviderDef getDef() {
            return def;
        }

        /**
         * @return the id
         */
        public ViewProviderID getProviderId() {
            return providerId;
        }

        /**
         * @param providerId the id to set
         * @return this
         */
        public Builder setProviderId(ViewProviderID providerId) {
            this.providerId = providerId;
            return this;
        }

        /**
         * @param def the def to set
         * @return this
         */
        public Builder setDef(ViewProviderDef def) {
            this.def = def;
            return this;
        }

        /**
         * Set values from request
         * 
         * @param request
         * @return this
         */
        public Builder setValues(T request) {
            this.def = request.getDef();
            this.providerId = request.getProviderId();
            return this;
        }

    }

}
