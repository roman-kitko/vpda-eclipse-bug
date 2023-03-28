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
package org.vpda.clientserver.viewprovider.generic.stateless;

import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;

/**
 * Request for submit of generic provider stateless service
 * 
 * @author kitko
 *
 */
public final class SubmitDataRequest extends BasicStatelessRequest {

    private static final long serialVersionUID = 2626055842872669768L;
    private final ViewProviderInitData initData;
    private final ComponentsGroupsDef compsDef;
    private final CommandEvent commandEvent;

    /**
     * 
     * @return components
     */
    public ComponentsGroupsDef getCompsDef() {
        return compsDef;
    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return initData;
    }

    /**
     * @return the commandEvent
     */
    public CommandEvent getCommandEvent() {
        return commandEvent;
    }

    private SubmitDataRequest(Builder builder) {
        super(builder);
        this.compsDef = builder.getCompsDef();
        this.initData = builder.getInitData();
        this.commandEvent = builder.getCommandEvent();
    }

    /**
     * Builder for {@link SubmitDataRequest}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends BasicStatelessRequest.Builder<SubmitDataRequest> {
        private ComponentsGroupsDef compsDef;
        private ViewProviderInitData initData;
        private CommandEvent commandEvent;

        /**
         * @return the commandEvent
         */
        public CommandEvent getCommandEvent() {
            return commandEvent;
        }

        /**
         * @param commandEvent the commandEvent to set
         * @return this
         */
        public Builder setCommandEvent(CommandEvent commandEvent) {
            this.commandEvent = commandEvent;
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
         * @return the compsDef
         */
        public ComponentsGroupsDef getCompsDef() {
            return compsDef;
        }

        /**
         * @param compsDef compsDef to set
         * @return this
         */
        public Builder setCompsDef(ComponentsGroupsDef compsDef) {
            this.compsDef = compsDef;
            return this;
        }

        @Override
        public Class<? extends SubmitDataRequest> getTargetClass() {
            return SubmitDataRequest.class;
        }

        @Override
        public SubmitDataRequest build() {
            return new SubmitDataRequest(this);
        }

        @Override
        public org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest.Builder setValues(SubmitDataRequest request) {
            this.compsDef = request.getCompsDef();
            this.initData = request.getInitData();
            this.commandEvent = request.getCommandEvent();
            super.setValues(request);
            return this;
        }

    }

}
