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
package org.vpda.clientserver.viewprovider.autocomplete;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vpda.internal.common.util.ClassUtil;
import org.vpda.internal.common.util.StringUtil;

/**
 * Utils for autocompletions
 * 
 * @author kitko
 *
 */
public final class AutoCompleteUtils {

    private AutoCompleteUtils() {
    }

    /**
     * Collect autocompletions for all propertiers for class
     * 
     * @param clazz
     * @return list of fields
     */
    public static List<AutoCompleteField> collectAutoCompleteFieldForGetters(Class<?> clazz) {
        Collection<Method> getters = ClassUtil.getGetters(clazz);
        List<AutoCompleteField> fields = new ArrayList<>(getters.size());
        for (Method m : getters) {
            String propertyName = m.getName();
            if (propertyName.startsWith("get")) {
                propertyName.substring(3);
            }
            else if (propertyName.startsWith("is")) {
                propertyName.substring(2);
            }
            propertyName = StringUtil.uncapitalize(propertyName);
            AutoCompleteField field = AutoCompleteField.create(propertyName, m.getReturnType());
            fields.add(field);
        }
        return fields;
    }

}
