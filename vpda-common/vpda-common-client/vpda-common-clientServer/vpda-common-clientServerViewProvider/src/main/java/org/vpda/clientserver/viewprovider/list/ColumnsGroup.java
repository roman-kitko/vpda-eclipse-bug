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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.comps.Member;
import org.vpda.common.comps.MemberContainer;
import org.vpda.common.comps.MemberListenerSupportImpl;
import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.StringLocValue;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.OrderedMap;

/**
 * Group of columns. It can contains columns but also inner groups
 * 
 * @author kitko
 *
 */
@Immutable
public final class ColumnsGroup implements Serializable, ColumnGroupMember, MemberContainer<ColumnGroupMember> {
    private static final long serialVersionUID = 3093340975635270069L;

    private final String id;
    private final String localId;
    private final String parentId;
    private final LocPair<StringLocValue> title;
    private final OrderedMap<String, ColumnGroupMember> members;
    private final LocKey columnsTitleKeyPrefix;
    private final GroupLocalizer localizer;

    private MemberListenerSupportWithFireForSingleMember memberListenerSupport;

    /** Id of main group */
    public static final String MAIN_GROUP = "main";

    private ColumnsGroup(Builder builder) {
        this.localId = Assert.isNotEmptyArgument(builder.localId, "localId");
        this.parentId = builder.parentId;
        this.id = this.parentId != null ? ListViewUtils.getColumnId(parentId, localId) : localId;
        this.title = Assert.isNotNullArgument(builder.title, "title");
        for (Member col : builder.members.values()) {
            if (!id.equals(col.getParentId())) {
                throw new IllegalStateException(MessageFormat.format("Col [{0}] has groupId [{1}] which is diffrent to group [{2}] column is adding to ", col.getId(), col.getParentId(), id));
            }
        }
        this.members = new OrderedMap<>(builder.members);
        this.columnsTitleKeyPrefix = builder.columnsTitleKeyPrefix;
        this.localizer = builder.localizer;
    }

    @Override
    public MemberListenerSupportWithFireForSingleMember getMemberListenerSupport() {
        if (memberListenerSupport == null) {
            memberListenerSupport = new MemberListenerSupportImpl(this);
        }
        return memberListenerSupport;
    }

