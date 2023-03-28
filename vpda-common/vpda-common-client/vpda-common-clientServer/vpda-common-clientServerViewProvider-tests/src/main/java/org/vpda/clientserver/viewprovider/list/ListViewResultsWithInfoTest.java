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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/** Test */
public class ListViewResultsWithInfoTest {

    /** Test */
    @Test
    public void testGetResults() {
        assertNotNull(createTestee().getResults());
    }

    /** Test */
    @Test
    public void testGetInfo() {
        assertNotNull(createTestee().getInfo());
    }

    /** Test */
    @Test
    public void testGetColumnValue() {
        assertEquals("val11", createTestee().getColumnValue(0, "col1"));
        assertEquals("val12", createTestee().getColumnValue(0, "col2"));
        assertEquals("val21", createTestee().getColumnValue(1, "col1"));
        assertEquals("val22", createTestee().getColumnValue(1, "col2"));
        assertNull(createTestee().getColumnValue(1, "col3"));
    }

    /** Test */
    @Test
    public void testGetRowsCount() {
        assertEquals(2, createTestee().getRowsCount());
    }

    /** Test */
    @Test
    public void testGetRow() {
        ListViewRow row2 = new ListViewRow("val21", "val22");
        assertEquals(row2, createTestee().getRow(1));
    }

    /** Test */
    @Test
    public void testGetMapedRow() {
        assertEquals(new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val11", "val12"), "val12"), createTestee().getMappedRow(0));
        assertEquals(new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val21", "val22"), "val22"), createTestee().getMappedRow(1));
    }

    /** Test */
    @Test
    public void testGetMapedRowWithConcreteColumns() {
        assertEquals(new ListViewMappedRow(Arrays.asList("col1"), Arrays.asList("val11"), "val12"), createTestee().getMappedRow(0, Arrays.<String>asList("col1")));
        assertEquals(new ListViewMappedRow(Arrays.asList("col2"), Arrays.asList("val22"), "val22"), createTestee().getMappedRow(1, Arrays.<String>asList("col2")));
    }

    private ListViewResultsWithInfo createTestee() {
        ListViewRow row1 = new ListViewRow("val11", "val12");
        ListViewRow row2 = new ListViewRow("val21", "val22");
        ListViewResults results = new ListViewResults(Arrays.asList(row1, row2), true, true);
        ListViewResultsInfo info = new ListViewResultsInfo(Arrays.asList("col1", "col2"), "col2");
        ListViewResultsWithInfo result = new ListViewResultsWithInfo(results, info);
        return result;
    }

}
