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
package org.vpda.common.util.exceptions;

/**
 * Configuration exception - thrown when there is any config problem, typically
 * it will be thrown during initalization
 * 
 * @author kitko
 */
public class VPDAConfigurationException extends AbstractTracedException {

    /**
     * 
     */
    private static final long serialVersionUID = -2077548717385211650L;

    /**
     * @param message
     * @param cause
     */
    public VPDAConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public VPDAConfigurationException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public VPDAConfigurationException(Throwable cause) {
        super(cause);
    }
}
