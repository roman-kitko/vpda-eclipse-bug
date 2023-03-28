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

import java.util.HashMap;
import java.util.Map;

/**
 * Default Border layout impl
 * 
 * @author kitko
 *
 */
public final class ContainerBorderLayout extends AbstractContainerLayout {
    private static final long serialVersionUID = 9165206386888078633L;

    /**
     * Constraints for Borderlayout
     * 
     * @author kitko
     *
     */
    public static enum BorderLayoutConstraint implements ComponentLayoutConstraint {
        /** North location */
        NORTH,
        /** South location */
        SOUTH,
        /** West location */
        WEST,
        /** East location */
        EAST,
        /** Center location */
        CENTER;
    }

    private Map<BorderLayoutConstraint, String> compIds;

    /**
     * 
     */
    public ContainerBorderLayout() {
        compIds = new HashMap<BorderLayoutConstraint, String>(3);
    }

    /**
     * @param targetContainer
     */
    public ContainerBorderLayout(AbstractContainerWithLayoutAC targetContainer) {
        super(targetContainer);
        compIds = new HashMap<BorderLayoutConstraint, String>(3);
    }

    /**
     * @return center component
     */
    public Component getCenterComponent() {
        return getComponent(BorderLayoutConstraint.CENTER);
    }

    /**
     * @param location
     * @return component at location
     */
    public Component getComponent(BorderLayoutConstraint location) {
        String compId = compIds.get(location);
        if (compId == null) {
            return null;
        }
        return this.targetContainer.getComponent(compId);
    }

    /**
     * Gets constraint for component id
     * 
     * @param compId
     * @return constraint
     */
    public BorderLayoutConstraint getConstraint(String compId) {
        for (Map.Entry<BorderLayoutConstraint, String> entry : compIds.entrySet()) {
            if (entry.getValue().equals(compId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @return east component
     */
    public Component getEastComponent() {
        return getComponent(BorderLayoutConstraint.EAST);
    }

    /**
     * @return north component
     */
    public Component getNorthComponent() {
        return getComponent(BorderLayoutConstraint.NORTH);
    }

    /**
     * @return south component
     */
    public Component getSouthComponent() {
        return getComponent(BorderLayoutConstraint.SOUTH);
    }

    /**
     * @return west component
     */
    public Component getWestComponent() {
        return getComponent(BorderLayoutConstraint.WEST);
    }

    @Override
    public void componentAdded(Component component, ComponentLayoutConstraint contraint) {
        if (contraint instanceof BorderLayoutConstraint) {
            compIds.put((BorderLayoutConstraint) contraint, component.getId());
        }
    }

    @Override
    public void componentRemoved(Component component, ComponentLayoutConstraint contraint) {
        if (contraint instanceof BorderLayoutConstraint) {
            compIds.remove(contraint);
        }
    }

}
