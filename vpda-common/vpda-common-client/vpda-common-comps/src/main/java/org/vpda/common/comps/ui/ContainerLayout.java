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

import java.io.Serializable;

/**
 * Layout somehow defines layout of components in container This interface just
 * defines some static memebership components to group We will use this iface on
 * client side to create real panel layout of components
 * 
 * @author kitko
 *
 */
public interface ContainerLayout extends Serializable {
    /**
     * Notify method of componnet add
     * 
     * @param component
     * @param contraint
     */
    public void componentAdded(Component component, ComponentLayoutConstraint contraint);

    /**
     * Notify method of component removal
     * 
     * @param component
     * @param contraint - constraint of component
     */
    public void componentRemoved(Component component, ComponentLayoutConstraint contraint);

    /**
     * @return target container
     */
    public ContainerWithLayout getTargetContainer();

    /**
     * Sets target container
     * 
     * @param targetContainer
     */
    public void setTargetContainer(ContainerWithLayout targetContainer);
}
