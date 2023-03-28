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
 * @author kitko
 *
 */
public abstract class AbstractButtonLocValueImplTest {

    /**
     * Test method for
     * {@link org.vpda.common.comps.loc.AbstractButtonLocValue#getIconValue()}.
     */
    @Test
    public void testGetIconLocValue() {
        AbstractButtonLocValue.Builder<AbstractButtonLocValue> b = createButtonLocValue();
        IconLocValue i = new IconLocValue("testIcon", new byte[0]);
        b.setIcon(i);
        assertSame(i, b.build().getIconValue());

    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.loc.AbstractButtonLocValue#getTooltip()}.
     */
    @Test
    public void testGetTooltip() {
        AbstractButtonLocValue.Builder<AbstractButtonLocValue> b = createButtonLocValue();
        String tooltip = "tooltip";
        b.setTooltip(tooltip);
        assertEquals(tooltip, b.build().getTooltip());
    }

    /**
     * Test method for
     * {@link org.vpda.common.comps.loc.AbstractButtonLocValue#getLabel()}.
     */
    @Test
    public void testGetLabel() {
        AbstractButtonLocValue.Builder<AbstractButtonLocValue> b = createButtonLocValue();
        String label = "label";
        b.setLabel(label);
        assertEquals(label, b.build().getLabel());
    }

    /**
     * @param <T>
     * @return AbstractButtonLocValue
     */
    protected abstract <T extends AbstractButtonLocValue> AbstractButtonLocValue.Builder<T> createButtonLocValue();

}
