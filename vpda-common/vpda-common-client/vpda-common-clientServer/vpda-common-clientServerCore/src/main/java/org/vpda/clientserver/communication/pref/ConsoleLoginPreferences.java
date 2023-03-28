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
package org.vpda.clientserver.communication.pref;

import org.vpda.clientserver.communication.data.ConsoleClientUILoginInfo;

public final class ConsoleLoginPreferences extends AbstractLoginPreferences {

    public ConsoleLoginPreferences(Builder builder) {
        super(builder);
    }

    private static final long serialVersionUID = -20827584850007608L;

    public final class Builder extends AbstractLoginPreferences.Builder<ConsoleLoginPreferences> implements org.vpda.common.util.Builder<ConsoleLoginPreferences> {

        @Override
        public Class<? extends ConsoleLoginPreferences> getTargetClass() {
            return ConsoleLoginPreferences.class;
        }

        @Override
        public ConsoleLoginPreferences build() {
            return new ConsoleLoginPreferences(this);
        }

    }

    @Override
    public ConsoleClientUILoginInfo createClientUILoginInfo() {
        ConsoleClientUILoginInfo.Builder builder = new ConsoleClientUILoginInfo.Builder();
        fillClientUILoginInfoBuilder(builder);
        return builder.build();
    }

}
