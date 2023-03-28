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

import org.vpda.abstractclient.core.profile.LoginProfileManager;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.comps.ui.UIComponentAccessor;
import org.vpda.common.service.localization.LocalizationService;

/**
 * Interface to client UI
 * 
 * @author kitko
 *
 */
public interface ClientUI {
    /**
     * 
     * @return Main Frame
     */
    public MainFrame getMainFrame();

    /**
     * 
     * @return LoginFrame
     */
    public LoginFrame getLoginFrame();

    /**
     * Shows Exception dialog with message
     * 
     * @param e
     * @param msg
     */
    public void showExceptionDialog(Throwable e, String msg);

    /**
     * Shows Message dialog
     * 
     * @param title
     * @param text
     */
    public void showMessageDialog(String title, String text);

    /**
     * Shows Message dialog within frame
     * 
     * @param parentFrame
     * @param title
     * @param text
     */
    public void showMessageDialog(Frame parentFrame, String title, String text);

    /**
     * Shows Exception dialog with message
     * 
     * @param parentFrame
     * @param e
     * @param msg
     */
    public void showExceptionDialog(Frame parentFrame, Throwable e, String msg);

    /**
     * 
     * @return WindowManager
     */
    public WindowManager getWindowManager();

    /**
     * Dispose UI
     */
    public void dispose();

    /**
     * Shows client settings frame
     * 
     * @return frame
     *
     */
    public Frame showSettingsFrame();

    /**
     * 
     * @param <T> - type of frame chooser
     * @return FrameChooser
     */
    public <T extends Frame> FrameChooser<T> getFrameChooser();

    /**
     * Executes command using passed executor This method will also handle thrown
     * exception
     * 
     * @param <T>
     * @param executor
     * @param command
     * @param env
     * @param event
     * @return execution result
     */
    public <T> ExecutionResult<T> executeCommand(CommandExecutor executor, Command<T> command, CommandExecutionEnv env, CommandEvent event);

    /**
     * Notify method that login was successful
     *
     */
    public void afterLogin();

    /**
     * Notify method after logout
     *
     */
    public void afterLogout();

    /**
     * 
     * @return client
     */
    public ClientWithUI getClient();

    /**
     * @return login profile manager
     */
    public LoginProfileManager getLoginProfileManager();

    /**
     * @return true if client on this ui can shutdown jvm
     */
    public boolean canShutdownJVM();

    /**
     * @return LocalizationService
     */
    public LocalizationService getLocService();

    /**
     * @return ui executor
     */
    public CommandExecutor getUIExecutor();

    /**
     * @return ui environment
     */
    public CommandExecutionEnv getUIExecutionEnvironment();

    /**
     * Before shutdown
     */
    public void beforeShutDown();

    /**
     * Will turn on/off full screen mode
     */
    public void toggleFullScreenMode();

    /**
     * Will create UI specific wrapper UI component of passed accesor
     * 
     * @param accesor
     * @return native ui component
     */
    public <V, U> UIComponentAccessor<V, U> createUIComponentAccesorWrapperUI(UIComponentAccessor<V, U> accesor);

}
