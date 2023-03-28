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
package org.vpda.abstractclient.core.ui;

import java.io.Serializable;

import org.vpda.common.util.exceptions.DefaultLoggingExceptionHandler;
import org.vpda.common.util.exceptions.ExceptionHandler;

/**
 * UI based exception handler
 * 
 * @author kitko
 *
 */
public final class ClientUIExceptionHandler implements ExceptionHandler, Serializable {
    private static final long serialVersionUID = 4037548551989359842L;
    private final ClientUI ui;

    /**
     * Creates CLientUIExceptionHandler
     * 
     * @param ui
     */
    public ClientUIExceptionHandler(ClientUI ui) {
        super();
        this.ui = ui;
    }

    @Override
    public void handleException(Throwable e) {
        DefaultLoggingExceptionHandler.getInstance().handleException(e);
        ui.showExceptionDialog(e, "Default exception handling");

    }

}
