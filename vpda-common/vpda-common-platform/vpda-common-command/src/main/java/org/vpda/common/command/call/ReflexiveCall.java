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
package org.vpda.common.command.call;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.vpda.common.util.exceptions.VPDARuntimeException;
import org.vpda.internal.common.util.ClassUtil;

/**
 * Call that uses reflection to explore call. It collects all getters to list
 * call parameters.
 * 
 * @author kitko
 *
 */
public abstract class ReflexiveCall implements Call, Serializable {
    private static final long serialVersionUID = -5961718669144444881L;
    /** Name of call */
    protected final String name;

    /**
     * Creates call with name
     * 
     * @param name
     */
    protected ReflexiveCall(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<String> getParamNames() {
        Collection<Method> getters = ClassUtil.getGetters(getClass());
        Collection<String> names = new ArrayList<String>(getters.size());
        for (Method m : getters) {
            String property = m.getName().substring("get".length());
            property = Character.toLowerCase(property.charAt(0)) + property.substring(1);
            if (!"name".equals(property)) {
                names.add(property);
            }
        }
        return names;
    }

    @Override
    public Object getParamValue(String paramName) {
        Object value = null;
        try {
            value = ClassUtil.getGetterValue(this, paramName);
        }
        catch (Exception e) {
            throw new VPDARuntimeException("Error while invoking getter", e);
        }
        return value;
    }

}
