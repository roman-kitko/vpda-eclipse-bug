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
package org.vpda.abstractclient.viewprovider.ui;

import org.vpda.abstractclient.core.comps.ComponentsUI;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.core.ui.ClientWithUI;
import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.abstractclient.core.ui.FramePresenter;
import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderContext;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInfo;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.preferences.AbstractViewProviderPreferences;
import org.vpda.clientserver.viewprovider.preferences.ListViewProviderPreferences;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutorAccessor;
import org.vpda.common.command.CommandExecutorWithRegistry;
import org.vpda.common.comps.CurrentData;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.UIComponentAccessor;
import org.vpda.common.comps.ui.resolved.ResolvingUIBasedComponent;
import org.vpda.common.util.Initializee;

/**
 * ViewProviderUI is client side of Provider. This is abstraction of UI
 * representation of provider
 * 
 * @author rki
 *
 */
public interface ViewProviderUI extends Initializee, CommandExecutorAccessor, FramePresenter, ComponentsUI {
    /**
     * Returns frame for this UI
     * 
     * @return Frame
     */
    public Frame getFrame();

    /**
     * 
     * @return Main Component for concrete UI like JPanel....
     */
    @Override
    public Object getMainComponent();

    /**
     * dispone UI
     *
     */
    @Override
    public void dispose();

    /**
     * @return true if disposed
     */
    public boolean isDisposed();

    /**
     * 
     * @return id for provider
     */
    public ViewProviderID getViewProviderID();

    /**
     * 
     * @return caller context for this View UI
     */
    public ViewProviderContext getCallerProviderContext();

    /**
     * Get current context, this means caller context plus current data
     * 
     * @return current context for this View UI
     */
    public ViewProviderContext getCurrentProviderContext();

    /**
     * 
     * @return info for UI
     */
    public ViewProviderInfo getViewProviderInfo();

    /**
     * Init data from server. Explicit call here
     */
    public void initData();

    /**
     * Will start initial data flow
     */
    public void startInitFlow();

    /**
     * Update current settings for providerUI This means client will update its ui,
     * probably sends updated ui to server and read new data from server. It will
     * also set new settings in current preferences
     * 
     * @param viewProviderSettings
     */
    public void applyViewProviderSettings(ViewProviderSettings viewProviderSettings);

    /**
     * Sets new preferences
     * 
     * @param preferences
     */
    public void applyViewProviderPreferences(AbstractViewProviderPreferences preferences);

    /**
     * @return current View ProviderSettings
     */
    public ViewProviderSettings getViewProviderSettings();

    /**
     * @return preferences
     */
    public AbstractViewProviderPreferences getViewProviderPreferences();

    /**
     * Gets viewProvider for this UI. This method should be used only in cases we
     * need some special info from viewProvider this UI does not expose
     * 
     * @return viewProvider for this UI
     */
    public ViewProvider getViewProvider();

    /**
     * 
     * @return CommandExecutionEnv
     */
    @Override
    public CommandExecutionEnv getCommandExecutionEnv();

    /**
     * 
     * @return client ui
     */
    @Override
    public ClientUI getClientUI();

    /**
     * @return client
     */
    public ClientWithUI getClient();

    /**
     * ViewProviderUIFactory
     * 
     * @return ViewProviderUIManager
     */
    public ViewProviderUIManager getViewProviderUIManager();

    /**
     * Notify that new child was opened
     * 
     * @param viewProviderUI
     */
    public void addChild(ViewProviderUI viewProviderUI);

    /**
     * @return children count
     */
    public int getChildrenCount();

    /**
     * 
     * @return current selected data
     */
    public abstract CurrentData getCurrentData();

    /**
     * Do embedding of child to our ui
     * 
     * @param childUI
     */
    public void embeddChildUI(ViewProviderUI childUI);

    /**
     * Remove child embedded in parrent ui
     * 
     * @param childUI
     */
    public void removeEmbeddedChildUI(ViewProviderUI childUI);

    /**
     * Change how children are added, either vertically or horizontally
     */
    public void flipChildrenOrientation();

    /**
     * @return event/listener support
     */
    public ViewProviderUIListenersSupport getListenersSupport();

    /**
     * Notify method that there was some change (add/remove) in listeners support
     */
    public void notifyListenersChanged();

    /**
     * Rebuilds main container and all its childs
     * 
     * @param rootContainer
     */
    @Override
    public void rebuildUI(ContainerAC rootContainer);

    /**
     * Refresh current data
     */
    public void refreshData();

    /**
     * @return resquestCommand executor
     */
    public CommandExecutorWithRegistry getRequestCommandsExecutor();

    /**
     * @return resolved title
     */
    public String resolveTitle();

    /**
     * Will show exception dialog with context of provider ui
     * 
     * @param e
     * @param msg
     */
    public void showExceptionDialog(Throwable e, String msg);

    /**
     * Will show Message dialog with context of provider ui
     * 
     * @param title
     * @param text
     */
    public void showMessageDialog(String title, String text);

    /**
     * @param comp
     * @return new preferences accessor component for ui platform that will provide
     *         view/editing option for {@link ListViewProviderPreferences}
     */
    public UIComponentAccessor<ListViewProviderInitResponse, ? extends Object> createListPreferencessAccessor(
            ResolvingUIBasedComponent<ListViewProviderInitResponse, UIComponentAccessor<ListViewProviderInitResponse, Object>> comp);

    public default void notifyFrameSized() {
    };

}
