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
package org.vpda.common.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Node in hierarchy
 * 
 * @author kitko
 *
 * @param <T> type of node
 * @param <I> type of id
 */
public interface HierarchicalNode<T, I> extends IdentifiedObject<I> {
    /**
     * 
     * @return parent node or null if root
     */
    public HierarchicalNode<T, I> getParent();

    /**
     * 
     * @return path to this node, like 1/2/3
     */
    public String getRootPath();

    /**
     * Sets roor path
     * 
     * @param rootPath
     */
    public void setRootPath(String rootPath);

    /**
     * 
     * @return path of ids
     */
    public List<? extends I> getPath();

    /**
     * Updates root path
     */
    default public void updateRootPath() {
        setRootPath(computeRootPathString(this));
    }

    /**
     * 
     * @param node
     * @return parsed long ids
     */
    public static <T> List<Long> parseLongIds(HierarchicalNode<T, ?> node) {
        List<Long> ids = new ArrayList<Long>();
        String p = node.getRootPath();
        String[] split = p.split("/");
        for (String id : split) {
            ids.add(Long.valueOf(id));
        }
        return ids;
    }

    /**
     * @param parent
     * @param id
     * @return childRootPath
     */
    public static <T> List<Long> childRootPath(HierarchicalNode<T, ?> parent, Long id) {
        if (parent == null) {
            return Collections.singletonList(id);
        }
        List<Long> ids = new ArrayList<Long>();
        ids.addAll(parseLongIds(parent));
        ids.add(id);
        return ids;
    }

    /**
     * @param node
     * @return root path
     */
    public static <T, I> String computeRootPathString(HierarchicalNode<T, I> node) {
        if (node.getParent() != null) {
            return node.getParent().getRootPath() + '/' + node.getId();
        }
        else {
            return node.getId().toString();
        }
    }
}
