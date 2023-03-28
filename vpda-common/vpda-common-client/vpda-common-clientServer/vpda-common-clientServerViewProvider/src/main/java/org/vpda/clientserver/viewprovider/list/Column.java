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
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.vpda.common.annotations.Immutable;
import org.vpda.common.comps.MemberListenerSupportImpl;
import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.LocalizationService;
import org.vpda.common.service.localization.StringLocValue;
import org.vpda.internal.common.util.Assert;

/**
 * Column holds information about one column in list view.
 * 
 * @author kitko
 *
 */
@Immutable
public final class Column implements ColumnAccessor, Serializable, ColumnGroupMember {
    /** All public flags */
    public enum Flags {
        /** Flag that column must be invisible */
        INVISIBLE;
    }

    private static final long serialVersionUID = 953570980920919965L;
    private final String id;
    private final String localId;
    private final LocPair<StringLocValue> title;
    private final String groupId;
    private final Class<?> type;
    private final Set<Flags> flags;
    private final Map<String, Object> properties;
    private final ColumnLocalizer localizer;
    private MemberListenerSupportWithFireForSingleMember memberListenerSupport;

    private Column(Builder builder) {
        this.groupId = Assert.isNotEmptyArgument(builder.groupId, "groupId");
        this.localId = Assert.isNotEmptyArgument(builder.localId, "localId");
        this.id = ListViewUtils.getColumnId(builder.groupId, builder.localId);
        this.type = Assert.isNotNullArgument(builder.type, "type");
        this.title = Assert.isNotNullArgument(builder.title, "title");
        this.properties = builder.properties != null ? new HashMap<String, Object>(builder.properties) : null;
        this.flags = builder.flags != null ? EnumSet.copyOf(builder.flags) : null;
        this.localizer = builder.localizer;
    }

    /**
     * @param groupId
     * @param localId
     * @param titlePrefix
     * @param type
     */
    public Column(String groupId, String localId, String titlePrefix, Class<?> type) {
        this.groupId = Assert.isNotEmptyArgument(groupId, "groupId");
        this.localId = Assert.isNotEmptyArgument(localId, "localId");
        this.id = ListViewUtils.getColumnId(groupId, localId);
        this.type = Assert.isNotNullArgument(type, "type");
        Assert.isNotNullArgument(titlePrefix, "titlePrefix");
        this.title = LocPair.createStringLocPair(titlePrefix, localId);
        this.properties = null;
        this.flags = null;
        this.localizer = null;
    }

    /**
     * @return the localizer
     */
    public ColumnLocalizer getLocalizer() {
        return localizer;
    }

