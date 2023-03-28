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
package org.vpda.clientserver.communication.command;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.vpda.clientserver.clientcommunication.ClientCommunication;
import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientInvocationResult;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.clientcommunication.ClientLoginInfoResolver;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.clientserver.communication.ServiceInvocationResult;
import org.vpda.clientserver.communication.ValueInvocationResult;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.util.exceptions.TracedException;
import org.vpda.common.util.exceptions.TracedRuntimeExceptionWrapper;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.internal.clientserver.communication.CommunicationKeyHolder;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.CacheKeyCreator;
import org.vpda.internal.common.util.ClassUtil;
import org.vpda.internal.common.util.StaticCache;

/**
 * Invocation handler around command executor. It gets CommandExecutor and
 * interface. It tries to resolve instance by interface and call method on it.
 * It is executed on client.
 * 
 * @author kitko
 *
 */
abstract class AbstractCommandExecutorInvocationHandler implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6674980960730337496L;
    private static transient final LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AbstractCommandExecutorInvocationHandler.class);
    final CommandExecutor commandExecutor;
    final CommandExecutionEnv env;
    final Class[] ifaces;
    final CommunicationId communicationId;
    final ClientCommunicationFactory ccf;
    final CommunicationStateKind communicationStateKind;

    private static final MyLoginResolver MYLOGINRESOLVER = new MyLoginResolver();

    AbstractCommandExecutorInvocationHandler(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor commandExecutor, CommandExecutionEnv env,
            CommunicationStateKind communicationStateKind, Class... ifaces) {
        super();
        this.ccf = Assert.isNotNullArgument(ccf, "ccf");
        this.commandExecutor = Assert.isNotNullArgument(commandExecutor, "commandExecutor");
        this.env = Assert.isNotNullArgument(env, "env");
        this.ifaces = Assert.isNotNullArgument(ifaces, "ifaces");
        Assert.arrayArgumentNotEmpty(ifaces, "ifaces");
        this.communicationId = Assert.isNotNullArgument(communicationId, "communicationId");
        this.communicationStateKind = Assert.isNotNullArgument(communicationStateKind, "communicationStateKind");
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        AbstractInvocationCommand command = createInvocationCommand(method, args);
        ClientInvocationResult invocationCommandResult = null;
        CacheKeyCreator oldCreator = null;
        try {
            oldCreator = StaticCache.setKeyCreator(CommunicationKeyHolder.serverCacheKeyCreatorHolder.getCacheKeyCreator());
            invocationCommandResult = commandExecutor.executeCommand(command, env, null).get();
        }
        catch (Exception e) {
            handleCommandExecutionException(method, e);
        }
        finally {
            StaticCache.setKeyCreator(oldCreator);
        }
        if (invocationCommandResult == null) {
            return null;
        }
        final ClientCommunication clientCommunication = ccf.createCommunication(communicationId);
        return invocationCommandResult.processInvocationResult(clientCommunication, env, ifaces, createLoginResolver(), method, args);
    }

    abstract AbstractInvocationCommand createInvocationCommand(final Method method, final Object[] args);

    private void handleCommandExecutionException(Method method, Exception e) throws Exception {
        boolean log = true;
        if (e instanceof TracedException) {
            log = ((TracedException) e).shouldLog() && !((TracedException) e).isLogged();
        }
        if (log) {
            logger.log(Level.SEVERE, "Error while executing InvocationCommand", e);
            if (e instanceof TracedException) {
                ((TracedException) e).setLogged(true);
            }
        }
        if (ClassUtil.canMethodThrowException(method, e)) {
            throw e;
        }
        TracedRuntimeExceptionWrapper wrapper = new TracedRuntimeExceptionWrapper("Undeclared exception thrown from commandExecutor.executeCommand", e);
        wrapper.setLogged(true);
        throw wrapper;
    }

    ClientLoginInfoResolver createLoginResolver() {
        return MYLOGINRESOLVER;
    }

    private static final class MyLoginResolver implements ClientLoginInfoResolver {
        @Override
        public ClientLoginInfo resolveLoginInfoForServiceReturned(Class[] ifaces, Method method, Object[] args, ServiceInvocationResult result) {
            for (int i = 0; i < args.length; i++) {
                if (ClientLoginInfo.class.isInstance(args[i])) {
                    return (ClientLoginInfo) args[i];
                }
            }
            throw new IllegalStateException("Cannot resolve loginInfo");
        }

        @Override
        public void serviceValueReturned(Class[] ifaces, Method method, Object[] args, ValueInvocationResult invocationResult) {
        }
    }
}
