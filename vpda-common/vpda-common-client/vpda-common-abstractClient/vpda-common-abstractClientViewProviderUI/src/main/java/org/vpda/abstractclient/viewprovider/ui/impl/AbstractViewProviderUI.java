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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vpda.abstractclient.core.comps.ComponentsManager;
import org.vpda.abstractclient.core.comps.impl.ComponentsManagerImpl;
import org.vpda.abstractclient.core.ui.ClientUI;
import org.vpda.abstractclient.core.ui.ClientWithUI;
import org.vpda.abstractclient.core.ui.Frame;
import org.vpda.abstractclient.core.ui.WindowManager;
import org.vpda.abstractclient.viewprovider.ui.ContainerViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.FrameOpenKind;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIManager;
import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderContext;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInfo;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.preferences.AbstractViewProviderPreferences;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.CommandExecutorWithRegistry;
import org.vpda.common.command.MethodCommandEvent;
import org.vpda.common.command.env.CEEnvDelegate;
import org.vpda.common.command.executor.impl.CommandExecutorBase;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandExceptionExecutionPolicy;
import org.vpda.common.comps.ui.UIComponentAccessor;
import org.vpda.common.entrypoint.ConcreteApplication;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.PicoContainerObjectResolver;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.StringUtil;

/**
 * Abstraction of view provider ui
 * 
 * @author kitko
 *
 */
public abstract class AbstractViewProviderUI implements ViewProviderUI, Serializable {
    private static final long serialVersionUID = -5649939832633834318L;
    /** Id of provider */
    protected ViewProviderID viewProviderID;
    /** Reference to real provider, usually some proxy to server object */
    protected ViewProvider provider;
    /** Frame this provider ui will be shown at */
    protected Frame frame;
    /** Owner provider ui */
    protected ContainerViewProviderUI owner;
    /** provider info of current provider */
    protected ViewProviderInfo viewProviderInfo;
    /** Calling context */
    protected ViewProviderContext callerProviderContext;
    /** Window manager */
    protected WindowManager windowManager;
    /** initialized flag */
    protected boolean isInitialized;
    /** Provider definition */
    protected ViewProviderDef viewProviderDef;
    /** Reference to client */
    protected ClientWithUI client;
    /** Provider preferences */
    protected AbstractViewProviderPreferences viewProviderPreferences;
    /** Client ui */
    protected ClientUI clientUI;
    /** Provider manager */
    protected ViewProviderUIManager viewProviderUIManager;
    /** our execution environment */
    protected CommandExecutionEnv commandExecutionEnv;
    /** Id of parent */
    protected ViewProviderID parrentProviderId;
    /** List of children */
    protected List<ViewProviderID> children;
    /** Localization service */
    protected LocalizationService locService;
    /** How we should be opened */
    public ViewProviderOpenInfoDU openInfo;
    /** Listeners support */
    protected ViewProviderUIListenersSupportWithFired listenersSupport;
    /** Components manager */
    protected ComponentsManager componentsManager;
    /** Initial provider init response */
    protected ViewProviderInitResponse initResponse;

    private CommandExecutor commandExecutor;
    private boolean beeingDisposed;
    private boolean disposed;

    @Override
    public void dispose() {
        // if dispose was called, return
        if (beeingDisposed) {
            return;
        }
        getListenersSupport().fireBeforeDispose();
        beeingDisposed = true;
        if (owner != null) {
            owner.setProviderActive(viewProviderID, false);
        }
        // dispose children, do copy of list
        List<ViewProviderID> children = new ArrayList<ViewProviderID>(this.children);
        for (ViewProviderID childId : children) {
            ViewProviderUI childUI = viewProviderUIManager.getViewProviderUI(childId);
            if (childUI != null) {
                childUI.dispose();
            }
        }
        provider.close();
        if (frame.isVisible()) {
            FrameOpenKind frameOpenKind = (openInfo.getViewProviderUIInfo()).getFrameOpenKind();
            if (frameOpenKind.equals(FrameOpenKind.NEW_FRAME)) {
                frame.dispose();
            }
            else {
                // we are embedded
                if (parrentProviderId != null) {
                    ViewProviderUI parentProviderUI = viewProviderUIManager.getViewProviderUI(parrentProviderId);
                    if (parentProviderUI != null) {
                        parentProviderUI.removeEmbeddedChildUI(this);
                    }
                }
            }
        }
        viewProviderUIManager.unregisterUIProvider(this);
        getListenersSupport().fireAfterDispose();
        clearVariables();
        disposed = true;
        isInitialized = false;
    }

