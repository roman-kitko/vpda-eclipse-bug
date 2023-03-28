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
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.easymock.EasyMock;
import org.vpda.common.command.Command;

/**
 * @author kitko
 *
 */
public class ComboBoxACTest extends AbstractComponentTest {

    @Override
    protected Object createTestValue() {
        return "item1";
    }

    @Override
    protected ComboBoxAC createTestee() {
        return new ComboBoxAC<String>("combo1", Arrays.asList("item1", "item2"), "item1");
    }

    /**
     * Test combobox method
     */
    public void testCombo() {
        ComboBoxAC<String> combo = new ComboBoxAC<String>("combo1", Arrays.asList("item1", "item2"), "item1");
        Command cmd = EasyMock.createMock(Command.class);
        combo.setCommand(cmd);
        assertSame(cmd, combo.getCommand());
        ComboBoxAC.ComboxBoxRenderer renderer = EasyMock.createMock(ComboBoxAC.ComboxBoxRenderer.class);
        combo.setRenderer(renderer);
        assertSame(renderer, combo.getRenderer());

        combo.setSelectedIndex(1);
        assertEquals("item2", combo.getValue());
        combo.setValue("item1");
        assertEquals(0, combo.getSelectedIndex());
        assertEquals("item1", combo.getValue());
    }

}
