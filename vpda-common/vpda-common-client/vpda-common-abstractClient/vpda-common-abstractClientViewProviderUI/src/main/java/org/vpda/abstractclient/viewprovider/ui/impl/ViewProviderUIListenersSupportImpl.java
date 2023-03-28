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
package org.vpda.abstractclient.viewprovider.ui.impl;

import java.util.List;

import org.vpda.abstractclient.viewprovider.ui.DisposeListener;
import org.vpda.abstractclient.viewprovider.ui.InitListener;
import org.vpda.abstractclient.viewprovider.ui.UIStructureListener;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderLayoutListener;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIListenersSupport;

/**
 * General implementation for {@link ViewProviderUIListenersSupport}
 * 
 * @author kitko
 *
 */
public class ViewProviderUIListenersSupportImpl extends AbstractViewProviderUIListenersSupportImpl implements ViewProviderUIListenersSupportWithFired {
    private static final long serialVersionUID = -2151305599517659288L;

    /**
     * Creates new ViewProviderUIListenersSupportImpl
     * 
     * @param viewProviderUI
     */
    public ViewProviderUIListenersSupportImpl(ViewProviderUI viewProviderUI) {
        super(viewProviderUI);
    }

    @Override
    public void addInitListener(InitListener afterInitListener) {
        addListener(InitListener.class, afterInitListener);
    }

    @Override
    public void addDisposeListener(DisposeListener disposeListener) {
        addListener(DisposeListener.class, disposeListener);
    }

    @Override
    public void addUIStructureListener(UIStructureListener listener) {
        addListener(UIStructureListener.class, listener);
    }

    /**
     * Fires after init event
     */
    @Override
    public void fireAfterInit() {
        List<InitListener> listeners = getListeners(InitListener.class);
        for (InitListener listener : listeners) {
            listener.afterUiInit(getProviderUI());
        }
    }

    /**
     * Fires before dispose event
     */
    @Override
    public void fireBeforeDispose() {
        List<DisposeListener> listeners = getListeners(DisposeListener.class);
        for (DisposeListener listener : listeners) {
            listener.beforeUiDisposed(getProviderUI());
        }
    }

    /**
     * Fires after dispose event
     */
    @Override
    public void fireAfterDispose() {
        List<DisposeListener> listeners = getListeners(DisposeListener.class);
        for (DisposeListener listener : listeners) {
            listener.afterUiDisposed(getProviderUI());
        }
    }

    @Override
    public void fireChildAdded(ViewProviderUI child) {
        List<UIStructureListener> listeners = getListeners(UIStructureListener.class);
        for (UIStructureListener listener : listeners) {
            listener.childAdded(getProviderUI(), child);
        }
    }

    @Override
    public void fireChildRemoved(ViewProviderUI child) {
        List<UIStructureListener> listeners = getListeners(UIStructureListener.class);
        for (UIStructureListener listener : listeners) {
            listener.childRemoved(getProviderUI(), child);
        }
    }

    @Override
    public void fireLayoutChanged() {
        List<ViewProviderLayoutListener> listeners = getListeners(ViewProviderLayoutListener.class);
        for (ViewProviderLayoutListener listener : listeners) {
            listener.layoutChanged(getProviderUI());
        }
    }

    @Override
    public void addLayoutListener(ViewProviderLayoutListener listener) {
        addListener(ViewProviderLayoutListener.class, listener);
    }

}
