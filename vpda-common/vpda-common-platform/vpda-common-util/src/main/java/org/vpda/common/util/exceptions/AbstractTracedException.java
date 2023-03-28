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
 * Abstract class for traced exception
 * 
 * @author kitko
 *
 */
public abstract class AbstractTracedException extends Exception implements TracedException {
    private static final long serialVersionUID = 8232454521170583934L;
    private boolean isLogged;
    private boolean isUserHandled;
    private boolean doNotLog;

    @Override
    public boolean isLogged() {
        return isLogged;
    }

    @Override
    public boolean isUserHandled() {
        return isUserHandled;
    }

    @Override
    public void setLogged(boolean b) {
        this.isLogged = b;
    }

    @Override
    public void setUserHandled(boolean b) {
        this.isUserHandled = b;
    }

    /**
     * @param msg
     */
    public AbstractTracedException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param cause
     */
    public AbstractTracedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * @param cause
     */
    public AbstractTracedException(Throwable cause) {
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
