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
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup.Builder;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.StringLocValue;

/**
 * @author kitko
 *
 */
public class ColumnsGroupTest {
    /** */
    @Test
    public void testBuild() {
        Column.Builder colBuilder = new Column.Builder().setGroupId("myGroup").setLocalId("myLocalId1").setType(String.class).setTitle(LocPair.createStringLocPair(new LocKey("myLocPath"), "stlpec"));
        Column col1 = colBuilder.build();
        Column col2 = colBuilder.setLocalId("myLocalId2").build();
        Column col3 = colBuilder.setLocalId("myLocalId3").build();

        LocPair<StringLocValue> title = LocPair.createStringLocPair("path", "key", "column");
        Builder builder = new ColumnsGroup.Builder().setLocalId("myGroup").setTitle(title);
        builder.addColumns(col1, col2, col3);
        ColumnsGroup group = builder.build();

        assertEquals("myGroup", group.getId());
        assertEquals(title, group.getTitle());
        assertEquals(col1, group.getColumn("myLocalId1"));
        assertEquals(col2, group.getColumn("myLocalId2"));
        assertEquals(col3, group.getColumn("myLocalId3"));
        assertEquals(col1, group.getColumn("myGroup.myLocalId1"));

        ColumnsGroup group1 = builder.setValue(group).build();
        assertEquals(group.getId(), group1.getId());
        assertEquals(group.getTitle(), group1.getTitle());
        assertEquals(group.getColumns(), group1.getColumns());
        assertNotNull(group.getMember("myGroup.myLocalId1"));
        assertNotNull(group.locateMember("myGroup.myLocalId1"));

    }

    /**
     * 
     */
    @Test
    public void testFail() {
        Column.Builder colBuilder = new Column.Builder().setGroupId("myGroup").setLocalId("myLocalId1").setType(String.class).setTitle(LocPair.createStringLocPair(new LocKey("myLocPath"), "stlpec"));
        Column col = colBuilder.build();
        LocPair<StringLocValue> title = LocPair.createStringLocPair("path", "key", "column");
        Builder builder = new ColumnsGroup.Builder().setLocalId("myGroup").setTitle(title);
        builder.addColumns(col);
        ColumnsGroup group = builder.build();
        try {
            Column col1 = colBuilder.setGroupId("myGroup1").build();
            builder.addColumns(col1).build();
            fail("Must fail on diff groupid");
        }
        catch (RuntimeException e) {
        }

        try {
            builder.setValue(group).setLocalId(null).build();
            fail("Must fail on null id");
        }
        catch (RuntimeException e) {
        }

        try {
            builder.setValue(group).setTitle(null).build();
            fail("Must fail on null title");
        }
        catch (RuntimeException e) {
        }

    }
    
}
