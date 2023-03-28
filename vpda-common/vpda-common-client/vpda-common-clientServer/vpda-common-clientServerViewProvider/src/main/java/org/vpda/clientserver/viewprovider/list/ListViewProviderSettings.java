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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.vpda.clientserver.viewprovider.LayoutBasedViewProviderSettings;
import org.vpda.clientserver.viewprovider.ViewProviderUIChildrenOrientation;
import org.vpda.common.criteria.CriteriaTree;
import org.vpda.common.criteria.sort.Sort;
import org.vpda.internal.common.util.OrderedMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Settings for list providers
 * 
 * @author kitko
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class ListViewProviderSettings extends LayoutBasedViewProviderSettings {
    /** generic criteria defined by user */
    public static final String USER_GENERIC_CRITERIA = "userGenericCriteria";

    private final OrderedMap<String, UserColumnSettings> columnsSettings;
    private final CriteriaTree userCriteria;
    private final Sort userSort;
    private final ListViewPagingSize pageSize;

    private static final long serialVersionUID = 5437959448610479971L;

    /**
     * @return list of visible column ids
     */
    public List<String> getVisibleColumnIds() {
        List<String> result = new ArrayList<>(columnsSettings.size());
        for (UserColumnSettings userColumnSettings : columnsSettings.values()) {
            if (userColumnSettings.isVisible()) {
                result.add(userColumnSettings.getColumnId());
            }
        }
        return result;
    }

    /**
     * @return the pageSize
     */
    public ListViewPagingSize getPageSize() {
        return pageSize;
    }

    /**
     * Creates ListViewProviderSettings from json
     * 
     * @param columnsSettings
     * @param userCriteria
     * @param userSort
     * @param pageSize
     * @param childrenOrientation
     * @return ListViewProviderSettings
     */
    @JsonCreator
    public static ListViewProviderSettings fromJson(@JsonProperty("columnsSettings") OrderedMap<String, UserColumnSettings> columnsSettings, @JsonProperty("userCriteria") CriteriaTree userCriteria,
            @JsonProperty("userSort") Sort userSort, @JsonProperty("pageSize") ListViewPagingSize pageSize,
            @JsonProperty("childrenOrientation") ViewProviderUIChildrenOrientation childrenOrientation) {
        Builder builder = new Builder();
        builder.setChildrenOrientation(childrenOrientation);
        builder.setGenericUserCriteria(userCriteria).setUserSort(userSort);
        if (columnsSettings != null) {
            for (UserColumnSettings ucs : columnsSettings.values()) {
                builder.addUserColumnSettings(ucs);
            }
        }
        return builder.build();
    }

    /**
     * @return list of user column settings
     */
    public List<UserColumnSettings> getUserColumnSettings() {
        return Collections.unmodifiableList(columnsSettings.values());
    }

    private ListViewProviderSettings(Builder builder) {
        super(builder);
        this.userSort = builder.userSort;
        this.userCriteria = builder.userCriteria;
        this.columnsSettings = new OrderedMap<>(builder.columnsSettings);
        this.pageSize = builder.getPageSize();
    }

    /**
     * @param columnId
     * @return column settings by id or null if not found
     */
    public UserColumnSettings getColumnSettings(String columnId) {
        return columnsSettings.get(columnId);
    }

    /**
     * @return user criteria
     */
    public CriteriaTree getGenericUserCriteria() {
        return userCriteria;
    }

    /**
     * @return user sort
     */
    public Sort getUserSort() {
        return userSort;
    }

    /**
     * @return Builder
     */
    @SuppressWarnings("unchecked")
    @Override
    public Builder createBuilderWithSameValues() {
        return new Builder().setValues(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((columnsSettings == null) ? 0 : columnsSettings.hashCode());
        result = prime * result + ((pageSize == null) ? 0 : pageSize.hashCode());
        result = prime * result + ((userCriteria == null) ? 0 : userCriteria.hashCode());
        result = prime * result + ((userSort == null) ? 0 : userSort.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ListViewProviderSettings other = (ListViewProviderSettings) obj;
        if (columnsSettings == null) {
            if (other.columnsSettings != null) {
                return false;
            }
        }
        else if (!columnsSettings.equals(other.columnsSettings)) {
            return false;
        }
        if (pageSize == null) {
            if (other.pageSize != null) {
                return false;
            }
        }
        else if (!pageSize.equals(other.pageSize)) {
            return false;
        }
        if (userCriteria == null) {
            if (other.userCriteria != null) {
                return false;
            }
        }
        else if (!userCriteria.equals(other.userCriteria)) {
            return false;
        }
        if (userSort == null) {
            if (other.userSort != null) {
                return false;
            }
        }
        else if (!userSort.equals(other.userSort)) {
            return false;
        }
        return true;
    }

    /**
     * Builder for {@link ListViewProviderSettings}
     * 
     * @author kitko
     *
     */
    public static final class Builder extends LayoutBasedViewProviderSettings.Builder<ListViewProviderSettings> implements org.vpda.common.util.Builder<ListViewProviderSettings> {
        private OrderedMap<String, UserColumnSettings> columnsSettings;
        private CriteriaTree userCriteria;
        private Sort userSort;
        private ListViewPagingSize pageSize;

        /**
         * Creates builder
         */
        public Builder() {
            this.columnsSettings = new OrderedMap<>();
            this.pageSize = ListViewPagingSize.RelativeSize.MAX_ROWS_PER_PAGE;
        }

        /**
         * @param listViewProviderInfo
         * @return this
         */
        public Builder setProviderInfo(ListViewProviderInfo listViewProviderInfo) {
            for (Column columnInfo : listViewProviderInfo.getColumns()) {
                if (!columnInfo.isInvisible()) {
                    addUserColumnSettings(new UserColumnSettings(columnInfo.getId()));
                }
            }
            return this;
        }

        /**
         * @param settings
         * @return this
         */
        @Override
        public Builder setValues(ListViewProviderSettings settings) {
            super.setValues(settings);
            this.userCriteria = settings.getGenericUserCriteria();
            this.userSort = settings.getUserSort();
            this.columnsSettings = new OrderedMap<>(settings.columnsSettings);
            this.pageSize = settings.getPageSize();
            return this;
        }

        /**
         * Clears all column settings
         * 
         * @return this
         */
        public Builder clearColumnSettings() {
            this.columnsSettings = new OrderedMap<>();
            return this;
        }

        /**
         * Add column settings
         * 
         * @param columnSettings
         * @param rest           another columns
         * @return this
         */
        public Builder addUserColumnSettings(UserColumnSettings columnSettings, UserColumnSettings... rest) {
            this.columnsSettings.put(columnSettings.getColumnId(), columnSettings);
            for (UserColumnSettings c : rest) {
                this.columnsSettings.put(c.getColumnId(), c);
            }
            return this;
        }

        /**
         * Add one column settings
         * 
         * @param columnSettings
         * @return this
         */
        public Builder addUserColumnSettings(UserColumnSettings columnSettings) {
            this.columnsSettings.put(columnSettings.getColumnId(), columnSettings);
            return this;
        }

        /**
         * @return the columnsSettings
         */
        public Map<String, UserColumnSettings> getColumnsSettings() {
            return Collections.unmodifiableMap(columnsSettings);
        }

        /**
         * @return the userCriteria
         */
        public CriteriaTree getGenericUserCriteria() {
            return userCriteria;
        }

        /**
         * @param userCriteria the userCriteria to set
         * @return this
         */
        public Builder setGenericUserCriteria(CriteriaTree userCriteria) {
            this.userCriteria = userCriteria;
            return this;
        }

        /**
         * @return the userSort
         */
        public Sort getUserSort() {
            return userSort;
        }

        /**
         * @param userSort the userSort to set
         * @return this
         */
        public Builder setUserSort(Sort userSort) {
            this.userSort = userSort;
            return this;
        }

        /**
         * @return the pageSize
         */
        public ListViewPagingSize getPageSize() {
            return pageSize;
        }

        /**
         * @param pageSize the pageSize to set
         * @return this
         */
        public Builder setPageSize(ListViewPagingSize pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        @Override
        public ListViewProviderSettings build() {
            return new ListViewProviderSettings(this);
        }

        @Override
        public Class<? extends ListViewProviderSettings> getTargetClass() {
            return ListViewProviderSettings.class;
        }

    }

}
