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
package org.vpda.common.comps.ui.def;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.comps.CurrentData;
import org.vpda.common.comps.MemberContainer;
import org.vpda.common.comps.MemberListenerSupportImpl;
import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.comps.ui.AbstractComponent;
import org.vpda.common.comps.ui.Component;
import org.vpda.common.comps.ui.ComponentMember;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.internal.common.util.OrderedMap;

/**
 * This is Raw access to {@link AbstractComponent} fields. Instead of traversing
 * all containers, here we have flat all components. Definition physically
 * contains {@link ComponentsGroup} groups which contains components.
 * 
 * @author kitko
 *
 */
@Immutable
public final class ComponentsGroupsDef implements Serializable, MemberContainer<ComponentsGroup>, CurrentData, FetchesCompletedValuesAware, AutoCompletedValuesAware {
    private static final long serialVersionUID = 2518139335935399425L;
    private final List<ComponentsGroup> groups;
    private final ComponentsGroupsDefLocalizer localizer;

    private transient OrderedMap<String, ComponentsGroup> allGroups;
    private transient OrderedMap<String, Component> allComponents;
    private MemberListenerSupportWithFireForSingleMember memberListenerSupport;
    private FetchesCompletedValues fetchesCompletedValues;
    private AutoCompletedValues autoCompletedValues;

    /**
     * Creates definition containing groups
     * 
     * @param groups
     */
    public ComponentsGroupsDef(List<ComponentsGroup> groups) {
        this(groups, null);
    }

    /**
     * Creates definition containing groups
     * 
     * @param groups
     */
    public ComponentsGroupsDef(ComponentsGroup... groups) {
        this(Arrays.asList(groups), null);
    }

    /**
     * Creates definition containing groups
     * 
     * @param groups
     * @param localizer
     */
    public ComponentsGroupsDef(List<ComponentsGroup> groups, ComponentsGroupsDefLocalizer localizer) {
        this.groups = new ArrayList<>(groups);
        this.localizer = localizer;
    }

