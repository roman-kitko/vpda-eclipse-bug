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

import java.util.List;

import org.vpda.common.service.Service;
import org.vpda.common.util.MethodParamProvider;

/**
 * Service that access ViewProvider by {@link ViewProviderDef}
 * 
 * @author kitko
 *
 */
public interface ViewProviderService extends Service {
    /**
     * /**Get provider
     * 
     * @param viewProviderDef
     * @return ViewProvider
     * @throws ViewProviderException
     */
    public ViewProvider getViewProvider(ViewProviderDef viewProviderDef) throws ViewProviderException;

    /**
     * Get provider
     * 
     * @param type
     * @param viewProviderDef
     * @return ViewProvider
     * @throws ViewProviderException
     */
    public <T extends ViewProvider> T getViewProvider(Class<T> type, ViewProviderDef viewProviderDef) throws ViewProviderException;

    /**
     * Get provider
     * 
     * @param type
     * @param viewProviderDef
     * @param dataReplacer
     * @return provider
     * @throws ViewProviderException
     */
    public <T extends ViewProvider> T getViewProvider(Class<T> type, ViewProviderDef viewProviderDef, MethodParamProvider dataReplacer) throws ViewProviderException;

    /**
     * Get and init of provider
     * 
     * @param type
     * @param viewProviderDef
     * @param viewProviderInitData
     * @return ViewProviderWithInitResponse
     * @throws ViewProviderException
     */
    public <T extends ViewProvider> ViewProviderWithInitResponse<T> getAndInitViewProvider(Class<T> type, ViewProviderDef viewProviderDef, ViewProviderInitData viewProviderInitData)
            throws ViewProviderException;

    /**
     * Get and init of provider
     * 
     * @param type
     * @param viewProviderDef
     * @param viewProviderInitData
     * @param dataReplacer
     * @return ViewProviderWithInitResponse
     * @throws ViewProviderException
     */
    public <T extends ViewProvider> ViewProviderWithInitResponse<T> getAndInitViewProvider(Class<T> type, ViewProviderDef viewProviderDef, ViewProviderInitData viewProviderInitData,
            MethodParamProvider dataReplacer) throws ViewProviderException;

    /**
     * 
     * @return all available provider definitions
     * @throws ViewProviderException
     */
    public List<ViewProviderDef> getAvailableProviders() throws ViewProviderException;
}
