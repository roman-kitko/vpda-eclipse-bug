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
package org.vpda.abstractclient.core.ui.dummy;

import org.vpda.abstractclient.core.profile.LoginProfileManager;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.core.ui.ClientWithUI;
import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.abstractclient.core.ui.FrameChooser;
import org.vpda.abstractclient.core.ui.LoginFrame;
import org.vpda.abstractclient.core.ui.MainFrame;
import org.vpda.abstractclient.core.ui.WindowManager;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.command.env.EmptyCommandExecutionEnv;
import org.vpda.common.comps.ui.UIComponentAccessor;
import org.vpda.common.service.localization.LocalizationService;

/**
 * @author kitko
 *
 */
public class DummyClientUI implements ClientUI {
    private MainFrame mainFrame;
    private LoginFrame loginFrame;
    private WindowManager windowManager;
    private FrameChooser<Frame> frameChooser;
    private ClientWithUI client;

    /**
     * @param client
     * 
     */
    public DummyClientUI(ClientWithUI client) {
        loginFrame = new DummyLoginFrame();
        mainFrame = new DummyMainFrame();
        windowManager = new DummyWindowManager();
        frameChooser = new DummyFrameChooser();
        this.client = client;
    }

    @Override
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    @Override
    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    @Override
    public WindowManager getWindowManager() {
        return windowManager;
    }

    @Override
    public void dispose() {
        mainFrame = null;
        loginFrame = null;
    }

    @Override
    public Frame showSettingsFrame() {
        return null;
    }

    @Override
    public void showExceptionDialog(Throwable e, String msg) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Frame> FrameChooser<T> getFrameChooser() {
        return (FrameChooser<T>) frameChooser;
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(CommandExecutor executor, Command<T> command, CommandExecutionEnv env, CommandEvent event) {
        return null;
    }

    @Override
    public void afterLogin() {
    }

    @Override
    public void afterLogout() {
    }

    @Override
    public ClientWithUI getClient() {
        return client;
    }

    @Override
    public LoginProfileManager getLoginProfileManager() {
        return null;
    }

    @Override
    public boolean canShutdownJVM() {
        return false;
    }

    @Override
    public LocalizationService getLocService() {
        return null;
    }

    @Override
    public CommandExecutor getUIExecutor() {
        return null;
    }

    @Override
    public CommandExecutionEnv getUIExecutionEnvironment() {
        return EmptyCommandExecutionEnv.getInstance();
    }

    @Override
    public void showMessageDialog(String title, String text) {
    }

    @Override
    public void showMessageDialog(Frame parentFrame, String title, String text) {
    }

    @Override
    public void showExceptionDialog(Frame parentFrame, Throwable e, String msg) {
    }

    @Override
    public void beforeShutDown() {
    }

    @Override
    public void toggleFullScreenMode() {
    }

    @Override
    public <V, U> UIComponentAccessor<V, U> createUIComponentAccesorWrapperUI(UIComponentAccessor<V, U> accesor) {
        return null;
    }

}
