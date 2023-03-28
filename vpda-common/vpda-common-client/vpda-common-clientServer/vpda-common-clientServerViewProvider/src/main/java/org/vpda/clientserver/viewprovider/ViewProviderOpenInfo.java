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

/**
 * Provides information we need to open {@link ViewProvider}
 * 
 * @author kitko
 *
 */
public interface ViewProviderOpenInfo {

    /**
     * @return the viewProviderContext
     */
    public abstract ViewProviderContext getViewProviderContext();

    /**
     * @return the viewProviderDef
     */
    public abstract ViewProviderDef getViewProviderDef();

    /**
     * @return the initData
     */
    public abstract AdjustCurrentData getAdjustData();

    /**
     * @return the operation
     */
    public abstract ViewProviderOperation getOperation();

    /**
     * @return user interface information
     */
    public ViewProviderUIInfo getViewProviderUIInfo();

    /**
     * @return initial properties
     */
    public ViewProviderInitProperties getInitProperties();

    /**
     * Sets context
     * 
     * @param viewProviderContext
     */
    public abstract void setViewProviderContext(ViewProviderContext viewProviderContext);

}