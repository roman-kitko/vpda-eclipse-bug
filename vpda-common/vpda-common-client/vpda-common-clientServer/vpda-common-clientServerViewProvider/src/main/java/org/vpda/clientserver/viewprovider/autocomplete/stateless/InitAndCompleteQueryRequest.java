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
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteQuery;
import org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest;
import org.vpda.internal.common.util.Assert;

/**
 * @author kitko
 *
 */
public class InitAndCompleteQueryRequest extends BasicStatelessRequest {
    private static final long serialVersionUID = -5887075392918289735L;
    private final ViewProviderInitData initData;
    private final AutoCompleteQuery query;

    /**
     * @param builder
     */
    public InitAndCompleteQueryRequest(Builder builder) {
        super(builder);
        this.initData = Assert.isNotNullArgument(builder.initData, "initData");
        this.query = Assert.isNotNullArgument(builder.query, "query");
    }

    /**
     * @param def
     * @param initData
     * @param query
     */
    public InitAndCompleteQueryRequest(ViewProviderDef def, ViewProviderInitData initData, AutoCompleteQuery query) {
        super(def);
        this.initData = Assert.isNotNullArgument(initData, "initData");
        this.query = Assert.isNotNullArgument(query, "query");
    }

    /**
     * @param providerId
     * @param initData
     * @param query
     */
    public InitAndCompleteQueryRequest(ViewProviderID providerId, ViewProviderInitData initData, AutoCompleteQuery query) {
        super(providerId);
        this.initData = Assert.isNotNullArgument(initData, "initData");
        this.query = Assert.isNotNullArgument(query, "query");
    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return initData;
    }

    /**
     * @return the query
     */
    public AutoCompleteQuery getQuery() {
        return query;
    }

    /**
     * Builder for {@link InitAndCompleteQueryRequest}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends BasicStatelessRequest.Builder<InitAndCompleteQueryRequest> {

        private ViewProviderInitData initData;
        private AutoCompleteQuery query;

        @Override
        public Class<? extends InitAndCompleteQueryRequest> getTargetClass() {
            return InitAndCompleteQueryRequest.class;
        }

        @Override
        public InitAndCompleteQueryRequest build() {
            return new InitAndCompleteQueryRequest(this);
        }

        /**
         * @return the initData
         */
        public ViewProviderInitData getInitData() {
            return initData;
        }

        /**
         * @param initData the initData to set
         * @return this
         */
        public Builder setInitData(ViewProviderInitData initData) {
            this.initData = initData;
            return this;
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
