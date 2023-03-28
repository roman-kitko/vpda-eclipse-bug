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

import java.util.List;

import org.vpda.internal.common.util.Assert;

/**
 * Processing inner class for fields
 * 
 * @author kitko
 * @param <T>
 */
public final class ClassItemContext<T> extends ClassContext<List> {

    private ClassItemContext(ProcessingContext<?> parent, Class<?> clazz, Class<T> targetItemClass, Class[] groups) {
        super(parent, clazz, List.class, null, groups);
        this.targetItemClass = Assert.isNotNullArgument(targetItemClass, "targetItemClass");
    }

    private final Class<T> targetItemClass;

    /**
     * @return the targetItemClass
     */
    public final Class<T> getTargetItemClass() {
        return targetItemClass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((targetItemClass == null) ? 0 : targetItemClass.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClassItemContext other = (ClassItemContext) obj;
        if (targetItemClass == null) {
            if (other.targetItemClass != null)
                return false;
        }
        else if (!targetItemClass.equals(other.targetItemClass))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClassItemContext [targetItemClass=");
        builder.append(targetItemClass);
        builder.append(", Instance=");
        builder.append(getInstance());
        builder.append(", Type=");
        builder.append(getProcessedClass());
        builder.append(", Parent=");
        builder.append(getParent());
        builder.append(", TargetClass=");
        builder.append(getTargetClass());
        builder.append("]");
        return builder.toString();
    }

    /**
     * @param <T>
     * @param parent
     * @param clazz
     * @param targetItemClass
     * @param groups
     * @return class item context
     */
    public static <T> ClassItemContext<T> createClassItemContext(ProcessingContext<?> parent, Class<?> clazz, Class<T> targetItemClass, Class<?>... groups) {
        return new ClassItemContext<T>(parent, clazz, targetItemClass, groups);
    }

    /**
     * @param <T>
     * @param clazz
     * @param targetItemClass
     * @param groups
     * @return class item context
     */
    public static <T> ClassItemContext<T> createRootClassItemContext(Class<?> clazz, Class<T> targetItemClass, Class<?>... groups) {
        return new ClassItemContext<T>(null, clazz, targetItemClass, groups);
    }

}