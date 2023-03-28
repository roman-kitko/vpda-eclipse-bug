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
package org.vpda.common.processor.annotation.eval;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.vpda.common.processor.annotation.GroupFilteringMethod;
import org.vpda.common.processor.annotation.TargetClassMethod;

final class ProcessingContextEvalAnnCache {

    private ProcessingContextEvalAnnCache() {
    }

    private static final ConcurrentMap<Class<? extends Annotation>, Object> groupsFilteringCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<? extends Annotation>, Object> targetClassCache = new ConcurrentHashMap<>();
    private static final Object NOT_FOUND = new Object();

    static Method getGroupsFilteringMethod(Class<? extends Annotation> annClass) {
        Object method = groupsFilteringCache.get(annClass);
        if (method == NOT_FOUND) {
            return null;
        }
        if (method instanceof Method) {
            return Method.class.cast(method);
        }
        for (Method m : annClass.getMethods()) {
            if (m.isAnnotationPresent(GroupFilteringMethod.class) && m.getReturnType().equals(Class[].class)) {
                Object old = groupsFilteringCache.putIfAbsent(annClass, m);
                return old instanceof Method ? (Method) old : m;
            }
        }
        groupsFilteringCache.putIfAbsent(annClass, NOT_FOUND);
        return null;
    }

    static Method getTargetClassMethod(Class<? extends Annotation> annClass) {
        Object method = targetClassCache.get(annClass);
        if (method == NOT_FOUND) {
            return null;
        }
        if (method instanceof Method) {
            return Method.class.cast(method);
        }
        for (Method m : annClass.getMethods()) {
            if (m.isAnnotationPresent(TargetClassMethod.class) && m.getReturnType().equals(Class.class)) {
                Object old = targetClassCache.putIfAbsent(annClass, m);
                return old instanceof Method ? (Method) old : m;
            }
        }
        targetClassCache.putIfAbsent(annClass, NOT_FOUND);
        return null;
    }

}
