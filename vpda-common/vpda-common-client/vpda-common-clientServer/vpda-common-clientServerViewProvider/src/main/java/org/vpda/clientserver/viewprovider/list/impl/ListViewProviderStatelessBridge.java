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
package org.vpda.clientserver.viewprovider.list.impl;

import java.io.Serializable;

import org.vpda.clientserver.viewprovider.ViewProviderCallerItem;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderException;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInitData;
import org.vpda.clientserver.viewprovider.list.ListViewProvider;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitDataRequest;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitDataResponse;
import org.vpda.clientserver.viewprovider.list.ListViewProviderInitResponse;
import org.vpda.clientserver.viewprovider.list.ListViewProviderOpenResultsAndReadDataRequest;
import org.vpda.clientserver.viewprovider.list.ListViewProviderSettings;
import org.vpda.clientserver.viewprovider.list.ListViewResults;
import org.vpda.clientserver.viewprovider.list.ListViewResultsInfo;
import org.vpda.clientserver.viewprovider.list.ListViewResultsWithInfo;
import org.vpda.clientserver.viewprovider.list.ListViewRow;
import org.vpda.clientserver.viewprovider.list.ReadingFrom;
import org.vpda.clientserver.viewprovider.list.stateless.GetResultsInfoAndReadDataStatelessRequest;
import org.vpda.clientserver.viewprovider.list.stateless.GetResultsInfoDataStatelessRequest;
import org.vpda.clientserver.viewprovider.list.stateless.GetRowSpecificContextMenuStatelessRequest;
import org.vpda.clientserver.viewprovider.list.stateless.InitAndReadDataStatelessRequest;
import org.vpda.clientserver.viewprovider.list.stateless.ReadDataStatelessRequest;
import org.vpda.clientserver.viewprovider.list.stateless.ReadDataStatelessRequestContent;
import org.vpda.clientserver.viewprovider.list.stateless.StatelessListViewProviderServices;
import org.vpda.clientserver.viewprovider.stateless.ExecuteComamndStatelessRequest;
import org.vpda.clientserver.viewprovider.stateless.InitStatelessRequest;
import org.vpda.common.command.Command;
import org.vpda.common.command.CommandEvent;
import org.vpda.common.command.CommandExecutionEnv;
import org.vpda.common.command.CommandExecutor;
import org.vpda.common.command.ExecutionResult;
import org.vpda.common.comps.ui.menu.ContextMenuAC;
import org.vpda.common.service.ServiceInfo;
import org.vpda.common.service.ServiceKind;
import org.vpda.internal.common.util.Assert;

/**
 * Bridge of {@link ListViewProvider} using
 * {@link StatelessListViewProviderServices}
 * 
 * @author kitko
 *
 */
@ServiceInfo(kind = ServiceKind.Stateless, clientExportTypes = { ListViewProvider.class })
public final class ListViewProviderStatelessBridge implements ListViewProvider, Serializable {
    private static final long serialVersionUID = -2850351354667194545L;
    private final StatelessListViewProviderServices statelessListViewProviderServices;
    private final ViewProviderDef def;
    private ViewProviderCallerItem lastCallerItem;
    private ListViewProviderSettings listViewSettings;
    private ViewProviderInitData initData;
    private ViewProviderID initProviderID;
    private Direction direction;
    private int position;

    private static enum Direction {
        FROM_START, FROM_END;
    }

    /**
     * Creates ListViewProviderStatelessBridge
     * 
     * @param def
     * @param statelessListViewProviderServices
     */
    public ListViewProviderStatelessBridge(ViewProviderDef def, StatelessListViewProviderServices statelessListViewProviderServices) {
        this(def, statelessListViewProviderServices, null, null);
    }

    /**
     * Creates ListViewProviderStatelessBridge
     * 
     * @param def
     * @param statelessListViewProviderServices
     * @param initData
     * @param initResponse
     */
    public ListViewProviderStatelessBridge(ViewProviderDef def, StatelessListViewProviderServices statelessListViewProviderServices, ViewProviderInitData initData,
            ListViewProviderInitResponse initResponse) {
        this.statelessListViewProviderServices = Assert.isNotNullArgument(statelessListViewProviderServices, "statelessListViewProviderServices");
        this.def = Assert.isNotNullArgument(def, "def");
        this.direction = Direction.FROM_START;
        this.initData = initData;
        if (initResponse != null) {
            this.initProviderID = initResponse.getViewProviderInfo().getViewProviderID();
        }
    }

