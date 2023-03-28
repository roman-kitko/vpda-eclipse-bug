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

import org.vpda.internal.common.util.Assert;

/**
 * Item for class processing
 * 
 * @author kitko
 * @param <T> type of class being processed
 */
public class ClassContext<T> extends ProcessingContext<T> {

    /**
     * @param parent
     * @param clazz
     * @param targetClass
     * @param instance
     * @param groups
     */
    protected ClassContext(ProcessingContext<?> parent, Class<?> clazz, Class<T> targetClass, Object instance, Class[] groups) {
        super(parent, targetClass, groups);
        this.instance = instance;
        this.clazz = Assert.isNotNullArgument(clazz, "clazz");
    }

    private final Object instance;
    private final Class<?> clazz;

    /**
     * @return instance of processed class. Can be null
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * @return clazz
     */
    public Class<?> getProcessedClass() {
        return clazz;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
        result = prime * result + ((instance == null) ? 0 : instance.hashCode());
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
        ClassContext other = (ClassContext) obj;
        if (clazz == null) {
            if (other.clazz != null)
                return false;
        }
        else if (!clazz.equals(other.clazz))
            return false;
        if (instance == null) {
            if (other.instance != null)
                return false;
        }
        else if (!instance.equals(other.instance))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClassContext [instance=");
        builder.append(instance);
        builder.append(", clazz=");
        builder.append(clazz);
        builder.append(", Parent=");
        builder.append(getParent());
        builder.append(", TargetClass=");
        builder.append(getTargetClass());
        builder.append("]");
        return builder.toString();
    }

    /**
     * @param <T>
     * @param clazz
     * @param targetClass
     * @param groups
     * @return new root Class context
     */
    public static <T> ClassContext<T> createRootClassContext(Class<?> clazz, Class<T> targetClass, Class<?>... groups) {
        return new ClassContext<T>(null, clazz, targetClass, null, groups);
    }

    /**
     * @param <T>
     * @param clazz
     * @param targetClass
     * @param instance
     * @param groups
     * @return new root Class context
     */
    public static <T> ClassContext<T> createRootInstanceClassContext(Class<?> clazz, Class<T> targetClass, Object instance, Class<?>... groups) {
        return new ClassContext<T>(null, clazz, targetClass, instance, groups);
    }

    /**
     * @param <T>
     * @param parent
     * @param clazz
     * @param targetClass
     * @param groups
     * @return new child Class context
     */
    public static <T> ClassContext<T> createClassContext(ProcessingContext<?> parent, Class<?> clazz, Class<T> targetClass, Class<?>... groups) {
        return new ClassContext<T>(parent, clazz, targetClass, null, groups);
    }

    /**
     * @param <T>
     * @param parent
     * @param clazz
     * @param targetClass
     * @param instance
     * @param groups
     * @return new child context
     */
    public static <T> ClassContext<T> createClassInstanceContext(ProcessingContext<?> parent, Class<?> clazz, Class<T> targetClass, Object instance, Class<?>... groups) {
        return new ClassContext<T>(parent, clazz, targetClass, instance, groups);
    }

    /**
     * Create a child class context. Child will point to same clazz, will have this
     * context as parent, but will have own target class
     * 
     * @param <C>
     * @param targetClass
     * @return child context
     */
    public <C> ClassContext<C> createChild(Class<C> targetClass) {
        return createClassContext(this, clazz, targetClass);
    }

    /**
     * Create a child class context. Child will point to same class, will have this
     * context as parent, but will have own target class and instance
     * 
     * @param <C>
     * @param targetClass
     * @param instance
     * @return child context
     */
    public <C> ClassContext<C> createChild(Class<C> targetClass, C instance) {
        return createClassInstanceContext(this, clazz, targetClass, instance);
    }

}