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
package org.vpda.clientserver.viewprovider;

import org.vpda.common.command.CommandExecutor;

/**
 * Client view of UI provider on server.
 * 
 * @author kitko
 *
 */
public interface ViewProvider extends CommandExecutor, AutoCloseable {
    /**
     * Init method of provider. This method must be called before working with
     * provider and can be called only once
     * 
     * @param viewProviderInitData
     * @return ResultWithCommands<ViewProviderInfo>
     * @throws ViewProviderException
     */
    public ViewProviderInitResponse initProvider(ViewProviderInitData viewProviderInitData) throws ViewProviderException;

    /**
     * Close this provider After closing provider, no other work with view is
     * possible
     *
     */
    @Override
    public void close();

}
