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
package org.vpda.clientserver.servercommunication;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.vpda.clientserver.clientcommunication.ClientInvocationResult;
import org.vpda.clientserver.clientcommunication.ClientValueInvocationResultImpl;
import org.vpda.clientserver.clientcommunication.CommunicationInvocationHandler;
import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.common.ioc.objectresolver.ObjectResolver;

/**
 * Empty test implementation of {@link CommunicationInvocationHandler}
 * 
 * @author kitko
 *
 */
public final class TestInvocationHandler implements CommunicationInvocationHandler, Serializable {
    /**
     * Creates EmptyServerCommunication
     */
    public TestInvocationHandler() {
    }

    private static final long serialVersionUID = -4242977065638233481L;

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public Object invokeServerObjectMethod(CommunicationId clientCommunicationId, CommunicationId forwardingCommunicationId, Object obj, Method method, Object[] args, ObjectResolver context)
            throws Exception {
        return method.invoke(obj, args);
    }

    @Override
    public ClientInvocationResult handleExecutionResult(CommunicationStateKind communicationStateKind, Object result, Object object, Method method) {
        return new ClientValueInvocationResultImpl(result);
    }

    @Override
    public void handleExecutionException(Exception e, CommunicationId clientCommunicationId, CommunicationId forwardingCommunicationId, Object object, Method method, Object[] args) throws Exception {
    }

}
