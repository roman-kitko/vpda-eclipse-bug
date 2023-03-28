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
package org.vpda.common.comps.loc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Test for LabelLocValueImpl
 * 
 * @author kitko
 *
 */
public class LabelLocValueImplTest {

    /**
     * Test method for
     * {@link org.vpda.common.comps.loc.LabelLocValue#getIconValue()}.
     */
    @Test
    public void testGetIconValue() {
        LabelLocValue.Builder l = createTestee();
        IconLocValue i = new IconLocValue("testPath", new byte[0]);
        l.setIcon(i);
        assertSame(i, l.build().getIconValue());
    }

    /**
     * Test method for {@link org.vpda.common.comps.loc.LabelLocValue#getTooltip()}.
     */
    @Test
    public void testGetTooltip() {
        LabelLocValue.Builder l = createTestee();
        String tooltip = "tooltip";
        l.setTooltip(tooltip);
        assertEquals(tooltip, l.build().getTooltip());
    }

    /**
     * Test method for {@link org.vpda.common.comps.loc.LabelLocValue#getLabel()}.
     */
    @Test
    public void testGetLabel() {
        LabelLocValue.Builder l = createTestee();
        String label = "label";
        l.setLabel(label);
        assertEquals(label, l.build().getLabel());
    }

    /**
     * Test
     */
    @Test
    public void testLabelLocValueImplStringIconLocValueString() {
        String tooltip = "tooltip";
        String label = "label";
        IconLocValue i = new IconLocValue("testPath", new byte[0]);
        LabelLocValue l = new LabelLocValue(label, i, tooltip);
        assertEquals(tooltip, l.getTooltip());
        assertEquals(label, l.getLabel());
        assertSame(i, l.getIconValue());
    }

    private LabelLocValue.Builder createTestee() {
        return new LabelLocValue.Builder();
    }

}
