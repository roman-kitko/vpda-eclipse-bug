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
package org.vpda.abstractclient.core.impl;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;

import org.vpda.abstractclient.core.Client;
import org.vpda.clientserver.communication.services.ClientResourceServices;
import org.vpda.common.service.resources.LoadResourceRequest;
import org.vpda.common.service.resources.UploadResourceRequest;
import org.vpda.internal.common.util.Assert;

/**
 * ClientServicesResolvedFromClient is iml of {@link ClientResourceServices}
 * that are resolved using <code>
 * 	{@link Client#getClientServices()}
 * </code>
 * 
 * @author rki
 *
 */
public final class ClientResourceServicesResolvedFromClient implements ClientResourceServices, Serializable {
    private static final long serialVersionUID = -255871155929858412L;
    private final Client client;
    private ClientResourceServices clientServices;

    /**
     * Creates ClientServicesResolvedFromClient
     * 
     * @param client
     */
    public ClientResourceServicesResolvedFromClient(Client client) {
        super();
        this.client = Assert.isNotNull(client, "client argument is null");
    }

    @Override
    public byte[] transferResource(LoadResourceRequest loadResourceRequest) throws IOException, MalformedURLException {
        ClientResourceServices clientServices = resolveClientServices();
        if (clientServices == null) {
            return null;
        }
        return clientServices.transferResource(loadResourceRequest);
    }

    @Override
    public void uploadBytes(UploadResourceRequest uploadRequest) throws IOException, MalformedURLException {
        ClientResourceServices clientServices = resolveClientServices();
        if (clientServices == null) {
            return;
        }
        clientServices.uploadBytes(uploadRequest);
    }

    private ClientResourceServices resolveClientServices() {
        if (clientServices != null) {
            return clientServices;
        }
        clientServices = client.getClientServices();
        return clientServices;
    }

}
