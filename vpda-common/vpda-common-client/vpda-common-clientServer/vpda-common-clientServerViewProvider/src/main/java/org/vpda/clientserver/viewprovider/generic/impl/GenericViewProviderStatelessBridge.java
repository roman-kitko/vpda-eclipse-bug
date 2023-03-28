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
package org.vpda.clientserver.viewprovider.generic.impl;

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.generic.GenericViewProvider;
import org.vpda.clientserver.viewprovider.generic.GenericViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.generic.stateless.StatelessGenericViewProviderServices;
import org.vpda.clientserver.viewprovider.stateless.ExecuteComamndStatelessRequest;
import org.vpda.clientserver.viewprovider.stateless.InitStatelessRequest;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.service.ServiceInfo;
import org.vpda.common.service.ServiceKind;
import org.vpda.internal.common.util.Assert;

/**
 * Bridge interface between {@link GenericViewProvider} and
 * {@link StatelessGenericViewProviderServices}
 * 
 * @author kitko
 *
 */
@ServiceInfo(kind = ServiceKind.Stateless, clientExportTypes = { GenericViewProvider.class })
public final class GenericViewProviderStatelessBridge implements GenericViewProvider, Serializable {
    private static final long serialVersionUID = -6938735066742985728L;
    private final StatelessGenericViewProviderServices statelessGenericViewProviderServices;
    private final ViewProviderDef def;
    private ViewProviderInitData initData;
    private ViewProviderID initProviderID;

    /**
     * Creates GenericViewProviderStatelessBridge
     * 
     * @param def
     * @param statelessGenericViewProviderServices
     */
    public GenericViewProviderStatelessBridge(ViewProviderDef def, StatelessGenericViewProviderServices statelessGenericViewProviderServices) {
        this(def, statelessGenericViewProviderServices, null, null);
    }

    /**
     * Creates GenericViewProviderStatelessBridge
     * 
     * @param def
     * @param statelessGenericViewProviderServices
     * @param initData
     * @param initialResponse
     */
    public GenericViewProviderStatelessBridge(ViewProviderDef def, StatelessGenericViewProviderServices statelessGenericViewProviderServices, ViewProviderInitData initData,
            GenericViewProviderInitResponse initialResponse) {
        this.statelessGenericViewProviderServices = Assert.isNotNullArgument(statelessGenericViewProviderServices, "statelessGenericViewProviderServices");
        this.def = Assert.isNotNullArgument(def, "def");
        this.initData = initData;
        if (initialResponse != null) {
            initProviderID = initialResponse.getViewProviderInfo().getViewProviderID();
        }
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
        return statelessGenericViewProviderServices.executeCommand(request);
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
    public GenericViewProviderInitResponse initProvider(ViewProviderInitData initData) throws ViewProviderException {
        this.initData = null;
        InitStatelessRequest.Builder builder = new InitStatelessRequest.Builder();
        builder.setDef(def);
        builder.setInitData(initData);
        GenericViewProviderInitResponse genericViewProviderInitResponse = statelessGenericViewProviderServices.getProviderInfo(builder.build());
        this.initData = initData;
        this.initProviderID = genericViewProviderInitResponse.getViewProviderInfo().getViewProviderID();
        return genericViewProviderInitResponse;
    }

}
