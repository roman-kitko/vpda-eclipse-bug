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
package org.vpda.common.command;

import org.vpda.common.util.ClassMethod;

/**
 * Command event fired from method.
 * 
 * @author kitko
 *
 */
public final class MethodCommandEvent implements CommandEvent {
    private static final long serialVersionUID = 7315958075862374072L;
    private final Object actionId;
    private final ClassMethod classMethod;

    @Override
    public Object getActionId() {
        return actionId;
    }

    @Override
    public ClassMethod getSource() {
        return classMethod;
    }

    /**
     * Creates MethodCommandEvent
     * 
     * @param actionId
     * @param classMethod
     */
    public MethodCommandEvent(Object actionId, ClassMethod classMethod) {
        super();
        this.actionId = actionId;
        this.classMethod = classMethod;
    }

    /**
     * Creates MethodCommandEvent
     * 
     * @param classMethod
     */
    public MethodCommandEvent(ClassMethod classMethod) {
        this.classMethod = classMethod;
        this.actionId = null;
    }

    /**
     * Creates MethodCommandEvent
     * 
     * @param clazz
     * @param method
     */
    public MethodCommandEvent(Class clazz, String method) {
        this(new ClassMethod(clazz, method));
    }

}
