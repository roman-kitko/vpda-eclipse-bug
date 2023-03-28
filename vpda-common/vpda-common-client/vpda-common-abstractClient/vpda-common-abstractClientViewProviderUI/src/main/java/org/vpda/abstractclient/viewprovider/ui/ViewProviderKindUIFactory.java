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
/**
 * 
 */
package org.vpda.abstractclient.viewprovider.ui;

import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderKind;

/**
 * Factory for {@link ViewProviderUI} by ViewProviderKind. This factory is able
 * to create ViewProviderUI only by its kind
 * 
 * @author rkitko
 *
 */
public interface ViewProviderKindUIFactory {

    /**
     * Will create viewProviderUI
     * 
     * @param uiFactory
     * @param openInfo
     * @param provider
     * @param initResponse
     * @return created viewProviderUI
     */
    public ViewProviderUI createViewProviderUI(ViewProviderUIManager uiFactory, ViewProviderOpenInfoDU openInfo, ViewProvider provider, ViewProviderInitResponse initResponse);

    /**
     * 
     * @return kind of view providers this factory is able to create
     */
    public ViewProviderKind getViewProviderKind();

}
