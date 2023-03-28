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
package org.vpda.common.comps.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.vpda.common.comps.annotations.ButtonInfo;
import org.vpda.common.comps.annotations.ComponentInfo;
import org.vpda.common.comps.annotations.IconData;
import org.vpda.common.comps.loc.IconLocValue;
import org.vpda.common.comps.ui.AbstractButtonAC;
import org.vpda.common.comps.ui.CheckBoxAC;
import org.vpda.common.comps.ui.PushButtonAC;
import org.vpda.common.context.localization.LocKey;
import org.vpda.common.ioc.objectresolver.EmptyObjectResolver;
import org.vpda.common.processor.ProcessorResolver;
import org.vpda.common.processor.ctx.ClassContext;
import org.vpda.common.processor.ctx.FieldContext;
import org.vpda.common.service.localization.LocKeyInfo;
import org.vpda.common.util.AnnotationConstants;

/**
 * @author kitko
 *
 */
public class ButtonBuilderTest {

    PushButtonAC button1;

    @ComponentInfo(localId = "myLocalId", enabled = AnnotationConstants.Boolean.FALSE, visible = AnnotationConstants.Boolean.FALSE, locKey = @LocKeyInfo(path = "locPath", key = "locKey"))
    PushButtonAC button2;

    @ButtonInfo(selected = AnnotationConstants.Boolean.TRUE, mnemonic = "ALT+L", iconValue = @IconData(path = "myIcon"), disabledIconValue = @IconData(path = "myIcon2"), label = "myLbl")
    PushButtonAC button3;

    CheckBoxAC chck1;

    CheckBoxAC chck2 = new CheckBoxAC();

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testSimpleButton() throws NoSuchFieldException, SecurityException {
        FieldContext<AbstractButtonAC> button1Context = FieldContext.createRootFieldContext(ButtonBuilderTest.class.getDeclaredField("button1"), AbstractButtonAC.class);
        AbstractButtonAC b1 = new ButtonBuilder().build(button1Context, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("button1", b1.getLocalId());
        assertEquals(ButtonBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("ButtonBuilderTest.button1", b1.getId());
        assertEquals("button1", b1.getLabel());
    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testButtonAnnotatedByComponentInfo() throws NoSuchFieldException, SecurityException {
        FieldContext<AbstractButtonAC> button1Context = FieldContext.createRootFieldContext(ButtonBuilderTest.class.getDeclaredField("button2"), AbstractButtonAC.class);
        AbstractButtonAC b1 = new ButtonBuilder().build(button1Context, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("myLocalId", b1.getLocalId());
        assertEquals(ButtonBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("ButtonBuilderTest.myLocalId", b1.getId());
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
    public void testButtonAnnotatedByButtonInfo() throws NoSuchFieldException, SecurityException {
        FieldContext<AbstractButtonAC> button1Context = FieldContext.createRootFieldContext(ButtonBuilderTest.class.getDeclaredField("button3"), AbstractButtonAC.class);
        AbstractButtonAC b1 = new ButtonBuilder().build(button1Context, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertEquals("button3", b1.getLocalId());
        assertEquals(ButtonBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("ButtonBuilderTest.button3", b1.getId());
        assertTrue(b1.isEnabled());
        assertTrue(b1.isVisible());
        assertEquals(new IconLocValue("myIcon", null), b1.getIconValue());
        assertEquals(new IconLocValue("myIcon2", null), b1.getDisabledIconValue());
        assertEquals("myLbl", b1.getLabel());

    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testCheckBox() throws NoSuchFieldException, SecurityException {
        FieldContext<AbstractButtonAC> button1Context = FieldContext.createRootFieldContext(ButtonBuilderTest.class.getDeclaredField("chck1"), AbstractButtonAC.class);
        AbstractButtonAC b1 = new ButtonBuilder().build(button1Context, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertTrue(b1 instanceof CheckBoxAC);
        assertEquals("chck1", b1.getLocalId());
        assertEquals(ButtonBuilderTest.class.getSimpleName(), b1.getGroupId());
        assertEquals("ButtonBuilderTest.chck1", b1.getId());
        assertTrue(b1.isEnabled());
        assertTrue(b1.isVisible());
        assertEquals("chck1", b1.getLabel());
    }

    /**
     * Test method
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testCheckBox2() throws NoSuchFieldException, SecurityException {
        FieldContext<AbstractButtonAC> button1Context = FieldContext.createFieldContext(ClassContext.createRootInstanceClassContext(getClass(), Object.class, this),
                ButtonBuilderTest.class.getDeclaredField("chck2"), AbstractButtonAC.class);
        AbstractButtonAC b1 = new ButtonBuilder().build(button1Context, (ProcessorResolver) null, EmptyObjectResolver.getInstance());
        assertNotNull(b1);
        assertSame(chck2, b1);
        assertEquals("chck2", b1.getLabel());
    }

    /**
     * Test method for
     * {@link org.vpda.common.processor.impl.AbstractFieldBuilder#getTargetClass()}.
     */
    @Test
    public void testGetTargetClass() {
        assertEquals(AbstractButtonAC.class, new ButtonBuilder().getTargetClass());
    }

}
