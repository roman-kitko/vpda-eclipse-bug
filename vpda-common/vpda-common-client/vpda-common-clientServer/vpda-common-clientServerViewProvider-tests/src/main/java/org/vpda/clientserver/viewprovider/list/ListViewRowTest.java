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

import org.junit.jupiter.api.Test;

/**
 * @author kitko
 *
 */
public class ListViewRowTest {

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewRow#getColumnValue(int)}.
     */
    @Test
    public void testGetColumnValue() {
        ListViewRow row = new ListViewRow(new Object[] { 1, "hello", null });
        assertEquals(3, row.getLength());
        assertEquals(1, row.getColumnValue(0));
        assertEquals("hello", row.getColumnValue(1));
        assertEquals(null, row.getColumnValue(2));

    }

}
