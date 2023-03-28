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
/**
 * 
 */
package org.vpda.clientserver.viewprovider.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup.Builder;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.StringLocValue;

/**
 * @author kitko
 *
 */
public class GroupsListViewDefTest {

    private static ColumnsGroupsDef def;

    /**
     * 
     */
    @BeforeAll
    public static void setup() {
        Column.Builder colBuilder = new Column.Builder().setGroupId("myGroup1").setLocalId("myLocalId1").setType(String.class).setTitle(LocPair.createStringLocPair(new LocKey("myLocPath"), "stlpec"));
        Column col1 = colBuilder.build();
        Column col2 = colBuilder.setLocalId("myLocalId2").build();
        Column col3 = colBuilder.setLocalId("myLocalId3").build();
        colBuilder.setGroupId("myGroup2");

        Column col4 = colBuilder.setLocalId("myLocalId4").build();
        Column col5 = colBuilder.setLocalId("myLocalId5").build();

        LocPair<StringLocValue> title = LocPair.createStringLocPair("path", "key", "column");
        Builder groupBuilder = new ColumnsGroup.Builder().setLocalId("myGroup1").setTitle(title);
        groupBuilder.addColumns(col1, col2, col3);
        ColumnsGroup group1 = groupBuilder.build();

        ColumnsGroup group2 = groupBuilder.clearMembers().setLocalId("myGroup2").addColumns(col4, col5).build();
        def = new ColumnsGroupsDef(group1, group2);

    }

    /**
     * Test
     */
    @Test
    public void testGroupsListViewDef() {
        ColumnsGroupsDef def1 = new ColumnsGroupsDef(def.getColumnGroups());
        assertEquals(2, def1.getColumnGroups().size());
        assertEquals("myGroup1", def1.getColumnGroups().get(0).getId());
        assertEquals("myGroup2", def1.getColumnGroups().get(1).getId());

    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef#getColumnGroups()}.
     */
    @Test
    public void testListColumnGroups() {
        assertEquals(2, def.getColumnGroups().size());
        assertEquals("myGroup1", def.getColumnGroups().get(0).getId());
        assertEquals("myGroup2", def.getColumnGroups().get(1).getId());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef#getColumnsIds()}.
     */
    @Test
    public void testListColumnsIds() {
        assertEquals(Arrays.asList("myGroup1.myLocalId1", "myGroup1.myLocalId2", "myGroup1.myLocalId3", "myGroup2.myLocalId4", "myGroup2.myLocalId5"), def.getColumnsIds());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef#getColumns()}.
     */
    @Test
    public void testListColumns() {
        assertEquals(5, def.getColumns().size());
        assertEquals("myGroup1.myLocalId1", def.getColumns().get(0).getId());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef#getColumnGroupIds()}.
     */
    @Test
    public void testListColumnGroupIds() {
        assertEquals(Arrays.asList("myGroup1", "myGroup2"), def.getColumnGroupIds());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef#getColumnGroup(java.lang.String)}.
     */
    @Test
    public void testGetColumnGroup() {
        assertNotNull(def.getColumnGroup("myGroup1"));
        assertNotNull(def.getColumnGroup("myGroup2"));
        assertNull(def.getColumnGroup("myGroup3"));
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef#getColumn(java.lang.String)}.
     */
    @Test
    public void testGetColumnInfo() {
        assertNotNull(def.getColumn("myGroup1.myLocalId1"));
        assertNull(def.getColumn("myLocalId1"));
        assertNotNull(def.getColumn("myGroup1", "myLocalId1"));
        assertNotNull(def.getColumn("myGroup1", "myGroup1.myLocalId1"));
    }

}
