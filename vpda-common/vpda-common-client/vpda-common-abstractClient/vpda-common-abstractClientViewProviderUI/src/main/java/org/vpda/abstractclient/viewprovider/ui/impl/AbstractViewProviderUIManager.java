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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vpda.abstractclient.core.comps.ComponentUIFactory;
import org.vpda.abstractclient.core.comps.ComponentsUI;
import org.vpda.abstractclient.core.ui.ClientWithUI;
import org.vpda.abstractclient.viewprovider.ui.CustomFactoryForViewProviderKindUIFactory;
import org.vpda.abstractclient.viewprovider.ui.DefaultViewProviderUIInfoImpl;
import org.vpda.abstractclient.viewprovider.ui.FrameOpenKind;
import org.vpda.abstractclient.viewprovider.ui.MutableViewProviderUIManager;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderKindUIFactory;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIManager;
import org.vpda.clientserver.communication.CommunicationStateKind;
import org.vpda.clientserver.communication.services.ClientServer;
import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderConstants;
import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.ViewProviderInitProperties;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderKind;
import org.vpda.clientserver.viewprovider.ViewProviderRuntimeException;
import org.vpda.clientserver.viewprovider.ViewProviderService;
import org.vpda.clientserver.viewprovider.ViewProviderWithInitResponse;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProvider;
import org.vpda.clientserver.viewprovider.autocomplete.AutoCompleteViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.autocomplete.impl.AutoCompleteViewProviderStatelessBridge;
import org.vpda.clientserver.viewprovider.autocomplete.stateless.StatelessAutoCompleteViewProviderServices;
import org.vpda.clientserver.viewprovider.generic.GenericViewProvider;
import org.vpda.clientserver.viewprovider.generic.GenericViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.generic.impl.GenericViewProviderStatelessBridge;
import org.vpda.clientserver.viewprovider.generic.stateless.StatelessGenericViewProviderServices;
import org.vpda.clientserver.viewprovider.list.ListViewProvider;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.list.impl.ListViewProviderStatelessBridge;
import org.vpda.clientserver.viewprovider.list.stateless.StatelessListViewProviderServices;
import org.vpda.clientserver.viewprovider.stateless.BasicStatelessViewProviderServices;
import org.vpda.clientserver.viewprovider.stateless.StatelessProviderServiceWithInitResponse;
import org.vpda.internal.common.util.Assert;

/**
 * Abstract implementation for
 * 
 * @author kitko {@link ViewProviderUIManager} regardless target client platform
 *
 */
public abstract class AbstractViewProviderUIManager implements MutableViewProviderUIManager, Serializable {
    private static final long serialVersionUID = 647160165676026587L;
    private ComponentUIFactory viewProviderComponentsUIFactory;
    private final Map<ViewProviderKind, ViewProviderKindUIFactory> factories;
    private final Map<ViewProviderID, ViewProviderUI> providers;
    private final ClientWithUI client;

    /**
     * Constructor with ViewProviderComponentsUIFactory
     * 
     * @param client
     * @param viewProviderComponentsFactory
     */
    public AbstractViewProviderUIManager(ClientWithUI client, ComponentUIFactory viewProviderComponentsFactory) {
        this.client = Assert.isNotNullArgument(client, "client");
        this.viewProviderComponentsUIFactory = viewProviderComponentsFactory;
        factories = new HashMap<ViewProviderKind, ViewProviderKindUIFactory>(4);
        providers = new HashMap<ViewProviderID, ViewProviderUI>();
    }

    /**
     * Default constructor with client
     * 
     * @param client
     */
    public AbstractViewProviderUIManager(ClientWithUI client) {
        this(client, null);
    }

    @Override
    public ClientWithUI getClient() {
        return client;
    }

    @Override
    public ViewProviderUI createViewProviderUI(ComponentsUI contextCaller, ViewProviderOpenInfoDU openInfo) {
        return createViewProviderUI(client, client.getClientServer(), contextCaller, openInfo);
    }