    /**
     * @return id of group
     */
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLocalId() {
        return localId;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    /**
     * @return title
     */
    public LocPair<StringLocValue> getTitle() {
        return title;
    }

    /**
     * @return the localizer
     */
    public GroupLocalizer getLocalizer() {
        return localizer;
    }

    /**
     * @return the columnsTitlePrefix
     */
    public LocKey getColumnsTitleKeyPrefix() {
        return columnsTitleKeyPrefix;
    }

    /**
     * Gets column by id
     * 
     * @param columnId
     * @return Column info by id
     */
    public Column getColumn(String columnId) {
        ColumnGroupMember member = getMember(columnId);
        return member instanceof Column col ? col : null;
    }

    /**
     * Gets inner group by id
     * 
     * @param groupId
     * @return inner group by id
     */
    public ColumnsGroup getGroup(String groupId) {
        ColumnGroupMember member = getMember(groupId);
        return member instanceof ColumnsGroup group? group : null;
    }

    /**
     * @return list of all columns
     */
    public List<Column> getColumns() {
        List<Column> res = new ArrayList<>();
        for (ColumnGroupMember member : members.values()) {
            if (member instanceof Column col) {
                res.add(col);
            }
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * @return list of all columns ids
     */
    public List<String> getColumnsIds() {
        List<String> res = new ArrayList<String>();
        for (Member member : members.values()) {
            if (member instanceof Column) {
                res.add(member.getId());
            }
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * Gets all inner groups
     * 
     * @return list of all groups inside
     */
    public List<ColumnsGroup> getGroups() {
        List<ColumnsGroup> res = new ArrayList<ColumnsGroup>();
        for (ColumnGroupMember member : members.values()) {
            if (member instanceof ColumnsGroup group) {
                res.add(group);
            }
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * @return list of columns local ids
     */
    public List<String> getColumnsLocalIds() {
        List<String> res = new ArrayList<String>();
        for (Member member : members.values()) {
            if (member instanceof Column) {
                res.add(member.getLocalId());
            }
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * @return map of columns by column id in this group
     */
    public Map<String, Column> getColumnsMappings() {
        Map<String, Column> res = new HashMap<>();
        for (ColumnGroupMember member : members.values()) {
            if (member instanceof Column col) {
                res.put(member.getId(), col);
            }
        }
        return res;
    }

    @Override
    public List<ColumnGroupMember> getMembers() {
        return Collections.unmodifiableList(members.values());
    }

    @Override
    public List<String> getMembersLocalIds() {
        List<String> res = new ArrayList<String>();
        for (Member member : members.values()) {
            res.add(member.getLocalId());
        }
        return Collections.unmodifiableList(res);
    }

    @Override
    public List<String> getMembersIds() {
        List<String> res = new ArrayList<String>();
        for (Member member : members.values()) {
            res.add(member.getId());
        }
        return Collections.unmodifiableList(res);
    }

    @Override
    public Map<String, ColumnGroupMember> getMembersMapping() {
        return Collections.unmodifiableMap(members);
    }

    @Override
    public ColumnGroupMember getMember(String id) {
        ColumnGroupMember col = members.get(id);
        if (col == null) {
            col = members.get(ListViewUtils.getColumnId(this.id, id));
        }
        return col;
    }

    /**
     * @return size of columns
     */
    public int size() {
        return members.size();
    }

    @Override
    public String toString() {
        return "ColumnsGroup [id=" + id + " ]";
    }

    /**
     * Gets all columns inside this group and inner groups
     * 
     * @return all columns inside
     */
    public List<Column> getAllColumns() {
        List<Column> res = new ArrayList<Column>();
        for (ColumnGroupMember member : members.values()) {
            if (member instanceof Column col) {
                res.add(col);
            }
            else if (member instanceof ColumnsGroup group) {
                res.addAll(group.getAllColumns());
            }
        }
        return res;
    }

    /**
     * Gets all Groups inside this group and inner groups
     * 
     * @return all groups inside
     */
    public List<ColumnsGroup> getAllGroups() {
        List<ColumnsGroup> res = new ArrayList<ColumnsGroup>();
        for (ColumnGroupMember member : members.values()) {
            if (member instanceof ColumnsGroup group) {
                res.add(group);
                res.addAll(group.getAllGroups());
            }
        }
        return res;
    }

    /**
     * Gets all columns and groups inside this group and inner groups
     * 
     * @return all columns and groups inside
     */
    public List<ColumnGroupMember> getAllMembers() {
        List<ColumnGroupMember> res = new ArrayList<>();
        for (ColumnGroupMember member : members.values()) {
            if (member instanceof Column) {
                res.add(member);
            }
            else if (member instanceof ColumnsGroup group) {
                res.add(member);
                res.addAll(group.getAllMembers());
            }
        }
        return res;
    }

    /**
     * Builder for {@link ColumnsGroup}
     * 
     * @author kitko
     *
     */
    public static final class Builder implements org.vpda.common.util.Builder<ColumnsGroup> {
        private String parentId;
        private String localId;
        private LocPair<StringLocValue> title;
        private final OrderedMap<String, ColumnGroupMember> members = new OrderedMap<String, ColumnGroupMember>();
        private LocKey columnsTitleKeyPrefix;
        private GroupLocalizer localizer;

        /**
         * @return the id
         */
        public String getLocalId() {
            return localId;
        }

        /**
         * @param localId the id to set
         * @return this
         */
        public Builder setLocalId(String localId) {
            this.localId = localId;
            return this;
        }

        /**
         * @return the parentId
         */
        public String getParentId() {
            return parentId;
        }

        /**
         * @param parentId the parentId to set
         */
        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        /**
         * @return the members
         */
        public OrderedMap<String, ColumnGroupMember> getMembers() {
            return new OrderedMap<String, ColumnGroupMember>(members);
        }

        /**
         * @return the title
         */
        public LocPair<StringLocValue> getTitle() {
            return title;
        }

        /**
         * @param title the title to set
         * @return this
         */
        public Builder setTitle(LocPair<StringLocValue> title) {
            this.title = title;
            return this;
        }

        /**
         * @return the columns
         */
        public Map<String, Column> getColumnsMappings() {
            Map<String, Column> res = new HashMap<String, Column>();
            for (Member member : members.values()) {
                if (member instanceof Column) {
                    res.put(member.getId(), (Column) member);
                }
            }
            return res;
        }

        /**
         * @return list of all columns
         */
        public List<Column> getColumns() {
            List<Column> res = new ArrayList<Column>();
            for (Member member : members.values()) {
                if (member instanceof Column) {
                    res.add((Column) member);
                }
            }
            return Collections.unmodifiableList(res);
        }

        /**
         * @return list of all groups
         */
        public List<ColumnsGroup> getGroups() {
            List<ColumnsGroup> res = new ArrayList<ColumnsGroup>();
            for (Member member : members.values()) {
                if (member instanceof ColumnsGroup) {
                    res.add((ColumnsGroup) member);
                }
            }
            return Collections.unmodifiableList(res);
        }

        /**
         * @param id
         * @return column
         */
        public Column getColumn(String id) {
            Member col = members.get(id);
            return (col instanceof Column) ? (Column) col : null;
        }

        /**
         * Adds single column to group
         * 
         * @param column
         * @return this
         */
        public Builder addColumn(Column column) {
            return addMember(column);
        }

        /**
         * Adds a group
         * 
         * @param group
         * @return this builder
         */
        public Builder addGroup(ColumnsGroup group) {
            return addMember(group);
        }

        /**
         * Adds single member to group
         * 
         * @param member
         * @return this
         */
        public Builder addMember(ColumnGroupMember member) {
            this.members.put(member.getId(), member);
            return this;
        }

        /**
         * Add columns
         * 
         * @param columns
         * @return this
         */
        public Builder addColumns(Column... columns) {
            return addMembers(columns);
        }

        /**
         * Add members
         * 
         * @param members
         * @return this
         */
        public Builder addMembers(ColumnGroupMember... members) {
            for (ColumnGroupMember col : members) {
                this.members.put(col.getId(), col);
            }
            return this;
        }

        /**
         * Adds columns from collection
         * 
         * @param columns
         * @return this
         */
        public Builder addColumns(Collection<Column> columns) {
            return addMembers(columns);
        }

        /**
         * Adds members from collection
         * 
         * @param members
         * @return this
         */
        public Builder addMembers(Collection<? extends ColumnGroupMember> members) {
            for (ColumnGroupMember col : members) {
                this.members.put(col.getId(), col);
            }
            return this;
        }

        /**
         * Remove members
         * 
         * @param members
         * @return this
         */
        public Builder removeMembers(Member... members) {
            for (Member col : members) {
                this.members.remove(col.getId());
            }
            return this;
        }

        /**
         * Remove members
         * 
         * @param memberIds
         * @return this
         */
        public Builder removeMembers(String... memberIds) {
            for (String col : memberIds) {
                this.members.remove(col);
            }
            return this;
        }

        /**
         * Clears columns
         * 
         * @return this
         */
        public Builder clearMembers() {
            this.members.clear();
            return this;
        }

        @Override
        public ColumnsGroup build() {
            return new ColumnsGroup(this);
        }

        @Override
        public Class<? extends ColumnsGroup> getTargetClass() {
            return ColumnsGroup.class;
        }

        /**
         * Sets values from group
         * 
         * @param group
         * @return this
         */
        public Builder setValue(ColumnsGroup group) {
            this.localId = group.localId;
            this.parentId = group.parentId;
            this.title = group.title;
            this.members.clear();
            this.members.putAll(group.members);
            this.columnsTitleKeyPrefix = group.columnsTitleKeyPrefix;
            return this;
        }

        /**
         * @return the columnsTitlePrefix
         */
        public LocKey getColumnsTitleKeyPrefix() {
            return columnsTitleKeyPrefix;
        }

        /**
         * @param columnsTitlePrefix the columnsTitlePrefix to set
         */
        public void setColumnsTitleKeyPrefix(LocKey columnsTitlePrefix) {
            this.columnsTitleKeyPrefix = columnsTitlePrefix;
        }

        /**
         * @return the localizer
         */
        public GroupLocalizer getLocalizer() {
            return localizer;
        }

        /**
         * @param localizer the localizer to set
         */
        public void setLocalizer(GroupLocalizer localizer) {
            this.localizer = localizer;
        }
    }

    /**
     * Marker interface for group localizer. This interface class will be used as
     * key when resolving localizer for group from context
     * 
     * @author kitko
     */
    public interface GroupLocalizer extends org.vpda.common.service.localization.Localizer<ColumnsGroup> {
    }

    /**
     * Localizer of the group
     * 
     * @author kitko
     *
     */
    public static final class DefaultLocalizer implements GroupLocalizer, Serializable {
        private static final long serialVersionUID = -9173530017416144447L;

        @Override
        public ColumnsGroup localize(ColumnsGroup t, LocalizationService localizationService, TenementalContext context) {
            ColumnsGroup.Builder builder = new ColumnsGroup.Builder();
            builder.setValue(t);
            builder.clearMembers();
            for (ColumnGroupMember member : t.getMembers()) {
                if (member instanceof Column) {
                    Column column = (Column) member;
                    org.vpda.common.service.localization.Localizer<Column> columnLocalizer = column.getLocalizer() != null ? column.getLocalizer() : new Column.DefaultLocalizer();
                    builder.addColumn(columnLocalizer.localize(column, localizationService, context));
                }
                if (member instanceof ColumnsGroup) {
                    ColumnsGroup group = (ColumnsGroup) member;
                    GroupLocalizer groupLocalizer = group.getLocalizer() != null ? group.getLocalizer() : this;
                    builder.addGroup(groupLocalizer.localize(group, localizationService, context));
                }
            }
            builder.setTitle(builder.getTitle().localize(localizationService, context));
            return builder.build();
        }
    }

}
