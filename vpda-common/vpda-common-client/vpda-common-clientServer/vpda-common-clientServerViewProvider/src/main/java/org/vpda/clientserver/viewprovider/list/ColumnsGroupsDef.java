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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.comps.MemberContainer;
import org.vpda.common.comps.MemberListenerSupportImpl;
import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.internal.common.util.OrderedMap;

/**
 * List of {@link ColumnsGroup}
 * 
 * @author kitko
 *
 */
@Immutable
public final class ColumnsGroupsDef implements Serializable, MemberContainer<ColumnsGroup> {
    private static final long serialVersionUID = 7252243739959644655L;
    private final List<ColumnsGroup> columnGroups;
    private final GroupsListViewDefLocalizer localizer;
    private transient OrderedMap<String, ColumnsGroup> allGroups;
    private transient OrderedMap<String, Column> allColumns;
    private MemberListenerSupportWithFireForSingleMember memberListenerSupport;

    /**
     * Creates def
     * 
     * @param groups
     */
    public ColumnsGroupsDef(Collection<? extends ColumnsGroup> groups) {
        columnGroups = new ArrayList<>(groups);
        this.localizer = null;
    }

    /**
     * Creates def with groups
     * 
     * @param groups
     */
    public ColumnsGroupsDef(ColumnsGroup... groups) {
        columnGroups = new ArrayList<>(Arrays.asList(groups));
        this.localizer = null;
    }

    private ColumnsGroupsDef(Builder builder) {
        this.localizer = builder.localizer;
        this.columnGroups = new ArrayList<>(builder.columnGroups.values());
    }

    @Override
    public MemberListenerSupportWithFireForSingleMember getMemberListenerSupport() {
        if (memberListenerSupport == null) {
            memberListenerSupport = new MemberListenerSupportImpl(this);
        }
        return memberListenerSupport;
    }

    private void initAllColumnsAndGroups() {
        if (allGroups != null) {
            return;
        }
        allGroups = new OrderedMap<>();
        allColumns = new OrderedMap<>();
        for (ColumnsGroup group : columnGroups) {
            allGroups.put(group.getId(), group);
            for (ColumnsGroup innerGroup : group.getAllGroups()) {
                allGroups.put(innerGroup.getId(), innerGroup);
            }
            for (Column col : group.getAllColumns()) {
                allColumns.put(col.getId(), col);
            }
        }
    }

    /**
     * @return the localizer
     */
    public GroupsListViewDefLocalizer getLocalizer() {
        return localizer;
    }

    /**
     * @return list of joined column groups
     */
    public List<ColumnsGroup> getColumnGroups() {
        initAllColumnsAndGroups();
        return Collections.unmodifiableList(allGroups.values());
    }

    /**
     * 
     * @return list of all columns ids of all groups
     */
    public List<String> getColumnsIds() {
        initAllColumnsAndGroups();
        List<String> result = new ArrayList<String>();
        for (Column col : allColumns.values()) {
            result.add(col.getId());
        }
        return result;
    }

    /**
     * @return list of all columns of all groups
     */
    public List<Column> getColumns() {
        initAllColumnsAndGroups();
        return Collections.unmodifiableList(allColumns.values());
    }
    
    

    @Override
    public List<ColumnGroupMember> getAllMembers() {
        initAllColumnsAndGroups();
        List<ColumnGroupMember> allMembers = new ArrayList<>();
        for(ColumnsGroup g : allGroups.values()) {
            allMembers.add(g);
            allMembers.addAll(g.getAllColumns());
        }
        return allMembers;
    }

    /**
     * 
     * @return list of column group ids
     */
    public List<String> getColumnGroupIds() {
        initAllColumnsAndGroups();
        List<String> res = new ArrayList<String>(columnGroups.size());
        for (ColumnsGroup group : allGroups.values()) {
            res.add(group.getId());
        }
        return res;
    }

    /**
     * Get group by id
     * 
     * @param groupId
     * @return Group or null if not found
     */
    public ColumnsGroup getColumnGroup(String groupId) {
        initAllColumnsAndGroups();
        return allGroups.get(groupId);
    }

    /**
     * Gets column by id from all groups
     * 
     * @param columnId
     * @return column by is
     */
    public Column getColumn(String columnId) {
        initAllColumnsAndGroups();
        Column column = allColumns.get(columnId);
        if (column != null) {
            return column;
        }
        return null;
    }

