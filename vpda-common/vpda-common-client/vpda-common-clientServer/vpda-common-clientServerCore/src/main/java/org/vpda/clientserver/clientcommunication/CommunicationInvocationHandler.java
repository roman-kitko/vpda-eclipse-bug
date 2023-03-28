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
package org.vpda.clientserver.clientcommunication;

import java.lang.reflect.Method;

import org.vpda.clientserver.communication.CommunicationId;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.common.ioc.objectresolver.ObjectResolver;

/**
 * Handler of invocation in server side communication
 * 
 * @author kitko
 *
 */
public interface CommunicationInvocationHandler {
    /**
     * Invokes server object method
     * 
     * @param clientCommunicationId
     * @param forwardingCommunicationId
     * @param object
     * @param method
     * @param args
     * @param context
     * @return method execution result
     * @throws Exception
     */
    public Object invokeServerObjectMethod(CommunicationId clientCommunicationId, CommunicationId forwardingCommunicationId, Object object, Method method, Object[] args, ObjectResolver context)
            throws Exception;

    /**
     * Handle the result of command execution
     * 
     * @param communicationStateKind
     * @param result
     * @param object
     * @param method
     * @return AbstractInvocationResult
     */
    public ClientInvocationResult handleExecutionResult(CommunicationStateKind communicationStateKind, Object result, Object object, Method method);

    /**
     * Handle exception thrown while method execution
     * 
     * @param e
     * @param clientCommunicationId
     * @param forwardingCommunicationId
     * @param object
     * @param method
     * @param args
     * @throws Exception
     */
    public void handleExecutionException(Exception e, CommunicationId clientCommunicationId, CommunicationId forwardingCommunicationId, Object object, Method method, Object[] args) throws Exception;

}
