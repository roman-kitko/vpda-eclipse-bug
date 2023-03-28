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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Settings for single column
 * 
 * @author kitko
 *
 */
@JsonInclude(Include.NON_NULL)
public final class UserColumnSettings implements Serializable {
    private final String columnId;
    private final boolean visible;
    private final Integer width;
    private static final long serialVersionUID = 7768400384401564368L;

    /**
     * @return columnId
     */
    public String getColumnId() {
        return columnId;
    }

    /**
     * @return visible flag
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @return the width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Creates UserColumnSettings
     * 
     * @param columnId
     * @param visible
     * @param width
     */
    public UserColumnSettings(String columnId, boolean visible, Integer width) {
        if (columnId == null) {
            throw new IllegalArgumentException("ColumnId argument is null");
        }
        this.columnId = columnId;
        this.visible = visible;
        this.width = width;
    }

    /**
     * Creates UserColumnSettings
     * 
     * @param columnId
     * @param visible
     */
    public UserColumnSettings(String columnId, boolean visible) {
        this(columnId, visible, null);
    }

    /**
     * Creates UserColumnSettings
     * 
     * @param columnId
     */
    public UserColumnSettings(String columnId) {
        this(columnId, true, null);
    }

    /**
     * Creates UserColumnSettings from json
     * 
     * @param columnId
     * @param visible
     * @param width
     * @return ListViewProviderSettings
     */
    @JsonCreator
    public static UserColumnSettings fromJson(@JsonProperty("columnId") String columnId, @JsonProperty("visible") boolean visible, @JsonProperty("width") Integer width) {
        return new UserColumnSettings(columnId, visible, width);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserColumnSettings)) {
            return false;
        }
        UserColumnSettings ucs = (UserColumnSettings) obj;
        return columnId.equals(ucs.getColumnId());
    }

    @Override
    public int hashCode() {
        return columnId.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserColumnSettings [columnId=");
        builder.append(columnId);
        builder.append(", visible=");
        builder.append(visible);
        builder.append("]");
        return builder.toString();
    }

}
