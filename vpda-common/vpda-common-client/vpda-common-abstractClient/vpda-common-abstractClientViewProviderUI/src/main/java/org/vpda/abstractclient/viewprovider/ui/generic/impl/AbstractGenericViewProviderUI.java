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
package org.vpda.abstractclient.viewprovider.ui.generic.impl;

import java.sql.Timestamp;
import java.util.UUID;

import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIManager;
import org.vpda.abstractclient.viewprovider.ui.commands.generic.CompleteAutoCompleteCall;
import org.vpda.abstractclient.viewprovider.ui.commands.generic.CompleteFetchCall;
import org.vpda.abstractclient.viewprovider.ui.commands.generic.FireFetchCall;
import org.vpda.abstractclient.viewprovider.ui.commands.generic.RefreshCall;
import org.vpda.abstractclient.viewprovider.ui.generic.GenericViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.impl.AbstractViewProviderUI;
import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.generic.GenericViewProvider;
import org.vpda.clientserver.viewprovider.generic.GenericViewProviderInfo;
import org.vpda.clientserver.viewprovider.generic.GenericViewProviderSettings;
import org.vpda.clientserver.viewprovider.preferences.GenericViewProviderPreferences;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.MethodCommandEvent;
import org.vpda.common.command.ResultWithCommands;
import org.vpda.common.command.call.CommandCall;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.comps.autocomplete.AutoCompleteDataItem;
import org.vpda.common.comps.autocomplete.AutoCompleteDef;
import org.vpda.common.comps.ui.AbstractFieldAC;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ComponentCommandEvent;
import org.vpda.common.comps.ui.def.AutoCompletedValues;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.common.comps.ui.def.SingleAutoCompletedValues;
import org.vpda.internal.common.util.ObjectUtil;

/**
 * Abstract Implementation of DetailViewProviderUI.
 * 
 * @author kitko
 *
 */
public abstract class AbstractGenericViewProviderUI extends AbstractViewProviderUI implements GenericViewProviderUI {
    private static final long serialVersionUID = -2589418197925623601L;
    /** Current detail components */
    protected ComponentsGroupsDef detailCompsDef;

    /**
     * Creates AbstractDetailViewProviderUI
     * 
     * @param viewProviderUIFactory
     * @param openInfo
     * @param provider
     * @param initResponse
     */
    protected AbstractGenericViewProviderUI(ViewProviderUIManager viewProviderUIFactory, ViewProviderOpenInfoDU openInfo, ViewProvider provider, ViewProviderInitResponse initResponse) {
        super(viewProviderUIFactory, openInfo, provider, initResponse);
    }

    @Override
    public GenericViewProviderSettings getViewProviderSettings() {
        return getViewProviderPreferences().getGenericViewProviderSettings();
    }

    @Override
    public GenericViewProviderPreferences getViewProviderPreferences() {
        return (GenericViewProviderPreferences) viewProviderPreferences;
    }

    @Override
    protected GenericViewProviderPreferences buildTemporalPreferences(ViewProviderSettings initSettings) {
        GenericViewProviderPreferences.Builder builder = new GenericViewProviderPreferences.Builder();
        builder.setViewProviderDef(viewProviderDef).setUuid(UUID.randomUUID()).setTemporal(true).setReadOnly(false).setName("Temporal settings").setDescription("List temporal preferences");
        builder.setCreator(getClient().getSession().getUser()).setContext(getClient().getSession().getApplContext()).setCreationTime(new Timestamp(System.currentTimeMillis()));
        builder.setGenericViewProviderSettings((GenericViewProviderSettings) initSettings);
        return builder.build();
    }

    @Override
    public void initData() {
        clientUI.executeCommand(getRequestCommandsExecutor(), createInitDataCmd(), commandExecutionEnv, new MethodCommandEvent(getClass(), "initData"));
    }

    @Override
    public void refreshData() {
        clientUI.executeCommand(getRequestCommandsExecutor(), createRefreshDataCmd(), commandExecutionEnv, null);
    }

    @Override
    public void applyViewProviderSettings(ViewProviderSettings viewProviderSettings) {
    }

    @Override
    public GenericViewProviderInfo getViewProviderInfo() {
        return (GenericViewProviderInfo) viewProviderInfo;
    }

    /**
     * Customize return value from super interface
     */
    @Override
    public GenericViewProvider getViewProvider() {
        return (GenericViewProvider) this.provider;
    }

