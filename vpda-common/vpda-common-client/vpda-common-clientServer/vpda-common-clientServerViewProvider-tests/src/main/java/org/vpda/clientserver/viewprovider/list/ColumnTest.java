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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.list.Column.Builder;
import org.vpda.clientserver.viewprovider.list.Column.Flags;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.service.localization.LocPair;
import org.vpda.common.service.localization.StringLocValue;

/**
 * @author kitko
 *
 */
public class ColumnTest {

    /** */
    @Test
    public void testBuild() {
        LocPair<StringLocValue> title = LocPair.createStringLocPair(new LocKey("myLocPath"), "stlpec");
        Builder builder = new Column.Builder().setGroupId("myGroup").setLocalId("myLocalId").setType(String.class).setTitle(title);
        Column col = builder.build();
        assertEquals("myGroup", col.getGroupId());
        assertEquals("myLocalId", col.getLocalId());
        assertEquals(title, col.getTitle());
        assertEquals("myGroup.myLocalId", col.getId());
        col = builder.setLocalId("myId").build();
        assertEquals("myGroup.myId", col.getId());

        col = builder.setFlags(EnumSet.of(Column.Flags.INVISIBLE)).build();
        assertTrue(col.getFlags().contains(Column.Flags.INVISIBLE));
        assertTrue(col.isInvisible());

        col = builder.setFlags(EnumSet.noneOf(Column.Flags.class)).build();
        assertFalse(col.getFlags().contains(Column.Flags.INVISIBLE));
        assertFalse(col.isInvisible());

        col = builder.setInvisible(true).build();
        assertTrue(col.getFlags().contains(Column.Flags.INVISIBLE));
        assertTrue(col.isInvisible());

        col = builder.setInvisible(false).build();
        assertFalse(col.getFlags().contains(Column.Flags.INVISIBLE));
        assertFalse(col.isInvisible());

        col = builder.addFlags(Flags.INVISIBLE).build();
        assertTrue(col.isInvisible());
        col = builder.removeFlags(Flags.INVISIBLE).build();
        assertFalse(col.isInvisible());

        col = builder.setFlags(null).build();
        assertTrue(col.getFlags().isEmpty());
        assertFalse(col.isInvisible());

        col = builder.setProperty("myKey1", "myValue1").setProperty("myKey2", "myValue2").build();
        Column col1 = builder.setValue(col).build();
        assertEquals(col.getId(), col1.getId());
        assertEquals(col.getLocalId(), col1.getLocalId());
        assertEquals(col.getGroupId(), col1.getGroupId());
        assertEquals(col.getFlags(), col1.getFlags());
        assertEquals(col.getProperties(), col1.getProperties());
        assertEquals(col.getType(), col1.getType());
        assertEquals(col.getTitle(), col1.getTitle());

    }

    /** */
    @Test
    public void testFail() {
        LocPair<StringLocValue> title = LocPair.createStringLocPair(new LocKey("myLocPath"), "stlpec");
        Builder builder = new Column.Builder().setGroupId("myGroup").setLocalId("myLocalId").setType(String.class).setTitle(title);
        Column col = builder.build();
        testBuildFail(builder.setValue(col).setGroupId(null), "Must fail on null grouId");
        testBuildFail(builder.setValue(col).setLocalId(null), "Must fail on null localId");
        testBuildFail(builder.setValue(col).setType(null), "Must fail on null type");
        testBuildFail(builder.setValue(col).setTitle(null), "Must fail on null title");
    }

    private void testBuildFail(Column.Builder builder, String msg) {
        try {
            builder.build();
            fail(msg);
        }
        catch (RuntimeException e) {
        }
    }

}
