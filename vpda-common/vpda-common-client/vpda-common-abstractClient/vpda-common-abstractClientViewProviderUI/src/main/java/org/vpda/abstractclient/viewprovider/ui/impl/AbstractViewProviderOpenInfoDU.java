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
package org.vpda.abstractclient.viewprovider.ui.impl;

import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfo;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.clientserver.viewprovider.AbstractViewProviderOpenInfo;

/**
 * Open info with {@link DefaultViewProviderUIInfo} as user interface
 * information
 * 
 * @author kitko
 *
 */
public abstract class AbstractViewProviderOpenInfoDU extends AbstractViewProviderOpenInfo implements ViewProviderOpenInfoDU {

    private static final long serialVersionUID = 4400980248241442745L;

    @Override
    public DefaultViewProviderUIInfo getViewProviderUIInfo() {
        return (DefaultViewProviderUIInfo) uiInfo;
    }

    /**
     * Creates AbstractViewProviderOpenInfoDU using builder
     * 
     * @param builder
     */
    protected AbstractViewProviderOpenInfoDU(AbstractViewProviderOpenInfo.Builder builder) {
        super(builder);
    }

}
