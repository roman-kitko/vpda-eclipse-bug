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
package org.vpda.common.comps.ui.def;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.comps.MemberContainer;
import org.vpda.common.comps.MemberListenerSupportImpl;
import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ComponentMember;
import org.vpda.common.comps.ui.ComponentUtils;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.StringLocValue;
import org.vpda.common.types.BackendObjectSource;
import org.vpda.common.types.BackendObjectSourceAware;
import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.Assert;
import org.vpda.internal.common.util.OrderedMap;

/**
 * Group of components
 * 
 * @author kitko
 *
 */
@Immutable
public final class ComponentsGroup implements Serializable, ComponentMember, MemberContainer<ComponentMember>, FetchesCompletedValuesAware, AutoCompletedValuesAware, BackendObjectSourceAware {
    private static final long serialVersionUID = 663998491341106083L;

    private final String id;
    private final String localId;
    private final String parentId;
    private final OrderedMap<String, ComponentMember> members;
    private final LocPair<StringLocValue> title;
    private final LocKey componentsTitlePrefix;
    private final ComponentsGroupLocalizer groupLocalizer;
    private FetchesCompletedValues fetchesCompletedValues;
    private AutoCompletedValues autoCompletedValues;
    private BackendObjectSource backendObjectSource;
    private MemberListenerSupportWithFireForSingleMember memberListenerSupport;

    private ComponentsGroup(Builder builder) {
        this.localId = Assert.isNotEmptyArgument(builder.localId, "localId");
        this.parentId = builder.parentId;
        this.id = builder.id;
        this.title = builder.title;
        this.members = new OrderedMap<String, ComponentMember>(builder.members);
        this.componentsTitlePrefix = builder.componentsTitlePrefix;
        this.groupLocalizer = builder.groupLocalizer;
        this.backendObjectSource = builder.getBackendObjectSource();
    }

    @Override
    public FetchesCompletedValues getFetchesCompletedValues() {
        fetchesCompletedValues = fetchesCompletedValues == null ? new FetchesCompletedValues() : fetchesCompletedValues;
        return fetchesCompletedValues;
    }

    @Override
    public AutoCompletedValues getAutoCompletedValues() {
        autoCompletedValues = autoCompletedValues == null ? new AutoCompletedValues() : autoCompletedValues;
        return autoCompletedValues;
    }

    @Override
    public BackendObjectSource getBackendObjectSource() {
        return backendObjectSource;
    }

    @Override
    public BackendObjectSourceAware setBackendObjectSource(BackendObjectSource source) {
        this.backendObjectSource = source;
        return this;
    }

    @Override
    public MemberListenerSupportWithFireForSingleMember getMemberListenerSupport() {
        if (memberListenerSupport == null) {
            memberListenerSupport = new MemberListenerSupportImpl(this);
        }
        return memberListenerSupport;
    }

    /**
     * @return the groupLocalizer
     */
    public ComponentsGroupLocalizer getGroupLocalizer() {
        return groupLocalizer;
    }

    /**
     * @return the componentsTitlePrefix
     */
    public LocKey getComponentsTitlePrefix() {
        return componentsTitlePrefix;
    }

    /**
     * @return id of group
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Gets Component by id
     * 
     * @param compId
     * @return component by id or null if not found
     */
    public Component<?, ?> getComponent(String compId) {
        ComponentMember member = getMember(compId);
        return member instanceof Component comp ? comp : null;
    }

    /**
     * Gets Group by id
     * 
     * @param groupId
     * @return group by id or null if not found
     */
    public ComponentsGroup getGroup(String groupId) {
        ComponentMember member = getMember(groupId);
        return member instanceof ComponentsGroup group ? group : null;
    }

    /**
     * @return all components
     */
    public List<Component> getComponents() {
        List<Component> result = new ArrayList<>(members.size());
        for (ComponentMember member : members.values()) {
            if (member instanceof Component comp) {
                result.add(comp);
            }
        }
        return result;
    }

    /**
     * @return all groups inside, not recursive
     */
    public List<ComponentsGroup> getGroups() {
        List<ComponentsGroup> result = new ArrayList<>(members.size());
        for (ComponentMember member : members.values()) {
            if (member instanceof ComponentsGroup group) {
                result.add(group);
            }
        }
        return result;
    }

