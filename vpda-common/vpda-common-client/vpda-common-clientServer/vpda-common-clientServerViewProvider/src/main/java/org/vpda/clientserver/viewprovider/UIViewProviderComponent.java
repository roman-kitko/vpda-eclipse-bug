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
package org.vpda.clientserver.viewprovider;

import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.internal.common.util.Assert;

/**
 * This component represents whole ui rendered on client side but defined on
 * server. We can compose complex ui providers from 'child' providers.
 * 
 * @author kitko
 *
 */
public final class UIViewProviderComponent extends AbstractComponent<ViewProviderOpenInfo, ViewProviderLocValue> {

    private ViewProviderOpenInfo openInfo;
    private ComponentsGroupsDef updatedCompsDef;

    /** */
    public UIViewProviderComponent() {
    }

    /**
     * Creates component with id and openInfo
     * 
     * @param localId
     * @param openInfo
     */
    public UIViewProviderComponent(String localId, ViewProviderOpenInfo openInfo) {
        super(localId);
        this.openInfo = Assert.isNotNullArgument(openInfo, "openInfo");
    }

    private static final long serialVersionUID = -2931887571588668154L;

    @Override
    protected void adjustFromLocValue(ViewProviderLocValue locValue) {
    }

    @Override
    protected ViewProviderLocValue createLocValue() {
        return null;
    }

    @Override
    public Class<ViewProviderLocValue> getLocValueClass() {
        return ViewProviderLocValue.class;
    }

    /**
     * @return openInfo
     */
    public ViewProviderOpenInfo getOpenInfo() {
        return openInfo;
    }

    /**
     * @return the updatedCompsDef
     */
    public ComponentsGroupsDef getUpdatedCompsDef() {
        return updatedCompsDef;
    }

    /**
     * @param updatedCompsDef the updatedCompsDef to set
     */
    public void setUpdatedCompsDef(ComponentsGroupsDef updatedCompsDef) {
        this.updatedCompsDef = updatedCompsDef;
    }

}
