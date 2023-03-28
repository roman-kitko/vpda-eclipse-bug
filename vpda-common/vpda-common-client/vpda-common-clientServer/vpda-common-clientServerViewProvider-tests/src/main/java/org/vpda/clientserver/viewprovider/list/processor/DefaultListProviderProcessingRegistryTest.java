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

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroup;
import org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnInfo;
import org.vpda.clientserver.viewprovider.list.annotations.Columns;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupInfo;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupsDefInfo;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.ClassItemContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.processor.impl.RegistryProcessorResolver;
import org.vpda.common.service.localization.LocKeyInfo;

/**
 * @author kitko
 *
 */
public class DefaultListProviderProcessingRegistryTest {

    @ColumnsGroupInfo(titleKey = @LocKeyInfo(path = "myPath"))
    static class Group1 {
        Column col1;

        @ColumnInfo
        Column col2;

        ColumnsGroup g1;

        @Columns
        class MyColumns {

        }
    }

    @ColumnsGroupsDefInfo
    static class Def1 {
        @ColumnsGroupInfo(titleKey = @LocKeyInfo(path = "myPath"))
        ColumnsGroup g2;
    }

    /** Test */
    @Test
    public void testResolveTargetProcessor() {
        MatcherAssert.assertThat(createResolver().resolveTargetProcessor(ClassContext.createRootClassContext(Group1.class, ColumnsGroup.class), EmptyObjectResolver.getInstance()),
                CoreMatchers.instanceOf(ColumnsGroupProcessor.class));

        MatcherAssert.assertThat(createResolver().resolveTargetProcessor(ClassContext.createRootClassContext(Def1.class, ColumnsGroupsDef.class), EmptyObjectResolver.getInstance()),
                CoreMatchers.instanceOf(ColumnsGroupsDefProcessor.class));
    }

    /**
     * Test
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testResolveTargetFieldBuilder() throws NoSuchFieldException, SecurityException {
        MatcherAssert.assertThat(
                createResolver().resolveTargetFieldBuilder(FieldContext.createRootFieldContext(Group1.class.getDeclaredField("col1"), Column.class), EmptyObjectResolver.getInstance()),
                CoreMatchers.instanceOf(ColumnFieldBuilder.class));
        MatcherAssert.assertThat(
                createResolver().resolveTargetFieldBuilder(FieldContext.createRootFieldContext(Group1.class.getDeclaredField("col2"), Column.class), EmptyObjectResolver.getInstance()),
                CoreMatchers.instanceOf(ColumnFieldBuilder.class));
        MatcherAssert.assertThat(
                createResolver().resolveTargetFieldBuilder(FieldContext.createRootFieldContext(Group1.class.getDeclaredField("g1"), ColumnsGroup.class), EmptyObjectResolver.getInstance()),
                CoreMatchers.instanceOf(ColumnsGroupFieldBuilder.class));
        MatcherAssert.assertThat(
                createResolver().resolveTargetFieldBuilder(FieldContext.createRootFieldContext(Def1.class.getDeclaredField("g2"), ColumnsGroup.class), EmptyObjectResolver.getInstance()),
                CoreMatchers.instanceOf(ColumnsGroupFieldBuilder.class));
    }

    /** Test */
    @Test
    public void testResolveTargetItemsProcessor() {
        MatcherAssert.assertThat(createResolver().resolveTargetItemsProcessor(ClassItemContext.createRootClassItemContext(Group1.MyColumns.class, Column.class), EmptyObjectResolver.getInstance()),
                CoreMatchers.instanceOf(ColumnsProcessor.class));
    }

    private ProcessorResolver createResolver() {
        return new RegistryProcessorResolver(new DefaultListProviderProcessingRegistry());
    }

}
