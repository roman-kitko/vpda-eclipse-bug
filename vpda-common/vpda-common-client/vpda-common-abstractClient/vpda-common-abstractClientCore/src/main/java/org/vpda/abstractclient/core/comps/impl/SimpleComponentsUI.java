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
package org.vpda.abstractclient.core.comps.impl;

import org.vpda.abstractclient.core.comps.ComponentUIFactory;
import org.vpda.abstractclient.core.comps.ComponentsManager;
import org.vpda.abstractclient.core.comps.ComponentsUI;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.comps.autocomplete.AutoCompleteDataItem;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.UIComponentAccessor;

/**
 * 
 * @author kitko
 *
 */
public final class SimpleComponentsUI implements ComponentsUI {
    private final ClientUI clientUI;
    private final ComponentUIFactory viewProviderComponentsUIFactory;

    /**
     * @param clientUI
     * @param viewProviderComponentsUIFactory
     */
    public SimpleComponentsUI(ClientUI clientUI, ComponentUIFactory viewProviderComponentsUIFactory) {
        this.clientUI = clientUI;
        this.viewProviderComponentsUIFactory = viewProviderComponentsUIFactory;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void fireFetch(String id) {
    }

    @Override
    public void completeFetch(String id) {
    }

    @Override
    public void completeAutoComplete(String compId, AutoCompleteDataItem item) {
    }

    @Override
    public ClientUI getClientUI() {
        return clientUI;
    }

    @Override
    public CommandExecutionEnv getCommandExecutionEnv() {
        return clientUI.getUIExecutionEnvironment();
    }

    @Override
    public ComponentsManager getComponentsManager() {
        return new ComponentsManagerImpl(this, viewProviderComponentsUIFactory);
    }

    @Override
    public Object getID() {
        return "componentsUI";
    }

    @Override
    public Object getMainComponent() {
        return null;
    }

    @Override
    public void handleException(Exception e1, String string) {
    }

    @Override
    public void rebuildUI(ContainerAC rootContainer) {
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return clientUI.getUIExecutor();
    }

    @Override
    public <V, U> UIComponentAccessor<V, U> createUIComponentAccesorWrapperUI(UIComponentAccessor<V, U> accesor) {
        return clientUI.createUIComponentAccesorWrapperUI(accesor);
    }

}
