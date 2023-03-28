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

import java.io.Serializable;
import java.util.EventListener;

/**
 * Listener associated with single component
 * 
 * @author kitko
 * @param <T>
 *
 */
public interface MemberListener<T extends Member> extends EventListener, Serializable {
    /**
     * Notify that environment data around component has changed
     * 
     * @param component   The component this listener is bound into
     * @param changeEvent
     */
    public default void environmentDataChanged(T component, EnvironmentDataChangeEvent changeEvent) {
    }

    /**
     * Notify that environment around component was initialized
     * 
     * @param component
     * @param initEvent
     */
    public default void environmentInitialized(T component, EnvironmentInitEvent initEvent) {
    }

    /**
     * Notify that structure of environment around component has changed
     * 
     * @param component
     * @param event
     */
    public default void environmentStructureChanged(T component, EnvironmentStructureChangeEvent event) {
    }

    /**
     * Notify that there was a change in environment ui layout
     * 
     * @param component
     * @param event
     */
    public default void environmentLayoutChanged(T component, EnvironmentLayoutChangeEvent event) {
    }

}
