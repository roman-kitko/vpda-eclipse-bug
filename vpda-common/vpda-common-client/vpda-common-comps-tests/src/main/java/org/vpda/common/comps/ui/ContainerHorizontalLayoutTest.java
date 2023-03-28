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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class ContainerHorizontalLayoutTest {

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerHorizontalLayout#getAlignment()}.
     */
    @Test
    public void testGetAlignment() {
        ContainerHorizontalLayout testee = new ContainerHorizontalLayout(HorizontalAlignment.CENTER);
        assertEquals(HorizontalAlignment.CENTER, testee.getAlignment());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.ui.ContainerHorizontalLayout#setLayoutAlignment(org.vpda.common.comps.ui.HorizontalAlignment)}.
     */
    @Test
    public void testSetLayoutAlignment() {
        ContainerHorizontalLayout testee = new ContainerHorizontalLayout(HorizontalAlignment.CENTER);
        testee.setLayoutAlignment(HorizontalAlignment.LEFT);
        assertEquals(HorizontalAlignment.LEFT, testee.getAlignment());
    }

    /**
     * Test method
     */
    @Test
    public void testContainerFlowLayoutImplViewProviderComponentContainerLayoutAlignment() {
        ContainerHorizontalLayout testee = new ContainerHorizontalLayout(HorizontalAlignment.RIGHT);
        assertNull(testee.getTargetContainer());
        testee = new ContainerHorizontalLayout(new ContainerAC("test"), HorizontalAlignment.LEADING);
        assertNotNull(testee.getTargetContainer());
        assertEquals(HorizontalAlignment.LEADING, testee.getAlignment());

    }

}
