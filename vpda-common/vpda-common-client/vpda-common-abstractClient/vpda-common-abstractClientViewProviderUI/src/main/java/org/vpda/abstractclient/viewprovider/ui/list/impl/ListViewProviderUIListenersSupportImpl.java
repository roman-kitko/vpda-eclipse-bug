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
/**
 * 
 */
package org.vpda.abstractclient.viewprovider.ui.list.impl;

import java.io.Serializable;
import java.util.List;

import org.vpda.abstractclient.viewprovider.ui.impl.ViewProviderUIListenersSupportImpl;
import org.vpda.abstractclient.viewprovider.ui.list.ListSelectionListener;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderUIListenersSupport;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderUIListenersSupportWithFire;

/**
 * Default implementation of {@link ListViewProviderUIListenersSupport}
 * 
 * @author rki
 *
 */
public final class ListViewProviderUIListenersSupportImpl extends ViewProviderUIListenersSupportImpl implements ListViewProviderUIListenersSupportWithFire, Serializable {
    private static final long serialVersionUID = -1584864554296886797L;

    /**
     * Creates ListViewProviderUIListenersSupportImpl
     * 
     * @param viewProviderUI
     */
    public ListViewProviderUIListenersSupportImpl(ListViewProviderUI viewProviderUI) {
        super(viewProviderUI);
    }

    @Override
    public void addListViewSelectionListener(ListSelectionListener listener) {
        addListener(ListSelectionListener.class, listener);
    }

    @Override
    public void fireSelectionChanged(int[] selectedRows) {
        List<ListSelectionListener> listeners = getListeners(ListSelectionListener.class);
        for (ListSelectionListener listener : listeners) {
            listener.selectionChanged(getProviderUI(), selectedRows);
        }
    }

    @Override
    public ListViewProviderUI getProviderUI() {
        return (ListViewProviderUI) viewProviderUI;
    }

}