    @Override
    public MemberListenerSupportWithFireForSingleMember getMemberListenerSupport() {
        if (memberListenerSupport == null) {
            memberListenerSupport = new MemberListenerSupportImpl(this);
        }
        return memberListenerSupport;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @return the localId
     */
    @Override
    public String getLocalId() {
        return localId;
    }

    /**
     * @return the locPair
     */
    public LocPair<StringLocValue> getTitle() {
        return title;
    }

    /**
     * Get the title in safe manner, when column was not localized
     * 
     * @return column title
     */
    public String getSafeTitle() {
        if (title != null && title.getLocValue() != null && title.getLocValue().getStringLocValue() != null) {
            return title.getLocValue().getStringLocValue();
        }
        return id;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getParentId() {
        return groupId;
    }

    /**
     * @return the type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * @return the flags
     */
    public Set<Flags> getFlags() {
        return flags != null ? Collections.unmodifiableSet(flags) : Collections.<Flags>emptySet();
    }

    /**
     * @return true if column flags contains {@value Flags#INVISIBLE}
     */
    public boolean isInvisible() {
        return flags != null && flags.contains(Flags.INVISIBLE);
    }

    /**
     * @return the properties
     */
    public Map<String, Object> getProperties() {
        return properties != null ? Collections.unmodifiableMap(properties) : Collections.<String, Object>emptyMap();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Column other = (Column) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Column [id=" + id + "]";
    }

    /**
     * Builder for ColumnInfo
     * 
     * @author kitko
     */
    public static final class Builder implements org.vpda.common.util.Builder<Column> {
        private String localId;
        private LocPair<StringLocValue> title;
        private String groupId;
        private Class<?> type;
        private Set<Flags> flags;
        private Map<String, Object> properties;
        private ColumnLocalizer localizer;

        @Override
        public Column build() {
            return new Column(this);
        }

        @Override
        public Class<? extends Column> getTargetClass() {
            return Column.class;
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
            this.localId = localId;
            return this;
        }

        /**
         * @return the locPair
         */
        public LocPair<StringLocValue> getTitle() {
            return title;
        }

        /**
         * @param title the locPair to set
         * @return this
         */
        public Builder setTitle(LocPair<StringLocValue> title) {
            this.title = title;
            return this;
        }

        /**
         * @return the groupId
         */
        public String getGroupId() {
            return groupId;
        }

        /**
         * @param groupId the groupId to set
         * @return this
         */
        public Builder setGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        /**
         * @return the type
         */
        public Class<?> getType() {
            return type;
        }

        /**
         * @param type the type to set
         * @return this
         */
        public Builder setType(Class<?> type) {
            this.type = type;
            return this;
        }

        /**
         * @return the flags
         */
        public Set<Flags> getFlags() {
            return flags != null ? Collections.unmodifiableSet(flags) : Collections.<Flags>emptySet();
        }

        /**
         * @param flags the flags to set
         * @return this
         */
        public Builder setFlags(Set<Flags> flags) {
            if (this.flags != null) {
                this.flags.clear();
            }
            if (flags != null) {
                this.flags = EnumSet.copyOf(flags);
            }
            return this;
        }

        /**
         * Adds flags to flags that are already set
         * 
         * @param flag
         * @return this
         */
        public Builder addFlags(Flags... flag) {
            if (this.flags == null) {
                this.flags = EnumSet.copyOf(Arrays.asList(flag));
            }
            else {
                Set<Flags> result = new HashSet<Flags>(this.flags);
                result.addAll(Arrays.asList(flag));
                this.flags = EnumSet.copyOf(result);
            }
            return this;
        }

        /**
         * Remove flags
         * 
         * @param flags
         * @return this
         */
        public Builder removeFlags(Flags... flags) {
            if (this.flags != null) {
                this.flags.removeAll(Arrays.asList(flags));
            }
            return this;
        }

        /**
         * @return the properties
         */
        public Map<String, Object> getProperties() {
            return properties != null ? Collections.unmodifiableMap(properties) : Collections.<String, Object>emptyMap();
        }

        /**
         * Sets property
         * 
         * @param key
         * @param value
         * @return this
         */
        public Builder setProperty(String key, Object value) {
            if (this.properties == null) {
                this.properties = new HashMap<String, Object>();
            }
            this.properties.put(key, value);
            return this;
        }

        /**
         * Gets value of property
         * 
         * @param key
         * @return property value
         */
        public Object getProperty(String key) {
            return this.properties != null ? this.properties.get(key) : null;
        }

        /**
         * Removes one property
         * 
         * @param key
         * @return old value
         */
        public Object removeProperty(String key) {
            if (this.properties != null) {
                return this.properties.remove(key);
            }
            return null;
        }

        /**
         * @param properties the properties to set
         * @return this
         */
        public Builder setProperties(Map<String, Object> properties) {
            if (this.properties != null) {
                this.properties.clear();
            }
            if (properties != null) {
                this.properties = new HashMap<String, Object>(properties);
            }
            return this;
        }

        /**
         * Shortcut to setting column invisible or not
         * 
         * @param b
         * @return this
         */
        public Builder setInvisible(boolean b) {
            if (b) {
                addFlags(Flags.INVISIBLE);
            }
            else {
                removeFlags(Flags.INVISIBLE);
            }
            return this;
        }

        /**
         * @return true if column flags contains {@value Flags#INVISIBLE}
         */
        public boolean isInvisible() {
            return flags != null && flags.contains(Flags.INVISIBLE);
        }

        /**
         * Sets values from column
         * 
         * @param col
         * @return this
         */
        public Builder setValue(Column col) {
            this.type = col.type;
            this.title = col.title;
            this.localId = col.localId;
            this.groupId = col.groupId;
            this.properties = col.properties != null ? new HashMap<String, Object>(col.properties) : null;
            this.flags = col.flags != null ? EnumSet.copyOf(col.flags) : null;
            this.localizer = col.getLocalizer();
            return this;
        }

        /**
         * @return the localizer
         */
        public org.vpda.common.service.localization.Localizer<Column> getLocalizer() {
            return localizer;
        }

        /**
         * @param localizer the localizer to set
         */
        public void setLocalizer(ColumnLocalizer localizer) {
            this.localizer = localizer;
        }

    }

    @Override
    public Column getColumn() {
        return this;
    }

    /**
     * Marker interface for column localizer. This interface class will be used as
     * key when resolving localizer for column from context
     * 
     * @author kitko
     */
    public interface ColumnLocalizer extends org.vpda.common.service.localization.Localizer<Column> {
    }

    /** Localizer of column */
    public static final class DefaultLocalizer implements ColumnLocalizer, Serializable {
        private static final long serialVersionUID = -5273795134329205981L;

        @Override
        public Column localize(Column t, LocalizationService localizationService, TenementalContext context) {
            Column.Builder builder = new Column.Builder().setValue(t);
            builder.setTitle(builder.getTitle().localize(localizationService, context));
            Column col = builder.build();
            return col;
        }
    }

    /** Localizer of column with prefix */
    public static final class PrefixLocalizer implements ColumnLocalizer, Serializable {
        private static final long serialVersionUID = -3276183634030465673L;
        private final LocKey prefixKey;
        private final String separator;

        /**
         * Creates localizer with prefix and default separator " - "
         * 
         * @param prefixKey
         */
        public PrefixLocalizer(LocKey prefixKey) {
            this.prefixKey = prefixKey;
            this.separator = " - ";
        }

        /**
         * Creates localizer with prefix and passed separator
         * 
         * @param prefixKey
         * @param separator
         */
        public PrefixLocalizer(LocKey prefixKey, String separator) {
            this.prefixKey = prefixKey;
            this.separator = separator;
        }

        /**
         * @return the prefixKey
         */
        public LocKey getPrefixKey() {
            return prefixKey;
        }

        /**
         * @return the separator
         */
        public String getSeparator() {
            return separator;
        }

        @Override
        public Column localize(Column t, LocalizationService localizationService, TenementalContext context) {
            Column.Builder builder = new Column.Builder().setValue(t);
            LocPair<StringLocValue> locTitle = builder.getTitle().localize(localizationService, context);
            if (locTitle.getLocValue() != null) {
                String prefMsg = localizationService.localizeMessage(prefixKey, context);
                prefMsg = prefMsg != null ? prefMsg : prefixKey.getPath();
                builder.setTitle(LocPair.createStringLocPair(locTitle.getLocKey(), prefMsg + separator + locTitle.getLocValue().getStringLocValue()));
            }
            return builder.build();
        }
    }

}
