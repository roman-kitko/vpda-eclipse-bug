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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

import org.vpda.clientserver.clientcommunication.ClientInvocationResult;
import org.vpda.clientserver.clientcommunication.CommunicationInvocationHandler;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.util.logging.LoggerMethodTracer;
import org.vpda.internal.common.util.Assert;

/**
 * Command which calls through reflection some method on resolved interface.
 * This command will be always passed and executed on server side of
 * communication.
 * 
 * @author kitko
 *
 */
public abstract class AbstractInvocationCommand implements Command<ClientInvocationResult>, Serializable {

    private static final long serialVersionUID = 5253136778992316580L;

    final Class[] ifaces;
    final String methodName;
    final Class[] paramTypes;
    final Object[] args;
    final CommunicationId communicationId;
    final CommunicationStateKind communicationStateKind;

    CommunicationId forwardingCommunicationId;

    private static LoggerMethodTracer logger = LoggerMethodTracer.getLogger(AbstractInvocationCommand.class);

    AbstractInvocationCommand(CommunicationStateKind communicationStateKind, CommunicationId communicationId, Class[] ifaces, String methodName, Class[] paramTypes, Object[] args) {
        super();
        this.communicationStateKind = Assert.isNotNullArgument(communicationStateKind, "communicationStateKind");
        this.communicationId = Assert.isNotNullArgument(communicationId, "communicationId");
        this.ifaces = Arrays.copyOf(Assert.isNotNullArgument(ifaces, "ifaces"), ifaces.length);
        Assert.arrayArgumentNotEmpty(ifaces, "ifaces");
        this.methodName = Assert.isNotNull(methodName);
        this.paramTypes = Arrays.copyOf(Assert.isNotNullArgument(paramTypes, "paramTypes"), paramTypes.length);
        this.args = args != null ? Arrays.copyOf(args, args.length) : null;
    }

    AbstractInvocationCommand(CommunicationStateKind communicationStateKind, CommunicationId communicationId, Class iface, String methodName, Class[] paramTypes, Object[] args) {
        this(communicationStateKind, communicationId, new Class[] { iface }, methodName, paramTypes, args);
    }

    /**
     * @return communicationId
     */
    public CommunicationId getCommunicationId() {
        return communicationId;
    }

    /**
     * Set forwarding communication id
     * 
     * @param forwardingCommunicationId
     */
    public void setForwardingCommunicationId(CommunicationId forwardingCommunicationId) {
        this.forwardingCommunicationId = forwardingCommunicationId;
    }

    /**
     * 
     * @return communication Id specified by forwarding client
     */
    public CommunicationId getForwardingCommunicationId() {
        return forwardingCommunicationId;
    }

    /**
     * @return the ifaces
     */
    public Class[] getIfaces() {
        return ifaces != null ? Arrays.copyOf(ifaces, ifaces.length) : null;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return the paramTypes
     */
    public Class[] getParamTypes() {
        return paramTypes != null ? Arrays.copyOf(paramTypes, paramTypes.length) : null;
    }

    /**
     * @return the args
     */
    public Object[] getArgs() {
        return args != null ? Arrays.copyOf(args, args.length) : null;
    }

    @Override
    public ClientInvocationResult execute(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
        Object result = null;
        ObjectMethod objectMethod = null;
        CommunicationInvocationHandler invocationHandler = null;
        try {
            objectMethod = determineObjectMethod(env);
            @SuppressWarnings("unused")
            Class clazz = objectMethod.object.getClass();
            result = null;
            objectMethod.method.setAccessible(true);
            invocationHandler = env.resolveObject(CommunicationInvocationHandler.class);
            if (invocationHandler == null) {
                throw new IllegalStateException("Cannot resolve InvocationHandler from environment");
            }
            result = invocationHandler.invokeServerObjectMethod(communicationId, forwardingCommunicationId, objectMethod.object, objectMethod.method, args, determineInvocationContext());
        }
        catch (Exception e) {
            if (invocationHandler == null) {
                logger.log(Level.SEVERE, "invocationHandler handler null while logging folowing exception", e);
                throw e;
            }
            invocationHandler.handleExecutionException(e, communicationId, forwardingCommunicationId, objectMethod.object, objectMethod.method, args);
        }
        ClientInvocationResult handleExecutionResult = invocationHandler.handleExecutionResult(communicationStateKind, result, objectMethod.object, objectMethod.method);
        return handleExecutionResult;
    }

    abstract ObjectMethod determineObjectMethod(CommandExecutionEnv env) throws Exception;

    abstract ObjectResolver determineInvocationContext();

    static final class ObjectMethod {
        Object object;
        Method method;

        ObjectMethod(Object object, Method method) {
            super();
            this.object = object;
            this.method = method;
        }
    }

}