    @Override
    public void startInitFlow() {
        if (initResponse.getInitCommand() == null) {
            initData();
        }
        else {
            clientUI.executeCommand(getCommandExecutor(), initResponse.getInitCommand(), commandExecutionEnv, new MethodCommandEvent(getClass(), "startInitFlow"));
        }
    }

    @Override
    public int getChildrenCount() {
        return children.size();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    /** Clear variables after dispose */
    protected void clearVariables() {
        viewProviderID = null;
        provider = null;
        frame = null;
        owner = null;
        viewProviderInfo = null;
        callerProviderContext = null;
        windowManager = null;
        viewProviderDef = null;
        client = null;
        viewProviderPreferences = null;
        clientUI = null;
        viewProviderUIManager = null;
        commandExecutionEnv = null;
        parrentProviderId = null;
        children = null;
        locService = null;
        openInfo = null;
        commandExecutor = null;
        componentsManager = null;
    }

    /**
     * Creates AbstractViewProviderUI and initialize fields
     * 
     * @param viewProviderUIManager
     * @param openInfo
     * @param provider
     * @param initResponse
     */
    protected AbstractViewProviderUI(ViewProviderUIManager viewProviderUIManager, ViewProviderOpenInfoDU openInfo, ViewProvider provider, ViewProviderInitResponse initResponse) {
        this.viewProviderUIManager = Assert.isNotNull(viewProviderUIManager, "viewProviderUIFactory argument is null");
        this.provider = Assert.isNotNullArgument(provider, "provider");
        this.openInfo = Assert.isNotNullArgument(openInfo, "openInfo");
        this.initResponse = Assert.isNotNullArgument(initResponse, "initResponse");
        this.viewProviderDef = Assert.isNotNull(openInfo.getViewProviderDef(), "viewProviderDef argument is null");
        this.client = viewProviderUIManager.getClient();
        this.clientUI = client.getClientUI();
        this.windowManager = client.getClientUI().getWindowManager();
        this.callerProviderContext = openInfo.getViewProviderContext();
        children = new ArrayList<ViewProviderID>(2);
        this.locService = client.getServiceRegistry().getService(LocalizationService.class);
        this.listenersSupport = createListenersSupport();
        this.componentsManager = createComponentsManager();
        this.viewProviderInfo = initResponse.getViewProviderInfo();
        this.viewProviderPreferences = initResponse.getInitPreferences();
        if (this.viewProviderPreferences == null) {
            this.viewProviderPreferences = buildTemporalPreferences(initResponse.getInitSettings());
        }
        this.viewProviderID = viewProviderInfo.getViewProviderID();
        this.viewProviderDef = this.viewProviderID.getDef();
        this.commandExecutor = createCommandExecutor();
        this.commandExecutionEnv = createCommandExecutionEnv();
    }

    @Override
    public AbstractViewProviderPreferences getViewProviderPreferences() {
        return viewProviderPreferences;
    }

    /**
     * @param initSettings
     * @return AbstractViewProviderPreferences
     */
    protected abstract AbstractViewProviderPreferences buildTemporalPreferences(ViewProviderSettings initSettings);

    /**
     * Creates ViewProviderComponentsManager
     * 
     * @return new created components manager
     */
    protected ComponentsManager createComponentsManager() {
        return new ComponentsManagerImpl(this, viewProviderUIManager.getComponentsFactory());
    }

    /**
     * Will create listener support
     * 
     * @return listenersSupport
     */
    protected ViewProviderUIListenersSupportWithFired createListenersSupport() {
        return new ViewProviderUIListenersSupportImpl(this);
    }

    @Override
    public Frame getFrame() {
        return frame;
    }

    @Override
    public ViewProviderID getViewProviderID() {
        return viewProviderID;
    }

    @Override
    public final void initialize() {
        adjustParent();
        frame = buildUI();
        setupListeners();
        getListenersSupport().fireAfterInit();
    }

    /**
     * Will setup basic listeners
     */
    protected void setupListeners() {
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public abstract Frame buildUI();

    @Override
    public ViewProviderContext getCallerProviderContext() {
        return callerProviderContext;
    }

    /**
     * 
     * @return caller context for this View UI
     */
    @Override
    @SuppressWarnings("unchecked")
    public ViewProviderContext getCurrentProviderContext() {
        return new ViewProviderContext(callerProviderContext, new ViewProviderCallerItem(viewProviderID, getCurrentData()));
    }

    @Override
    public ViewProviderInfo getViewProviderInfo() {
        return viewProviderInfo;
    }

    /**
     * Creates command execution environment
     * 
     * @return CommandExecutionEnv
     */
    protected CommandExecutionEnv createCommandExecutionEnv() {
        List<ObjectResolver> resolvers = new ArrayList<ObjectResolver>();
        resolvers.add(new PicoContainerObjectResolver(ConcreteApplication.getInstance().getAllModulesContainer()));
        resolvers.add(createViewProviderObjectResolver());
        CommandExecutionEnv env = new CEEnvDelegate(new MacroObjectResolverImpl(resolvers));
        return env;
    }

    @Override
    public CommandExecutionEnv getCommandExecutionEnv() {
        if (commandExecutionEnv == null) {
            commandExecutionEnv = createCommandExecutionEnv();
        }
        return commandExecutionEnv;
    }

    /**
     * Creates command executor that can executes command for this ui
     * 
     * @return CommandExecutor
     */
    protected CommandExecutor createCommandExecutor() {
        commandExecutor = new CommandExecutorBase(getClass().getName());
        return commandExecutor;
    }

    private static final class ProviderObjectResolver implements ObjectResolver, Serializable {
        private static final long serialVersionUID = -8430394415129386027L;
        private final AbstractViewProviderUI ui;

        /**
         * @param ui
         */
        private ProviderObjectResolver(AbstractViewProviderUI ui) {
            super();
            this.ui = ui;
        }

        @Override
        public <T> boolean canResolveObject(Class<T> clazz) {
            return clazz.isInstance(ui);
        }

        @Override
        public <T> T resolveObject(Class<T> clazz, Map<?, Object> contextObjects) {
            if (clazz.isInstance(ui)) {
                return clazz.cast(ui);
            }
            return null;
        }

        @Override
        public <T> T resolveObject(Class<T> clazz, Object key, Map<?, Object> contextObjects) {
            return resolveObject(clazz, contextObjects);
        }

        @Override
        public <T> boolean canResolveObject(Class<T> clazz, Object key) {
            return canResolveObject(clazz);
        }

        @Override
        public <T> T resolveObject(Class<T> clazz) {
            return resolveObject(clazz, null);
        }

        @Override
        public <T> T resolveObject(Class<T> clazz, Object key) {
            return resolveObject(clazz, key, null);
        }

        @Override
        public Collection<?> getKeys() {
            return Collections.singletonList(AbstractViewProviderUI.class);
        }

    }

    /**
     * Creates resolver for command execution
     * 
     * @return object resolver holding important provider ui references
     */
    protected ObjectResolver createViewProviderObjectResolver() {
        return new ProviderObjectResolver(this);
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        if (commandExecutor == null) {
            commandExecutor = createCommandExecutor();
        }
        return commandExecutor;
    }

    /**
     * @return resquestCommand executor
     */
    @Override
    public CommandExecutorWithRegistry getRequestCommandsExecutor() {
        return client.getRequestCommandExecutor();
    }

    @Override
    public ViewProvider getViewProvider() {
        return provider;
    }

    /**
     * handle exception for this UI
     * 
     * @param e
     * @param msg
     */
    @Override
    public void handleException(Exception e, String msg) {
        clientUI.showExceptionDialog(e, msg);
    }

    @Override
    public ClientUI getClientUI() {
        return clientUI;
    }

    @Override
    public ClientWithUI getClient() {
        return client;
    }

    @Override
    public ViewProviderUIManager getViewProviderUIManager() {
        return viewProviderUIManager;
    }

    /**
     * Sets parent from AbstractListViewProviderUI
     */
    protected void adjustParent() {
        if (callerProviderContext != null && callerProviderContext.getLastCaller() != null) {
            parrentProviderId = callerProviderContext.getLastCaller().getProviderId();
            ViewProviderUI parrent = viewProviderUIManager.getViewProviderUI(parrentProviderId);
            parrent.addChild(this);
        }
    }

    @Override
    public void addChild(ViewProviderUI child) {
        children.add(child.getViewProviderID());
        child.getListenersSupport().addDisposeListener(new DisposeListenerAdapter() {
            private static final long serialVersionUID = 7201805277429223136L;

            @Override
            public void afterUiDisposed(ViewProviderUI ui) {
                removeChild(ui);
            }

        });
        getListenersSupport().fireChildAdded(child);
    }

    /**
     * Will remove child
     * 
     * @param ui
     */
    protected void removeChild(ViewProviderUI ui) {
        children.remove(ui.getViewProviderID());
        getListenersSupport().fireChildRemoved(ui);
    }

    @Override
    public String toString() {
        return viewProviderID != null ? viewProviderID.toString() : super.toString();
    }

    @Override
    public ViewProviderUIListenersSupportWithFired getListenersSupport() {
        return listenersSupport;
    }

    @Override
    public ComponentsManager getComponentsManager() {
        return componentsManager;
    }

    @Override
    public void notifyListenersChanged() {
    }

    /**
     * Template command for calling server
     * 
     * @author rki
     * @param <T>
     *
     */
    protected abstract class AbstractCallServerCommandBase<T> extends AbstractRequestCommand<T> {
        /** Creates command */
        protected AbstractCallServerCommandBase() {
            super(getRequestCommandsExecutor(), AbstractViewProviderUI.this.getCommandExecutor());
        }

        @Override
        protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
            if (disposed) {
                return false;
            }
            getFrame().setEnabledUI(false);
            return true;
        }

        @Override
        protected void executeAfterRequest(T results, CommandExecutionEnv env, CommandEvent event) {
            if (disposed) {
                return;
            }
            getFrame().setEnabledUI(true);
        }

        @Override
        protected RequestCommandExceptionExecutionPolicy executeExceptionHandling(Exception e, CommandExecutionEnv env, CommandEvent event) {
            clientUI.showExceptionDialog(e, "Error getting data from provider");
            if (getFrame() != null) {
                getFrame().setEnabledUI(true);
            }
            return RequestCommandExceptionExecutionPolicy.ABORT;
        }
    }

    /**
     * @return resolved title
     */
    @Override
    public String resolveTitle() {
        String title = null;
        if (viewProviderInfo.getLocValue() != null && viewProviderInfo.getLocValue().getLocValue() != null && viewProviderInfo.getLocValue().getLocValue().getTitle() != null) {
            title = viewProviderInfo.getLocValue().getLocValue().getTitle();
        }
        if (StringUtil.isEmpty(title)) {
            title = openInfo.getViewProviderUIInfo().getFrameInfo().getTitle();
        }
        if (StringUtil.isEmpty(title)) {
            title = viewProviderDef.getCodeTitle();
        }
        return title;
    }

    @Override
    public Object getID() {
        return getViewProviderID();
    }

    @Override
    public void showExceptionDialog(Throwable e, String msg) {
        getClientUI().showExceptionDialog(getFrame(), e, msg);
    }

    @Override
    public void showMessageDialog(String title, String text) {
        getClientUI().showMessageDialog(getFrame(), title, text);
    }

    @Override
    public void flipChildrenOrientation() {
        getListenersSupport().fireLayoutChanged();
    }

    @Override
    public void applyViewProviderPreferences(AbstractViewProviderPreferences preferences) {
        applyViewProviderSettings(preferences.getViewProviderSettings());
        this.viewProviderPreferences = preferences;
    }

    @Override
    public <V, U> UIComponentAccessor<V, U> createUIComponentAccesorWrapperUI(UIComponentAccessor<V, U> accesor) {
        return getClientUI().createUIComponentAccesorWrapperUI(accesor);
    }

}
