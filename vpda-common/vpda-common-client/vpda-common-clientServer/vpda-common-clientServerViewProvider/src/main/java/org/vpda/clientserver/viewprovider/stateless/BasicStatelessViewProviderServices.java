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

import java.util.List;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderKind;
import org.vpda.clientserver.viewprovider.list.stateless.StatelessListViewProviderServices;
import org.vpda.common.service.Service;

/** Stateless bases services for accesing view providers */
public interface BasicStatelessViewProviderServices extends Service {
    /**
     * 
     * @return all available provider definitions
     * @throws ViewProviderException
     */
    public List<ViewProviderDef> getAvailableProviders() throws ViewProviderException;

    /**
     * Gets services specific for provider kind
     * 
     * @param kind
     * @param type
     * @return kind specific services
     */
    public <T extends StatelessViewProviderServices> T getViewProviderSpecificKindServices(ViewProviderKind kind, Class<T> type);

    /**
     * Gets {@link StatelessListViewProviderServices} with
     * {@link ViewProviderInitResponse}
     * 
     * @param kind
     * @param type
     * @param def
     * @param viewProviderInitData
     * @return StatelessProviderServiceWithInitResponse
     * @throws ViewProviderException
     */
    public <T extends StatelessViewProviderServices> StatelessProviderServiceWithInitResponse<T> getViewProviderSpecificKindServicesWithInitResponse(ViewProviderKind kind, Class<T> type,
            ViewProviderDef def, ViewProviderInitData viewProviderInitData) throws ViewProviderException;
}
