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
package org.vpda.common.processor.ctx;

import java.util.Arrays;

import org.vpda.internal.common.util.Assert;

/**
 * Item in class processing or field building
 * 
 * @author kitko
 * @param <T> type of final result
 *
 */
public abstract class ProcessingContext<T> {
    private final ProcessingContext<?> parent;
    private final Class<T> targetClass;
    private final int depth;
    private final Class[] groups;

    private static final Class[] EMPTY_GROUPS = new Class[0];

    ProcessingContext(ProcessingContext<?> parent, Class<T> targetClass) {
        this.parent = parent;
        this.targetClass = Assert.isNotNullArgument(targetClass, "targetClass");
        this.depth = parent != null ? parent.getDepth() + 1 : 0;
        this.groups = null;
    }

    ProcessingContext(ProcessingContext<?> parent, Class<T> targetClass, Class[] groups) {
        this.parent = parent;
        this.targetClass = Assert.isNotNullArgument(targetClass, "targetClass");
        this.depth = parent != null ? parent.getDepth() + 1 : 0;
        this.groups = groups;
    }

    /**
     * @return the request
     */
    public Class[] getGroups() {
        return (groups != null && groups.length > 0) ? groups : (parent != null ? parent.getGroups() : EMPTY_GROUPS);
    }

    /**
     * 
     * @return depth of this context zero based
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return parent of this item, null on root item
     */
    public final ProcessingContext<?> getParent() {
        return parent;
    }

    /**
     * @return root context
     */
    public final ProcessingContext<?> getRootContext() {
        return parent != null ? parent.getRootContext() : this;
    }

    /**
     * @return the targetClass
     */
    public final Class<T> getTargetClass() {
        return targetClass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + depth;
        result = prime * result + Arrays.hashCode(groups);
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
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
        ProcessingContext other = (ProcessingContext) obj;
        if (depth != other.depth)
            return false;
        if (!Arrays.equals(groups, other.groups))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        }
        else if (!parent.equals(other.parent))
            return false;
        if (targetClass == null) {
            if (other.targetClass != null)
                return false;
        }
        else if (!targetClass.equals(other.targetClass))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProcessingContext [parent=" + parent + ", targetClass=" + targetClass + "]";
    }

}
