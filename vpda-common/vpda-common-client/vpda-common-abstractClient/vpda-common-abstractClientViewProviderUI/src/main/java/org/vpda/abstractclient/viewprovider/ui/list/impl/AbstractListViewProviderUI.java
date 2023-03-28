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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.vpda.abstractclient.viewprovider.ui.InitListener;
import org.vpda.abstractclient.viewprovider.ui.UIStructureListener;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderLayoutListener;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderOpenInfoDU;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.ViewProviderUIManager;
import org.vpda.abstractclient.viewprovider.ui.commands.generic.CompleteFetchWithListViewResultsWithInfoCall;
import org.vpda.abstractclient.viewprovider.ui.generic.GenericViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.impl.AbstractViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.list.ListSelectionListener;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderUI;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderUIDataNavigation;
import org.vpda.abstractclient.viewprovider.ui.list.ListViewProviderUIListenersSupportWithFire;
import org.vpda.clientserver.viewprovider.ViewProvider;
import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.ViewProviderSettings;
import org.vpda.clientserver.viewprovider.fetch.FetchOpenInfo;
import org.vpda.clientserver.viewprovider.list.ListViewPagingSize;
import org.vpda.clientserver.viewprovider.list.ListViewPagingSize.ConcreteSize;
import org.vpda.clientserver.viewprovider.list.ListViewPagingSize.RelativeSize;
import org.vpda.clientserver.viewprovider.list.ListViewProvider;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInfo;
import org.vpda.clientserver.viewprovider.list.ListViewProviderOpenResultsAndReadDataRequest;
import org.vpda.clientserver.viewprovider.list.ListViewProviderSettings;
import org.vpda.clientserver.viewprovider.list.ListViewResults;
import org.vpda.clientserver.viewprovider.list.ListViewResultsInfo;
import org.vpda.clientserver.viewprovider.list.ListViewResultsWithInfo;
import org.vpda.clientserver.viewprovider.list.ReadDataRequestContent;
import org.vpda.clientserver.viewprovider.list.ReadingFrom;
import org.vpda.clientserver.viewprovider.preferences.ListViewProviderPreferences;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.MethodCommandEvent;
import org.vpda.common.command.ResultWithCommands;
import org.vpda.common.command.call.CommandCall;
import org.vpda.common.command.request.AbstractRequestCommand;
import org.vpda.common.command.request.RequestCommandExceptionExecutionPolicy;
import org.vpda.common.comps.EnvironmentDataChangeEvent;
import org.vpda.common.comps.EnvironmentInitEvent;
import org.vpda.common.comps.EnvironmentLayoutChangeEvent;
import org.vpda.common.comps.EnvironmentStructureChangeEvent;
import org.vpda.common.comps.autocomplete.AutoCompleteDataItem;
import org.vpda.common.comps.ui.ContainerAC;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.common.criteria.sort.Sort;
import org.vpda.common.criteria.sort.SortItem;
import org.vpda.common.criteria.sort.SortingOptions;
import org.vpda.common.criteria.sort.SortItem.SortDirection;
import org.vpda.common.ioc.objectresolver.ObjectResolver;

/**
 * @author kitko
 *
 */
public abstract class AbstractListViewProviderUI extends AbstractViewProviderUI implements ListViewProviderUI {
    private static final long serialVersionUID = 2814261907186348332L;
    /** Rows per page */
    protected int rowsPerPage = 0;
    /** Data navigation policy */
    protected ListViewProviderUIDataNavigation dataNavigation;
    /** Selection listener to refresh children */
    private ListSelectionListener refreshChildrenListener;

    private ListViewResults lastReadData;

    /**
     * Creates AbstractListViewProviderUI and initialize fields
     * 
     * @param viewProviderUIFactory
     * @param openInfo
     * @param provider
     * @param initResponse
     */
    protected AbstractListViewProviderUI(ViewProviderUIManager viewProviderUIFactory, ViewProviderOpenInfoDU openInfo, ViewProvider provider, ViewProviderInitResponse initResponse) {
        super(viewProviderUIFactory, openInfo, provider, initResponse);
        dataNavigation = new ListViewproviderUINavigationImpl(this);
    }

    /** List view results for current data */
    protected ListViewResultsInfo listViewResultsInfo;

