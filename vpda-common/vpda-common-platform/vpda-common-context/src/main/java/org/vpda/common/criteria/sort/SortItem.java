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
package org.vpda.common.criteria.sort;

import java.io.Serializable;

import org.vpda.common.context.TenementalContext;
import org.vpda.common.context.localization.BasicLocalizationService;
import org.vpda.common.context.localization.LocKey;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/** One sort item consisting from column id and direction */
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class SortItem implements Serializable {
    private static final long serialVersionUID = 8637662450648551642L;

    /** Direction of sort */
    public static enum SortDirection {
        /** ASC Sort */
        ASC,
        /** DESC Sort */
        DESC;

        private static final String LOC_PATH = "common/common/sortDirection";

        /**
         * Localize direction name
         * 
         * @param localizationService
         * @param context
         * @return localized name
         */
        public String localize(BasicLocalizationService localizationService, TenementalContext context) {
            LocKey key = LocKey.pathAndKey(LOC_PATH, name());
            return localizationService.localizeString(key, context, name());
        }

    }

    private final String columnId;
    private final SortDirection direction;

    /**
     * Creates SortItem from json
     * 
     * @param columnId
     * @param direction
     * @return SortItem
     */
    @JsonCreator
    public static SortItem fromJson(@JsonProperty("columnId") String columnId, @JsonProperty("direction") SortDirection direction) {
        return new SortItem(columnId, direction);
    }

    /**
     * @return the columnId
     */
    public String getColumnId() {
        return columnId;
    }

    /**
     * @return the direction
     */
    public SortDirection getDirection() {
        return direction;
    }

    /**
     * Creates sort item
     * 
     * @param columnId
     * @param direction
     */
    public SortItem(String columnId, SortDirection direction) {
        super();
        this.columnId = columnId;
        this.direction = direction;
    }

    /**
     * Creates sort item with {@link SortDirection#ASC} direction
     * 
     * @param columnId
     */
    public SortItem(String columnId) {
        this(columnId, SortDirection.ASC);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((columnId == null) ? 0 : columnId.hashCode());
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
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
        SortItem other = (SortItem) obj;
        if (columnId == null) {
            if (other.columnId != null)
                return false;
        }
        else if (!columnId.equals(other.columnId))
            return false;
        if (direction == null) {
            if (other.direction != null)
                return false;
        }
        else if (!direction.equals(other.direction))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return columnId + " " + direction;
    }

}