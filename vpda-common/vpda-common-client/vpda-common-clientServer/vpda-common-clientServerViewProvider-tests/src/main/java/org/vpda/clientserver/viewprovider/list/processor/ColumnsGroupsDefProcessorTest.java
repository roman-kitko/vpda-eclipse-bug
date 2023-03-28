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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.vpda.clientserver.viewprovider.list.Column;
import org.vpda.clientserver.viewprovider.list.ColumnsGroupsDef;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupInfo;
import org.vpda.clientserver.viewprovider.list.annotations.ColumnsGroupsDefInfo;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.impl.RegistryProcessorResolver;
import org.vpda.common.service.localization.LocKeyInfo;

/** Tests for ColumnsGroupsDefProcessor */
public class ColumnsGroupsDefProcessorTest {

    @ColumnsGroupInfo(titleKey = @LocKeyInfo(path = "myPath"))
    static class Group1 {
        Column col1;
    }

    @ColumnsGroupsDefInfo
    static class MyDef {
        Group1 myGroup1;

        @ColumnsGroupInfo(localId = "g2", titleKey = @LocKeyInfo(path = "myPath"))
        Group1 myGroup2;

        @ColumnsGroupInfo(titleKey = @LocKeyInfo(path = "myPath"))
        static class Group3 {
            Column col2;
        }

        @ColumnsGroupInfo(titleKey = @LocKeyInfo(path = "myPath"), localId = "myGroup4")
        static class Group4 {
            Column col2;
        }

    }

    /** Test */
    @Test
    public void testProcess() {
        ColumnsGroupsDef def = new ColumnsGroupsDefProcessor().process(ClassContext.createRootClassContext(MyDef.class, ColumnsGroupsDef.class), createResolver(), EmptyObjectResolver.getInstance());
        assertNotNull(def);
        assertNotNull(def.getColumnGroup("myGroup1"));
        assertNotNull(def.getColumnGroup("g2"));
        assertNull(def.getColumnGroup("myGroup2"));

        assertNotNull(def.getColumnGroup("Group3"));
        assertNotNull(def.getColumnGroup("myGroup4"));

    }

    private ProcessorResolver createResolver() {
        return new RegistryProcessorResolver(new DefaultListProviderProcessingRegistry());
    }

}
