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
package org.vpda.clientserver.communication.command;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.vpda.clientserver.clientcommunication.ClientCommunicationFactory;
import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.util.exceptions.VPDARuntimeException;

/**
 * Implementation of CommadExecutorClientProxyFactory using {@link Proxy}
 * 
 * @author kitko
 *
 */
public final class CommadExecutorClientProxyFactoryDynamicProxy implements CommadExecutorClientProxyFactory, Serializable {
    private static final long serialVersionUID = 5893909447962552429L;

    @Override
    public Object createStatefullProxy(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor executor, CommandExecutionEnv env, Class[] ifaces) {
        if (ifaces.length == 0) {
            throw new IllegalArgumentException("No interface specified for proxy creation");
        }
        InvocationHandler invocationHandler = new StatefullCommandExecutorInvocationHandler(ccf, communicationId, executor, env, ifaces);
        ClassLoader classLoader = resolveClassLoader(ifaces);
        ;
        Class<?> proxyClass = Proxy.getProxyClass(classLoader, ifaces);
        Object result = null;
        try {
            result = proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
        }
        catch (Exception e) {
            throw new VPDARuntimeException("Error creating proxy", e);
        }
        return result;
    }

    @Override
    public Object createStatelessProxy(ClientCommunicationFactory ccf, CommunicationId communicationId, CommandExecutor executor, CommandExecutionEnv env, ClientLoginInfo loginInfo, Class[] ifaces) {
        if (ifaces.length == 0) {
            throw new IllegalArgumentException("No interface specified for proxy creation");
        }
        InvocationHandler invocationHandler = new StatelessCommandExecutorInvocationHandler(ccf, communicationId, executor, env, ifaces, loginInfo);
        ClassLoader classLoader = resolveClassLoader(ifaces);
        ;
        Class<?> proxyClass = Proxy.getProxyClass(classLoader, ifaces);
        Object result = null;
        try {
            result = proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
        }
        catch (Exception e) {
            throw new VPDARuntimeException("Error creating proxy", e);
        }
        return result;
    }

    private ClassLoader resolveClassLoader(Class[] ifaces) {
        ClassLoader classLoader = null;
        if (ifaces.length == 1) {
            classLoader = ifaces[0].getClassLoader();
        }
        else {
            for (int i = 0; i < ifaces.length; i++) {
                if (classLoader == null) {
                    classLoader = ifaces[i].getClassLoader();
                }
                else {
                    if (ifaces[i].getClassLoader().getParent() == classLoader) {
                        classLoader = ifaces[i].getClassLoader();
                    }
                }
            }
        }
        return classLoader;
    }

    /**
     * Creates CommadExecutorClientProxyFactoryDynamicProxies
     */
    public CommadExecutorClientProxyFactoryDynamicProxy() {

    }

}