    @Override
    public ViewProviderUI createViewProviderUI(ClientWithUI client, ClientServer clientServer, ComponentsUI contextCaller, ViewProviderOpenInfoDU openInfo) {
        CommunicationStateKind communicationStateKind = client.getSession().getLoginInfo().getConnectionInfo().getCommunicationStateKind();
        ViewProvider provider = null;
        ViewProviderInitResponse initResponse = null;
        FrameOpenKind frameOpenKind = openInfo.getViewProviderUIInfo().getFrameOpenKind();
        ViewProviderInitProperties initProperties = openInfo.getInitProperties();
        if (initProperties == null) {
            initProperties = new ViewProviderInitProperties();
        }
        ViewProviderInitData initData = new ViewProviderInitData(openInfo.getViewProviderContext(), openInfo.getOperation(), new DefaultViewProviderUIInfoImpl(frameOpenKind), initProperties);
        ViewProviderWithInitResponse initResponseWithProvider = null;
        if (CommunicationStateKind.Statefull.equals(communicationStateKind)) {
            initResponseWithProvider = createAndInitStatefullProvider(client, clientServer, openInfo, initData);
        }
        else {
            initResponseWithProvider = createAndInitStatelessProvider(client, clientServer, openInfo, initData);
        }
        provider = initResponseWithProvider.getViewProvider();
        initResponse = initResponseWithProvider.getInitResponse();
        ViewProviderKindUIFactory factory = null;
        CustomFactoryForViewProviderKindUIFactory customFactory = (CustomFactoryForViewProviderKindUIFactory) initResponse
                .getProperty(ViewProviderConstants.CUSTOM_FACTORY_FOR_VIEWPROVIDER_KINDUIFACTORY);
        if (customFactory != null) {
            factory = customFactory.createCustomFactory(client.getClientPlatform(), this, openInfo);
        }
        if (factory == null) {
            factory = factories.get(openInfo.getViewProviderDef().getKind());
        }
        if (factory == null) {
            throw new IllegalArgumentException("No factory registered for viewProviderKind " + openInfo.getViewProviderDef().getKind());
        }
        ViewProviderUI viewProviderUI = factory.createViewProviderUI(this, openInfo, provider, initResponse);
        return viewProviderUI;
    }

    private ViewProviderWithInitResponse createAndInitStatelessProvider(ClientWithUI client, ClientServer clientServer, ViewProviderOpenInfoDU openInfo, ViewProviderInitData initData) {
        try {
            ViewProvider provider = null;
            BasicStatelessViewProviderServices basicStatelessViewProviderServices = client.getServiceRegistry().getService(BasicStatelessViewProviderServices.class);
            if (basicStatelessViewProviderServices == null) {
                basicStatelessViewProviderServices = clientServer.getService(BasicStatelessViewProviderServices.class);
                client.getServiceRegistry().registerService(BasicStatelessViewProviderServices.class, basicStatelessViewProviderServices);
            }
            if (ViewProviderKind.ListViewProvider.equals(openInfo.getViewProviderDef().getKind())) {
                StatelessProviderServiceWithInitResponse<StatelessListViewProviderServices> viewProviderSpecificKindServicesWithInitResponse = basicStatelessViewProviderServices
                        .getViewProviderSpecificKindServicesWithInitResponse(ViewProviderKind.ListViewProvider, StatelessListViewProviderServices.class, openInfo.getViewProviderDef(), initData);
                provider = new ListViewProviderStatelessBridge(openInfo.getViewProviderDef(), viewProviderSpecificKindServicesWithInitResponse.getService(), initData,
                        (ListViewProviderInitResponse) viewProviderSpecificKindServicesWithInitResponse.getInitResponse());
                return new ViewProviderWithInitResponse<ListViewProvider>(ListViewProvider.class, viewProviderSpecificKindServicesWithInitResponse.getInitResponse(),
                        ListViewProvider.class.cast(provider));
            }
            else if (ViewProviderKind.GenericViewProvider.equals(openInfo.getViewProviderDef().getKind())) {
                StatelessProviderServiceWithInitResponse<StatelessGenericViewProviderServices> viewProviderSpecificKindServicesWithInitResponse = basicStatelessViewProviderServices
                        .getViewProviderSpecificKindServicesWithInitResponse(ViewProviderKind.GenericViewProvider, StatelessGenericViewProviderServices.class, openInfo.getViewProviderDef(), initData);
                provider = new GenericViewProviderStatelessBridge(openInfo.getViewProviderDef(), viewProviderSpecificKindServicesWithInitResponse.getService(), initData,
                        (GenericViewProviderInitResponse) viewProviderSpecificKindServicesWithInitResponse.getInitResponse());
                return new ViewProviderWithInitResponse<GenericViewProvider>(GenericViewProvider.class, viewProviderSpecificKindServicesWithInitResponse.getInitResponse(),
                        GenericViewProvider.class.cast(provider));
            }
            else if (ViewProviderKind.AutoCompleteViewProvider.equals(openInfo.getViewProviderDef().getKind())) {
                StatelessProviderServiceWithInitResponse<StatelessAutoCompleteViewProviderServices> viewProviderSpecificKindServicesWithInitResponse = basicStatelessViewProviderServices
                        .getViewProviderSpecificKindServicesWithInitResponse(ViewProviderKind.AutoCompleteViewProvider, StatelessAutoCompleteViewProviderServices.class, openInfo.getViewProviderDef(),
                                initData);
                provider = new AutoCompleteViewProviderStatelessBridge(openInfo.getViewProviderDef(), viewProviderSpecificKindServicesWithInitResponse.getService(), initData,
                        (AutoCompleteViewProviderInitResponse) viewProviderSpecificKindServicesWithInitResponse.getInitResponse());
                return new ViewProviderWithInitResponse<AutoCompleteViewProvider>(AutoCompleteViewProvider.class, viewProviderSpecificKindServicesWithInitResponse.getInitResponse(),
                        AutoCompleteViewProvider.class.cast(provider));
            }
            throw new IllegalArgumentException("Unsupported ViewProviderKind : " + openInfo.getViewProviderDef().getKind());
        }
        catch (ViewProviderException e) {
            client.getClientUI().showExceptionDialog(e, "Error while creating stateless provider");
            throw new ViewProviderRuntimeException(openInfo.getViewProviderDef(), e);
        }
    }

