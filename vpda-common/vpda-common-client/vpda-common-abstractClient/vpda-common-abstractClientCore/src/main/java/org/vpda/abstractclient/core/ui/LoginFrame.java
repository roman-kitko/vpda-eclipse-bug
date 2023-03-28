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

import java.util.Locale;

import org.vpda.clientserver.clientcommunication.ClientLoginInfo;
import org.vpda.clientserver.communication.data.FullApplicableContexts;
import org.vpda.common.context.TenementalContext;

/**
 * Abstraction of client login frame dialog
 * 
 * @author kitko
 *
 */
public interface LoginFrame extends ProgressableFrame {

    /**
     * Show dialog chooser for contexts user can choose from
     * 
     * @param loginInfo
     * @param contexts
     */
    public void showContextChooserDialog(ClientLoginInfo loginInfo, FullApplicableContexts contexts);

    /**
     * Show dialog with exception
     * 
     * @param e
     */
    public void showExceptionDialog(Exception e);

    /**
     * 
     * @return main window platform component
     */
    public Object getMainWindow();

    /**
     * 
     * @return root pane platform component
     */
    public Object getRootPane();

    /**
     * Make login request to server
     * 
     * @param loginInfo
     * @param context
     */
    public void startLoginWorkFlow(ClientLoginInfo loginInfo, TenementalContext context);

    /**
     * Make get Contexts request
     * 
     * @return ClientLoginInfo
     */
    public ClientLoginInfo startConnectWorkFlow();

    /**
     * @return current locale
     */
    public Locale getLocale();
}
