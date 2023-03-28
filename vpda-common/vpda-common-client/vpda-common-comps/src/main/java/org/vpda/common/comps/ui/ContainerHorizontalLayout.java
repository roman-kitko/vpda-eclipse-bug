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
 * Horizontal layout
 * 
 * @author kitko
 *
 */
public final class ContainerHorizontalLayout extends AbstractContainerLayout {
    private static final long serialVersionUID = 1073265179275849444L;
    private HorizontalAlignment alignment;

    /**
     * @return allignment
     */
    public HorizontalAlignment getAlignment() {
        return alignment;
    }

    /**
     * Sets layout
     * 
     * @param alignment
     */
    public void setLayoutAlignment(HorizontalAlignment alignment) {
        if (alignment == null) {
            throw new IllegalArgumentException("Alignment argument is null");
        }
        this.alignment = alignment;
    }

    /**
     * @param alignment
     * 
     */
    public ContainerHorizontalLayout(HorizontalAlignment alignment) {
        super();
        setLayoutAlignment(alignment);
    }

    /**
     * @param targetContainer
     * @param alignment
     */
    public ContainerHorizontalLayout(AbstractContainerWithLayoutAC targetContainer, HorizontalAlignment alignment) {
        super(targetContainer);
        setLayoutAlignment(alignment);
    }

}
