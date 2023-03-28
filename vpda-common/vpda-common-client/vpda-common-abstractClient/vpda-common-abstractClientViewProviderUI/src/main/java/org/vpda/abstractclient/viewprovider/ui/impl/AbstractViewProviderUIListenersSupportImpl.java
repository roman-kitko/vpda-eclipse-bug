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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIListener;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIListenersSupport;
import org.vpda.internal.common.util.Assert;

/**
 * General abstract implementation for {@link ViewProviderUIListenersSupport}
 * 
 * @author kitko
 *
 */
public abstract class AbstractViewProviderUIListenersSupportImpl implements ViewProviderUIListenersSupport, Serializable {
    private static final long serialVersionUID = 367297920228538946L;
    private Map<Class<? extends ViewProviderUIListener>, List<ViewProviderUIListener>> listeners;
    private static final List<? extends ViewProviderUIListener> EMPTY = Collections.emptyList();
    /** Associated provider ui */
    protected final ViewProviderUI viewProviderUI;

    /**
     * Creates new ViewProviderUIListenersSupportImpl
     * 
     * @param viewProviderUI
     */
    protected AbstractViewProviderUIListenersSupportImpl(ViewProviderUI viewProviderUI) {
        this.viewProviderUI = Assert.isNotNullArgument(viewProviderUI, "viewProviderUI");
        listeners = new HashMap<Class<? extends ViewProviderUIListener>, List<ViewProviderUIListener>>(1);
    }

    /**
     * Add listener by class
     * 
     * @param clazz
     * @param listener
     */
    protected synchronized void addListener(Class<? extends ViewProviderUIListener> clazz, ViewProviderUIListener listener) {
        List<ViewProviderUIListener> list = listeners.get(clazz);
        if (list == null) {
            list = new ArrayList<ViewProviderUIListener>(2);
            listeners.put(clazz, list);
        }
        if (!list.contains(listener)) {
            list.add(listener);
            viewProviderUI.notifyListenersChanged();
        }
    }

    @Override
    public synchronized List<? extends ViewProviderUIListener> getAllListeners() {
        List<ViewProviderUIListener> resList = new ArrayList<ViewProviderUIListener>();
        for (Class<? extends ViewProviderUIListener> clazz : listeners.keySet()) {
            List<? extends ViewProviderUIListener> list = listeners.get(clazz);
            if (list != null) {
                resList.addAll(list);
            }
        }
        return resList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewProviderUIListener> List<T> getListeners(Class<T> clazz) {
        List<T> l = (List<T>) listeners.get(clazz);
        return (List<T>) (l != null ? new ArrayList<T>(l) : EMPTY);
    }

    @Override
    public synchronized void removeListener(Class<? extends ViewProviderUIListener> clazz, ViewProviderUIListener listener) {
        List<? extends ViewProviderUIListener> list = listeners.get(clazz);
        if (list != null) {
            if (list.contains(listener)) {
                list.remove(listener);
                viewProviderUI.notifyListenersChanged();
            }
        }
    }

    @Override
    public ViewProviderUI getProviderUI() {
        return viewProviderUI;
    }

}
