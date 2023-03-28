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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.vpda.common.comps.ui.ContainerBorderLayout.BorderLayoutConstraint;

/**
 * @author kitko
 *
 */
@SuppressWarnings("unchecked")
public class ContainerBorderLayoutTest {

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerBorderLayout#getCenterComponent()}.
     */
    @Test
    public void testGetCenterComponent() {
        ContainerBorderLayout testee = createTestee();
        testee.getTargetContainer().addComponent(new PushButtonAC("testButton"), ContainerBorderLayout.BorderLayoutConstraint.CENTER);
        assertNotNull(testee.getCenterComponent());
        assertNull(testee.getEastComponent());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerBorderLayout#getComponent(org.vpda.common.comps.ui.ContainerBorderLayout.BorderLayoutConstraint)}.
     */
    @Test
    public void testGetComponent() {
        ContainerBorderLayout testee = createTestee();
        testee.getTargetContainer().addComponent(new PushButtonAC("testButton"), BorderLayoutConstraint.CENTER);
        assertNotNull(testee.getComponent(BorderLayoutConstraint.CENTER));
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerBorderLayout#getEastComponent()}.
     */
    @Test
    public void testGetEastComponent() {
        ContainerBorderLayout testee = createTestee();
        testee.getTargetContainer().addComponent(new PushButtonAC("testButton"), BorderLayoutConstraint.EAST);
        assertNotNull(testee.getEastComponent());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerBorderLayout#getNorthComponent()}.
     */
    @Test
    public void testGetNorthComponent() {
        ContainerBorderLayout testee = createTestee();
        testee.getTargetContainer().addComponent(new PushButtonAC("testButton"), BorderLayoutConstraint.NORTH);
        assertNotNull(testee.getNorthComponent());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerBorderLayout#getSouthComponent()}.
     */
    @Test
    public void testGetSouthComponent() {
        ContainerBorderLayout testee = createTestee();
        testee.getTargetContainer().addComponent(new PushButtonAC("testButton"), BorderLayoutConstraint.SOUTH);
        assertNotNull(testee.getSouthComponent());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerBorderLayout#getWestComponent()}.
     */
    @Test
    public void testGetWestComponent() {
        ContainerBorderLayout testee = createTestee();
        testee.getTargetContainer().addComponent(new PushButtonAC("testButton"), BorderLayoutConstraint.WEST);
        assertNotNull(testee.getWestComponent());
    }

    private ContainerBorderLayout createTestee() {
        ContainerAC container = new ContainerAC("test");
        ContainerBorderLayout cb = new ContainerBorderLayout(container);
        return cb;
    }

}
