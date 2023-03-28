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

import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;

/**
 * Request for initial provider data
 * 
 * @author kitko
 * @param <T> type of comamnd
 *
 */
public final class ExecuteComamndStatelessRequest<T> extends BasicStatelessRequest {
    private static final long serialVersionUID = 1666103570472295841L;

    private final ViewProviderInitData initData;
    private final Command<? extends T> command;
    private final CommandExecutionEnv env;
    private final CommandEvent event;

    private ExecuteComamndStatelessRequest(Builder<T> builder) {
        super(builder);
        this.initData = builder.getInitData();
        this.command = builder.getCommand();
        this.env = builder.getEnv();
        this.event = builder.getEvent();
    }

    /**
     * @return the command
     */
    public Command<? extends T> getCommand() {
        return command;
    }

    /**
     * @return the env
     */
    public CommandExecutionEnv getEnv() {
        return env;
    }

    /**
     * @return the event
     */
    public CommandEvent getEvent() {
        return event;
    }

    /**
     * @return the initData
     */
    public ViewProviderInitData getInitData() {
        return initData;
    }

    /**
     * Builder for InitStatelessRequest
     * 
     * @author kitko
     * @param <T> type of comamnd
     *
     */
    public static final class Builder<T> extends BasicStatelessRequest.Builder<ExecuteComamndStatelessRequest> {
        private ViewProviderInitData initData;
        private Command<? extends T> command;
        private CommandExecutionEnv env;
        private CommandEvent event;

        /**
         * @return the initData
         */
        public ViewProviderInitData getInitData() {
            return initData;
        }

        /**
         * @return the command
         */
        public Command<? extends T> getCommand() {
            return command;
        }

        /**
         * @param command the command to set
         * @return this
         */
        public Builder<T> setCommand(Command<? extends T> command) {
            this.command = command;
            return this;
        }

        /**
         * @return the env
         */
        public CommandExecutionEnv getEnv() {
            return env;
        }

        /**
         * @param env the env to set
         * @return this
         */
        public Builder<T> setEnv(CommandExecutionEnv env) {
            this.env = env;
            return this;
        }

        /**
         * @return the event
         */
        public CommandEvent getEvent() {
            return event;
        }

        /**
         * @param event the event to set
         * @return this
         */
        public Builder<T> setEvent(CommandEvent event) {
            this.event = event;
            return this;
        }

        /**
         * @param initData the initData to set
         * @return this
         */
        public Builder setInitData(ViewProviderInitData initData) {
            this.initData = initData;
            return this;
        }

        @Override
        public Class<? extends ExecuteComamndStatelessRequest> getTargetClass() {
            return ExecuteComamndStatelessRequest.class;
        }

        @Override
        public ExecuteComamndStatelessRequest<T> build() {
            return new ExecuteComamndStatelessRequest<T>(this);
        }

        @SuppressWarnings("unchecked")
        @Override
        public org.vpda.clientserver.viewprovider.stateless.BasicStatelessRequest.Builder setValues(ExecuteComamndStatelessRequest request) {
            this.initData = request.getInitData();
            this.command = request.getCommand();
            this.env = request.getEnv();
            this.event = request.getEvent();
            return super.setValues(request);
        }

    }

}
