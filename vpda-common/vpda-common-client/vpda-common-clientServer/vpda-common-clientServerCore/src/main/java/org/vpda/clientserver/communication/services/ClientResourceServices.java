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
package org.vpda.clientserver.communication.services;

import java.io.IOException;
import java.net.MalformedURLException;

import org.vpda.common.service.Service;
import org.vpda.common.service.resources.LoadResourceRequest;
import org.vpda.common.service.resources.UploadResourceRequest;

/**
 * Services Client can call to run on server.
 * 
 * @author kitko
 *
 */
public interface ClientResourceServices extends Service {
    /**
     * @param loadResourceRequest
     * @return transfered resources bytes
     * @throws IOException           when there was error while creating resource
     * @throws MalformedURLException while resolving URL
     */
    public byte[] transferResource(LoadResourceRequest loadResourceRequest) throws IOException, MalformedURLException;

    /**
     * Upload file to server and storing it to URL
     * 
     * @param uploadRequest
     * @throws IOException           when there is error while storing file on
     *                               server
     * @throws MalformedURLException while resolving URL
     */
    public void uploadBytes(UploadResourceRequest uploadRequest) throws IOException, MalformedURLException;

}
