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

import org.vpda.common.comps.loc.AbstractCompLocValue;

/**
 * @author kitko
 * @param <V>
 *
 * @param <L>
 */
public interface ModifiableContainer<V, L extends AbstractCompLocValue> extends Container<V, L> {

    /**
     * @param component
     * @return this
     */
    @SuppressWarnings("hiding")
    public <V, T extends AbstractCompLocValue> ModifiableContainer addComponent(Component<V, T> component);

    /**
     * @param component
     * @return this
     */
    @SuppressWarnings("hiding")
    public <V, T extends AbstractCompLocValue> ModifiableContainer addComponentIfNotNull(Component<V, T> component);

    /**
     * @param component
     * @return removed component if contained
     */
    public Component removeComponent(Component component);

    /**
     * Replace one component with another in this container
     * 
     * @param oldComp
     * @param newComp
     * @return current component
     */
    public Component<?, ?> replaceComponent(Component<?, ?> oldComp, Component<?, ?> newComp);

    /**
     * @param index
     * @return emoved component at inedx
     */
    public Component removeComponent(int index);

    /**
     * @param id
     * @return removed component
     */
    public Component removeComponent(String id);
}