    @Override
    public void close() {
    }

    @Override
    public <T> ExecutionResult<T> executeCommand(Command<? extends T> command, CommandExecutionEnv env, CommandEvent event) throws Exception {
        @SuppressWarnings("unchecked")
        ExecuteComamndStatelessRequest.Builder<T> builder = new ExecuteComamndStatelessRequest.Builder<T>().setCommand(command).setEnv(env).setEvent(event).setInitData(initData);
        builder.setDef(def);
        builder.setProviderId(initProviderID);
        ExecuteComamndStatelessRequest<T> request = builder.build();
        return statelessListViewProviderServices.executeCommand(request);
    }

    @Override
    public String getExecutorId() {
        return System.identityHashCode(this) + "";
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return this;
    }

    @Override
    public void unreferenced() {
    }

    @Override
    public ListViewResults readNext(int count) throws ViewProviderException {
        checkInitAndOpenResultsCalled();
        ReadDataStatelessRequest.Builder requestBuilder = new ReadDataStatelessRequest.Builder();
        requestBuilder.setCount(count);
        requestBuilder.setDef(def);
        requestBuilder.setOffset(position);
        requestBuilder.setInitData(initData);
        requestBuilder.setListViewSettings(listViewSettings);
        requestBuilder.setLastCallerItem(lastCallerItem);
        requestBuilder.setProviderId(this.initProviderID);

        ListViewResultsWithInfo results = null;
        if (Direction.FROM_START.equals(direction)) {
            requestBuilder.setReadingFrom(ReadingFrom.FROM_START);
            results = statelessListViewProviderServices.read(requestBuilder.build());
            position = position + results.getRowsCount();
        }
        else {
            requestBuilder.setReadingFrom(ReadingFrom.FROM_END);
            results = statelessListViewProviderServices.read(requestBuilder.build());
            position = position - results.getRowsCount();
        }
        return results.getResults();
    }

    @Override
    public ListViewResults readNext(int move, int count) throws ViewProviderException {
        checkInitAndOpenResultsCalled();
        ReadDataStatelessRequest.Builder requestBuilder = new ReadDataStatelessRequest.Builder();
        requestBuilder.setCount(count);
        requestBuilder.setDef(def);
        requestBuilder.setInitData(initData);
        requestBuilder.setListViewSettings(listViewSettings);
        requestBuilder.setLastCallerItem(lastCallerItem);
        requestBuilder.setProviderId(this.initProviderID);

        ListViewResultsWithInfo results = null;
        if (Direction.FROM_START.equals(direction)) {
            requestBuilder.setReadingFrom(ReadingFrom.FROM_START);
            position = position + move;
            requestBuilder.setOffset(position);
            results = statelessListViewProviderServices.read(requestBuilder.build());
            position = position + results.getRowsCount();
        }
        else {
            requestBuilder.setReadingFrom(ReadingFrom.FROM_START);
            position = position - move;
            requestBuilder.setOffset(position);
            results = statelessListViewProviderServices.read(requestBuilder.build());
            position = position - results.getRowsCount();
        }
        return results.getResults();
    }

    @Override
    public ListViewResults readFirst(int count) throws ViewProviderException {
        position = 0;
        direction = Direction.FROM_START;
        checkInitAndOpenResultsCalled();
        ReadDataStatelessRequest.Builder requestBuilder = new ReadDataStatelessRequest.Builder();
        requestBuilder.setCount(count);
        requestBuilder.setDef(def);
        requestBuilder.setOffset(0);
        requestBuilder.setInitData(initData);
        requestBuilder.setListViewSettings(listViewSettings);
        requestBuilder.setLastCallerItem(lastCallerItem);
        requestBuilder.setProviderId(this.initProviderID);
        ListViewResultsWithInfo results = statelessListViewProviderServices.read(requestBuilder.build());
        position = results.getRowsCount();
        return results.getResults();
    }

