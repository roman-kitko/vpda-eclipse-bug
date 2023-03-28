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
package org.vpda.clientserver.viewprovider.stateless;

import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.service.Service;

/**
 * Stateless view provider related service
 * 
 * @author kitko
 *
 */
public interface StatelessViewProviderServices extends Service {
    /**
     * 
     * @param request
     * @return provider meta info
     * @throws ViewProviderException
     */
    public ViewProviderInitResponse getProviderInfo(InitStatelessRequest request) throws ViewProviderException;

    /**
     * Executes command
     * 
     * @param request
     * @return execution result
     * @throws Exception
     */
    public <T> ExecutionResult<T> executeCommand(ExecuteComamndStatelessRequest<T> request) throws Exception;

}
