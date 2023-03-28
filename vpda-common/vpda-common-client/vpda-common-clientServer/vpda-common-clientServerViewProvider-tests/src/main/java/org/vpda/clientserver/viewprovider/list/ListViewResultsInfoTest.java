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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class ListViewResultsInfoTest {

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewResultsInfo#getColumn(int)}.
     */
    @Test
    public void testgetColumn() {
        ListViewResultsInfo info = createTestee();
        assertEquals(2, info.getColumnsCount());
        assertEquals("col1", info.getColumn(0));
        assertEquals("col2", info.getColumn(1));
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewResultsInfo#getColumnIndex(java.lang.String)}.
     */
    @Test
    public void testGetColumnIndex() {
        ListViewResultsInfo info = createTestee();
        assertEquals(2, info.getColumnsCount());
        assertEquals(0, info.getColumnIndex("col1"));
        assertEquals(1, info.getColumnIndex("col2"));
        assertEquals(-1, info.getColumnIndex("col3"));
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewResultsInfo#getColumnIndex(java.lang.String)}.
     */
    @Test
    public void testGetManadatoryColumnIndex() {
        ListViewResultsInfo info = createTestee();
        assertEquals(2, info.getColumnsCount());
        assertEquals(0, info.getColumnIndex("col1"));
        assertEquals(1, info.getColumnIndex("col2"));
        try {
            assertEquals(-1, info.getMandatoryColumnIndex("col3"));
            fail("Should fail for col3");
        }
        catch (Exception e) {

        }
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewResultsInfo#containsColumn(java.lang.String)}.
     */
    @Test
    public void testContainsColumn() {
        ListViewResultsInfo info = createTestee();
        assertTrue(info.containsColumn("col1"));
        assertTrue(info.containsColumn("col2"));
        assertFalse(info.containsColumn("col3"));
    }

    private ListViewResultsInfo createTestee() {
        return new ListViewResultsInfo(Arrays.asList("col1", "col2"), "col1");
    }

}
