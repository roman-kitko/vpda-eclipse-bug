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
import org.vpda.internal.common.util.Assert;

/**
 * Client complex result
 * 
 * @author kitko
 *
 */
public final class ClientComplexValueInvocationResultImpl extends AbstractClientInvocationResult implements ClientComplexValueInvocationResult {

    private final ClientCommunicationExporter exporter;

    /**
     * Creates ClientComplexValueInvocationResult
     * 
     * @param result
     * @param exporter
     */
    public ClientComplexValueInvocationResultImpl(Object result, ClientCommunicationExporter exporter) {
        super(InvocationResultType.COMPLEX, result);
        this.exporter = Assert.isNotNullArgument(exporter, "exporter");
    }

    /**
     * 
     */
    private static final long serialVersionUID = -1735432177467944507L;

    @Override
    public ClientCommunicationExporter getClientExporter() {
        return exporter;
    }

}
