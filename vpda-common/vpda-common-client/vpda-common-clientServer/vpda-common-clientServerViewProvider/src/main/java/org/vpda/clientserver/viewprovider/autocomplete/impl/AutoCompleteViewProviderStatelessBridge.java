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
package org.vpda.clientserver.viewprovider.autocomplete.impl;

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteQuery;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProvider;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompletionResults;
import org.vpda.clientserver.viewprovider.autocomplete.stateless.CompleteQueryRequest;
import org.vpda.clientserver.viewprovider.autocomplete.stateless.StatelessAutoCompleteViewProviderServices;
import org.vpda.clientserver.viewprovider.stateless.ExecuteComamndStatelessRequest;
import org.vpda.clientserver.viewprovider.stateless.InitStatelessRequest;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.service.ServiceInfo;
import org.vpda.common.service.ServiceKind;

/**
 * Stateless bridge for AutoCompleteViewProvider
 * 
 * @author kitko
 *
 */
@ServiceInfo(kind = ServiceKind.Stateless, clientExportTypes = { AutoCompleteViewProvider.class })
public class AutoCompleteViewProviderStatelessBridge implements AutoCompleteViewProvider, Serializable {
    private static final long serialVersionUID = 135792205173510609L;
    private final ViewProviderDef def;
    private final StatelessAutoCompleteViewProviderServices statelessViewProviderServices;
    private ViewProviderInitData initData;
    private ViewProviderID initProviderID;

    /**
     * @param def
     * @param statelessViewProviderServices
     */
    public AutoCompleteViewProviderStatelessBridge(ViewProviderDef def, StatelessAutoCompleteViewProviderServices statelessViewProviderServices) {
        super();
        this.def = def;
        this.statelessViewProviderServices = statelessViewProviderServices;
    }

    /**
     * @param viewProviderDef
     * @param service
     * @param initData
     * @param initResponse
     */
    public AutoCompleteViewProviderStatelessBridge(ViewProviderDef viewProviderDef, StatelessAutoCompleteViewProviderServices service, ViewProviderInitData initData,
            AutoCompleteViewProviderInitResponse initResponse) {
        this.def = viewProviderDef;
        this.statelessViewProviderServices = service;
        this.initData = initData;
        this.initProviderID = initResponse != null && initResponse.getViewProviderInfo() != null ? initResponse.getViewProviderInfo().getViewProviderID() : null;
    }

    @Override
    public void close() {
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        @SuppressWarnings("unchecked")
        ExecuteComamndStatelessRequest.Builder<T> builder = new ExecuteComamndStatelessRequest.Builder<T>().setCommand(command).setEnv(env).setEvent(event).setInitData(initData);
        builder.setDef(def);
        builder.setProviderId(initProviderID);
        ExecuteComamndStatelessRequest<T> request = builder.build();
        return statelessViewProviderServices.executeCommand(request);
    }

    @Override
    public String getExecutorId() {
        return System.identityHashCode(this) + "";
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return this;
    }

    @Override
    public void unreferenced() {
    }

    @Override
    public AutoCompletionResults completeQuery(AutoCompleteQuery query) throws ViewProviderException {
        CompleteQueryRequest.Builder builder = new CompleteQueryRequest.Builder();
        builder.setDef(def).setProviderId(initProviderID);
        builder.setQuery(query);
        return statelessViewProviderServices.completeQuery(builder.build());
    }

    @Override
    public AutoCompleteViewProviderInitResponse initProvider(ViewProviderInitData viewProviderInitData) throws ViewProviderException {
        InitStatelessRequest.Builder builder = new InitStatelessRequest.Builder();
        builder.setDef(def).setProviderId(initProviderID);
        builder.setInitData(viewProviderInitData);
        AutoCompleteViewProviderInitResponse providerInfo = statelessViewProviderServices.getProviderInfo(builder.build());
        initProviderID = providerInfo.getViewProviderInfo().getViewProviderID();
        this.initData = viewProviderInitData;
        return providerInfo;
    }

}
