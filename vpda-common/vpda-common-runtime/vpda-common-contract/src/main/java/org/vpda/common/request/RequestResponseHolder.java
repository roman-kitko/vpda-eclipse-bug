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
package org.vpda.common.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Will hold data for one request */
public final class RequestResponseHolder {
    private RequestResponseHolder() {
    }

    private static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<>();

    /**
     * Set current http servlet request
     * 
     * @param request
     * @param response
     */
    public static void setCurrentHttpServletRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        currentRequest.set(request);
        currentResponse.set(response);
    }

    /**
     * Clears current request
     */
    public static void releaseCurrentHttpServletRequestAndResponse() {
        currentRequest.remove();
        currentResponse.remove();
    }

    /**
     * @return current servlet request
     */
    public static HttpServletRequest getCurrentHttpServletRequest() {
        return currentRequest.get();
    }

    /**
     * 
     * @return current servlet response
     */
    public static HttpServletResponse getCurrentHttpServletResponse() {
        return currentResponse.get();
    }

}
