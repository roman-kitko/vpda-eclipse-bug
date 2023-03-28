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
package org.vpda.clientserver.viewprovider.list.stateless;

import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest;

/**
 * Combine request to init and get results
 * 
 * @author kitko
 *
 */
public final class InitAndReadDataStatelessRequest extends BasicStatelessRequest {

    private static final long serialVersionUID = -8161843189751235338L;

    private final ViewProviderInitData initData;
    private final ReadDataStatelessRequestContent readRequestcontent;

    private InitAndReadDataStatelessRequest(Builder builder) {
        super(builder);
        this.initData = builder.getInitData();
        this.readRequestcontent = builder.getReadDataRequest();

    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return initData;
    }

    /**
     * @return the readDataRequest
     */
    public ReadDataStatelessRequestContent getReadRequestContent() {
        return readRequestcontent;
    }

    @Override
    public String toString() {
        return "InitAndReadDataStatelessRequest [initData=" + initData + ", readRequestcontent=" + readRequestcontent + ", def=" + def + ", providerId=" + providerId + "]";
    }

    /**
     * Builder for {@link InitAndReadDataStatelessRequest}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends BasicStatelessRequest.Builder<InitAndReadDataStatelessRequest> implements org.vpda.common.util.Builder<InitAndReadDataStatelessRequest> {
        private ViewProviderInitData initData;
        private ReadDataStatelessRequestContent readRequestcontent;

        @Override
        public Builder setValues(InitAndReadDataStatelessRequest request) {
            super.setValues(request);
            this.initData = request.getInitData();
            this.readRequestcontent = request.getReadRequestContent();
            return this;
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
         * @return the readDataRequest
         */
        public ReadDataStatelessRequestContent getReadDataRequest() {
            return readRequestcontent;
        }

        /**
         * @param readRequestcontent the readDataRequest to set
         * @return this
         */
        public Builder setReadDataRequest(ReadDataStatelessRequestContent readRequestcontent) {
            this.readRequestcontent = readRequestcontent;
            return this;
        }

        @Override
        public Class<? extends InitAndReadDataStatelessRequest> getTargetClass() {
            return InitAndReadDataStatelessRequest.class;
        }

        @Override
        public InitAndReadDataStatelessRequest build() {
            return new InitAndReadDataStatelessRequest(this);
        }

    }

}
