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
 * Abstract class for traced runtime exceptions
 * 
 * @author kitko
 *
 */
public abstract class AbstractTracedRuntimeException extends RuntimeException implements TracedException {
    private static final long serialVersionUID = 9107037815505020576L;
    private boolean logged;
    private boolean handled;
    private boolean doNotLog;

    @Override
    public boolean isLogged() {
        return logged;
    }

    @Override
    public void setLogged(boolean b) {
        this.logged = b;

    }

    @Override
    public boolean isUserHandled() {
        return handled;
    }

    @Override
    public void setUserHandled(boolean b) {
        this.handled = b;
    }

    /**
     * @param message
     * @param cause
     */
    protected AbstractTracedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    protected AbstractTracedRuntimeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    protected AbstractTracedRuntimeException(Throwable cause) {
        super(cause);
    }

    @Override
    public boolean shouldLog() {
        return !doNotLog;
    }

    @Override
    public void doNotLog() {
        this.doNotLog = true;
    }

}
