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
package org.vpda.clientserver.communication.services;

/**
 * Exception thrown at login process when not valid user/password is provided
 * 
 * @author kitko
 *
 */
public class InvalidCredentialsException extends LoginException {
    private static final long serialVersionUID = -18419669063598563L;

    /**
     * @param message
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

}
