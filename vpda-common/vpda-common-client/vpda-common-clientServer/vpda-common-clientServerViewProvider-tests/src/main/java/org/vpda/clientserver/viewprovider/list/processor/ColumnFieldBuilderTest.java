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
package org.vpda.clientserver.viewprovider.list.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnInfo;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.RegistryProcessorResolver;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.service.localization.LocPair;

/**
 * @author kitko
 *
 */
public class ColumnFieldBuilderTest {

    @SuppressWarnings("unused")
    private static final class Columns {
        @ColumnInfo
        Column col1;
        @ColumnInfo(localId = "myCol2")
        Column col2;
        Column col3;
        @ColumnInfo(localId = "myCol4", type = Integer.class)
        Column col4;
        @ColumnInfo(localId = "Col", type = Long.class, visibility = ColumnInfo.Visibility.INVISIBLE, titleKey = @LocKeyInfo(path = "myPath", key = "myKey"))
        Column col5;

    }

    /**
     * Test method
     * 
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    @Test
    public void testBuild() throws SecurityException, NoSuchFieldException {
        ProcessingRegistry registry = new DefaultListProviderProcessingRegistry();
        RegistryProcessorResolver resolver = new RegistryProcessorResolver(registry);
        ColumnsGroup group = new ColumnsGroup.Builder().setLocalId("myGroup").setTitle(LocPair.createStringLocPair("myPath", "myKey", null)).build();

        ObjectResolver context = new SingleObjectResolver<ColumnsGroup>(group);
        ColumnFieldBuilder builder = new ColumnFieldBuilder();

        Column col1 = builder.build(FieldContext.createRootFieldContext(Columns.class.getDeclaredField("col1"), Column.class), resolver, context);
        assertNotNull(col1);
        assertEquals("myGroup.col1", col1.getId());
        assertEquals(String.class, col1.getType());
        assertEquals(LocPair.createStringLocPair("myPath/myKey", "col1", null), col1.getTitle());

        Column col2 = builder.build(FieldContext.createRootFieldContext(Columns.class.getDeclaredField("col2"), Column.class), resolver, context);
        assertNotNull(col2);
        assertEquals("myGroup.myCol2", col2.getId());

        Column col4 = builder.build(FieldContext.createRootFieldContext(Columns.class.getDeclaredField("col4"), Column.class), resolver, context);
        assertNotNull(col4);
        assertEquals("myGroup.myCol4", col4.getId());
        assertEquals(Integer.class, col4.getType());

        Column col5 = builder.build(FieldContext.createRootFieldContext(Columns.class.getDeclaredField("col5"), Column.class), resolver, context);
        assertNotNull(col5);
        assertEquals(Long.class, col5.getType());
        assertTrue(col5.isInvisible());
        assertEquals(LocPair.createStringLocPair("myPath", "myKey", null), col5.getTitle());
        assertNull(col5.getLocalizer());

        col5 = builder.build(FieldContext.createRootFieldContext(Columns.class.getDeclaredField("col5"), Column.class), resolver,
                new MacroObjectResolverImpl(context, new SingleObjectResolver<Column.ColumnLocalizer>(EasyMock.createMock(Column.ColumnLocalizer.class))));
        assertNotNull(col5.getLocalizer());

    }

    /** */
    @Test
    public void testGetTargetClass() {
        assertEquals(Column.class, new ColumnFieldBuilder().getTargetClass());
    }

}
