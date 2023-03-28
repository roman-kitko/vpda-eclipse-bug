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
package org.vpda.abstractclient.core.comps;

import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutorAccessor;
import org.vpda.common.comps.autocomplete.AutoCompleteDataItem;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.UIComponentAccessor;

/** Abstract wrapping ui */
public interface ComponentsUI extends CommandExecutorAccessor {

    /**
     * @return id of components container
     */
    public Object getID();

    /**
     * 
     * @return client ui
     */
    public ClientUI getClientUI();

    /**
     * 
     * @return CommandExecutionEnv
     */
    public CommandExecutionEnv getCommandExecutionEnv();

    /**
     * 
     * @return main ui component
     */
    public Object getMainComponent();

    /**
     * dispone UI
     *
     */
    public void dispose();

    /**
     * Rebuild ui
     * 
     * @param rootContainer
     */
    public void rebuildUI(ContainerAC rootContainer);

    /**
     * Handle the exception
     * 
     * @param e1
     * @param string
     */
    public void handleException(Exception e1, String string);

    /**
     * @return manager of components
     */
    public ComponentsManager getComponentsManager();

    /**
     * Will fire fetch action
     * 
     * @param id
     */
    public void fireFetch(String id);

    /**
     * Will complete data by fetch
     * 
     * @param id Id of field
     */
    public void completeFetch(String id);

    /**
     * Will complete data by autocomplete
     * 
     * @param compId         Id of field
     * @param completionData
     */
    public void completeAutoComplete(String compId, AutoCompleteDataItem completionData);

    /**
     * Will create UI specific wrapper UI component of passed accesor
     * 
     * @param accesor
     * @return native ui component
     */
    public <V, U> UIComponentAccessor<V, U> createUIComponentAccesorWrapperUI(UIComponentAccessor<V, U> accesor);

}
