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

/**
 * Abstract impl of ContainerLayout
 * 
 * @author kitko
 *
 */
public abstract class AbstractContainerLayout implements ContainerLayout {
    private static final long serialVersionUID = -4740174521774861055L;
    /** Our target container which uses this layout */
    protected ContainerWithLayout targetContainer;

    /**
     * Constructor for subclasses
     */
    protected AbstractContainerLayout() {
    }

    /**
     * Creates layout and sets targetContainer
     * 
     * @param targetContainer
     */
    protected AbstractContainerLayout(AbstractContainerWithLayoutAC targetContainer) {
        setTargetContainer(targetContainer);
    }

    @Override
    public void componentAdded(Component component, ComponentLayoutConstraint contraint) {
    }

    @Override
    public void componentRemoved(Component component, ComponentLayoutConstraint contraint) {
    }

    @Override
    public ContainerWithLayout getTargetContainer() {
        return targetContainer;
    }

    @Override
    public void setTargetContainer(ContainerWithLayout targetContainer) {
        if (targetContainer == null) {
            throw new IllegalArgumentException("TargetContainer argument is null");
        }
        this.targetContainer = targetContainer;
        if (this.targetContainer.getContainerLayout() != this) {
            targetContainer.setContainerLayout(this);
        }
    }

}
