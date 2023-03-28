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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.easymock.EasyMock;
import org.vpda.common.command.Command;

/**
 * @author kitko
 *
 */
public class ListViewACTest extends AbstractComponentTest {

    @Override
    protected Object createTestValue() {
        return Arrays.asList("item1", "item2");
    }

    @Override
    protected ListAC createTestee() {
        return new ListAC<String>("list1", Arrays.asList("item1", "item2", "item3"), Arrays.asList("item1", "item2"));
    }

    /**
     * Test combobox method
     */
    public void testList() {
        ListAC<String> list = new ListAC<String>("list1", Arrays.asList("item1", "item2", "item3"), Arrays.asList("item1", "item2"));
        ListAC.ListRenderer renderer = EasyMock.createMock(ListAC.ListRenderer.class);
        list.setRenderer(renderer);
        assertSame(renderer, list.getRenderer());
        Command selectionCmd = EasyMock.createMock(Command.class);
        list.setSelectionCommand(selectionCmd);
        assertSame(selectionCmd, list.getSelectionCommand());
        list.setSelectionMode(SelectionMode.SINGLE_SELECTION);
        assertEquals(SelectionMode.SINGLE_SELECTION, list.getSelectionMode());
        list.setSelectedIndices(new int[] { 1, 2 });
        assertArrayEquals(new int[] { 1, 2 }, list.getSelectedIndices());
        list.clearSelection();
        assertNull(list.getSelectedIndices());
        list.setValue(Arrays.asList("item2"));
        assertEquals(Arrays.asList("item2"), list.getValue());
        assertArrayEquals(new int[] { 1 }, list.getSelectedIndices());
        list.setSelectedIndices(new int[] { 1, 2 });
        assertEquals(Arrays.asList("item2", "item3"), list.getValue());
    }

}
