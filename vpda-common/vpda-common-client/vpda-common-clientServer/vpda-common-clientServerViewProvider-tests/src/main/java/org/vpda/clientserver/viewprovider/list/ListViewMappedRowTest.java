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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/** Test */
public class ListViewMappedRowTest {

    /** Test */
    @Test
    public void testGetColumns() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals(Arrays.asList("col1", "col2"), row.getColumns());
    }

    /** Test */
    @Test
    public void testGetColumnName() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals("col1", row.getColumnName(0));
        assertEquals("col2", row.getColumnName(1));
    }

    /** Test */
    @Test
    public void testGetColumnValueString() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals("val1", row.getColumnValue("col1"));
        assertEquals("val2", row.getColumnValue("col2"));
        assertNull(row.getColumnValue("col3"));
    }

    /** Test */
    @Test
    public void testGetColumnCount() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals(2, row.getColumnCount());
    }

    /** Test */
    @Test
    public void testGetColumnIndex() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals(0, row.getColumnIndex("col1"));
        assertEquals(1, row.getColumnIndex("col2"));
        assertEquals(-1, row.getColumnIndex("col3"));
    }

    /** Test */
    @Test
    public void testContainsColumn() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertTrue(row.containsColumn("col1"));
        assertTrue(row.containsColumn("col2"));
        assertFalse(row.containsColumn("col3"));
    }

    /** Test */
    @Test
    public void testGetColumnValueInt() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals("val1", row.getColumnValue(0));
        assertEquals("val2", row.getColumnValue(1));
    }

    /** Test */
    @Test
    public void testConvertRow() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals(new ListViewRow("val2"), row.convertRow(Arrays.asList("col2")));
    }

    /** Test */
    @Test
    public void testConvertToMappedRow() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1");
        assertEquals(new ListViewMappedRow(Arrays.asList("col2"), Arrays.asList("val2"), "val1"), row.convertToMappedRow(Arrays.asList("col2")));
    }

    /** Test */
    @Test
    public void testCreateFromListViewRow() {
        ListViewMappedRow row = new ListViewMappedRow(Arrays.asList("col1", "col2"), new ListViewRow("val1", "val2"), "val1");
        assertEquals(new ListViewMappedRow(Arrays.asList("col1", "col2"), Arrays.asList("val1", "val2"), "val1"), row);
    }

}
