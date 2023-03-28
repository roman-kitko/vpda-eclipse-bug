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

import org.vpda.clientserver.communication.InvocationResultType;

/**
 * Value only CLient site invocation result with predefined
 * ClientCommunicationExporter
 * 
 * @author kitko
 *
 */
public final class ClientValueInvocationResultImpl extends AbstractClientInvocationResult implements ClientValueInvocationResult {
    /**
     * Creates ClientValueInvocationResult
     * 
     * @param result
     */
    public ClientValueInvocationResultImpl(Object result) {
        super(InvocationResultType.VALUE, result);
    }

    private static final long serialVersionUID = -816719332528569219L;

    @Override
    public ClientCommunicationExporter getClientExporter() {
        return DefaultValueClientCommunicationExporter.INSTANCE;
    }

    /**
     * @param result
     * @return ClientValueInvocationResultImpl
     */
    public static ClientValueInvocationResultImpl create(Object result) {
        return new ClientValueInvocationResultImpl(result);
    }

}
