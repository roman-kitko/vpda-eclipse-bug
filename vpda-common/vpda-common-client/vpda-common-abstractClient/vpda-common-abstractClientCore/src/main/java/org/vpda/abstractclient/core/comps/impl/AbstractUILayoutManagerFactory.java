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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vpda.abstractclient.core.comps.UILayoutManager;
import org.vpda.abstractclient.core.comps.UILayoutManagerFactory;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.common.comps.ui.ContainerLayout;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ClassUtil;

/**
 * This is abstract impl of ViewProviderUILayoutManagerFactory which registers
 * ViewProviderUILayoutManager
 * 
 * @author kitko
 *
 */
public abstract class AbstractUILayoutManagerFactory implements UILayoutManagerFactory, Serializable {
    private static final long serialVersionUID = -1291001515426691698L;
    private final Map<Class<? extends ContainerLayout>, UILayoutManager> managers;
    /** Reference to client ui */
    protected final ClientUI clientUI;

    /**
     * Creates AbstractViewProviderUILayoutManagerFactory with clientUI
     * 
     * @param clientUI
     */
    protected AbstractUILayoutManagerFactory(ClientUI clientUI) {
        this.clientUI = Assert.isNotNullArgument(clientUI, "clientUI");
        managers = new HashMap<Class<? extends ContainerLayout>, UILayoutManager>(3);
        registerLayoutManagers();
    }

    @Override
    public UILayoutManager getViewProviderUILayoutManager(ContainerLayout layout) {
        Class<? extends ContainerLayout> layoutClass = layout.getClass();
        UILayoutManager manager = managers.get(layoutClass);
        if (manager != null) {
            return manager;
        }
        for (Class iface : ClassUtil.getInterfacesHierarchy(layoutClass)) {
            if (ContainerLayout.class.isAssignableFrom(iface)) {
                manager = managers.get(iface);
                if (manager != null) {
                    return manager;
                }
            }
        }
        return null;
    }

    /**
     * Here register layout managers
     */
    protected abstract void registerLayoutManagers();

    @Override
    public void registerLayoutManager(UILayoutManager manager) {
        managers.put(manager.getContainerLayoutClass(), manager);
    }

    @Override
    public Collection<Class<? extends ContainerLayout>> getLayoutClasses() {
        return Collections.unmodifiableSet(managers.keySet());
    }

    @Override
    public UILayoutManager getMandatoryViewProviderUILayoutManagerFactory(ContainerLayout layout) {
        UILayoutManager manager = getViewProviderUILayoutManager(layout);
        if (manager == null) {
            throw new VPDARuntimeException("No layout manager registered for layout " + layout);
        }
        return manager;
    }

}
