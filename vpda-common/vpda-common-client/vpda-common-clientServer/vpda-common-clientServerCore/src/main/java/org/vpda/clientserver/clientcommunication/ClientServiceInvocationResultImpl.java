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

import java.util.Arrays;

import org.vpda.clientserver.communication.InvocationResultType;
import org.vpda.common.service.ServiceKind;

/**
 * Client service result of remote invocation
 * 
 * @author kitko
 *
 */
public final class ClientServiceInvocationResultImpl extends AbstractClientInvocationResult implements ClientServiceInvocationResult {
    private static final long serialVersionUID = 1L;
    private final Class[] clientInterfaces;
    private final ServiceKind serviceKind;

    /**
     * Create ServiceInvocationResult
     * 
     * @param result
     * @param interfaces
     * @param serviceKind
     */
    public ClientServiceInvocationResultImpl(Object result, Class[] interfaces, ServiceKind serviceKind) {
        super(InvocationResultType.SERVICE, result);
        this.serviceKind = serviceKind;
        this.clientInterfaces = Arrays.copyOf(interfaces, interfaces.length);
    }

    /**
     * @return the clientInterfaces
     */
    @Override
    public Class[] getClientInterfaces() {
        return clientInterfaces;
    }

    /**
     * @return the serviceKind
     */
    @Override
    public ServiceKind getServiceKind() {
        return serviceKind;
    }

    @Override
    public ClientCommunicationExporter getClientExporter() {
        return DefaultServiceClientCommunicationExporter.INSTANCE;
    }

    @Override
    public String toString() {
        return "ClientServiceInvocationImpl [clientInterfaces=" + Arrays.toString(clientInterfaces) + ", serviceKind=" + serviceKind + ", getResultType()=" + getResultType() + ", getResult()="
                + getResult() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(clientInterfaces);
        result = prime * result + ((serviceKind == null) ? 0 : serviceKind.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ClientServiceInvocationResultImpl other = (ClientServiceInvocationResultImpl) obj;
        if (!Arrays.equals(clientInterfaces, other.clientInterfaces)) {
            return false;
        }
        if (serviceKind != other.serviceKind) {
            return false;
        }
        return true;
    }

}
