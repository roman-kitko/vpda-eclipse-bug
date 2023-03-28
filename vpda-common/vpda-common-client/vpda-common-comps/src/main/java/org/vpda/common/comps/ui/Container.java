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

import java.util.List;

import org.vpda.common.comps.Member;
import org.vpda.common.comps.MemberContainer;
import org.vpda.common.comps.loc.AbstractCompLocValue;

/**
 * @author kitko
 * @param <V>
 *
 * @param <L>
 */
public interface Container<V, L extends AbstractCompLocValue> extends Component<V, L>, MemberContainer<org.vpda.common.comps.ui.Component> {
    /**
     * @param childId
     * @return child component or null if not found
     */
    public abstract Component getComponent(String childId);

    /**
     * @param childId
     * @param targetType
     * @return Component
     */
    public <X extends Component> X getComponent(String childId, Class<X> targetType);

    /**
     * @return number of components
     */
    public abstract int getComponentsCount();

    /**
     * @param <V>
     * @param <T>
     * @param i
     * @return component at index
     */
    @SuppressWarnings("hiding")
    public abstract <V, T extends AbstractCompLocValue> Component<V, T> getComponent(int i);

    /**
     * Gets all components
     * 
     * @return all components
     */
    public abstract List<org.vpda.common.comps.ui.Component<?, ?>> getComponents();

    @Override
    public List<org.vpda.common.comps.ui.Component<?, ?>> getAllMembers();
}
