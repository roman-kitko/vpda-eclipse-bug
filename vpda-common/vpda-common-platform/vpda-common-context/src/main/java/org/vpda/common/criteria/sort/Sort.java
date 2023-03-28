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
package org.vpda.common.criteria.sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User defined sort. Sort is list of {@link SortItem}. Sort can have custom
 * name or name will be concatenation of {@link SortItem} items names.
 * 
 * @author kitko
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class Sort implements Serializable {
    private static final long serialVersionUID = 4995453118852265358L;

    private static final Sort EMPTY = new Sort(Collections.emptyList());

    private final List<SortItem> items;
    private final String name;

    public static Sort getEmpty() {
        return EMPTY;
    }

    /**
     * Creates sort with name
     * 
     * @param name
     * @param items
     */
    public Sort(String name, List<SortItem> items) {
        this.items = new ArrayList<SortItem>(items);
        this.name = name;
    }

    /**
     * Creates sort with related name
     * 
     * @param items
     */
    public Sort(List<SortItem> items) {
        this.items = new ArrayList<SortItem>(items);
        name = generateName();
    }

    /**
     * Creates Sort from json
     * 
     * @param name
     * @param items
     * @return Sort
     */
    @JsonCreator
    public static Sort fromJson(@JsonProperty("name") String name, @JsonProperty("items") List<SortItem> items) {
        return new Sort(name, items);
    }

    /**
     * Creates single sort item
     * 
     * @param colId
     * @return single sort ascending item
     */
    public static Sort createSingleItemAscSort(String colId) {
        SortItem item = new SortItem(colId);
        return new Sort(Collections.singletonList(item));
    }

    /**
     * @return size of sort items
     */
    public int size() {
        return items.size();
    }

    /**
     * @param index
     * @return {@link SortItem} at index
     */
    public SortItem get(int index) {
        return items.get(index);
    }

    /**
     * @return name of ort
     */
    public String getName() {
        return name;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return items.isEmpty();
    }

    private String generateName() {
        StringBuilder name = new StringBuilder();
        for (SortItem item : items) {
            if (name.length() > 0) {
                name.append(',');
            }
            name.append(item);
        }
        return name.toString();
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * @return items
     */
    public List<SortItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Sort other = (Sort) obj;
        if (items == null) {
            if (other.items != null) {
                return false;
            }
        }
        else if (!items.equals(other.items)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
