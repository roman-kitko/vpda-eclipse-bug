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
package org.vpda.common.comps.ui;

import org.vpda.common.comps.ui.resolved.ResolvingUIBasedComponent;

/**
 * Optional UI component accesor native component binding. Can be created by
 * ComponetUIResolver
 * 
 * @author kitko
 *
 * @param <VALUE> type of value we will return and accept
 * @param <UI>    type of main UI component
 */
public interface UIComponentAccessor<VALUE, UI> {

    /**
     * 
     * @return real ui component
     */
    public UI getUIComponent();

    /**
     * 
     * @return updated value fromm ui
     */
    public VALUE getUpdatedValue();

    /**
     * Apply new value to UI
     * 
     * @param value
     * @param comp
     */
    public void updateValueToUI(VALUE value, ResolvingUIBasedComponent<VALUE, ? extends UIComponentAccessor<VALUE, UI>> comp);

}
