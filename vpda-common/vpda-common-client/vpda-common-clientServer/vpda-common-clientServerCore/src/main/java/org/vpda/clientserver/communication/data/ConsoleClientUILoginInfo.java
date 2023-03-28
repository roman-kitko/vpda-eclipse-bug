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
 * Console based login info
 * 
 * @author kitko
 *
 */
public final class ConsoleClientUILoginInfo extends AbstractClientUILoginInfo implements ClientUILoginInfo, Serializable {
    private static final long serialVersionUID = -6585920003697419296L;

    private ConsoleClientUILoginInfo(Builder builder) {
        super(builder);
    }

    @Override
    public ConsoleClientUILoginInfo.Builder createBuilder() {
        return new ConsoleClientUILoginInfo.Builder();
    }

    @Override
    public ConsoleClientUILoginInfo.Builder createBuilderWithSameValues() {
        return (ConsoleClientUILoginInfo.Builder) super.createBuilderWithSameValues();
    }

    /**
     * Builder for ConsoleClientUILoginInfo
     * 
     * @author kitko
     *
     */
    public static final class Builder extends AbstractClientUILoginInfo.AbstractClientUILoginInfoBuilder<ConsoleClientUILoginInfo> implements org.vpda.common.util.Builder<ConsoleClientUILoginInfo> {

        /**
         * Sets values from {@link ConsoleClientUILoginInfo}
         * 
         * @param loginInfo
         * @return this
         */
        @Override
        public Builder setValues(ConsoleClientUILoginInfo loginInfo) {
            if (loginInfo == null) {
                return this;
            }
            super.setValues(loginInfo);
            return this;
        }

        @Override
        public Class<? extends ConsoleClientUILoginInfo> getTargetClass() {
            return ConsoleClientUILoginInfo.class;
        }

        @Override
        public ConsoleClientUILoginInfo build() {
            return new ConsoleClientUILoginInfo(this);
        }

    }

}