    /**
     * Gets i-th component
     * 
     * @param i
     * @return component at index i
     */
    public ComponentMember getMember(int i) {
        return members.getValue(i);
    }

    /**
     * @return components count
     */
    public int getMembersCount() {
        return members.size();
    }

    /**
     * @return localizable title
     */
    public LocPair<StringLocValue> getTitle() {
        return title;
    }

    @Override
    public List<ComponentMember> getMembers() {
        return Collections.unmodifiableList(members.values());
    }

    @Override
    public Map<String, ComponentMember> getMembersMapping() {
        return Collections.unmodifiableMap(members);
    }

    @Override
    public ComponentMember getMember(String compId) {
        ComponentMember component = members.get(compId);
        if (component == null) {
            component = members.get(ComponentUtils.getComponentId(this.id, compId));
        }
        return component;
    }

    @Override
    public List<String> getMembersLocalIds() {
        List<String> localIds = new ArrayList<String>();
        for (ComponentMember c : members.values()) {
            localIds.add(c.getLocalId());
        }
        return localIds;
    }

    @Override
    public List<String> getMembersIds() {
        return Collections.unmodifiableList(members.keys());
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
     * Gets all components inside this group and inner groups
     * 
     * @return all components inside
     */
    public List<Component> getAllComponents() {
        List<Component> res = new ArrayList<Component>();
        for (ComponentMember member : members.values()) {
            if (member instanceof Component comp) {
                res.add(comp);
            }
            else if (member instanceof ComponentsGroup group) {
                res.addAll(group.getAllComponents());
            }
        }
        return res;
    }

    /**
     * Gets all Groups inside this group and inner groups
     * 
     * @return all groups inside
     */
    public List<ComponentsGroup> getAllGroups() {
        List<ComponentsGroup> res = new ArrayList<ComponentsGroup>();
        for (ComponentMember member : members.values()) {
            if (member instanceof ComponentsGroup group) {
                res.add(group);
                res.addAll(group.getAllGroups());
            }
        }
        return res;
    }

    /**
     * Gets all components and groups inside this group and inner groups
     * 
     * @return all components and groups inside
     */
    public List<ComponentMember> getAllMembers() {
        List<ComponentMember> res = new ArrayList<ComponentMember>();
        for (ComponentMember member : members.values()) {
            if (member instanceof Component) {
                res.add(member);
            }
            else if (member instanceof ComponentsGroup group) {
                res.add(group);
                res.addAll(group.getAllMembers());
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ComponentsGroup [id=");
        builder2.append(id);
        builder2.append(", localId=");
        builder2.append(localId);
        builder2.append("]");
        return builder2.toString();
    }

    /** Builder for CompsGroup */
    public static final class Builder implements org.vpda.common.util.Builder<ComponentsGroup> {
        private String localId;
        private String parentId;
        private String id;
        private OrderedMap<String, ComponentMember> members = new OrderedMap<String, ComponentMember>();
        private LocPair<StringLocValue> title;
        private LocKey componentsTitlePrefix;
        private ComponentsGroupLocalizer groupLocalizer;
        private BackendObjectSource backendObjectSource;
        private FetchesCompletedValues fetchesCompletedValues;
        private AutoCompletedValues autoCompletedValues;

        /**
         * @return this
         */
        public BackendObjectSource getBackendObjectSource() {
            return backendObjectSource;
        }

        /**
         * @param backendObjectSource
         * @return this
         */
        public Builder setBackendObjectSource(BackendObjectSource backendObjectSource) {
            this.backendObjectSource = backendObjectSource;
            return this;
        }

        /**
         * @return this
         */
        public FetchesCompletedValues getFetchesCompletedValues() {
            return fetchesCompletedValues;
        }

        /**
         * @param fetchesCompletedValues
         * @return this
         */
        public Builder setFetchesCompletedValues(FetchesCompletedValues fetchesCompletedValues) {
            this.fetchesCompletedValues = fetchesCompletedValues;
            return this;
        }

        /**
         * @return the autoCompletedValues
         */
        public AutoCompletedValues getAutoCompletedValues() {
            return autoCompletedValues;
        }

        /**
         * @param autoCompletedValues the autoCompletedValues to set
         * @return this
         */
        public Builder setAutoCompletedValues(AutoCompletedValues autoCompletedValues) {
            this.autoCompletedValues = autoCompletedValues;
            return this;
        }

        /**
         * @return the groupLocalizer
         */
        public ComponentsGroupLocalizer getGroupLocalizer() {
            return groupLocalizer;
        }

        /**
         * @param groupLocalizer the groupLocalizer to set
         */
        public void setGroupLocalizer(ComponentsGroupLocalizer groupLocalizer) {
            this.groupLocalizer = groupLocalizer;
        }

        /**
         * @return the componentsTitlePrefix
         */
        public LocKey getComponentsTitlePrefix() {
            return componentsTitlePrefix;
        }

        /**
         * @param componentsTitlePrefix the componentsTitlePrefix to set
         * @return this
         */
        public Builder setComponentsTitlePrefix(LocKey componentsTitlePrefix) {
            this.componentsTitlePrefix = componentsTitlePrefix;
            return this;
        }

        @Override
        public Class<? extends ComponentsGroup> getTargetClass() {
            return ComponentsGroup.class;
        }

        @Override
        public ComponentsGroup build() {
            return new ComponentsGroup(this);
        }

        /**
         * @return the localId
         */
        public String getLocalId() {
            return localId;
        }

        /**
         * @param localId the localId to set
         * @return this
         */
        public Builder setLocalId(String localId) {
            this.localId = Assert.isNotEmptyArgument(localId, "localId");
            this.id = computeId();
            return this;
        }

        private String computeId() {
            return parentId == null ? localId : ComponentUtils.getComponentId(parentId, localId);
        }

        /**
         * @return the parentId
         */
        public String getParentId() {
            return parentId;
        }

        /**
         * @param parentId the parentId to set
         * @return this
         */
        public Builder setParentId(String parentId) {
            this.parentId = parentId;
            this.id = computeId();
            return this;
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
         * @return the components
         */
        public OrderedMap<String, ComponentMember> getMembers() {
            return new OrderedMap<String, ComponentMember>(members);
        }

        /**
         * Sets values from the group
         * 
         * @param group
         * @return this
         */
        public Builder setValues(ComponentsGroup group) {
            clearMembers();
            this.title = group.getTitle();
            this.parentId = group.getParentId();
            this.localId = group.getLocalId();
            this.id = computeId();
            this.members = new OrderedMap<String, ComponentMember>();
            for (ComponentMember member : group.getMembers()) {
                if (member instanceof Component) {
                    Component component = (Component) member;
                    component = component.clone();
                    addComponent(component);
                }
                else if (member instanceof ComponentsGroup) {
                    ComponentsGroup group2 = (ComponentsGroup) member;
                    addGroup(new Builder().setValues(group2).build());
                }
            }
            this.fetchesCompletedValues = group.fetchesCompletedValues;
            this.autoCompletedValues = group.autoCompletedValues;
            this.backendObjectSource = group.getBackendObjectSource();
            return this;
        }

        /**
         * Clear components
         * 
         * @return this
         */
        public Builder clearMembers() {
            for (ComponentMember c : members.values()) {
                if (c instanceof Component) {
                    ((Component) c).setGroupId(null);
                }
            }
            this.members.clear();
            return this;
        }

        /**
         * Adds components to group
         * 
         * @param comp
         * @return this
         */
        public Builder addComponent(Component<?, ?> comp) {
            checkIdSet();
            if (comp.getGroupId() != null && !comp.getGroupId().equals(id)) {
                throw new VPDARuntimeException(MessageFormat.format("Component with id [{0}] has groupId [{1}] and cannot be add to group [{2}]", comp.getId(), comp.getGroupId(), id));
            }
            if (members.containsKey(comp.getId())) {
                return this;
            }
            comp.setGroupId(id);
            members.put(comp.getId(), comp);
            return this;
        }

        /**
         * Adds components
         * 
         * @param comps
         * @return this
         */
        public Builder addComponents(Component<?, ?>... comps) {
            for (Component comp : comps) {
                addComponent(comp);
            }
            return this;
        }

        /**
         * Adds components
         * 
         * @param comps
         * @return this
         */
        public Builder addComponents(Collection<Component> comps) {
            for (Component comp : comps) {
                addComponent(comp);
            }
            return this;
        }

        /**
         * Adds a group as inner group
         * 
         * @param group
         * @return this
         */
        public Builder addGroup(ComponentsGroup group) {
            checkIdSet();
            if (group.getParentId() != null && !group.getParentId().equals(id)) {
                throw new VPDARuntimeException(MessageFormat.format("Group with id [{0}] has parentId [{1}] and cannot be added to group [{2}]", group.getId(), group.getParentId(), id));
            }
            if (members.containsKey(group.getId())) {
                return this;
            }
            members.put(group.getId(), group);
            return this;
        }

        /**
         * Adds groups
         * 
         * @param groups
         * @return this
         */
        public Builder addGroups(ComponentsGroup... groups) {
            for (ComponentsGroup group : groups) {
                addGroup(group);
            }
            return this;
        }

        /**
         * Adds groups
         * 
         * @param groups
         * @return this
         */
        public Builder addGroups(Collection<ComponentsGroup> groups) {
            for (ComponentsGroup group : groups) {
                addGroup(group);
            }
            return this;
        }

        /**
         * Adds a member
         * 
         * @param member
         * @return this
         */
        public Builder addMember(ComponentMember member) {
            if (member instanceof Component) {
                return addComponent((Component<?, ?>) member);
            }
            else if (member instanceof ComponentsGroup) {
                return addGroup((ComponentsGroup) member);
            }
            return this;
        }

        /**
         * Adds members
         * 
         * @param members
         * @return this
         */
        public Builder addMembers(ComponentMember... members) {
            for (ComponentMember member : members) {
                addMember(member);
            }
            return this;
        }

        /**
         * Adds members
         * 
         * @param members
         * @return this
         */
        public Builder addMembers(Collection<ComponentMember> members) {
            for (ComponentMember member : members) {
                addMember(member);
            }
            return this;
        }

        private void checkIdSet() {
            if (id == null) {
                throw new IllegalStateException("Set id on group before calling operations");
            }
        }

        /**
         * @param compId
         * @return removed component with id
         */
        public ComponentMember removeMember(String compId) {
            ComponentMember member = members.remove(compId);
            if (member == null) {
                member = members.remove(ComponentUtils.getComponentId(this.id, compId));
            }
            if (member instanceof Component) {
                ((Component) member).setGroupId(null);
            }
            return member;
        }

    }

    /**
     * Marker interface for group localizer. This interface class will be used as
     * key when resolving localizer for group from context
     * 
     * @author kitko
     */
    public interface ComponentsGroupLocalizer extends org.vpda.common.service.localization.Localizer<ComponentsGroup> {
    }

    /**
     * Localizer of the group
     * 
     * @author kitko
     *
     */
    public static final class DefaultLocalizer implements ComponentsGroupLocalizer, Serializable {
        private static final long serialVersionUID = -9173530017416144447L;
        private static final DefaultLocalizer INSTANCE = new DefaultLocalizer();

        /** Creates new */
        public DefaultLocalizer() {
        }

        /**
         * @return instance
         */
        public static final DefaultLocalizer getInstance() {
            return INSTANCE;
        }

        @Override
        public ComponentsGroup localize(ComponentsGroup t, LocalizationService localizationService, TenementalContext context) {
            ComponentsGroup.Builder builder = new ComponentsGroup.Builder();
            builder.setValues(t);
            builder.clearMembers();
            for (ComponentMember member : t.getMembers()) {
                if (member instanceof Component) {
                    Component component = (Component) member;
                    component = component.clone();
                    component.localize(localizationService, context);
                    builder.addComponent(component);
                }
                else if (member instanceof ComponentsGroup) {
                    ComponentsGroup group = (ComponentsGroup) member;
                    ComponentsGroupLocalizer groupLocalizer = group.getGroupLocalizer() != null ? group.getGroupLocalizer() : this;
                    builder.addGroup(groupLocalizer.localize(group, localizationService, context));
                }
            }
            builder.setTitle(builder.getTitle().localize(localizationService, context));
            return builder.build();
        }
    }
}