    private ComponentsGroupsDef(Builder builder) {
        this.groups = new ArrayList<>(builder.groups);
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
     * Gets localizer
     * 
     * @return localizer
     */
    public ComponentsGroupsDefLocalizer getLocalizer() {
        return localizer;
    }

    private void initAllComponentsAndGroups() {
        if (allGroups != null) {
            return;
        }
        allGroups = new OrderedMap<>();
        allComponents = new OrderedMap<>();
        for (ComponentsGroup group : groups) {
            allGroups.put(group.getId(), group);
            for (ComponentsGroup innerGroup : group.getAllGroups()) {
                allGroups.put(innerGroup.getId(), innerGroup);
            }
            for (Component comp : group.getAllComponents()) {
                allComponents.put(comp.getId(), comp);
            }
        }
    }

    /**
     * Gets Component by id
     * 
     * @param compId
     * @return component by id or null if not found
     */
    public Component<?, ?> getComponent(String compId) {
        initAllComponentsAndGroups();
        return allComponents.get(compId);
    }

    /**
     * @return components count
     */
    public int getComponentsCount() {
        initAllComponentsAndGroups();
        return allComponents.size();
    }

    /**
     * Gets all components
     * 
     * @return all components
     */
    public List<Component> getComponents() {
        initAllComponentsAndGroups();
        return Collections.unmodifiableList(allComponents.values());
    }

    /**
     * @return collection of all component ids
     */
    public List<String> getComponentsIds() {
        initAllComponentsAndGroups();
        List<String> ids = new ArrayList<String>();
        for (Component comp : allComponents.values()) {
            ids.add(comp.getId());
        }
        return ids;
    }

    /**
     * @param groupId
     * @return Group by id or null if not registered
     */
    public ComponentsGroup getGroup(String groupId) {
        initAllComponentsAndGroups();
        return allGroups.get(groupId);
    }

    /**
     * @return list of group ids
     */
    public List<String> getGroupsIds() {
        initAllComponentsAndGroups();
        return allGroups.keys();
    }

    /**
     * gets all groups
     * 
     * @return all groups
     */
    public List<ComponentsGroup> getGroups() {
        initAllComponentsAndGroups();
        return Collections.unmodifiableList(allGroups.values());
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
    public List<ComponentsGroup> getMembers() {
        initAllComponentsAndGroups();
        return Collections.unmodifiableList(allGroups.values());
    }

    @Override
    public Map<String, ? extends ComponentsGroup> getMembersMapping() {
        initAllComponentsAndGroups();
        return Collections.unmodifiableMap(allGroups);
    }
    
    

    @Override
    public List<ComponentMember> getAllMembers() {
        initAllComponentsAndGroups();
        List<ComponentMember> allMembers = new ArrayList<>();
        for(ComponentsGroup group : allGroups.values()) {
            allMembers.add(group);
            allMembers.addAll(group.getAllComponents());
        }
        return allMembers;
    }

    @Override
    public ComponentsGroup getMember(String id) {
        return getGroup(id);
    }

    @Override
    public List<String> getMembersLocalIds() {
        initAllComponentsAndGroups();
        List<String> localIds = new ArrayList<String>();
        for (ComponentsGroup group : allGroups.values()) {
            localIds.add(group.getLocalId());
        }
        return localIds;
    }

    @Override
    public List<String> getMembersIds() {
        return getGroupsIds();
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ComponentsGroupsDef [groups=");
        builder2.append(groups);
        builder2.append("]");
        return builder2.toString();
    }

    /**
     * Marker interface for list view definition localizer. This interface class
     * will be used as key when resolving localizer for list view definition from
     * context
     * 
     * @author kitko
     */
    public interface ComponentsGroupsDefLocalizer extends org.vpda.common.service.localization.Localizer<ComponentsGroupsDef> {
    }

    /**
     * Default Localizer of the group def
     * 
     * @author kitko
     *
     */
    public static final class DefaultLocalizer implements ComponentsGroupsDefLocalizer {
        private static final DefaultLocalizer INSTANCE = new DefaultLocalizer();

        /** Creates DefaultLocalizer */
        public DefaultLocalizer() {
        }

        /**
         * @return instance
         */
        public static DefaultLocalizer getInstance() {
            return INSTANCE;
        }

        @Override
        public ComponentsGroupsDef localize(ComponentsGroupsDef t, LocalizationService localizationService, TenementalContext context) {
            List<ComponentsGroup> groups = new ArrayList<ComponentsGroup>();
            for (ComponentsGroup group : t.getMembers()) {
                org.vpda.common.service.localization.Localizer<ComponentsGroup> groupLocalizer = group.getGroupLocalizer() != null ? group.getGroupLocalizer()
                        : ComponentsGroup.DefaultLocalizer.getInstance();
                groups.add(groupLocalizer.localize(group, localizationService, context));
            }
            ComponentsGroupsDef result = new ComponentsGroupsDef(groups, t.getLocalizer());
            return result;
        }
    }

    /** Builder for ComponentsGroupsDef */
    public static final class Builder implements org.vpda.common.util.Builder<ComponentsGroupsDef> {
        private List<ComponentsGroup> groups;
        private ComponentsGroupsDefLocalizer localizer;

        /** Creates builder */
        public Builder() {
            this.groups = new ArrayList<>();
        }

        /**
         * @return the localizer
         */
        public ComponentsGroupsDefLocalizer getLocalizer() {
            return localizer;
        }

        /**
         * @param localizer the localizer to set
         * @return this
         */
        public Builder setLocalizer(ComponentsGroupsDefLocalizer localizer) {
            this.localizer = localizer;
            return this;
        }

        /**
         * Adds group
         * 
         * @param group
         * @return this
         */
        public Builder addGroup(ComponentsGroup group) {
            this.groups.add(group);
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
                this.groups.add(group);
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
                this.groups.add(group);
            }
            return this;
        }

        /**
         * @return the groups
         */
        public List<ComponentsGroup> getGroups() {
            return groups;
        }

        @Override
        public Class<? extends ComponentsGroupsDef> getTargetClass() {
            return ComponentsGroupsDef.class;
        }

        @Override
        public ComponentsGroupsDef build() {
            return new ComponentsGroupsDef(this);
        }

    }

    @Override
    public boolean isEmpty() {
        return getComponentsCount() == 0;
    }

    @Override
    public int size() {
        return getComponentsCount();
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
}
