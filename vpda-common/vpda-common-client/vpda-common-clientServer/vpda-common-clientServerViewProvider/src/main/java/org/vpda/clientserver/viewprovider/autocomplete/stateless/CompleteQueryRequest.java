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
package org.vpda.clientserver.viewprovider.autocomplete.stateless;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteQuery;
import org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest;

/**
 * Request for auto complete query
 * 
 * @author kitko
 *
 */
public final class CompleteQueryRequest extends BasicStatelessRequest {

    private static final long serialVersionUID = 2922800236550169499L;
    private final AutoCompleteQuery query;

    /**
     * @return the query
     */
    public AutoCompleteQuery getQuery() {
        return query;
    }

    /**
     * @param def
     * @param query
     */
    public CompleteQueryRequest(ViewProviderDef def, AutoCompleteQuery query) {
        super(def);
        this.query = query;
    }

    /**
     * @param providerId
     * @param query
     */
    public CompleteQueryRequest(ViewProviderID providerId, AutoCompleteQuery query) {
        super(providerId);
        this.query = query;
    }

    private CompleteQueryRequest(Builder builder) {
        super(builder);
        this.query = builder.getQuery();
    }

    /**
     * Builder for CompleteQueryRequest
     * 
     * @author kitko
     *
     */
    public static final class Builder extends BasicStatelessRequest.Builder<CompleteQueryRequest> {
        private AutoCompleteQuery query;

        @Override
        public Class<? extends CompleteQueryRequest> getTargetClass() {
            return CompleteQueryRequest.class;
        }

        @Override
        public CompleteQueryRequest build() {
            return new CompleteQueryRequest(this);
        }

        /**
         * @return the query
         */
        public AutoCompleteQuery getQuery() {
            return query;
        }

        /**
         * @param query the query to set
         * @return this
         */
        public Builder setQuery(AutoCompleteQuery query) {
            this.query = query;
            return this;
        }

    }

}
