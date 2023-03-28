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
import org.vpda.common.comps.annotations.ComponentsGroupsDefInfo;
import org.vpda.common.comps.processor.ComponentsGroupClassProcessorTest.Group3;
import org.vpda.common.comps.ui.LabelAC;
import org.vpda.common.comps.ui.PushButtonAC;
import org.vpda.common.comps.ui.def.ComponentsGroupsDef;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.annotation.IgnoredAsInnerClass;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.impl.RegistryProcessorResolver;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/** Test */
public class ComponentsGroupDefClassProcessorTest {

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

        @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
        static class Group22 {
            PushButtonAC b22 = new PushButtonAC();
        }

    }

    @ComponentsGroupsDefInfo
    static class Def1 {
        Group1 g1;
        Group1 g11;
        @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"), localId = "otherGroup")
        Group1 g111;

        Group2 g2;

        @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
        static class Group3 {
            PushButtonAC b3 = new PushButtonAC();

            Group2 g2;

            @ComponentsGroupInfo(titleKey = @LocKeyInfo(path = "myLocPath"))
            static class Group4 {
                PushButtonAC b4 = new PushButtonAC();
            }
        }
    }

    /** test */
    @Test
    public void testGetTargetClass() {
        assertEquals(ComponentsGroupsDef.class, new ComponentsGroupsDefClassProcessor().getTargetClass());
    }

    /** test */
    @Test
    public void testProcess() {
        ComponentsGroupsDef def1 = new ComponentsGroupsDefClassProcessor().process(ClassContext.createRootClassContext(Def1.class, ComponentsGroupsDef.class),
                new RegistryProcessorResolver(new DefaultComponentsProcessingRegistry()), EmptyObjectResolver.getInstance());
        assertNotNull(def1);
        assertNotNull(def1.getGroup("g1"));
        assertNotNull(def1.getGroup("g11"));
        assertNull(def1.getGroup("g111"));
        assertNotNull(def1.getGroup("otherGroup"));
        assertNotNull(def1.getGroup("g2"));
        assertNotNull(def1.getGroup("g2.Group22"));
        assertNotNull(def1.getGroup(Group3.class.getSimpleName()));
        assertNotNull(def1.getGroup("Group3.g2"));
        assertNotNull(def1.getGroup("Group3.Group4"));

        assertNotNull(def1.getComponent("g1.b1"));
        assertNotNull(def1.getComponent("g1.label"));
        assertNotNull(def1.getComponent("g11.b1"));
        assertNotNull(def1.getComponent("otherGroup.b1"));
        assertNotNull(def1.getComponent("g2.b1"));
        assertNotNull(def1.getComponent("g2.b2"));
        assertNotNull(def1.getComponent("g2.Group22.b22"));
        assertNotNull(def1.getComponent("Group3.b3"));
        assertNotNull(def1.getComponent("Group3.Group4.b4"));
    }

    @ComponentsGroupsDefInfo()
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
        ComponentsGroupsDef parent = new ComponentsGroupsDefClassProcessor().process(ClassContext.createRootClassContext(Parent.class, ComponentsGroupsDef.class),
                new RegistryProcessorResolver(new DefaultComponentsProcessingRegistry()), EmptyObjectResolver.getInstance());
        assertNotNull(parent);
        assertNotNull(parent.getGroup("G1"));
        assertNotNull(parent.getGroup("g1"));
        assertNull(parent.getGroup("G2"));
        assertNotNull(parent.getGroup("g2"));

    }

}