    @Override
    public void initData() {
        ListViewPagingSize pageSize = this.getViewProviderSettings().getPageSize();
        if (pageSize == null) {
            pageSize = RelativeSize.MAX_ROWS_PER_PAGE;
            ListViewProviderSettings settings = getViewProviderPreferences().getViewProviderSettings().createBuilderWithSameValues().setPageSize(pageSize).build();
            this.viewProviderPreferences = getViewProviderPreferences().createBuilderWithSameValues().setListViewProviderSettings(settings).build();
            this.rowsPerPage = computeRowsPerPage(pageSize);
        }
        if (rowsPerPage == 0) {
            rowsPerPage = computeRowsPerPage(pageSize);
        }
        clientUI.executeCommand(getRequestCommandsExecutor(), createInitDataCmd(), commandExecutionEnv, new MethodCommandEvent(getClass(), "initData"));
    }

    /**
     * @param pageSize
     * @return new pagesize
     */
    protected int computeRowsPerPage(ListViewPagingSize pageSize) {
        if (pageSize instanceof ConcreteSize) {
            return ((ConcreteSize) pageSize).getPageSize();
        }
        if (pageSize instanceof RelativeSize) {
            if (pageSize == RelativeSize.MAX_ROWS_PER_PAGE) {
                return computeMaxRowsPerPage();
            }
            if (pageSize == RelativeSize.HALF_PAGE_SIZE) {
                return computeMaxRowsPerPage() / 2;
            }
        }
        throw new IllegalArgumentException("Cannot compute rows count per page based on pageSize : " + pageSize);
    }

    @Override
    public final void appendData(ListViewResults results) {
        appendDataToUI(results);
    }

    @Override
    public final void appendDataFromStart(ListViewResults results) {
        appendDataFromStartToUI(results);
    }

    @Override
    public void updateData(ListViewResults results) {
        updateDataInUI(results);
    }

    /**
     * Will do real update in model and ui table
     * 
     * @param results
     */
    protected abstract void updateDataInUI(ListViewResults results);

    /**
     * Append data to UI (some table implementation)
     * 
     * @param results
     */
    protected abstract void appendDataToUI(ListViewResults results);

    /**
     * Appends data to start to UI
     * 
     * @param results
     */
    protected abstract void appendDataFromStartToUI(ListViewResults results);

    /**
     * Clears all data
     */
    protected abstract void clearData();

    @Override
    public ListViewProviderInfo getViewProviderInfo() {
        return (ListViewProviderInfo) viewProviderInfo;
    }

    @Override
    public ListViewProviderSettings getViewProviderSettings() {
        return getViewProviderPreferences().getViewProviderSettings();
    }

    @Override
    public ListViewProvider getViewProvider() {
        return (ListViewProvider) provider;
    }

    /**
     * Command that reads data from server
     * 
     * @author kitko
     *
     */
    protected abstract class ReadRequestCommandBase extends AbstractRequestCommand<ListViewResults> {

        private ReadRequestCommandBase() {
            super(getRequestCommandsExecutor(), AbstractListViewProviderUI.this.getCommandExecutor());
        }

        /** Last size of data */
        protected int lastDataSize;

        @Override
        protected boolean executeBeforeRequest(CommandExecutionEnv env, CommandEvent event) {
            lastDataSize = getAllShownDataSize();
            getFrame().setEnabledUI(false);
            clearData();
            return true;
        }

        @Override
        protected void executeAfterRequest(ListViewResults results, CommandExecutionEnv env, CommandEvent event) {
            getFrame().setEnabledUI(true);
            lastReadData = results;
            appendData(results);
            lastDataSize = getAllShownDataSize();
        }

        @Override
        protected RequestCommandExceptionExecutionPolicy executeExceptionHandling(Exception e, CommandExecutionEnv env, CommandEvent event) {
            clientUI.showExceptionDialog(e, "Error getting data from provider");
            getFrame().setEnabledUI(true);
            return RequestCommandExceptionExecutionPolicy.ABORT;
        }
    }

