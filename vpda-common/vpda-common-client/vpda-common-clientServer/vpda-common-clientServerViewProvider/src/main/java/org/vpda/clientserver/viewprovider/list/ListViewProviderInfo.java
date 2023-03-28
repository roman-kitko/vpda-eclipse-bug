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
package org.vpda.clientserver.viewprovider.list;

import java.util.Collections;
import java.util.List;

import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderInfo;
import org.vpda.clientserver.viewprovider.criteria.CriteriaOptions;
import org.vpda.common.comps.ui.menu.ContextMenuAC;
import org.vpda.common.criteria.sort.SortingOptions;
import org.vpda.internal.common.util.Assert;

/**
 * Init info for list view provider
 * 
 * @author kitko
 *
 */
public final class ListViewProviderInfo extends ViewProviderInfo {
    private static final long serialVersionUID = 7828199039028697533L;
    private final ColumnsGroupsDef groupsListViewDef;
    private final CriteriaOptionsFactory genericSearchingOptionsFactory;
    private final SortingOptionsFactory sortingOptionsFactory;
    private final ViewProviderDef customSearchingDetail;
    private final ContextMenuAC staticContextMenu;
    private final boolean supportDynamicContextMenu;

    /**
     * @return columns
     */
    public List<Column> getColumns() {
        return Collections.unmodifiableList(groupsListViewDef.getColumns());
    }

    @Override
    public ViewProviderID getViewProviderID() {
        return viewProviderID;
    }

    /**
     * Creates ListViewProviderInfoImpl
     */
    private ListViewProviderInfo(Builder builder) {
        super(builder);
        this.customSearchingDetail = builder.customSearchingDetail;
        this.sortingOptionsFactory = builder.sortingOptionsFactory;
        this.genericSearchingOptionsFactory = builder.genericSearchingOptions;
        this.groupsListViewDef = Assert.isNotNullArgument(builder.clientGroupsListViewDef, "clientGroupsListViewDef");
        this.staticContextMenu = builder.getStaticContextMenu();
        this.supportDynamicContextMenu = builder.supportDynamicContextMenu();
    }

    /**
     * @return the staticContextMenu
     */
    public ContextMenuAC getStaticContextMenu() {
        return staticContextMenu;
    }

    /**
     * @return the supportDynamicContextMenu
     */
    public boolean supportDynamicContextMenu() {
        return supportDynamicContextMenu;
    }

    /**
     * @param groupId
     * @return column group
     */
    public ColumnsGroup getColumnGroup(String groupId) {
        return groupsListViewDef.getColumnGroup(groupId);
    }

    /**
     * @return list of group ids
     */
    public List<String> getColumnGroupIds() {
        return groupsListViewDef.getColumnGroupIds();
    }

    /**
     * @param columnId
     * @return ClientColumnInfo by col id
     */
    public Column getColumn(String columnId) {
        return groupsListViewDef.getColumn(columnId);
    }

    /**
     * @return list of all column ids
     */
    public List<String> getColumnIds() {
        return groupsListViewDef.getColumnsIds();
    }

    /**
     * @return criteria options
     */
    public CriteriaOptionsFactory getGenericSearchingOptionsFactory() {
        return genericSearchingOptionsFactory;
    }

    /**
     * @return CriteriaOptions
     */
    public CriteriaOptions createGenericSearchingOptions() {
        return genericSearchingOptionsFactory != null ? genericSearchingOptionsFactory.createSearchingOptions(this) : null;
    }

    /**
     * @return sorting options
     */
    public SortingOptionsFactory getSortingOptionsFactory() {
        return sortingOptionsFactory;
    }

    /**
     * @return SortingOptions
     */
    public SortingOptions createSortingOptions() {
        return sortingOptionsFactory != null ? sortingOptionsFactory.createSortingOptions(this) : null;
    }

    /**
     * @return custom searching detail
     */
    public ViewProviderDef getCustomSearchingDetail() {
        return customSearchingDetail;
    }

