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

import org.vpda.internal.common.util.Assert;

/**
 * Initial data for {@link ViewProvider}
 * 
 * @author rkitko
 * 
 */
public final class ViewProviderInitData implements java.io.Serializable {
    private static final long serialVersionUID = 9156437176681460503L;

    private final ViewProviderContext context;
    private final ViewProviderOperation operation;
    private final ViewProviderUIInfo uiInfo;
    private final ViewProviderInitProperties initProperties;

    /**
     * Creates ViewProviderInitData
     * 
     * @param context
     * @param operation
     * @param uiInfo
     * @param initProperties
     */
    public ViewProviderInitData(ViewProviderContext context, ViewProviderOperation operation, ViewProviderUIInfo uiInfo, ViewProviderInitProperties initProperties) {
        super();
        this.context = context;
        this.operation = Assert.isNotNullArgument(operation, "operation");
        this.uiInfo = Assert.isNotNullArgument(uiInfo, "uiInfo");
        this.initProperties = Assert.isNotNullArgument(initProperties, "initProperties");
    }

    /**
     * @return the context
     */
    public ViewProviderContext getContext() {
        return context;
    }

    /**
     * @return the operation
     */
    public ViewProviderOperation getOperation() {
        return operation;
    }

    /**
     * @return ui info
     */
    public ViewProviderUIInfo getUiInfo() {
        return uiInfo;
    }

    /**
     * @return init properties
     */
    public ViewProviderInitProperties getInitProperties() {
        return initProperties;
    }

    @Override
    public String toString() {
        return "ViewProviderInitData [context=" + context + ", operation=" + operation + ", uiInfo=" + uiInfo + ", initProperties=" + initProperties + "]";
    }

}