    /**
     * Creates command that initializes data
     * 
     * @return command that will initialize data for detail
     */
    protected Command<ResultWithCommands<ComponentsGroupsDef>> createInitDataCmd() {
        AbstractRequestCommand<ResultWithCommands<ComponentsGroupsDef>> initDataCmd = new AbstractCallServerCommandBase<ResultWithCommands<ComponentsGroupsDef>>() {
            @Override
            protected ResultWithCommands<ComponentsGroupsDef> executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                AbstractGenericViewProviderUI.this.detailCompsDef = getViewProviderInfo().getDetailCompsDef();
                // We have already detail components data
                ComponentsGroupsDef results = null;
                return new ResultWithCommands<ComponentsGroupsDef>(results);
            }
        };
        return initDataCmd;
    }

    /**
     * Creates command that initializes data
     * 
     * @return command that will initialize data for detail
     */
    protected Command<ResultWithCommands<ComponentsGroupsDef>> createRefreshDataCmd() {
        AbstractRequestCommand<ResultWithCommands<ComponentsGroupsDef>> refreshDataCmd = new AbstractCallServerCommandBase<ResultWithCommands<ComponentsGroupsDef>>() {
            @SuppressWarnings("unchecked")
            @Override
            protected ResultWithCommands<ComponentsGroupsDef> executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ViewProviderCallerItem lastCallerItem = null;
                if (callerProviderContext != null) {
                    lastCallerItem = callerProviderContext.getLastCaller();
                    // update last caller current data
                    ViewProviderUI callerUI = viewProviderUIManager.getViewProviderUI(lastCallerItem.getProviderId());
                    lastCallerItem.setCurrentData(callerUI.getCurrentData());
                    if (openInfo.getAdjustData() != null) {
                        openInfo.getAdjustData().adjustCurrentData(lastCallerItem);
                    }
                }
                Command command = new CommandCall(new RefreshCall(lastCallerItem, getCurrentData()));
                Object result = provider.executeCommand(command, null, event).get();
                if (result instanceof ResultWithCommands) {
                    return (ResultWithCommands<ComponentsGroupsDef>) result;
                }
                return null;
            }
        };
        return refreshDataCmd;
    }

    @Override
    public ComponentsGroupsDef getCurrentData() {
        if (detailCompsDef == null) {
            return null;
        }
        // Update data
        componentsManager.updateAllComponentsFromUI();
        mergeCurrentCompsDef();
        return detailCompsDef;
    }

    /**
     * When we have different components in ui and in detailCompsDef, we must merge
     * it
     */
    @SuppressWarnings("unchecked")
    private void mergeCurrentCompsDef() {
        for (String compId : componentsManager.getComponentsIds()) {
            org.vpda.common.comps.ui.Component comp1 = componentsManager.getComponent(compId);
            Component comp2 = detailCompsDef.getComponent(compId);
            if (comp1 != null && comp2 != null && comp1 != comp2) {
                comp2.assignValues(comp1);
            }
        }
    }

    @Override
    public void fireFetch(String compId) {
        Component component = componentsManager.getComponent(compId);
        if (!(component instanceof AbstractFieldAC)) {
            throw new IllegalArgumentException("Cannot fire fetch on non fetch component");
        }
        String fetchId = ((AbstractFieldAC) component).getFetchId();
        if (fetchId == null) {
            throw new IllegalArgumentException("No fetch defined on field " + compId);
        }
        componentsManager.updateAllComponentsFromUI();
        boolean fireFetch = true;
        AutoCompleteDef autoCompleteFieldDef = ((AbstractFieldAC) component).getAutoCompleteFieldDef();
        if (autoCompleteFieldDef != null) {
            AutoCompletedValues autoCompletedValues = getCurrentData().getAutoCompletedValues();
            SingleAutoCompletedValues singleFetchCompletedValues = autoCompletedValues.getSingleFetchCompletedValues(autoCompleteFieldDef.getCompleteId());
            if (singleFetchCompletedValues != null) {
                Object filledValue = singleFetchCompletedValues.getFilledValue(component.getId());
                if (ObjectUtil.equalsConsiderNull(((AbstractFieldAC) component).getValue(), filledValue)) {
                    fireFetch = false;
                }
            }
        }

        // We are going to send all components, because server can customize fetch open
        if (fireFetch) {
            clientUI.executeCommand(getRequestCommandsExecutor(), createFireFetchCmd(compId, fetchId), commandExecutionEnv, new ComponentCommandEvent("fireFetch", getID(), component));
        }
    }

    @Override
    public void completeFetch(String compId) {
        Component component = componentsManager.getComponent(compId);
        if (!(component instanceof AbstractFieldAC)) {
            throw new IllegalArgumentException("Cannot fire fetch on non fetch component");
        }
        String fetchId = ((AbstractFieldAC) component).getFetchId();
        if (fetchId == null) {
            throw new IllegalArgumentException("No fetch defined on field : " + compId);
        }
        componentsManager.updateAllComponentsFromUI();
        // We are going to send all components, because server can customize fetch open
        clientUI.executeCommand(getRequestCommandsExecutor(), createCompleteFetchCmd(compId, fetchId), commandExecutionEnv, new ComponentCommandEvent("completeFetch", getID(), component));
    }

    @Override
    public void completeAutoComplete(String compId, AutoCompleteDataItem item) {
        Component component = componentsManager.getComponent(compId);
        if (!(component instanceof AbstractFieldAC)) {
            throw new IllegalArgumentException("Cannot fire fetch on non fetch component");
        }
        AutoCompleteDef autoCompleteFieldDef = ((AbstractFieldAC) component).getAutoCompleteFieldDef();
        if (autoCompleteFieldDef == null) {
            throw new IllegalArgumentException("No AutoCompleteFieldDef defined for field : " + compId);
        }
        componentsManager.updateAllComponentsFromUI();
        // We are going to send all components, because server can customize fetch open
        clientUI.executeCommand(getRequestCommandsExecutor(), createCompleteAutoCompleteCmd(compId, autoCompleteFieldDef, item), commandExecutionEnv,
                new ComponentCommandEvent("completeAutoComplete", getID(), component));
    }

    /**
     * Creates command that fires fetch
     * 
     * @param compId
     * @param fetchId
     * @return command that will fire fetch
     */
    protected Command<ResultWithCommands<ComponentsGroupsDef>> createFireFetchCmd(final String compId, final String fetchId) {
        AbstractRequestCommand<ResultWithCommands<ComponentsGroupsDef>> fetchCmd = new AbstractCallServerCommandBase<ResultWithCommands<ComponentsGroupsDef>>() {
            @SuppressWarnings("unchecked")
            @Override
            protected ResultWithCommands<ComponentsGroupsDef> executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                Command cmd = new CommandCall(new FireFetchCall(detailCompsDef, compId, fetchId));
                Object result = provider.executeCommand(cmd, null, event).get();
                if (result instanceof ResultWithCommands) {
                    return (ResultWithCommands<ComponentsGroupsDef>) result;
                }
                return null;
            }
        };
        return fetchCmd;
    }

    /**
     * Creates command that completes fetch
     * 
     * @param compId
     * @param fetchId
     * @return command that will complete fetch
     */
    protected Command<ResultWithCommands<ComponentsGroupsDef>> createCompleteFetchCmd(final String compId, final String fetchId) {
        AbstractRequestCommand<ResultWithCommands<ComponentsGroupsDef>> fetchCmd = new AbstractCallServerCommandBase<ResultWithCommands<ComponentsGroupsDef>>() {
            @SuppressWarnings("unchecked")
            @Override
            protected ResultWithCommands<ComponentsGroupsDef> executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                Command cmd = new CommandCall(new CompleteFetchCall(detailCompsDef, compId, fetchId));
                Object result = provider.executeCommand(cmd, null, event).get();
                if (result instanceof ResultWithCommands) {
                    return (ResultWithCommands<ComponentsGroupsDef>) result;
                }
                return null;
            }
        };
        return fetchCmd;
    }

    /**
     * Creates command that completes fetch
     * 
     * @param compId
     * @param autoCompleteFieldDef
     * @param autoCompletionData
     * @return command that will complete fetch
     */
    protected Command<ResultWithCommands<ComponentsGroupsDef>> createCompleteAutoCompleteCmd(final String compId, AutoCompleteDef autoCompleteFieldDef, AutoCompleteDataItem autoCompletionData) {
        AbstractRequestCommand<ResultWithCommands<ComponentsGroupsDef>> cmd = new AbstractCallServerCommandBase<ResultWithCommands<ComponentsGroupsDef>>() {
            @SuppressWarnings("unchecked")
            @Override
            protected ResultWithCommands<ComponentsGroupsDef> executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                Command cmd = new CommandCall(new CompleteAutoCompleteCall(detailCompsDef, compId, autoCompleteFieldDef, autoCompletionData));
                Object result = provider.executeCommand(cmd, null, event).get();
                if (result instanceof ResultWithCommands) {
                    return (ResultWithCommands<ComponentsGroupsDef>) result;
                }
                return null;
            }
        };
        return cmd;
    }

    @Override
    protected void clearVariables() {
        super.clearVariables();
        detailCompsDef = null;
    }

}