    @Override
    public ListViewResults readLast(int count) throws ViewProviderException {
        position = 0;
        direction = Direction.FROM_END;
        checkInitAndOpenResultsCalled();
        ReadDataStatelessRequest.Builder requestBuilder = new ReadDataStatelessRequest.Builder();
        requestBuilder.setCount(count);
        requestBuilder.setDef(def);
        requestBuilder.setOffset(0);
        requestBuilder.setInitData(initData);
        requestBuilder.setListViewSettings(listViewSettings);
        requestBuilder.setLastCallerItem(lastCallerItem);
        requestBuilder.setProviderId(this.initProviderID);
        requestBuilder.setReadingFrom(ReadingFrom.FROM_END);
        ListViewResultsWithInfo results = statelessListViewProviderServices.read(requestBuilder.build());
        position = position + results.getRowsCount();
        return results.getResults();
    }

    @Override
    public ListViewResults read(int count, int offset) throws ViewProviderException {
        return read(count, offset, ReadingFrom.FROM_START);
    }

    @Override
    public ListViewResults readLast(int count, int offset) throws ViewProviderException {
        return read(count, offset, ReadingFrom.FROM_END);
    }

    @Override
    public ListViewResults read(int count, int offset, ReadingFrom readingFrom) throws ViewProviderException {
        checkInitAndOpenResultsCalled();
        ReadDataStatelessRequest.Builder requestBuilder = new ReadDataStatelessRequest.Builder();
        requestBuilder.setCount(count);
        requestBuilder.setDef(def);
        requestBuilder.setOffset(offset);
        requestBuilder.setInitData(initData);
        requestBuilder.setListViewSettings(listViewSettings);
        requestBuilder.setLastCallerItem(lastCallerItem);
        requestBuilder.setProviderId(this.initProviderID);
        requestBuilder.setReadingFrom(readingFrom);
        ListViewResultsWithInfo results = statelessListViewProviderServices.read(requestBuilder.build());
        direction = ReadingFrom.FROM_START.equals(readingFrom) ? Direction.FROM_START : Direction.FROM_END;
        position = offset + results.getRowsCount();
        return results.getResults();
    }

    @Override
    public ListViewProviderInitResponse initProvider(ViewProviderInitData initData) throws ViewProviderException {
        this.initData = null;
        this.lastCallerItem = null;
        this.listViewSettings = null;
        InitStatelessRequest.Builder builder = new InitStatelessRequest.Builder();
        builder.setDef(def);
        builder.setInitData(initData);
        ListViewProviderInitResponse listViewProviderInitResponse = statelessListViewProviderServices.getProviderInfo(builder.build());
        this.initData = initData;
        this.initProviderID = listViewProviderInitResponse.getViewProviderInfo().getViewProviderID();
        return listViewProviderInitResponse;
    }

    @Override
    public ListViewResultsInfo openResults(ViewProviderCallerItem lastCallerItem, ListViewProviderSettings listViewSettings) throws IllegalStateException, ViewProviderException {
        this.lastCallerItem = null;
        this.listViewSettings = null;
        checkInitCalled();
        GetResultsInfoDataStatelessRequest.Builder builder = new GetResultsInfoDataStatelessRequest.Builder();
        builder.setDef(def);
        builder.setLastCallerItem(lastCallerItem);
        builder.setListViewSettings(listViewSettings);
        builder.setInitData(initData);
        builder.setProviderId(this.initProviderID);
        ListViewResultsInfo resultsInfo = statelessListViewProviderServices.getResultsInfo(builder.build());
        this.lastCallerItem = lastCallerItem;
        this.listViewSettings = listViewSettings;
        return resultsInfo;
    }

    private void checkInitCalled() {
        Assert.isNotNull(initData, "Init not called");
    }

    private void checkInitAndOpenResultsCalled() {
        checkInitCalled();
        Assert.isNotNull(listViewSettings, "Open Results not called");
    }

