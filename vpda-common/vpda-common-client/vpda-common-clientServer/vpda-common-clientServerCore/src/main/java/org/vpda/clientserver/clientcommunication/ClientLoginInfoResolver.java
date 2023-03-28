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

import org.vpda.clientserver.communication.ServiceInvocationResult;
import org.vpda.clientserver.communication.ValueInvocationResult;

/**
 * Will resolve CLientLoginInfo from passed args and result
 * 
 * @author kitko
 *
 */
public interface ClientLoginInfoResolver {
    /**
     * Resolves login info
     * 
     * @param ifaces
     * @param method
     * @param result
     * @param args
     * @return {@link ClientLoginInfo}
     */
    public ClientLoginInfo resolveLoginInfoForServiceReturned(Class[] ifaces, Method method, Object[] args, ServiceInvocationResult result);

    /**
     * @param ifaces
     * @param method
     * @param args
     * @param invocationResult
     */
    public void serviceValueReturned(Class[] ifaces, Method method, Object[] args, ValueInvocationResult invocationResult);
}
