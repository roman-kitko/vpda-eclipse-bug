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
/**
 * 
 */
package org.vpda.client.clientcommunication;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.clientcommunication.ClientLoginInfoResolver;
import org.vpda.clientserver.clientcommunication.ClientServiceInvocationResult;
import org.vpda.clientserver.clientcommunication.ClientValueInvocationResult;
import org.vpda.clientserver.clientcommunication.MutableClientCommunicationFactory;
import org.vpda.clientserver.communication.CommunicationException;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationProtocol;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ResultWithCommands;
import org.vpda.common.command.executor.impl.LazyDelegateCommandExecutor;
import org.vpda.common.service.ServiceKind;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ClassUtil;

/**
 * Basic Abstraction for {@link ClientCommunication}
 * 
 * @author kitko
 *
 */
public abstract class AbstractClientCommunication implements ClientCommunication, Serializable {
    private static final long serialVersionUID = -6336227271460222658L;
    /** Id of communication */
    protected final CommunicationId commId;
    /** Client communication factory */
    protected final ClientCommunicationFactory ccf;

    /**
     * Creates abstract client communication
     * 
     * @param ccf
     * @param commId
     */
    protected AbstractClientCommunication(ClientCommunicationFactory ccf, CommunicationId commId) {
        this.commId = Assert.isNotNullArgument(commId, "commId");
        this.ccf = Assert.isNotNullArgument(ccf, "ccf");
        if (ccf instanceof MutableClientCommunicationFactory) {
            ((MutableClientCommunicationFactory) ccf).registerClientCommunication(this);
        }
    }

    @Override
    public CommunicationId getCommunicationId() {
        return commId;
    }

    @Override
    public CommunicationProtocol getProtocol() {
        return commId.getProtocol();
    }

    @Override
    public void start() throws CommunicationException {
    }

    @Override
    public void stop() {
    }

    @Override
    public void notifyNewServerRef(Object serverRef, Object clientProxy, Class[] ifaces) {
    }

    @Override
    public Object simpleValueReturned(Object value) {
        return value;
    }

    @Override
    public boolean shouldStatelessEntryTryToReconnectOnThisFailure(Exception e) {
        return false;
    }

    @Override
    public Object processServiceResult(CommandExecutionEnv env, Class[] ifaces, ClientLoginInfoResolver clientLoginInfoResolver, final Method method, final Object[] args,
            ClientServiceInvocationResult serviceInvocationResult) {
        if (ServiceKind.Statefull.equals(serviceInvocationResult.getServiceKind())) {
            if (serviceInvocationResult.getResult() == null) {
                return null;
            }
            return handleStatefullExecutionResult(env, ifaces, method, serviceInvocationResult.getResult(), serviceInvocationResult);
        }
        else {
            return handleStatelessExecutionResult(env, ifaces, method, args, serviceInvocationResult, clientLoginInfoResolver);
        }
    }

    @Override
    public Object processValueResult(CommandExecutionEnv env, Class[] ifaces, ClientLoginInfoResolver clientLoginInfoResolver, final Method method, final Object[] args,
            ClientValueInvocationResult invocationResult) {
        Object result = invocationResult.getResult();
        clientLoginInfoResolver.serviceValueReturned(ifaces, method, args, invocationResult);
        // if there is something else
        if (result != null && !ClassUtil.isMethodResultCompatible(method.getReturnType(), result)) {
            throw new VPDARuntimeException("Incompatible value was returned in CommandExecutorInvocationHandler. Expected " + method.getReturnType() + " , but was " + result.getClass());
        }
        return simpleValueReturned(result);
    }

    private Object handleStatelessExecutionResult(CommandExecutionEnv env, Class[] ifaces, final Method method, final Object[] args, ClientServiceInvocationResult serviceInvocationResult,
            ClientLoginInfoResolver clientLoginInfoResolver) {
        final ClientLoginInfo loginInfo = clientLoginInfoResolver.resolveLoginInfoForServiceReturned(ifaces, method, args, serviceInvocationResult);
        final class ResolveCall implements Callable<CommandExecutor>, Serializable {
            private static final long serialVersionUID = -250105122737760309L;

            @Override
            public CommandExecutor call() throws Exception {
                CommandExecutor statelessServiceRegistryExecutor = getStatelessClientServerEntryExecutor(loginInfo);
                return statelessServiceRegistryExecutor;
            }
        }
        Callable<CommandExecutor> resolveCall = new ResolveCall();
        Object newProxy = createStatelessProxy(new LazyDelegateCommandExecutor(resolveCall), env, serviceInvocationResult.getClientInterfaces(), loginInfo);
        return newProxy;
    }

    @SuppressWarnings("unchecked")
    private Object handleStatefullExecutionResult(CommandExecutionEnv env, Class[] ifaces, Method method, Object result, ClientServiceInvocationResult serviceInvocationResult) {
        if (result instanceof CommandExecutor) {
            CommandExecutor commandExecutor = (CommandExecutor) result;
            Object newProxy = createStatefullProxy(commandExecutor, env, serviceInvocationResult.getClientInterfaces());
            notifyNewServerRef(result, newProxy, ifaces);
            return newProxy;
        }
        else if (result instanceof ResultWithCommands) {
            if (!method.getReturnType().isAssignableFrom(ResultWithCommands.class)) {
                throw new VPDARuntimeException("Incompatible value was returned in CommandExecutorInvocationHandler : class = " + result.getClass());
            }
            ResultWithCommands resultWithCommands = (ResultWithCommands) result;
            Object realResult = resultWithCommands.getResult();
            if (realResult == null) {
                return resultWithCommands;
            }
            else if (realResult instanceof CommandExecutor) {
                CommandExecutor commandExecutor = (CommandExecutor) realResult;
                Object newProxy = createStatefullProxy(commandExecutor, env, serviceInvocationResult.getClientInterfaces());
                notifyNewServerRef(realResult, newProxy, ifaces);

                List<Command> commands = resultWithCommands.getCommands();
                ResultWithCommands newRes = new ResultWithCommands(newProxy, commands.toArray(new Command[commands.size()]));
                return newRes;
            }
            else {
                return resultWithCommands;
            }
        }
        throw new VPDARuntimeException("Incompatible value was returned in CommandExecutorInvocationHandler : class = " + result.getClass());
    }

}
