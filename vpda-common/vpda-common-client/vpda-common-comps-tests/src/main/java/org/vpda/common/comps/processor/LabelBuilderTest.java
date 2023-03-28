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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.vpda.common.comps.annotations.ComponentInfo;
import org.vpda.common.comps.annotations.IconData;
import org.vpda.common.comps.annotations.LabelInfo;
import org.vpda.common.comps.loc.IconLocValue;
import org.vpda.common.comps.ui.LabelAC;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/** Test of label builder */
public class LabelBuilderTest {
    LabelAC lbl1;

    @ComponentInfo(localId = "myLocalId", enabled = AnnotationConstants.Boolean.FALSE, visible = AnnotationConstants.Boolean.FALSE, locKey = @LocKeyInfo(path = "locPath", key = "locKey"))
    LabelAC lbl2;

    @LabelInfo(label = "myLbl", iconValue = @IconData(path = "myIcon"))
    LabelAC lbl3;

    LabelAC lbl4 = new LabelAC("myId", "myLbl");

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testSimpleLabel() throws NoSuchFieldException, SecurityException {
        FieldContext<LabelAC> labelContext = FieldContext.createRootFieldContext(LabelBuilderTest.class.getDeclaredField("lbl1"), LabelAC.class);
        LabelAC b1 = new LabelBuilder().build(labelContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("lbl1", b1.getLocalId());
        assertEquals(LabelBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("LabelBuilderTest.lbl1", b1.getId());
        assertEquals("lbl1", b1.getLabel());
    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testLabelAnnotatedByComponentInfo() throws NoSuchFieldException, SecurityException {
        FieldContext<LabelAC> labelContext = FieldContext.createRootFieldContext(LabelBuilderTest.class.getDeclaredField("lbl2"), LabelAC.class);
        LabelAC b1 = new LabelBuilder().build(labelContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("myLocalId", b1.getLocalId());
        assertEquals("LabelBuilderTest.myLocalId", b1.getId());
        assertFalse(b1.isEnabled());
        assertFalse(b1.isVisible());
        assertEquals(new LocKey("locPath", "locKey"), b1.getLocKey());
        assertEquals("myLocalId", b1.getLabel());

    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testLabelAnnotatedByLabelInfo() throws NoSuchFieldException, SecurityException {
        FieldContext<LabelAC> labelContext = FieldContext.createRootFieldContext(LabelBuilderTest.class.getDeclaredField("lbl3"), LabelAC.class);
        LabelAC b1 = new LabelBuilder().build(labelContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("lbl3", b1.getLocalId());
        assertEquals("LabelBuilderTest.lbl3", b1.getId());
        assertTrue(b1.isEnabled());
        assertTrue(b1.isVisible());
        assertEquals(new IconLocValue("myIcon", null), b1.getIconValue());
        assertEquals("myLbl", b1.getLabel());

    }

    /**
     * Test
     * 
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    @Test
    public void testLabelWithInstance() throws NoSuchFieldException, SecurityException {
        FieldContext<LabelAC> labelContext = FieldContext.createFieldContext(ClassContext.createRootInstanceClassContext(getClass(), Object.class, this),
                LabelBuilderTest.class.getDeclaredField("lbl4"), LabelAC.class);
        LabelAC b1 = new LabelBuilder().build(labelContext, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertSame(lbl4, b1);
        assertEquals("myLbl", b1.getLabel());
        assertEquals("myId", b1.getLocalId());

    }

}
