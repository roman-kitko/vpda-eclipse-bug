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
package org.vpda.common.comps;

import java.text.MessageFormat;

import org.vpda.common.ioc.objectresolver.ObjectResolver;

/**
 * Describes environment data change event
 * 
 * @author kitko
 *
 */
public final class EnvironmentDataChangeEvent extends EnvironmentEvent {
    private final Object changedData;

    /**
     * Creates EnvironmentChangeEvent
     * 
     * @param env
     * @param changedData
     */
    public EnvironmentDataChangeEvent(ObjectResolver env, Object changedData) {
        super(env);
        this.changedData = changedData;
    }

    /**
     * @return changed data
     */
    public Object getChangedData() {
        return changedData;
    }

    /**
     * @param type
     * @return cast data
     */
    public <Z> Z castChangedData(Class<Z> type) {
        if (changedData == null) {
            return null;
        }
        if (type.isInstance(changedData)) {
            return type.cast(changedData);
        }
        throw new IllegalStateException(MessageFormat.format("Changed data is of incompatible type. Required [{0}] but was [{1}]", type.getName(), changedData.getClass().getName()));
    }

}
