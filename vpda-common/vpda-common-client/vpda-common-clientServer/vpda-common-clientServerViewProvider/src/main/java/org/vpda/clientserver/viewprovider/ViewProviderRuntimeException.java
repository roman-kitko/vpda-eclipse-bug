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
 * Runtime exception regarding View providers work flow
 * 
 * @author kitko
 *
 */
public class ViewProviderRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8325919336508431044L;
    private ViewProviderDef providerDef;

    /**
     * @param providerDef
     * @param msg
     */
    public ViewProviderRuntimeException(ViewProviderDef providerDef, String msg) {
        super(msg);
        this.providerDef = providerDef;
    }

    /**
     * @param providerDef
     * @param throwable
     */
    public ViewProviderRuntimeException(ViewProviderDef providerDef, Throwable throwable) {
        super(throwable);
        this.providerDef = providerDef;
    }

    /**
     * @param providerDef
     * @param msg
     * @param throwable
     */
    public ViewProviderRuntimeException(ViewProviderDef providerDef, String msg, Throwable throwable) {
        super(msg, throwable);
        this.providerDef = providerDef;
    }

    /**
     * @return the providerDef
     */
    public ViewProviderDef getProviderDef() {
        return providerDef;
    }

}
