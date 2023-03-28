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
public class ToggleButtonLocValueImplTest extends AbstractButtonLocValueImplTest {

    /**
     * Test
     */
    @Test
    public void testToggleButtonLocValueImplStringIconLocValueString() {
        String label = "label";
        String tooltip = "tooltip";
        IconLocValue i = new IconLocValue("testPath", new byte[0]);
        ToggleButtonLocValue p = new ToggleButtonLocValue(label, i, tooltip);
        assertEquals(label, p.getLabel());
        assertSame(i, p.getIconValue());
    }

    /**
     */
    @Test
    public void testToggleButtonLocValueImplString() {
        String label = "label";
        PushButtonLocValue p = new PushButtonLocValue(label);
        assertEquals(label, p.getLabel());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ToggleButtonLocValue.Builder createButtonLocValue() {
        return new ToggleButtonLocValue.Builder();
    }

}
