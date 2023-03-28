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
package org.vpda.abstractclient.viewprovider.ui.impl;

import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.abstractclient.core.ui.dummy.DummyFrame;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIManager;
import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.preferences.AbstractViewProviderPreferences;
import org.vpda.common.comps.CurrentData;
import org.vpda.common.comps.autocomplete.AutoCompleteDataItem;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.UIComponentAccessor;
import org.vpda.common.comps.ui.resolved.ResolvingUIBasedComponent;

/**
 * Dummy view provider ui
 * 
 * @author kitko
 *
 */
public class DummyViewProviderUI extends AbstractViewProviderUI {
    private static final long serialVersionUID = 1L;

    /**
     * @param viewProviderUIFactory
     * @param openInfo
     * @param provider
     * @param initResponse
     */
    public DummyViewProviderUI(ViewProviderUIManager viewProviderUIFactory, ViewProviderOpenInfoDU openInfo, ViewProvider provider, ViewProviderInitResponse initResponse) {
        super(viewProviderUIFactory, openInfo, provider, initResponse);
    }

    @Override
    public Frame buildUI() {
        return new DummyFrame();
    }

    @Override
    public Object getMainComponent() {
        return new Object();
    }

    @Override
    public void initData() {
    }

    @Override
    public void applyViewProviderSettings(ViewProviderSettings viewProviderSettings) {
    }

    @Override
    public ViewProviderSettings getViewProviderSettings() {
        return null;
    }

    @Override
    public CurrentData getCurrentData() {
        return null;
    }

    @Override
    public void embeddChildUI(ViewProviderUI childUI) {
    }

    @Override
    public void removeEmbeddedChildUI(ViewProviderUI childUI) {
    }

    @Override
    public void rebuildUI(ContainerAC rootContainer) {
    }

    @Override
    public void refreshData() {
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
    public void flipChildrenOrientation() {
    }

    @Override
    protected AbstractViewProviderPreferences buildTemporalPreferences(ViewProviderSettings initSettings) {
        return null;
    }

    @Override
    public UIComponentAccessor<ListViewProviderInitResponse, ? extends Object> createListPreferencessAccessor(
            ResolvingUIBasedComponent<ListViewProviderInitResponse, UIComponentAccessor<ListViewProviderInitResponse, Object>> comp) {
        return null;
    }

}
