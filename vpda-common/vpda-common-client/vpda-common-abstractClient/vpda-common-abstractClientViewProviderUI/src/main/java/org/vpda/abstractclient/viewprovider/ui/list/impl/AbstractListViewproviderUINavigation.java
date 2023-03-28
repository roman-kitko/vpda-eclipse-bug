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
package org.vpda.abstractclient.viewprovider.ui.list.impl;

import java.io.Serializable;

import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderUIDataNavigation;
import org.vpda.clientserver.viewprovider.list.ReadingFrom;
import org.vpda.common.command.MethodCommandEvent;

/**
 * @author kitko
 *
 */
public abstract class AbstractListViewproviderUINavigation implements ListViewProviderUIDataNavigation, Serializable {
    private static final long serialVersionUID = -5493557951758755514L;
    private AbstractListViewProviderUI providerUI;

    @Override
    public void readFirstData() {
        providerUI.getClientUI().executeCommand(providerUI.getRequestCommandsExecutor(), providerUI.createReadFirstCmd(), providerUI.getCommandExecutionEnv(),
                new MethodCommandEvent(getClass(), "readFirstData"));
    }

    @Override
    public void readNextData() {
        providerUI.getClientUI().executeCommand(providerUI.getRequestCommandsExecutor(), providerUI.createReadNextCmd(), providerUI.getCommandExecutionEnv(),
                new MethodCommandEvent(getClass(), "readNextData"));
    }

    @Override
    public void readPrevData() {
        providerUI.getClientUI().executeCommand(providerUI.getRequestCommandsExecutor(), providerUI.createReadPrevCmd(), providerUI.getCommandExecutionEnv(),
                new MethodCommandEvent(getClass(), "readPrevData"));
    }

    @Override
    public void readLastData() {
        providerUI.getClientUI().executeCommand(providerUI.getRequestCommandsExecutor(), providerUI.createReadLastCmd(), providerUI.getCommandExecutionEnv(),
                new MethodCommandEvent(getClass(), "readLastData"));
    }

    @Override
    public void readData(int count, int offset, ReadingFrom readingFrom) {
        providerUI.getClientUI().executeCommand(providerUI.getRequestCommandsExecutor(), providerUI.createReadDataCmd(count, offset, readingFrom), providerUI.getCommandExecutionEnv(),
                new MethodCommandEvent(getClass(), "readData"));
    }

    /**
     * @param providerUI
     */
    public AbstractListViewproviderUINavigation(AbstractListViewProviderUI providerUI) {
        super();
        if (providerUI == null) {
            throw new IllegalArgumentException("providerUI is null");
        }
        this.providerUI = providerUI;
    }

}
