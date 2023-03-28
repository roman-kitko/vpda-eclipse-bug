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

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;

/**
 * {@link StatelessProviderServiceWithInitResponse} with init response
 * 
 * @author kitko
 *
 * @param <T>
 */
public final class StatelessProviderServiceWithInitResponse<T extends StatelessViewProviderServices> implements Serializable {
    private static final long serialVersionUID = -8704661133412384378L;
    private final Class<T> type;
    private final ViewProviderInitResponse initResponse;
    private final T service;

    /**
     * @param type
     * @param initResponse
     * @param service
     */
    public StatelessProviderServiceWithInitResponse(Class<T> type, ViewProviderInitResponse initResponse, T service) {
        this.type = type;
        this.initResponse = initResponse;
        this.service = service;
    }

    /**
     * @return the type
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * @return the initResponse
     */
    public ViewProviderInitResponse getInitResponse() {
        return initResponse;
    }

    /**
     * @return the service
     */
    public T getService() {
        return service;
    }

}