    /**
     * @return ClientGroupsListViewDef
     */
    public ColumnsGroupsDef getClientGroupsListViewDef() {
        return this.groupsListViewDef;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T extends ViewProviderInfo> ViewProviderInfo.Builder<T> createBuilder() {
        return (ViewProviderInfo.Builder<T>) new Builder();
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ListViewProviderInfo [groupsListViewDef=");
        builder2.append(groupsListViewDef);
        builder2.append(", genericSearchingOptionsFactory=");
        builder2.append(genericSearchingOptionsFactory);
        builder2.append(", sortingOptionsFactory=");
        builder2.append(sortingOptionsFactory);
        builder2.append(", customSearchingDetail=");
        builder2.append(customSearchingDetail);
        builder2.append(", viewProviderID=");
        builder2.append(viewProviderID);
        builder2.append(", rootViewProviderComponentContainer=");
        builder2.append(rootViewProviderComponentContainer);
        builder2.append(", locValue=");
        builder2.append(locValue);
        builder2.append("]");
        return builder2.toString();
    }

    /**
     * Builder for ListViewProviderInfoImpl
     * 
     * @author kitko
     *
     */
    public final static class Builder extends ViewProviderInfo.Builder<ListViewProviderInfo> {
        private ColumnsGroupsDef clientGroupsListViewDef;
        private CriteriaOptionsFactory genericSearchingOptions;
        private SortingOptionsFactory sortingOptionsFactory;
        private ViewProviderDef customSearchingDetail;
        private ContextMenuAC staticContextMenu;
        private boolean supportDynamicContextMenu;

        /**
         * Creates Empty builder
         */
        public Builder() {
            supportDynamicContextMenu = false;
        }

        @Override
        public Builder setValues(ListViewProviderInfo info) {
            super.setValues(info);
            this.clientGroupsListViewDef = info.getClientGroupsListViewDef();
            this.genericSearchingOptions = info.getGenericSearchingOptionsFactory();
            this.genericSearchingOptions = info.getGenericSearchingOptionsFactory();
            this.sortingOptionsFactory = info.getSortingOptionsFactory();
            this.customSearchingDetail = info.getCustomSearchingDetail();
            this.staticContextMenu = info.getStaticContextMenu();
            this.supportDynamicContextMenu = info.supportDynamicContextMenu();
            return this;
        }

        /**
         * @return the staticContextMenu
         */
        public ContextMenuAC getStaticContextMenu() {
            return staticContextMenu;
        }

        /**
         * @param staticContextMenu the staticContextMenu to set
         * @return this
         */
        public Builder setStaticContextMenu(ContextMenuAC staticContextMenu) {
            this.staticContextMenu = staticContextMenu;
            return this;
        }

        /**
         * @return the supportDynamicContextMenu
         */
        public boolean supportDynamicContextMenu() {
            return supportDynamicContextMenu;
        }

        /**
         * @param supportDynamicContextMenu the supportDynamicContextMenu to set
         * @return this
         */
        public Builder setSupportDynamicContextMenu(boolean supportDynamicContextMenu) {
            this.supportDynamicContextMenu = supportDynamicContextMenu;
            return this;
        }

        /**
         * @return ClientGroupsListViewDef
         */
        public ColumnsGroupsDef getClientGroupsListViewDef() {
            return clientGroupsListViewDef;
        }

        /**
         * @param clientGroupsListViewDef
         * @return this
         */
        public Builder setClientGroupsListViewDef(ColumnsGroupsDef clientGroupsListViewDef) {
            this.clientGroupsListViewDef = clientGroupsListViewDef;
            return this;
        }

        /**
         * @return genericSearchingOptions
         */
        public CriteriaOptionsFactory getGenericSearchingOptions() {
            return genericSearchingOptions;
        }

        /**
         * @param genericSearchingOptions
         * @return this
         */
        public Builder setGenericSearchingOptions(CriteriaOptionsFactory genericSearchingOptions) {
            this.genericSearchingOptions = genericSearchingOptions;
            return this;
        }

        /**
         * @return sortingOptionsFactory
         */
        public SortingOptionsFactory getSortingOptionsFactory() {
            return sortingOptionsFactory;
        }

        /**
         * @param sortingOptionsFactory
         * @return this
         */
        public Builder setSortingOptionsFactory(SortingOptionsFactory sortingOptionsFactory) {
            this.sortingOptionsFactory = sortingOptionsFactory;
            return this;
        }

        /**
         * @return customSearchingDetail
         */
        public ViewProviderDef getCustomSearchingDetail() {
            return customSearchingDetail;
        }

        /**
         * @param customSearchingDetail
         * @return this
         */
        public Builder setCustomSearchingDetail(ViewProviderDef customSearchingDetail) {
            this.customSearchingDetail = customSearchingDetail;
            return this;
        }

        /**
         * Builds info
         * 
         * @return new created info
         */
        @Override
        public ListViewProviderInfo build() {
            return new ListViewProviderInfo(this);
        }

        @Override
        public Class<ListViewProviderInfo> getTargetClass() {
            return ListViewProviderInfo.class;
        }

    }

}
