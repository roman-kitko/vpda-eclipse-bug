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

import org.vpda.abstractclient.core.comps.ComponentUIFactory;
import org.vpda.abstractclient.core.comps.ComponentUISingleFactory;
import org.vpda.abstractclient.core.comps.ComponentsManager;
import org.vpda.abstractclient.core.comps.CustomComponentUISingleFactory;
import org.vpda.abstractclient.core.comps.UILayoutManagerFactory;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.common.comps.loc.AbstractCompLocValue;
import org.vpda.common.comps.ui.ACConstants;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.AbstractFieldAC;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.ClassUtil;

/**
 * Abstract factory that creates and updates concrete platform ui objects for
 * {@link AbstractComponent}
 * 
 * @author kitko
 *
 */
public abstract class AbstractComponentUIFactory implements ComponentUIFactory, Serializable {
    private static final long serialVersionUID = -7652493519860186003L;
    private Map<Class<? extends Component>, ComponentUISingleFactory> oneComponentfactoryMap;
    private UILayoutManagerFactory viewProviderUILayoutManagerFactory;
    /** Reference to client ui */
    protected ClientUI clientUI;

    private ComponentUISingleFactory fetchFactory;

    /**
     * Creates AbstractViewProviderComponentsUIFactory
     * 
     * @param clientUI
     */
    protected AbstractComponentUIFactory(ClientUI clientUI) {
        this.clientUI = Assert.isNotNullArgument(clientUI, "clientUI");
        oneComponentfactoryMap = new HashMap<Class<? extends Component>, ComponentUISingleFactory>();
        registerOneComponentFactories();
        viewProviderUILayoutManagerFactory = createViewProviderUILayoutManagerFactory();
    }

    @Override
    public <V, T extends AbstractCompLocValue, Z> Z createUIComponent(ComponentsManager uiCompManager, Component<V, T> comp) {
        ComponentUISingleFactory<Component<V, T>, Z> viewProviderOneComponentFactory = getOneComponentFactory(uiCompManager, comp);
        return viewProviderOneComponentFactory.createUIComponent(uiCompManager, comp);
    }

    @Override
    public <V, T extends AbstractCompLocValue, Z> void updateUIComponent(ComponentsManager uiCompManager, Component<V, T> comp, Z uiComp) {
        ComponentUISingleFactory<Component<V, T>, Z> viewProviderOneComponentFactory = getOneComponentFactory(uiCompManager, comp);
        viewProviderOneComponentFactory.updateUIComponent(uiCompManager, comp, uiComp);
    }

    @Override
    public <V, T extends AbstractCompLocValue, Z> void updateComponentFromUI(ComponentsManager uiCompManager, Component<V, T> comp, Z uiComp) {
        ComponentUISingleFactory<Component<V, T>, Z> viewProviderOneComponentFactory = getOneComponentFactory(uiCompManager, comp);
        viewProviderOneComponentFactory.updateComponentFromUI(uiCompManager, comp, uiComp);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V, T extends AbstractCompLocValue, Z> ComponentUISingleFactory<Component<V, T>, Z> getOneComponentFactory(ComponentsManager uiCompManager, Component<V, T> comp) {
        ComponentUISingleFactory<Component<V, T>, Z> viewProviderOneComponentFactory = null;
        CustomComponentUISingleFactory custom = (CustomComponentUISingleFactory) comp.getProperty(ACConstants.CUSTOM_VIEWPROVIDER_ONE_COMPONENT_FACTORY);
        if (custom != null) {
            viewProviderOneComponentFactory = custom.createComponentFactory(clientUI.getClient().getClientPlatform(), uiCompManager);
        }
        if (viewProviderOneComponentFactory != null) {
            return viewProviderOneComponentFactory;
        }
        if (comp instanceof AbstractFieldAC) {
            if (((AbstractFieldAC) comp).getFetchId() != null) {
                viewProviderOneComponentFactory = getFetchSingleComponentFactory();
                if (viewProviderOneComponentFactory == null) {
                    throw new IllegalArgumentException("No fetch Factory register for component : " + comp.getClass());
                }
            }
        }
        if (viewProviderOneComponentFactory != null) {
            return viewProviderOneComponentFactory;
        }
        viewProviderOneComponentFactory = oneComponentfactoryMap.get(comp.getClass());
        if (viewProviderOneComponentFactory == null) {
            // Get interface that extends AbstractViewProviderComponent
            for (Class<?> iface : ClassUtil.getInterfacesHierarchy(comp.getClass())) {
                if (Component.class.isAssignableFrom(iface)) {
                    viewProviderOneComponentFactory = oneComponentfactoryMap.get(iface);
                    if (viewProviderOneComponentFactory != null) {
                        oneComponentfactoryMap.put(comp.getClass(), viewProviderOneComponentFactory);
                        break;
                    }
                }
            }
        }
        if (viewProviderOneComponentFactory == null) {
            throw new IllegalArgumentException("No Factory register for component : " + comp.getClass());
        }
        return viewProviderOneComponentFactory;
    }

    /**
     * Here in subclasses we can register our factories for components
     */
    protected abstract void registerOneComponentFactories();

    /**
     * Register one component factory
     * 
     * @param <T>
     * @param <V>
     * @param factory
     */
    @Override
    public <T extends Component, V> void registerSingleComponentFactory(ComponentUISingleFactory<T, V> factory) {
        oneComponentfactoryMap.put(factory.getComponentClass(), factory);
    }

    @Override
    public Collection<Class<? extends Component>> getAvailableCompClasses() {
        return Collections.unmodifiableSet(oneComponentfactoryMap.keySet());
    }

    @Override
    public ComponentUISingleFactory getSingleComponentFactory(Class<? extends Component> clazz) {
        ComponentUISingleFactory factory = oneComponentfactoryMap.get(clazz);
        if (factory != null) {
            return factory;
        }
        for (Class iface : ClassUtil.getInterfacesHierarchy(clazz)) {
            if (Component.class.isAssignableFrom(iface)) {
                factory = oneComponentfactoryMap.get(iface);
                if (factory != null) {
                    return factory;
                }
            }
        }
        return null;
    }

    @Override
    public ComponentUISingleFactory getMandatorySingleComponentFactory(Class<? extends Component> clazz) {
        ComponentUISingleFactory factory = getSingleComponentFactory(clazz);
        if (factory == null) {
            throw new VPDARuntimeException("No factory registered by class " + clazz);
        }
        return null;
    }

    @Override
    public UILayoutManagerFactory getUILayoutManagerFactory() {
        return viewProviderUILayoutManagerFactory;
    }

    /**
     * Creates ViewProviderUILayoutManagerFactory
     * 
     * @return new creates ViewProviderUILayoutManagerFactory
     */
    protected abstract UILayoutManagerFactory createViewProviderUILayoutManagerFactory();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Component, V> ComponentUISingleFactory<T, V> getFetchSingleComponentFactory() {
        return fetchFactory;
    }

    @Override
    public <T extends Component, V> void registerFetchSingleComponentFactory(ComponentUISingleFactory<T, V> factory) {
        this.fetchFactory = factory;
    }

}