    /**
     * Gets column by groupId and local or global column id
     * 
     * @param groupId
     * @param columnId
     * @return Column or null
     */
    public Column getColumn(String groupId, String columnId) {
        ColumnsGroup group = allGroups.get(groupId);
        return group != null ? group.getColumn(columnId) : null;
    }

    @Override
    public String toString() {
        return "GroupsListViewDef [groups=" + columnGroups + "]";
    }

    @Override
    public String getLocalId() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getParentId() {
        return null;
    }

    @Override
    public List<ColumnsGroup> getMembers() {
        initAllColumnsAndGroups();
        return Collections.unmodifiableList(allGroups.values());
    }

    @Override
    public Map<String, ? extends ColumnsGroup> getMembersMapping() {
        initAllColumnsAndGroups();
        return Collections.unmodifiableMap(allGroups);
    }

    @Override
    public ColumnsGroup getMember(String id) {
        return getColumnGroup(id);
    }

    @Override
    public List<String> getMembersLocalIds() {
        initAllColumnsAndGroups();
        List<String> localIds = new ArrayList<String>();
        for (ColumnsGroup group : allGroups.values()) {
            localIds.add(group.getLocalId());
        }
        return localIds;
    }

    @Override
    public List<String> getMembersIds() {
        return getColumnGroupIds();
    }

    /** Builder for GroupsListViewDef */
    public static final class Builder implements org.vpda.common.util.Builder<ColumnsGroupsDef> {
        private OrderedMap<String, ColumnsGroup> columnGroups;
        private GroupsListViewDefLocalizer localizer;

        /**
         * Create builder
         */
        public Builder() {
            this.columnGroups = new OrderedMap<String, ColumnsGroup>();
        }

        @Override
        public Class<? extends ColumnsGroupsDef> getTargetClass() {
            return ColumnsGroupsDef.class;
        }

        @Override
        public ColumnsGroupsDef build() {
            return new ColumnsGroupsDef(this);
        }

        /**
         * @return the localizer
         */
        public GroupsListViewDefLocalizer getLocalizer() {
            return localizer;
        }

        /**
         * @param localizer the localizer to set
         * @return this
         */
        public Builder setLocalizer(GroupsListViewDefLocalizer localizer) {
            this.localizer = localizer;
            return this;
        }

        /**
         * Adds groups
         * 
         * @param groups
         * @return this
         */
        public Builder addColumnsGroups(ColumnsGroup... groups) {
            for (ColumnsGroup group : groups) {
                this.columnGroups.put(group.getId(), group);
            }
            return this;
        }

        /**
         * Adds groups
         * 
         * @param groups
         * @return this
         */
        public Builder addColumnsGroups(Collection<ColumnsGroup> groups) {
            for (ColumnsGroup group : groups) {
                this.columnGroups.put(group.getId(), group);
            }
            return this;
        }

        /**
         * Adds single group
         * 
         * @param group
         * @return this
         */
        public Builder addColumnsGroup(ColumnsGroup group) {
            this.columnGroups.put(group.getId(), group);
            return this;
        }

    }

    /**
     * Marker interface for list view definition localizer. This interface class
     * will be used as key when resolving localizer for list view definition from
     * context
     * 
     * @author kitko
     */
    public interface GroupsListViewDefLocalizer extends org.vpda.common.service.localization.Localizer<ColumnsGroupsDef> {
    }

    /**
     * Localizer of the group
     * 
     * @author kitko
     *
     */
    public static final class Localizer implements org.vpda.common.service.localization.Localizer<ColumnsGroupsDef> {
        @Override
        public ColumnsGroupsDef localize(ColumnsGroupsDef t, LocalizationService localizationService, TenementalContext context) {
            List<ColumnsGroup> groups = new ArrayList<ColumnsGroup>();
            for (ColumnsGroup group : t.getColumnGroups()) {
                org.vpda.common.service.localization.Localizer<ColumnsGroup> groupLocalizer = group.getLocalizer() != null ? group.getLocalizer() : new ColumnsGroup.DefaultLocalizer();
                groups.add(groupLocalizer.localize(group, localizationService, context));
            }
            ColumnsGroupsDef result = new ColumnsGroupsDef(groups);
            return result;
        }
    }

}
