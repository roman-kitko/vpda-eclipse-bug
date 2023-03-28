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
package org.vpda.clientserver.communication.data;

import java.io.Serializable;

/**
 * Web based login info
 * 
 * @author kitko
 *
 */
public final class WebBasedClientUILoginInfo extends AbstractClientUILoginInfo implements ClientUILoginInfo, Serializable {
    private static final long serialVersionUID = -6585920003697419296L;

    private final String httpSessionId;

    private WebBasedClientUILoginInfo(Builder builder) {
        super(builder);
        this.httpSessionId = builder.getHttpSessionId();
    }

    /**
     * @return the httpSessionId
     */
    public String getHttpSessionId() {
        return httpSessionId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((httpSessionId == null) ? 0 : httpSessionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        WebBasedClientUILoginInfo other = (WebBasedClientUILoginInfo) obj;
        if (httpSessionId == null) {
            if (other.httpSessionId != null)
                return false;
        }
        else if (!httpSessionId.equals(other.httpSessionId))
            return false;
        return true;
    }

    @Override
    public WebBasedClientUILoginInfo.Builder createBuilder() {
        return new WebBasedClientUILoginInfo.Builder();
    }

    @Override
    public WebBasedClientUILoginInfo.Builder createBuilderWithSameValues() {
        return (WebBasedClientUILoginInfo.Builder) super.createBuilderWithSameValues();
    }

    /**
     * Builder for WebBasedClientUILoginInfo
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractClientUILoginInfo.AbstractClientUILoginInfoBuilder<WebBasedClientUILoginInfo> implements org.vpda.common.util.Builder<WebBasedClientUILoginInfo> {
        private String httpSessionId;

        /**
         * Sets values from {@link WebBasedClientUILoginInfo}
         * 
         * @param loginInfo
         * @return this
         */
        @Override
        public Builder setValues(WebBasedClientUILoginInfo loginInfo) {
            if (loginInfo == null) {
                return this;
            }
            super.setValues(loginInfo);
            this.httpSessionId = loginInfo.getHttpSessionId();
            return this;
        }

        /**
         * @return the httpSessionId
         */
        public String getHttpSessionId() {
            return httpSessionId;
        }

        /**
         * @param httpSessionId the httpSessionId to set
         * @return this
         */
        public Builder setHttpSessionId(String httpSessionId) {
            this.httpSessionId = httpSessionId;
            return this;
        }

        @Override
        public Class<? extends WebBasedClientUILoginInfo> getTargetClass() {
            return WebBasedClientUILoginInfo.class;
        }

        @Override
        public WebBasedClientUILoginInfo build() {
            return new WebBasedClientUILoginInfo(this);
        }

    }

}