    /**
     * Creates init data command
     * 
     * @return init data command
     */
    protected Command<ListViewResults> createInitDataCmd() {
        AbstractRequestCommand<ListViewResults> initDataCmd = new ReadRequestCommandBase() {
            @SuppressWarnings("unchecked")
            @Override
            protected ListViewResults executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
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
                ReadDataRequestContent.Builder readDataBuilder = new ReadDataRequestContent.Builder();
                readDataBuilder.setOffset(0);
                readDataBuilder.setCount(rowsPerPage);
                readDataBuilder.setLastCallerItem(lastCallerItem);
                readDataBuilder.setListViewSettings(getViewProviderSettings());
                readDataBuilder.setReadingFrom(ReadingFrom.FROM_START);
                ListViewProviderOpenResultsAndReadDataRequest request = new ListViewProviderOpenResultsAndReadDataRequest(readDataBuilder.build());
                ListViewResultsWithInfo openResultsWithFirstData = ((ListViewProvider) provider).openResultsAndReadData(request);
                listViewResultsInfo = openResultsWithFirstData.getInfo();
                ListViewResults results = openResultsWithFirstData.getResults();
                return results;
            }
        };
        return initDataCmd;
    }

    /**
     * Creates Read first data command
     * 
     * @return command that can request fisrt data from server
     */
    protected Command<ListViewResults> createReadFirstCmd() {
        AbstractRequestCommand<ListViewResults> readDataCmd = new ReadRequestCommandBase() {
            @Override
            protected ListViewResults executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ListViewResults results = ((ListViewProvider) provider).readFirst(rowsPerPage);
                return results;
            }
        };
        return readDataCmd;
    }

    /**
     * Creates command that will read next data from server
     * 
     * @return command that reads next data from server
     */
    protected Command<ListViewResults> createReadNextCmd() {
        AbstractRequestCommand<ListViewResults> readNextCmd = new ReadRequestCommandBase() {
            @Override
            protected ListViewResults executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ListViewResults results = ((ListViewProvider) provider).readNext(rowsPerPage);
                return results;
            }
        };
        return readNextCmd;
    }

    /**
     * Creates command that reads prev data from server
     * 
     * @return command that reads prev data from server
     */
    protected Command<ListViewResults> createReadPrevCmd() {
        AbstractRequestCommand<ListViewResults> readPrevCmd = new ReadRequestCommandBase() {
            @Override
            protected ListViewResults executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ListViewResults results = ((ListViewProvider) provider).readNext((rowsPerPage + lastDataSize) * (-1), rowsPerPage);
                return results;
            }
        };
        return readPrevCmd;
    }

    /**
     * Creates command that reads last data
     * 
     * @return command that can read last data
     */
    protected Command<ListViewResults> createReadLastCmd() {
        AbstractRequestCommand<ListViewResults> readLastCmd = new ReadRequestCommandBase() {
            @Override
            protected ListViewResults executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ListViewResults results = ((ListViewProvider) provider).readLast(rowsPerPage);
                return results;
            }
        };
        return readLastCmd;
    }

    /**
     * Create Read data with size and offset
     * 
     * @param count
     * @param offset
     * @param readingFrom
     * @return comamnd
     */
    protected Command<ListViewResults> createReadDataCmd(int count, int offset, ReadingFrom readingFrom) {
        AbstractRequestCommand<ListViewResults> readDataCmd = new ReadRequestCommandBase() {
            @Override
            protected ListViewResults executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                ListViewResults results = ((ListViewProvider) provider).read(count, offset, readingFrom);
                return results;
            }
        };
        return readDataCmd;
    }

    /**
     * Computes rows that user should see on one page This is paging count from
     * client to server side If not all rows can be displayed, table will use
     * scrollbar
     * 
     * @return paging client server rows count
     */
    protected abstract int computeMaxRowsPerPage();

    @Override
    public ListViewResultsInfo getCurrentListViewResultsInfo() {
        return listViewResultsInfo;
    }

    @Override
    public void refreshData() {
        initData();
    }

    @Override
    protected ListViewProviderUIListenersSupportWithFire createListenersSupport() {
        return new ListViewProviderUIListenersSupportImpl(this);
    }

    @Override
    public ListViewProviderUIListenersSupportWithFire getListenersSupport() {
        return (ListViewProviderUIListenersSupportWithFire) listenersSupport;
    }

    @Override
    public void addChild(ViewProviderUI child) {
        super.addChild(child);
        if (refreshChildrenListener == null) {
            refreshChildrenListener = new ListSelectionListener() {
                private static final long serialVersionUID = 2717733500835645863L;

                @Override
                public void selectionChanged(ListViewProviderUI ui, int[] selectedRows) {
                    if (ui.getCurrentData() != null) {
                        for (ViewProviderID childId : children) {
                            ViewProviderUI child = viewProviderUIManager.getViewProviderUI(childId);
                            child.refreshData();
                        }
                    }
                }

            };
            getListenersSupport().addListViewSelectionListener(refreshChildrenListener);
        }
    }

    @Override
    protected void removeChild(ViewProviderUI ui) {
        super.removeChild(ui);
        if (children == null || children.isEmpty()) {
            if (refreshChildrenListener != null) {
                getListenersSupport().removeListener(ListSelectionListener.class, refreshChildrenListener);
                refreshChildrenListener = null;
            }
        }
    }

    @Override
    public void rebuildUI(ContainerAC rootContainer) {
        throw new UnsupportedOperationException("Cannot rebuild ui for list");
    }

    /**
     * Completes fetch open. It will send command with current row to detail that
     * fired fetch
     */
    @SuppressWarnings("unchecked")
    protected void completeFetch() {
        final GenericViewProviderUI detail = (GenericViewProviderUI) viewProviderUIManager.getViewProviderUI(callerProviderContext.getLastCaller().getProviderId());
        FetchOpenInfo openInfo = (FetchOpenInfo) this.openInfo;
        ComponentsGroupsDef components = detail.getCurrentData();
        ListViewResultsWithInfo currentData = getCurrentData();
        final Command completeFetchCommand = new CommandCall(new CompleteFetchWithListViewResultsWithInfoCall(openInfo.getFetchDef().getFetchId(), openInfo.getFetchField(), currentData, components));
        AbstractCallServerCommandBase call = new AbstractCallServerCommandBase<Object>() {
            @Override
            protected ResultWithCommands<Object> executeRequest(CommandExecutor executor, CommandExecutionEnv env, CommandEvent event) throws Exception {
                Object result = detail.getViewProvider().executeCommand(completeFetchCommand, null, event).get();
                if (result instanceof ResultWithCommands) {
                    return (ResultWithCommands<Object>) result;
                }
                return null;
            }

            @Override
            protected void executeAfterRequest(Object results, CommandExecutionEnv env, CommandEvent event) {
                super.executeAfterRequest(results, env, event);
                dispose();
            }

        };
        clientUI.executeCommand(detail.getRequestCommandsExecutor(), call, detail.getCommandExecutionEnv(), null);

    }

    /**
     * Toggle sorting by column. If append, append sorting item. If we already sort
     * ASC, then sort DESC, if DESC witch off sort by this column
     * 
     * @param columnId
     * @param append
     * @param settings
     * @return updated settings or null if nothing changed
     */
    protected ListViewProviderSettings toggleSortColumn(String columnId, boolean append, ListViewProviderSettings settings) {
        // check if sorting is possible
        if (getViewProviderInfo().getSortingOptionsFactory() == null) {
            return null;
        }
        SortingOptions sortingOptions = getViewProviderInfo().getSortingOptionsFactory().createSortingOptions(getViewProviderInfo());
        if (sortingOptions == null || !sortingOptions.canDefineCustomSort() || !sortingOptions.canSortByColumn(columnId)) {
            return null;
        }
        Sort userSort = settings.getUserSort();
        if (userSort == null) {
            userSort = new Sort(Arrays.asList(new SortItem(columnId)));
        }
        else {
            List<SortItem> items = append ? new ArrayList<SortItem>(userSort.getItems()) : new ArrayList<SortItem>();
            SortDirection newDirection = SortDirection.ASC;
            for (SortItem item : userSort.getItems()) {
                if (columnId.equals(item.getColumnId())) {
                    if (SortDirection.ASC.equals(item.getDirection())) {
                        newDirection = SortDirection.DESC;
                    }
                    else {
                        newDirection = null;
                    }
                }
            }
            if (append) {
                items.remove(new SortItem(columnId, SortDirection.ASC));
                items.remove(new SortItem(columnId, SortDirection.DESC));
            }
            if (newDirection != null) {
                items.add(new SortItem(columnId, newDirection));
            }
            userSort = new Sort(items);
        }
        return new ListViewProviderSettings.Builder().setValues(settings).setUserSort(userSort).build();
    }

    @Override
    public void fireFetch(String id) {
        throw new UnsupportedOperationException("FireFetch not supported");
    }

    @Override
    public void completeFetch(String id) {
        throw new UnsupportedOperationException("CompleteFetch not supported");
    }

    @Override
    public void completeAutoComplete(String compId, AutoCompleteDataItem item) {
        throw new UnsupportedOperationException("completeAutoComplete not supported");
    }

    @Override
    protected void clearVariables() {
        this.dataNavigation = null;
        this.refreshChildrenListener = null;
        this.listViewResultsInfo = null;
        super.clearVariables();
    }

    @Override
    protected void setupListeners() {
        ListSelectionListener selectionListener = new ListSelectionListener() {
            private static final long serialVersionUID = 6457628544264416300L;

            @Override
            public void selectionChanged(ListViewProviderUI ui, int[] selectedRows) {
                ListViewResultsWithInfo listViewResultsWithInfo = getCurrentData();
                ObjectResolver resolver = getCommandExecutionEnv();
                EnvironmentDataChangeEvent changeEvent = new EnvironmentDataChangeEvent(resolver, listViewResultsWithInfo);
                getComponentsManager().fireEnvironmentDataChanged(changeEvent);
            }

        };
        getListenersSupport().addListViewSelectionListener(selectionListener);

        InitListener initListener = new InitListener() {
            private static final long serialVersionUID = 1653095923231318478L;

            @Override
            public void afterUiInit(ViewProviderUI ui) {
                ObjectResolver resolver = getCommandExecutionEnv();
                EnvironmentInitEvent event = new EnvironmentInitEvent(resolver);
                getComponentsManager().fireEnvironmentInitialized(event);
            }
        };
        getListenersSupport().addInitListener(initListener);

        UIStructureListener structureListener = new UIStructureListener() {
            private static final long serialVersionUID = -3249838298300418955L;

            @Override
            public void childAdded(ViewProviderUI ui, ViewProviderUI child) {
                ObjectResolver resolver = getCommandExecutionEnv();
                EnvironmentStructureChangeEvent event = new EnvironmentStructureChangeEvent(resolver);
                getComponentsManager().fireEnvironmentStructureChanged(event);
            }

            @Override
            public void childRemoved(ViewProviderUI ui, ViewProviderUI child) {
                ObjectResolver resolver = getCommandExecutionEnv();
                EnvironmentStructureChangeEvent event = new EnvironmentStructureChangeEvent(resolver);
                getComponentsManager().fireEnvironmentStructureChanged(event);
            }

        };
        getListenersSupport().addUIStructureListener(structureListener);

        ViewProviderLayoutListener viewProviderLayoutListener = new ViewProviderLayoutListener() {
            private static final long serialVersionUID = 8972870002974867520L;

            @Override
            public void layoutChanged(ViewProviderUI ui) {
                ObjectResolver resolver = getCommandExecutionEnv();
                EnvironmentLayoutChangeEvent event = new EnvironmentLayoutChangeEvent(resolver);
                getComponentsManager().fireEnvironmentLayoutChanged(event);
            }
        };
        getListenersSupport().addLayoutListener(viewProviderLayoutListener);
    }

    @Override
    public void applyRowsPerPage(ListViewPagingSize rowsPerPage) {
        if (rowsPerPage.equals(getViewProviderSettings().getPageSize())) {
            return;
        }
        ListViewProviderSettings settings = getViewProviderPreferences().getViewProviderSettings().createBuilderWithSameValues().setPageSize(rowsPerPage).build();
        this.viewProviderPreferences = getViewProviderPreferences().createBuilderWithSameValues().setListViewProviderSettings(settings).build();
        int size = computeRowsPerPage(rowsPerPage);
        if (this.rowsPerPage == size) {
            return;
        }
        this.rowsPerPage = size;
        if (lastReadData != null) {
            this.dataNavigation.readData(this.rowsPerPage, lastReadData.getOffset(), lastReadData.getReadingFrom());
        }
        else {
            this.dataNavigation.readFirstData();
        }
    }

    @Override
    public ListViewProviderPreferences getViewProviderPreferences() {
        return (ListViewProviderPreferences) viewProviderPreferences;
    }

    @Override
    protected ListViewProviderPreferences buildTemporalPreferences(ViewProviderSettings initSettings) {
        ListViewProviderPreferences.Builder builder = new ListViewProviderPreferences.Builder();
        builder.setViewProviderDef(viewProviderDef).setUuid(UUID.randomUUID()).setTemporal(true).setReadOnly(false).setName("Temporal settings").setDescription("List temporal preferences");
        builder.setCreator(getClient().getSession().getUser()).setContext(getClient().getSession().getApplContext()).setCreationTime(new Timestamp(System.currentTimeMillis()));
        builder.setListViewProviderSettings((ListViewProviderSettings) initSettings);
        return builder.build();
    }

}
