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
package org.vpda.common.comps.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.vpda.common.comps.annotations.ComponentInfo;
import org.vpda.common.comps.annotations.ComponentsGroupInfo;
import org.vpda.common.comps.ui.LabelAC;
import org.vpda.common.comps.ui.PushButtonAC;
import org.vpda.common.comps.ui.def.ComponentsGroup;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.annotation.IgnoredAsInnerClass;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.impl.RegistryProcessorResolver;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/** Test */
public class ComponentsGroupClassProcessorTest {

    @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"), localId = "myGroup")
    static class Group1 {
        PushButtonAC b1 = new PushButtonAC();

        @ComponentInfo(visible = AnnotationConstants.Boolean.FALSE, locKey = @LocKeyInfo(path = "lblPath"))
        LabelAC label = new LabelAC("lbl");
    }

    @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
    static class Group2 {
        PushButtonAC b1 = new PushButtonAC();

        @org.vpda.common.comps.annotations.Components
        class Components {
            PushButtonAC b2 = new PushButtonAC();
        }
    }

    @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
    static class Group3 {
        PushButtonAC b3 = new PushButtonAC();
        Group1 group1;
        Group1 group11;
        Group2 group2;
        @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"), localId = "2222")
        Group2 group22;

        @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
        static class Group4 {
            PushButtonAC b4 = new PushButtonAC();
            Group2 group2;
        }
    }

    /** Test */
    @Test
    public void testProcess1() {
        ComponentsGroup group1 = new ComponentsGroupClassProcessor().process(ClassContext.createRootClassContext(Group1.class, ComponentsGroup.class),
                new RegistryProcessorResolver(new DefaultComponentsProcessingRegistry()), EmptyObjectResolver.getInstance());
        assertNotNull(group1);
        assertEquals("myGroup", group1.getId());
        assertEquals("myLocPath", group1.getTitle().getPathPart());
    }

    /** Test */
    @Test
    public void testProcessComponents() {
        ComponentsGroup group2 = new ComponentsGroupClassProcessor().process(ClassContext.createRootClassContext(Group2.class, ComponentsGroup.class),
                new RegistryProcessorResolver(new DefaultComponentsProcessingRegistry()), EmptyObjectResolver.getInstance());
        assertNotNull(group2);
        assertEquals(Group2.class.getSimpleName(), group2.getLocalId());
        assertEquals(Group2.class.getSimpleName(), group2.getId());
        assertNotNull(group2.getComponent("Group2.b1"));
        assertNotNull(group2.getComponent("b1"));
        assertNotNull(group2.getComponent("Group2.b2"));
        assertNotNull(group2.getComponent("b2"));
    }

    /** Test */
    @Test
    public void testProcessInnerGroups() {
        ComponentsGroup group3 = new ComponentsGroupClassProcessor().process(ClassContext.createRootClassContext(Group3.class, ComponentsGroup.class),
                new RegistryProcessorResolver(new DefaultComponentsProcessingRegistry()), EmptyObjectResolver.getInstance());
        assertNotNull(group3);
        assertEquals(Group3.class.getSimpleName(), group3.getLocalId());
        assertEquals(Group3.class.getSimpleName(), group3.getId());
        assertNotNull(group3.getComponent("b3"));
        assertNotNull(group3.getComponent("Group3.b3"));
        assertNotNull(group3.getGroup("group1"));
        assertNotNull(group3.getGroup("Group3.group1"));
        assertNotNull(group3.getGroup("Group3.group11"));
        assertNotNull(group3.getGroup("Group3.group2"));
        assertNull(group3.getGroup("Group3.group22"));
        assertNotNull(group3.getGroup("Group3.2222"));
        assertNotNull(group3.getGroup("Group3.Group4"));

    }

    @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
    static class Parent {
        @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
        static class G1 {
        }

        G1 g1;

        @IgnoredAsInnerClass
        @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
        static class G2 {
        }

        G2 g2;
    }

    /** Test */
    @Test
    public void testIgnoreAsInnerClass() {
        ComponentsGroup parent = new ComponentsGroupClassProcessor().process(ClassContext.createRootClassContext(Parent.class, ComponentsGroup.class),
                new RegistryProcessorResolver(new DefaultComponentsProcessingRegistry()), EmptyObjectResolver.getInstance());
        assertNotNull(parent);
        assertNotNull(parent.getGroup("G1"));
        assertNotNull(parent.getGroup("g1"));
        assertNull(parent.getGroup("G2"));
        assertNotNull(parent.getGroup("g2"));

    }

}
