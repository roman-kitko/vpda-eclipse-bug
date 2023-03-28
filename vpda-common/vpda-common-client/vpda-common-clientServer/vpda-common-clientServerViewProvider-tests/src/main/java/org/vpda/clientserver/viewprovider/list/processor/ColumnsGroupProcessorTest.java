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
package org.vpda.clientserver.viewprovider.list.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnInfo;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnInfo.Visibility;
import org.vpda.clientserver.viewprovider.list.annotations.Columns;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupInfo;
import org.vpda.common.ioc.objectresolver.MacroObjectResolverImpl;
import org.vpda.common.ioc.objectresolver.ObjectResolver;
import org.vpda.common.ioc.objectresolver.SingleObjectResolver;
import org.vpda.common.processor.ProcessingRegistry;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.annotation.Ignored;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.impl.RegistryProcessorResolver;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.service.localization.LocPair;

/** */
public class ColumnsGroupProcessorTest {

    @ColumnsGroupInfo(localId = "MyGroup1", titleKey = @LocKeyInfo(path = "MyLocPath"))
    static class MyGroup {
        @ColumnInfo(localId = "MyCol2")
        Column col1;
        @ColumnInfo(type = Long.class)
        Column col2;
        Column col3;
        @Ignored
        Column col4;

        @Columns
        static class Columns1 {
            Column col5;
            @ColumnInfo(visibility = Visibility.VISIBLE)
            Column col6;
        }

        @Ignored
        @Columns
        static class Columns2 {
            Column col7;
            Column col8;
        }

        Columns3 otherColumns;
    }

    @Columns
    static class Columns3 {
        Column col9;
        Column col10;
    }

    static class MyGroupWithInners {
        Column col1;
        MyGroup innerGroup1;

        @ColumnsGroupInfo(localId = "innerWithId", titleKey = @LocKeyInfo(path = "MyLocPath"))
        MyGroup innerWithId;

        @ColumnsGroupInfo(localId = "inner2", titleKey = @LocKeyInfo(path = "MyLocPath"))
        static class MyInnerGroup2 {
            Column col1;
        }

    }

    /** */
    @Test
    public void testGetTargetClass() {
        assertEquals(ColumnsGroup.class, new ColumnsGroupProcessor().getTargetClass());
    }

    /** */
    @Test
    public void testProcess() {
        ProcessingRegistry registry = new DefaultListProviderProcessingRegistry();
        ProcessorResolver resolver = new RegistryProcessorResolver(registry);

        ObjectResolver context = new MacroObjectResolverImpl();
        ColumnsGroup group = new ColumnsGroupProcessor().process(ClassContext.createRootClassContext(MyGroup.class, ColumnsGroup.class), resolver, context);
        assertNotNull(group);
        assertEquals("MyGroup1", group.getId());
        assertEquals(LocPair.createStringLocPair("MyLocPath", null, null), group.getTitle());
        assertEquals(7, group.getColumns().size());
        assertEquals(Long.class, group.getColumn("col2").getType());
        assertNotNull(group.getColumn("col3"));
        assertNull(group.getColumn("col4"));
        assertNotNull(group.getColumn("col5"));
        assertFalse(group.getColumn("col5").isInvisible());
        assertNotNull(group.getColumn("col6"));
        assertFalse(group.getColumn("col6").isInvisible());

        assertNull(group.getColumn("col7"));
        assertNull(group.getColumn("col8"));
        assertNotNull(group.getColumn("col9"));
        assertNotNull(group.getColumn("col10"));
        assertEquals(LocPair.createStringLocPair("MyLocPath", "MyCol2", null), group.getColumn("MyCol2").getTitle());
        assertNull(group.getLocalizer());

        ColumnsGroup group1 = new ColumnsGroupProcessor().process(ClassContext.createRootClassContext(MyGroup.class, ColumnsGroup.class), resolver,
                new MacroObjectResolverImpl(context, new SingleObjectResolver<ColumnsGroup.GroupLocalizer>(EasyMock.createMock(ColumnsGroup.GroupLocalizer.class))));
        assertNotNull(group1.getLocalizer());
    }

    /** Test inner groups */
    @Test
    public void testInnerGroups() {
        ProcessingRegistry registry = new DefaultListProviderProcessingRegistry();
        ProcessorResolver resolver = new RegistryProcessorResolver(registry);

        ObjectResolver context = new MacroObjectResolverImpl();
        ColumnsGroup group = new ColumnsGroupProcessor().process(ClassContext.createRootClassContext(MyGroupWithInners.class, ColumnsGroup.class), resolver, context);
        assertNotNull(group);
        assertEquals(MyGroupWithInners.class.getSimpleName(), group.getId());
        assertNotNull(group.getColumn("col1"));
        assertNotNull(group.getGroup("innerGroup1"));
        assertNotNull(group.getGroup("MyGroupWithInners.innerGroup1"));
        assertNotNull(group.getGroup("innerWithId"));
        assertNotNull(group.getGroup("MyGroupWithInners.innerWithId"));
        assertNotNull(group.getGroup("inner2"));
        assertNotNull(group.getGroup("MyGroupWithInners.inner2"));
    }

}
