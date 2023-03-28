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
package org.vpda.clientserver.viewprovider.list;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author rki
 *
 */
public class ListViewResultsTest {

    /**
     * Test method
     */
    @Test
    public void testGetRow() {
        assertEquals(1, createTestee().getRow(0).getColumnValue(0));
        assertEquals("hello", createTestee().getRow(0).getColumnValue(1));
        assertEquals(2, createTestee().getRow(1).getColumnValue(0));
        assertEquals("hello", createTestee().getRow(0).getColumnValue(1));
    }

    /**
     * Test method
     */
    @Test
    public void testGetRowsCount() {
        assertEquals(2, createTestee().getRowsCount());
    }

    private ListViewResults createTestee() {
        List<ListViewRow> rows = new ArrayList<ListViewRow>(2);
        rows.add(new ListViewRow(new Object[] { 1, "hello" }));
        rows.add(new ListViewRow(new Object[] { 2, "world" }));
        return new ListViewResults(rows, true, true);
    }

}