    @Override
    public ContextMenuAC getRowSpecificContextMenu(ListViewRow row) throws ViewProviderException {
        checkInitCalled();
        GetRowSpecificContextMenuStatelessRequest.Builder builder = new GetRowSpecificContextMenuStatelessRequest.Builder();
        builder.setDef(def);
        builder.setLastCallerItem(lastCallerItem);
        builder.setListViewSettings(listViewSettings);
        builder.setInitData(initData);
        builder.setProviderId(this.initProviderID);
        builder.setRow(row);
        ContextMenuAC rowSpecificContextMenu = statelessListViewProviderServices.getRowSpecificContextMenu(builder.build());
        return rowSpecificContextMenu;
    }

    @Override
    public long getRowsCount(ViewProviderCallerItem lastCallerItem, ListViewProviderSettings listViewSettings) throws ViewProviderException {
        checkInitCalled();
        GetResultsInfoDataStatelessRequest.Builder builder = new GetResultsInfoDataStatelessRequest.Builder();
        builder.setDef(def);
        builder.setLastCallerItem(lastCallerItem);
        builder.setListViewSettings(listViewSettings);
        builder.setInitData(initData);
        builder.setProviderId(this.initProviderID);
        long count = statelessListViewProviderServices.getRowsCount(builder.build());
        return count;

    }

    @Override
    public ListViewProviderInitDataResponse initAndReadData(ListViewProviderInitDataRequest request) throws ViewProviderException {
        this.initData = null;
        this.lastCallerItem = null;
        this.listViewSettings = null;

        InitAndReadDataStatelessRequest.Builder reqBuilder = new InitAndReadDataStatelessRequest.Builder();
        reqBuilder.setDef(def);
        reqBuilder.setInitData(request.getInitData());
        ReadDataStatelessRequestContent readRequestcontent = new ReadDataStatelessRequestContent.Builder().setCount(request.getCount()).setInitData(request.getInitData())
                .setLastCallerItem(request.getLastCallerItem()).setListViewSettings(request.getListViewSettings()).setOffset(request.getOffset()).setReadingFrom(request.getReadingFrom()).build();
        reqBuilder.setReadDataRequest(readRequestcontent);
        ListViewProviderInitDataResponse response = statelessListViewProviderServices.initAndRead(reqBuilder.build());
        this.initData = request.getInitData();
        this.initProviderID = response.getInitResponse().getViewProviderInfo().getViewProviderID();
        this.lastCallerItem = request.getLastCallerItem();
        this.listViewSettings = request.getListViewSettings();
        int offset = request.getOffset();
        if (response.getResultsWithInfo().getResults().getOffset() > 0) {
            offset = response.getResultsWithInfo().getResults().getOffset();
        }
        position = offset + response.getResultsWithInfo().getRowsCount();
        return response;
    }

    @Override
    public ListViewResultsWithInfo openResultsAndReadData(ListViewProviderOpenResultsAndReadDataRequest request) throws IllegalStateException, ViewProviderException {
        this.lastCallerItem = null;
        this.listViewSettings = null;

        GetResultsInfoAndReadDataStatelessRequest.Builder builder = new GetResultsInfoAndReadDataStatelessRequest.Builder();
        builder.setDef(def);
        builder.setInitData(this.initData);
        builder.setProviderId(this.initProviderID);

        ReadDataStatelessRequestContent readRequestcontent = new ReadDataStatelessRequestContent.Builder().setCount(request.getCount()).setInitData(initData)
                .setLastCallerItem(request.getLastCallerItem()).setListViewSettings(request.getListViewSettings()).setOffset(request.getOffset()).setReadingFrom(request.getReadingFrom()).build();

        builder.setReadDataRequestContent(readRequestcontent);
        ListViewResultsWithInfo response = statelessListViewProviderServices.getResultsInfoAndRead(builder.build());
        this.lastCallerItem = request.getLastCallerItem();
        this.listViewSettings = request.getListViewSettings();
        int offset = request.getOffset();
        if (response.getResults().getOffset() > 0) {
            offset = response.getResults().getOffset();
        }
        position = offset + response.getRowsCount();
        return response;
    }

}
