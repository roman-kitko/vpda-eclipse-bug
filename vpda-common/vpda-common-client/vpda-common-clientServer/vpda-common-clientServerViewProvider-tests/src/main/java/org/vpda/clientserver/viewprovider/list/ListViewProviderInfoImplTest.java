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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.ViewProviderDef;
import org.vpda.clientserver.viewprovider.ViewProviderID;
import org.vpda.clientserver.viewprovider.ViewProviderTestHelper;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;

/**
 * Test for ListViewProviderInfoImpl
 * 
 * @author kitko
 *
 */
public class ListViewProviderInfoImplTest {

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewProviderInfo#getColumns()}.
     */
    @Test
    public void testListColumns() {
        assertTrue(createTestee(null).getColumns().size() == 1);
    }

    /** Test get groups */
    @Test
    public void testGetGroups() {
        ViewProviderID viewProviderID = createTesteeViewProviderID();
        ListViewProviderInfo listViewProviderInfoImpl = createTestee(viewProviderID);
        assertEquals(Collections.singletonList(ColumnsGroup.MAIN_GROUP), listViewProviderInfoImpl.getColumnGroupIds());
        assertNotNull(listViewProviderInfoImpl.getColumnGroup(ColumnsGroup.MAIN_GROUP));
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewProviderInfo#getViewProviderID()}.
     */
    @Test
    public void testGetViewProviderID() {
        ViewProviderID viewProviderID = createTesteeViewProviderID();
        assertEquals(viewProviderID, createTestee(viewProviderID).getViewProviderID());
    }

    /**
     * Test method for
     * {@link org.vpda.clientserver.viewprovider.list.ListViewProviderInfo#ListViewProviderInfoImpl(org.vpda.clientserver.viewprovider.ViewProviderID, java.util.List)}.
     */

    private ListViewProviderInfo createTestee(ViewProviderID viewProviderID) {
        if (viewProviderID == null) {
            viewProviderID = createTesteeViewProviderID();
        }
        List<Column> columns = new ArrayList<Column>(1);
        columns.add(createTesteeColumn());
        ColumnsGroup group = new ColumnsGroup.Builder().setLocalId(ColumnsGroup.MAIN_GROUP).setTitle(LocPair.createStringLocPair(new LocKey("testPath", "testKey"))).addColumns(columns).build();
        ColumnsGroupsDef clientGroupsListViewDef = new ColumnsGroupsDef(group);
        ListViewProviderInfo.Builder builder = new ListViewProviderInfo.Builder();
        builder.setViewProviderID(viewProviderID);
        return builder.setClientGroupsListViewDef(clientGroupsListViewDef).build();
    }

    private Column createTesteeColumn() {
        return new Column.Builder().setGroupId(ColumnsGroup.MAIN_GROUP).setLocalId("testColumn").setTitle(LocPair.createStringLocPair(new LocKey("testPath", "testKey"))).setType(String.class).build();
    }

    private ViewProviderID createTesteeViewProviderID() {
        ViewProviderDef def = ViewProviderTestHelper.createTestViewProviderDef();
        ViewProviderID viewProviderID = new ViewProviderID(ViewProviderID.generateId(), def);
        return viewProviderID;
    }

    /** Test get column info */
    @Test
    public void testGetColumnInfo() {
        assertNotNull(createTestee(null).getColumn(ListViewUtils.getColumnId(ColumnsGroup.MAIN_GROUP, "testColumn")));
    }

    /** test list column ids */
    @Test
    public void testListColumnIds() {
        assertEquals(Collections.singletonList(ListViewUtils.getColumnId(ColumnsGroup.MAIN_GROUP, "testColumn")), createTestee(null).getColumnIds());
    }

}
