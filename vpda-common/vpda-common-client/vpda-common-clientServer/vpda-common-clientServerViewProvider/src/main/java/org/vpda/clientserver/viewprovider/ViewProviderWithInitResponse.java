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

import java.io.Serializable;

/**
 * Creation and init of view provider
 * 
 * @author kitko
 * @param <T>
 *
 */
public final class ViewProviderWithInitResponse<T extends ViewProvider> implements Serializable {
    private static final long serialVersionUID = -6803451014183029854L;
    private final Class<T> type;
    private final ViewProviderInitResponse initResponse;
    private final T viewProvider;

    /**
     * @param type
     * @param initResponse
     * @param viewProvider
     */
    public ViewProviderWithInitResponse(Class<T> type, ViewProviderInitResponse initResponse, T viewProvider) {
        this.type = type;
        this.initResponse = initResponse;
        this.viewProvider = viewProvider;
    }

    /**
     * @return the initResponse
     */
    public ViewProviderInitResponse getInitResponse() {
        return initResponse;
    }

    /**
     * @return the viewProvider
     */
    public T getViewProvider() {
        return viewProvider;
    }

    /**
     * @return the type
     */
    public Class<T> getType() {
        return type;
    }

}