    private ViewProviderWithInitResponse createAndInitStatefullProvider(ClientWithUI client, ClientServer clientServer, ViewProviderOpenInfoDU openInfo, ViewProviderInitData initData) {
        try {
            ViewProviderService viewProviderService = client.getServiceRegistry().getService(ViewProviderService.class);
            if (viewProviderService == null) {
                viewProviderService = clientServer.getService(ViewProviderService.class);
                client.getServiceRegistry().registerService(ViewProviderService.class, viewProviderService);
            }
            if (ViewProviderKind.ListViewProvider.equals(openInfo.getViewProviderDef().getKind())) {
                ViewProviderWithInitResponse<ListViewProvider> providerWithInit = viewProviderService.getAndInitViewProvider(ListViewProvider.class, openInfo.getViewProviderDef(), initData);
                return providerWithInit;
            }
            else if (ViewProviderKind.GenericViewProvider.equals(openInfo.getViewProviderDef().getKind())) {
                ViewProviderWithInitResponse<GenericViewProvider> providerWithInit = viewProviderService.getAndInitViewProvider(GenericViewProvider.class, openInfo.getViewProviderDef(), initData);
                return providerWithInit;
            }
            else if (ViewProviderKind.AutoCompleteViewProvider.equals(openInfo.getViewProviderDef().getKind())) {
                ViewProviderWithInitResponse<AutoCompleteViewProvider> providerWithInit = viewProviderService.getAndInitViewProvider(AutoCompleteViewProvider.class, openInfo.getViewProviderDef(),
                        initData);
                return providerWithInit;
            }
            throw new IllegalArgumentException("Unsupported ViewProviderKind : " + openInfo.getViewProviderDef().getKind());
        }
        catch (ViewProviderException e) {
            client.getClientUI().showExceptionDialog(e, "Error while creating statefull provider");
            throw new ViewProviderRuntimeException(openInfo.getViewProviderDef(), e);
        }
    }

    @Override
    public ComponentUIFactory getComponentsFactory() {
        if (viewProviderComponentsUIFactory == null) {
            viewProviderComponentsUIFactory = createViewProviderComponentsUIFactory();
        }
        return viewProviderComponentsUIFactory;
    }

    /**
     * Creates ViewProviderComponentsUIFactory
     * 
     * @return new created ViewProviderComponentsUIFactory
     */
    protected abstract ComponentUIFactory createViewProviderComponentsUIFactory();

    @Override
    public void registerViewProviderKindUIFactory(ViewProviderKindUIFactory factory) {
        factories.put(factory.getViewProviderKind(), factory);

    }

    @Override
    public void setViewProviderComponentsUIFactory(ComponentUIFactory viewProviderComponentsUIFactory) {
        this.viewProviderComponentsUIFactory = viewProviderComponentsUIFactory;
    }

    @Override
    public ViewProviderUI getViewProviderUI(ViewProviderID providerId) {
        return providers.get(providerId);
    }

    @Override
    public Collection<? extends ViewProviderUI> getProviders() {
        return providers.values();
    }

    @Override
    public Collection<ViewProviderID> getProvidersIDS() {
        return providers.keySet();
    }

    @Override
    public void registerUIProvider(ViewProviderUI provider) {
        providers.put(provider.getViewProviderID(), provider);
    }

    @Override
    public void unregisterUIProvider(ViewProviderUI provider) {
        providers.remove(provider.getViewProviderID());
    }

    @Override
    public ViewProviderUI createViewProviderUIAndRegister(ComponentsUI contextCaller, ViewProviderOpenInfoDU openInfo) {
        ViewProviderUI viewProviderUI = createViewProviderUI(contextCaller, openInfo);
        registerUIProvider(viewProviderUI);
        return viewProviderUI;
    }

    @Override
    public ViewProviderUI createViewProviderUIAndRegister(ClientWithUI client, ClientServer clientServer, ComponentsUI contextCaller, ViewProviderOpenInfoDU openInfo) {
        ViewProviderUI viewProviderUI = createViewProviderUI(client, clientServer, contextCaller, openInfo);
        registerUIProvider(viewProviderUI);
        return viewProviderUI;
    }

}
